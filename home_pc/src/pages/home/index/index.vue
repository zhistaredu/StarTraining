<template>
  <div class="home_index_cont">
    <div class="banner">
      <div class="banner_left">
        <swiper :options="swiperOption" ref="mySwiper">
          <template>
            <swiper-slide v-for="(item, index) in bannerList" :key="index">
              <img :src="item.cdnImage" alt="" @click="goPage(item)" />
            </swiper-slide>
          </template>
        </swiper>
        <div class="swiper-pagination"></div>
      </div>
      <div class="banner_right">
        <div class="user_data_cont">
          <div class="user-data">
            <div class="avatar">
              <img :src="userInfo.avatar" alt="" class="user-avatar" />
            </div>
            <OpenDataCom
              v-if="userInfo.userName"
              type="userName"
              :openid="userInfo.userName"
              :defaultname="userInfo.userName"
            ></OpenDataCom>
            <div class="compony">{{ userInfo.companyName }}</div>
          </div>
          <div class="top-block-time">
            <div class="item item1">
              <div class="num">{{ todayHour }}</div>
              <div class="title">今日学时</div>
            </div>
            <div class="item item2">
              <div class="num">{{ totalHour }}</div>
              <div class="title">累计学时</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 知识中心 -->
    <div class="my-course">
      <div class="common-title">
        <div class="title">知识中心</div>
        <div class="more" @click="checkMoreCourse">
          <span>全部</span>
          <i class="iconfont icon-xiangyoujiantou"></i>
        </div>
      </div>
      <div class="course-list">
        <div class="nodata" v-if="courseList.length == 0">
          <img src="@/assets/img/nodata.png" alt="" />
          <div>暂无数据</div>
        </div>
        <div
          class="course-item"
          v-for="(item, index) in courseList"
          :key="index"
          @click="gotoDetail(item)"
        >
          <div class="course-cont">
            <div class="img">
              <img :src="item.imgUrl" alt="" />
            </div>
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
</template>

<script>
import { getRemendCourse, getBannerList } from '@/api/index'
import { getUserInfo, insertPicture } from '@/api/user'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
require('swiper/css/swiper.css')

