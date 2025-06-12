<template>
  <div class="video-lesson-set">
    <el-row style="margin-top:20px;">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="16">
            <el-form-item label="添加视频" prop="object">

              <el-upload class="upload-demo" drag :action="uploadUrl" :accept="accept"
                :file-list="fileList" :before-upload="beforeUpload" :limit="5"
                :on-exceed="handleExceed" :on-success="uploadSuccess" :data="uploadParams" :on-remove="handleRemove"
                multiple>
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
        <el-form-item>
          <el-button type="primary" @click="add()" :loading="isLoading" v-if="fileList.length">保存</el-button>
        </el-form-item>

      </el-form>
    </el-row>
  </div>
</template>

<script>
import {
  getSignature,
  uploadfile
} from "@/api/aliyun/aliyunApi";
import { videoUpload } from "@/assets/js/utils/videoUpload";

export default {
  mixins: [videoUpload],
  props: {
    coursewareId: {
      type: String,
      default: ''
    },
    isedit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      isLoading: false,
      fileList: [],
      uploadParams: {
        'name': '',
        'key': '',
        'policy': '',
        'signature': '',
        'OSSAccessKeyId': '',
        'success_action_status': '200',
      },
      uploadUrl: '',
      companyId: '',
      accept: '.3gp,.avi,.flv,.mp4,.mpg,.asf,.wmv,.mkv,.mov,.vob',
      title: '',
      length: 0,
      object: '',
      name: '',
      size: 0,
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
        object: [{
          required: true,
          message: "请先上传文件"
        }],

      }
    };
  },
  computed: {
    courseInfo() {
      return this.$store.state.course.courseInfo
    },
  },
  mounted() {
    this.getUploadParam();
  },
  methods: {
    back() {
      this.$emit('back')
    },
    handleRemove(file, fileList) {
      this.form.object = "";
    },
    handleExceed(files, fileList) {
      this.$message({
        showClose: true,
        message: "一次最多同时上传5个文件",
        type: 'error',
      });
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
    },
    add() {
      let that = this;
      this.isLoading = true;
      this.form.categoryId = this.courseInfo.categoryId;
      this.form.categoryTitle = this.courseInfo.categoryTitle;
      uploadfile(this.form).then(function (res) {
        if (res.code == 200) {
          let value = {
            uuid: res.data.uuid,
            title: res.data.title,
            length: res.data.length,
            fileType: res.data.fileType,
            type: 'video',
          }
          that.$emit('ok', value);
          that.isLoading = false;
        }
      })
    },
    cancel() {
      this.$emit('ok', "cancel");
    },
  },
}
</script>

<style lang="less">
.video-lesson-set {

  .el-upload-list__item .el-icon-close {
    top: 5px;
  }

  .uploaded_list {
    width: 100%;
    padding: 0;
    margin: 0;

    li {
      width: 100%;
      display: flex;
      align-items: center;
      font-size: 14px;
      color: #333;

      .iconfont {
        margin-right: 5px;

      }
    }
  }
}
</style>
