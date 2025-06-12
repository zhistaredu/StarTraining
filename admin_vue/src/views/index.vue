<template>
  <div class="app-container home">
    <div class="faster-list">
      <div class="course-team-list">

        <div class="course cont-list">
          <div class="tit">知识情况</div>
          <div class="cont-box">
            <div class="cont-lf">
              <div class="cont-item">
                <div>知识总量</div>
                <div>{{ courseData.courseCount }}</div>
              </div>
              <div class="cont-item">
                <div>本期新增</div>
                <div>{{ courseData.growthCourseCount }}</div>
              </div>
              <div class="cont-item">
                <div>学习人次</div>
                <div>{{ courseData.totalLearners }}</div>
              </div>
              <div class="cont-item">
                <div>学习时长</div>
                <div>{{ courseData.totalDuration }}<span>h</span></div>
              </div>
              <div class="cont-item">
                <div>完成人次</div>
                <div>{{ courseData.totalCompleted }}</div>
              </div>
            </div>
            <div class="cont-rg">
              <div class="progress" v-if="courseData.finishedRatio">
                <el-progress
                  color="#5881db"
                  type="circle"
                  :percentage="Number(courseData.finishedRatio)"
                ></el-progress>
              </div>

              <div class="type-list">
                <div class="type-item">
                  <div class="circle"></div>
                  <div>知识完成率</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="cont-list">
          <div class="normal-fun">
            <div class="tit">快捷入口</div>
            <div class="fun-list">
              <div class="create-course create-item" @click="skipAct('course')">
                <img src="@static/image/home/createCourse.png" alt="" />
                <span>知识中心</span>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>

    <div class="learning-echart">
      <div class="tit">学习分析</div>
      <div id="main" style="width: 100%; height: 400px"></div>
    </div>

    <div class="loading_box" v-if="fromLogin && isLoading">
      <div class="spinner">
        <div class="dot1"></div>
        <div class="dot2"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";

import {
  getWorkbench,
  getCompanyData,
  learningProject,
} from "@/api/system/admin";

