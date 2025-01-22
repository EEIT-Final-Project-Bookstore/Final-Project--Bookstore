<template>
  <div id="app">
   <!--全局導航欄-->
   <nav>
    <ul>
      <li><router-link to="/">首頁</router-link></li>
        <li><router-link to="/admin">管理後台</router-link></li>
        <li><router-link to="/admin/images">圖片管理</router-link></li>
    </ul>
   </nav>
   <!--路由切換內容顯示區-->
   <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
#app {
  font-family: Arial, sans-serif;
  padding: 20px;
}
nav{
  background-color: #f8f9fa;
  padding:10px;
}
nav ul{
  display: flex;
  list-style-type: none;
  padding:0;
}
nav ul li a{
  text-decoration: none;
  color:#007bff;
}
nav ul li a:hover{
  text-decoration: underline;
  
}
</style>
