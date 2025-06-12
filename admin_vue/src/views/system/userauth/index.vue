<template>
  <div class="app-container role-admin-box" :style="{ 'height': windowHeight + 'px' }">
    <!-- 用户组 -->
    <div class="left-role">
      <div class="tit-name">用户组</div>

      <div class="role-list">
        <div class="add" @click="handleAdd">
          <i class="iconfont icon-tianjia"></i>
          <span>添加用户组</span>
        </div>
        <div class="role-item" :class="{ 'active': activeGroupId == item.uuid }" v-for="(item, index) in groupList"
          :key="index">
          <div class="name" @click="choiceRoleItem(item.uuid)">{{ item.name }}</div>
          <div class="edit" v-if="item.roleKey != 'admin'">
            <i class="iconfont icon-bianji" @click="handleUpdate(item)"></i>
            <i class="iconfont icon-shanchu" @click="handleDelete(item)"></i>
          </div>
        </div>
      </div>

    </div>
    <div class="right-data">
      <el-card class="box-card">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openSelectUser">添加用户</el-button>
          </el-col>

          <right-toolbar :showSearch.sync="showSearch" @queryTable="getChildList"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="userChildList">
          <!-- <el-table-column type="selection" width="55" align="center" /> -->
          <el-table-column prop="userName" label="姓名">
            <template slot-scope="scope">
              <!-- 兼容企微用户姓名及部门名称 -->
              <OpenDataCom type="userName" :openid="scope.row.userName" :defaultname="scope.row.userName"></OpenDataCom>
            </template>
          </el-table-column>
          <el-table-column prop="jobNumber" label="工号"/>
          <el-table-column prop="deptNames" label="所在部门"/>
          <el-table-column prop="createTime" label="加入时间"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-circle-close" @click="cancelAuthUser(scope.row)"
               v-if="total > 1">
              取消授权</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="bottom-block">
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />
        </div>

      </el-card>
    </div>
    <!-- 添加或修改用户组配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="用户组名称">
          <el-input v-model="form.name" placeholder="请输入用户组名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <org-picker title="请选择人员添加至此用户组"   choiceType="user" multiple ref="orgRequired" :selected="selectRequired"
      @ok="selected"></org-picker>

    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :show-close="false" :close-on-click-modal="false"
      :close-on-press-escape="false">
      <div>
        <span>确认要取消该用户</span>
        <OpenDataCom type="userName" :openid="choiceduserName" :defaultname="choiceduserName"></OpenDataCom>
        <span>吗？</span>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmClose">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { listRole, getRole, delRole, addRole, updateRole, dataScope, allocatedUserList, authUserCancel, authUserCancelAll, authUserSelectAll } from "@/api/system/role";

import { listUserGroup,getUserGroup,addUserGroup,updateUserGroup,getUserGroupItemList,authUserGroupSelectAll } from "@/api/system/usergroup";

