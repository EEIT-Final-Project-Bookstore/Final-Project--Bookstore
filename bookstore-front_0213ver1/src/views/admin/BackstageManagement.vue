<template>
	<el-row>
	  <!-- 左側導航欄 -->
	  	<el-col :span="4">
			<el-menu
				active-text-color="#ffd04b"
				background-color="#545c64"
				class="el-menu-vertical-demo"
				default-active="1"
				text-color="#fff"
				@select="handleSelect"
				style="height: 100%"
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
					<el-menu-item index="4-2">新增書籍</el-menu-item>
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
			</el-menu>
	  </el-col>
  
	  <!-- 右側內容區域，根據選擇的菜單顯示對應的功能 -->
		<el-col :span="20">
			<div v-show="activeMenu === '1-1'">  <!-- 首頁 -->
				<!-- <h2>顧客列表</h2> -->
				<Dashboard />
			</div>
			<div v-show="activeMenu === '2-1'">
				<h2>顧客列表</h2>
				<List_Customer />
			</div>
			<div v-show="activeMenu === '3-1'">
				<h2>顧客列表</h2>
				<List_Admin />
			</div>
			<div v-show="activeMenu === '4-1'">
				<h2>書籍列表</h2>
				<BookManagement /> <!-- 0213新增 -->
				<!-- 待加入 -->
			</div>
			<div v-show="activeMenu === '4-2'">
				<h2>新增書籍</h2>
				<!-- 待加入 -->
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
				<h2>新增通知</h2>
				<NotificationManagement />
			</div>
			<div v-show="activeMenu === '7-1'">
				<h2>書籍評論管理</h2>
				<ReviewManagement />
			</div>
		</el-col>
	</el-row>
</template>
  
<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from "vue-router";
import { HomeFilled, User, Notebook, Ticket, Bell, Comment } from '@element-plus/icons-vue'  // Element Plus 的 Icons
import { useAuthStore } from '@/stores/authStore';
import AddCoupon from './AddCoupon.vue'
import SearchCoupon from './SearchCoupon.vue'
import NotificationManagement from './NotificationManagement.vue'
import ReviewManagement from './ReviewManagement.vue'
import List_Customer from './List_Customer.vue'
import List_Admin from './List_Admin.vue'
import Dashboard from './Dashboard.vue'
import BookManagement from '@/views/admin/BookManagement.vue'; //0213新增

const router = useRouter();
const authStore = useAuthStore();

// 定義當前選擇的菜單項目
const activeMenu = ref('1-1')
const searchCouponRef = ref(null)

//如果沒有登入，會自動導向登入頁面 (下面要改成沒有adminId之類的)
onMounted(() => {
    if (!authStore.adminId) {
        router.push('/admin/login');
        return;
    }
});

// 當選擇菜單項目時更新 activeMenu
const handleSelect = (index) => {
	activeMenu.value = index;
};
// 新增優惠券後，強制刷新查詢頁面
const refreshCoupons = () => {
    if (searchCouponRef.value) {
        searchCouponRef.value.fetchCoupons();
    }
};
</script>

<style scoped>
	.el-sub-menu span {
		font-size: 16px;
	}
</style>  