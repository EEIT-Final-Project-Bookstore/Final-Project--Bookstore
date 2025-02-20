<template>
  <div class="app-admin">
    <!-- 若有登錄且為管理員帳號 -->
    <div
      v-if="authStore.isAuthenticated && authStore.adminAccount"
      class="admin-container"
    >
      <!-- 主要內容區 (管理員) -->
      <main class="admin-main">
        <!-- 這裡放你的 <el-row> -->
        <el-row class="content-row">
          <!-- 左側導覽欄：加上 sidebar-col 以確保撐到底 -->
          <el-col :span="4" class="sidebar-col">
            <el-menu
              active-text-color="#ffd04b"
              background-color="#545c64"
              class="el-menu-vertical-demo"
              default-active="1"
              text-color="#fff"
              @select="handleSelect"
              style="height: 100%"
              :default-openeds="['2', '3', '4', '5', '6', '7', '8']"
            >
              <el-menu-item index="1">
                <template #title>
                  <el-icon><HomeFilled /></el-icon>
                  <span>首頁</span>
                </template>
              </el-menu-item>

              <el-sub-menu index="2">
                <template #title>
                  <el-icon><User /></el-icon>
                  <span>顧客管理</span>
                </template>
                <el-menu-item index="2-1">顧客列表</el-menu-item>
              </el-sub-menu>

              <el-sub-menu index="3">
                <template #title>
                  <el-icon><UserFilled /></el-icon>
                  <span>管理員管理</span>
                </template>
                <el-menu-item index="3-1">管理員列表</el-menu-item>
              </el-sub-menu>

              <el-sub-menu index="4">
                <template #title>
                  <el-icon><Notebook /></el-icon>
                  <span>書籍管理</span>
                </template>
                <el-menu-item index="4-1">書籍列表</el-menu-item>
              </el-sub-menu>

              <el-sub-menu index="5">
                <template #title>
                  <el-icon><Ticket /></el-icon>
                  <span>優惠券管理</span>
                </template>
                <el-menu-item index="5-1">查詢優惠券</el-menu-item>
                <el-menu-item index="5-2">新增優惠券</el-menu-item>
              </el-sub-menu>

              <el-sub-menu index="6">
                <template #title>
                  <el-icon><Bell /></el-icon>
                  <span>通知管理</span>
                </template>
                <el-menu-item index="6-1">新增/查詢通知</el-menu-item>
              </el-sub-menu>

              <el-sub-menu index="7">
                <template #title>
                  <el-icon><Comment /></el-icon>
                  <span>書籍評論管理</span>
                </template>
                <el-menu-item index="7-1">評論管理</el-menu-item>
              </el-sub-menu>

              <el-sub-menu index="8">
                <template #title>
                  <el-icon><Picture /></el-icon>
                  <span>首頁圖片管理</span>
                </template>
                <el-menu-item index="8-1">圖片管理</el-menu-item>
              </el-sub-menu>
            </el-menu>
          </el-col>

          <!-- 右側內容區：根據 activeMenu 顯示相應的功能 -->
          <el-col :span="20" class="content-col">
            <div v-show="activeMenu === '1'">
              <Dashboard />
            </div>
            <div v-show="activeMenu === '2-1'">
              <h2>顧客列表</h2>
              <List_Customer />
            </div>
            <div v-show="activeMenu === '3-1'">
              <h2>管理員列表</h2>
              <List_Admin />
            </div>
            <div v-show="activeMenu === '4-1'">
              <h2>書籍列表</h2>
              <BookManagement />
            </div>
            <div v-show="activeMenu === '5-1'">
              <h2>查詢優惠券</h2>
              <SearchCoupon ref="searchCouponRef" />
            </div>
            <div v-show="activeMenu === '5-2'">
              <h2>新增優惠券</h2>
              <AddCoupon @couponAdded="refreshCoupons" />
            </div>
            <div v-show="activeMenu === '6-1'">
              <h2>通知管理</h2>
              <NotificationManagement />
            </div>
            <div v-show="activeMenu === '7-1'">
              <h2>書籍評論管理</h2>
              <ReviewManagement />
            </div>
            <div v-show="activeMenu === '8-1'">
              <h2>首頁圖片管理</h2>
              <ManageImages />
            </div>
          </el-col>
        </el-row>
      </main>
      <!-- 管理員底部 -->
      <!-- <footer class="site-footer"></footer> -->
    </div>

    <!-- ================= 一般使用者介面 (略) ================= -->
    <div v-else class="user-container">
      <!-- 你原本的 user header + main + footer -->
      <header class="site-header">...</header>
      <main class="user-main">
        <router-view />
      </main>
      <!-- <footer class="site-footer"></footer> -->
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

/* 需要的元件 / icon */
import SearchPage from "@/components/SearchPage.vue";
import { useAuthStore } from "@/stores/authStore";
import { useCartStore } from "@/stores/cartStore";
import {
  HomeFilled,
  User,
  UserFilled,
  Notebook,
  Ticket,
  Bell,
  Comment,
  Picture,
} from "@element-plus/icons-vue";

/* 你原先引用的管理員子元件 */
import AddCoupon from "@/views/admin/AddCoupon.vue";
import SearchCoupon from "@/views/admin/SearchCoupon.vue";
import NotificationManagement from "@/views/admin/NotificationManagement.vue";
import ReviewManagement from "@/views/admin/ReviewManagement.vue";
import List_Customer from "@/views/admin/List_Customer.vue";
import List_Admin from "@/views/admin/List_Admin.vue";
import Dashboard from "@/views/admin/Dashboard.vue";
import ManageImages from "@/views/admin/ManageImages.vue";
import BookManagement from "@/views/admin/BookManagement.vue";

export default {
  name: "App",
  components: {
    /* 一般使用者有用到的：SearchPage, icons */
    SearchPage,
    HomeFilled,
    User,
    UserFilled,
    Notebook,
    Ticket,
    Bell,
    Comment,
    Picture,

    /* 後台功能 */
    Dashboard,
    List_Customer,
    List_Admin,
    AddCoupon,
    SearchCoupon,
    NotificationManagement,
    ReviewManagement,
    ManageImages,
    BookManagement,
  },
  data() {
    return {
      isModalOpen: false,
      notifications: [],
      /* 後台 activeMenu */
      activeMenu: "1",
    };
  },
  setup() {
    const router = useRouter();
    const authStore = useAuthStore();
    const cartStore = useCartStore();

    const searchCouponRef = ref(null);

    onMounted(() => {
      // 如果沒有登入，轉 admin login (可自行調整條件)
      if (!authStore.adminId) {
        router.push("/admin/login");
      }
    });

    return { router, authStore, cartStore, searchCouponRef };
  },
  methods: {
    logout() {
      this.authStore.logout();
      this.$router.push("/");
    },
    handleSelect(index) {
      // 菜單切換
      this.activeMenu = index;
    },
    refreshCoupons() {
      // 新增優惠券後，刷新查詢
      if (this.searchCouponRef) {
        this.searchCouponRef.fetchCoupons();
      }
    },
    // 其他：如 openModal(), fetchNotifications(), markAllAsRead() etc...
  },
};
</script>

<style scoped>
.admin-container {
  display: flex;
  flex-direction: column;
}
.content-row {
  display: flex;
  flex: 1;
  align-items: stretch;
}
.content-col {
  flex: 1;
  display: flex;
  flex-direction: column;
}
.sidebar-col {
  height: 100% !important;
  display: flex;
  flex-direction: column;
}
.el-sub-menu span {
	font-weight: bolder !important;
	font-size: 16px;
}
.admin-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
}
</style>