import OrgPicker from "@/components/Common/OrgPicker";
export default {
  name: "Role",
  dicts: ['sys_normal_disable'],
  components: { OrgPicker },
  data() {
    return {
      windowHeight: 0,
      activeGroupId: "", // 选中的用户组
      dialogVisible: false,
      choiceduserName: '', //
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户组表格数据
      groupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 日期范围
      dateRange: [],
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined
      },
      // 查询参数
      childParams: {
        pageNum: 1,
        pageSize: 10,
        groupId: undefined,
        userName: undefined,
      },
      userChildList: [], // 用户组下用户列表
      selectRequired: [],
      //选中用户
      userId: '',
      // 非多个禁用
      multiple: true,
      deptExpand: true,
      deptNodeAll: false,
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户组名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.windowHeight = window.innerHeight - 80;
    this.getList();
  },
  methods: {
    /** 查询用户组列表 */
    getList() {
      this.loading = true;
      listUserGroup(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.groupList = response.rows;
        this.loading = false;
        this.activeGroupId = this.groupList[0].uuid;
        this.childParams.groupId = this.activeGroupId;
        this.getChildList()
      }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.menuExpand = false,
        this.menuNodeAll = false,
        this.deptExpand = true,
        this.deptNodeAll = false,
        this.form = {
          uuid: undefined,
          name: undefined
        };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户组";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getUserGroup(row.uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户组";
      });
    },
    choiceRoleItem(id) {
      this.activeGroupId = id
      this.childParams.groupId = id;
      this.getChildList()
    },
    /** 查询授权用户列表 */
    getChildList() {
      this.loading = true;
      this.userChildList = [];
      getUserGroupItemList(this.childParams).then(response => {
        this.userChildList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 取消授权按钮操作 */
    confirmClose() {
      let data = {
        userId: this.userId,
        groupId: this.groupId
      }
      authUserCancel(data).then(res => {
        this.$modal.msgSuccess("取消授权成功");
        if (res.code === 200) {
          this.dialogVisible = false;
          this.getChildList()
        }
      });
    },
    cancelAuthUser(row) {
      this.userId = row.userId
      this.groupId = row.groupId
      this.dialogVisible = true
      this.choiceduserName = row.userName

    },
    openSelectUser() {
      this.$refs.orgRequired.show()
    },
    /** 批量取消授权按钮操作 */
    cancelAuthUserAll(row) {
      const userIds = this.userIds.join(",");
      this.$modal.confirm('是否取消选中用户授权数据项？').then(function () {
        return authUserCancelAll({ groupId: this.activeGroupId, userIds: userIds });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消授权成功");
      }).catch(() => { });
    },
    selected(values) {
      let that = this;
      const userIds = [];
      values.forEach(item => {
        userIds.push({
          uuid: item.uuid,
          showName: item.showName
        });
      });
      if (userIds.length == 0) {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      let data = {
        groupId: this.activeGroupId,
        userItems: userIds
      }
      authUserGroupSelectAll(data).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.getChildList()
        }
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != undefined) {
            updateUserGroup(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUserGroup(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const groupIds = row.groupId || this.ids;
      this.$modal.confirm('是否确认删除该用户组项？').then(function () {
        return delRole(groupIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    }
  }
};
</script>

<style lang="less">
.role-admin-box {
  width: 100%;
  position: relative;
  padding-right: 15px;
  padding-left: 260px;
  box-sizing: border-box;
  padding-top: 15px;
  box-sizing: border-box;

  .left-role {
    width: 230px;
    position: absolute;
    left: 15px;
    top: 15px;
    height: calc(100% - 15px);
    overflow-y: auto;
    border: 1px solid #eee;
    border-radius: 6px;
    box-sizing: border-box;
    background: #fff;

    .tit-name {
      width: 100%;
      height: 60px;
      text-align: center;
      line-height: 60px;
      font-size: 16px;
      color: #333;
      border-bottom: 1px solid #eee;
    }

    .role-list {
      padding: 10px 10px 0;
      box-sizing: border-box;

      .add {
        width: 100%;
        height: 40px;
        border: 1px solid #eee;
        border-radius: 4px;
        font-size: 14px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 10px;
        cursor: pointer;

        .iconfont {
          font-size: 14px;
          margin-right: 4px;
        }
      }

      .role-item {
        width: 100%;
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: space-between;

        &.active {
          .name {
            font-size: 14px;
            color: #4a7ff0;
          }
        }

        .name {
          font-size: 14px;
          color: #333;
          cursor: pointer;
        }

        .edit {

          .iconfont {
            font-size: 14px;
            margin-left: 10px;
            cursor: pointer;

            &:hover {
              color: #4a7ff0;
            }
          }
        }
      }
    }
  }

  .right-data {
    width: 100%;
    height: 100%;
    overflow-y: auto;
    background: #fff;

    .box-card {
      box-shadow: none;
      width: 100%;
      height: 100%;
      position: relative;
      padding-bottom: 60px;
      box-sizing: border-box;

      .bottom-block {
        position: absolute;
        bottom: 10px;
        left: 0;
        width: 100%;

      }
    }
  }
}
</style>
