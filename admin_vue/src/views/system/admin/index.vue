<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="唯一ID" prop="visibleId">
        <el-input
          v-model="queryParams.visibleId"
          placeholder="请输入唯一ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标Id" prop="targetId">
        <el-input
          v-model="queryParams.targetId"
          placeholder="请输入目标Id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标标题" prop="targetTitle">
        <el-input
          v-model="queryParams.targetTitle"
          placeholder="请输入目标标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类来源，默认为公司创建" prop="source">
        <el-input
          v-model="queryParams.source"
          placeholder="请输入分类来源，默认为公司创建"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除" prop="isDelete">
        <el-input
          v-model="queryParams.isDelete"
          placeholder="请输入是否删除"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门ID" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门名称" prop="deptName">
        <el-input
          v-model="queryParams.deptName"
          placeholder="请输入部门名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权重" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入权重"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者用户ID" prop="createUserId">
        <el-input
          v-model="queryParams.createUserId"
          placeholder="请输入创建者用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者用户姓名" prop="createUserName">
        <el-input
          v-model="queryParams.createUserName"
          placeholder="请输入创建者用户姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新者用户ID" prop="updateUserId">
        <el-input
          v-model="queryParams.updateUserId"
          placeholder="请输入更新者用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新者用户姓名" prop="updateUserName">
        <el-input
          v-model="queryParams.updateUserName"
          placeholder="请输入更新者用户姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者部门内公司ID" prop="createDeptUserId">
        <el-input
          v-model="queryParams.createDeptUserId"
          placeholder="请输入创建者部门内公司ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建公司内用户姓名" prop="createDeptUserName">
        <el-input
          v-model="queryParams.createDeptUserName"
          placeholder="请输入创建公司内用户姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新者公司内用户ID" prop="updateDeptUserId">
        <el-input
          v-model="queryParams.updateDeptUserId"
          placeholder="请输入更新者公司内用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新者公司内用户姓名" prop="updateDeptUserName">
        <el-input
          v-model="queryParams.updateDeptUserName"
          placeholder="请输入更新者公司内用户姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司ID" prop="companyId">
        <el-input
          v-model="queryParams.companyId"
          placeholder="请输入公司ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入公司名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="adminList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="递增ID" align="center" prop="id" />
      <el-table-column label="唯一ID" align="center" prop="visibleId" />
      <el-table-column label="目标Id" align="center" prop="targetId" />
      <el-table-column label="目标标题" align="center" prop="targetTitle" />
      <el-table-column label="-1删除,0未发布1发布2关闭" align="center" prop="status" />
      <el-table-column label="分类来源，默认为公司创建" align="center" prop="source" />
      <el-table-column label="范围方式dept部门user用户" align="center" prop="type" />
      <el-table-column label="是否删除" align="center" prop="isDelete" />
      <el-table-column label="部门ID" align="center" prop="deptId" />
      <el-table-column label="部门名称" align="center" prop="deptName" />
      <el-table-column label="权重" align="center" prop="weight" />
      <el-table-column label="创建者用户ID" align="center" prop="createUserId" />
      <el-table-column label="创建者用户姓名" align="center" prop="createUserName" />
      <el-table-column label="更新者用户ID" align="center" prop="updateUserId" />
      <el-table-column label="更新者用户姓名" align="center" prop="updateUserName" />
      <el-table-column label="创建者部门内公司ID" align="center" prop="createDeptUserId" />
      <el-table-column label="创建公司内用户姓名" align="center" prop="createDeptUserName" />
      <el-table-column label="更新者公司内用户ID" align="center" prop="updateDeptUserId" />
      <el-table-column label="更新者公司内用户姓名" align="center" prop="updateDeptUserName" />
      <el-table-column label="公司ID" align="center" prop="companyId" />
      <el-table-column label="公司名称" align="center" prop="companyName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加或修改1212对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="唯一ID" prop="visibleId">
          <el-input v-model="form.visibleId" placeholder="请输入唯一ID" />
        </el-form-item>
        <el-form-item label="目标Id" prop="targetId">
          <el-input v-model="form.targetId" placeholder="请输入目标Id" />
        </el-form-item>
        <el-form-item label="目标标题" prop="targetTitle">
          <el-input v-model="form.targetTitle" placeholder="请输入目标标题" />
        </el-form-item>
        <el-form-item label="分类来源，默认为公司创建" prop="source">
          <el-input v-model="form.source" placeholder="请输入分类来源，默认为公司创建" />
        </el-form-item>
        <el-form-item label="是否删除" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入是否删除" />
        </el-form-item>
        <el-form-item label="部门ID" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门ID" />
        </el-form-item>
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="权重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入权重" />
        </el-form-item>
        <el-form-item label="创建者用户ID" prop="createUserId">
          <el-input v-model="form.createUserId" placeholder="请输入创建者用户ID" />
        </el-form-item>
        <el-form-item label="创建者用户姓名" prop="createUserName">
          <el-input v-model="form.createUserName" placeholder="请输入创建者用户姓名" />
        </el-form-item>
        <el-form-item label="更新者用户ID" prop="updateUserId">
          <el-input v-model="form.updateUserId" placeholder="请输入更新者用户ID" />
        </el-form-item>
        <el-form-item label="更新者用户姓名" prop="updateUserName">
          <el-input v-model="form.updateUserName" placeholder="请输入更新者用户姓名" />
        </el-form-item>
        <el-form-item label="创建者部门内公司ID" prop="createDeptUserId">
          <el-input v-model="form.createDeptUserId" placeholder="请输入创建者部门内公司ID" />
        </el-form-item>
        <el-form-item label="创建公司内用户姓名" prop="createDeptUserName">
          <el-input v-model="form.createDeptUserName" placeholder="请输入创建公司内用户姓名" />
        </el-form-item>
        <el-form-item label="更新者公司内用户ID" prop="updateDeptUserId">
          <el-input v-model="form.updateDeptUserId" placeholder="请输入更新者公司内用户ID" />
        </el-form-item>
        <el-form-item label="更新者公司内用户姓名" prop="updateDeptUserName">
          <el-input v-model="form.updateDeptUserName" placeholder="请输入更新者公司内用户姓名" />
        </el-form-item>
        <el-form-item label="公司ID" prop="companyId">
          <el-input v-model="form.companyId" placeholder="请输入公司ID" />
        </el-form-item>
        <el-form-item label="公司名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入公司名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAdmin, getAdmin, delAdmin, addAdmin, updateAdmin } from "@/api/system/admin";

