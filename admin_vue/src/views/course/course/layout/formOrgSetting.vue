<template>
  <div id="course-assign-orgsetting">
    <div class="course-assing-main">
      <div class="zhipai-box">
        <div class="zhipai-tit">可见范围</div>
        <div class="zhipai-cont">
          <div class="cont-top">
            <div class="radio">
              <el-radio-group v-model="courseInfo.model" size="small">
                <el-radio label="1" border>默认管理范围学习</el-radio>
                <el-radio label="2" border>自定义范围学习</el-radio>
              </el-radio-group>
            </div>

            <div class="add-btn" v-if="courseInfo.model == '2'" @click="assignUser()">
              <i class="iconfont icon-jiahao"></i>
              <span>请选择可见范围</span>
            </div>
          </div>
          <div class="cont-bottom" v-if="courseInfo.model == '2'">
            <div class="flex-box" style="margin-bottom: 20px;">
              <span class="assign-label">选取部门:</span>
              <div class="right-zhipai-list">
                <div class="zhipai-item" v-for="(item, index) in electiveDeptList" :key="index">
                  <OpenDataCom type="departmentName" :openid="item.showName" :defaultname="item.showName"></OpenDataCom>
                </div>
              </div>

            </div>
            <div class="flex-box" style="margin-bottom: 20px;">
              <span class="assign-label">选取学员:</span>
              <div class="right-zhipai-list">
                <div class="zhipai-item" v-for="(item, index) in electiveUserList" :key="index">
                  <OpenDataCom type="userName" :openid="item.showName" :defaultname="item.showName"></OpenDataCom>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
    <div class="confirmSave">
      <el-button type="primary" @click="updateAssign()" :loading="isLoading">保存</el-button>
    </div>
    <org-picker title="请选择可见范围" multiple ref="orgElective" :selected="selectElective" @ok="selected"></org-picker>
  </div>
</template>

<script>
import {
  courseAssignUser,
  updateCourseAssignUser
} from "@/api/learn/course";
import OrgPicker from "@/components/Common/OrgPicker";

export default {
  components: {
    OrgPicker,
  },
  props: ["uuid", "courseStatus"],
  data() {
    return {
      radio1: 'elective',
      choiType: 'all',
      choiceType: 'user',
      requiredDept: [],
      required_user: [],
      electiveDept: [],
      electiveUser: [],
      loadingAssignUser: true,
      selectElective: [],
      isLoading: false,
    }
  },
  computed: {
    electiveDeptList() {
      return this.electiveDept;
    },
    electiveUserList() {
      return this.electiveUser;
    },
    courseInfo() {
      return this.$store.state.course.courseInfo
    },
  },
  mounted() {
    this.getData();
  },
  methods: {
    updateAssign() {
      let that = this;
      let reqData = [];
      if (this.courseInfo.model == "2") {
        this.selectElective.forEach(dept => {
          reqData.push({
            uuid: dept.uuid,
            type: dept.type,
            showName: dept.showName
          });
        });
      }
      let formData = {
        uuid: this.courseInfo.uuid,
        model: this.courseInfo.model,
        data: JSON.stringify(reqData),
      }
      updateCourseAssignUser(formData).then((res) => {
        if (res.code === 200) {
          that.$message.success(res.msg);
        }
      });
    },
    selected(values) {
      let that = this;
      this.selectElective = [];
      this.selectElective = values;
      this.electiveDept = [];
      this.electiveUser = [];
      values.forEach(item => {
        if (item.type == 'dept') {
          that.electiveDept.push(item);
        }
        if (item.type == 'user') {
          that.electiveUser.push(item);
        }
      });
    },
    getData() {
      let that = this;
      let uuidStr = that.courseInfo.uuid;
      if (that.courseInfo.uuid == "" || that.courseInfo.uuid == undefined) {
        uuidStr = this.$route.query.uuid;

      }
      courseAssignUser({ targetId: uuidStr }).then(function (res) {
        //选修
        that.selectElective = [];
        let electiveDeptList = res.data.viewRequired.dept;
        let electiveUserList = res.data.viewRequired.user;
        if (electiveDeptList != undefined && electiveDeptList.length > 0) {
          that.electiveDept = electiveDeptList;
          electiveDeptList.forEach(dept => {
            that.selectElective.push(dept);
          });
        }
        if (electiveUserList != undefined && electiveUserList.length > 0) {
          that.electiveUser = electiveUserList;
          electiveUserList.forEach(user => {
            that.selectElective.push(user);
          });
        }
        that.loadingAssignUser = false;
      })
    },
    assignUser() {
      this.$refs.orgElective.show()
    },
  },
}

</script>

<style lang="less" scoped>
#course-assign-orgsetting {
  overflow: hidden;
  margin: 0 auto;
  width: 800px;
  height: calc(100vh - 155px);
  background: #ffffff;
  margin-top: 10px;
  padding: 15px 20px;
  position: relative;

  .confirmSave {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .course-assing-main {
    height: calc(100% - 80px);
    .zhipai-box {
      width: 100%;
      max-height: 100%;
      overflow-y: auto;
      border: 1px solid #eee;
      box-sizing: border-box;
      border-radius: 4px;

      .zhipai-tit {
        width: 100%;
        height: 40px;
        background: #eee;
        padding: 0 10px;
        box-sizing: border-box;
        line-height: 40px;
        font-size: 16px;
        color: #333;
        font-weight: bold;
      }

      .zhipai-cont {
        width: 100%;
        padding: 10px;
        box-sizing: border-box;

        .right-zhipai-list {
          display: flex;
          align-items: center;
          flex-wrap: wrap;
          padding-left: 10px;
          box-sizing: border-box;

          .zhipai-item {
            line-height: 36px;
            font-size: 14px;
            color: #333;
            border: 1px solid #eee;
            border-radius: 4px;
            margin-right: 10px;
            margin-bottom: 5px;
            padding: 0 10px;
            box-sizing: border-box;
          }
        }

        .assign-label {
          display: inline-block;
          width: 80px;
          line-height: 41px;
          margin-right: 5px;
          text-align: right;
          font-weight: bold;
          color: #606662;
          font-size: 14px;
          flex-shrink: 0;
        }

        .cont-bottom {
          padding-top: 20px;
          box-sizing: border-box;
        }
      }
    }
  }


  .flex-box {
    display: flex;
    align-items: flex-start;


  }

  .el-radio-group {
    display: flex;
    align-items: center;
  }

  .zhipai-cont {
    width: 100%;
    padding: 20px 0px;
    box-sizing: border-box;

    .radio {
      padding-left: 20px;
      box-sizing: border-box;
    }

    .add-btn {
      width: 160px;
      height: 36px;
      border: 1px solid #5881db;
      box-sizing: border-box;
      display: inline-block;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      color: #5881db;
      border-radius: 4px;
      cursor: pointer;
      margin-top: 20px;
      margin-left: 20px;

      &:hover {
        background: #5881db;
        color: #fff;
      }

      .iconfont {
        margin-right: 6px;
      }
    }
  }


}
</style>
