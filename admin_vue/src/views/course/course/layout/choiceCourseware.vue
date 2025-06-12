<template>
  <div id="know_courseware_choice">

    <div class="right-cont">
      <div class="filter-box">
        <div class="block" v-if="categoryList.length">
          <el-select class="main-select-tree" ref="selectExamTree" clearable v-model="categoryTitle" placeholder="请选择分类"
            @clear="clearOption" style="width: 300px">
            <el-option v-for="(item, index) in optionData(categoryList)" :key="index" :label="item.label"
              :value="item.value" style="display: none" />
            <el-tree class="main-select-el-tree" ref="selectelExamTree" :data="categoryList" :props="treeProps"
              highlight-current @node-click="handleNodeClick" :expand-on-click-node="expandOnClickNode"
              default-expand-all />
          </el-select>
        </div>
        <div class="search">
          <el-input v-model="searchValue" placeholder="请输入搜索内容"></el-input>
          <div class="btn" @click="confirmSearch">搜索</div>
        </div>
      </div>

      <div class="course-list" ref="corse_list">
        <vue-data-loading :offset="-3" :loading="loading" :completed="completed" :listens="['infinite-scroll']"
          :init-scroll="true" container="corse_list" @infinite-scroll="infiniteScroll">
          <div slot="pull-down-ready">ready to refresh</div>
          <ul id="courseware_list">
            <li>
              <div class="course-item-top">
                <div class="type_name">类型</div>
                <div class="title">标题</div>
                <div class="length">时长</div>
                <div class="btn">操作</div>
              </div>
            </li>
            <li v-for="(item, index) in courseList" :key="index">
              <div class="course-item" @click="choiceCourse(item)">
                <div class="type">
                  <i v-if="item.type == 'video'" 
                    class="iconfont icon-shipin1" style="font-size: 16px; color: rgb(255, 77, 79);"></i>
                  <i v-else-if="item.fileType == 'txt'" 
                    class="iconfont icon-txt" style="font-size: 16px;"></i>
                  <i v-else-if="item.fileType == 'xls' || item.fileType == 'xlsx'" 
                    class="iconfont icon-xlsx" style="font-size: 16px;"></i>
                  <i v-else-if="item.fileType == 'wps'" 
                    class="iconfont icon-meeting-icon-WPS" style="font-size: 16px; color: rgb(27, 144, 255);"></i>
                  <i v-else-if="item.fileType == 'ppt' || item.fileType == 'pptx'" 
                    class="iconfont icon-ppt" style="font-size: 16px; color: rgb(249, 127, 17);"></i>
                  <i v-else-if="item.fileType == 'pdf'" 
                    class="iconfont icon-PDF" style="font-size: 16px; color: rgb(221, 49, 91);"></i>
                  <i v-else class="iconfont icon-icon-doc" style="font-size: 16px; color: rgb(27, 144, 255);"></i>
                </div>
                <div class="title">{{ item.title }}</div>
                <div class="length">时长：{{(item.length/60).toFixed(0)}}分{{item.length%60}}秒</div>
                <div class="btn">
                  <span v-if="choiceCourseUuid.indexOf(item.uuid) >= 0">
                    <i class="iconfont icon-duihao"></i>
                  </span>
                  <span v-else>选择</span>
                </div>
              </div>
            </li>
          </ul>
          <div slot="infinite-scroll-loading">加载中...</div>
          <div slot="completed">加载完毕!</div>
        </vue-data-loading>
      </div>

      <div class="btn-box">
        <div class="cancel btn-itm" @click="canccelChoice">取消</div>
        <div class="confirm btn-itm" @click="confirmChoice">确定</div>
      </div>
    </div>
  </div>
</template>

<script>
import { listLesson } from "@/api/learn/lesson";
import { listJavaCategory } from "@/api/learn/category";
import VueDataLoading from "vue-data-loading";