export default {
  name: "Admin",
  data() {
    return {
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
      // 1212表格数据
      adminList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        visibleId: null,
        targetId: null,
        targetTitle: null,
        status: null,
        source: null,
        type: null,
        isDelete: null,
        deptId: null,
        deptName: null,
        weight: null,
        createUserId: null,
        createUserName: null,
        updateUserId: null,
        updateUserName: null,
        createDeptUserId: null,
        createDeptUserName: null,
        updateDeptUserId: null,
        updateDeptUserName: null,
        companyId: null,
        companyName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        visibleId: [
          { required: true, message: "唯一ID不能为空", trigger: "blur" }
        ],
        targetId: [
          { required: true, message: "目标Id不能为空", trigger: "blur" }
        ],
        targetTitle: [
          { required: true, message: "目标标题不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "-1删除,0未发布1发布2关闭不能为空", trigger: "blur" }
        ],
        source: [
          { required: true, message: "分类来源，默认为公司创建不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "范围方式dept部门user用户不能为空", trigger: "change" }
        ],
        isDelete: [
          { required: true, message: "是否删除不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "部门ID不能为空", trigger: "blur" }
        ],
        deptName: [
          { required: true, message: "部门名称不能为空", trigger: "blur" }
        ],
        weight: [
          { required: true, message: "权重不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        createUserId: [
          { required: true, message: "创建者用户ID不能为空", trigger: "blur" }
        ],
        createUserName: [
          { required: true, message: "创建者用户姓名不能为空", trigger: "blur" }
        ],
        updateUserId: [
          { required: true, message: "更新者用户ID不能为空", trigger: "blur" }
        ],
        updateUserName: [
          { required: true, message: "更新者用户姓名不能为空", trigger: "blur" }
        ],
        createDeptUserId: [
          { required: true, message: "创建者部门内公司ID不能为空", trigger: "blur" }
        ],
        createDeptUserName: [
          { required: true, message: "创建公司内用户姓名不能为空", trigger: "blur" }
        ],
        updateDeptUserId: [
          { required: true, message: "更新者公司内用户ID不能为空", trigger: "blur" }
        ],
        updateDeptUserName: [
          { required: true, message: "更新者公司内用户姓名不能为空", trigger: "blur" }
        ],
        companyId: [
          { required: true, message: "公司ID不能为空", trigger: "blur" }
        ],
        companyName: [
          { required: true, message: "公司名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询1212列表 */
    getList() {
      this.loading = true;
      listAdmin(this.queryParams).then(response => {
        this.adminList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        visibleId: null,
        targetId: null,
        targetTitle: null,
        status: 0,
        source: null,
        type: null,
        isDelete: null,
        deptId: null,
        deptName: null,
        weight: null,
        createTime: null,
        updateTime: null,
        createUserId: null,
        createUserName: null,
        updateUserId: null,
        updateUserName: null,
        createDeptUserId: null,
        createDeptUserName: null,
        updateDeptUserId: null,
        updateDeptUserName: null,
        companyId: null,
        companyName: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加1212";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAdmin(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改1212";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAdmin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAdmin(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除1212编号为"' + ids + '"的数据项？').then(function() {
        return delAdmin(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/admin/export', {
        ...this.queryParams
      }, `admin_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
