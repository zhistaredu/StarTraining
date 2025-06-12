<template>
  <div id="course-base-setup">
    <el-form ref="form" :model="courseInfo" :rules="rules" label-width="150px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model.trim="courseInfo.title"
          :maxlength="titleLength"
        ></el-input>
      </el-form-item>
      <el-form-item label="分类" prop="categoryTitle">
        <el-select
          class="main-select-tree"
          ref="selectTree"
          v-model="courseInfo.categoryTitle"
          style="width: 300px"
        >
          <el-option
            v-for="(item, index) in optionData(fullTreeData)"
            :key="index"
            :label="item.label"
            :value="item.value"
            style="display: none"
          />
          <el-tree
            class="main-select-el-tree"
            ref="selectelTree"
            :data="fullTreeData"
            :props="treeProps"
            highlight-current
            @node-click="handleNodeClick"
            :expand-on-click-node="expandOnClickNode"
            default-expand-all
          />
        </el-select>
      </el-form-item>
      <el-form-item label="封面图" prop="cdnPicture">
        <div>
          <div
            v-if="
              this.courseInfo.cdnPicture != '' &&
              this.courseInfo.cdnPicture != undefined
            "
          >
            <img
              :src="this.courseInfo.cdnPicture"
              alt="image"
              style="max-width: 300px"
            />
          </div>
          <div class="upload-demo">
            <el-button size="small" type="primary" @click="selectImage"
              >选择封面图</el-button
            >
            <div slot="tip" class="el-upload__tip">
              建议尺寸：960*540px 或 16：9，请上传jpg/png/gif格式
            </div>
          </div>
        </div>
      </el-form-item>
      <el-form-item label="关联讲师">
        <lector-choice :lectorId="courseInfo.lectorId" :lectorName="courseInfo.lectorName" @choiceLector="choiceLector"></lector-choice>
      </el-form-item>
      <el-form-item label="学习模式" prop="learnMode">
        <el-radio v-model="courseInfo.learnMode" label="1"
          >自由式学习</el-radio
        >
        <el-radio v-model="courseInfo.learnMode" label="2"
          >解锁式学习</el-radio
        >
        <el-tooltip placement="top">
          <div slot="content">
            自由式学习：学员自由安排学习过程
            <br />
            解锁式学习：学员按顺序学习
          </div>
          <i class="iconfont icon-tishi2" style="font-size: 14px; color: #ccc;cursor: pointer;"></i>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="同步已学进度">
        <el-switch v-model="courseInfo.syncLearn" active-value="open" inactive-value="close" active-text="开启"
          inactive-text="不开启">
        </el-switch>
        <el-tooltip placement="top">
          <div slot="content">
            添加的课程/素材同步学员在其他途径的学习进度。 同步机制：播放时才同步进度
          </div>
          <i class="iconfont icon-tishi2" style="font-size: 14px; color: #ccc;cursor: pointer;margin-left: 30px;"></i>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="倍速播放" prop="speed">
        <el-switch
          v-model="courseInfo.speed"
          active-value="open"
          inactive-value="close"
          active-text="开启"
          inactive-text="不开启"
        >
        </el-switch>
        <el-tooltip placement="top">
          <div slot="content">
            开启倍速播放(开启后，学员可以切换1.25/1.5/2倍速播放，学习进度和课时仍按照原时长计算)
          </div>
          <i class="iconfont icon-tishi2" style="font-size: 14px; color: #ccc;cursor: pointer;margin-left: 30px;"></i>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="首次播放拖动" prop="drag">
        <el-switch
          v-model="courseInfo.drag"
          active-value="open"
          inactive-value="close"
          active-text="允许"
          inactive-text="不允许"
        >
        </el-switch>
      </el-form-item>
      <el-form-item label="奖励标准" prop="eligible">
        <el-radio v-model="courseInfo.eligible" label="all">完成课程</el-radio>
        <el-radio v-model="courseInfo.eligible" label="sector">完成指定进度</el-radio>
        <el-tooltip placement="top">
          <div slot="content">
            完成全部学习内容：课程内所有课件都要学习完成才能获得奖励
            <br />
            完成指定学习进度：达到指定学习进度才能获得奖励
          </div>
          <i class="iconfont icon-tishi2" style="font-size: 14px; color: #ccc;cursor: pointer;"></i>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="完成进度" v-if="courseInfo.eligible == 'sector'">
        <el-col :span="4">
          <el-input
            v-model.trim="courseInfo.learnProgress"
            type="number"
            min="0" max="100" :maxlength='3'
          ></el-input>
        </el-col>
        <el-col :span="4"> % </el-col>
      </el-form-item>
      <el-form-item label="学分" prop="credit">
        <el-col :span="4">
          <el-input
            v-model.trim="courseInfo.credit"
            type="number"
            min="0" max="999" :maxlength='5'
          ></el-input>
        </el-col>
        <el-col :span="4"> 分 </el-col>
      </el-form-item>
      <el-form-item>
        <div class="btn-box">
          <el-button type="primary" @click="add()" :loading="isLoading"
            >保存</el-button
          >

          <el-button @click="backList()">返回</el-button>
        </div>
      </el-form-item>
    </el-form>
    <el-dialog
      title="图片库"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="800px"
    >
      <upload-picture v-if="dialogVisible" type="course" @getImage="getImageAct" />
    </el-dialog>
  </div>
