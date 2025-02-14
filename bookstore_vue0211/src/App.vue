<template>
  <div id="app">
    <header v-if="!isAdminRoute">
      <nav>
        <router-link to="/">首頁</router-link>
        <router-link :to="'/book/' + book.id">書籍商品頁</router-link>
      </nav>
    </header>
    <main>
      <router-view />
    </main>
  </div>
</template>

<script>
import { useRoute } from "vue-router";


export default {
  name: "App",
  data() {
    return {
      book: { id: 4 } // 假設 `id = 2`，這樣 `router-link` 才會生效
    };
  },
  computed: {
    isAdminRoute() {
      return this.$route.path.startsWith("/admin"); // 🔹 Vue 2 & Options API 寫法
    }
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
header {
  background-color: #6200ea;
  color: white;
  padding: 10px 20px;
}
header nav {
  display: flex;
  gap: 20px;
}
header nav a {
  color: white;
  font-weight: bold;
  text-decoration: none;
}
main {
  padding: 20px;
}
</style>  


 <!--
<script setup>
import HelloWorld from './components/HelloWorld.vue'
import TheWelcome from './components/TheWelcome.vue'
</script>

<template>
  <header>
    <img alt="Vue logo" class="logo" src="./assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />
    </div>
  </header>

  <main>
    <TheWelcome />
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
-->