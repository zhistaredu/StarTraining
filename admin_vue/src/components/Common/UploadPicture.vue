<template>
  <div>
    <el-row :gutter="30">
      <el-col class="colLeft">
        <div class="img-coationar mar-bt-15 relative">
          <div class="bnt">
            <el-button
              size="small"
              type="primary"
              class="mar-rg-15 mb20"
              @click="editPic"
              style="padding: 0 20px;height: 36px;"
              >{{ editName }}</el-button
            >
            <div class="mb20">
              <el-tooltip
                class="item"
                effect="dark"
                content="上传图片"
                placement="top-start"
                v-show="!editPicture"
              >
                <el-button
                  icon="el-icon-upload2"
                  size="small"
                  class="mar-rg-15"
                  @click="$refs.uploadImage.open()"
                >
                </el-button>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                content="删除图片"
                placement="top-start"
                v-show="editPicture"
              >
                <el-button
                  icon="el-icon-delete"
                  class="mar-rg-15"
                  type="danger"
                  size="small"
                  @click.stop="deletePicList"
                ></el-button>
              </el-tooltip>
            </div>
          </div>
          <div class="pictrueList acea-row" v-loading="loadingPic">
            <div v-show="isShowPic" class="no-images">
              <i
                class="el-icon-picture"
                style="font-size: 60px; color: rgb(219, 219, 219)"
              />
              <span class="imagesNo_sp">图片库为空</span>
            </div>
            <div class="picconters scrollbarAll" style="max-height: 500px">
              <div
                v-for="(item, index) in pictrueList"
                :key="index"
                class="pic-item"
              >
                <span class="choiced" v-if="item.isSelect"
                  ><i class="iconfont icon-duihao"></i
                ></span>
                <img
                  style="object-fit: contain"
                  v-lazy="item.cdnObject ? item.cdnObject : localImg"
                  :class="item.isSelect ? 'on' : ''"
                  @click="choiceImage(item)"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="block">
          <el-pagination
            :page-sizes="[10, 20, 30, 40]"
            :page-size="tableData.limit"
            :current-page="tableData.page"
            :pager-count="5"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pictrueTotal"
            @size-change="handleSizeChange"
            @current-change="pageChange"
          />
        </div>
      </el-col>
    </el-row>
    <uploadImage
      :aspectRatio="25 / 9"
      :width="750"
      :height="270"
      directory="course/image"
      @success="successUpload"
      ref="uploadImage"
      v-if="type == 'banner' || type == 'survey'"
    ></uploadImage>

    <uploadImage
      :aspectRatio="16 / 9"
      :width="960"
      :height="540"
      directory="course/image"
      @success="successUpload2"
      ref="uploadImage"
      v-else
    ></uploadImage>
  </div>
</template>

