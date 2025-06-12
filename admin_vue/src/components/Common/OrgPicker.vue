<template>
  <w-dialog
    :border="false"
    closeFree
    width="800px"
    @ok="selectOk"
    :title="title"
    v-model="visible"
  >
    <div class="picker-self-box">
      <div class="candidate" v-loading="loading">
        <div>
          <el-input
            v-model="search"
            @input="searchUser"
            style="width: 95%"
            size="small"
            clearable
            placeholder="搜索部门或人员"
            prefix-icon="el-icon-search"
          />
          <div style="margin-top: 5px" v-show="!showUsers">
            <ellipsis
              hoverTip
              style="height: 24px; color: #8c8c8c; padding: 5px 0 0"
              :row="1"
              :content="deptStackStr"
              type="dept"
            >
              <i slot="pre" class="el-icon-office-building"></i>
            </ellipsis>
            <div style="margin-top: 15px" class="flex-box2">
              <el-checkbox
                v-model="checkAll"
                @change="handleCheckAllChange"
                :disabled="!multiple"
                >全选</el-checkbox
              >
              <span
                v-show="deptStack.length > 0"
                class="top-dept"
                @click="beforeNode"
                >上一级</span
              >
            </div>
          </div>
        </div>

        <div class="org-items">
          <el-empty
            :image-size="100"
            description="似乎没有数据"
            v-show="orgs.length === 0"
          />
          <div
            v-for="(org, index) in orgs"
            :key="index"
            :class="orgItemClass(org)"
            @click.prevent="selectChange(org)"
          >
            <el-checkbox
              v-model="org.selected"
              :disabled="disableDept(org)"
            ></el-checkbox>
            <div
              class="flex-box"
              v-if="org.type === 'dept'"
              style="cursor: pointer"
            >
              <i class="el-icon-folder-opened"></i>
              <span class="name">
                <OpenDataCom
                  type="departmentName"
                  :openid="org.showName"
                  :defaultname="org.showName"
                ></OpenDataCom>
              </span>
              <span
                @click.stop="nextNode(org, 'dept')"
                :class="`next-dept${org.selected ? '-disable' : ''}`"
              >
                <i class="iconfont icon-map-site"></i>下级
              </span>
            </div>
            <div
              class="flex-box"
              v-else-if="org.type === 'user'"
              style="display: flex; align-items: center; cursor: pointer"
            >
              <el-avatar
                :size="35"
                :src="org.avatar"
                v-if="$isNotEmpty(org.avatar)"
              />
              <span v-else class="avatar">{{
                getShortName(org.showName)
              }}</span>
              <span class="name">
                <OpenDataCom
                  type="userName"
                  :openid="org.showName"
                  :defaultname="org.showName"
                ></OpenDataCom>
              </span>
            </div>
            <div class="flex-box" v-else-if="org.type === 'postGroup'">
              <i class="el-icon-folder-opened"></i>
              <span class="name">{{ org.showName }}</span>
              <span @click.stop="nextNode(org,'postGroup')" :class="`next-dept${org.selected ? '-disable':''}`">
                <i class="iconfont icon-map-site"></i>下级
              </span>
            </div>
            <div class="flex-box" v-else-if="org.type === 'post'" style="display: flex; align-items: center">
              <i class="iconfont icon-bumen"></i>
              <span class="name">{{ org.showName }}</span>
            </div>
            <div
              class="flex-box"
              style="display: inline-block; cursor: pointer"
              v-else
            >
              <i class="iconfont icon-bumen"></i>
              <span class="name">
                <OpenDataCom
                  type="userName"
                  :openid="org.showName"
                  :defaultname="org.showName"
                ></OpenDataCom>
              </span>
            </div>
          </div>
        </div>
      </div>
      <div class="selected">
        <div class="count">
          <span>已选 {{ select.length }} 项</span>
          <span @click="clearSelected">清空</span>
        </div>
        <div class="org-items" >
          <el-empty
            :image-size="100"
            description="请点击左侧列表选择数据"
            v-show="select.length === 0"
          />
          <div
            v-for="(org, index) in select"
            :key="index"
            :class="orgItemClass(org)"
          >
            <div v-if="org.type === 'dept'">
              <i class="el-icon-folder-opened"></i>
              <span style="position: static" class="name">
                <OpenDataCom
                  type="departmentName"
                  :openid="org.showName"
                  :defaultname="org.showName"
                ></OpenDataCom>
              </span>
            </div>
            <div
              v-else-if="org.type === 'user'"
              style="display: flex; align-items: center"
            >
              <el-avatar
                :size="35"
                :src="org.avatar"
                v-if="$isNotEmpty(org.avatar)"
              />
              <span v-else class="avatar">{{
                getShortName(org.showName)
              }}</span>
              <span class="name">
                <OpenDataCom
                  type="userName"
                  :openid="org.showName"
                  :defaultname="org.showName"
                ></OpenDataCom>
              </span>
            </div>
            <div v-else>
              <i class="iconfont icon-bumen"></i>
              <span class="name">
                <OpenDataCom
                  type="userName"
                  :openid="org.showName"
                  :defaultname="org.showName"
                ></OpenDataCom>
              </span>
            </div>
            <i class="el-icon-close" @click="noSelected(index)"></i>
          </div>
        </div>
      </div>
    </div>
  </w-dialog>
