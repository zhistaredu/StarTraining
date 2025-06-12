<style lang="less">
	.error-index {
    margin-top: 200px;
    text-align: center;
    .text{
      margin-bottom: 20px;
      font-size: 16px;
    }
    .btn {
			width: 120px;
		}
	}
</style>

<template>
  <div class="error-index">
      <p class="text">页面不存在，请检查路径~</p>
      <el-button class="btn" round type="primary" @click="goHome">去首页</el-button>
  </div>
</template>

<script>
	export default {
		name: 'error',
		data() {
			return {
			}
		},
		methods: {
      goHome() {
        this.$router.push('/')
      }
    }
	}
</script>
