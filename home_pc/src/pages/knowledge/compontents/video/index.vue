<template>
  <div class="video-play-box" style="width: 100%; height: 100%">
    <div id="video-wrap-player" v-if="mediaUri">
      <video-player
        class="video-player vjs-custom-skin"
        ref="videoPlayer"
        :options="playerOptions"
        :playsinline="true"
        @ready="onPlayerReady"
        @play="onPlayerPlay"
        @timeupdate="onPlayerTimeupdate"
        @pause="onPlayerPause"
        @ended="onPlayerEnded"
      >
      </video-player>
    </div>
    <div class="watermark">{{ waterText }}</div>
  </div>
</template>

<script>
import { getCourseLessonsDetail, updateCourseLearning } from '@/api/course'

export default {
  name: 'videoboPlayer',
  props: ['courseId', 'isDrag', 'doubleSpeed', 'from'],
  data() {
    return {
      coursewareId: this.$route.query.coursewareId,
      stageId: this.$route.query.stageId, // 阶段ID
      isPaused: '', // 视频暂停
      canDrag: false, // 是否可拖动 只有首次不可拖动
      currentTime: 0, // 当前学习位置
      nowCurrentTime: '', // 视频当前学习位置
      learnProgress: 0, // 学习进度
      TimingUpdateProgress: {}, // 定时更新进度计时器
      videoTotalLength: 0, // 视频总时长
      player: null, // 视频对象
      isEnded: false, // 是否触发视频end事件
      isUpdateing: false, // 更新课件进度接口请求中
      waterText: '', // 水印文字
      sourceId: '', // 来源id
      nextCourseware: {}, // 下一课件的内容
      lessonData: {},
      mediaUri: '',
    }
  },
  components: {},
  computed: {},
  watch: {
    $route: function () {
      if (!this.isStart) {
        this.getVideoDetail()
      }
    },
    // 视频暂停更新进度
    isPaused(val) {
      if(val) {
        this.updateLearningTime(this.currentTime)
      }
    },
  },
  mounted() {
    let _self = this
    // 水印
    _self.waterText = window.localStorage.getItem('companyName')
    if (this.from == 'train') {
      this.sourceId = this.$route.query.trainId
    } else {
      this.sourceId = this.courseId
    }
    // 禁用鼠标右键
    document.querySelector('.course-learn-body').onselectstart =
      document.querySelector('.course-learn-body').oncontextmenu = function () {
        return false
      }
    function KeyDown() {
      // 屏蔽鼠标右键、Ctrl+n、shift+F10、F5刷新、退格键
      if (
        window.event.altKey &&
        (window.event.keyCode == 37 || window.event.keyCode == 39)
      ) {
        // 屏蔽 Alt+ 方向键 →
        event.returnValue = false
      }
    }
    document.onkeydown = KeyDown
    _self.getVideoDetail()
  },
  beforeDestroy() {
    var _self = this
    _self.updateLearningTime(_self.currentTime)
    // 清除视频30秒记录定时器
    clearInterval(_self.TimingUpdateProgress)

  },
  destroyed() {},
  methods: {
    onPlayerReady(player) {
      let _self = this
      _self.isStart = false
      console.log('Player is ready!', player)
      $('.vjs-control-bar').children().addClass('needsclick')
      if (_self.isDrag == 'close' && !_self.canDrag) {
        // 禁止拖动
        document.querySelector('.vjs-progress-control').style.pointerEvents =
          'none'

        console.log('禁止拖动')
      } else {
        document.querySelector('.vjs-progress-control').style.pointerEvents =
          'auto'

        console.log('可以拖动')
      }
      // 设置初始播放位置
      player.currentTime(_self.currentTime)
    },
    onPlayerPlay(player) {
      console.log('开始播放')
      // 设置初始播放位置
      player.currentTime(this.currentTime)
      this.isPaused = false
      this.isEnded = false

      // 定时同步进度（30s一次）
      this.TimingUpdateProgress = setInterval(() => {
        this.updateLearningTime(this.currentTime)
      }, 30000)
    },
    onPlayerTimeupdate(player) {
      // console.log('播放中', this.nowCurrentTime)

      this.nowCurrentTime = player.currentTime().toFixed(2)
      this.currentTime = this.nowCurrentTime

      // 暂停时传递进度
      if (this.isPaused) {
        this.currentTime = this.nowCurrentTime
      }
    },
    onPlayerPause(player) {
      console.log('暂停播放')
      // 防止拖动产生的pause事件重复发送请求
      if (!this.isPaused) {
        clearInterval(this.TimingUpdateProgress)
        this.currentTime = player.currentTime().toFixed(2)
        this.isPaused = true
      }
    },
    onPlayerEnded(player) {
      // 添加isEnded判断，防止重复
      if (this.isEnded) {
        return
      }
      // 结束播放时自动推出全屏
      if (player.isFullscreen()) {
        player.exitFullscreen()
      }

      clearInterval(this.TimingUpdateProgress)

      // 播放完毕，设置进度条拖动状态
      this.canDrag = true
      this.isEnded = true
      this.isPaused = true
    },
    // 视频 end
    // 获取视频详情
    async getVideoDetail() {
      let _self = this
      _self.isStart = true
      _self.coursewareId = _self.$route.query.coursewareId
      await getCourseLessonsDetail(_self.coursewareId, {
        from: _self.from,
        sourceId: _self.sourceId,
        courseId: _self.courseId,
        stageId: _self.stageId ? _self.stageId : _self.courseId,
      })
        .then(function (res) {
          let data = res.data
          _self.lessonData = data
          _self.nextCourseware = data.nextCourseware ? data.nextCourseware : {}

          if (data.convertStatus == 1) {
            let playbackRates = []
            if (_self.doubleSpeed == 'open') {
              // 开启倍速
              playbackRates = [0.5, 1.0, 1.5, 2.0]
            } else {
              // 不开启倍速
              playbackRates = []
            }
            _self.playerOptions = {
              //视频url设置,直播流为例
              sources: [
                {
                  src: _self.lessonData.transCodeUri, //视频文件地址
                  type: 'application/x-mpegURL', //视频类型，这里可以不写，如果写一定要写对，否则会无法播放
                },
              ],
              playbackRates: playbackRates,
              fluid: false,
              autoplay: false,
              muted: false,
              inactivityTimeout: 2000,
              controls: true,
              responsive: true,
            }
            _self.mediaUri = _self.lessonData.transCodeUri
            let currentTime = _self.lessonData.progressBar
              ? _self.lessonData.progressBar
              : 0
            _self.currentTime = currentTime
            _self.videoTotalLength = _self.lessonData.length
            _self.learnProgress = _self.lessonData.progress
            if (_self.learnProgress == 100) {
              _self.canDrag = true
              if (Number(_self.currentTime) >= Number(_self.videoTotalLength)) {
                // 如果已完成，且当前位置大于总时长，定位到0
                _self.currentTime = 0
              }
            } else {
              _self.canDrag = false
            }
            _self.currentTime = Math.round(_self.currentTime)
          } else {
            // 转码失败
            _self.$alert('视频文件正在转换中，稍后完成后即可查看', {
              confirmButtonText: '确定',
              callback: (action) => {
                _self.$router.replace({
                  name: 'knowledgeDetail',
                  query: {
                    uuid: _self.courseId,
                  },
                })
              },
            })
            return false
          }
        })
        .catch((err) => {
          console.log(err)
          return
        })
    },

    // 更新课件播放时间公共方法(nowPlayTime:当前播放的时间)
    updateLearningTime(nowPlayTime) {
      let _self = this
      if (_self.isUpdateing) {
        return
      }
      _self.isUpdateing = true
      updateCourseLearning({
        coursewareId: _self.coursewareId,
        progressBar: Number(nowPlayTime).toFixed(2),
        from: _self.from,
        sourceId: _self.sourceId,
        courseId: _self.courseId,
        stageId: _self.stageId ? _self.stageId : _self.courseId,
      })
        .then((res) => {
          setTimeout(() => {
            _self.isUpdateing = false
          }, 1000);

          if (res.data.progress == '100' && res.data.open) {
            // 已完成
            let lessonInfo = {}
            if (_self.nextCourseware.uuid) {
              lessonInfo = {
                title: _self.nextCourseware.title,
                courseId: _self.nextCourseware.courseId,
                uuid: _self.nextCourseware.uuid,
                coursewareType: _self.nextCourseware.type,
              }
            } else {
              lessonInfo = {
                uuid: '',
              }
            }
            _self.$emit('nextLessonPlay', lessonInfo)
          }
        })
        .catch((err) => {
          _self.isUpdateing = false
        })
    },


  },
}
</script>

<style src="./video.less" lang="less"></style>

<style lang="less">
.vjs-watermark {
  color: #fff;
  text-shadow: #000 2px 2px 2px;
  letter-spacing: 3px;
  position: relative;
  width: 200px;
  display: block;
  padding-left: 40px;
  box-sizing: border-box;
  text-align: center;
  white-space: nowrap;
  line-height: 30px;

  img {
    width: 30px;
    height: 30px;
    position: absolute;
    top: 0;
    left: 0;
  }
}
</style>
