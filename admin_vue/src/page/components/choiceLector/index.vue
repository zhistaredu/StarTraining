<template>
  <div id="lector-choice-com">

    <div class="choice-lector-box">
      <div class="choiced-lector" @click="isShow = !isShow">
        <div class="name-box" v-if="nowlectorName">
          <OpenDataCom type="userName" :openid="nowlectorName" :defaultname="nowlectorName"></OpenDataCom>
        </div>
        <span v-else>请选择讲师</span>

        <i class="iconfont icon-xiangxiajiantou"></i>
      </div>
      <div class="lector-list" v-if="isShow">
        <ul>
          <li v-for="item in lectorList" :key="item.value" @click="choiceItem(item)">
            <OpenDataCom type="userName" :openid="item.label" :defaultname="item.label"></OpenDataCom>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
import {
  getLector
} from "@/api/system/lector";
export default {
  props: ["lectorId", "lectorName"],
  components: {

  },
  data() {
    return {
      isShow: false,
      lectorList: [],//讲师列表

      nowlectorId: '',
      nowlectorName: '',

    };
  },
  computed: {

  },
  mounted() {
    this.getLectorList()
  },
  methods: {
    choiceItem(item) {
      // console.log(item)
      this.nowlectorId = item.value
      this.nowlectorName = item.label
      this.$emit('choiceLector', { lectorId: item.value, lectorName: item.label })
      this.isShow = false
    },
    getLectorList() {
      let that = this;
      let data = {};
      getLector(data).then((res) => {
        if (res.code === 200) {
          res.data.map(function (items) {
            let lectorData = {
              value: items.lectorId,
              label: items.lectorName,
            }
            that.lectorList.push(lectorData);
            setTimeout(() => {
              that.nowlectorId = that.lectorId
              that.nowlectorName = that.lectorName
            }, 500);
          })
        }
      })
        .catch((error) => {
          console.error(error);
        });
    },

  },
};
</script>
<style lang="less">
#lector-choice-com {


  .choice-lector-box {
    width: 300px;
    height: 36px;
    position: relative;

    .choiced-lector {
      width: 300px;
      height: 36px;
      padding-left: 15px;
      padding-right: 10px;
      box-sizing: border-box;
      border-radius: 4px;
      border: 1px solid #DCDFE6;
      box-sizing: border-box;
      cursor: pointer;
      display: flex;
      justify-content: space-between;
      align-items: center;

      >span{
        font-size: 14px;
        color: #666;
      }
      .name-box{
        flex: 1;
      }
      .iconfont{
        flex-shrink: 0;
        font-size: 12px;
        color: #999;
      }
    }

    .lector-list {
      position: absolute;
      top: 36px;
      left: 0;
      z-index: 99;
      width: 300px;
      background: #fff;
      box-shadow: 0 0 12px rgba(0, 0, 0, .3);
      border-radius: 4px;

      ul {
        margin: 0;
        padding: 0;

        li {
          padding: 0 10px;
          line-height: 40px;
          box-sizing: border-box;
          cursor: pointer;
        }
      }
    }
  }


}
</style>
