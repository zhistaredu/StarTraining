<template>
  <div class="document-lesson-set">
    <el-row style="margin-top:20px;">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row v-if="fileList.length">
          <el-col :span="18">
            <el-form-item label="标题" prop="title">
              <el-input v-model.trim="form.title" class="h-40 w-100" :maxlength='50'></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="添加文档" prop="object">
              <el-upload class="upload-demo" drag :action="uploadUrl" :accept="accept" :on-change="handleChange"
                :file-list="fileList" :before-upload="beforeUpload" :on-success="uploadSuccess" :data="uploadParams"
                :on-remove="handleRemove" multiple>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div slot="tip" class="el-upload__tip">
                  (1)、PDF文档默认每页学习时长1分钟，其他类型文件需要管理员设置学习时长。
                  <br>(2)、支持doc，docx，wps，ppt，pptx，pdf，txt，xls，xlsx格式文档上传。
                  <br>(3)、请勿上传已加密或只读文件，否则系统因无法转换而报错。
                  <br>(4)、课件上传成功后，自动添加到课件管理列表，方便后续使用或维护。
                </div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="课件学时">
              <el-col :xs="12" :sm="12" :md="8" :lg="6">
                <el-form-item prop="minute">
                  <el-input v-model.number="form.minute" size="small" type="number" min="0" max="999" :maxlength='5'>
                    <template slot="append">分</template>
                  </el-input>
                </el-form-item>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button type="primary" @click="add('form')" :loading="isLoading" v-if="fileList.length">保存</el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import {
  getSignature,
  uploadfile
} from "@/api/aliyun/aliyunApi"
import {
  getCourseLessonDetail,
  updateCourseLessonDetail,
  saveCourseLessonList
} from "@/api/learn/course";
import documentUpload from "@/assets/js/utils/documentUpload";

export default {
  mixins: [documentUpload],
  props: {
    coursewareId: {
      type: String,
      default: ''
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
        'OSSAccessKeyId': '',
        'success_action_status': '200',
        'signature': '',
      },
      uploadUrl: '',
      companyId: '',
      accept: '.doc,.docx,.wps,.ppt,.pptx,.pdf,.txt,.xls,.xlsx',
      form: {
        title: '',
        minute: 5,
        categoryId: '',
        categoryTitle: '',
        object: '',
        length: 300,
        fileType: '',
        type: 'document',
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
          message: "标题最多不超多50个字",
          trigger: 'blur'
        }],
        object: [{
          required: true,
          message: "请先上传文件"
        }],
        minute: [{
          required: true,
          type: 'integer',
          max: 999,
          min: 0,
          message: "请输入1000以内的非负整数"
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
    handleChange(file, fileList) { },
    handleRemove(file, fileList) {
      this.form.object = "";
    },
    beforeUpload(file) {
      let that = this;
      let data = this.beforeDocumentUpload(file);
      if (!data.status) {
        return false;
      }
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
      let _self = this;
      getSignature().then(function (res) {
        _self.uploadUrl = res.data.host;
        _self.uploadParams.policy = res.data.policy;
        _self.uploadParams.signature = res.data.signature;
        _self.uploadParams.OSSAccessKeyId = res.data.accessid;
        _self.companyId = res.data.companyId;
      });
    },
    add(form) {
      let _self = this;
      this.isLoading = true;
      this.form.length = this.form.minute * 60;
      this.form.categoryId = this.courseInfo.categoryId;
      this.form.categoryTitle = this.courseInfo.categoryTitle;
      uploadfile(this.form).then(function (res) {
        if (res.code == 200) {
          let data = {
            uuid: res.data.uuid,
            title: _self.form.title,
            fileType: _self.form.fileType,
            type: 'document',
            length: _self.form.length,
          }
          _self.$emit('ok', data);
          _self.isLoading = false;
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
.document-lesson-set {
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
