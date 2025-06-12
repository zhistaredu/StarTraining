<template>
  <div class="app-container teacher-container">
    <el-tabs v-model="courseStatus" @tab-click="changeCourseStatus">
      <el-tab-pane label="已启用" name="published"></el-tab-pane>
      <el-tab-pane label="已停用" name="close"></el-tab-pane>
    </el-tabs>

    <div class="filter-box">
      <div class="filter-left">
        <div class="block">
          <el-select v-model="queryParams.lectorGrade" clearable placeholder="请选择">
            <el-option
              v-for="item in gradeList"
              :key="item.uuid"
              :label="item.title"
              :value="item.uuid">
            </el-option>
          </el-select>
        </div>

        <div class="search">
          <el-input v-model="searchValue" placeholder="请输入搜索内容"></el-input>
          <div class="btn" @click="confirmSearch">搜索</div>
        </div>
      </div>
      <div class="filter-right">
        <el-button type="primary" @click="addLectorfun">新建</el-button>
      </div>
    </div>

    <div class="teacher-list">
      <el-table v-loading="loading" :data="courseList" :header-cell-style="{ background: '#e8eef8', color: '#000' }" :border="false" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column fixed prop="lectorName" label="讲师名称" min-width="300">
          <template slot-scope="scope">
            <!-- 兼容企微用户姓名及部门名称 -->
            <OpenDataCom type="userName" :openid="scope.row.lectorName" :defaultname="scope.row.lectorName"></OpenDataCom>
          </template>
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="120">
          <template slot-scope="scope">
            <span v-if="scope.row.sex = 'man'">男</span>
            <span v-else-if="scope.row.sex = 'woman'">女</span>
          </template>
        </el-table-column>
        <el-table-column prop="lectorGradeName" label="讲师等级" width="120">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="editLector(scope.row)">编辑</el-button>
            <el-button @click="publishLector(scope.row.lectorId, scope.$index)" type="text" size="small"
              v-if="scope.row.status == 2">启用</el-button>
            <el-button @click="closeLector(scope.row.lectorId, scope.$index)" type="text" size="small"
              v-if="scope.row.status == 1">停用</el-button>
            <el-button @click="deleteLector(scope.row.lectorId, scope.$index)" type="text" size="small"
              v-if="scope.row.status == 2">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <el-dialog :title="dialogTitle" :visible.sync="lectorVisible" width="700px">
      <add-lector :editLectorUuid="editLectorUuid" v-if="lectorVisible" @close="closeAdd"></add-lector>
    </el-dialog>
  </div>
</template>

<script>
import {
  listLector,
  publishLector,
  closeLector,
  delLector,
} from "@/api/system/lector";
import addLector from "@/views/teachers/lector/addLector";
import { listJavaCategory } from "@/api/learn/category";
import {
  listLectorGrade
} from "@/api/system/lectorGrade";

