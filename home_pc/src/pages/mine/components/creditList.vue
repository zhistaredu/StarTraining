<template>
  <div class="credit_box_list">
    <div class="top-top">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="学分排行" name="rank"></el-tab-pane>
        <el-tab-pane label="学分详情" name="detail"></el-tab-pane>
      </el-tabs>
    </div>
    <div class="course-list" v-loading="isLoading" v-if="activeName == 'detail'">
      <el-table :data="creditList" :border="false" style="width: 100%">

        <el-table-column prop="module" label="类型" width="200">
          <template slot-scope="scope">
            <span v-if="scope.row.module == 'test'">考试</span>
            <span v-else-if="scope.row.module == 'course'">课程</span>
            <span v-else-if="scope.row.module == 'train'">培训</span>
          </template>
        </el-table-column>
        <el-table-column prop="targetTitle" label="所属任务名称">
        </el-table-column>
        <el-table-column prop="credit" label="获得学分" width="200">
          <template slot-scope="scope">
            <div style="text-align: center;width: 60px;">{{ scope.row.credit }}</div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="course-list" v-loading="isLoading" v-else>
      <div class="nodata" v-if="rankList.length == 0">
        <img src="@/assets/img/nodata.png" alt="" />
        <div>暂无数据</div>
      </div>
      <div class="rank-item" v-for="(item, index) in rankList" :key="index">
        <div class="rank-cont">
          <div class="index" :class="{ 'active': index < 3 }">
            <span>{{ index + 1 }}</span>
          </div>
          <div class="avatar">
            <img :src="item.avatar" alt="">
          </div>
          <div class="user-data">
            <div class="username">
              {{item.userName}}
            </div>
          </div>
          <div class="credit">{{ item.totalCredit }}</div>
        </div>
      </div>
    </div>

    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
      :page-sizes="[14, 28, 42]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"
      v-if="creditList.length">
    </el-pagination>

  </div>
</template>

<script>
import { getCreditList, getCreditInfo, getCreditRank } from '@/api/user'
export default {
  name: 'creditList',
  components: {},
  data() {
    return {
      activeName: 'rank',
      searchValue: '', // 搜索内容
      creditList: [], // 学分列表
      rankList: [],
      currentPage: 1, // 当前页数
      pageSize: 14, // 一页多少条
      total: 100, // 一共多少
      categoryId: '', // 分类id
      isLoading: false,

    }
  },
  mounted() {
    // this.getCreditList()
    // this.getCreditRank()
  },
  methods: {
    handleClick(tab, event) {
      // console.log(tab, event)
      if (this.activeName == 'rank') {
        this.currentPage = 1
        this.getCreditRank()
      } else {
        this.currentPage = 1
        this.getCreditList()
      }
    },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.getCreditList()
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.getCreditList()
    },
    // 搜索
    searchList() {
      if (this.activeName == 'rank') {
        this.currentPage = 1
        this.getCreditRank()
      } else {
        this.currentPage = 1
        this.getCreditList()
      }
    },
    // 获取课程列表
    getCreditList() {
      this.isLoading = true
      getCreditList({
        pageNum: this.currentPage,
        pageSize: this.pageSize,
      }).then((res) => {
        this.creditList = res.rows
        this.total = res.total
        this.isLoading = false
      })
    },
    getCreditRank() {
      this.params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize
      }
      getCreditRank(this.params).then(res => {
        if (res.code == '200') {
          this.rankList = res.rows
          this.total = res.total
          this.isLoading = false
        }
      })
    },

  },
}
</script>
<style lang="less">
.credit_box_list {
  width: 100%;
  height: 100%;
  background: #fff;
  overflow-x: hidden;
  overflow: hidden;

  .top-top {
    width: 100%;
    height: 50px;
    border-radius: 10px;
    padding: 10px 10px 0;
    box-sizing: border-box;
    background: #fff;
  }
  .rank-item {
      width: 100%;
      margin-top: 10px;

      .rank-cont {
        width: 100%;
        background-color: #fff;
        border-radius: 6px;
        padding: 6px 10px 10px;
        box-sizing: border-box;
        display: flex;
        align-items: center;
        .index{
          flex-shrink: 0;
          width: 40px;
          height: 40px;
          display: flex;
          align-items: center;
          justify-content: center;
          margin-right: 15px;
          background: #eee;
          color: #333;
          font-size: 14px;
          border-radius: 50%;
          &.active{
            border-radius: 0;
            background: url(~@assets/img/rank.png);
            background-size: 100% 100%;
            color: #fff;
            >span{
              margin-top: 4px;
            }
          }
        }
        .avatar{
          flex-shrink: 0;
          width: 40px;
          height: 40px;
          border-radius: 50%;
          overflow: hidden;
          img{
            width: 100%;
            height: 100%;
          }
        }
        .user-data{
          flex:1;
          overflow: hidden;
          padding: 0 10px;
          box-sizing: border-box;
          font-size: 14px;
        }
        .credit{
          flex-shrink: 0;
          margin-left: 10px;
          color: #ef962f;
          font-size: 16px;
          font-weight: bold;
        }
      }
    }
  .course-list {
    width: 100%;
    height: calc(100% - 80px);
    overflow-y: auto;
    display: flex;
    align-content: flex-start;
    flex-wrap: wrap;
    padding: 20px 15px;
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

        .progress {
          padding: 0 10px;
          box-sizing: border-box;
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
  }

}
</style>
