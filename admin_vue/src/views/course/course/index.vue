<template>
  <div class="app-container course-container">
    <el-tabs v-model="courseStatus" @tab-click="changeCourseStatus">
      <el-tab-pane label="未发布" name="unpublished"></el-tab-pane>
      <el-tab-pane label="已发布" name="published"></el-tab-pane>
      <el-tab-pane label="已关闭" name="close"></el-tab-pane>
    </el-tabs>

    <div class="filter-box">
      <div class="filter-left">
        <div class="block">
          <el-select
            class="main-select-tree"
            ref="selectTree"
            clearable
            v-model="categoryTitle"
            @clear="clearOption"
            style="width: 300px"
          >
            <el-option
              v-for="(item, index) in optionData(categoryList)"
              :key="index"
              :label="item.label"
              :value="item.value"
              style="display: none"/>
              <el-tree
                class="main-select-el-tree"
                ref="selectelTree"
                :data="categoryList"
                :props="treeProps"
                highlight-current
                @node-click="handleNodeClick"
                :expand-on-click-node="expandOnClickNode"
                default-expand-all>
                <span class="custom-tree-node" slot-scope="{ node, data }">
                  <span>{{ data.label }}</span>
                </span>
              </el-tree>
          </el-select>
        </div>

        <div class="search">
          <el-input
            v-model="searchValue"
            placeholder="请输入搜索内容"
          ></el-input>
          <div class="btn" @click="confirmSearch">搜索</div>
        </div>
      </div>
      <div class="filter-right">
        <el-button type="primary" @click="newProcess">新建</el-button>
      </div>
    </div>

    <div class="course-list">
      <el-table
        v-loading="loading"
        :data="courseList"
        :border="false"
        style="width: 100%"
        :header-cell-style="{ background: '#e8eef8', color: '#000' }"
        @selection-change="handleSelectionChange"
      >
        <el-table-column fixed prop="title" label="课程标题" min-width="300">
        </el-table-column>
        <el-table-column prop="coursewareNum" label="课件节数" width="120">
        </el-table-column>
        <el-table-column prop="duration" label="课程时长" width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.duration | timeFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="learnNum" label="学习人数" width="120">
        </el-table-column>
        <el-table-column prop="finishNum" label="完成人数" width="120">
        </el-table-column>
        <el-table-column prop="categoryTitle" label="分类" width="220">
        </el-table-column>
        <el-table-column label="创建时间" width="160">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="editCourse(scope.row)"
              >编辑</el-button
            >
            <el-button
              @click="zhipaiCourse(scope.row)"
              type="text"
              size="small"
              v-if="courseStatus == 'published'"
              >可见范围</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="tongjiCourse(scope.row)"
              v-if="courseStatus != 'unpublished'"
              >统计</el-button
            >
            <el-button
              @click="publishCourse(scope.row.uuid, scope.$index)"
              type="text"
              size="small"
              v-if="courseStatus != 'published'"
              >发布</el-button
            >

            <el-button
              @click="closeCourse(scope.row.uuid, scope.$index)"
              type="text"
              size="small"
              v-if="courseStatus == 'published'"
              >关闭</el-button
            >

            <el-button
              @click="deleteCourse(scope.row.uuid, scope.$index)"
              type="text"
              size="small"
              v-if="courseStatus != 'published'"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  listCourse,
  delCourse,
  publishCourse,
  closeCourse,
  addCourse,
  updateCourse,
} from "@/api/learn/course";
import { listJavaCategory } from "@/api/learn/category";

export default {
  name: "Course",
  components: {},
  data() {
    return {
      courseStatus: "published", // 课程状态
      categoryList: [], // 分类列表
      searchValue: "", // 搜索内容
      status:
        this.$route.query.status == undefined ? 1 : this.$route.query.status,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 课程管理表格数据
      courseList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status: 1, // 1 已发布  0 未发布  2 已关闭
        categoryId: "", // 分类
        searchValue: "", // 搜索
      },
      expandOnClickNode: true,
      categoryTitle: "",
      treeProps: {
        children: "children",
        label: "label",
      },

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
    newProcess() {
      this.$router.push({
        path: "/course/course/design",
      });
    },
    // 修改课程状态
    changeCourseStatus(tab, event) {
      // console.log(tab, event);
      // console.log(this.courseStatus);
      if (this.courseStatus == "unpublished") {
        this.queryParams.status = 0;
      } else if (this.courseStatus == "published") {
        this.queryParams.status = 1;
      } else if (this.courseStatus == "close") {
        this.queryParams.status = 2;
      }
      this.queryParams.pageNum = 1;
      this.getList();
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
    // 关闭课程
    closeCourse(uuid, index) {
      let _self = this;
      closeCourse(uuid).then(function (res) {
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
            delCourse(uuid).then(function (res) {
              if (res.code == 200) {
                _self.courseList.splice(index, 1);
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

    /** 查询课程管理列表 */
    getList() {
      this.loading = true;
      listCourse(this.queryParams).then((response) => {
        let arr = response.rows;
        this.courseList = arr.map((item) => {
          item.learnPeople = item.electiveNum + item.requiredNum;
          return item;
        });
        this.total = response.total;
        this.loading = false;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {},

    // 编辑课程
    editCourse(row) {
      this.$router.push({
        path: "/course/course/design",
        query: { uuid: row.uuid, courseStatus: this.courseStatus },
      });
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
        query: { uuid: row.uuid, title: row.title },
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.course-container {
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

  .course-list {
    width: 100%;
    padding: 16px 0;
    box-sizing: border-box;

  }
}
</style>
