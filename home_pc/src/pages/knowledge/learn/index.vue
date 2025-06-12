<template>
  <div
    class="course-lesson-learn"
    :style="{
      width: windowWidth + 'px',
      height: windowHeight + 'px',
      left: domLeft + 'px',
    }"
  >
    <div class="title">
      <div class="back" @click="goback" v-if="from == 'train'">
        <i class="iconfont icon-fanhui"></i>返回培训
      </div>
      <div class="back" @click="goback" v-else>
        <i class="iconfont icon-fanhui"></i>返回课程
      </div>
      <div class="title-name" v-if="coursewareList.length">
        当前课件：{{ coursewareList[coursewareIndex].title }}
      </div>

      <!-- <div class="close" @click="changeShow">
        <i class="iconfont icon-shouqi" v-if="isShow"></i>
        <i class="iconfont icon-zhankai" v-else></i>
      </div> -->
    </div>
    <div class="course-learn-body">
      <!-- 播放器  学习 -->
      <div class="course-learn-player" v-if="playerName">
        <component
          ref="normalChild"
          :is="playerName"
          :courseId="courseId"
          :isDrag="isDrag"
          :doubleSpeed="doubleSpeed"
          :isShow="isShow"
          :from="from"
          @nextLessonPlay="nextLessonPlay"
        ></component>
      </div>
      <div class="lesson-list-box" :class="{ active: isShow }">
        <div class="course-lesson-list">
          <div class="tit">课件列表：</div>
          <ul class="lesson-list">
            <li
              v-for="(list, index) in coursewareList"
              :key="list.id"
              :class="{
                'learning-lesson': coursewareId === list.uuid,
              }"
              @click="clickLesson(list, index)"
            >
              <div class="cont">
                <div class="top">
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
                        style="color: #4c98fc"
                      ></i>
                      <i
                        v-else-if="
                          list.fileType == 'xls' || list.fileType == 'xlsx'
                        "
                        class="iconfont icon-xlsx"
                        style="color: #039e55"
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
                  <div class="title">{{ list.title }}</div>
                </div>
                <div class="bottom">
                  <div class="tab">时长：{{ list.length | timeLong }}</div>

                  <span v-if="list.progress == 100" class="progressa">
                    已完成
                  </span>
                  <span v-else-if="list.progress > 0" class="progress"
                    >学习中</span
                  >
                </div>
              </div>
              <div
                class="suo"
                v-if="
                  learnMode == 'unlock' &&
                  !list.isUnlocked &&
                  index > coursewareIndex
                "
              >
                <i class="iconfont icon-suo4"></i>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCourseLessons, getCourseLessonsDetail } from '@/api/course'
