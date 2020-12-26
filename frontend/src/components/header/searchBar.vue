<template>
  <div id="search-bar">
    <input type="text"  placeholder="   Search Emails" id="search-input">
    <div class="seperator"></div>
    <div id="search-select" @click="toggleSearchMenu()">
      <div id="searchBy-label">{{searchParameter}}</div>
      <img src="../../assets/header/arrowDown.png" width="13px" :class="['arrow', !searchMenuVisisble ? 'close-menu': 'open-menu']">
    </div>
    <div class="seperator"></div>
    <div id="search-btn" @click="activateSearch()"><img src="../../assets/header/search-gray.png" width="20px"></div>

    <div  id="search-menu" 
          :class="searchMenuVisisble ? 'visible-menu': 'hidden-menu'">
      <div class="menu-item" @click=" setSearchBy('all')">
        all
      </div>
      <div class="menu-item" @click="setSearchBy('sender')">
        sender
      </div>
      <div class="menu-item" @click="setSearchBy('receiver')">
        receivers
      </div>
      <div class="menu-item" @click="setSearchBy('subject')">
        subject
      </div>
      <div class="menu-item" @click="setSearchBy('textBody')">
        textBody
      </div>
      <div class="menu-item" @click="setSearchBy('attachment')">
        attachments
      </div>
    </div>
  </div>
</template>

<script>
import store from '../../store';
import axios from 'axios';
import  { mapActions } from 'vuex';

export default {
  name: 'searchBar',
  data(){
    return{
      searchParameter: "all",
      searchMenuVisisble: false,
    }
  },
  methods:{
    ...mapActions(['updateEmails']),
    searchBarFocus(){
      document.getElementById("search-bar").style.border = "1px solid red";
    },
    searchBarUnFocus(){
      document.getElementById("search-bar").style.border = "none";
    },
    setSearchBy(searchBy){
      this.searchParameter = searchBy;
    },
    toggleSearchMenu(){
      this.searchMenuVisisble = !this.searchMenuVisisble;
    },
    searchEmails(required, type){
      store.commit('setSubOpStart', 0);
      store.commit('setSearchBy', type);
      store.commit('setSearchWord', required);
      store.commit('setSearchCond', true);
      store.commit('setFilterCond', false);
      store.commit('setSortCond', false);

      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//searchFile`, {
        params: { 
          userId: store.getters.getUserId,
          required: required,
          folderName: store.getters.getActiveFolder,
          criteria: type,
        }
      })
      .then( response => {
        homePage.reset();
        const result = response.data.slice(0, 15);
        homePage.addEmails(result);
        this.updateEmails(result);
      })
      .catch( error => console.log(error)); 
    },

    activateSearch(){
      const required = document.getElementById("search-input").value;
      if(required == "") return;

      store.commit('setSubOpActive', true);
      this.searchEmails(required, this.searchParameter);
    }
  }
}
</script>

<style scoped>
#search-bar{
  display: flex;
  align-items: center;
  margin-left: 4rem;
  background-color: rgb(233, 233, 233);
  border-radius: 0.4rem;
  border: 1px solid #767676;
}
#search-input{
  box-sizing: border-box;
  border: none;
  height: 2rem;
  width: 17rem;
  font-size: 15px;
  background-color:  rgb(233, 233, 233);
  border: 1px solid rgb(233, 233, 233);
  border-top-left-radius: 0.4rem;
  border-bottom-left-radius: 0.4rem;
}
#search-bar > input[type=text]:focus{
  outline-style: none;
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

#search-select{
  height: 2rem;
  width: 6rem;
  background-color: rgb(233, 233, 233);
  color: #767676;
  text-align: center;
  vertical-align: middle;
  line-height: 2rem;
  font-size: 14px;
  font-weight: bold;
  user-select: none;

  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;

  padding-left: 0.5rem;
  padding-right: 0.5rem;
}
#search-select:hover{
  background-color: rgb(224, 224, 224);
}
.seperator{
  height: 2rem;
  border-left: 1px solid #767676;
}
.arrow{
  margin-left: auto;
}

#search-menu{
  position: absolute;
  width: 7rem;
  color: #767676;
  background-color: rgb(233, 233, 233);
  border-bottom-left-radius: 0.3rem;
  border-bottom-right-radius: 0.3rem;
  border-left: 1px solid #767676;
  border-right: 1px solid #767676;
  border-bottom: 1px solid #767676;
  top: 3rem;
  margin-left: 17rem;
  z-index: 2;
}
.menu-item{
  height: 2rem;
  text-align: center;
  vertical-align: middle;
  line-height: 2rem;
  user-select: none;
}
.menu-item:hover{
  background-color: rgb(224, 224, 224);
  border-bottom-left-radius: 0.3rem;
  border-bottom-right-radius: 0.3rem;
  user-select: none;
}

.hidden-menu{
  visibility: hidden;
  display: none;
}
.visible-menu{
  visibility: visible;
  display: block;
}
#searchBy-label{
  width: 5rem;
  text-align: center;
}

.open-menu{
  transform: rotate(-180deg);
  transition: 0.1s linear;
}
.close-menu{
  transform: rotate(-360deg);
  transition: 0.1s linear;
}
</style>