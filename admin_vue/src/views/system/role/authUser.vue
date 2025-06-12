<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="openSelectUser"
        >添加用户</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-circle-close"
          size="mini"
          :disabled="multiple"
          @click="cancelAuthUserAll"
        >批量取消授权</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleClose"
        >关闭</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" prop="userName" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDataScope(scope.row)"
          >数据权限</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-circle-close"
            @click="cancelAuthUser(scope.row)"
          >取消授权</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <select-user ref="select" :roleId="queryParams.roleId" @ok="handleQuery" />\

    <org-picker title="请选择可以管理此表单的人员"  multiple ref="orgRequired" :selected="selectRequired" @ok="selected"></org-picker>

    <!-- 分配数据权限对话框 -->
    <el-dialog title="分配数据权限" :visible.sync="openDataScope" width="500px" append-to-body>
      <dataScope :userId="userId" v-if="openDataScope" @ok="submit"></dataScope>
    </el-dialog>
  </div>
</template>

<script>
import { roleDeptTreeselect } from "@/api/system/dept";
import { getRole, allocatedUserList, authUserCancel, authUserCancelAll,authUserSelectAll } from "@/api/system/role";

import selectUser from "./selectUser";
import dataScope from "./dataScope";
import OrgPicker from "@/components/Common/OrgPicker";

export default {
  name: "AuthUser",
  dicts: ['sys_normal_disable'],
  components: { selectUser,dataScope,OrgPicker },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中用户组
      userIds: [],
      //选中用户
      userId: '',
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      openDataScope: false,
      deptExpand: true,
      deptNodeAll: false,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: [],
      // 表单参数
      form: {
        dataScope: 1,
        deptCheckStrictly: true
      },
      selectRequired: [],
      // 数据范围选项
      dataScopeOptions: [
        {
          value: "1",
          label: "全部数据权限"
        },
        {
          value: "2",
          label: "自定数据权限"
        },
        {
          value: "3",
          label: "本部门及以下数据权限"
        }
      ],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: undefined,
        userName: undefined,
        phonenumber: undefined
      }
    };
  },
  created() {
    const roleId = this.$route.params && this.$route.params.roleId;
    if (roleId) {
      this.queryParams.roleId = roleId;
      this.getList();
    }
  },
  methods: {
    selected(values) {
      let that = this;
      const roleId = this.queryParams.roleId;
      const userIds = [];
      values.forEach(item => {
          userIds.push(item.uuid);
      });
      if (userIds == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      let data = {
        roleId: roleId,
        userIds: userIds
      }
      authUserSelectAll(data).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    },
    openSelectUser() {
      this.$refs.orgRequired.show()
    },
    /** 选择角色权限范围触发 */
    dataScopeSelectChange(value) {
      if(value !== '2') {
        this.$refs.dept.setCheckedKeys([]);
      }
    },
    // 表单重置
    reset() {
      this.deptExpand = true,
      this.deptNodeAll = false,
      this.form = {
        dataScope: 1,
        deptCheckStrictly: true
      };
      this.resetForm("form");
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value) {
      let treeList = this.deptOptions;
      for (let i = 0; i < treeList.length; i++) {
        this.$refs.dept.store.nodesMap[treeList[i].id].expanded = value;
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value) {
      this.$refs.dept.setCheckedNodes(value ? this.deptOptions: []);
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value) {
      this.form.deptCheckStrictly = value ? true: false;
    },
    /** 分配数据权限操作 */
    handleDataScope(row) {
      this.userId = row.userId;
      this.openDataScope = true;
    },
    /** 提交按钮（数据权限） */
    submitDataScope: function() {
      dataScope(this.form).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.openDataScope = false;
        this.getList();
      });
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
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    /** 根据角色ID查询部门树结构 */
    getRoleDeptTreeselect(roleId) {
      return roleDeptTreeselect(roleId).then(response => {
        this.deptOptions = response.depts;
        return response;
      });
    },
    /** 查询授权用户列表 */
    getList() {
      this.loading = true;
      allocatedUserList(this.queryParams).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 返回按钮
    handleClose() {
      const obj = { path: "/system/role" };
      this.$tab.closeOpenPage(obj);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userIds = selection.map(item => item.userId)
      this.multiple = !selection.length
    },
    /** 取消授权按钮操作 */
    cancelAuthUser(row) {
      const roleId = this.queryParams.roleId;
      this.$modal.confirm('确认要取消该用户"' + row.userName + '"角色吗？').then(function() {
        return authUserCancel({ userId: row.userId, roleId: roleId });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消授权成功");
      }).catch(() => {});
    },
    /** 批量取消授权按钮操作 */
    cancelAuthUserAll(row) {
      const roleId = this.queryParams.roleId;
      const userIds = this.userIds.join(",");
      this.$modal.confirm('是否取消选中用户授权数据项？').then(function() {
        return authUserCancelAll({ roleId: roleId, userIds: userIds });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消授权成功");
      }).catch(() => {});
    },
    submit() {
      this.openDataScope = false;
    }
  }
};
</script>
