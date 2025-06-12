<template>
  <div class="app-container role-admin-box" :style="{ 'height': windowHeight + 'px' }">
    <!-- 角色 -->
    <div class="left-role">
      <div class="tit-name">角色</div>

      <div class="role-list">
        <div class="add" @click="handleAdd">
          <i class="iconfont icon-tianjia"></i>
          <span>添加角色</span>
        </div>
        <div class="role-item" :class="{ 'active': activeRoleId == item.roleId }" v-for="(item, index) in roleList"
          :key="index">
          <div class="name" @click="choiceRoleItem(item.roleId)">{{ item.roleName }}</div>
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
        </el-row>
        <el-table v-loading="loading" :data="userChildList" :header-cell-style="{ background: '#eaeaea', color: '#000' }">
          <el-table-column prop="userName" label="姓名">
            <template slot-scope="scope">
              <!-- 兼容企微用户姓名及部门名称 -->
              <OpenDataCom type="userName" :openid="scope.row.userName" :defaultname="scope.row.userName"></OpenDataCom>
            </template>
          </el-table-column>
          <el-table-column prop="scopeNames" label="数据权限范围">
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleDataScope(scope.row)"
                v-if="scope.row.roleType != 'admin'">数据权限</el-button>
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
    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选</el-checkbox>
          <el-tree class="tree-border" :data="menuOptions" default-expand-all show-checkbox ref="menu" node-key="id"
            :check-strictly="!form.menuCheckStrictly" empty-text="加载中，请稍候" :props="defaultProps">
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <OpenDataCom type="departmentName" :openid="node.label" :defaultname="node.label"></OpenDataCom>
            </span>
          </el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <org-picker title="请选择人员添加至此角色"   choiceType="user" multiple ref="orgRequired" :selected="selectRequired"
      @ok="selected"></org-picker>

    <!-- 分配数据权限对话框 -->
    <el-dialog title="分配数据权限" :visible.sync="openDataScope2" width="500px" append-to-body>
      <dataScopeCom :userId="userId" :roleId="activeRoleId" v-if="openDataScope2" @ok="submit"></dataScopeCom>
    </el-dialog>


    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :show-close="false" :close-on-click-modal="false"
      :close-on-press-escape="false">
      <div>
        <span>确认要取消该用户</span>
        <OpenDataCom type="userName" :openid="choiceduserName" :defaultname="choiceduserName"></OpenDataCom>
        <span>角色吗？</span>
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

