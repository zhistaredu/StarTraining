<template>
    <div  :style="{ backgroundColor: settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">

        <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper">
            <el-menu
                :default-active="activeMenu"
                :collapse="isCollapse"
                :background-color="settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground"
                :text-color="settings.sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor"
                :unique-opened="false"
                :active-text-color="settings.theme"
                :collapse-transition="false"
                :default-openeds="openArr"
                mode="vertical"
            >
                <sidebar-item
                    v-for="(route, index) in sidebarRouters"
                    :key="route.path  + index"
                    :item="route"
                    :base-path="route.path"
                />
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import variables from "@/assets/styles/variables.scss";

export default {
    components: { SidebarItem, Logo },
    data() {
        return{
            openArr: ['/form/learnStatistic','/form/deptStatistic','/train/survey','/train/plan','/exam/exercise','/train/learn','/exam', '/system/orgSetting', '/system/operate']
        }
    },
    computed: {
        ...mapState(["settings"]),
        ...mapGetters(["sidebarRouters", "sidebar"]),
        activeMenu() {
            const route = this.$route;
            // console.log(route, '999')
            const { meta, path } = route;
            // if set path, the sidebar will highlight the path you set
            if (meta.activeMenu) {
                return meta.activeMenu;
            }
            return path;
        },
        variables() {
            return variables;
        },
        isCollapse() {
            return !this.sidebar.opened;
        }
    }
};
</script>
