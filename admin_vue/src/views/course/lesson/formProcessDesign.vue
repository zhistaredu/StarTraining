<template>
  <el-container>
    <el-header style="background: #fff;">
      <layout-header></layout-header>
    </el-header>
    <div class="layout-body">
      <form-base-setting ref="baseSetting" :uuid="uuid"/>
    </div>
  </el-container>

</template>

<script>
import layoutHeader from './layoutHeader'
import formBaseSetting from '@/views/course/lesson/layout/formBaseSetting'
import {
    getLesson
} from "@/api/learn/lesson";

export default {
  name: "FormProcessDesign",
  components: {layoutHeader, formBaseSetting},
  data() {
    return {
      uuid: this.$route.query.uuid,
    }
  },
  computed: {
    
  },
  created() {

  },
  mounted() {
    
  },
  methods: {
    
  }
}
</script>

<style lang="scss" scoped>

.layout-body {
  min-width: 980px;
  background-color: #f5f6f6;
}

::v-deep .el-step {
  .is-success {
    color: #2a99ff;
    border-color: #2a99ff;
  }
}

.err-info{
  max-height: 180px;
  overflow-y: auto;
  & > div{
    padding: 5px;
    margin: 2px 0;
    width: 220px;
    text-align: left;
    border-radius: 3px;
    background: rgb(242 242 242);
  }
  i{
    margin: 0 5px;
  }
}

::-webkit-scrollbar {
  width: 2px;
  height: 2px;
  background-color: white;
}

::-webkit-scrollbar-thumb {
  border-radius: 16px;
  background-color: #e8e8e8;
}

</style>
