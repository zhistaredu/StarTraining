<template>
  <div class="ellip-box" :class="{ 'line': row === 1, 'lines': row > 1 }" :title="hoverTip ? content : null" :style="{ '--row': row }"
    v-if="type == 'dept'">
    <slot name="pre"></slot>
    <div v-for="(item, index) in arr" :key="index" class="show-dept-name">
      <i class="iconfont icon-xiangyoujiantou" v-if="index != 0"></i>
      <OpenDataCom type="departmentName" :openid="item" :defaultname="item"></OpenDataCom>
    </div>
    <!-- {{content}} -->
    <!-- <OpenDataCom type="departmentName" :openid="content" :defaultname="content"></OpenDataCom> -->
  </div>
  <div :class="{ 'line': row === 1, 'lines': row > 1 }" :title="hoverTip ? content : null" :style="{ '--row': row }"
    v-else>
    <slot name="pre"></slot>
    {{ content }}
  </div>
</template>

<script>
//超出指定行数自动隐藏文字
export default {
  name: "Ellipsis",
  install(Vue) {
    Vue.component('ellipsis', this)
  },
  components: {

  },
  props: {
    row: {
      type: Number,
      default: 1
    },
    hoverTip: {
      type: Boolean,
      default: false
    },
    content: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: ''
    }
  },
  watch: {

    content: {
      handler(newName, oldName) {
        let arr = newName.split('>')
        this.arr = arr
        this.$forceUpdate()
        console.log(this.arr)
      },
      deep: true,
      immediate: true
    }
  },
  data() {
    return {
      arr: []
    }
  },
  methods: {},
  mounted() {
    let arr = this.content.split('>')
    this.arr = arr
  }
}
</script>

<style lang="less" scoped>
.line {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.lines {
  display: -webkit-box;
  word-break: break-all;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: var(--row);
  -webkit-box-orient: vertical;
}
.ellip-box{
  display: flex !important;
  align-items: center !important;
}
.show-dept-name {
  display: flex;
  align-items: center;
}
</style>
