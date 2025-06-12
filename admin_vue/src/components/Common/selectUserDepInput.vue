<template>
  <el-tooltip
    class="item"
    effect="dark"
    placement="top"
    :disabled="!selectTags.length"
  >
    <div slot="content">
      <span v-for="(item, index) in selectTags" :key="index">
        <OpenDataCom
          :type="item.openDataType"
          :openid="item.showName"
          :defaultname="item.showName"
        ></OpenDataCom>
        <i v-if="selectTags.length - 1 !== index">,</i>
      </span>
    </div>
    <div
      class="choosed_list_dept_user"
      :style="{ minWidth: minWidth }"
      @click="handleClick"
      v-loading="isLoading"
    >
      <div v-if="selectTags.length == 0">
        <el-button class="button-new-tag" size="small" v-if="selectType == 'org'">+ 选择部门 / 人员</el-button>
        <el-button class="button-new-tag" size="small" v-else-if="selectType == 'dept'">+ 选择部门</el-button>
        <el-button class="button-new-tag" size="small" v-else-if="selectType == 'user'">+ 人员</el-button>
      </div>
      <el-tag
        class="tags"
        :closable="closable"
        type="info"
        @close="handleClose(item, index)"
        v-for="(item, index) in selectTags"
        :key="index"
      >
        <OpenDataCom
          :type="item.openDataType"
          :openid="item.showName"
          :defaultname="item.showName"
        ></OpenDataCom>
      </el-tag>
    </div>
  </el-tooltip>
</template>
<script>
export default {
  name: "ChooseUserDep",
  props: {
    // 最小宽度
    minWidth: {
      type: String,
      default: "100px",
    },
    selectList: {
      type: Array,
      default: [],
    },

    // 是否允许删除
    closable: {
      type: Boolean,
      default: true,
    },

    selectType: {
      type: String,
      default: "org",
    },
  },
  components: {},
  data() {
    return {
      value: "",
      isLoading: false,
    };
  },
  computed: {
    // 标签长度
    tagLength() {
      return this.selectTags.length - 1;
    },
    selectTags() {

      let _filterData = [];
      this.selectList &&
        this.selectList.forEach((item) => {
          if (item.type == "dept") {
            item["openDataType"] = "departmentName";
          } else {
            item["openDataType"] = "userName";
          }
          _filterData.push(item);
        });

      console.log(_filterData, "数组");
      return _filterData;
    },
  },
  methods: {
    // 点击
    handleClick() {
      this.isLoading = true;
      this.$emit("on-click");

      // 简单做个加载交互
      setTimeout(() => {
        this.isLoading = false;
      }, 500);
    },
    // 删除
    handleClose(item, index) {

      this.selectTags.splice(index, 1);
      this.$emit("deleteitem", item);
      this.$forceUpdate()

    },
  },
  mounted() {},
};
</script>
<style lang="less" scoped>
.choosed_list_dept_user {
  position: relative;
  display: flex;
  align-items: center;
  height: 40px;
  padding-right: 10px;
  border-radius: 4px;
  cursor: pointer;
  overflow: hidden;
  overflow-x: auto;
  p {
    color: #ccc;
    line-height: 40px;
    font-size: 14px;
  }
  .tags {
    margin-right: 10px;
  }
  /deep/ .el-loading-spinner {
    margin-top: 0;
    transform: translateY(-50%);
  }
  /deep/ .el-loading-spinner .circular {
    width: 28px;
  }
  .button-new-tag {
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
}
</style>
