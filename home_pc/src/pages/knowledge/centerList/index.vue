<template>
  <div class="course_index_box_center cont_all_width">
    <div class="center-cont">
      <div class="category" :class="{ active: isShowCate }">
        <div class="category-tit">分类筛选：</div>
        <el-tree ref="treecate" :data="categoryList" :props="defaultProps" :default-expand-all="true"
          :highlight-current="true" :expand-on-click-node="false" node-key="id" @node-click="handleNodeClick"></el-tree>
      </div>
      <div class="course-list-cont">
        <div class="top-filter">
          <div class="status">
            <div class="type">
              <el-tabs v-model="learnStatus" @tab-click="changeLearnStatus">
                <el-tab-pane :label="item.text" :name="item.value" v-for="(item, index) in option1"
                  :key="index"></el-tab-pane>
              </el-tabs>
            </div>
          </div>
          <div class="right">

            <div class="search">
              <el-input v-model="searchValue" placeholder="请输入内容"></el-input>
              <span @click="searchList">搜索</span>
            </div>
          </div>

        </div>
        <div class="course-list" v-loading="isLoading">
          <div class="course-item" v-for="(item, index) in courseList" :key="index" @click="gotoDetail(item)">
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
          <div class="nodata" v-if="courseList.length == 0">
            <img src="@/assets/img/nodata.png" alt="" />
            <div>暂无数据</div>
          </div>
        </div>
        <div class="bottom-block">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
            :page-sizes="[9, 12, 24, 32]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
            :total="total" v-if="courseList.length">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCourseList, getCourseCategoryList } from '@/api/course'
export default {
  name: 'knowledgeList',
  data() {
    return {
      learnStatus: 'all', // 学习状态
      searchValue: '', // 搜索内容
      courseList: [], // 课程列表
      currentPage: 1, // 当前页数
      pageSize: 9, // 一页多少条
      total: 100, // 一共多少
      categoryId: 'all', // 分类id
      categoryList: [], // 分类列表
      defaultProps: {
        children: 'children',
        label: 'label',
      },
      windowHeight: 0,
      isShowCate: true, // 是否显示分类
      isLoading: false,
      params: {}, // 请求参数
      option1: [
        { text: '全部', value: 'all' },
        { text: '学习中', value: 'learning' },
        { text: '已完成', value: 'finished' },
      ],

    }
  },
  created() {
    this.getCategoryList()
  },
  mounted() {
    this.windowHeight = window.innerHeight - 90
    this.getCourseList()

  },
  methods: {
    // 分类筛选
    handleNodeClick(data) {
      if (data.id == 'all') {
        this.categoryId = ''
      } else {
        this.categoryId = data.id
      }
      this.currentPage = 1
      this.getCourseList()
    },
    // 获取分类列表
    getCategoryList() {
      getCourseCategoryList().then((res) => {
        this.categoryList = res.data
        this.categoryList.unshift({
          label: '全部',
          id: 'all',
        })
        setTimeout(() => {
          this.$refs.treecate.setCurrentKey('all', true, false)
        }, 500);
      })
    },
    // 改变每页条数
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.getCourseList()
    },
    // 切换页码
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.getCourseList()
    },
    // 切换学习状态
    changeLearnStatus(tab, event) {
      this.currentPage = 1
      this.getCourseList()
    },
    // 搜索
    searchList() {
      this.currentPage = 1
      this.getCourseList()
    },
    // 获取课程列表
    getCourseList() {

      this.params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        categoryId: this.categoryId == 'all' ? '' : this.categoryId,
        learnStatus: this.learnStatus,
        keyword: this.searchValue,
      }

      this.isLoading = true
      getCourseList(this.params).then((res) => {
        this.courseList = res.rows
        this.total = res.total
        this.isLoading = false
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
    }
  }
}
</script>
<style lang="less">
.course_index_box_center {
  width: 100%;
  height: 100%;
  background: #f5f5f5;
  overflow-x: hidden;
  overflow: hidden;

  .top-filter {
    width: 100%;
    padding: 10px 15px 10px;
    box-sizing: border-box;
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;


    .type {
      display: flex;
      align-items: center;
      box-sizing: border-box;
      margin-right: 40px;

      span.tit {
        font-size: 14px;
        margin-right: 5px;
        white-space: nowrap;
      }

      .card-item {
        color: #333;
        font-size: 14px;
        cursor: pointer;
        white-space: nowrap;

        .name {
          white-space: nowrap;
        }

        &.active {
          color: #5881db;
          font-weight: bold;
          border-bottom: 1px solid #5881db;
        }

        &:hover {
          color: #5881db;
        }
      }

      .line {
        width: 2px;
        height: 12px;
        background: #eee;
        margin: 0 10px;
      }
    }

    .status {
      display: flex;
      align-items: center;

      span.tit {
        font-size: 14px;
      }
    }

    .right {
      display: flex;
      align-items: center;


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

    @media screen and (max-width: 1300px) {
      .category {
        width: 230px;
      }

      .course-list-cont {
        width: 760px;
        height: 100%;
        box-sizing: border-box;
        background: #fff;
        border-radius: 10px;
        overflow: hidden;
      }
    }

    @media screen and (min-width: 1301px) {
      .category {
        width: 280px;
      }

      .course-list-cont {
        width: 910px;
        height: 100%;
        box-sizing: border-box;
        background: #fff;
        border-radius: 10px;
        overflow: hidden;
      }
    }

    .category {
      height: 100%;
      flex-shrink: 0;
      border-right: 1px solid #eee;
      box-sizing: border-box;
      background: #fff;
      border-radius: 10px;
      overflow-y: auto;
      padding-bottom: 30px;
      box-sizing: border-box;

      .category-tit {
        width: 100%;
        padding: 0 10px;
        box-sizing: border-box;
        font-size: 16px;
        line-height: 50px;
      }

      .el-tree-node__label {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }

  .bottom-block {
    background: #fff;
  }

  .course-list {
    width: 100%;
    height: calc(100% - 100px);
    overflow-y: auto;
    display: flex;
    align-content: flex-start;
    flex-wrap: wrap;
    box-sizing: border-box;

    @media screen and (max-width: 1300px) {
      .course-item {
        width: 247px;

        .img {
          height: 136px;
        }
      }
    }

    @media screen and (min-width: 1301px) {
      .course-item {
        width: 297px;

        .img {
          height: 156px;
        }
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

        .img {
          width: 100%;
          border-radius: 5px;
          overflow: hidden;
          position: relative;

          img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: all 0.3s;
            -webkit-transition: all 0.3s;
          }

          .status {
            position: absolute;
            top: 10px;
            left: 10px;
            width: 60px;
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
              margin-left: 4px;
            }
          }

          .type {
            position: absolute;
            top: 10px;
            left: 10px;
            width: 50px;
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
              margin-left: 4px;
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
  }
}
</style>