import documentPlayer from '../compontents/document/index'
import videoboPlayer from '../compontents/video/index'
export default {
  name: 'learnKnowledge',
  components: {
    documentPlayer,
    videoboPlayer,
  },
  data() {
    return {
      from: this.$route.query.from, // p培训任务还是课程
      trainId: this.$route.query.trainId,
      stageId: this.$route.query.stageId, // 阶段ID
      courseId: this.$route.query.uuid,
      coursewareId: this.$route.query.coursewareId, // 课件id
      coursewareIndex: Number(this.$route.query.coursewareIndex), // 课件序号
      coursewareType: this.$route.query.coursewareType, // 课件类型
      isDrag: this.$route.query.isDrag, // 课件是否可拖动
      doubleSpeed: this.$route.query.doubleSpeed, // 课件是否开启倍速
      learnMode: this.$route.query.learnMode, // 学习类型 解锁 or 不解锁
      companyId: window.localStorage.getItem('companyId'),
      coursewareList: [], // 课件列表
      playerName: null, // 播放组件的名称
      // 课件数据
      lessonData: null,
      isShow: true, // 是否显示课件列表
      windowWidth: '',
      windowHeight: '',
      domLeft: '',
      sourceId: '', // 来源ID
    }
  },
  computed: {},
  created() {
    if (this.from == 'train') {
      this.sourceId = this.$route.query.trainId
    } else {
      this.sourceId = this.$route.query.uuid
    }
  },
  mounted() {
    if (window.innerWidth < 1301) {
      this.windowWidth = window.innerWidth - 60
      this.windowHeight = window.innerHeight - 110
      this.domLeft = 30
    } else {
      this.windowWidth = window.innerWidth - 200
      this.windowHeight = window.innerHeight - 110
      this.domLeft = 100
    }
    // 获取浏览器宽度
    window.onresize = () => {
      return (() => {
        // _this.screenWidth = document.body.clientWidth
        if (window.innerWidth < 1301) {
          this.windowWidth = window.innerWidth - 60
          this.windowHeight = window.innerHeight - 110
          this.domLeft = 30
        } else {
          this.windowWidth = window.innerWidth - 200
          this.windowHeight = window.innerHeight - 110
          this.domLeft = 100
        }

        // console.log(this.windowWidth, '宽度')
      })()
    }

    this.getLessonList(() => {
      this.startPlay(this.coursewareIndex, 'first')
    })
  },
  methods: {
    changeShow() {
      this.isShow = !this.isShow
    },
    // 返回课程详情页
    goback() {
      if (this.from == 'train') {
        this.$router.replace({
          name: 'trainDetail',
          query: {
            uuid: this.$route.query.trainId,
          },
        })
      } else {
        this.$router.replace({
          name: 'knowledgeDetail',
          query: {
            uuid: this.$route.query.uuid,
          },
        })
      }
    },
    // 点击课件进行学习
    clickLesson(list, index) {
      let oldIndex = this.coursewareIndex
      this.coursewareId = list.uuid
      this.coursewareType = list.type
      this.coursewareIndex = index

      if (this.learnMode == 'unlock') {
        // 解锁式学习
        if (list.isUnlocked) {
          this.startPlay(this.coursewareIndex)
        } else {
          this.coursewareIndex = oldIndex
          this.$message({
            message: '请按顺序学习',
            type: 'warning',
          })
        }
      } else {
        this.startPlay(this.coursewareIndex)
      }
    },
    // 开始播放
    startPlay(index, first) {
      this.playerName = ''
      const _index = index ? index : 0
      // console.log(this.coursewareType)
      if (this.coursewareType === 'document') {
        // 文档课程
        this.playerName = 'documentPlayer'
      } else if (this.coursewareType === 'video') {
        // 视频课程
        this.playerName = 'videoboPlayer'
      }
      if (!first) {
        this.$router
          .replace({
            name: 'learnKnowledge',
            query: {
              uuid: this.courseId,
              coursewareType: this.coursewareType,
              coursewareId: this.coursewareId,
              coursewareIndex: _index,
              isDrag: this.isDrag,
              doubleSpeed: this.doubleSpeed,
              learnMode: this.learnMode,
              from: this.from,
              trainId: this.trainId,
              stageId: this.stageId,
            },
          })
          .catch((err) => err)
      }
      // console.log(this.playerName)
    },

    // 播放下一课件
    nextLessonPlay(nextCourseware) {
      // console.log(nextCourseware)
      this.getLessonList()
      if (nextCourseware && nextCourseware.uuid == '') {
        // 提示，完成了本课时的学习
        this.$alert('恭喜您，完成了本课时的学习！', '温馨提示', {
          confirmButtonText: '确定',
          callback: (action) => {
            if (this.from == 'train') {
              this.$router.replace({
                name: 'trainDetail',
                query: {
                  uuid: this.trainId,
                },
              })
            } else {
              this.$router.push({
                name: 'knowledgeDetail',
                query: {
                  uuid: this.courseId,
                },
              })
            }
          },
        })
      } else {
        this.isfinished = 'finished'
        // 已完成本课件，提示下一课件的名字
        if (this.coursewareType == 'document') {
          this.$confirm(
            '您已经完成了本课件的最短学习时间，是否继续学习本课件？',
            '温馨提示',
            {
              confirmButtonText: '下一课',
              cancelButtonText: '继续学习',
              type: 'warning',
              center: true,
            }
          )
            .then(() => {
              this.courseId = nextCourseware.courseId
              this.coursewareId = nextCourseware.uuid
              this.coursewareType = nextCourseware.coursewareType
              this.coursewareIndex = this.coursewareIndex + 1
              // console.log(this.coursewareIndex)
              this.startPlay(this.coursewareIndex)
            })
            .catch(() => {})
        } else {
          this.$confirm(
            `恭喜您，完成了本课时的学习！【下一课:${nextCourseware.title}】`,
            '温馨提示',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
              center: true,
            }
          )
            .then(() => {
              this.courseId = nextCourseware.courseId
              this.coursewareId = nextCourseware.uuid
              this.coursewareType = nextCourseware.coursewareType
              this.coursewareIndex = this.coursewareIndex + 1
              this.startPlay(this.coursewareIndex)
            })
            .catch(() => {})
        }
      }
    },

    // 加载课件列表
    getLessonList(callback) {
      let _lessonList = []
      new Promise((reslove) => {
        getCourseLessons({
          courseId: this.courseId,
          from: this.from,
          sourceId: this.sourceId,
        })
          .then((res) => {
            _lessonList = res.data
            this.coursewareList = _lessonList
            reslove()
          })
          .catch((err) => {
            console.log(err)
          })
      })
        .then(() => {
          if (callback && typeof callback === 'function') {
            callback()
          }
        })
        .catch((err) => {
          console.error(err)
        })
    },
  },
}
</script>

<style scoped lang="less">
@import 'index';
</style>