<script>
import uploadImage from "../uploadImage/uploadImage";
import { listPicture, insertPicture, deletePicture } from "@/api/file/picture";
export default {
  components: {
    uploadImage,
  },
  name: "Upload",
  props: {
    // 上传什么类型的图片
    type: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      loadingPic: false,
      tableData: {
        page: 1,
        limit: 10,
        type: this.type
      },
      pictrueList: [],
      pictrueTotal: 0,
      isShowPic: false,
      checkPicList: [], // 选中的图片数组
      ids: [],
      localImg: "",
      editName: "编辑图片",
      editPicture: false, // 是否编辑图片

    };
  },
  mounted() {
    this.getPicList();
  },
  methods: {
    // 编辑取消编辑图片
    editPic() {
      this.editPicture = !this.editPicture;
      this.checkPicList = [];
      this.pictrueList.map((el, i) => {
        el.isSelect = false;
      });
      this.$forceUpdate();
      if (this.editPicture) {
        this.editName = "取消编辑";
      } else {
        this.editName = "编辑图片";
      }
    },
    /**
     * 图片上传裁切成功
     */
    successUpload(url) {
      this.loadingPic = true;
      this.pictrueList = [];
      this.isShowPic = true;
      let data = {
        object: url,
        type: this.type,
      }
      insertPicture(data).then((res) => {
        this.getPicList();
      });
    },
    /**
     * 图片上传裁切成功
     */
     successUpload2(url) {
      this.loadingPic = true;
      this.pictrueList = [];
      this.isShowPic = true;
      let data = {
        object: url,
        type: this.type,
      }
      insertPicture(data).then((res) => {
        this.getPicList();
      });
    },
    // 文件列表
    getPicList() {
      let that = this;
      that.pictrueList = [];
      this.loadingPic = true;
      this.isShowPic = true;
      if (this.type == "banner") {
        // 选取轮播图
        listPicture(this.tableData).then((res) => {
          that.isShowPic = false;
          that.loadingPic = false;
          that.pictrueList = res.rows;
          that.pictrueTotal = res.total;
        });
      } else {
        // 选取课程封面
        listPicture(this.tableData).then((res) => {
          that.isShowPic = false;
          that.loadingPic = false;
          that.pictrueList = res.rows;
          that.pictrueTotal = res.total;
        });
      }
    },
    pageChange(page) {
      this.tableData.page = page;
      this.checkPicList = [];
      this.getPicList();
    },
    handleSizeChange(val) {
      this.tableData.limit = val;
      this.getPicList();
    },
    // 选中图片
    choiceImage(item) {
      let activeIndex = 0;
      if (!item.isSelect) {
        this.$set(item, "isSelect", true);
        this.checkPicList.push(item);
      } else {
        this.$set(item, "isSelect", false);
        this.checkPicList.map((el, index) => {
          if (el.uuid == item.uuid) {
            activeIndex = index;
          }
        });
        this.checkPicList.splice(activeIndex, 1);
      }
      this.ids = [];
      this.checkPicList.map((item, i) => {
        this.ids.push(item.uuid);
      });
      this.$forceUpdate();

      if (this.editPicture) {
      } else {
        this.$emit("getImage", [...this.checkPicList]);
      }
    },

    // 删除图片
    deletePicList() {
      if (this.checkPicList.length == 0) {
        this.$message.warning("请先选择图片");
      } else {
        this.$confirm("确定要删除选中的图片吗?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then((action) => {
            if (action == "confirm") {
              //删除
              deletePicture(this.ids).then((res) => {
                this.ids = []
                this.getPicList();
                this.$message({
                  type: "success",
                  message: "删除成功!",
                });
              });
            }
          })
      }
    },
  },
};
</script>

<style scoped lang="less">
.mar-rg-15 {
  margin-right: 15px;
}

.picconters {
  display: flex;
  flex-wrap: wrap;
  /*max-height: 296px;*/
  overflow: auto;
}

.picconters:after {
  content: "";
  width: 410px !important;
}

.pic-item {
  margin-right: 15px;
  margin-bottom: 10px;
  width: 228px;
  height: 130px;
  position: relative;
  cursor: pointer;

  img {
    width: 100%;
    height: 100%;
    display: block;
  }

  .choiced {
    position: absolute;
    bottom: 6px;
    right: 6px;
    display: inline-block;
    min-width: 20px;
    height: 20px;
    border-radius: 10px;
    background: #5881db;
    font-size: 14px;
    text-align: center;
    color: #fff;
    line-height: 20px;
  }
}

.img-coationar {
  width: 99%;
  height: 100%;

  .bnt {
    width: 100%;
    padding: 0 13px 10px 7px;
    box-sizing: border-box;
    display: flex;
  }

  .pictrueList {
    /*padding-left: 15px;*/
    width: 100%;
    height: 500px;

    el-image {
      width: 100%;
      border: 2px solid #fff;
    }

    .on {
      border: 2px solid #5881db;
    }
  }

  .el-image {
    width: 110px;
    height: 110px;
    cursor: pointer;
  }

  .no-images {
    width: 100%;
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
    height: 100%;

    .imagesNo_sp {
      font-size: 13px;
      color: #dbdbdb;
      line-height: 3;
    }
  }
}

.relative {
  position: relative;
}

</style>
