<template>
  <div class="certificate_list">
    <div class="top-filter">
      <div class="lf">

      </div>
      <div class="search">
        <el-input v-model="searchValue" placeholder="请输入内容"></el-input>
        <span @click="searchList">搜索</span>
      </div>
    </div>
    <div class="course-list" v-loading="isLoading">
      <div class="course-item" v-for="(item, index) in certificateList" :key="index" @click="gotoDetail(item)">
        <div class="course-cont">
          <div class="img">
            <img :src="item.certImage" alt="" v-if="item.certImage" />
            <img :src="item.coverImg" alt="" v-else />
          </div>
          <div class="name">{{ item.certTitle }}</div>
        </div>
      </div>
      <div class="nodata" v-if="certificateList.length == 0">
        <img src="@/assets/img/nodata.png" alt="" />
        <div>暂无数据</div>
      </div>
    </div>

    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
      :page-sizes="[9, 12, 24, 32]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"
      v-if="certificateList.length">
    </el-pagination>

    <div class="certificate-detail-box" v-if="isShowDetail">
      <div class="certificate-detail" v-loading="isLoadingDetail">
        <!-- <div class="certificate-top">{{ certificateDetail.certTitle }}</div> -->
        <div class="certificate-center">
          <div class="img">
            <img :src="certificateDetail.certImage" alt="" v-if="certificateDetail.certImage" />
            <img :src="certificateDetail.coverImg" alt="" v-else />
            <div class="user-name">
              {{certificateDetail.userName}}
            </div>
          </div>
          <!-- <div class="canvas">
            <canvas ref="imageCanvas"></canvas>
          </div> -->

        </div>
        <div class="close" @click="closedetail">
          <i class="iconfont icon-cuohao"></i>
        </div>
        <!-- <div class="certificate-bottom"> -->
          <!-- <div class="btn" @click="saveImge">保存图片</div> -->
          <!-- <div class="btn" @click="closedetail">关闭</div> -->
        <!-- </div> -->
      </div>
    </div>


  </div>
</template>

<script>
import { getCertificateList, getCertificateDetails } from '@/api/certificate'
export default {
  name: 'certificateList',
  components: {  },
  data() {
    return {
      searchValue: '', // 搜索内容
      certificateList: [], // 证书列表
      currentPage: 1, // 当前页数
      pageSize: 9, // 一页多少条
      total: 100, // 一共多少
      categoryId: '', // 分类id
      isLoading: false,
      isLoadingDetail: false,
      isShowDetail: false,
      certificateDetail: {},
    }
  },
  mounted() {
    // this.getCertificateList()
  },
  methods: {

    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.getCertificateList()
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.getCertificateList()
    },
    // 搜索
    searchList() {
      this.currentPage = 1
      this.getCertificateList()
    },
    // 获取课程列表
    getCertificateList() {
      this.isLoading = true
      getCertificateList({
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        params: {
          keyword: this.searchValue
        }
      }).then((res) => {
        this.certificateList = res.rows
        this.total = res.total
        this.isLoading = false
      })
    },
    // 前往学习
    gotoDetail(item) {
      this.isShowDetail = true
      this.isLoadingDetail = true
      getCertificateDetails(item.uuid).then((res) => {
        this.certificateDetail = res.data
        this.isLoadingDetail = false
      })

    },
    closedetail() {
      this.isShowDetail = false
    },
    saveImge() {
      console.log('保存')
      // 获取Canvas元素
      const canvas = this.$refs.imageCanvas;

      // 设置画布尺寸与原始图像尺寸一样
      canvas.width = 1800;
      canvas.height = 3200;

      // 获取2D上下文
      const ctx = canvas.getContext('2d');

      // 从URL加载图像
      const img = new Image();
      img.src = this.certificateDetail.certImage;

      // 当图像加载完成时，将其绘制到Canvas上
      img.onload = () => {
        ctx.drawImage(img, 0, 0);
        // 设置文本样式
        ctx.font = '60px Arial'; // 设置字体大小和字体类型
        ctx.fillStyle = '#333'; // 设置填充颜色
        ctx.textAlign = 'center'; // 可以是 'start', 'end', 'left', 'right', 'center'
        ctx.fillText('Hello, World!', canvas.width / 2, 1200); // 参数分别是文本内容、x坐标、y坐标

        // 导出为PNG格式的Base64编码字符串
        const dataUrl = canvas.toDataURL('image/png');

        // 创建一个a标签，模拟点击下载
        const link = document.createElement('a');
        link.href = dataUrl;
        link.download = 'saved_image.png';
        link.dispatchEvent(new MouseEvent('click'));
      };
    }
  },
}
</script>
<style lang="less">
.certificate-detail-box {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, .3);
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;

  .certificate-detail {
    width: 98%;
    height: 98%;
    background: #fff;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    position: relative;
    .close{
      position: absolute;
      top: 20px;
      right: 20px;
      width: 50px;
      height: 50px;
      cursor: pointer;
      .iconfont{
        font-size: 30px;
        color: #333;
      }
    }

    .certificate-top {
      width: 100%;
      height: 60px;
      line-height: 60px;
      color: #333;
      font-size: 16px;
      flex-shrink: 0;
      padding: 0 30px;
      box-sizing: border-box;
      font-weight: bold;
      border-bottom: 1px solid #eee;
    }

    .certificate-center {
      height: 100%;
      padding: 10px 0;
      box-sizing: border-box;
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      position: relative;

      .canvas {
        position: absolute;
        width: 500px;
        height: 500px;
        overflow: auto;
        left: 0;
        top: 0;
      }

      .img {
        width: 100%;
        height: 100%;
        position: relative;

        img {
          width: 100%;
          height: 100%;
          object-fit: contain;
        }

        .user-name {
          position: absolute;
          top: 200px;
          width: 100%;
          text-align: center;
          font-size: 18px;
          color: #333;
        }
      }
    }

    .certificate-bottom {
      flex-shrink: 0;
      width: 100%;
      height: 70px;
      border-top: 1px solid #eee;
      display: flex;
      align-items: center;
      justify-content: center;

      .btn {
        width: 120px;
        height: 40px;
        margin: 0 10px;
        background: #5881db;
        text-align: center;
        line-height: 40px;
        color: #fff;
        font-size: 14px;
        cursor: pointer;
        border-radius: 4px;
      }
    }
  }
}

.certificate_list {
  width: 100%;
  height: 100%;
  background: #fff;
  overflow-x: hidden;
  overflow: hidden;

  .top-filter {
    width: 100%;
    height: 50px;
    border-radius: 10px;
    padding: 0 10px;
    box-sizing: border-box;
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .lf {
      display: flex;
      align-items: center;
    }

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

  .course-list {
    width: 100%;
    height: calc(100% - 100px);
    overflow-y: auto;
    display: flex;
    align-content: flex-start;
    flex-wrap: wrap;
    // padding: 0 10px;
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
          background: #eee;
          overflow: hidden;
          position: relative;

          img {
            width: 100%;
            height: 100%;
            object-fit: contain;
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