</template>

<script>
import { getdepartment, getContactSearch } from "@/api/system/dept";
import { getdepartmentUser } from "@/api/system/user";

export default {
  name: "OrgPicker",
  components: {

  },
  props: {
    title: {
      default: "请选择",
      type: String,
    },
    choiceType: {
      default: "org", //org选择部门/人员  user-选人  dept-选部门
      type: String,
    },
    showType: {
      default: 'dept',
      type: String,
    },
    multiple: {
      //是否多选
      default: false,
      type: Boolean,
    },
    selected: {
      default: () => {
        return [];
      },
      type: Array,
    },
  },
  data() {
    return {
      visible: false,
      loading: false,
      checkAll: false,
      nowDeptId: null,
      isIndeterminate: false,
      searchUsers: [],
      nodes: [],
      deptList: [],
      userList: [],
      select: [],
      search: "",
      deptStack: [],
    };
  },
  computed: {
    deptStackStr() {
      return String(this.deptStack.map((v) => v.showName)).replaceAll(",", ">");
    },
    orgs() {
      return !this.search || this.search.trim() === ""
        ? this.nodes
        : this.searchUsers;
    },
    showUsers() {
      return this.search || this.search.trim() !== "";
    },
  },
  methods: {
    show() {
      this.visible = true;
      this.init();
      if (this.showType == 'post') {
        this.getPostList()
      } else {
        this.getOrgList()
      }
    },
    orgItemClass(org) {
      return {
        "org-item": true,
        "org-dept-item": org.type === "dept",
        "org-user-item": org.type === "user",
        "org-role-item": org.type === "role",
        "org-category-item": org.type === "category",
        "org-course-item": org.type === "course",
        "org-exam-item": org.type === "exam",
        "org-post-group-item": org.type === "postGroup",
        "org-post-item": org.type === "post",
      };
    },
    disableDept(node) {
      return (
        this.choiceType === "user" && "dept" === node.type
      );
    },
    async getOrgList() {
      this.loading = true;
      const [deptRes, userRes] = await Promise.all([
        getdepartment({ parentId: this.nowDeptId }),
        this.choiceType == 'dept' ? {data:[]} : getdepartmentUser({ deptId: this.nowDeptId }),
      ]);
      this.nodes = [...deptRes.data, ...userRes.data];
      this.selectToLeft();
      this.loading = false;
    },
    getShortName(name) {
      if (name) {
        return name.length > 2 ? name.substring(1, 3) : name;
      }
      return "**";
    },
    searchUser() {
      let userName = this.search.trim();
      this.searchUsers = [];
      this.loading = true;
      getContactSearch({ keyword: userName })
        .then((rsp) => {
          this.loading = false;
          this.searchUsers = rsp.data;
          this.selectToLeft();
        })
        .catch((err) => {
          this.loading = false;
          this.$message.error("接口异常");
        });
    },
    selectToLeft() {
      let nodes = this.search.trim() === "" ? this.nodes : this.searchUsers;
      nodes.forEach((node) => {
        for (let i = 0; i < this.select.length; i++) {
          if (this.select[i].uuid === node.uuid) {
            node.selected = true;
            break;
          } else {
            node.selected = false;
          }
        }
      });
    },
    selectChange(node) {
      if (node.selected) {
        this.checkAll = false;
        for (let i = 0; i < this.select.length; i++) {
          if (this.select[i].uuid === node.uuid) {
            this.select.splice(i, 1);
            break;
          }
        }
        node.selected = false;
      } else if (!this.disableDept(node)) {
        node.selected = true;
        let nodes = this.search.trim() === "" ? this.nodes : this.searchUsers;
        if (!this.multiple) {
          nodes.forEach((nd) => {
            if (node.uuid !== nd.uuid) {
              nd.selected = false;
            }
          });
        }
        if (node.type === "dept") {
          if (!this.multiple) {
            this.select = [node];
          } else {
            this.select.unshift(node);
          }
        } else {
          if (!this.multiple) {
            this.select = [node];
          } else {
            this.select.push(node);
          }
        }
      }
    },
    noSelected(index) {
      let nodes = this.nodes;
      for (let f = 0; f < 2; f++) {
        for (let i = 0; i < nodes.length; i++) {
          if (nodes[i].uuid === this.select[index].uuid) {
            nodes[i].selected = false;
            this.checkAll = false;
            break;
          }
        }
        nodes = this.searchUsers;
      }
      this.select.splice(index, 1);
    },
    handleCheckAllChange() {
      this.nodes.forEach((node) => {
        if (this.checkAll) {
          if (!node.selected && !this.disableDept(node)) {
            node.selected = true;
            this.select.push(node);
          }
        } else {
          node.selected = false;
          for (let i = 0; i < this.select.length; i++) {
            if (this.select[i].uuid === node.uuid) {
              this.select.splice(i, 1);
              break;
            }
          }
        }
      });
    },
    nextNode(node, type) {
      this.nowDeptId = node.uuid;
      this.deptStack.push(node);
      if (this.showType == 'post') {
        this.getPostList();
      } else {
        this.getOrgList();
      }
    },
    beforeNode() {
      if (this.deptStack.length === 0) {
        return;
      }
      if (this.deptStack.length < 2) {
        this.nowDeptId = null;
      } else {
        this.nowDeptId = this.deptStack[this.deptStack.length - 2].uuid;
      }
      this.deptStack.splice(this.deptStack.length - 1, 1);
      if (this.showType == 'post') {
        this.getPostList();
      } else {
        this.getOrgList();
      }
    },
    recover() {
      this.select = [];
      this.nodes.forEach((nd) => (nd.selected = false));
    },
    selectOk() {
      this.$emit(
        "ok",
        Object.assign(
          [],
          this.select.map((v) => {
            // v.avatar = undefined;
            return v;
          })
        )
      );
      this.visible = false;
      this.recover();
    },
    clearSelected() {
      this.$confirm("您确定要清空已选中的项?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then((action) => {
        if (action == "confirm") {
          this.recover();
        }
      });
    },
    close() {
      this.$emit("close");
      this.recover();
    },
    init() {
      this.checkAll = false;
      this.nowDeptId = null;
      this.deptStack = [];
      this.nodes = [];
      this.select = Object.assign([], this.selected);
      this.selectToLeft();
    },
  },
};
</script>

