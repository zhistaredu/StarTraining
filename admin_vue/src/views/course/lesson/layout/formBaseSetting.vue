<template>
  <div id="lesson-base-setup">
    <el-form ref="form" :model="lessonInfo" :rules="rules" label-width="150px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model.trim="lessonInfo.title"
          :maxlength="titleLength"
        ></el-input>
      </el-form-item>
      <el-form-item label="分类" prop="categoryTitle">
        <el-select
          class="main-select-tree"
          ref="selectTree"
          v-model="lessonInfo.categoryTitle"
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
      <el-form-item label="使用范围" prop="learnMode">
        <el-radio v-model="lessonInfo.useRange" label="all"
          >全部管理员</el-radio
        >
        <el-radio v-model="lessonInfo.useRange" label="default"
          >默认范围</el-radio
        >
        <el-tooltip placement="top">
          <div slot="content">
            默认范围:默认管理范围是本部门及上级部门管理员
          </div>
          <i class="iconfont icon-tishi2" style="font-size: 14px; color: #ccc;cursor: pointer;"></i>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="学习时长">
        <el-col :xs="12" :sm="12" :md="8" :lg="6" v-if="lessonInfo.type == 'video'">
          <span>{{ lessonInfo.length | timeFormat }}</span>
        </el-col>
        <el-col :xs="12" :sm="12" :md="8" :lg="6" v-else>
          <el-input v-model.number="minute" size="small" type="number" min="1" max="999" :maxlength='5'>
            <template slot="append">分</template>
          </el-input>
        </el-col>
      </el-form-item>
      <el-form-item>
        <div class="btn-box">
          <el-button type="primary" @click="addLesson()" :loading="isLoading"
            >保存</el-button
          >
          <el-button @click="backList()">返回</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {
    getLesson,
    updateLesson
} from "@/api/learn/lesson";
import { listJavaCategory } from "@/api/learn/category";
export default {
  props: ["uuid"],
  components: {
  },
  data() {
    return {
      titleLength: 101,
      dialogVisible: false,
      isLoading: false,
      fullTreeData: [], // 分类列表
      expandOnClickNode: true,
      lessonInfo: {
        uuid: '',
        title: "",
        categoryId: "",
        categoryTitle: "",
        length: 0,
        useRange: "default",
      },
      minute: 0,
      second: 0,
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
      },
      showAdd: true,
    };
  },
  mounted() {
    this.getData();
    this.getCategory();
  },
  methods: {
    backList() {
        this.$router.push({
          path: "/course/lessonList",
          query: {
            categoryId: this.lessonInfo.categoryId,
          },
        });
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
      this.lessonInfo.categoryId = node.id;
      this.lessonInfo.categoryTitle = node.label;
      this.$refs.selectTree.blur();
    },
    // 获取课程详情
    getData() {
      const that = this;
      if (that.uuid) {
        getLesson(that.uuid)
          .then((res) => {
            if (res.code === 200) {
              that.lessonInfo.uuid = res.data.uuid;
              that.lessonInfo.title = res.data.title;
              that.lessonInfo.categoryId = res.data.categoryId;
              that.lessonInfo.categoryTitle = res.data.categoryTitle;
              that.lessonInfo.useRange = res.data.useRange;
              that.lessonInfo.length = res.data.length;
              that.lessonInfo.type = res.data.type;
              that.minute = parseInt(res.data.length / 60);
              that.second = parseInt(res.data.length % 60);
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
    addLesson() {
      let that = this;
      if (!this.lessonInfo.title) {
        this.$message({
          showClose: true,
          message: "请填写标题",
          type: "error",
        });
        return;
      }
      if (!this.lessonInfo.categoryId) {
        this.$message({
          showClose: true,
          message: "请选择分类",
          type: "error",
        });
        return;
      }
      if (this.isLoading) {
        return;
      }
      this.isLoading = true;
      let template = {
        title: this.lessonInfo.title,
        categoryId: this.lessonInfo.categoryId,
        categoryTitle: this.lessonInfo.categoryTitle,
        useRange: this.lessonInfo.useRange,
        length: this.minute * 60 + this.second,
      };
      if (that.uuid) {
        template.uuid = that.uuid;
        updateLesson(template).then(function (res) {
          if (res.code == 200) {
            that.isLoading = false;
            that.$message({
              showClose: true,
              message: res.msg,
              type: "success",
              onClose: function () {
                that.$router.push({
                  path: "/course/lessonList",
                  query: {
                    categoryId: that.lessonInfo.categoryId,
                  },
                });
              },
            });
          }
        });
      }
    },
  },
};
</script>
<style lang="less">
#lesson-base-setup {
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
