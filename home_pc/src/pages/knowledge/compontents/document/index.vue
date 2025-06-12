<template>
  <div id="DocumentPlay">
    <div id="document_container"></div>
    <div id="time-tip-top">
      <div class="time-tip-top-time">
        <span>
          <i class="icon iconfont icon-tishi"></i>
          <span>温馨提示：本课件最少阅读时间为{{ timeFormat }}</span>
        </span>
      </div>
      <template v-if="learnProgress == 100">
        <div class="learing-text">已完成</div>
      </template>
      <template v-else>
        <div class="learing-text">学习中： {{ timeShow }}</div>
      </template>
    </div>
  </div>
</template>
<script>
import {
  getCourseLessonsDetail,
  updateCourseLearning,
  getDocumentToken,
} from '@/api/course'

import watermark from '@/assets/js/watermark'

export default {
  name: 'DocumentPlay',
  props: ['courseId', 'isDrag', 'isShow', 'from'],
  data() {
    return {
      coursewareId: this.$route.query.coursewareId,
      stageId: this.$route.query.stageId, // 阶段ID
      instance: null,
      tokenInfo: {},
      learnTime: '', // 学员已学习时间
      documentLength: '', // 文档需要学习时间
      learnProgress: 0, // 学习进度
      timeFormat: '',
      countInter: null, // 计时器
      timeShow: '',
      TimingUpdateProgress: null, // 定时同步进度
      nextCourseware: {}, // 下节课件
      lessonData: {},
      sourceId: '', // 来源id
      waterText: '', // 水印文字
    }
  },
  watch: {
    $route: 'startPlay',
    documentLength(nval) {
      this.documentLength = nval
    },
    learnTime(nval) {
      this.learnTime = nval
    },
    timeShow(nval) {
      this.timeShow = nval
    },
    isShow(val) {
      //   if (this.instance) {
      //   this.instance.destroy()
      // }
      // this.getLessonDetail();
    },
  },
  created() {
    let _self = this
  },
  mounted() {
    if (this.instance) {
      this.instance.destroy()
    }
    if (this.from == 'train') {
      this.sourceId = this.$route.query.trainId
    } else {
      this.sourceId = this.courseId
    }
    this.getLessonDetail()
  },
  methods: {
    startPlay() {
      // 取消定时器
      this.cancelIntervalEvent()
      this.coursewareId = this.$route.query.coursewareId
      if (this.instance) {
        this.instance.destroy()
      }

      this.getLessonDetail()
    },
    // 取消定时器
    cancelIntervalEvent() {
      if (this.countInter) {
        clearInterval(this.countInter)
        this.countInter = null
      }
      if (this.TimingUpdateProgress) {
        clearInterval(this.TimingUpdateProgress)
        this.TimingUpdateProgress = null
      }
    },

    /**
     * 获取课程详情
     */
    getLessonDetail() {
      let _self = this

      getCourseLessonsDetail(_self.coursewareId, {
        from: _self.from,
        sourceId: _self.sourceId,
        courseId: _self.courseId,
        stageId: _self.stageId ? _self.stageId : _self.courseId,
      })
        .then((res) => {
          let data = res.data
          _self.lessonData = data
          _self.nextCourseware = data.nextCourseware ? data.nextCourseware : {}
          _self.learnProgress = data.progress
          _self.learnTime = data.progressBar // 学员已学习时间
          _self.documentLength = data.length // 后台设置文档需要学习时间
          _self.tokenInfo = {
            WebofficeURL: data.transCodeUri,
            AccessToken: data.accessToken,
            RefreshToken: data.refreshToken,
          }
          _self.previewDocument(_self.tokenInfo)

          _self.waterText = window.localStorage.getItem('companyName')
          // 开启水印
          if (_self.waterText) {
            watermark({
              watermark_id: 'DocumentPlay',
              watermark_txt: _self.waterText,
              watermark_fontsize: '14px',
              watermark_width: 140,
              watermark_cols: 6,
            })
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 设置学习时间 倒计时
    async setLearnTime() {
      let _self = this
      // 设置最短学习时间提示
      _self.timeFormat = _self.$_courseCountDown(_self.documentLength)
      // 开始计时(课程未完成时才进行计时)
      if (_self.learnProgress == 100) {
        _self.timeShow = _self.$_courseCountDown(_self.documentLength, 'count')
      } else {
        if (parseInt(_self.learnTime) < parseInt(_self.documentLength)) {
          // 取消定时器
          _self.cancelIntervalEvent()
          _self.countInter = setInterval(() => {
            _self.learnTime++
            // 判断是否达完成学习
            if (parseInt(_self.learnTime) >= parseInt(_self.documentLength)) {
              // 取消定时器
              _self.cancelIntervalEvent()
              _self.updateCourseLearning()
            }
            _self.timeShow = _self.$_courseCountDown(_self.learnTime, 'count')
          }, 1000)
          // 定时同步进度（30s一次）
          _self.TimingUpdateProgress = setInterval(() => {
            _self.updateCourseLearning()
          }, 30000)
        } else {
          _self.timeShow = _self.$_courseCountDown(
            _self.documentLength,
            'count'
          )
          _self.updateCourseLearning()
        }
      }
    },
    // 预览
    async previewDocument(tokenInfo) {
      let mount = document.querySelector('#document_container')
      this.instance = aliyun.config({
        mount,
        url:
          tokenInfo.PreviewURL || tokenInfo.EditURL || tokenInfo.WebofficeURL,
        mode: 'simple',
        refreshToken: this.refreshToken(),
      })
      this.instance.setToken({
        token: tokenInfo.AccessToken,
        timeout: 25 * 60 * 1000,
      })

      this.instance.on('fileOpen', function (data) {
        // console.log(data)
      })

      this.instance.on('error', (err) => {
        console.log('发生错误：', err)
      })
      //等待instance ready之后再显示课程倒计时
      await this.instance.ready()
      console.log('加载完成')
      await this.setLearnTime()
    },
    // 重新获取token
    refreshToken() {
      let _self = this
      let lastTokenInfo = _self.tokenInfo
      return new Promise(function (resolve) {
        //业务处理逻辑，调用服务端封装的refreshToken接口。
        getDocumentToken({
          refreshToken: lastTokenInfo.RefreshToken,
          accessToken: lastTokenInfo.AccessToken,
        })
          .then((res) => {
            let data = res.data
            _self.tokenInfo = {
              WebofficeURL: lastTokenInfo.WebofficeURL,
              AccessToken: data.accessToken,
              RefreshToken: data.refreshToken,
            }
            lastTokenInfo = _self.tokenInfo
            resolve({
              token: _self.tokenInfo.AccessToken, //必须设置。
              timeout: 25 * 60 * 1000, // 必须设置，Token超时时间，单位为ms，示例设置超时时间为25分钟。可配合refreshToken配置函数使用，在超时前5分钟调用refreshToken重新刷新Token。
            })
          })
          .catch((err) => {
            console.log(err)
            getCourseLessonsDetail(_self.coursewareId, {
              from: _self.from,
              sourceId: _self.sourceId,
              courseId: _self.courseId,
              stageId: _self.stageId ? _self.stageId : _self.courseId,
            }).then((res) => {
              let data = res.data
              _self.tokenInfo = {
                WebofficeURL: data.transCodeUri,
                AccessToken: data.accessToken,
                RefreshToken: data.refreshToken,
              }
              lastTokenInfo = _self.tokenInfo
              resolve({
                token: _self.tokenInfo.AccessToken, //必须设置。
                timeout: 25 * 60 * 1000, // 必须设置，Token超时时间，单位为ms，示例设置超时时间为25分钟。可配合refreshToken配置函数使用，在超时前5分钟调用refreshToken重新刷新Token。
              })
            })
          })
      })
    },
    // 记录进度
    updateCourseLearning() {
      updateCourseLearning({
        coursewareId: this.coursewareId,
        progressBar: Number(this.learnTime).toFixed(2),
        from: this.from,
        sourceId: this.sourceId,
        courseId: this.courseId,
        stageId: this.stageId ? this.stageId : this.courseId,
      })
        .then((res) => {
          if (res.data.progress == '100' && res.data.open) {
            let lessonInfo = {}

            if (this.nextCourseware.uuid) {
              lessonInfo = {
                title: this.nextCourseware.title,
                courseId: this.nextCourseware.courseId,
                uuid: this.nextCourseware.uuid,
                coursewareType: this.nextCourseware.type,
              }
            } else {
              lessonInfo = {
                uuid: '',
              }
            }
            this.$emit('nextLessonPlay', lessonInfo)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },

  beforeRouteUpdate(to, from, next) {
    // 取消定时器
    this.cancelIntervalEvent()
    this.updateCourseLearning()
    next()
  },
  // 监听组件销毁之前（可在此回调记录时间）
  beforeDestroy() {
    console.log('销毁')
    if (this.instance) {
      this.instance.destroy()
    }
    // 取消定时器
    this.cancelIntervalEvent()
    this.updateCourseLearning()
  },
}
</script>
<style lang="less">
#DocumentPlay {
  width: 100%;
  overflow: hidden;
  height: 100%;
  background: #fff;
  box-sizing: border-box;
  position: relative;
  padding-top: 65px;
  box-sizing: border-box;

  #document_container {
    width: 100%;
    height: 100%;
  }

  #time-tip-top {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 65px;
    overflow: hidden;
    background-color: rgba(0, 0, 0, 0.4);
    pointer-events: none;

    .time-tip-top-time {
      padding-left: 30px;
      line-height: 65px;
      font-size: 18px;
      color: #fff;
      transition: 0.3s all;

      .iconfont {
        font-size: 18px;
        margin-right: 4px;
      }
    }

    @media screen and (max-width: 1300px) {
      .time-tip-top-time {
        padding-left: 15px;
        font-size: 14px;
      }
    }
  }

  .learing-text {
    position: absolute;
    right: 30px;
    top: 17px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    font-size: 16px;
    color: #5881db;
    font-weight: bold;
  }

  @media screen and (max-width: 1300px) {
    .learing-text {
      font-size: 14px;
    }
  }
}
</style>