export default {
  name: 'homeList',
  data() {
    return {
      fromLogin: this.$route.query.fromLogin,
      isLoading: true,
      bannerList: [],
      courseList: [],
      swiperOption: {
        notNextTick: true,
        autoplay: {
          delay: 3000, //3秒切换一次
        },
        speed: 1000,
        direction: 'horizontal',
        grabCursor: true,
        setWrapperSize: true,
        loop: true,
        paginationClickable: true,
        prevButton: '.swiper-button-prev',
        nextButton: '.swiper-button-next',
        mousewheelControl: false,
        observeParents: true,
        debugger: false,
        pagination: {
          el: '.swiper-pagination',
          type: 'bullets',
          progressbarOpposite: true,
          bulletElement: 'li',
          dynamicMainBullets: 2,
          clickable: true,
        },
        onTransitionStart() {},
      },
      openFirst: window.localStorage.getItem('openApp'),
      userInfo: {},
      todayHour: 0, // 今日学时
      totalHour: 0, // 累计学时
    }
  },

  components: {
    swiper,
    swiperSlide,
  },
  mounted() {
    this.getUserInfo()
    this.getCourseList()
    this.getBannerList()
    setTimeout(() => {
      this.isLoading = false
      window.localStorage.removeItem('openApp')
    }, 1200);
  },
  methods: {
    getUserInfo() {
      getUserInfo({})
        .then((res) => {
          this.creditNum = res.creditNum
          this.certificateNum = res.certificateNum
          this.userInfo = res.userInfo
          if (res.todayHour > 0) {
            this.todayHour = this.$_courseCountDown(res.todayHour)
          } else {
            this.todayHour = '0'
          }
          if (res.totalHour > 0) {
            this.totalHour = this.$_courseCountDown(res.totalHour)
          } else {
            this.totalHour = '0'
          }
          window.localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
          window.localStorage.setItem('userId', this.userInfo.userId)
          window.localStorage.setItem('companyId', this.userInfo.companyId)
          window.localStorage.setItem('companyName', this.userInfo.companyName)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 轮播图跳转
    goPage(item) {

    },

    // 获取轮播图
    getBannerList() {
      getBannerList({})
        .then((res) => {
          this.bannerList = res.data
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 查看全部课程
    checkMoreCourse() {
      this.$router.push({
        path: '/knowledge',
      })
    },

    // 前往学习
    gotoDetail(item) {
      this.$router.push({
        name: 'knowledgeDetail',
        query: {
          uuid: item.uuid,
        },
      })
    },

    // 获取推荐课程
    getCourseList() {
      getRemendCourse({
        isLearned: 2,
        action: 'new',
        pageNum: 1,
        pageSize: 8,
      })
        .then((res) => {
          this.courseList = res.rows
        })
        .catch((err) => {
          console.log(err)
        })
    },

  },
}
</script>

<style lang="less">
.home_index_cont {
  width: 100%;
  background: #f5f5f5;
  position: relative;

  .banner {
    display: flex;
    align-items: center;
    justify-content: space-between;
    .banner_left {
      height: 100%;
      background: #fff;
      border-radius: 10px;
      overflow: hidden;
      position: relative;
    }
    .banner_right {
      height: 100%;
      .user_data_cont {
        width: 100%;
        height: 100%;
        background-color: #fff;
        border-radius: 10px;
        border: 1px solid #eee;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: center;
        .avatar {
          width: 80px;
          height: 80px;
          border-radius: 50%;
          overflow: hidden;
          flex-shrink: 0;
          margin-bottom: 25px;
          img {
            width: 100%;
            height: 100%;
          }
        }
        .user-data {
          font-size: 14px;
          display: flex;
          flex-direction: column;
          justify-content: flex-start;
          align-items: center;
          .compony {
            margin-top: 15px;
          }
        }

      }
    }
    .swiper-pagination {
      position: absolute;
      bottom: 10px;
      left: 50%;
      margin-left: -250px;
      width: 500px;
      z-index: 999;

      li {
        margin: 0 10px;
      }

      .swiper-pagination-bullet-active {
        background: #fff !important;
      }

      .swiper-pagination-bullet {
        &:nth-of-type(n + 6) {
          display: none !important;
        }
      }
    }
  }

  @media screen and (min-width: 1301px) {
    .banner {
      width: 100%;
      height: 344px;
      .banner_left {
        width: 960px;
      }
      .banner_right {
        width: 230px;
        .top-block-time {
          width: 100%;
          display: flex;
          /* flex-direction: column; */
          align-items: center;
          justify-content: space-between;
          .item {
            width: 48%;
            height: 60px;
            padding: 4px 15px;
            box-sizing: border-box;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: center;
            position: relative;

            .title {
              font-size: 12px;
              color: #333;
              font-weight: bold;
              letter-spacing: 3px;
            }
            .num {
              font-size: 14px;
              color: #5881db;
              font-weight: bold;
              margin-bottom: 10px;
              white-space: nowrap;
            }
          }
        }
      }
      .swiper-slide {
        background: rgb(226, 225, 225) no-repeat center;
        background-size: auto 100%;
        height: 344px;
        width: 100%;
      }

      img {
        width: 100%;
        height: 100%;
      }
    }
  }

  @media screen and (max-width: 1300px) {
    .banner {
      width: 100%;
      height: 288px;
      .banner_left {
        width: 800px;
      }
      .banner_right {
        width: 190px;
        .top-block-time {
          width: 100%;
          display: flex;
          /* flex-direction: column; */
          align-items: center;
          justify-content: space-between;
          .item {
            width: 48%;
            height: 60px;
            padding: 4px 15px;
            box-sizing: border-box;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: center;
            position: relative;

            .title {
              font-size: 11px;
              color: #333;
              font-weight: bold;
              letter-spacing: 3px;
            }
            .num {
              font-size: 12px;
              color: #5881db;
              font-weight: bold;
              margin-bottom: 10px;
              white-space: nowrap;
            }
          }
        }
      }
      .swiper-slide {
        background: rgb(226, 225, 225) no-repeat center;
        background-size: auto 100%;
        height: 288px;
        width: 100%;
      }

      img {
        width: 100%;
        height: 100%;
      }
    }
  }

  .common-title {
    width: 100%;
    height: 50px;
    padding: 0 5px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: space-between;
    border-bottom: 1px solid #f5f5f5;
    box-sizing: border-box;

    .title {
      font-size: 18px;
      color: #333;
      font-weight: bold;
    }

    .more {
      display: flex;
      align-items: center;
      cursor: pointer;
      color: #666;

      &:hover {
        color: #5881db;
      }

      i.iconfont {
        margin-left: 8px;
        font-size: 14px;
      }

      span {
        font-size: 14px;
      }
    }
  }

  .data-cont {
    width: 100%;
    display: flex;
    align-items: center;

    .cont-item {
      width: 50%;
      flex-shrink: 0;

      &:first-child {
        padding-right: 8px;
        box-sizing: border-box;
      }

      &:last-child {
        padding-left: 8px;
        box-sizing: border-box;
      }
    }
  }

  .nodata {
    width: 100%;
    height: 200px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    img {
      width: 150px;
    }

    div {
      line-height: 50px;
      font-size: 16px;
      color: #999;
    }
  }

  .my-course {
    width: 100%;
    margin-top: 10px;
    box-sizing: border-box;

    .course-list {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      flex-wrap: wrap;

      @media screen and (min-width: 1301px) {
        .course-item {
          width: 300px;

          .img {
            height: 156px;
          }
        }

        .exam-item {
          width: 386px;
        }
      }

      @media screen and (max-width: 1300px) {
        .course-item {
          width: 250px;

          .img {
            height: 136px;
          }
        }

        .exam-item {
          width: 318px;
        }
      }

      .course-item {
        padding: 8px;
        box-sizing: border-box;

        &:hover {
          img {
            transform-origin: 50% 50%;
            transform: scale(1.1);
            -webkit-transform: scale(1.1);
          }
        }

        .course-cont {
          width: 100%;
          border: 1px solid #f5f5f5;
          background-color: #fff;
          border-radius: 10px;
          cursor: pointer;
          overflow: hidden;

          .img {
            width: 100%;
            overflow: hidden;
            position: relative;

            img {
              width: 100%;
              height: 100%;
              object-fit: cover;
              transition: all 0.3s;
              -webkit-transition: all 0.3s;
            }

            .type {
              position: absolute;
              top: 10px;
              left: 10px;
              min-width: 50px;
              padding: 0 6px;
              box-sizing: border-box;
              height: 24px;
              background: rgba(0, 0, 0, 0.3);
              border-radius: 4px;
              display: flex;
              align-items: center;
              justify-content: center;

              i {
                width: 4px;
                height: 4px;
                background: #b82121;
                border-radius: 50%;
              }

              i.active {
                background: #3cbb4d;
              }

              span {
                font-size: 12px;
                color: #fff;
              }
            }
          }

          .name {
            width: 100%;
            padding: 0 10px;
            box-sizing: border-box;
            line-height: 40px;
            height: 40px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            font-size: 14px;
            color: #333;
          }

          .people {
            padding: 0 10px;
            box-sizing: border-box;
            font-size: 12px;
            color: #999;
            line-height: 30px;
            height: 30px;
            display: flex;
            align-items: center;

            i.iconfont {
              font-size: 12px;
              margin-right: 5px;
            }
          }
        }
      }

      .exam-item {
        padding: 20px 16px 0px;
        margin: 6px;
        box-sizing: border-box;
        font-size: 14px;
        border-radius: 10px;
        position: relative;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        overflow: hidden;
        border: 1px solid #eee;
        background: #fff;

        .type-status {
          position: absolute;
          right: 0;
          top: 0;
          height: 22px;
          background: #f8c6b5;
          color: #fff;
          padding: 0 15px;
          box-sizing: border-box;
          line-height: 22px;
          border-bottom-left-radius: 10px;
          font-size: 12px;
        }

        .line {
          width: 100%;
          display: flex;
          align-items: center;
          justify-content: space-between;

          &:last-child {
            margin-bottom: 10px;
          }
        }

        .name {
          width: 100%;
          display: flex;
          align-items: center;
          justify-content: flex-start;
          font-size: 16px;
          color: #333;
          font-weight: bold;
          margin-bottom: 18px;

          .iconfont {
            font-size: 16px;
            margin-right: 6px;
            line-height: 20px;
          }

          span {
            flex-shrink: 0;
            max-width: 260px;
            line-height: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }

        .time {
          width: 100%;
          min-height: 30px;
          display: flex;
          align-items: center;
          justify-content: flex-start;

          .iconfont {
            font-size: 14px;
            margin-right: 5px;
            margin-bottom: 1px;
          }

          span {
            font-size: 12px;
            white-space: nowrap;

            &:nth-child(2) {
              color: #333;
              margin-right: 5px;
            }

            &:nth-child(3) {
              color: #333;
            }
          }

          .single_exam_time {
            display: flex;
            align-items: center;
            font-size: 12px;
            color: #333;

            .left {
              padding: 8px 0;

              span {
                line-height: 24px;
                text-align: center;
              }
            }

            .center {
              margin: 0 10px;
            }

            .right {
              padding: 8px 0;

              span {
                line-height: 24px;
                text-align: center;
              }
            }
          }
        }

        .length {
          flex: 1;
          height: 30px;
          display: flex;
          align-items: center;
          margin-top: 3px;
          justify-content: flex-start;

          .iconfont {
            font-size: 15px;
            margin-right: 5px;
            margin-bottom: 1px;
          }

          span {
            font-size: 12px;

            &:nth-child(2) {
              color: #333;
              margin-right: 5px;
            }

          }
        }

        .btn-list {
          display: flex;
          align-items: center;
          justify-content: center;
          width: 100%;
          height: 70px;
          box-sizing: border-box;

          .check {
            width: 120px;
            height: 30px;
            border: 1px solid #5881db;
            color: #5881db;
            text-align: center;
            line-height: 28px;
            border-radius: 4px;
            cursor: pointer;
            margin: 0 10px;
            box-sizing: border-box;
            white-space: nowrap;
            transition: all 0.3s;
            -webkit-transition: all 0.3s;

            &:hover {
              transform: translateY(-6px);
              -webkit-transform: translateY(-6px);
            }
          }

          .start {
            margin: 0 10px;
            width: 100px;
            height: 30px;
            background-color: #5881db;
            color: #fff;
            text-align: center;
            line-height: 30px;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.3s;
            -webkit-transition: all 0.3s;

            &:hover {
              transform: translateY(-6px);
              -webkit-transform: translateY(-6px);
            }
          }
        }


        .wait {
          position: absolute;
          top: 0px;
          right: 0px;

          img {
            width: 80px;
            height: 60px;
            object-fit: contain;
          }
        }
      }
    }
  }
}
</style>
