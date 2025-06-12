<template>
  <div>
    <div class="header">
      <el-menu
        :default-active="value"
        active-text-color="#5881db"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item
          index="baseSetting"
          @click="gotoPage('baseSetting')"
          v-if="courseInfo.uuid != ''"
          >① 基础信息</el-menu-item
        >
        <el-menu-item
          index="lessonSetting"
          @click="gotoPage('lessonSetting')"
          v-if="courseInfo.uuid != ''"
          >② 课件列表</el-menu-item
        >
        <el-menu-item
          index="orgSetting"
          @click="gotoPage('orgSetting')"
          v-if="courseInfo.uuid != ''"
          >③ 可见范围
        </el-menu-item>
      </el-menu>
      <div class="publish" v-if="courseStatus != 'published'">
        <el-button size="mini" type="primary" @click="publish"
          ><i class="el-icon-s-promotion"></i>发布</el-button
        >
      </div>
      <div class="back">
        <el-button
          @click="goback"
          size="medium"
          icon="el-icon-arrow-left"
          circle
        ></el-button>
        <span>
          <span>{{ courseInfo.title }}</span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import { courseDetail } from "@/api/learn/course";

export default {
  name: "LayoutHeader",
  props: {
    value: {
      type: String,
      default: "baseSetup",
    },
    courseStatus: {
      type: String,
    },
  },
  data() {
    return {};
  },
  computed: {
    courseInfo() {
      return this.$store.state.course.courseInfo;
    },
  },
  created() {

  },
  mounted() {

  },
  methods: {

    publish() {
      this.$emit("publish", { uuid: this.courseInfo.uuid });
    },
    goback() {
      this.$router.push({
        path: "/course/courseList",
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
    gotoPage(path) {
      this.$emit("input", path);
    },
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
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

  .publish {
    position: absolute;
    top: 15px;
    right: 20px;
    z-index: 1000;

    i {
      margin-right: 6px;
    }

    button {
      border-radius: 15px;
    }
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
