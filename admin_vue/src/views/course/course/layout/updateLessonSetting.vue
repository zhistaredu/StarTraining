<template>
  <div class="video-lesson-update">
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
          <el-col :span="18">
            <el-form-item label="文件">
              <ul class="uploaded_list">
                <li v-for="(item, index) in fileList" :key="index">
                  <i class="iconfont icon-shipin" v-if="form.type == 'video'"></i>
                  <i class="iconfont icon-wendang1" v-else-if="form.type == 'document'"></i>
                  <span>{{ item.name }}</span>
                </li>
              </ul>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row v-if="form.type == 'document'">
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
          <el-button type="primary" @click="add('form')" :loading="isLoading">保存</el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import {
  getCourseLessonDetail,
  updateCourseLessonDetail
} from "@/api/learn/course";

export default {
  components: {
  },
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
      isSave: false,
      fileList: [],
      multipart_params: {
        'name': '',
        'key': '',
        'policy': '',
        'OSSAccessKeyId': '',
        'success_action_status': '200', //让服务端返回200,不然，默认会返回204
        'signature': '',
      },
      uploadUrl: '',
      companyId: '',
      accept: '.3gp,.avi,.flv,.mp4,.mpg,.asf,.wmv,.mkv,.mov,.vob',
      form: {
        uuid: "",
        title: '',
        minute: 5,
        length: 0,
        fileType: '',
        type: 'video',
        filename: '',
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
      }
    };
  },
  computed: {
    courseInfo() {
      return this.$store.state.course.courseInfo
    },
  },
  mounted() {
    this.getData();
  },
  methods: {
    add(form) {
      this.$refs[form].validate((valid) => {
        if (this.coursewareId) {
          if (this.isLoading) {
            return
          }
          this.isLoading = true;
          if (this.form.type == 'document') {
            this.form.length = this.form.minute * 60;
          }
          let data = {
            uuid: this.form.uuid,
            title: this.form.title,
            type: this.form.type,
            fileType: this.form.fileType,
            length: this.form.length,
          }
          this.$emit('ok', data);
          this.isLoading = false;
        }
      })
    },
    cancel() {
      this.$emit('ok', "cancel");
    },
    getData() {
      let that = this;
      if (this.coursewareId) {
        let data = {
          uuid: this.coursewareId,
          courseId: this.courseInfo.uuid,
        }
        getCourseLessonDetail(data).then(function (res) {
          if (res.code == 200) {
            that.form.uuid = res.data.uuid;
            that.form.title = res.data.title;
            that.form.type = res.data.type;
            that.form.fileType = res.data.fileType;
            that.form.length = res.data.length;
            that.form.filename = res.data.mediaName;
            if (res.data.type == 'document') {
              that.form.minute = res.data.length / 60;
            }
            that.fileList.push({
              uid: Math.random() * 1000,
              name: res.data.title
            });
          }
        })
      }
    }
  },

}
</script>
<style lang="less">
.video-lesson-update {

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
