<template>
  <div class="add_lector_cont">
    <el-form ref="form" :model="lectorInfo" :rules="rules" label-width="150px">
      <template v-if="editLectorUuid">
        <el-form-item label="选择讲师">
          <div class="name-box" v-if="lectorInfo.lectorName">
            <OpenDataCom
              type="userName"
              :openid="lectorInfo.lectorName"
              :defaultname="lectorInfo.lectorName"
            ></OpenDataCom>
          </div>
        </el-form-item>
      </template>
      <template v-else>
        <el-form-item label="选择讲师">
          <div
            class="name-box"
            v-if="lectorInfo.lectorName"
            @click="editLector"
          >
            <OpenDataCom
              type="userName"
              :openid="lectorInfo.lectorName"
              :defaultname="lectorInfo.lectorName"
            ></OpenDataCom>
          </div>
          <div class="name-box" v-else @click="editLector">请选择讲师</div>
        </el-form-item>
      </template>
      <el-form-item label="性别">
        <el-radio v-model="lectorInfo.sex" label="man">男</el-radio>
        <el-radio v-model="lectorInfo.sex" label="woman">女</el-radio>
      </el-form-item>
      <el-form-item label="选择等级" prop="lectorGrade">
        <el-select
          v-model="lectorInfo.lectorGrade"
          placeholder="请选择讲师等级"
        >
          <el-option
            v-for="item in gradeList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input
          v-model.trim="lectorInfo.contactWay"
          placeholder="请填写电话/邮箱"
        ></el-input>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input
          type="textarea"
          v-model.trim="lectorInfo.synopsis"
          :maxlength="titleLength"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <div class="btn-box">
          <el-button
            type="primary"
            @click="addLectorData()"
            :loading="isLoading"
            >保存</el-button
          >
          <el-button @click="backList()">返回</el-button>
        </div>
      </el-form-item>
    </el-form>
    <org-picker
      title="请选择人员"
      choiceType="user"
      ref="orgRequired"
      :selected="selectRequired"
      @ok="selected"
    ></org-picker>
  </div>
</template>
<script>
import { addLector, updateLector, lectorDetail } from "@/api/system/lector";
import { listLectorGrade } from "@/api/system/lectorGrade";
import OrgPicker from "@/components/Common/OrgPicker";

export default {
  props: ["editLectorUuid"],
  components: {
    OrgPicker,
  },
  data() {
    return {
      titleLength: 1000,
      isLoading: false,
      selectRequired: [],
      gradeList: [],
      uploadUrl: "",
      fileList: [],
      multipart_params: {},
      companyId: "",
      imgdefault: require("@/assets/images/1790708689137700864.png"),
      rules: {
        lectorName: [
          {
            required: true,
            message: "请选择讲师",
          },
        ],
        lectorGrade: [
          {
            type: "string",
            required: true,
            message: "请选择等级",
            trigger: "change",
          },
        ],
      },
      lectorInfo: {
        lectorType: "interior",
        sex: "man",
        lectorId: "",
        lectorName: "",
        lectorAvatar: "",
      },
    };
  },
  computed: {},
  mounted() {
    this.visible = true;
    if (this.editLectorUuid) {
      this.getData();
    }
    this.getLectorGrade();
  },
  methods: {
    getLectorGrade() {
      let that = this;
      listLectorGrade()
        .then((res) => {
          if (res.code === 200) {
            res.data.map(function (items) {
              let grades = {
                value: items.uuid,
                label: items.title,
              };
              that.gradeList.push(grades);
            });
          }
        })
        .catch((error) => {
          console.error(error);
        });
      // console.log(that.gradeList);
    },
    editLector() {
      this.$refs.orgRequired.show();
    },
    selected(values) {
      if (values.length > 0) {
        this.lectorInfo.lectorId = values[0].uuid;
        this.lectorInfo.lectorName = values[0].showName;
        this.lectorInfo.lectorAvatar = values[0].avatar;
      }
    },
    backList() {
      this.$emit("close");
    },
    // 获取讲师详情
    getData() {
      let that = this;
      if (that.editLectorUuid) {
        lectorDetail(that.editLectorUuid)
          .then((res) => {
            if (res.code === 200) {
              that.lectorInfo = res.data;
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    // 保存
    addLectorData() {
      let that = this;
      if (!this.lectorInfo.lectorName) {
        this.$message({
          showClose: true,
          message: "请填写课程标题",
          type: "error",
        });
        return;
      }
      if (!this.lectorInfo.lectorGrade) {
        this.$message({
          showClose: true,
          message: "请选择课程分类",
          type: "error",
        });
        return;
      }
      if (this.isLoading) {
        return;
      }
      this.isLoading = true;
      let template = {
        lectorId: this.lectorInfo.lectorId,
        lectorName: this.lectorInfo.lectorName,
        sex: this.lectorInfo.sex,
        lectorGrade: this.lectorInfo.lectorGrade,
        contactWay: this.lectorInfo.contactWay,
        synopsis: this.lectorInfo.synopsis,
      };
      if (!that.editLectorUuid) {
        addLector(template).then(function (res) {
          that.isLoading = false;
          that.$message({
            showClose: true,
            message: "创建成功",
            type: "success",
            onClose: function () {},
          });
          that.$emit("close", "save");
        });
      } else {
        updateLector(template).then(function (res) {
          if (res.code == 200) {
            that.isLoading = false;
            that.$message({
              showClose: true,
              message: res.msg,
              type: "success",
              onClose: function () {},
            });
            that.$emit("close", "save");
          }
        });
      }
    },
  },
};
</script>
<style lang="less">
.add_lector_cont {
  .avatar-img {
    width: 160px;
    height: 160px;
    object-fit: cover;
  }
  .name-box {
    width: 300px;
    height: 36px;
    border: 1px solid #eee;
    box-sizing: border-box;
    padding: 0 10px;
    border-radius: 4px;
    cursor: pointer;
  }
}
</style>
