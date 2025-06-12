<template>
  <div class="static_item_cont">
    <div class="header_filter">
      <div class="filter_item">
        <div class="name">时间筛选：</div>
        <el-date-picker
          v-model="timeScreen"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </div>
      <div class="filter_item">
        <div class="name">是否统计子部门数据：</div>
        <el-select v-model="relevancy" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <div class="filter_item filter_item_dept">
        <div class="name">部门筛选：</div>
        <selectUserDepInput
            :selectList="selectList"
            placeholder="请选择部门"
            :minWidth="'160px'"
            @on-click="selectDept"
            @deleteitem="deleteitem"
          ></selectUserDepInput>
      </div>
      <div class="filter_item">
        <div class="search_btn" @click="confirmSearch">查询</div>
      </div>
    </div>
    <div class="table_data">
      <el-table :data="tableData" :border="true" :header-cell-style="{ background: '#eaeaea', color: '#000' }">
        <el-table-column prop="userName" label="姓名" fixed>
          <template slot-scope="scope">
            <OpenDataCom
              type="userName"
              :openid="scope.row.userName"
              :defaultname="scope.row.userName"
            ></OpenDataCom>
          </template>
        </el-table-column>
        <el-table-column prop="deptName" label="所属部门">
          <template slot-scope="scope">
            <OpenDataCom
              type="departmentName"
              :openid="scope.row.deptNames"
              :defaultname="scope.row.deptNames"
            ></OpenDataCom>
          </template>
        </el-table-column>
        <el-table-column prop="position" label="职位"> </el-table-column>
        <el-table-column prop="jobNumber" label="工号"> </el-table-column>
        <el-table-column prop="createTime" label="参与时间"> </el-table-column>
        <el-table-column prop="progress" label="学习进度">
          <template slot-scope="scope"> {{ scope.row.progress }}% </template>
        </el-table-column>
        <el-table-column prop="learnTime" label="学习时长">
          <template slot-scope="scope">
            {{ scope.row.learnTime | timeFormat }}
          </template>
        </el-table-column>
        <el-table-column label="学习状态">
          <template slot-scope="scope">
            <div v-if="scope.row.learnStatus == 'learning'">学习中</div>
            <div v-else-if="scope.row.learnStatus == 'finished'">已完成</div>
          </template>
        </el-table-column>
        <el-table-column prop="finishTime" label="完成时间">
          <template slot-scope="scope">
            <div v-if="scope.row.learnStatus == 'finished'">
              {{ scope.row.finishTime }}
            </div>
            <div v-else>-</div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="bottom_page">
      <el-pagination
        v-if="dataCount > 0"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="dataCount"
      >
      </el-pagination>
    </div>
    <org-picker
      title="请选择可以管理此表单的人员"
      choiceType="dept"
      multiple
      ref="orgPicker"
      :selected="selectList"
      @ok="selected"
    ></org-picker>
  </div>
</template>
<script>
import { getCourseUser } from "@/api/learn/course";
import orgPicker from "@/components/Common/OrgPicker";
import selectUserDepInput from "@/components/Common/selectUserDepInput";

export default {
  components: {
    orgPicker,
    selectUserDepInput,
  },
  props: ["uuid"],
  data() {
    return {
      currentPage: 1,
      limit: 10,
      dataCount: 0,
      tableData: [],
      timeScreen: "",
      options: [
        {
          value: "yes",
          label: "是",
        },
        {
          value: "no",
          label: "否",
        },
      ],
      relevancy: "yes",
      chooseData: {
        departments: [],
        users: [],
      },
      selectList: [],
      searchValue: "",
    };
  },
  mounted() {
    this.getList();
  },
  methods: {
    handleSizeChange(val) {
      this.limit = val;
      this.getList();
    },
    handleCurrentChange(page) {
      this.currentPage = page;
      this.getList();
    },
    getList() {
      let _self = this;
      let data = {
        targetId: _self.uuid,
        page: _self.currentPage,
        limit: _self.limit,
        timeScreen: _self.timeScreen,
        relevancy: _self.relevancy,
      };
      getCourseUser(data).then(function (res) {
        if (res.code == 200) {
          _self.tableData = res.rows;
          _self.dataCount = res.total;
        }
      });
    },
    // 确认搜索
    confirmSearch() {},
    selectDept() {
      this.$refs.orgPicker.show();
    },
    deleteitem(itemdata) {
      let that = this;
      that.selectList.forEach((item,index) => {
        if(itemdata.uuid == item.uuid) {
          that.selectList.splice(index,1)
        }
      })
    },
    selected(values) {
      let that = this;
      that.selectList = [];
      values.forEach((item) => {
        that.selectList.push(item);
        if (item.type == "dept") {
          let data = {
            uuid: item.uuid,
            showName: item.showName,
          };
          that.chooseData.departments.push(item);
        }
        if (item.type == "user") {
          let data = {
            uuid: item.uuid,
            showName: item.showName,
          };
          that.chooseData.users.push(item);
        }
      });

    },
  },
};
</script>
<style src="@/utils/statistic.less" lang="less"></style>
<style lang="less" scoped></style>
