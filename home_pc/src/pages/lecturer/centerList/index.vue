<template>
  <div class="lecturer-center" >
    <div class="center-cont">
      <div class="course-list-cont">
        <div class="top-filter">
          <div class="status">
            <span class="tit">讲师等级：</span>
            <div class="tab-list">
              <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="全部" name="all" ></el-tab-pane>
                <el-tab-pane :label="item.title" :name="item.uuid" v-for="(item, index) in  lecturerGrade" :key="index"></el-tab-pane>
              </el-tabs>
            </div>
          </div>

        </div>
        <div class="course-list" ref="corse_list">
          <vue-data-loading
            :offset="-3"
            :loading="loading"
            :completed="completed"
            :listens="['infinite-scroll']"
            :init-scroll="true"
            container="corse_list"
            @infinite-scroll="infiniteScroll"
          >
            <div slot="pull-down-ready">ready to refresh</div>
            <ul>
              <li
                class="course-item"
                v-for="(item, index) in trainList"
                :key="index"
                @click="gotoLecturerDetail(item)"
              >
                <div class="course-cont">
                  <div class="img">
                    <img :src="item.lectorAvatar" alt="" />
                    <div class="sex" :class="{ 'man': item.sex == 'man', 'woman' : item.sex == 'woman' }">
                      <i
                        class="iconfont icon-icon2"
                        v-if="item.sex == 'man'"
                      ></i>
                      <i class="iconfont icon-icon1" v-else></i>
                    </div>
                  </div>
                  <div class="lecturer-data">
                    <div class="name">
                      {{item.lectorName}}
                    </div>
                    <div class="people">{{ item.lectorGradeName }}</div>
                    <div class="bottom">
                      <div>课程：{{ item.courseCount }}</div>
                    </div>
                  </div>
                </div>
              </li>
            </ul>
            <div slot="infinite-scroll-loading">加载中...</div>
            <div slot="completed">加载完毕!</div>
          </vue-data-loading>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getLecturerList, getLecturerGrade } from '@/api/lecturer'
import VueDataLoading from 'vue-data-loading'
export default {
  name: 'lecturerList',
  components: { VueDataLoading },
  data() {
    return {
      activeName: 'all',
      gradeId: '', // 等级ID
      radio: 'learning', // 学习状态
      searchValue: '', // 搜索内容
      trainList: [], // 课程列表
      currentPage: 1, // 当前页数
      pageSize: 20, // 一页多少条
      total: 100, // 一共多少
      windowHeight: 0,
      loading: false,
      completed: false,
      // 总条数
      total: 0,
      lecturerGrade: [], // 等级列表
    }
  },
  created() {
    this.getLecturerGrade()
  },
  mounted() {
    this.windowHeight = window.innerHeight - 90
    // console.log(this.windowHeight)

  },
  methods: {
    handleClick(tab, event) {
      // console.log(tab, event)
      if(this.activeName == 'all') {
        this.gradeId = ''
      }else{
        this.gradeId = this.activeName
      }
      this.trainList = []
      this.currentPage = 1
      this.getLecturerList()
    },
    infiniteScroll() {
      console.log('下拉加载')
      this.getLecturerList()
      this.currentPage++
    },
    // 搜索
    searchList() {
      this.trainList = []
      this.currentPage = 1
      this.getLecturerList()
    },
    // 获取讲师等级列表
    getLecturerGrade() {
      getLecturerGrade().then((res) => {
        this.lecturerGrade = res.data

      })
    },
    // 获取讲师列表
    getLecturerList() {
      getLecturerList({
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        lectorGrade: this.gradeId,
      }).then((response) => {
        let arr = response.rows
        // console.log(arr)
        this.trainList = this.trainList.concat(arr)
        // console.log(this.trainList)
        this.total = response.total
        this.loading = false
        if (this.currentPage == 0 && this.trainList.length == 0) {
          this.noData = true
        } else if (this.trainList.length == response.total) {
          this.completed = true
          this.loading = false
        }
        this.$forceUpdate()
      })
    },
    // 前往学习
    gotoLecturerDetail(item) {
      this.$router.push({
        name: 'lecturerDetail',
        query: {
          uuid: item.lectorId,
        },
      })
    },
  },
}
</script>
<style lang="less">
.lecturer-center {
  width: 100%;
  height: 100%;
  background: #f5f5f5;
  overflow-x: hidden;
  overflow: hidden;

  .top-filter {
    width: 100%;
    height: 50px;
    padding: 0 10px;
    box-sizing: border-box;
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;
    border-bottom: 1px solid #eee;

    .status {
      display: flex;
      align-items: center;
      padding-left: 20px;
      box-sizing: border-box;
      span.tit {
        font-size: 14px;
      }
      .tab-list {
        padding-left: 20px;
        box-sizing: border-box;
        .el-tabs__header {
          margin: 0;
        }
      }
    }

    .search {
      width: 200px;
      height: 36px;
      border-radius: 5px;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      overflow: hidden;
      border: 1px solid #5881db;

      input {
        border: none;
        width: 120px;
      }

      span {
        height: 36px;
        line-height: 36px;
        white-space: nowrap;
        font-size: 14px;
        padding: 0 10px;
        box-sizing: border-box;
        background: #5881db;
        color: #fff;
        cursor: pointer;
      }
    }
  }

  .center-cont {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: flex-start;
    justify-content: space-between;

    .course-list-cont {
      width: 100%;
      height: 100%;
      box-sizing: border-box;
      background: #fff;
      border-radius: 10px;
      overflow: hidden;
    }
  }
  .bottom-block {
    background: #fff;
  }
  .course-list {
    width: 100%;
    height: calc(100% - 100px);
    overflow-y: auto;

    padding: 10px 0 0;
    box-sizing: border-box;
    .vue-data-loading {
      width: 100%;
      ul {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        align-content: flex-start;
      }
      // display: flex;
      // align-content: flex-start;
      // flex-wrap: wrap;
    }
    @media screen and (max-width: 1300px) {
      .course-item {
        width: 33%;
      }
    }

    @media screen and (min-width: 1301px) {
      .course-item {
        width: 25%;
      }
    }

    .nodata {
      width: 100%;
      height: 500px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      img {
        width: 300px;
      }

      div {
        line-height: 50px;
        font-size: 16px;
        color: #999;
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
        display: flex;
        align-items: center;
        padding: 15px 10px;
        box-sizing: border-box;

        .img {
          width: 100px;
          flex-shrink: 0;
          position: relative;
          img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            transition: all 0.3s;
            -webkit-transition: all 0.3s;
          }
          .sex {
            position: absolute;
            font-size: 20px;
            bottom: 0;
            right: 0;
            width: 20px;
            height: 20px;
            background-color: #fff;
            color: #fff;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 4px;
            &.man {
              background-color: #1677ff;
            }
            &.woman {
              background-color: #f5222d;
            }
          }
        }
        .lecturer-data {
          flex: 1;
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
          color: #5881db;
          line-height: 30px;
          height: 30px;
          display: flex;
          align-items: center;

          i.iconfont {
            font-size: 12px;
            margin-right: 5px;
          }
        }
        .bottom {
          width: 100%;
          padding: 0 10px;
          box-sizing: border-box;
          font-size: 12px;
          color: #333;
          line-height: 30px;
          height: 30px;
          display: flex;
          align-items: center;
          div {
            margin-right: 20px;
          }
        }
      }
    }
  }
}
</style>
