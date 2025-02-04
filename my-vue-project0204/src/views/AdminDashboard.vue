<template>
        <div class="admin-dashboard"></div>
           <h1>管理後台</h1>
           <nav>
            <ul>
                <li><router-link to="/admin/images">管理首頁圖片</router-link></li>
                <li><router-link to="/notification-management">通知管理</router-link></li>
                <li><router-link to="/reviews">評論管理</router-link></li>
                <li><router-link to="/">回到前台首頁</router-link></li>
            </ul>
           </nav>
          <!--<router-view /hompage>--> 
</template>
    
<script setup>
    
</script>
    
<style>
    .admin-dashboard{
        text-align:center;
    }
    nav ul{
        list-style:none;
        padding:0;
    }
    nav ul li{
        margin:10px 0;
        
    }
</style>