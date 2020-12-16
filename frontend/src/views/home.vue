<template>
  <div id="home">
    <sideMenu/>
    <div id="main">
      <headerBar/>
      <controlBar/>
      <compose/>
    </div>
  </div>
</template>

<script>
import sideMenu from '../components/sideNav.vue';
import headerBar from '../components/header.vue';
import controlBar from '../components/controlBar.vue';
import compose from '../components/compose.vue';
export default {
  name: 'home',
  components: {
    sideMenu,
    headerBar,
    controlBar,
    compose,
  }
}
</script>

<style scoped>
html,body{
  height: 100%;
}

#home{
  height: 100%;
}

#main {
  transition: margin-left .4s ease-out;
  margin-left: 4rem;
}
</style>