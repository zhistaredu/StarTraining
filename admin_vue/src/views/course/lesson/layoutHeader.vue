<template>
  <div>
    <div class="header">
      <div class="back">
        <el-button
          @click="goback"
          size="medium"
          icon="el-icon-arrow-left"
          circle
        ></el-button>

      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "LayoutHeader",
  data() {
    return {};
  },
  created() {

  },
  mounted() {

  },
  methods: {
    goback() {
      this.$router.push({
        path: "/course/lessonList",
        query: {
          status:
            this.courseStatus == "unpublished"
              ? "0"
              : this.courseStatus == "close"
              ? "2"
              : "1",
        },
      });
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .header {
  min-width: 980px;
  position: relative;

  .el-menu {
    top: 0;
    z-index: 999;
    display: flex;
    justify-content: center;
    width: 100%;
  }

  .back {
    position: absolute;
    z-index: 1000;
    top: 10px;
    left: 20px;
    font-size: small;

    span {
      margin-left: 6px;
      i {
        border-radius: 10px;
        padding: 7.8px;
        font-size: 20px;
        color: #ffffff;
        margin: 0 10px;
      }
    }
  }
}
</style>