export default {
  name: "Index",
  data() {
    return {
      fromLogin: this.$route.query.fromLogin,
      isLoading: true,
      screenWidth: null, //初始化宽度
      pubCourseNum: 0, // 已发布课程数量
      noCourseNum: 0, // 未发布课程数量
      pubExamNum: 0, // 已发布课程数量
      noExamNum: 0, // 未发布课程数量
      userData: {},
      chart: null,
      learnPeople: [], // 学习人数
      learnTime: [], // 学习时长
      timeList: [],
      periodValidity: "", // 有效期
      edition: "free", // 版本
      buyNum: 0, // 购买人数
      accreditNum: 0, // 授权人数
      totalSpaceGb: "0GB", //总存储
      usedSpaceGb: "0GB", //使用空间
      freeSpaceGb: "0GB", //剩余空间
      spacePercentage: 0,
      accreditPercentage: 0,
      days: 0,
      isPay: 0,
      defineColor: "#fa541c",
      courseData: {},
      trainData: {},
      examData: {},
    };
  },
  components: {},

  watch: {
    // 浏览器宽度
    screenWidth: {
      handler() {
        if (!this.timer) {
          this.timer = true;
          let _this = this;
          if (this.chart) {
            this.chart.dispose(); //销毁
          }
          setTimeout(function () {
            _this.initEcharts();
            _this.timer = false;
          }, 500);
        }
      },
      // 立即以obj.name的当前值触发回调
      immediate: true,
    },
  },
  mounted() {
    var _this = this;
    setTimeout(() => {
      this.isLoading = false;
    }, 1200);
    // 获取浏览器宽度
    window.onresize = () => {
      return (() => {
        _this.screenWidth = document.body.clientWidth;
      })();
    };
    setTimeout(() => {
      if (window.localStorage.getItem("userInfo")) {
        this.userData = JSON.parse(window.localStorage.getItem("userInfo"));
      }
    }, 100);
    this.getWorkbenchData();
    this.getCompany();
    this.getMenuData();
  },
  methods: {
    skipAct(type) {
      if (type == "course") {
        this.$router.push({
          path: "/course/courseList",
        });
      } else if (type == "training") {
        this.$router.push({
          path: "/train/learn/trainList",
        });
      } else if (type == "exam") {
        this.$router.push({
          path: "/exam/exam",
        });
      } else if (type == "newTrain") {
        this.$router.push({
          path: "/train/learn/newTrainList",
        });
      } else if (type == "survey") {
        this.$router.push({
          path: "/survey/survey",
        });
      } else if (type == "certificate") {
        this.$router.push({
          path: "/system/operate/certificate",
        });
      } else if (type == "lector") {
        this.$router.push({
          path: "/teachers/lector/lectorList",
        });
      } else if (type == "exercise") {
        this.$router.push({
          path: "/exam/poolExercise",
        });
      }
    },
    getMenuData() {
      let _this = this;
      learningProject().then(function (res) {
        if (res.code == 200) {
          _this.courseData = res.data.course;
          _this.trainData = res.data.train;
          _this.examData = res.data.test;
        }
      });
    },
    getWorkbenchData() {
      let _this = this;
      getWorkbench().then(function (res) {
        if (res.code == 200) {
          _this.pubCourseNum = res.data.pubCourseNum; // 已发布课程数量
          _this.noCourseNum = res.data.noCourseNum; // 未发布课程数量
          _this.pubExamNum = res.data.pubExamNum; // 已发布课程数量
          _this.noExamNum = res.data.noExamNum; // 未发布课程数量
          _this.learnPeople = res.data.loginList;
          _this.learnTime = res.data.learnTimeList;
          _this.timeList = res.data.dateArr;
          _this.screenWidth = document.body.clientWidth;
        }
      });
    },
    getCompany() {
      let _this = this;
      getCompanyData().then(function (res) {
        if (res.code == 200) {
          _this.periodValidity = res.data.periodValidity; // 有效期
          _this.edition = res.data.edition; // 版本
          _this.buyNum = res.data.buyNum; // 购买人数
          _this.accreditNum = res.data.accreditNum; // 授权人数
          if (_this.buyNum > 0) {
            _this.accreditPercentage = (_this.accreditNum / _this.buyNum) * 100;
          }
          _this.totalSpaceGb = res.data.totalSpaceGb; //总存储
          _this.usedSpaceGb = res.data.usedSpaceGb; //使用空间
          _this.freeSpaceGb = res.data.freeSpaceGb; //剩余空间
          if (_this.totalSpaceGb > 0) {
            _this.spacePercentage =
              (_this.usedSpaceGb / _this.totalSpaceGb) * 100;
          }
          _this.days = res.data.days;
          _this.isPay = res.data.isPay;
        }
      });
    },
    initEcharts() {
      // 基于准备好的dom，初始化echarts实例
      this.chart = echarts.init(document.getElementById("main"));
      // 绘制图表
      this.chart.setOption({
        color: ["#5881db", "#104dd3"],

        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            label: {
              backgroundColor: "#6a7985",
            },
          },
        },
        legend: {
          data: ["学习人数", "学习时长"],
        },
        toolbox: {
          feature: {
            saveAsImage: {},
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: true,
            data: this.timeList,
          },
        ],
        yAxis: [
          {
            type: "value",
          },
          {
            type: "value",
          },
        ],
        series: [
          {
            name: "学习人数",
            type: "bar",
            barWidth: "60%",
            data: this.learnPeople,
          },
          {
            name: "学习时长",
            type: "line",
            data: this.learnTime,
          },
        ],
      });
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  max-width: 1200px;
  min-width: 1000px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px 30px 10px;
  box-sizing: border-box;
  margin: 0 auto;
  .loading_box {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 99999;
    background-color: rgba(0, 0, 0, 0.3);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    box-sizing: border-box;

    .spinner {
      margin: 100px auto;
      width: 90px;
      height: 90px;
      position: relative;
      text-align: center;
      -webkit-animation: rotate 2s infinite linear;
      animation: rotate 2s infinite linear;
    }
    .dot1,
    .dot2 {
      width: 60%;
      height: 60%;
      display: inline-block;
      position: absolute;
      top: 0;
      background-color: #5881db;
      border-radius: 100%;
      -webkit-animation: bounce 2s infinite ease-in-out;
      animation: bounce 2s infinite ease-in-out;
    }
    .dot2 {
      top: auto;
      bottom: 0px;
      -webkit-animation-delay: -1s;
      animation-delay: -1s;
    }
    @-webkit-keyframes rotate {
      100% {
        -webkit-transform: rotate(360deg);
      }
    }
    @keyframes rotate {
      100% {
        transform: rotate(360deg);
        -webkit-transform: rotate(360deg);
      }
    }
    @-webkit-keyframes bounce {
      0%,
      100% {
        -webkit-transform: scale(0);
      }
      50% {
        -webkit-transform: scale(1);
      }
    }
    @keyframes bounce {
      0%,
      100% {
        transform: scale(0);
        -webkit-transform: scale(0);
      }
      50% {
        transform: scale(1);
        -webkit-transform: scale(1);
      }
    }
  }
  .header-top {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;

    .hello-name {
      width: 300px;
      height: 330px;
      border-radius: 10px;
      flex-shrink: 0;
      background: #fff;
      border: 1px solid #ededed;
      box-shadow: 0px 5px 16px 2px rgba(187, 187, 187, 0.24);

      .quanyi-tit {
        font-size: 16px;
        color: #333;
        font-weight: bold;
        width: 100%;
        line-height: 60px;
        padding: 0 10px;
        box-sizing: border-box;
      }

      .quanyi-cont {
        width: 100%;
        padding: 0 10px;
        box-sizing: border-box;

        .qu-list {
          width: 100%;
          padding: 15px 0;
          box-sizing: border-box;
          display: flex;
          align-items: center;
          justify-content: flex-start;

          .icon {
            flex-shrink: 0;
            width: 52px;
            height: 52px;
            background: #2dcc5c;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;

            .iconfont {
              font-size: 26px;
              color: #fff;
            }
          }

          .qu-cont {
            flex: 1;
            padding-left: 10px;
            box-sizing: border-box;

            .qu-cont-item {
              width: 100%;
              display: flex;
              align-items: center;
              justify-content: space-between;
              font-size: 14px;
            }

            .qu-cont-center {
              padding: 3px 0;
              box-sizing: border-box;
            }

            .qu-cont-bottom {
              width: 100%;
            }
          }
        }
      }

      .hello-cont {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        .avatar {
          width: 60px;
          height: 60px;
          border-radius: 50%;
          overflow: hidden;
          background: #333;

          img {
            width: 100%;
            height: 100%;
            object-fit: contain;
          }
        }

        .name {
          width: 150px;
          font-size: 16px;
          color: #333;
          line-height: 50px;
          display: flex;
          align-items: center;
        }

        .desc {
          font-size: 14px;
          color: #333;
          line-height: 50px;
        }
      }
    }
  }

  .faster-list {
    width: 100%;

    .course-team-list {
      width: 100%;
      height: 280px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 10px;
    }

    .cont-list {
      flex: 1;
      height: 100%;
      padding: 20px;
      border-radius: 10px;
      background: #fff;
      box-sizing: border-box;
      border: 1px solid #ededed;
      box-shadow: 0px 5px 16px 2px rgba(187, 187, 187, 0.24);

      &:nth-child(2) {
        margin: 0 10px;
      }

      .tit {
        margin-bottom: 20px;
        font-size: 16px;
        color: #333;
        font-weight: bold;
      }

      .cont-box {
        width: 100%;
        display: flex;
        align-items: flex-start;
        justify-content: space-between;

        .cont-lf {
          width: 60%;
          flex-shrink: 0;
          display: flex;
          align-items: center;
          justify-content: flex-start;
          flex-wrap: wrap;

          .cont-item {
            width: 50%;
            margin-bottom: 10px;

            div {
              &:nth-child(1) {
                font-size: 14px;
                color: #333;
                line-height: 38px;
              }

              &:nth-child(2) {
                font-size: 16px;
                color: #333;
                font-weight: bold;

                > span {
                  font-size: 14px;
                }
              }
            }
          }
        }

        .cont-rg {
          width: 40%;
          flex-shrink: 0;
          padding-top: 15px;
          box-sizing: border-box;

          .progress {
            width: 100%;

            .progress-container {
              position: relative;
            }

            svg path {
              transition: all 0.3s ease;
              /* 添加过渡效果 */
            }
          }

          .type-list {
            .type-item {
              height: 24px;
              display: flex;
              align-items: center;
              justify-content: center;
              font-size: 12px;

              .circle {
                width: 10px;
                height: 10px;
                background: #5881db;
                border-radius: 50%;
                margin-right: 5px;
              }

              .orange {
                background: #ff7a45;
              }
            }
          }
        }
      }
    }

    .course {
      .cont-box {
        .cont-rg {
          width: 40%;
          flex-shrink: 0;
          padding-top: 15px;
          box-sizing: border-box;

          .progress {
            width: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;

            .top-pro {
              width: 80%;
              height: 40px;
              background: url("~@static/image/zhu1.png");
              background-size: 100% 100%;
              text-align: center;
              line-height: 20px;
              color: #fff;
              font-size: 14px;
            }

            .bottom-pro {
              width: 100%;
              height: 50px;
              background: url("~@static/image/zhu2.png");
              background-size: 100% 100%;
              text-align: center;
              line-height: 30px;
              color: #fff;
              font-size: 14px;
              margin-top: 5px;
            }
          }

          .type-list {
            padding-top: 20px;

            .type-item {
              height: 24px;
              display: flex;
              align-items: center;
              justify-content: center;
              font-size: 12px;

              .circle {
                width: 10px;
                height: 10px;
                background: #5881db;
                border-radius: 50%;
                margin-right: 5px;
              }

              .orange {
                background: #ff7a45;
              }
            }
          }
        }
      }
    }

  }

  .course-team {
    flex: 1;
    height: 330px;
    margin-right: 10px;

    .tit {
      width: 100%;
      height: 50px;
      line-height: 50px;
      font-size: 18px;
      color: #333;
      font-weight: bold;
    }
  }



  @media screen and (min-width: 1401px) {
    .normal-fun {

      .fun-list {
        .create-item {
          img {
            width: 45px;
            height: 45px;
            object-fit: contain;
            margin-right: 10px;
          }

          span {
            font-size: 18px;
            color: #333;
          }
        }
      }
    }
  }

  @media screen and (max-width: 1400px) {
    .normal-fun {

      .fun-list {
        .create-item {
          img {
            width: 40px;
            height: 40px;
            object-fit: contain;
            margin-right: 10px;
          }

          span {
            font-size: 16px;
            color: #333;
          }
        }
      }
    }
  }

  .normal-fun {
    width: 100%;
    height: 100%;
    box-sizing: border-box;


    .fun-list {
      width: 100%;
      box-sizing: border-box;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .create-item {
        display: flex;
        /* width: 20%; */
        align-items: center;
        justify-content: flex-start;
        cursor: pointer;
      }

      .fun-item {
        width: 100px;
        height: 40px;
        border: 1px solid #ededed;
        box-shadow: 0px 5px 16px 2px rgba(187, 187, 187, 0.24);
        text-align: center;
        line-height: 40px;
        margin-right: 20px;
        border-radius: 5px;
        font-size: 14px;
        color: #333;
        margin-bottom: 10px;
        cursor: pointer;

        &:hover {
          border-color: #40a0ff;
          color: #40a0ff;
        }
      }
    }
  }

  .learning-echart {
    width: 100%;
    border: 1px solid #ededed;
    box-shadow: 0px 5px 16px 2px rgba(187, 187, 187, 0.24);
    padding: 20px;
    box-sizing: border-box;
    border-radius: 10px;

    .tit {
      font-size: 18px;
      color: #333;
      font-weight: bold;
      line-height: 50px;
    }
  }

  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
