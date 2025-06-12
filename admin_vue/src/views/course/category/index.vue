<template>
  <div
    v-loading="isLoading"
    class="comp-tree"
    :style="{ height: windowHeight + 'px' }"
  >
    <el-button
      class="comp-tr-top"
      type="primary"
      size="small"
      @click="handleAddTop"
      >添加一级分类</el-button
    >
    <!-- tree -->
    <el-tree
      ref="SlotTree"
      :data="treeData"
      :props="defaultProps"
      current-node-key="1"
      :node-key="NODE_KEY"
      @node-click="nodeClick"
      :expand-on-click-node="false"
      default-expand-all
    >
      <div class="comp-tr-node" slot-scope="{ node, data }">
        <!-- 编辑状态 -->
        <template v-if="node.isEdit">
          <el-input
            v-model="data.title"
            autofocus
            size="mini"
            :ref="'slotTreeInput' + data[NODE_KEY]"
            @blur.stop="handleInput(node, data)"
            @keyup.enter.native="handleInput(node, data)"
          ></el-input>
        </template>

        <!-- 非编辑状态 -->
        <template v-else>
          <i
            :class="data.open ? 'el-icon-folder-opened' : 'el-icon-folder'"
            style="color: #448ac4; margin-right: 5px"
          ></i>
          <!-- 名称： 新增分类增加class（is-new） -->
          <span
            :class="[
              data[NODE_KEY] < NODE_ID_START ? 'is-new' : '',
              'comp-tr-node--name',
            ]"
          >
            {{ node.label }}
          </span>

          <!-- 按钮 -->
          <span class="comp-tr-node--btns">
            <!-- 新增 -->
            <el-button
              icon="el-icon-plus"
              size="mini"
              title="新增"
              circle
              type="primary"
              @click="handleAdd(node, data)"
            ></el-button>

            <!-- 编辑 -->
            <el-button
              icon="el-icon-edit"
              size="mini"
              title="编辑"
              circle
              type="info"
              @click="handleEdit(node, data)"
            ></el-button>

            <!-- 删除 -->
            <el-button
              icon="el-icon-delete"
              size="mini"
              title="删除"
              circle
              type="danger"
              @click="handleDelete(node, data)"
            ></el-button>
          </span>
        </template>
      </div>
    </el-tree>
    <el-dialog
      title="添加分类"
      :visible.sync="createVisible"
      width="30%"
      center
    >
      <addCategory
        :parentId.sync="parentId"
        :count.sync="count"
        v-if="createVisible"
        @ok="sublimt"
      >
      </addCategory>
    </el-dialog>
  </div>
</template>

