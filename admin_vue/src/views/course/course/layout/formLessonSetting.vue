<template>
  <div class="lesson-list-box">
    <div class="addstagecont">
      <div class="item" @click="toChoose('sucai')">
        <i class="ware-img iconfont icon-a-wenjiankugengxin" style="font-size: 38px; color: rgb(27, 144, 255);"></i>
        <!-- <img class="ware-img" src="@static/image/addCourseware.png" alt="" /> -->
        <p>选择已有课件</p>
      </div>

      <div class="item" @click="toChoose('video')">
        <i class="video-img iconfont icon-tianjiashipinwenjianjia" style="font-size: 38px; color: rgb(221, 49, 91);"></i>
        <!-- <img class="video-img" src="@static/image/addVideo.png" alt="" /> -->
        <p>添加视频</p>
      </div>
      <div class="item" @click="toChoose('document')">
        <i class="document-img iconfont icon-addNew" style="font-size: 38px; color: rgb(255, 77, 79);"></i>
        <!-- <img class="document-img" src="@static/image/addDocument.png" alt="" /> -->
        <p>添加文档</p>
      </div>

    </div>
    <div class="stage-item-list">
      <ul v-if="tableData.length > 0">
        <li class="top">
          <div class="type">类型</div>
          <div class="title">标题</div>
          <div class="length">时长</div>
          <div class="btn-list">操作</div>
        </li>
        <draggable :forceFallback="true" :list="tableData" handle=".dargBtn" :animation="1000" filter=".undraggable"
          fallbackClass="fallbackStyle" ghostClass="item_ghost" chosenClass="chosenStyle" dragClass="dragStyle">
          <li v-for="(item, index2) in tableData" :key="item.uuid" class="my-handle">
            <div class="type">
              <i class="iconfont icon-shipin1" style="font-size: 16px; color: rgb(255, 77, 79);"></i>
              {{
                item.type == "document"
                ? "文档"
                : "视频"
              }}
            </div>
            <div class="title">{{ item.title }}</div>
            <div class="length">{{ item.length | timeFormat }}</div>
            <div class="btn-list">
              <div class="delete" @click="editLesson(item.uuid, item.type)">
                <i class="iconfont icon-bianji"></i>
                <span>编辑</span>
              </div>
              <div class="delete" @click="deleteCourseLesson(item.uuid, index)">
                <i class="iconfont icon-shanchu"></i>
                <span>删除</span>
              </div>
              <div class="dargBtn" title="拖拽排序">
                <i class="iconfont icon-move"></i>
                <span>拖拽</span>
              </div>
            </div>
          </li>
        </draggable>
      </ul>
    </div>
    <div class="btn-box" v-if="tableData.length > 0">
      <el-button type="primary" @click="saveLesson" :loading="isLoading">保存</el-button>
    </div>

    <el-drawer :title="addLessonName" :visible.sync="drawer" size="900px">
      <div>
        <video-lesson-setting :coursewareId="coursewareId" v-if="drawer && coursewareType == 'video'"  @ok="submitFile"/>
        <document-lesson-setting :coursewareId="coursewareId"
          v-else-if="drawer && coursewareType == 'document'" @ok="submitFile"/>
        <choiceCourseware :isedit="isedit" :coursewareId="coursewareId"
          :choicedCourseList="tableData" v-else-if="drawer && coursewareType == 'sucai'" @ok="submitsucai" @close="drawer=false"/>
      </div>
    </el-drawer>
    <el-drawer :title="addLessonName" :visible.sync="drawerUpdate" size="900px">
      <div>
        <update-lesson-setting :coursewareId="coursewareId" v-if="drawerUpdate"  @ok="submitUpdate"/>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  saveBatchLesson,
  deleteCourseLessons,
  courseLessonList,
  courseLessonListSort,
  gotoConvert,
} from "@/api/learn/course";
import videoLessonSetting from "./videoLessonSetting";
import updateLessonSetting from "./updateLessonSetting";
import documentLessonSetting from "./documentLessonSetting";
import choiceCourseware from "./choiceCourseware";
import sortable from "sortablejs";
import draggable from "vuedraggable";
export default {
  components: {
    videoLessonSetting,
    updateLessonSetting,
    documentLessonSetting,
    choiceCourseware,
    draggable
  },
  props: ["uuid", "courseStatus"],
  data() {
    return {
      drawer: false,
      drawerUpdate: false,
      activeName: "video",
      coursewareId: "",
      coursewareType: "",
      addLessonName: "",
      tableData: [],
      showDialog: false,
      isSort: false,
      sortList: [],
      btnTitle: "排列顺序",
      sortObj: {},
      isedit: false, // 是否哦是编辑
      isLoading: false,
    };
  },
  computed: {
    courseInfo() {
      return this.$store.state.course.courseInfo;
    },
  },
  mounted() { },
  created() {
    this.getAllList();
  },

  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    // 获取课件列表
    getAllList() {
      let that = this;
      let data = {
        courseId: this.courseInfo.uuid,
      };
      this.tableData = [];
      courseLessonList(data).then((res) => {
        if (res.code == 200) {
          res.data.forEach(function (items) {
            let value = {
              uuid : items.uuid,
              title : items.title,
              length : items.length,
              type : items.type,
            }
            that.tableData.push(value);
          })
        }
      });
    },


    toChoose(value) {
      switch (value) {
        case "video":
          this.addLessonName = "添加视频";
          break;
        case "document":
          this.addLessonName = "添加文档";
          break;
        case "sucai":
          this.addLessonName = "选择课件";
          break;
      }
      this.isedit = false;
      this.coursewareType = value;
      this.coursewareId = "";
      this.drawer = true;


    },
    editLesson(uuid, type) {
      this.isedit = true;
      this.coursewareId = uuid;
      switch (type) {
        case "video":
          this.addLessonName = "编辑视频";
          break;
        case "document":
          this.addLessonName = "编辑文档";
          break;
      }
      this.drawerUpdate = true;
      this.coursewareType = type;
    },
    // 删除课件
    deleteCourseLesson(uuid, index) {
      let _self = this;
      _self.tableData.splice(index, 1)
    },
    saveLesson() {
      let _self = this;
      let formData = {
        courseId: this.courseInfo.uuid,
        lessonValue: JSON.stringify(this.tableData),
      }
      _self.isLoading = true;
      saveBatchLesson(formData).then(function (res) {
          if (res.code == 200) {
              _self.isLoading = false;
              _self.$message({
                  showClose: true,
                  message: res.msg,
                  type: 'success',
              });
          }
      })
    },
    submitFile(value) {
      this.drawer = false;
      this.tableData.push(value);
      this.saveLesson()
    },
    submitUpdate(value) {
      this.drawerUpdate = false;
      this.tableData.map(function (items) {
        if (items.uuid == value.uuid) {
          items.title = value.title;
          items.length = value.length;
        }
      })
    },
    submitsucai(value) {
      this.drawer = false;
      this.tableData = value;
      this.saveLesson()
    },
  },
};
</script>