<style lang="scss" scoped>
@containWidth: 278px;
.flex-box2 {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.flex-box {
  display: flex;
  align-items: center;
}

.org-items {
  .flex-box {
    display: flex;
    align-items: center;
  }
}
.candidate,
.selected {
  position: absolute;
  display: inline-block;
  width: 380px;
  height: 600px;
  border: 1px solid #e8e8e8;
}

.picker-self-box {
  height: 602px;
  position: relative;
  text-align: left;
  .candidate {
    left: 0;
    top: 0;
    .org-items {
      height: 493px;
    }
    .role-header {
      padding: 10px !important;
      margin-bottom: 5px;
      border-bottom: 1px solid #e8e8e8;
    }

    .top-dept {
      margin-left: 20px;
      cursor: pointer;
      color: #38adff;
    }
    .next-dept {
      float: right;
      color: #5881db;
      cursor: pointer;
    }

    .next-dept-disable {
      float: right;
      color: #8c8c8c;
      cursor: not-allowed;
    }

    & > div:first-child {
      padding: 5px 10px;
    }
  }

  .selected {
    right: 0;
    top: 0;
    .org-items {
      height: 560px;
    }
  }

  .org-items {
    overflow-y: auto;

    .el-icon-close {
      position: absolute;
      right: 5px;
      cursor: pointer;
      font-size: larger;
    }
    .org-dept-item,
    .org-post-group-item,
    .org-category-item,
    .org-role-item {
      padding: 10px 5px;
      display: flex;
      align-items: center;
      // & > div {
      //   display: inline-block;

      //   & > span:last-child {
      //     position: absolute;
      //     right: 5px;
      //   }
      // }
      & > div.flex-box {
        display: flex;
        align-items: center;

        & > span:last-child {
          position: absolute;
          right: 5px;
        }
      }
    }

    .org-course-item,
    .org-exam-item,
    .org-post-item,
    .org-role-item {
      display: flex;
      align-items: center;
      padding: 10px 5px;
    }

    ::v-deep .org-user-item {
      display: flex;
      align-items: center;
      padding: 5px;

      & > div {
        display: inline-block;
      }

      .avatar {
        width: 35px;
        text-align: center;
        line-height: 35px;
        background: #5881db;
        color: white;
        border-radius: 50%;
      }
    }

    ::v-deep .org-item {
      margin: 0 5px;
      border-radius: 5px;
      position: relative;

      .el-checkbox {
        margin-right: 10px;
      }

      .name {
        margin-left: 5px;
      }

      &:hover {
        background: #f1f1f1;
      }
    }
  }
}

.selected {
  border-left: none;

  .count {
    width: calc(400px - 25px);
    padding: 10px;
    display: inline-block;
    border-bottom: 1px solid #e8e8e8;
    margin-bottom: 5px;
    & > span:nth-child(2) {
      float: right;
      color: #c75450;
      cursor: pointer;
    }
  }
}

::v-deep .el-dialog__body {
  padding: 10px 20px;
}

.disabled {
  cursor: not-allowed !important;
  color: #8c8c8c !important;
}

::-webkit-scrollbar {
  float: right;
  width: 4px;
  height: 4px;
  background-color: white;
}

::-webkit-scrollbar-thumb {
  border-radius: 16px;
  background-color: #efefef;
}
</style>
