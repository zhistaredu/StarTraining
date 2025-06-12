<template>
  <div class="app-container">
    <el-form :model="form" label-width="80px">
      <el-form-item label="权限范围">
        <el-select v-model="form.scopeType" @change="dataScopeSelectChange">
          <el-option v-for="item in dataScopeOptions" :key="item.value" :label="item.label"
            :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="数据权限" v-show="form.scopeType == 2">
        <el-tree class="tree-border" :data="deptOptions" show-checkbox default-expand-all ref="dept" node-key="id"
          :check-strictly="!deptCheckStrictly" empty-text="加载中，请稍候" :props="defaultProps">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <OpenDataCom type="departmentName" :openid="node.label" :defaultname="node.label"></OpenDataCom>
          </span>
        </el-tree>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitDataScope">确 定</el-button>
      <el-button @click="cancelDataScope">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { roleDeptTreeselect } from "@/api/system/dept";
import { getRole, allocatedUserList, authUserCancel, dataScope, authUserCancelAll } from "@/api/system/role";
export default {
  name: "AuthUser",
  dicts: ['sys_normal_disable'],
  components: {  },
  props: {
    userId: {
      type: String,
      default: ''
    },
    roleId: {
      type: String,
      default: ''
    },
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中用户组
      userIds: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      deptExpand: true,
      deptCheckStrictly: false,
      deptNodeAll: false,
      // 总条数
      total: 0,
      // 表单参数
      form: {
        scopeType: 1,
      },
      // 数据范围选项
      dataScopeOptions: [
        {
          value: 1,
          label: "全公司数据权限"
        },
        {
          value: 2,
          label: "自定数据权限"
        },
        {
          value: 3,
          label: "所在部门及以下数据权限"
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
  mounted() {
    this.handleDataScope();
  },
  methods: {
    /** 选择角色权限范围触发 */
    dataScopeSelectChange(value) {
      if (value !== 2) {
        this.$refs.dept.setCheckedKeys([]);
      }
    },
    handleDataScope() {
      this.getRoleDeptTreeselect(this.userId, this.roleId);
    },
    /** 根据角色ID查询部门树结构 */
    getRoleDeptTreeselect(userId, roleId) {
      let data = {
        userId: userId,
        roleId: roleId
      }
      roleDeptTreeselect(data).then(response => {
        this.deptOptions = response.depts;
        if (response.scopeType == 2) {
          this.$refs.dept.setCheckedKeys(response.checkedKeys);
        }
        this.form.scopeType = response.scopeType;
      });
    },
    /** 提交按钮（数据权限） */
    submitDataScope: function () {
      let scopeIds = this.getDeptAllCheckedKeys();
      if (Array.isArray(scopeIds)) {
        this.form.scopeIds = scopeIds.join(",");
      } else {
        this.form.scopeIds = '';
      }
      this.form.roleId = this.roleId;
      this.form.userId = this.userId;
      dataScope(this.form).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.$emit('ok', "submit");
      });
    },
    // 所有部门节点数据
    getDeptAllCheckedKeys() {
      // 目前被选中的部门节点
      let checkedKeys = this.$refs.dept.getCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys);
      return checkedKeys;
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.$emit('ok', "submit");
    }
  }
};
</script>