import { treeselect as menuTreeselect, roleMenuTreeselect } from "@/api/system/menu";
import { treeselect as deptTreeselect, roleDeptTreeselect } from "@/api/system/dept";
import selectUser from "./selectUser";
import dataScopeCom from "./dataScope";
import OrgPicker from "@/components/Common/OrgPicker";
export default {
  name: "Role",
  dicts: ['sys_normal_disable'],
  components: { selectUser, dataScopeCom, OrgPicker },
  data() {
    return {
      windowHeight: 0,
      activeRoleId: "", // 选中的角色
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
      // 总条数
      total: 0,
      // 角色表格数据
      roleList: [],
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
        roleId: undefined,
        userName: undefined,
        phonenumber: undefined
      },
      userChildList: [], // 角色下用户列表
      selectRequired: [],
      //选中用户
      userId: '',
      // 非多个禁用
      multiple: true,
      openDataScope2: false,
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
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleKey: [
          { required: true, message: "权限字符不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色顺序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.windowHeight = window.innerHeight - 80;
    this.getList();
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      listRole(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.roleList = response.data;
        this.loading = false;
        this.activeRoleId = this.roleList[0].roleId
        this.childParams.roleId = this.activeRoleId;
        this.getChildList()
      }
      );
    },
    /** 查询菜单树结构 */
    getMenuTreeselect() {
      menuTreeselect().then(response => {
        this.menuOptions = response.data;
      });
    },
    /** 查询部门树结构 */
    getDeptTreeselect() {
      deptTreeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      // 半选中的菜单节点
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    // 所有部门节点数据
    getDeptAllCheckedKeys() {
      // 目前被选中的部门节点
      let checkedKeys = this.$refs.dept.getCheckedKeys();
      // 半选中的部门节点
      let halfCheckedKeys = this.$refs.dept.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    /** 根据角色ID查询菜单树结构 */
    getRoleMenuTreeselect(roleId) {
      return roleMenuTreeselect(roleId).then(response => {
        this.menuOptions = response.menus;
        return response;
      });
    },
    /** 根据角色ID查询部门树结构 */
    getRoleDeptTreeselect(roleId) {
      return roleDeptTreeselect(roleId).then(response => {
        this.deptOptions = response.depts;
        return response;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {

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
          roleId: undefined,
          roleName: undefined,
          roleKey: undefined,
          roleSort: 0,
          menuIds: [],
          deptIds: [],
          menuCheckStrictly: true,
          deptCheckStrictly: true,
          remark: undefined
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      if (type == 'menu') {
        this.$refs.menu.setCheckedNodes(value ? this.menuOptions : []);
      } else if (type == 'dept') {
        this.$refs.dept.setCheckedNodes(value ? this.deptOptions : []);
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getMenuTreeselect();
      this.open = true;
      this.title = "添加角色";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const roleId = row.roleId || this.ids
      const roleMenu = this.getRoleMenuTreeselect(roleId);
      getRole(roleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.$nextTick(() => {
          roleMenu.then(res => {
            let checkedKeys = res.checkedKeys
            checkedKeys.forEach((v) => {
              // this.$refs.menu.store.nodesMap[v.id].expanded = true;
              this.$nextTick(() => {
                this.$refs.menu.setChecked(v, true, false);
              })
            })
          });
        });
        this.title = "修改角色";
      });
    },
    /** 选择角色权限范围触发 */
    dataScopeSelectChange(value) {
      if (value !== '2') {
        this.$refs.dept.setCheckedKeys([]);
      }
    },
    /** 分配用户操作 */
    handleAuthUser: function (row) {
      this.$router.push("/system/role-auth/user/" + row.roleId);
    },
    choiceRoleItem(id) {
      this.activeRoleId = id
      this.childParams.roleId = id;
      this.getChildList()
    },
    /** 查询授权用户列表 */
    getChildList() {
      this.loading = true;
      allocatedUserList(this.childParams).then(response => {
        this.userChildList = response.rows;
        this.total = response.total;
        this.loading = false;
      }
      );
    },
    /** 分配数据权限操作 */
    handleDataScope(row) {
      this.userId = row.userId;
      this.openDataScope2 = true;
    },
    submit() {
      this.openDataScope2 = false;
      this.getChildList();
    },
    /** 取消授权按钮操作 */
    confirmClose() {
      let data = {
        userId: this.userId,
        roleId: this.roleId
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
      this.roleId = row.roleId
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
        return authUserCancelAll({ roleId: this.activeRoleId, userIds: userIds });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消授权成功");
      }).catch(() => { });
    },
    selected(values) {
      let that = this;
      const userIds = [];
      values.forEach(item => {
        userIds.push(item.uuid);
      });
      if (userIds == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      let data = {
        roleId: this.activeRoleId,
        userIds: userIds
      }
      authUserSelectAll(data).then(res => {
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
          if (this.form.roleId != undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            updateRole(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            addRole(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮（数据权限） */
    submitDataScope: function () {
      if (this.form.roleId != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.openDataScope = false;
          this.getList();
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const roleIds = row.roleId || this.ids;
      this.$modal.confirm('是否确认删除该角色项？').then(function () {
        return delRole(roleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/role/export', {
        ...this.queryParams
      }, `role_${new Date().getTime()}.xlsx`)
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
