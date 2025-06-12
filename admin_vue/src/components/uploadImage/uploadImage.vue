<template>
  <div class="upload-image">
    <div class="el-dialog__wrapper_box" :class="{show: file}">
      <div class="el-dialog">
        <div class="el-dialog__header">
          <span class="el-dialog__title">调整尺寸</span>
          <button type="button" class="el-dialog__headerbtn" @click="closeCropper">
            <i class="el-dialog__close el-icon el-icon-close"></i>
          </button>
        </div>
        <div class="el-dialog__body">
          <vue-cropper
            class="upload-image-box"
            ref="cropper"
            :zoomable="true"
            :autoCropArea="1"
            :viewMode="viewMode"
            :aspectRatio="aspectRatio">
          </vue-cropper>
        </div>
        <div class="el-dialog__footer">
          <span class="upload-image-bar">
            <el-button @click="open">重新上传</el-button>
            <el-button type="primary" @click="clipperSubmit">确定</el-button>
            <input type="file" :accept="accept" class="upload-image-input" ref="upload">
          </span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
 import { getSignature } from "@/api/aliyun/aliyunApi";
  /**
   * 使用方法：载入组件后，this.$res.uploadImage.open();
   */
   import "cropperjs/dist/cropper.css";
    import VueCropper from "vue-cropperjs";
	export default {
		name: 'uploadImage',
		data () {
			return {
        // 裁切数据
        cropData: null,
        // input提交的文件
        file: null,
        // 图片url
        imageURL: null,
			}
    },
    props: {
      // 显示上传的图片格式
      accept: {
        type: String,
        default: '.jpg,.jpeg,.png,.gif,.bmp'
      },
      // 裁切比例
      aspectRatio: {
        type: Number,
        default: 0,
      },
      // OSS存储的目录
      directory: {
        type: String,
        default: 'course/image'
      },
      // 裁切输出的图片宽度
      width: {
        type: Number,
        default: 0,
      },
      // 裁切输出的图片高度
      height: {
        type: Number,
        default: 0,
      },
      maxWidth: {
        type: Number,
        default: 2560,
      },
      maxHeight: {
        type: Number,
        default: 1280,
      },
      viewMode: {
        type: Number,
        default: 2,
      },
      // 从自定义logo传过来（为了解决透明图片生成黑色背景问题）
      isLogo : {
        type: Boolean,
      },
      isBanner:{
        type: Boolean,
      }
    },
    components: {
      VueCropper
    },
    watch: {
      aspectRatio (val) {
        this.$refs.cropper.setAspectRatio(val);
      }
    },
    methods: {

      /**
       * 开启
       */
      open () {
        this.$refs.upload.click();
      },

      /**
       * 获取文件的回调
       */
      handleFileChange (event) {
        this.file = event.target.files[0];
        if (this.file && /^image\/\w+$/.test(this.file.type)) {
          let URL = window.URL || window.webkitURL;
          if (this.imageURL) {
            URL.revokeObjectURL(this.imageURL);
          }
          this.imageURL = URL.createObjectURL(this.file);
          this.$refs.cropper.replace(this.imageURL);
        }
      },

      /**
       * 关闭窗口
       */
      closeCropper () {
        let URL = window.URL || window.webkitURL;
        URL.revokeObjectURL(this.imageURL);
        this.imageURL = null;
        this.file = null;
        this.$refs.cropper.clear();
        this.$refs.upload.value = '';
      },

      /**
       * 获取裁切数据并上传
       */
      clipperSubmit () {
        let _blob = null;
        if (this.width > 0 && this.height > 0) {
          if(this.isLogo){
            _blob = this.convertBase64UrlToBlob(this.$refs.cropper.getCroppedCanvas({
              width: this.width,
              height: this.height,
              maxWidth: this.maxWidth,
              maxHeight: this.maxHeight,
              imageSmoothingQuality: 'high',
            }).toDataURL('image/png'));
          }else{
            _blob = this.convertBase64UrlToBlob(this.$refs.cropper.getCroppedCanvas({
              width: this.width,
              height: this.height,
              maxWidth: this.maxWidth,
              maxHeight: this.maxHeight,
              imageSmoothingQuality: 'high',
            }).toDataURL('image/jpeg'));
          }
        } else if (this.width) {
          _blob = this.convertBase64UrlToBlob(this.$refs.cropper.getCroppedCanvas({
            width: this.width,
            maxWidth: this.maxWidth,
            maxHeight: this.maxHeight,
            imageSmoothingQuality: 'high',
          }).toDataURL('image/jpeg'));
        } else if (this.height) {
          _blob = this.convertBase64UrlToBlob(this.$refs.cropper.getCroppedCanvas({
            height: this.height,
            maxWidth: this.maxWidth,
            maxHeight: this.maxHeight,
            imageSmoothingQuality: 'high',
          }).toDataURL('image/jpeg'));
        } else {
          _blob = this.convertBase64UrlToBlob(this.$refs.cropper.getCroppedCanvas({
            maxWidth: this.maxWidth,
            maxHeight: this.maxHeight,
            imageSmoothingQuality: 'high',
          }).toDataURL('image/jpeg'));
        }

        getSignature().then((res) => {
          let _suffix = this.file.name.substring(this.file.name.lastIndexOf('.') + 1);
          const generateRandomString = (length) => {
            const characters = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
            return Array.from({ length }, () => characters.charAt(Math.floor(Math.random() * characters.length))).join('');
          };
          let _uniq = generateRandomString(10) + Date.now();
          let _newname = `${res.data.companyId}/${this.directory}/${_uniq}.${_suffix}`;
          let _formData = new FormData();
          _formData.append('OSSAccessKeyId', res.data.accessid);
          _formData.append('policy', res.data.policy);
          _formData.append('signature', res.data.signature);
          _formData.append('key', _newname);
          _formData.append('name', _uniq + '.' + _suffix);
          _formData.append('success_action_status', '200');
          _formData.append('file', _blob, this.file.name);
          let _oReq = new XMLHttpRequest();
          _oReq.addEventListener('load', () => {
            this.$emit('success', _newname);
            this.closeCropper();
          });
          _oReq.onerror = () => {
            this.closeCropper();
          };
          _oReq.open('POST', res.data.host);
          _oReq.send(_formData);
        })
      },


      /**
       * 获取裁切数据并上传
       * @param {string} urlData url数据
       */
      convertBase64UrlToBlob (urlData) {
        // console.log("获取裁切数据并上传");
        let bytes = window.atob(urlData.split(',')[1]); // 去掉url的头，并转换为byte
        // 处理异常,将ascii码小于0的转换为大于0
        let ab = new ArrayBuffer(bytes.length);
        let ia = new Uint8Array(ab);
        for (let i = 0; i < bytes.length; i++) {
            ia[i] = bytes.charCodeAt(i);
        }
        return new Blob([ab], {
          type: 'image/jpeg'
        });
      },
		},
		mounted () {
      this.$refs.upload.removeEventListener('change', this.handleFileChange);
      this.$refs.upload.addEventListener('change', this.handleFileChange);
      // 如果是banner就是 4：1 宽高比
      if(this.isBanner){
        this.maxHeight  = 640
      }
      // console.log("mounted");
    },
    beforeDestroy () {
      this.$refs.upload.removeEventListener('change', this.handleFileChange);
    }
	}
</script>
<style lang="less" scoped>
.el-dialog__wrapper_box{
  opacity: 0;
  visibility: hidden;
  z-index: 9999;
  &.show{
    opacity: 1;
    visibility: visible;
  }
  .el-dialog{
    position: absolute;
    left: 80px;
    top: 80px;
    right: 80px;
    bottom: 80px;
    margin: 0 !important;
    width: auto !important;
    height: 530px;
    display: flex;
    flex-flow: column;
    .el-dialog__body{
      height: 400px;
      overflow: hidden;
      padding: 0 !important;
      .upload-image-box{
        height: 400px;
        overflow: hidden;
        .upload-image-input{
          opacity: 0;
          position: absolute;
          left: 0;
          bottom: 0;
        }
      }
    }
    .el-dialog__footer{
      .upload-image-bar{
        .upload-image-input{
          display: none;
        }
      }
    }
  }
}
.v-modal{
  opacity: 0;
  visibility: hidden;
  z-index: 9998;
  &.show{
    opacity: .5;
    visibility: visible;
  }
}
</style>