export default {
  name: "Course",
  dicts: ["sys_user_sex"],
  components: {
    addLector,
  },
  data() {
    return {
      courseStatus: "published", // 课程状态
      categoryList: [], // 分类列表
      searchValue: "", // 搜索内容
      lectorVisible: false,
      editLectorUuid: '', // 编辑的讲师详情
      status:
        this.$route.query.status == undefined ? 1 : this.$route.query.status,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      gradeList: [],
      // 课程管理表格数据
      courseList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status: 1, // 1 已发布  0 未发布  2 已关闭
        categoryId: "", // 分类
        searchValue: "", // 搜索
        lectorGrade: "",
      },
      expandOnClickNode: true,
      categoryTitle: "",
      treeProps: {
        children: "children",
        label: "label",
      },
      dialogTitle: '添加讲师',
      isClick: false, // 防止发布按钮二次点击
    };
  },
  created() {
    if (this.status == 1) {
      this.courseStatus = "published";
    } else if (this.status == 2) {
      this.courseStatus = "close";
    }
    this.queryParams.status = this.status;
    this.getList();
    this.getLectorGradeList();
  },
  methods: {
    getLectorGradeList() {
      this.loading = true;
      listLectorGrade().then((res) => {
        this.gradeList = res.data;
      });
    },
    clearOption() {
      this.queryParams.categoryId = "";
      this.queryParams.pageNum = 1;
      this.getList();
    },
    optionData(array, result = []) {
      array.forEach((item) => {
        result.push({ label: item.label, value: item.id });
        if (item.children && item.children.length !== 0) {
          this.optionData(item.children, result);
        }
      });
      return JSON.parse(JSON.stringify(result));
    },
    handleNodeClick(node) {
      this.categoryTitle = node.label;
      this.queryParams.categoryId = node.id;
      this.$refs.selectTree.blur();
      this.queryParams.pageNum = 1;
      // console.log(this.queryParams);
      this.getList();
    },
    // 新建讲师
    addLectorfun() {
      this.dialogTitle = '添加讲师'
      this.editLectorUuid = ''
      this.lectorVisible = true;
    },
    closeAdd(save) {
      this.lectorVisible = false;
      // console.log(save)
      if (save) {
        this.queryParams.pageNum = 1;
        this.getList();
      }
    },
    // 修改课程状态
    changeCourseStatus(tab, event) {
      if (this.courseStatus == "published") {
        this.queryParams.status = 1;
      } else if (this.courseStatus == "close") {
        this.queryParams.status = 2;
      }
      this.queryParams.pageNum = 1;
      this.getList();
    },
    publishLector(uuid, index) {
      let _self = this;
      // 防止二次点击
      if (this.isClick) {
        return;
      }
      this.isClick = true;
      publishLector(uuid).then(function (res) {
        if (res.code == 200) {
          _self.isClick = false;
          _self.courseList.splice(index, 1);
          _self.total -= 1;
          _self.$message({
            showClose: true,
            message: res.msg,
            type: "success",
            onClose: function () {},
          });
        } else {
          _self.isClick = false;
          _self.$message({
            type: "info",
            message: res.msg,
          });
        }
      });
    },
    closeLector(uuid, index) {
      let _self = this;
      closeLector(uuid).then(function (res) {
        if (res.code == 200) {
          _self.courseList.splice(index, 1);
          _self.total -= 1;
          _self.$message({
            showClose: true,
            message: res.msg,
            type: "success",
            onClose: function () {},
          });
        } else {
          this.$message({
            type: "info",
            message: res.msg,
          });
        }
      });
    },
    // 删除课程
    deleteLector(uuid, index) {
      let _self = this;
      _self
        .$confirm(
          "该课程删除后将无法恢复, 请谨慎操作避免无法找回。",
          "确定删除该课程",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
            center: true,
          }
        )
        .then((action) => {
          if (action == "confirm") {
            delLector(uuid).then(function (res) {
              if (res.code == 200) {
                _self.courseList.splice(index, 1);
                _self.total -= 1;
                _self.$message({
                  showClose: true,
                  message: res.msg,
                  type: "success",
                  onClose: function () { },
                });
              }
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 确认搜索
    confirmSearch() {
      this.queryParams.searchValue = this.searchValue;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 查询分类列表 */
    getCategoryList() {
      let _self = this;
      listJavaCategory().then((res) => {
        _self.categoryList = res.data;
      });
    },

    /** 讲师列表 */
    getList() {
      this.loading = true;
      listLector(this.queryParams).then((response) => {
        this.courseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) { },

    // 编辑讲师
    editLector(row) {
      this.dialogTitle = '编辑讲师'
      this.editLectorUuid = row.lectorId
      this.lectorVisible = true;

    },
    tongjiCourse(row) {
      this.$router.push({
        path: "/course/course/studentStatistic",
        query: { uuid: row.uuid },
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.teacher-container {
  .filter-box {
    width: 100%;
    padding: 10px 0;
    box-sizing: border-box;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .filter-left {
      display: flex;
      justify-content: flex-start;
      align-items: center;

      .search {
        margin: 0 20px;
        height: 36px;
        border-radius: 4px;
        overflow: hidden;
        display: flex;
        justify-content: flex-start;
        align-items: center;

        .btn {
          height: 100%;
          padding: 0 10px;
          background: #5881db;
          color: #fff;
          line-height: 36px;
          font-size: 14px;
          box-sizing: border-box;
          white-space: nowrap;
          cursor: pointer;
        }
      }
    }
  }

  .teacher-list {
    width: 100%;
    padding: 16px 0;
    box-sizing: border-box;


  }
}
</style>
