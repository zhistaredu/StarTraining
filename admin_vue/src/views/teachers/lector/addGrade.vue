<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
<!--       <el-form-item label="等级类型" prop="learnMode">
        <el-radio v-model="form.type" label="interior">内部讲师</el-radio>
        <el-radio v-model="form.type" label="external">外部讲师</el-radio>
      </el-form-item> -->
      <el-form-item label="等级名称" prop="title">
        <el-input
          v-model.trim="form.title"
        ></el-input>
      </el-form-item>
      <el-form-item label="等级简介">
        <el-input
          type="textarea"
          v-model.trim="form.synopsis"
          :maxlength="titleLength"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <div class="btn-box">
          <el-button type="primary" @click="add()" :loading="isLoading">保存</el-button>
          <el-button @click="backList()">返回</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {
  addLectorGrade,
  updateLectorGrade,
  lectorGradeDetail
} from "@/api/system/lectorGrade";
export default {
  props: ["gradeId"],
  components: {
  },
  data() {
    return {
      title: "",
      titleLength: 101,
      dialogVisible: false,
      isLoading: false,
      fullTreeData: [], // 分类列表
      selectRequired: [],
      expandOnClickNode: true,
      selectValue: "",
      treeProps: {
        children: "children",
        label: "label",
      },
      form: {
        uuid: "",
        type: "interior",
        title: "",
        synopsis: "",
      },
      rules: {
        title: [
          {
            required: true,
            message: "请输入标题",
          },
          {
            max: 100,
            message: "长度在100字符内",
          },
        ],
      },
      showAdd: true,
    };
  },
  computed: {
    
  },
  mounted() {
    this.visible = true;
    this.getData();
  },
  methods: {
    backList() {
      this.$emit('cancel', "cancel");
    },
    // 获取课程详情
    getData() {
      const that = this;
      if (this.gradeId) {
        lectorGradeDetail(this.gradeId).then((res) => {
            if (res.code === 200) {
              this.form.uuid = res.data.uuid;
              this.form.type = res.data.type;
              this.form.title = res.data.title;
              this.form.synopsis = res.data.synopsis;
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    // 保存
    add() {
      let that = this;

      if (!this.form.title) {
        this.$message({
          showClose: true,
          message: "请填写标题",
          type: "error",
        });
        return;
      }
      if (this.isLoading) {
        return;
      }
      this.isLoading = true;
      if (!this.gradeId) {
        addLectorGrade(this.form).then(function (res) {
          if (res.code == 200) {
            that.$message({
              showClose: true,
              message: "创建成功",
              type: "success",
              onClose: function () {
                that.$emit('ok', "submit");
              },
            });
          }
        });
      } else {
        this.form.uuid = this.gradeId;
        updateLectorGrade(this.form).then(function (res) {
          if (res.code == 200) {
            that.isLoading = false;
            that.$message({
              showClose: true,
              message: res.msg,
              type: "success",
              onClose: function () {
                that.$emit('ok', "submit");
              },
            });
          }
        });
      }
    },
  },
};
</script>
<style lang="less">

</style>
