<template>
  <div id="header">
    <div id="current-folder-name">Inbox</div>
    <searchBar/>
    <sort/>
    <div id="sign-up-btn" @click="goToSignUp()">Log out</div>
  </div>
</template>

<script>
import searchBar from './header/searchBar.vue';
import sort from './header/sort.vue';

export default {
  name: 'headerBar',
  components:{
    searchBar,
    sort,
  },
  methods:{
    goToSignUp(){
      this.$router.push('/signIn');
    }
  }
}
</script>

<style scoped>
#header{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  height: 3rem;
  padding: 0.5rem;
  padding-left: 1rem;
  border-bottom: 1px solid rgb(224, 224, 224, 0.6);
}
#current-folder-name{
  color: #3474E0;
  font-size: x-large;
  font-weight: bold;
  height: 2rem;
  width: 5rem;
  text-align: center;
  vertical-align: center;
  line-height: 2rem;
}
#sign-up-btn{
  height: 2rem;
  width: 5rem;
  margin-left: auto;
  border: 2px solid #3474E0;
  border-radius: 2rem;
  color: #3474E0;
  text-align: center;
  vertical-align: center;
  line-height: 2rem;
  font-weight: bold;
  cursor: pointer;
}
#sign-up-btn:hover{
  background-color: rgb(170, 201, 253);
}
</style>