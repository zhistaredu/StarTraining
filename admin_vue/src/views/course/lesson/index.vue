<template>
  <div
    class="app-container lesson_list_box"
    :style="{ height: windowHeight + 'px' }"
  >
    <!-- 左侧分类 -->
    <div class="left-category">
      <div class="tit-name">资源分类</div>

      <div class="category-list">
        <el-tree
          ref="treecate"
          :data="categoryList"
          :props="defaultProps"
          :default-expand-all="true"
          :highlight-current="true"
          :expand-on-click-node="false"
          node-key="id"
          :current-node-key="categoryId"
          @node-click="changeNowCategory"
        ></el-tree>
      </div>
    </div>
    <!-- 右侧课件 -->
    <div class="right-data">
      <el-card class="box-card">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-dropdown @command="addFile">
              <el-button type="primary">
                上传课件<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="video">上传视频</el-dropdown-item>
                <el-dropdown-item command="document">上传文档</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-button
              type="primary"
              plain
              icon="el-icon-rank"
              v-if="selectIds.length > 0"
              @click="dialogFormVisible = true"
              style="margin-left: 20px"
              >批量移动</el-button
            >
          </el-col>
        </el-row>
        <el-table
          v-loading="loading"
          :data="courseList"
          :border="false"
          ref="multipleTable"
          tooltip-effect="dark"
          style="width: 100%"
          :header-cell-style="{ background: '#e8eef8', color: '#000' }"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column fixed prop="title" label="课件名称" min-width="300">
            <template slot-scope="scope">
              <div v-if="scope.row.type == 'document'" style="display: flex;align-items: center;">
                <i v-if="scope.row.fileType == 'txt'" 
                  class="iconfont icon-txt" style="font-size: 16px;"></i>
                <i v-else-if="scope.row.fileType == 'xls' || scope.row.fileType == 'xlsx'" 
                  class="iconfont icon-xlsx" style="font-size: 16px;"></i>
                <i v-else-if="scope.row.fileType == 'wps'" 
                  class="iconfont icon-meeting-icon-WPS" style="font-size: 16px; color: rgb(27, 144, 255);"></i>
                <i v-else-if="scope.row.fileType == 'ppt' || scope.row.fileType == 'pptx'" 
                  class="iconfont icon-ppt" style="font-size: 16px; color: rgb(249, 127, 17);"></i>
                <i v-else-if="scope.row.fileType == 'pdf'" 
                  class="iconfont icon-PDF" style="font-size: 16px; color: rgb(221, 49, 91);"></i>
                <i v-else class="iconfont icon-icon-doc" style="font-size: 16px; color: rgb(27, 144, 255);"></i>
                <span style="margin-left: 5px;">{{ scope.row.title }}</span>
              </div>
              <div v-else style="display: flex;align-items: center;">
                <i class="iconfont icon-shipin1" style="font-size: 16px; color: rgb(255, 77, 79);"></i>
                <span style="margin-left: 5px;">{{ scope.row.title }}</span>
              </div>

            </template>
          </el-table-column>
          <el-table-column prop="categoryTitle" label="分类" width="120">
            <template slot-scope="scope">
              <span v-if="scope.row.categoryId == 'empty'">未归类</span>
              <span v-else>{{ scope.row.categoryTitle }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="filesize" label="时长" width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.length | timeFormat }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" width="160">
            <template slot-scope="scope">
              <span>{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <el-button @click="editLesson(scope.row)" type="text" size="small"
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
      </el-card>

      <div class="bottom-block">
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
    <!-- 修改课件分类弹窗 -->
    <el-dialog
      title="修改课件分类"
      :visible.sync="dialogFormVisible"
      width="400px"
      center
    >
      <div>
        <div class="block">
          <el-select
            class="main-select-tree"
            ref="selectCateTree"
            clearable
            v-model="changeCategoryTit"
            @clear="clearOption"
            style="width: 300px"
          >
            <el-option
              v-for="(item, index) in optionData(categoryList)"
              :key="index"
              :label="item.label"
              :value="item.value"
              style="display: none"
            />
            <el-tree
              class="main-select-el-tree"
              ref="selectelTree"
              :data="categoryList"
              :props="treeProps"
              highlight-current
              @node-click="choicedCategory"
              :expand-on-click-node="expandOnClickNode"
              default-expand-all
            >
              <span class="custom-tree-node" slot-scope="{ node, data }">
                <span>{{ data.label }}</span>
              </span>
            </el-tree>
          </el-select>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelChange">取 消</el-button>
        <el-button type="primary" @click="confirmChange">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 添加文件弹窗 start-->
    <el-dialog
      :title="addFileTitle"
      :visible.sync="addFileVisible"
      :close-on-click-modal="false"
      width="800px"
      :before-close="handleClose">
      <video-lesson-setting
        :categoryId="categoryId"
        :categoryTitle="categoryTitle"
        v-if="addFileVisible && fileType == 'video'"
        @changeCate="changeCate"
        @ok="submit"
      >
      </video-lesson-setting>
      <document-lesson-setting
        :categoryId="categoryId"
        :categoryTitle="categoryTitle"
        v-if="addFileVisible && fileType == 'document'"
        @changeCate="changeCate"
        @ok="submit"
      >
      </document-lesson-setting>
    </el-dialog>
  </div>
</template>

<script>
import { listLesson, delLesson, addMove } from "@/api/learn/lesson";
import { listJavaCategory } from "@/api/learn/category";
import videoLessonSetting from "./videoLessonSetting";
import documentLessonSetting from "./documentLessonSetting";

export default {
  name: "lessonlist",
  components: {
    videoLessonSetting,
    documentLessonSetting,
  },
  data() {
    return {
      windowHeight: 0,
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
      selectIds: [], // 选中的课件列表
      categoryId: this.$route.query.categoryId == undefined ? "all" : this.$route.query.categoryId, // 当前分类id
      categoryTitle: "", // 当前分类名称
      fileType: "", // 课件类型
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: this.$route.query.categoryId == undefined ? "all" : this.$route.query.categoryId, // 当前分类id
        keyword: "", // 搜索
      },
      expandOnClickNode: true,
      dialogFormVisible: false, // 移动弹出显示判断
      rangeVisible: false, // 使用范围弹窗开关控制
      addFileVisible: false, // 添加课件弹窗显示隐藏
      addFileTitle: "",
      treeProps: {
        children: "children",
        label: "label",
      },
      isClick: false, // 防止发布按钮二次点击
      defaultProps: {
        children: "children",
        label: "label",
      },
      changeCategoryTit: "", // 修改后的分类
      changeCategoryId: "", // 修改后的分类
    };
  },
  mounted() {
    this.windowHeight = window.innerHeight - 80;
    this.queryParams.categoryId = this.categoryId;
    this.getList();
    this.getCategoryList();
  },
  methods: {
    clearOption() {
      this.changeCategoryId = "";
      this.changeCategoryTit = "";
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
    // 选中分类
    choicedCategory(node) {
      this.changeCategoryTit = node.label;
      this.changeCategoryId = node.id;
      this.$refs.selectCateTree.blur();
    },
    // 取消修改分类
    cancelChange() {
      this.dialogFormVisible = false;
      this.changeCategoryId = "";
      this.changeCategoryTit = "";
    },
    // 确定修改分类
    confirmChange() {
      addMove({
        selectIds: JSON.stringify(this.selectIds),
        categoryId: this.changeCategoryId,
      }).then((res) => {
        if (res.code == 200) {
          this.dialogFormVisible = false;
          this.changeCategoryId = "";
          this.changeCategoryTit = "";
          this.$message({
            type: "success",
            message: "修改成功",
          });
          this.queryParams.pageNum = 1;
          this.getList();
        }
      });
    },
    // 编辑课件
    editLesson(row) {
      this.$router.push({
        path: "/course/lesson/design",
        query: { uuid: row.uuid, courseStatus: this.courseStatus },
      });
    },
    // 添加课件
    addFile(type) {
      this.addFileVisible = true;
      if (type == "video") {
        this.addFileTitle = "添加视频";
        this.fileType = "video";
      } else if (type == "document") {
        this.addFileTitle = "添加文档";
        this.fileType = "document";
      }
    },
    handleClose() {
      this.addFileVisible = false;
      this.queryParams.categoryId = this.categoryId;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 修改当前分类
    changeCate(data) {
      // console.log("改变", this.categoryId);
      this.$refs.treecate.setCurrentKey(data.id, true, false);
      this.categoryId = data.id;
      this.categoryTitle = data.label;
      this.queryParams.categoryId = data.id;
      this.queryParams.pageNum = 1;
      this.getList();
      this.$forceUpdate();
    },
    submit(value) {
      this.addFileVisible = false;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 切换当前分类
    changeNowCategory(node) {
      this.categoryTitle = node.label;
      this.categoryId = node.id;

      this.queryParams.categoryId = node.id;
      this.queryParams.pageNum = 1;
      this.getList();
    },

    // 删除课件
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
            delLesson(uuid).then(function (res) {
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

    /** 查询分类列表 */
    getCategoryList() {
      let _self = this;
      listJavaCategory().then((res) => {
        _self.categoryList = res.data;
        _self.categoryList.unshift({
          label: "全部",
          id: "all",
        });
        if(_self.categoryId == undefined){
          setTimeout(() => {
            _self.$refs.treecate.setCurrentKey("all", true, false);
          }, 500);
        }
      });
    },

    // 课件列表
    getList() {
      if (this.queryParams.categoryId == "") {
        this.loading = false;
        return;
      }
      this.loading = true;
      listLesson(this.queryParams).then((response) => {
        this.courseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 多选框选中课件
    handleSelectionChange(selection) {
      let that = this;
      this.selectIds = [];
      selection.map(function (items) {
        that.selectIds.push(items.uuid);
      });
    },
  },
};
</script>

<style lang="less">
.lesson_list_box {
  width: 100%;
  position: relative;
  padding-right: 15px;
  padding-left: 260px;
  box-sizing: border-box;
  padding-top: 15px;
  box-sizing: border-box;

  .left-category {
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

    .category-list {
      padding: 10px 10px 0;
      box-sizing: border-box;

      .el-tree-node__label {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

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
    overflow: hidden;
    background: #fff;
    padding-bottom: 60px;
    box-sizing: border-box;
    position: relative;
    .bottom-block {
      height: 60px;
      position: absolute;
      bottom: 0;
      left: 0;
      width: 100%;
    }
    .box-card {
      box-shadow: none;
      width: 100%;
      height: 100%;
      overflow-y: auto;
      position: relative;
      box-sizing: border-box;
    }
  }
}
</style>
