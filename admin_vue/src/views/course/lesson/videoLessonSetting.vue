<template>
  <div class="video-lesson-set">
    <el-row style="margin-top:20px;">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="18">
            <el-form-item label="当前所属分类" prop="categoryId">
              <el-select class="main-select-tree" ref="selectTree" v-model="form.categoryTitle" style="width: 300px">
                <el-option v-for="(item, index) in optionData(fullTreeData)" :key="index" :label="item.label"
                  :value="item.value" style="display: none" />
                <el-tree class="main-select-el-tree" ref="selectelTree" :data="fullTreeData" :props="treeProps"
                  highlight-current @node-click="handleNodeClick" :expand-on-click-node="expandOnClickNode"
                  default-expand-all />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row  v-if="fileList.length">
          <el-col :span="20">
            <el-form-item label="标题" prop="title">
              <el-input
                v-model.trim="form.title"
                :maxlength="titleLength"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item label="视频文件" prop="materialId">
              <el-upload class="upload-demo" drag :action="uploadUrl" :accept="accept"
                :file-list="fileList"
                :before-upload="beforeUpload"
                :on-success="uploadSuccess"
                :data="uploadParams"
                :on-remove="handleRemove">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div slot="tip" class="el-upload__tip">
                  (1)、上传单视频小于5G。
                  <br>(2)、支持3gp、avi、flv、mp4、mpg、asf、wmv、mkv、mov等格式的视频文件上传。
                  <br>(3)、为了适配手机端播放，上传视频需同时进行转码处理，请耐心等待。
                  <br>(4)、请勿上传已加密或只读文件，否则系统因无法转换而报错。
                  <br>(5)、课件上传成功后，自动添加到课件管理列表，方便后续使用或维护。
                </div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item label="使用范围" prop="learnMode">
              <el-radio v-model="form.useRange" label="all"
                >全部管理员</el-radio
              >
              <el-radio v-model="form.useRange" label="default"
                >默认范围</el-radio
              >
              <el-tooltip placement="top">
                <div slot="content">
                  默认范围:默认管理范围是本部门及上级部门管理员
                </div>
                <i class="iconfont icon-tishi2" style="font-size: 14px"></i>
              </el-tooltip>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <div class="btn-box">
              <el-button type="primary" @click="addLesson()" :loading="isLoading"
                >保存</el-button
              >
              <el-button @click="backList()">关闭</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import {
  getSignature,
  uploadfile
} from "@/api/aliyun/aliyunApi";
import {
  delLesson
} from "@/api/learn/lesson";
import { listJavaCategory } from "@/api/learn/category";
import videoUpload from "@/assets/js/utils/videoUpload";

export default {
  mixins: [videoUpload],
  props: {
    categoryId: {
      type: String,
      default: ''
    },
    categoryTitle: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      titleLength: 150,
      isLoading: false,
      fileList: [],
      fullTreeData: [], // 分类列表
      treeProps: {
        children: "children",
        label: "label",
      },
      expandOnClickNode: true,
      uploadParams: {
        'name': '',
        'key': '',
        'policy': '',
        'signature': '',
        'OSSAccessKeyId': '',
      },
      uploadUrl: '',
      companyId: '',
      accept: '.3gp,.avi,.flv,.mp4,.mpg,.asf,.wmv,.mkv,.mov,.vob',
      form: {
        title: '',
        categoryId: '',
        categoryTitle: '',
        object: '',
        length: 0,
        fileType: '',
        type: 'video',
        name: '',
        size: 0,
        useRange: "default",
      },
      rules: {
        title: [{
          required: true,
          message: "请输入标题"
        }, {
          max: 50,
          message: "长度在 50 字符内",
          trigger: 'blur'
        }],
        categoryId: [{
          required: true,
          message: "请先选择分类"
        }],
      }
    };
  },
  watch: {
    categoryId(val) {
      this.form.categoryId = val
    },
    categoryTitle(val) {
      this.form.categoryTitle = val
    },
  },
  mounted() {
    this.fileList = [];
    this.getUploadParam();
    this.getCategory();
  },
  methods: {
    // 获取分类列表
    getCategory() {
      let _self = this;
      listJavaCategory().then(function (res) {
        _self.fullTreeData = res.data;
        if (_self.categoryTitle && _self.categoryTitle != '全部') {
          _self.form.categoryTitle = _self.categoryTitle
        } else {
          _self.form.categoryTitle = _self.fullTreeData[0].label
        }
        if (_self.categoryId && _self.categoryId != 'all') {
          _self.form.categoryId = _self.categoryId
        } else {
          _self.form.categoryId = _self.fullTreeData[0].id
        }
      });
    },
    handleNodeClick(node) {
      this.$refs.selectTree.blur();
      this.form.categoryId = node.id;
      this.form.categoryTitle = node.label;
      this.$emit('changeCate', { id: node.id, label: node.label })
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
    handleExceed(files, fileList) {
       this.$message.warning('最多上传5个文件！');
    },
    handleRemove(file, fileList) {
      this.form.object = "";
    },
    beforeUpload(file) {
      let that = this;
      let data = this.beforeVideoUpload(file);
      if (!data.status) {
        return false;
      }
      let localFileSrc = null;
      if (window.createObjectURL != undefined) {
        localFileSrc = window.createObjectURL(file);
      } else if (window.URL != undefined) {
        localFileSrc = window.URL.createObjectURL(file);
      } else if (window.webkitURL != undefined) {
        localFileSrc = window.webkitURL.createObjectURL(file);
      }
      this.form.length = 300;
      this.uploadParams.key = this.companyId + data.uploadKey;
      this.uploadParams.name = data.uploadName;
      this.form.fileType = data.fileType;
      this.form.size = file.size;
      this.form.name = file.name;
      this.form.object = this.uploadParams.key;
    },
    //上传成功回调事件
    uploadSuccess(response, file, fileList) {
      this.form.title = file.name.substring(0, file.name.lastIndexOf('.'));
      this.fileList = [];
      this.fileList.push(file);
    },
    addLesson() {
      let that = this;
      this.isLoading = true;
      uploadfile(this.form).then(function (res) {
        if (res.code == 200) {
          that.isLoading = false;
          that.$emit("ok", that.form.categoryId);
        }
      })
    },
    backList() {
      this.$emit("ok", this.form.categoryId);
    },
    //获取文件本地目录
    getFileObjectURL(file) {
      var url = null;
      if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
      } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
      } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
      }
      return url;
    },
    //获取上传签名
    getUploadParam() {
      let that = this;
      getSignature().then(function (res) {
        that.uploadUrl = res.data.host;
        that.uploadParams.policy = res.data.policy;
        that.uploadParams.signature = res.data.signature;
        that.uploadParams.OSSAccessKeyId = res.data.accessid;
        that.companyId = res.data.companyId;
      })
    }
  },

}
</script>
<style lang="less">
.video-lesson-set {
  .el-upload-list__item .el-icon-close {
    top: 5px;
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
