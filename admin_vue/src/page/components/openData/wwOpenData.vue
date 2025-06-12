<!--企业微信获取用户信息-->
<template>
  <div class="name">
    <!--企业微信环境-->
    <div v-if="dw.isWx()">
      <div v-if="type == 'departmentName'">
        <span v-for="(item, index) in deptList" :key="index">
          <span v-if="index != 0">-</span>
          <ww-open-data :type="type" :openid="item" />
        </span>
      </div>
      <div v-else>
        <ww-open-data :type="type" :openid="userName" />
      </div>
    </div>
    <!--钉钉环境-->
    <div v-else-if="dw.isDingtalk()">
      <div v-if="type == 'departmentName'">
        <span v-for="(item, index) in deptList" :key="index">
          <span v-if="index != 0">-</span>
          <dt-open-data open-type="deptName" :open-id="item"></dt-open-data>
        </span>
      </div>
      <div v-else-if="type == 'userName'">
        <dt-open-data open-type="userName" :open-id="userName"></dt-open-data>
      </div>
      <div v-else>
        <!-- 职位 -->
        <dt-open-data open-type="userTitle" :open-id="userTitle"></dt-open-data>
      </div>
    </div>

    <!--浏览器环境-->
    <div v-else>
      {{ defaultname }}
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex';

// 企微通讯录展示组件
export default {
  name: 'OpenDataCom',
  props: ['type', 'openid', 'defaultname'],
  data() {
    return {
      deptList: [],
      userName: '', // 用户名
      userTitle: '', // 用户职位
      companyId: window.localStorage.getItem('companyId'),
    };
  },
  computed: {
    wxAgentConfigSuccess() {
      return this.$store?.state?.wxAgentConfigSuccess;
    },

  },
  watch: {
    openid: {
      handler(newVal, oldVal) {
        this.$nextTick(() => {
          if (this.dw.isWx()) {
            if (this.type == 'departmentName' && this.openid) {
              this.deptList = [];
              let str = this.cutString(this.openid);
              str.forEach((item) => {
                let arr = [];
                arr = item.replace('departmentName=', '');
                this.deptList.push(arr);
              });
            } else if (this.type == 'userName' && this.openid) {
              this.userName = '';
              let str = this.cutStringName(this.openid);
              this.userName = str;
            }
            window.WWOpenData && window.WWOpenData.bind(this.$el);
          } else if (this.dw.isDingtalk()) {
            if (this.type == 'departmentName' && this.openid) {
              this.deptList = [];
              let str = this.cutString(this.openid);
              str.forEach((item) => {
                let arr = [];
                arr = item.replace('departmentName=', '');
                this.deptList.push(arr);
              });
            } else if (this.type == 'userName' && this.openid) {
              this.userName = '';
              let str = this.cutStringName(this.openid);
              this.userName = str;
            }
            // console.log(this.userName, '钉钉环境')
            setTimeout(() => {
              window.DTOpenData.update(document.querySelectorAll('dt-open-data'));
            }, 500);

          }
        });
      },
      immediate: true,
    },

    wxAgentConfigSuccess(nval) {
      if (this.dw.isWx()) {
        nval && window.WWOpenData.bind(document.querySelector('ww-open-data'));
        this.$forceUpdate();
      }
    },

  },
  created() {

  },
  mounted() {

    if (this.dw.isWx()) {
      this.wxAgentConfig(this)
        .then(() => {
          console.log('我鉴权成功了');
          window.WWOpenData &&
            window.WWOpenData.bind(document.querySelector('ww-open-data'));

          this.handleData()
        })
        .catch((error) => {
          console.log('我鉴权失败了');
          const _errMsg = error.errMsg || 'wx.agentConfig失败-wwOpenData';
          this.$message.warning(_errMsg);
        });


    } else if (this.dw.isDingtalk()) {
      // console.log('钉钉环境')

    }

  },
  methods: {
    ...mapActions(['wxAgentConfig']),
    handleData() {
      // console.log('处理数据')
      if (this.type == 'departmentName' && this.openid) {
        this.deptList = [];
        let str = this.cutString(this.openid);
        str.forEach((item) => {
          let arr = [];
          arr = item.replace('departmentName=', '');
          this.deptList.push(arr);
        });
      } else if (this.type == 'userName' && this.openid) {
        this.userName = '';
        let str = this.cutStringName(this.openid);
        this.userName = str;
        // console.log('用户名' + this.userName)
      }
    },

    /**
     * 处理多部门的问题
     */
    cutString(str) {
      let t1 = str.split(',');
      let arr = [];
      let arr2 = [];
      let str1 = '';
      let arr4 = [];
      let arr5 = [];
      t1.forEach((item) => {
        if (item.indexOf('$-$') != -1) {
          let strRe = item.replace(/-/g, ',').replace(/\s*/g, '');
          str1 += strRe + ',';
        } else {
          arr.push(item);
        }
      });

      arr4 = str1.split(',');
      arr4.forEach((item) => {
        if (item != '') {
          arr5.push(item);
        }
      });
      let c = arr.concat(arr5);
      c.forEach((item2) => {
        arr2.push(item2.replace(/^\$/, '').replace(/\$$/, ''));
      });
      return arr2;
    },

    /**
     * 处理用户名的问题
     */
    cutStringName(str) {
      // 如果包含着$userName=这个字符，去掉前后的$,然后取出里面的值，没包含的话直接取值
      if (str.indexOf('$userName=') != -1) {
        let newstr = str.replace(/^\$/, '').replace(/\$$/, '');
        return newstr.replace('userName=', '');
      } else {
        return str;
      }
    },
  },

};
</script>

<style lang="less">
.name {
  max-width: 110px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: inline;

  >div {
    display: inline;

    >div {
      display: inline;

      >div {
        display: inline;
      }
    }
  }
}
</style>
