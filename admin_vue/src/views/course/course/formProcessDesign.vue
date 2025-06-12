<template>
  <el-container>
    <el-header style="background: #fff;">
      <layout-header v-model="activeSelect" :courseStatus="courseStatus" @publish="publishProcess"></layout-header>
    </el-header>
    <div class="layout-body">
      <form-base-setting ref="baseSetting" :uuid="uuid" :courseStatus="courseStatus" v-if="activeSelect === 'baseSetting'"  @changeType="changeType"/>
      <form-lesson-setting :uuid="uuid" ref="lessonSetting" v-if="activeSelect === 'lessonSetting'"/>
      <form-org-setting :uuid="uuid" ref="orgSetting" v-if="activeSelect === 'orgSetting'"/>
    </div>
  </el-container>

</template>

<script>
import layoutHeader from './layoutHeader'
import formLessonSetting from '@/views/course/course/layout/formLessonSetting'
import formOrgSetting from '@/views/course/course/layout/formOrgSetting'
import formBaseSetting from '@/views/course/course/layout/formBaseSetting'
import {
    courseDetail,
    publishCourse
} from "@/api/learn/course";

export default {
  name: "FormProcessDesign",
  components: {layoutHeader, formBaseSetting,formLessonSetting,formOrgSetting},
  data() {
    return {
      uuid: this.$route.query.uuid,
      courseStatus: this.$route.query.courseStatus,
      activeSelect: this.$route.query.activeSelect || 'baseSetting',
      isClick: false,
    }
  },
  computed: {
    courseInfo() {
      return this.$store.state.course.courseInfo
    }
  },
  created() {
    this.getData();
  },
  mounted() {

  },
  beforeDestroy() {
  },
  methods: {
    changeType(data) {
      // console.log(data)
      this.activeSelect = data.type
    },
    // 获取课程详情
    getData() {
        const that = this;
        if (that.uuid) {
          courseDetail(that.uuid)
            .then(res => {
              if (res.code === 200) {
                this.$store.dispatch('course/addView', res.data)
              }
            })
            .catch(error => {
              console.error(error);
            });
        }else{
          this.$store.dispatch('course/addView', {
            uuid: '',
            imgPicked: 1,
            title: "",
            categoryId: "",
            categoryTitle: "",
            lectorId: "",
            syncLearn: 'close',
            model: "1",
            eligible: 'all',
            learnProgress: 0,
            learnMode: '1',
            speed: 'close',
            drag: 'close',
            picture: '',
            cdnPicture: '',
            credit: 0,
            certificate: '',
          })
        }
    },
    publishProcess(data) {
      // console.log(data);
      let _self = this;
			// 防止二次点击
			if(this.isClick) {
				return
			}
			this.isClick = true
			publishCourse(data.uuid).then(function (res) {
				if (res.code == 200) {
					_self.isClick = false

					_self.$message({
						showClose: true,
						message: res.msg,
						type: 'success',
					});

          _self.$router.push({
            path: "/course/courseList",
            query: {
              status:"1"
            },
          });
				} else {
					_self.isClick = false
					_self.$message({
						type: 'info',
						message: res.msg
					});
				}
			})
    }
  }
}
</script>

<style lang="scss" scoped>

.layout-body {
  min-width: 980px;
  background-color: #f5f6f6;
}

::v-deep .el-step {
  .is-success {
    color: #2a99ff;
    border-color: #2a99ff;
  }
}

.err-info{
  max-height: 180px;
  overflow-y: auto;
  & > div{
    padding: 5px;
    margin: 2px 0;
    width: 220px;
    text-align: left;
    border-radius: 3px;
    background: rgb(242 242 242);
  }
  i{
    margin: 0 5px;
  }
}

::-webkit-scrollbar {
  width: 2px;
  height: 2px;
  background-color: white;
}

::-webkit-scrollbar-thumb {
  border-radius: 16px;
  background-color: #e8e8e8;
}

</style>