</template>
<script>
import {
  addCourse,
  updateCourse,
  courseDetail,
} from "@/api/learn/course";
import {
  getLector
} from "@/api/system/lector";
import { listJavaCategory } from "@/api/learn/category";
import UploadPicture from "@/components/Common/UploadPicture";
import lectorChoice from '@/page/components/choiceLector/index.vue';
export default {
  props: ["uuid", "courseStatus"],
  components: {
    UploadPicture,
    lectorChoice
  },
  data() {
    return {
      titleLength: 101,
      dialogVisible: false,
      isLoading: false,
      fullTreeData: [], // 分类列表
      expandOnClickNode: true,
      lectorList:[],//讲师列表
      treeProps: {
        children: "children",
        label: "label",
      },
      rules: {
        title: [
          {
            required: true,
            message: "请输入标题",
          },
          {
            max: 100,
            message: "长度在100字符内",
          },
        ],
        categoryTitle: [
          {
            type: "string",
            required: true,
            message: "请选择分类",
            trigger: "change",
          },
        ],
        cdnPicture: [
          {
            type: "string",
            required: true,
            message: "请上传封面图",
            trigger: "change",
          },
        ],
      },
      showAdd: true,
      lectorId: '',
      lectorName: ''
    };
  },
  computed: {
    courseInfo() {
      let info = this.$store.state.course.courseInfo;
      if(info.lectorId) {
        info.lectorName = '$userName=' + info.lectorId
      }else{
        info.lectorName = ''
      }
      return info;
    },
  },
  mounted() {
    this.getData();
    this.getCategory();
  },
  methods: {
    // 选择讲师
    choiceLector(item) {
      this.lectorId = item.lectorId
      this.lectorName = item.lectorName
    },
    getLectorList() {
      let that = this;
      let data = {};
      getLector(data).then((res) => {
        if (res.code === 200) {
          res.data.map(function (items) {
            let lectorData = {
              value: items.lectorId,
              label: items.lectorName,
            }
            that.lectorList.push(lectorData);
          })
        }
      })
      .catch((error) => {
        console.error(error);
      });
    },
    backList() {
        this.$router.push({
        path: "/course/courseList",
        query: {
          status:
            this.courseStatus == "unpublished"
              ? "0"
              : this.courseStatus == "close"
              ? "2"
              : "1",
        },
      });
    },
    getImageAct(values) {
      this.courseInfo.picture = values[0].object ? values[0].object : "";
      this.courseInfo.cdnPicture = values[0].cdnObject ? values[0].cdnObject : "";
      this.dialogVisible = false;
    },
    selectImage() {
      this.dialogVisible = true;
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
      this.courseInfo.categoryId = node.id;
      this.courseInfo.categoryTitle = node.label;
      this.$refs.selectTree.blur();
    },
    // 获取课程详情
    getData() {
      const that = this;
      if (that.uuid) {
        courseDetail(that.uuid)
          .then((res) => {
            if (res.code === 200) {
              that.$store.dispatch("course/addView", res.data);
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    // 获取分类列表
    getCategory() {
      let _self = this;
      listJavaCategory().then(function (res) {
        _self.fullTreeData = res.data;
      });
    },
    // 保存
    add() {
      let that = this;

      if (!this.courseInfo.title) {
        this.$message({
          showClose: true,
          message: "请填写课程标题",
          type: "error",
        });
        return;
      }
      if (!this.courseInfo.categoryId) {
        this.$message({
          showClose: true,
          message: "请选择课程分类",
          type: "error",
        });
        return;
      }
      if (!this.courseInfo.cdnPicture) {
        this.$message({
          showClose: true,
          message: "请上传封面图",
          type: "error",
        });
        return;
      }
      if (this.isLoading) {
        return;
      }
      this.isLoading = true;
      let template = {
        imgPicked: this.courseInfo.imgPicked,
        title: this.courseInfo.title,
        categoryId: this.courseInfo.categoryId,
        categoryTitle: this.courseInfo.categoryTitle,
        action: "update",
        learnMode: this.courseInfo.learnMode,
        speed: this.courseInfo.speed,
        drag: this.courseInfo.drag,
        picture: this.courseInfo.picture,
        credit: this.courseInfo.credit,
        lectorId: this.lectorId,
        lectorName: this.lectorName,
        syncLearn: this.courseInfo.syncLearn,
        eligible: this.courseInfo.eligible,
        learnProgress: this.courseInfo.learnProgress,
      };
      if (!that.uuid) {
        addCourse(template).then(function (res) {
          that.$store.dispatch("course/addView", res.data);

          setTimeout(() => {
            that.isLoading = false;
            that.$emit("changeType", { type: "lessonSetting" });
          }, 500);

          that.$message({
            showClose: true,
            message: "创建成功",
            type: "success",
            onClose: function () {},
          });
        });
      } else {
        template.uuid = that.uuid;
        updateCourse(template).then(function (res) {
          if (res.code == 200) {

            that.isLoading = false;
            that.$message({
              showClose: true,
              message: res.msg,
              type: "success",
              onClose: function () {},
            });
          }
        });
      }
    },
  },
};
</script>
<style lang="less">
#course-base-setup {
  overflow: auto;
  margin: 0 auto;
  width: 800px;
  height: calc(100vh - 155px);
  background: #ffffff;
  margin-top: 10px;
  padding: 15px 20px;

  .el-radio-group {
    display: block !important;
    height: 46px;
    line-height: 46px;
  }

  .tooltipcss {
    text-align: justify;
    text-justify: newspaper;
    word-break: break-all;
    width: 400px;
  }

  .addlec {
    .el-button--primary {
      color: #fff;
      background-color: #3296fa;
      border-color: #3296fa;
      margin-top: 40px;
    }
  }

  .el-tag + .el-tag {
    margin-left: 10px;
  }

  /deep/ .el-select__input {
    height: auto;
  }

  .button-new-tag {
    margin-left: 0px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  .el-form-item__content {
    // white-space: nowrap;
    .img1,
    .img2,
    .img3 {
      width: 200px;
      height: 130px;
      display: inline-block;
      margin-right: 10px;
      cursor: pointer;

      .img {
        display: inline-block;
        width: 200px;
        height: 100px;
        position: relative;

        .ctit {
          position: absolute;
          top: 30%;
          left: 40%;
          margin-left: -70px;
          margin-top: -30px;
          color: #fff;
          height: 60px;
          width: 120px;
          line-height: 30px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          white-space: normal;
          word-break: break-all;
        }

        .ctit2 {
          position: absolute;
          top: 30%;
          left: 40%;
          margin-left: -70px;
          margin-top: -30px;
          color: #999;
          height: 60px;
          width: 120px;
          line-height: 30px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          white-space: normal;
          word-break: break-all;
        }

        .change {
          position: absolute;
          top: 50%;
          left: 50%;
          margin-left: 48px;
          margin-top: -47px;
          color: #3296fa;
          width: 50px;
          line-height: 21px;
          text-align: center;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          white-space: normal;
          word-break: break-all;
          background-color: #fff;
          opacity: 0.8;
          border-radius: 26px;
        }
      }

      img {
        width: 100%;
        height: 100%;
      }

      > div {
        input {
          display: inline-block;
          vertical-align: middle;
          margin-right: 5px;
        }

        span {
          display: inline-block;
          vertical-align: middle;
        }
      }
    }

    .custom {
      cursor: pointer;

      input {
        display: inline-block;
        vertical-align: middle;
        margin-right: 5px;
      }

      span {
        display: inline-block;
        vertical-align: middle;
      }
    }
  }

  .multiple-tag {
    .el-select__tags-text {
      display: inline-block;
      max-width: 150px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .el-select .el-tag__close.el-icon-close {
      top: -6px;
    }
  }

  .btn-box {
    padding-top: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding-right: 150px;
    box-sizing: border-box;
    .el-button {
      margin: 0 10px;
    }
  }
}
</style>
