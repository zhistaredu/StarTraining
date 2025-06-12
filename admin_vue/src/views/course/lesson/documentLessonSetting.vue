<template>
  <div class="document-lesson-set">
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
        <el-row>
          <el-col :span="20">
            <el-form-item label="添加文档" prop="object">
              <el-upload class="upload-demo" drag :action="uploadUrl" :accept="accept"
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
        <el-row v-if="fileList.length">
          <el-col :span="18">
            <el-form-item label="标题" prop="title">
              <el-input v-model.trim="form.title" class="h-40 w-100" :maxlength='50'></el-input>
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
            <el-form-item label="学习时长">
              <el-col :xs="12" :sm="12" :md="8" :lg="6">
                <el-input v-model.number="form.minute" size="small" type="number" min="1" max="999" :maxlength='5'>
                  <template slot="append">分</template>
                </el-input>
              </el-col>
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
} from "@/api/aliyun/aliyunApi"
import { listJavaCategory } from "@/api/learn/category";
import documentUpload from "@/assets/js/utils/documentUpload";

export default {
  mixins: [documentUpload],
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
      isLoading: false,
      isSave: false,
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
        'success_action_status': '200', //让服务端返回200,不然，默认会返回204
      },
      uploadUrl: '',
      companyId: '',
      accept: '.doc,.docx,.wps,.ppt,.pptx,.pdf,.txt,.xls,.xlsx',
      form: {
        title: '',
        categoryId: '',
        categoryTitle: '',
        length: 0,
        minute: 5,
        object: '',
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
        categoryId: [{
          required: true,
          message: "请先选择分类"
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
  mounted() {
    this.getUploadParam();
    this.getCategory();
  },
  methods: {
    // 获取分类列表
    getCategory() {
      let that = this;
      listJavaCategory().then(function (res) {
        that.fullTreeData = res.data;
        if (that.categoryTitle && that.categoryTitle!='全部') {
          that.form.categoryTitle = that.categoryTitle
        } else {
          that.form.categoryTitle = that.fullTreeData[0].label
        }
        if (that.categoryId && that.categoryId!='all') {
          that.form.categoryId = that.categoryId
        } else {
          that.form.categoryId = that.fullTreeData[0].id
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
    addLesson() {
      let that = this;
      this.isLoading = true;
      this.form.object = this.uploadParams.key;
      this.form.title = this.form.title;
      this.form.length = this.form.minute * 60;
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
    //获取上传签名
    getUploadParam() {
      let that = this;
      getSignature().then(function (res) {
        that.uploadUrl = res.data.host;
        that.uploadParams.policy = res.data.policy;
        that.uploadParams.signature = res.data.signature;
        that.uploadParams.OSSAccessKeyId = res.data.accessid;
        that.companyId = res.data.companyId;
      });
    },
  },
}

</script>
<style lang="less">
.document-lesson-set {
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
