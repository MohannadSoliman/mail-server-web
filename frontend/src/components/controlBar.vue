<template>
  <div id="control-bar">

    <div id="control-wrapper" :class="['control-wrapper', getActiveStatus ? 'hidden': 'visible']">
      <div id="refresh-btn" class="control-btn" @click="refresh()">
        <img src="../assets/controlBar/refresh-gray.png" width="20px">
      </div>
      <form id="select-all-container">
        <input type="checkbox" id="select-all" 
              :value="[selectAllActive ? 'Un-select all' : 'Select all']" 
              @change="toggleSelectAll()">
        <label for="select-all">Select all</label>
      </form>
      <div id="emails-nav">
        <div id="backward-btn" class="control-btn" @click="goToPrevPage()">
          <img src="../assets/controlBar/backward.png" width="17px">
        </div>
        <div id="forward-btn" class="control-btn" @click="goToNextPage()">
          <img src="../assets/controlBar/forward.png" width="17px">
        </div>
      </div>
    </div>
    
    <div id="current-email-control" :class="['control-wrapper', getActiveStatus? 'visible': 'hidden']">
      <div id="go-back" class="control-btn" @click="goBackHome()">
        <img src="../assets/header/back.png" width="20px">
      </div>
      <div id="delete-current-email" class="control-btn">
        <img src="../assets/sideMenu/trash-gray.png" width="20px">
      </div>
    </div>

  </div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex';
import store from '../store';
import axios from 'axios';

export default {
  name: 'controlBar',
  data(){
    return{
      selectAllActive: false,
    }
  },
  computed: mapGetters(['getUserId', 'getActiveFolder', 'getEmailsListPageInfo', 'getStartIndex', 'getEmailsNum', 'getActiveStatus', 'getSortingParam']),
	methods:{
    ...mapActions(['updateEmails', 'incrementStartIndex', 'decrementStartIndex']),
		refresh(){
      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//getEmailsList`, {
        params: { 
          userId: this.getUserId,
          folderName: this.getActiveFolder,
          sortType: this.getSortingParam.sortType,
          sortIdntifier: this.getSortingParam.sortIdntifier,
          start: 0,
        }
      })
      .then( response => {
        homePage.reset();
        homePage.addEmails(response.data);
        this.updateEmails(response.data);
      })
      .catch( error => console.log(error)); 
    },
    toggleSelectAll(){
      this.selectAllActive = !this.selectAllActive;
      if(this.selectAllActive){
        this.selectAllEmails();
      }
      else{
        this.unSelectAllEmails();
      }
    },
    selectAllEmails(){
      store.commit('clearSelecteEmails');
      for(const email of [...this.getEmailsListPageInfo.emailsList]){
        email.selectSelf();
        store.commit('addSelectedEmail', email);
      }
    },
    unSelectAllEmails(){
      for(const email of [...this.getEmailsListPageInfo.emailsList]){
        email.unSelectSelf();
      }
      store.commit('clearSelecteEmails');
    },
    goToNextPage(){
      if(this.getStartIndex + 15 >= this.getEmailsNum) return;
      this.incrementStartIndex();
      this.updateEmailsList();
    },
    goToPrevPage(){
      if(this.getStartIndex - 15 < 0) return; 
      this.decrementStartIndex();
      this.updateEmailsList();
    },
    updateEmailsList(){
      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//getEmailsList`, {
        params: { 
          userId: this.getUserId,
          folderName: this.getActiveFolder,
          sortType: 1,
          sortIdntifier: 0,
          start: this.getStartIndex,
        }
      })
      .then( response => {
        homePage.reset();
        homePage.addEmails(response.data);
        this.updateEmails(response.data);
      })
      .catch( error => console.log(error)); 
    },
    goBackHome(){
      store.commit('setActiveEmail', false)
    },
    deleteCurrentEmail(){

    }
	}
}
</script>

<style scoped>
#control-bar{
	/* display: flex;
	flex-direction: row;
	justify-content: flex-start;
	align-items: center;
	height: 3rem;
	border-bottom: 1px solid rgb(224, 224, 224, 0.6);
	padding-left: 1rem; */
}
.control-wrapper{
  display: flex;
	flex-direction: row;
	justify-content: flex-start;
	align-items: center;
	height: 3rem;
	border-bottom: 1px solid rgb(224, 224, 224, 0.6);
	padding-left: 1rem;
  width: 100%;
}

.control-btn{
	display: flex;
	justify-content: center;
	align-items: center;
	height: 2rem;
	width: 2rem;
	border-radius: 50%;
}
.control-btn:hover{
	background-color: rgb(224, 224, 224, 0.6);
}
#emails-nav{
	height: 2rem;
	display: flex;
	justify-content: space-evenly;
	align-items: center;
	width: 5rem;
	margin-left: auto;
	margin-right: 2rem;
}

input[type=checkbox] + label {
  display: block;
	user-select: none;
}

input[type=checkbox] {
  display: none;
}
label{
	color: #767676;
	font-size: medium;
	font-weight: normal;
}

input[type=checkbox] + label:before {
  content: "\2714";
  border: 2px solid #767676;
  border-radius: 0.3rem;
  display: inline-block;
  width: 0.8rem;
  height: 0.8rem;
  padding: 0.2rem;
  margin-right: 0.4rem;
  vertical-align: bottom;
  color: transparent;
  transition: .2s;
}

input[type=checkbox] + label:active:before {
  transform: scale(0);
}

input[type=checkbox]:checked + label:before {
  background-color: #3474E0;
  border-color: #3474E0;
}

input[type=checkbox]:disabled + label:before {
  transform: scale(1);
  border-color: #767676;
}

input[type=checkbox]:checked:disabled + label:before {
  transform: scale(1);
  background-color: #3474E0;
  border-color: #3474E0;
}

#select-all-container{
	display: flex;
	justify-content: center;
	align-items: center;
	margin-left: 1.5rem;
}

.hidden{
  visibility: hidden;
  display: none;
}
.visible{
  visibility: visible;
  display: flex;
}

#delete-current-email{
  margin-left: auto;
  margin-right: 3rem;
}
</style>