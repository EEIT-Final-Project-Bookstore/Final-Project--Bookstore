<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <span class="navbar-brand">森途書店</span>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <RouterLink class="nav-link active" aria-current="page" to="/">首頁</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/login">會員登入</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/register">加入會員</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/modify">修改會員資料</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/customers">查詢會員</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/cart">購物車</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/orderList">訂單查詢</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/coupon/management">優惠券管理</RouterLink>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>
    
<script setup>

</script>
    
<style>

</style>