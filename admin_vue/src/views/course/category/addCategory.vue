<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="分类名称" prop="title">
            <el-input v-model.trim="form.title"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="addCate('form')">提交</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { addCategory } from "@/api/learn/category";
export default {
  components: {},
  props: ["parentId", "count"],
  data() {
    return {
      newCount: 0,
      form: {
        title: "",
        parentId: this.parentId,
        weight: 0,
      },
      rules: {
        title: [
          {
            required: true,
            message: "请输入分类名称",
          },
          {
            max: 50,
            message: "分类名称不能超过50字",
          },
        ],
      },
      max_len_desc: 21,
    };
  },
  mounted() {},
  created() {},
  methods: {
    addCate(form) {
      let that = this;
      that.$refs[form].validate((valid) => {
        if (!valid) {
          return;
        }
        let numCount = that.newCount ? that.newCount : that.count;
        that.form["weight"] = numCount + 1;
        addCategory(that.form).then((res) => {
          that.$message({
            showClose: true,
            message: "创建成功",
            type: "success",
          });
          that.$emit("ok", res.data);
        });
      });
    },
    //取消添加
    cancel() {
      this.$emit("ok", "");
    },
  },
};
</script>
<style></style>
