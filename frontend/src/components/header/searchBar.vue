<template>
  <div id="search-bar">
    <input type="text"  placeholder="   Search Emails" id="search-input">
    <div id="search-btn"><img src="../../assets/header/search-gray.png" width="20px"></div>
  </div>
</template>

<script>
export default {
  name: 'searchBar',
  methods:{
    searchBarFocus(){
      document.getElementById("search-bar").style.border = "1px solid red";
    },
    searchBarUnFocus(){
      document.getElementById("search-bar").style.border = "none";
    }
  }
}
</script>

<style scoped>
#search-bar{
  display: flex;
  margin-left: 4rem;
}
#search-input{
  box-sizing: border-box;
  border: none;
  height: 2rem;
  width: 20rem;
  font-size: 15px;
  background-color:  rgb(233, 233, 233);
  border: 1px solid rgb(233, 233, 233);
  border-top-left-radius: 0.4rem;
  border-bottom-left-radius: 0.4rem;
}
#search-bar > input[type=text]:focus{
  outline-style: none;
  /* border-left: 1px solid #767676;
  border-top: 1px solid #767676;
  border-bottom: 1px solid #767676; */
}
#search-btn{
  height: 2rem;
  width: 3rem;
  background-color: rgb(233, 233, 233);
  display: flex;
  justify-content: center;
  align-items: center;
  user-select: none;
  border-top-right-radius: 0.4rem;
  border-bottom-right-radius: 0.4rem;
  transition: 0.2s ease;
  color: #767676;
}
#search-btn:hover{
  background-color: rgb(224, 224, 224);
  transition: 0.2s ease;
}
</style>