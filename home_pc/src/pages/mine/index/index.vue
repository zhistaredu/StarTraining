
<template>
  <div class="temp_index_mine">
    <div class="mine-top">
      <div class="cont_all_width">
        <div class="user-data-cont">
          <div class="avatar" >
            <img :src="userInfo.avatar" alt="" class="user-avatar" />
          </div>
          <div class="user-data">
            <div class="username">{{userInfo.userName}}</div>
            <div class="compony">{{ userInfo.companyName }}</div>
          </div>
        </div>

        <div class="learn-time">
          <div class="item">
            <div class="num">{{ creditNum }}</div>
            <div class="desc">获得学分</div>
          </div>
          <div class="line" style="color: #eee;">|</div>
          <div class="item">
            <div class="num">{{ certificateNum || 0 }}</div>
            <div class="desc">获得证书</div>
          </div>

        </div>
      </div>
    </div>
    <div class="mine-bottom cont_all_width">
      <div class="mine-lf">


        <div class="mine-list">
          <div class="list-item" :class="{ active: listType == 'credit' }" @click="changeListType('credit')">
            <span><i class="iconfont icon-jifenxuefen"></i><span>学分记录</span></span>
            <i class="iconfont icon-xiangyoujiantou"></i>
          </div>
          <div class="list-item" :class="{ active: listType == 'certificate' }" @click="changeListType('certificate')">
            <span><i class="iconfont icon-zhengshu2"></i><span>我的证书</span></span>
            <i class="iconfont icon-xiangyoujiantou"></i>
          </div>

        </div>
      </div>

      <div class="mine-rg">
        <!-- 学分记录 -->
        <creditList v-if="listType == 'credit'"></creditList>
        <!-- 证书 -->
        <CertificateList v-else-if="listType == 'certificate'"></CertificateList>
      </div>
    </div>

  </div>
</template>

<script>
import { getUserInfo, insertPicture } from '@/api/user'

import CertificateList from '../components/certificateList'
import creditList from '../components/creditList'

export default {
  name: 'mineIndex',
  components: {
    CertificateList,
    creditList,
  },
  data() {
    return {
      listType: this.$route.query.type || 'credit',
      learnRequireTime: 0,
      learnEletiveTime: 0,
      creditNum: 0, // 学分
      certificateNum: 0, // 证书
      examCount: 0,
      userInfo: {},
      windowHeight: 0,
    }
  },
  mounted() {
    this.windowHeight = window.innerHeight - 90
    this.getUserInfo()
  },
  methods: {
    changeListType(type) {
      this.listType = type
    },
    getUserInfo() {
      getUserInfo({})
        .then((res) => {
          this.learnRequireTime = res.reqNum
          this.learnEletiveTime = res.optNum
          this.creditNum = res.creditNum
          this.certificateNum = res.certificateNum
          this.examCount = res.examNum
          this.userInfo = res.userInfo
          window.localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
          window.localStorage.setItem('userId', this.userInfo.userId)
          window.localStorage.setItem('companyId', this.userInfo.companyId)
          window.localStorage.setItem('companyName', this.userInfo.companyName)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    /**
     * 图片上传裁切成功
     */
    successUpload(url) {
      // console.log(url)

      insertPicture({ avatar: url }).then((res) => {
        this.$message({
          message: '上传头像成功',
          type: 'success',
        })
        this.getUserInfo()
      })
    },
  },
}
</script>
<style lang="less">
.temp_index_mine {
  width: 100%;
  height: 100%;
  background: #f5f5f5;
  box-sizing: border-box;

  .mine-top {
    width: 100%;
    height: 150px;
    background: #5881db;
    background: url('~@/assets/img/user-top.png');

    .cont_all_width {
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
  }

  .mine-bottom {
    margin: 10px auto 0;
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
  }

  @media screen and (max-width: 1300px) {

    .mine-lf {
      width: 230px;
      height: 100%;
      flex-shrink: 0;
      box-sizing: border-box;
      background: #fff;
      border-radius: 10px;
    }

    .mine-rg {
      width: 760px;
      height: 100%;
      box-sizing: border-box;
      background: #fff;
      border-radius: 10px;
      overflow: hidden;
    }
  }

  @media screen and (min-width: 1301px) {

    .mine-lf {
      width: 280px;
      height: 100%;
      flex-shrink: 0;
      box-sizing: border-box;
      background: #fff;
      box-sizing: border-box;
      border-radius: 10px;
    }

    .mine-rg {
      width: 910px;
      height: 100%;
      box-sizing: border-box;
      background: #fff;
      border-radius: 10px;
      overflow-y: auto;
    }
  }

  .user-data-cont {
    width: 100%;
    padding: 10px 0;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: flex-start;

    .avatar {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      background-color: #eee;
      overflow: hidden;
      cursor: pointer;
      border: 2px solid #eee;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }

    .user-data {
      color: #fff;
      font-size: 14px;
      padding-left: 20px;
      box-sizing: border-box;
      text-align: left;

      .name {
        font-size: 18px;
        // width: 100px;
      }

      .compony {
        margin-top: 10px;
        font-size: 14px;
      }
    }
  }

  .learn-time {
    color: #fff;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    padding-right: 50px;

    .line {
      margin: 0 20px;
    }

    .item {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      margin: 0 5px;

      .num {
        font-size: 18px;
        font-weight: bold;
        color: #fff;
        margin-bottom: 10px;
      }

      .desc {
        font-size: 14px;
        white-space: nowrap;
      }
    }
  }

  .mine-list {
    width: 100%;
    padding: 10px 0;
    box-sizing: border-box;
    border-top: 1px solid #f5f5f5;

    .list-item {
      width: 100%;
      height: 46px;
      padding: 0 10px;
      box-sizing: border-box;

      display: flex;
      align-items: center;
      justify-content: space-between;
      font-size: 15px;
      color: #333;
      cursor: pointer;

      &:hover {
        color: #5881db;
      }

      i.iconfont {
        font-size: 12px;
      }

      &.active {
        font-weight: bold;
        color: #5881db;

        i.iconfont {
          color: #5881db;
        }
      }

      span {
        display: flex;
        align-items: center;

        i.iconfont {
          font-size: 16px;
          color: #333;
          margin-right: 5px;
        }

        i.icon-zhengshu1 {
          font-size: 16px;
        }
      }
    }
  }
}
</style>
