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
                    <li class="nav-item dropdown">
                        <span class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">顧客功能</span>
                        <ul class="dropdown-menu">
                            <li>
                                <RouterLink class="dropdown-item" to="/register">加入會員</RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" to="/modify">修改會員資料</RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" to="/pwdforget">忘記密碼</RouterLink>
                            </li>
                        </ul>
                    </li>
                    <!-- 後台: -->
                    <li class="nav-item dropdown">
                    <!-- 上面加上 v-show="authStore.isAuthenticated" -> 讓只有admins在登入之後才能看得到下面"後台管理"下拉式選單->*再想想*-->
                        <span class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">後台管理</span>
                        <ul class="dropdown-menu">
                            <li class="nav-item">
                                <RouterLink class="dropdown-item" to="/admins/login">管理員登入</RouterLink>
                            </li>
                            <li class="nav-item">
                                <RouterLink class="dropdown-item" to="/customers/list">查詢會員</RouterLink>
                            </li>
                            <li class="nav-item">
                                <RouterLink class="dropdown-item" to="/admins/list">查詢管理員</RouterLink>
                            </li>
                            <li class="nav-item">
                                <RouterLink class="dropdown-item" to="/coupon/management">優惠券管理</RouterLink>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/book/1">書籍(測試)</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/cart">購物車</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/order/list">訂單查詢</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/coupon/list">優惠券查詢</RouterLink>
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