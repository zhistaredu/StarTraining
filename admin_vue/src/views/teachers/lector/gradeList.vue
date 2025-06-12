<template>
  <div class="app-container lector-container">
    <div class="filter-box">
      <div class="filter-left">
        <div class="block">
         <!--  <el-select v-model="lectorType" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select> -->
        </div>
      </div>
      <div class="filter-right">
        <el-button type="primary" @click="addFun">新建</el-button>
      </div>
    </div>

    <div class="lector-list">
      <el-table
        v-loading="loading"
        :data="lectorList"
        :border="false"
        style="width: 100%"
        :header-cell-style="{ background: '#e8eef8', color: '#000' }"
      >
        <el-table-column fixed prop="title" label="讲师等级">
        </el-table-column>
        <el-table-column prop="type" label="讲师类型">
          <template slot-scope="scope">
            <span v-if="scope.row.type == 'interior'">内部讲师</span>
            <span v-else-if="scope.row.type == 'external'">外部讲师</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="editGrade(scope.row)"
              >编辑</el-button
            >
            <el-button
              @click="deleteCourse(scope.row.uuid, scope.$index)"
              type="text"
              size="small"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="添加等级" :visible.sync="gradeVisible" width="30%">
      <add-grade :gradeId="gradeId" @cancel="cancel" @ok="submit"></add-grade>
    </el-dialog>
  </div>
</template>

<script>
import {
  listLectorGrade,
  delLectorGrade,
} from "@/api/system/lectorGrade";
import addGrade from "@/views/teachers/lector/addGrade";
import { listJavaCategory } from "@/api/learn/category";

export default {
  name: "Course",
  dicts: ["sys_user_sex"],
  components: {
    addGrade
  },
  data() {
    return {
      courseStatus: "published", // 课程状态
      categoryList: [], // 分类列表
      searchValue: "", // 搜索内容
      status:
        this.$route.query.status == undefined ? 1 : this.$route.query.status,
      // 遮罩层
      loading: true,
      gradeVisible: false,
      // 总条数
      total: 0,
      gradeId: "",
      // 课程管理表格数据
      lectorList: [],
      options: [{
          value: 'all',
          label: '全部'
        }, {
          value: 'interior',
          label: '内部讲师'
        }, {
          value: 'external',
          label: '外部讲师'
        }],
      lectorType: 'all',
      // 查询参数
      queryParams: {
        status: 1, // 1 已发布  0 未发布  2 已关闭
        categoryId: "", // 分类
        keyword: "", // 搜索
      },
      categoryTitle: "",
      isClick: false, // 防止发布按钮二次点击
    };
  },
  created() {
    if (this.status == 0) {
      this.courseStatus = "unpublished";
    } else if (this.status == 1) {
      this.courseStatus = "published";
    } else if (this.status == 2) {
      this.courseStatus = "close";
    }
    this.queryParams.status = this.status;
    this.getList();
    this.getCategoryList();
  },
  methods: {
    addFun() {
      this.gradeVisible = true;
    },
    submit() {
      this.gradeVisible = false;
      this.getList();
    },
    cancel() {
      this.gradeVisible = false;
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
    newProcess() {
      this.$router.push({
        path: "/course/course/design",
      });
    },
    // 发布课程
    publishCourse(uuid, index) {
      let _self = this;
      // 防止二次点击
      if (this.isClick) {
        return;
      }
      this.isClick = true;
      publishCourse(uuid).then(function (res) {
        if (res.code == 200) {
          _self.isClick = false;
          _self.lectorList.splice(index, 1);
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
    // 关闭课程
    closeCourse(uuid, index) {
      let _self = this;
      closeCourse(uuid).then(function (res) {
        if (res.code == 200) {
          _self.lectorList.splice(index, 1);
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
    deleteCourse(uuid, index) {
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
            delLectorGrade(uuid).then(function (res) {
              if (res.code == 200) {
                _self.lectorList.splice(index, 1);
                _self.total -= 1;
                _self.$message({
                  showClose: true,
                  message: res.msg,
                  type: "success",
                  onClose: function () {},
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
      this.queryParams.keyword = this.searchValue;
      this.getList();
    },
    /** 查询分类列表 */
    getCategoryList() {
      let _self = this;
      listJavaCategory().then((res) => {
        _self.categoryList = res.data;
      });
    },

    /** 查询课程管理列表 */
    getList() {
      this.loading = true;
      listLectorGrade(this.queryParams).then((res) => {
        this.lectorList = res.data;
        this.loading = false;
      });
    },
    // 创建课程
    createCourse() {
      this.$router.push({
        path: "/train/course/course/create",
      });
    },
    editGrade(row) {
      this.gradeId = row.uuid;
      this.gradeVisible = true;
    },
    // 指派课程
    zhipaiCourse(row) {
      this.$router.push({
        path: "/course/course/design",
        query: { uuid: row.uuid, courseStatus: this.courseStatus, activeSelect: 'orgSetting' },
      });
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
.lector-container {
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

  .lector-list {
    width: 100%;
    padding: 16px 0;
    box-sizing: border-box;


  }
}
</style>
