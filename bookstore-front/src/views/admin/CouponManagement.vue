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
				<el-sub-menu index="1">
					<template #title>
						<el-icon><Ticket /></el-icon>
						<span>優惠券管理</span>
					</template>
					<el-menu-item index="1-1">查詢優惠券</el-menu-item>
					<el-menu-item index="1-2">新增優惠券</el-menu-item>
				</el-sub-menu>
				<!-- 其他導航項目 -->
				<el-menu-item index="2">
					<span>其他功能(待整合)</span>
				</el-menu-item>
			</el-menu>
	  </el-col>
  
	  <!-- 右側內容區域，根據選擇的菜單顯示對應的功能 -->
	  <el-col :span="20">
		<div v-show="activeMenu === '1-1'">
			<!-- 查詢(&刪除)優惠券的表單 -->
			<h2>查詢優惠券</h2>
			<SearchCoupon ref="searchCouponRef" />
		</div>
		<div v-show="activeMenu === '1-2'">
			<h2>新增優惠券</h2>
			<AddCoupon @couponAdded="refreshCoupons" />
		</div>
	  </el-col>
	</el-row>
</template>
  
<script setup>
import { ref } from 'vue'
import { Ticket } from '@element-plus/icons-vue'  // Element-plus 的 Icons
import AddCoupon from './AddCoupon.vue'
import SearchCoupon from './SearchCoupon.vue'

// 定義當前選擇的菜單項目
const activeMenu = ref('1-1')
const searchCouponRef = ref(null)

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

<style>

</style>  