<style lang="scss">
.lesson-list-box {
  overflow: auto;
  margin: 0 auto;
  width: 900px;
  height: calc(100vh - 155px);
  background: #ffffff;
  margin-top: 10px;
  padding: 15px 20px;

  .addstagecont {
    width: 100%;
    display: flex;
    align-items: center;
    margin-bottom: 20px;

    .item {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      margin: 0 15px;

      &:hover {
        img {
          transform: translateY(-6px);
          -webkit-transform: translateY(-6px);
        }
      }

      img {
        width: 40px;
        height: 40px;
        object-fit: contain;
        transition: all 0.3s;
        -webkit-transition: all 0.3s;

        &.ware-img {
          width: 40px;
        }

        &.video-img {
          width: 36px;
        }

        &.document-img {
          width: 36px;
        }
      }

      p {
        margin: 0;
        padding: 0;
        font-size: 14px;
        color: #333;
        font-weight: bold;
      }
    }
  }

  .btn-box {
    width: 100%;
    padding-top: 30px;
    display: flex;
    align-items: center;
    justify-content: center;

    .btn {
      width: 140px;
      height: 36px;
      background: #5881db;
      text-align: center;
      line-height: 36px;
      color: #fff;
      border-radius: 4px;
      font-size: 14px;
      cursor: pointer;
    }
  }

  .stage-item-list {
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    // min-height: 200px;
    ul {
      width: 100%;
      margin: 0;
      padding: 0;
      box-sizing: border-box;

      li.top {
        background: #ccc;
        border-bottom: none;
      }

      li {
        width: 100%;
        display: flex;
        align-items: center;
        height: 50px;
        border-bottom: 1px solid #eee;
        box-sizing: border-box;
        font-size: 14px;
        color: #333;

        &:last-child {
          border-bottom: none;
        }

        div {
          text-align: center;
        }

        div.type {
          width: 10%;

        }

        div.title {
          width: 50%;
          margin: 0 !important;
        }

        div.length {
          width: 15%;
        }

        div.status {
          width: 15%;
        }

        div.btn-list {
          width: 25%;
          padding-right: 10px;
          box-sizing: border-box;
          display: flex;
          align-items: center;
          justify-content: space-around;

          .delete {
            display: flex;
            align-items: center;
            cursor: pointer;
            color: #999;
            margin-right: 10px;

            &:hover {
              color: #5881db;
            }

            span {
              font-size: 14px;
            }

            .iconfont {
              font-size: 14px;
              margin-right: 3px;
            }
          }

          .dargBtn {
            display: flex;
            align-items: center;

            cursor: move;
            color: #5881db;

            &:hover {
              color: #5881db;
            }

            span {
              font-size: 14px;
            }

            .iconfont {
              font-size: 14px;
              margin-right: 3px;
            }
          }
        }
      }
    }

    .nodata {
      width: 100%;
      height: 200px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      .iconfont {
        font-size: 60px;
        color: #999;
      }

      p {
        color: #999;
        font-size: 14px;
      }
    }
  }
}



</style>
