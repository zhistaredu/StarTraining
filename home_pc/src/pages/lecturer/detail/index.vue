<template>
  <div class="lecturer-detail-box" :style="{ height: windowHeight + 'px' }">
    <div class="lecturer-data">
      <div class="avatar">
        <img :src="lecturerDetail.lectorAvatar" alt="" />
        <div class="sex" :class="{ 'man': lecturerDetail.sex == 'man', 'woman': lecturerDetail.sex == 'woman' }">
          <i class="iconfont icon-icon2" v-if="lecturerDetail.sex == 'man'"></i>
          <i class="iconfont icon-icon1" v-else></i>
        </div>
      </div>
      <div class="name">
        {{lecturerDetail.lectorName}}
      </div>
      <div class="people">{{ lecturerDetail.lectorGradeName }}</div>
      <div class="bottom">
        <div>课程：{{ lecturerDetail.courseCount }}</div>
      </div>

      <div class="desc">
        <div class="tit">个人介绍：</div>
        <div class="cont">
          {{ lecturerDetail.synopsis }}
        </div>
      </div>
    </div>
    <div class="lecturer-course">
      <div class="top-filter">
        <div class="status">
          <span class="tit">课程</span>
        </div>

      </div>
      <div class="course-list" v-loading="isLoading">
        <div class="course-item" v-for="(item, index) in trainList" :key="index" @click="gotoDetail(item)">
          <div class="course-cont">
            <div class="img">
              <img :src="item.imgUrl" alt="" />
            </div>
            <div class="name">{{ item.title }}</div>
            <div class="people">
              <i class="iconfont icon-tuandui"></i>
              <span>{{ item.electiveNum }}人学习</span>
            </div>
          </div>
        </div>
        <div class="nodata" v-if="trainList.length == 0">
          <img src="@/assets/img/nodata.png" alt="" />
          <div>暂无数据</div>
        </div>
      </div>
      <div class="bottom-block">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
          :page-sizes="[9, 12, 24, 32]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
          :total="total" v-if="trainList.length">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { getCourseList } from '@/api/course'
import { getLecturerDetails } from '@/api/lecturer'
export default {
  name: 'lecturerDetail',
  components: {  },
  data() {
    return {
      lectorId: this.$route.query.uuid,
      lecturerDetail: {},
      activeName: 'course',
      searchValue: '', // 搜索内容
      trainList: [], // 课程列表
      currentPage: 1, // 当前页数
      pageSize: 9, // 一页多少条
      total: 100, // 一共多少
      categoryId: 1, // 分类id
      categoryList: [], // 分类列表
      defaultProps: {
        children: 'children',
        label: 'label',
      },
      windowHeight: 0,
      isShowCate: true, // 是否显示分类
      isLoading: false,
    }
  },
  computed: {},
  mounted() {
    this.windowHeight = window.innerHeight - 90
    // console.log(this.windowHeight)
    this.getLecturerDetails()
    this.getCourseList()
  },
  methods: {
    // 获取讲师详情
    getLecturerDetails() {
      getLecturerDetails(this.lectorId).then((res) => {
        this.lecturerDetail = res.data
      })
    },

    // 改变每页条数
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.pageSize = val
      if (this.activeName == 'course') {
        this.getCourseList()
      } else {
        this.getTrainList()
      }
    },
    // 切换页码
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
      if (this.activeName == 'course') {
        this.getCourseList()
      } else {
        this.getTrainList()
      }
    },
    // 搜索
    searchList() {
      this.currentPage = 1
      this.getCourseList()
    },

    // 获取课程列表
    getCourseList() {
      this.isLoading = true
      getCourseList({
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        categoryId: '',
        keyword: this.searchValue,
        lectorId: this.lectorId,
      }).then((res) => {
        this.trainList = res.rows
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
    },
  },
}
</script>

<style scoped lang="less">
@import 'index';
</style>
