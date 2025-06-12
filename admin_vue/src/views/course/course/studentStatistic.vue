<template>
  <div class="common_statistic_box">
    <div class="top_back">
      <div class="back">
        <el-button @click="goBack" size="medium" icon="el-icon-arrow-left" circle></el-button>
        <span>{{ coursename }}</span>
      </div>
    </div>
    <div class="tab_box">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="部门维度" name="dept"></el-tab-pane>
        <el-tab-pane label="个人维度" name="user"></el-tab-pane>
      </el-tabs>
    </div>
    <div class="ststic_cont">
      <dept-dimension :uuid="uuid" v-if="activeName == 'dept'"/>
      <user-dimension :uuid="uuid" v-if="activeName == 'user'"/>
    </div>
  </div>
</template>

<script>
import { courseDetail } from "@/api/learn/course";
import { getCourseUser } from "@/api/learn/course";
import { treeselect } from "@/api/system/dept";
import userDimension from "./userDimension";
import deptDimension from "./deptDimension";

export default {
  components: {
    deptDimension,
    userDimension
  },
  data() {
    return {
      uuid: this.$route.query.uuid,
      activeName: 'dept',
      coursename: this.$route.query.title, // 课程名称
      isLoading: true,
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    handleClick() {

    }
  },
}

</script>

<style src="@/utils/statistic.less" lang="less"></style>
