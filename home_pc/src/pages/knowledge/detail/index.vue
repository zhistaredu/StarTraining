<template>
  <div class="course-detail" v-loading="isLoading">
    <div class="course-top"></div>
    <div class="learning-top cont_all_width">
      <div class="cover-img">
        <img :src="courseDetail.cdnPicture" alt="" />
      </div>
      <div class="course-data">
        <!-- 课程标题 -->
        <div class="course-title">
          <span class="tit">{{ courseDetail.title }}</span>
        </div>
        <div class="lector">
          <span>讲师：</span>
          <div v-if="courseDetail.lectorName">
            <OpenDataCom v-if="courseDetail.lectorName" type="userName" :openid="courseDetail.lectorName"
              :defaultname="courseDetail.lectorName"></OpenDataCom>
          </div>
          <div v-else>无</div>
        </div>
        <template v-if="courseDetail.eligible=='all'">
        <div class="pub-time" v-if="courseDetail.credit > 0 && courseDetail.certificateTitle">学习完成可获得<span>{{ courseDetail.credit }}</span>学分和证书<span>《{{
        courseDetail.certificateTitle }}》</span></div>
        <div class="pub-time" v-if="courseDetail.credit > 0 && !courseDetail.certificateTitle">学习完成可获得<span>{{ courseDetail.credit }}</span>学分</div>
        <div class="pub-time" v-if="!courseDetail.credit && courseDetail.certificateTitle">学习完成可获得证书<span>《{{
        courseDetail.certificateTitle }}》</span></div>
      </template>
      <template v-else>
        <div class="pub-time" v-if="courseDetail.credit > 0 && courseDetail.certificateTitle">完成学习进度{{courseDetail.learnProgress}}%可获得<span>{{ courseDetail.credit }}</span>学分和证书<span>《{{
        courseDetail.certificateTitle }}》</span></div>
        <div class="pub-time" v-if="courseDetail.credit > 0 && !courseDetail.certificateTitle">完成学习进度{{courseDetail.learnProgress}}%可获得<span>{{ courseDetail.credit }}</span>学分</div>
        <div class="pub-time" v-if="!courseDetail.credit && courseDetail.certificateTitle">完成学习进度{{courseDetail.learnProgress}}%可获得证书<span>《{{
        courseDetail.certificateTitle }}》</span></div>
      </template>
        <!-- <div class="pub-time" v-if="courseDetail.credit > 0">学分：{{ courseDetail.credit }}</div>
        <div class="certificate" v-if="courseDetail.certificateTitle">
          <span>获得证书：</span>
          <span @click="checkCertificate(courseDetail.certificateUrl)" style="color: #5881db; cursor: pointer;">《{{
    courseDetail.certificateTitle }}》</span>
        </div> -->
        <!-- <div class="pub-time">发布时间：{{ courseDetail.publishTime }}</div> -->
        <!-- 已完成 -->
        <div class="btn-box" v-if="courseInfo.learnStatus == 'finished'">
          <span class="btn">
            已完成
          </span>
        </div>
        <template v-else>
          <!-- 继续学习 -->
          <div class="btn-box progress" v-if="courseInfo.progress > 0">
            <div class="progress-cont">
              <span>进度:</span>
              <div class="line">
                <el-progress :percentage="courseInfo.progress" color="#5881db"></el-progress>
              </div>
            </div>
            <span @click="clickPlayBtn" class="btn"> 继续学习 </span>
          </div>
          <!-- 开始学习 -->
          <div class="btn-box" v-else>
            <span @click="clickPlayBtn" class="btn"> 开始学习 </span>
          </div>
        </template>

      </div>
    </div>

    <div class="course-detail-data cont_all_width">
      <!-- 课件列表： -->
      <div class="course-lesson-list">
        <div class="tit">课件列表：</div>
        <ul class="lesson-list">
          <li v-for="(list, index) in coursewareList" :key="list.id" :class="{
    'learning-lesson':
      lastLearnData && lastLearnData.uuid === list.uuid,
  }" @click="courseLearn(list, index)">
            <div class="type">
              <i
                class="iconfont icon-shipin1"
                style="color: #ff4d4f"
                v-if="list.type == 'video'"
              ></i>
              <template v-else-if="list.type == 'document'">
                <i
                  v-if="list.fileType == 'txt'"
                  class="iconfont icon-txt"
                  style="color: #4c98fc;"
                ></i>
                <i
                  v-else-if="
                    list.fileType == 'xls' || list.fileType == 'xlsx'
                  "
                  class="iconfont icon-xlsx"
                  style="color: #039e55;"
                ></i>
                <i
                  v-else-if="list.fileType == 'wps'"
                  class="iconfont icon-meeting-icon-WPS"
                  style="color: #1b90ff"
                ></i>
                <i
                  v-else-if="
                    list.fileType == 'ppt' || list.fileType == 'pptx'
                  "
                  class="iconfont icon-ppt"
                  style="color: #f97f11"
                ></i>
                <i
                  v-else-if="list.fileType == 'pdf'"
                  class="iconfont icon-PDF"
                  style="color: #dd315b"
                ></i>
                <i
                  v-else
                  class="iconfont icon-icon-doc"
                  style="color: #1b90ff"
                ></i>
              </template>
            </div>
            <div class="cont">
              <div class="top">
                <p class="title">{{ list.title }}</p>
              </div>
              <div class="bottom">
                <div class="tab">时长：{{ list.length | timeLong }}</div>

                <div class="progress-box">
                  <div class="progress-line">
                    <el-progress :percentage="list.progress" color="#5881db" :show-text="false"></el-progress>
                  </div>
                  <span v-if="list.progress == 100" class="progressa">
                    已完成
                  </span>
                  <span v-else class="progress">进度：{{ list.progress }} %</span>
                </div>
              </div>
            </div>
            <!-- 是否是解锁式学习 -->
            <div class="suo" v-if="courseDetail.learnMode == 'unlock' &&
    !list.isUnlocked &&
    index > coursewareIndex
    ">
              <i class="iconfont icon-suo4"></i>
            </div>
          </li>
        </ul>
      </div>
      <!-- 热门知识 -->
      <div class="tui-list">
        <div class="tit">热门课程</div>
        <div class="course-list">
          <div class="course-item" v-for="(item, index) in courseList" :key="index" @click="gotoDetail(item)">
            <div class="img">
              <img :src="item.imgUrl" alt="" />
            </div>
            <div class="course-cont">
              <div class="name">{{ item.title }}</div>
              <div class="people">
                <i class="iconfont icon-tuandui"></i>
                <span>{{ item.studentNum }}人学习</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 证书预览 -->
    <div class="certificate-preview" @click.capture.self="isShowCertificate = false" v-if="isShowCertificate">
      <div class="certificate-cont">
        <div class="certificate-top">
          <span>证书预览</span>
          <i class="iconfont icon-chahao" @click="isShowCertificate = false"></i>
        </div>
        <div class="certificate-img">
          <img :src="courseDetail.certificateUrl" alt="">
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import { getCourseDetails, getCourseLessons, joinXuanLearn } from '@/api/course'
import { getRemendCourse } from '@/api/index'
export default {
  name: 'knowledgeDetail',
  components: {  },
  data() {
    return {
      courseList: [],
      courseId: this.$route.query.uuid,
      companyId: window.localStorage.getItem('companyId'),
      courseDetail: {}, // 课程详情
      courseInfo: {}, // 学员学习情况
      coursewareList: [], // 课件列表
      isDrag: 'close', // 课件是否可拖动
      doubleSpeed: 'close', // 是否开启倍速
      lastLearnData: {}, // 上节学习的课件
      coursewareIndex: 0, // 当前课件学习的序列号
      isLoading: true, // 加载中
      isShowCertificate: false, // 证书预览
    }
  },
  computed: {},
  mounted() {
    this.getCourseList()
    // 获取课程详情u
    this.getCourseDetail(() => {
      this.getLessonList()
    })
  },
  methods: {
    checkCertificate(url) {
      this.isShowCertificate = true
    },
    // 获取推荐课程
    getCourseList() {
      getRemendCourse({
        isLearned: 2,
        action: 'new',
        pageNum: 1,
        pageSize: 5,
      })
        .then((res) => {
          if (res.rows.length > 5) {
            this.courseList = res.rows.slice(0, 5)
          } else {
            this.courseList = res.rows
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 推荐课程前往学习
    gotoDetail(item) {
      this.$router
        .push({
          name: 'knowledgeDetail',
          query: {
            uuid: item.uuid,
          },
        })
        .catch((err) => err)

      this.courseId = item.uuid
      this.coursewareList = []
      this.courseInfo = {}
      this.courseDetail = {}
      // 获取课程详情
      this.isLoading = true
      this.getCourseDetail(() => {
        this.getLessonList()
      })
    },

    // 跳转学习页面
    startLearn(index) {
      const _index = index ? index : 0
      // console.log(this.coursewareType)
      this.$router.push({
        name: 'learnKnowledge',
        query: {
          uuid: this.courseId,
          coursewareType: this.coursewareType,
          coursewareId: this.coursewareId,
          coursewareIndex: _index,
          isDrag: this.isDrag,
          doubleSpeed: this.doubleSpeed,
          learnMode: this.courseDetail.learnMode,
          from: 'course'
        },
      })
    },

    // 获取课程详情
    getCourseDetail(callback) {
      new Promise((reslove) => {
        getCourseDetails(this.courseId)
          .then((res) => {
            // 课程详情
            this.courseDetail = res.info
            this.isLoading = false
            // 学员学习情况
            this.courseInfo = res.courseUser ? res.courseUser : {}

            reslove()
          })
          .catch((err) => {
            // console.log(err)
            setTimeout(() => {
              this.$router.replace({
                path: '/home',
              })
            }, 1000)
          })
      })
        .then(() => {
          if (callback && typeof callback === 'function') {
            callback()
          }

          // 是否允许首次拖动
          this.isDrag = this.courseDetail.drag
          this.doubleSpeed = this.courseDetail.speed
          // 上次学习课程
          if (this.courseInfo) {
            this.lastLearnData = this.courseInfo.lastLearnData
              ? this.courseInfo.lastLearnData
              : {}
          } else {
            this.lastLearnData = {}
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 加载课件列表
    getLessonList() {
      let _lessonList = []
      new Promise((reslove) => {
        getCourseLessons({
          courseId: this.courseId,
          from: 'course',
          sourceId: this.courseId,
        })
          .then((res) => {
            this.isLoading = false
            _lessonList = res.data
            // 获取当前课件的序列号
            this.getLessonIndex()
            this.coursewareList = _lessonList
            reslove()
          })
          .catch((err) => {
            console.log(err)
          })
      })
        .then(() => { })
        .catch((err) => {
          console.error(err)
        })
    },
    // 获取当前课件学习的序列号
    getLessonIndex() {
      if (
        Object.keys(this.coursewareList).length &&
        Object.keys(this.lastLearnData).length
      ) {
        this.coursewareList.forEach((lesson, index) => {
          if (lesson.uuid === this.lastLearnData.uuid) {
            this.coursewareIndex = index
          }
        })
      }
    },

    // 开始学习按钮
    clickPlayBtn() {

      let index
      if (this.lastLearnData && this.lastLearnData.uuid) {
        // console.log('111')
        this.coursewareId = this.lastLearnData.uuid
        this.coursewareType = this.lastLearnData.type
        index = this.lastLearnData.weight - 1
      } else if (this.coursewareList.length > 0) {
        // console.log('000')
        this.coursewareIndex = 0
        index = 0
        let firstLesson = this.coursewareList[0]
        this.coursewareId = firstLesson.uuid
        this.coursewareType = firstLesson.type
      }
      setTimeout(() => {
        this.startLearn(index)
      }, 500)

    },

    // 跳转播放
    gotoJump(index) {
      if (Number(index) >= 0) {
        this.lastLearnData = this.coursewareList[index]
      }

      if (this.lastLearnData && this.lastLearnData.uuid) {
        this.coursewareId = this.lastLearnData.uuid
        this.coursewareType = this.lastLearnData.type
      } else if (this.coursewareList.length > 0) {
        let num = index ? index : 0
        let firstLesson = this.coursewareList[num]
        this.coursewareId = firstLesson.uuid
        this.coursewareType = firstLesson.type

        if (this.coursewareIndex == 0) {
          this.coursewareIndex = num
        }
      }

      this.startLearn(index)
    },

    /**
     * 点击课件进行学习
     * @param {object} list 课件数据
     * @param {number} index 课件序列号
     */
    courseLearn(list, index) {
      let oldIndex = this.coursewareIndex
      this.coursewareId = list.uuid
      this.coursewareType = list.type
      this.coursewareIndex = index

      if (this.courseDetail.learnMode == 'unlock') {
        // 解锁式学习
        if (list.isUnlocked) {
          this.gotoJump(index)
        } else {
          this.coursewareIndex = oldIndex
          this.$message({
            message: '请按顺序学习',
            type: 'warning',
          })
        }
      } else {
        this.gotoJump(index)
      }
    },
  },
}
</script>

<style scoped lang="less">
@import 'index';
</style>
