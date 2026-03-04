<template>
  <div class="change_code_bx">
    <div class="code_box">
      <div class="line">
        <div class="tit">请输入原密码：</div>
        <div class="input">
          <input type="text" placeholder="请输入原密码" v-model="oldCode">
        </div>
      </div>
      <div class="line">
        <div class="tit">请输入新密码：</div>
        <div class="input">
          <input type="text" placeholder="请输入新密码" v-model="newCode">
        </div>
      </div>

      <div class="btn_box">
        <div class="btn" @click="confirmChange">确认修改</div>
      </div>
    </div>

  </div>
</template>

<script>
import { codeChange } from '@/api/user'
export default {
  name: 'changeCode',
  components: {},
  data() {
    return {
      oldCode: '', // 原始密码
      newCode: '', // 新密码
    }
  },
  computed: {},
  mounted() {},
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    // 确认修改
    confirmChange() {
      if(!this.oldCode) {
        this.$message({
          showClose: false,
          message: '请输入旧密码',
          type: 'success',
        })
        return
      }
      if(!this.newCode) {
        this.$message({
          showClose: false,
          message: '请输入新密码',
          type: 'success',
        })
        return
      }
      codeChange({
        oldPassword: this.oldCode,
        newPassword: this.newCode,
      }).then(res => {
        this.$message({
          showClose: false,
          message: '修改密码成功',
          type: 'success',
        })
        this.goBack()
      })
    },
  }
}
</script>

<style  lang="less">
.change_code_bx {
  width: 100%;
  height: 100%;
  overflow-y: auto;
  box-sizing: border-box;
  background-color: #fff;
  padding: 20px 30px 0;
  box-sizing: border-box;
  .code_box{
    width: 100%;
    padding: 5px 10px;
    box-sizing: border-box;
    .line{
      width: 100%;
      .tit{
        line-height: 50px;
        font-size: 14px;
      }
      .input{
        width: 500px;
        input{
          width: 100%;
          height: 40px;
          border: 1px solid #ccc;
          outline: none;
          border-radius: 4px;
          box-sizing: border-box;
          font-size: 14px;
          padding: 0 10px;
        }
      }
    }
  }

  .btn_box{
    width: 100%;
    height: 100px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding-left: 200px;
    box-sizing: border-box;
    .btn{
      width: 120px;
      height: 36px;
      background-color: #5881db;
      border-radius: 4px;
      text-align: center;
      line-height: 36px;
      font-size: 14px;
      color: #fff;
    }
  }
}
</style>
