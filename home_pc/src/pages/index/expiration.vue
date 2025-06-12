<template>
  <div id="expiration">
    <div class="expiration-cont">
      <i class="iconfont icon-daoqi"></i>
      <div class="tit">试用已到期</div>
      <div class="desc">应用已到试用期限，请联系服务商购买应用</div>

      <div class="btn">联系服务商</div>
    </div>
  </div>
</template>
  
<script>
export default {
  name: 'Expiration',
  data() {
    return {}
  },
  methods: {},
  mounted() {},
}
</script>
  
  
<style lang="less">
#expiration {
  .expiration-cont {
    width: 100%;
    height: 500px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .iconfont{
        font-size: 80px;
        color: #e4b211;
    }
    .tit{
        font-size: 24px;
        color: #333;
        font-weight: bold;
        margin: 40px 0 20px;
    }
    .desc{
        font-size: 18px;
        color: #333; 
    }
    .btn{
        margin-top: 30px;
        width: 120px;
        height: 40px;
        background: #5881db;
        text-align: center;
        line-height: 40px;
        border-radius: 6px;
        color: #fff;
        font-size: 16px;
        cursor: pointer;
    }

  }
}
</style>