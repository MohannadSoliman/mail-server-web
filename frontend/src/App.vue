<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
  components:{
  },
  created(){
    this.$router.push('/signIn');
  }
}
</script>

<style>
html, body{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}

.pop-up-background {
    position: fixed;
    left: 0;
    top: 0;        
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    transform: scale(1.1);
    transition: visibility 0s linear 0.25s, opacity 0.25s 0s, transform 0.25s;
    z-index: 99999999999;
}
.pop-up {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 1rem 1rem;
    width: 24rem;
    height: 8rem;
    border: 3px solid #3474E0;
    border-radius: 0.5rem;

    display: grid;
    align-items: center;
    justify-content: center;
    justify-items: center;
    grid-template-rows: repeat(2, 1fr);
    grid-template-columns: 11rem 11rem;
}

.pop-up h2 {
    font-size: 19px;
    color: #525252;
    grid-row: 1/2;
    grid-column: 1/3;
}

.pop-up-close-button {
    float: right;
    width: 6rem;
    height: 2rem;
    font-size: medium;
    line-height: 2rem;
    text-align: center;
    color: white;
    cursor: pointer;
    background-color: #3474E0;
    border-radius: 0.5rem;
    grid-row: 2/3;
}

.cancel-btn{
  grid-column: 1/2;
}
.confrim-btn{
  grid-column: 2/3;
  background-color: #fc033d;
}

.ok-btn{
  grid-column: 1/3;
  background-color: #3474E0;
}
</style>