export default {
  props: ["choicedCourseList"],
  name: "choiceCourseware",
  components: { VueDataLoading },
  data() {
    return {
      courseStatus: "published", // 课程状态
      categoryList: [], // 分类列表
      defaultProps: {
        children: "children",
        label: "label",
      },
      searchValue: "", // 搜索内容
      status:
        this.$route.query.status == undefined ? 1 : this.$route.query.status,
      loading: false,
      completed: false,
      // 总条数
      total: 0,
      // 课程管理表格数据
      courseList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        status: 1, // 1 已发布  0 未发布  2 已关闭
        categoryId: "", // 分类
        keyword: "", // 搜索
      },
      expandOnClickNode: true,
      categoryTitle: "",
      treeProps: {
        children: "children",
        label: "label",
      },

      isClick: false, // 防止发布按钮二次点击
      choiceCourseList: [], // 选中的课程
      choiceCourseUuid: [], // 选中的课程ID
    };
  },
  created() {
    this.choicedCourseList.forEach((item) => {
      this.choiceCourseList.push(item);
      this.choiceCourseUuid.push(item.uuid);
    });
    this.queryParams.pageNum = 1;

    this.getCategoryList();
  },
  mounted() { },
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
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handleNodeClick(node) {

      this.$refs.selectExamTree.blur();

      this.courseList = [];
      this.categoryTitle = node.label;
      this.queryParams.categoryId = node.id;
      this.queryParams.pageNum = 1;
      // console.log(this.queryParams);
      this.getList();
    },

    // 确认搜索
    confirmSearch() {
      this.courseList = [];
      this.queryParams.keyword = this.searchValue;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 查询分类列表 */
    getCategoryList() {
      let _self = this;
      listJavaCategory().then((res) => {
        _self.categoryList = res.data;
        // console.log(_self.categoryList);
      });
    },

    infiniteScroll() {
      // console.log("下拉加载");
      this.getList();
      this.queryParams.pageNum++;
    },
    /** 查询课程管理列表 */
    getList() {
      this.loading = true;
      listLesson(this.queryParams).then((response) => {
        let arr = response.rows;
        // console.log(arr);
        this.courseList = this.courseList.concat(arr);
        // console.log(this.courseList);
        this.total = response.total;
        this.loading = false;
        if (this.queryParams.pageNum == 0 && this.courseList.length == 0) {
          this.noData = true;
        } else if (this.courseList.length == response.total) {
          this.completed = true;
          this.loading = false;
        }
        this.$forceUpdate();
      });
    },
    // 选择课程
    choiceCourse(data) {
      let items = data;

      if (this.choiceCourseUuid.indexOf(data.uuid) >= 0) {
        let index = this.choiceCourseUuid.indexOf(data.uuid);
        this.choiceCourseList.splice(index, 1);
        this.choiceCourseUuid.splice(index, 1);
      } else {
        this.choiceCourseList.push(items);
        this.choiceCourseUuid.push(data.uuid);
      }
    },
    // 确认选择课程
    confirmChoice() {
      let lessons = [];
      this.choiceCourseList.map(function(items) {
        lessons.push({
          uuid: items.uuid,
          title: items.title,
          type: items.type,
          length: items.length
        });
      })
      this.$emit("ok", lessons);
    },
    canccelChoice() {
      this.$emit("close");
    },
  },
};
</script>

<style lang="scss">
#know_courseware_choice {
  height: 600px;
  box-sizing: border-box;
  display: flex;
  align-items: flex-start;
  overflow: hidden;

  .left-dept {
    flex-shrink: 0;
    width: 200px;
    height: 600px;
    overflow-y: auto;

    .tit-name {
      margin-bottom: 10px;
      font-size: 16px;
      color: #666;
    }
  }

  .right-cont {
    flex: 1;
    height: 600px;
    overflow: hidden;
  }

  .filter-box {
    width: 100%;
    height: 60px;
    padding: 10px 0;
    box-sizing: border-box;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 20px;
    box-sizing: border-box;

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

  .btn-box {
    width: 100%;
    height: 70px;
    display: flex;
    align-items: center;
    justify-content: flex-end;

    .btn-itm {
      cursor: pointer;
      margin: 0 15px;
      width: 120px;
      border-radius: 8px;
      height: 40px;
      background: #5881db;
      text-align: center;
      line-height: 40px;
      color: #fff;

      &.cancel {
        border: 1px solid #5881db;
        background: #fff;
        box-sizing: border-box;
        line-height: 38px !important;
        color: #5881db;
      }
    }
  }

  .course-list {
    width: 100%;
    padding: 16px 0;
    box-sizing: border-box;
    height: 470px;
    overflow-y: auto;

    #courseware_list {
      width: 100%;
      margin-top: 0;
      margin-bottom: 0;
      padding: 0 20px;
      box-sizing: border-box;

      li {
        width: 100%;
        padding: 5px;
        box-sizing: border-box;
        .course-item-top{
          width: 100%;
          cursor: pointer;
          display: flex;
          align-items: center;
          justify-content: space-between;
          >.type_name{
            width: 50px;
            flex-shrink: 0;
            text-align: center;
            font-size: 14px;
            line-height: 30px !important;
            font-weight: bold;
          }
          >.title {
            flex: 1;
            text-align: left;
            font-size: 14px;
            line-height: 30px !important;
            margin: 0 !important;
            color: #333;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            font-weight: bold;
          }
          >.length{
            flex-shrink: 0;
            width: 120px;
            text-align: left;
            font-size: 14px;
            color: #333;
            font-weight: bold;
          }
          >.btn {
            width: 30px;
            text-align: center;
            flex-shrink: 0;
            cursor: pointer;
            font-size: 14px;
            color: #333;
            font-weight: bold;
          }
        }
        .course-item {
          width: 100%;
          cursor: pointer;
          display: flex;
          align-items: center;
          justify-content: space-between;

          >.type {
            width: 50px;
            flex-shrink: 0;
            text-align: center;

            i.iconfont {
              font-size: 28px;
            }
          }

          >.title {
            flex: 1;
            text-align: left;
            font-size: 14px;
            line-height: 30px !important;
            margin: 0 !important;
            color: #333;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
          }
          >.length{
            flex-shrink: 0;
            width: 120px;
            text-align: left;
            font-size: 14px;
            color: #333;
          }
          >.btn {
            width: 30px;
            text-align: center;
            flex-shrink: 0;
            cursor: pointer;
            font-size: 14px;
            color: #5881db;
          }
        }
      }
    }
  }
}
</style>