<script>
import addCategory from "./addCategory";
import {
  listJavaCategory,
  updateCategory,
  delCategory,
} from "@/api/learn/category";
export default {
  name: "categoryList",
  data() {
    return {
      windowHeight: 0,
      isLoading: false, // 是否加载
      createVisible: false,
      NODE_KEY: "id", // id对应字段
      MAX_LEVEL: 3, // 设定最大层级
      NODE_ID_START: 0, // 新增分类id，逐次递减
      startId: null,
      uuid: "",
      parentId: "1",
      title: "",
      count: 0,
      defaultProps: {
        // 默认设置
        children: "children",
        label: "label",
      },
      treeData: [],
      childrenData: {},
    };
  },
  components: {
    addCategory,
  },
  created() {
    this.windowHeight = window.innerHeight - 80;
    // 初始值
    this.startId = this.NODE_ID_START;
    this.getCategoryList();
  },
  methods: {
    nodeClick(data, checked, node) {
      data.open = !data.open;
    },
    // 获取分类列表
    getCategoryList() {
      let _self = this;
      listJavaCategory().then(function (res) {
        _self.treeData = res.data;
        _self.count = res.count;
      });
    },
    updateCategoryFun(uuid, title) {
      let _self = this;
      let data = {
        uuid: uuid,
        title: title,
      };
      updateCategory(data).then(function (res) {
        _self.$message({
          showClose: true,
          message: res.msg,
          type: "success",
        });
        _self.getCategoryList();
      });
    },
    // 删除分类
    handleDelete(_node, _data) {
      let _self = this;
      // 判断是否存在子分类
      if (_data.children && _data.children.length !== 0) {
        this.$message.error("此分类有子级，不可删除！");
        return false;
      } else {
        // 删除操作
        let DeletOprate = () => {
          this.$nextTick(() => {
            if (this.$refs.SlotTree) {
              delCategory(_data.id).then(function (res) {
                _self.$refs.SlotTree.remove(_data);
                _self.$message.success("删除成功！");
              });
            }
          });
        };

        // 二次确认
        let ConfirmFun = () => {
          this.$confirm("是否删除此分类？", "提示", {
            confirmButtonText: "确认",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then((action) => {
              if (action == "confirm") {
                DeletOprate();
              }
            })
            .catch(() => {});
        };

        // 判断是否新增： 新增分类直接删除，已存在的分类要二次确认
        _data[this.NODE_KEY] < this.NODE_ID_START
          ? DeletOprate()
          : ConfirmFun();
      }
    },
    // 修改分类
    handleInput(_node, _data) {
      // 退出编辑状态
      if (_node.isEdit) {
        this.$set(_node, "isEdit", false);
        if (_data.title) {
          this.updateCategoryFun(_data.id, _data.title);
        }
      }
    },
    // 编辑分类
    handleEdit(_node, _data) {
      // 设置编辑状态
      if (!_node.isEdit) {
        this.$set(_node, "isEdit", true);
      }

      // 输入框聚焦
      this.$nextTick(() => {
        if (this.$refs["slotTreeInput" + _data[this.NODE_KEY]]) {
          this.$refs[
            "slotTreeInput" + _data[this.NODE_KEY]
          ].$refs.input.focus();
        }
      });
    },
    // 新增分类
    handleAdd(_node, _data) {
      this.childrenData = {};
      this.childrenNode = {};
      // 判断层级
      if (_node.level >= this.MAX_LEVEL) {
        this.$message.warning("当前已达到" + this.MAX_LEVEL + "级，无法新增！");
        return false;
      }
      this.createVisible = true;
      this.parentId = _data[this.NODE_KEY]; // 父uuid
      this.childrenData = _data;
      this.childrenNode = _node;
    },
    // 添加顶部分类
    handleAddTop() {
      this.createVisible = true;
      this.parentId = "1";
    },
    // 添加
    sublimt(values) {
      if (values) {
        if (this.parentId != "1") {
          // 判断字段是否存在
          if (!this.childrenData.children) {
            this.$set(this.childrenData, "children", []);
          }
          // 新增数据
          this.childrenData.children.push(values);

          // 展开分类
          if (!this.childrenNode.expanded) {
            this.childrenNode.expanded = true;
          }
        } else {
          this.treeData.push(values);
        }
      }
      this.createVisible = false;
      this.getCategoryList();
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-tree {
  /* // 将每一行的设置为相对定位 方便后面before after 使用绝对定位来固定位置 */
  .el-tree-node {
    position: relative;
    padding-left: 10px;
  }
  /*
  // 子集像右偏移 给数线留出距离 */
  .el-tree-node__children {
    padding-left: 10px;
  }

  /* //这是竖线 */
  .el-tree-node :last-child:before {
    height: 40px;
  }

  .el-tree > .el-tree-node:before {
    border-left: none;
  }

  .el-tree > .el-tree-node:after {
    border-top: none;
  }

  /* //这自定义的线 的公共部分 */
  .el-tree-node:before,
  .el-tree-node:after {
    content: "";
    left: -4px;
    position: absolute;
    right: auto;
    border-width: 1px;
  }

  .tree :first-child .el-tree-node:before {
    border-left: none;
  }

  /* // 竖线 */
  .el-tree-node:before {
    border-left: 1px solid #e3e3e3;
    bottom: 0px;
    height: 100%;
    top: -25px;
    width: 1px;
  }

  /* //横线 */
  .el-tree-node:after {
    border-top: 1px solid #e3e3e3;
    height: 20px;
    top: 14px;
    width: 24px;
  }

  .el-tree-node__expand-icon.is-leaf {
    width: 8px;
  }

  .el-tree-node__content {
    line-height: 30px;
    height: 30px;
  }
}

::v-deep .el-tree > div {
  &::before {
    display: none;
  }

  &::after {
    display: none;
  }
}

.show-btns {
  opacity: 1;
}

.comp-tree {
  width: 100%;
  padding: 2em;
  overflow: auto;

  .comp-tr-top {
    width: 100px;
    margin-bottom: 2em;
  }

  .comp-tr-node {
    display: flex;
    align-items: center;

    .comp-tr-node--name {
      display: inline-block;
      line-height: 40px;
      min-height: 40px;

      &.is-new {
        font-weight: bold;
      }
    }

    .comp-tr-node--btns {
      margin-left: 20px;
      opacity: 0;
      transition: opacity 0.1s;
      display: flex;
      align-items: center;
      .el-button {
        transform: scale(0.8);
        margin-right: 10px;
        & + .el-button {
          margin-left: -1px;
        }
      }
    }
  }

  .is-current {
    & > .el-tree-node__content {
      .comp-tr-node--btns {
        @extend .show-btns;
      }
    }
  }

  .el-tree-node__content {
    &:hover {
      .comp-tr-node--btns {
        @extend .show-btns;
      }
    }
  }
}
</style>
