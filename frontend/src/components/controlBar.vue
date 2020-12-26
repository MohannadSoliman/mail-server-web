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
      <div id="delete-selected-emails" class="control-btn" @click="deleteCurrentEmails()">
        <img src="../assets/sideMenu/trash-gray.png" width="20px">
      </div>
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
import {fireInfoPopUp} from '../alerts.js';
export default {
  name: 'controlBar',
  data(){
    return{
      selectAllActive: false,
    }
  },
  computed: mapGetters(['getUserId', 'getActiveFolder', 'getEmailsListPageInfo', 'getStartIndex', 'getEmailsNum', 'getActiveStatus', 'getSortingParam', 'getSelectedEmails']),
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
        // store.commit('addSelectedEmail', email);
      }
    },
    unSelectAllEmails(){
      for(const email of [...this.getEmailsListPageInfo.emailsList]){
        email.unSelectSelf();
      }
      store.commit('clearSelecteEmails');
    },
    goToNextPage(){
      if(store.getters.getSubOperation.active) {
        if(store.getters.getOpsConds.filter) this.getNextFiltered();
        else if(store.getters.getOpsConds.sort) this.getNextSorted();
        else if(store.getters.getOpsConds.search) this.getNextSearch();
        return;
      }
      if(this.getStartIndex + 15 >= this.getEmailsNum) return;
      this.incrementStartIndex();
      this.updateEmailsList();
    },
    goToPrevPage(){
      if(store.getters.getSubOperation.active) {
        if(store.getters.getOpsConds.filter) this.getPrevFiltered();
        else if(store.getters.getOpsConds.sort) this.getPrevSorted();
        else if(store.getters.getOpsConds.search) this.getPrevSearch();
        return;
      }
      if(this.getStartIndex - 15 < 0) return; 
      this.decrementStartIndex();
      this.updateEmailsList();
    },
    getNextFiltered(){
      if(store.getters.getSubOperation.start + 15 >= this.getEmailsNum) return;
      store.commit('setSubOpStart', store.getters.getSubOperation.start + 15);
      this.fetchFilteredEmails()
    },
    getPrevFiltered(){
      if(store.getters.getSubOperation.start - 15 < 0) return;
      store.commit('setSubOpStart', store.getters.getSubOperation.start - 15);
      this.fetchFilteredEmails()
    },
    getNextSorted(){
      if(store.getters.getSubOperation.start + 15 >= this.getEmailsNum) return;
      store.commit('setSubOpStart', store.getters.getSubOperation.start + 15);
      this.fetchSortedEmails();
    },
    getPrevSorted(){
      if(store.getters.getSubOperation.start - 15 < 0) return;
      store.commit('setSubOpStart', store.getters.getSubOperation.start - 15);   
      this.fetchSortedEmails(); 
    },
    getNextSearch(){
      if(store.getters.getSubOperation.start + 15 >= this.getEmailsNum) return;
      store.commit('setSubOpStart', store.getters.getSubOperation.start + 15);
      this.fetchSearchedEmail();
    },
    getPrevSearch(){
      if(store.getters.getSubOperation.start - 15 < 0) return;
      store.commit('setSubOpStart', store.getters.getSubOperation.start - 15);   
      this.fetchSearchedEmail(); 
    },
    fetchFilteredEmails(){
      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//filter`, {
        params: { 
          userId: this.getUserId,
          required: store.getters.getSubOperation.required,
          fileName: this.getActiveFolder,
          criteria: store.getters.getSubOperation.type,
        }
      })
      .then( response => {
        homePage.reset();
        const filteredEmails = response.data.slice(store.getters.getSubOperation.start, store.getters.getSubOperation.start +15);
        homePage.addEmails(filteredEmails);
        this.updateEmails(filteredEmails);
      })
      .catch( error => console.log(error)); 
    },
    fetchSortedEmails(){
      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//sort`, {
        params: { 
          userId: store.getters.getUserId,
          folderName: store.getters.getActiveFolder,
          sortType: store.getters.getSubOperation.sortType,
          sortIdntifier: store.getters.getSubOperation.sortIdntifier,
        }
      })
      .then( response => {
        homePage.reset();
        const sortedEmails = response.data.slice(store.getters.getSubOperation.start, store.getters.getSubOperation.start +15);
        homePage.addEmails(sortedEmails);
        this.updateEmails(sortedEmails);
      })
      .catch( error => console.log(error)); 
    },
    fetchSearchedEmail(){
      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//searchFile`, {
        params: { 
          userId: store.getters.getUserId,
          required: store.getters.getSubOperation.word,
          folderName: store.getters.getActiveFolder,
          criteria: store.getters.getSubOperation.searchBy,
        }
      })
      .then( response => {
        homePage.reset();
        const result = response.data.slice(store.getters.getSubOperation.start, store.getters.getSubOperation.start +15);
        homePage.addEmails(result);
        this.updateEmails(result);
      })
      .catch( error => console.log(error)); 
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
    deleteCurrentEmails(){
      const selectedEmailsNumber = this.getSelectedEmails.length;
      console.log(selectedEmailsNumber);
      if(selectedEmailsNumber === 0) return;
      console.log("enter")

      const popUpBackground = document.createElement('div');
      popUpBackground.className = 'pop-up-background';
      document.body.appendChild(popUpBackground);

      const popUp = document.createElement('div');
      popUp.className = 'pop-up';
      popUpBackground.appendChild(popUp);

      const msg = document.createElement('h2');
      if(store.getters.getActiveFolder == "trash") msg.innerText = `${selectedEmailsNumber} will be deleted permanently`;
      else msg.innerText = `Sure to move ${selectedEmailsNumber} email(s) to Trash?`;
      popUp.appendChild(msg);

      const cancelBtn = document.createElement('span');
      cancelBtn.innerText = 'Cancel';
      cancelBtn.className = 'pop-up-close-button cancel-btn';

      const confrimBtn = document.createElement('span');
      confrimBtn.innerText = 'Confirm';
      confrimBtn.className = 'pop-up-close-button confrim-btn';   

      cancelBtn.onclick = () => {    
        document.body.removeChild(popUpBackground); 
      }

      confrimBtn.onclick = () => {
        if(store.getters.getActiveFolder != "trash") {
          this.deleteAllSelectedEmails();
          fireInfoPopUp(`${selectedEmailsNumber} email(s) has been moved to trash`);
        }
        else {
          this.deleteAllSelectedEmailsForever();
          fireInfoPopUp(`${selectedEmailsNumber} email(s) has been deleted permanently`);
        }
        document.body.removeChild(popUpBackground); 
      }

      popUp.appendChild(cancelBtn);
      popUp.appendChild(confrimBtn);
    },

    deleteAllSelectedEmailsForever(){
      for(const email of this.getSelectedEmails){
        console.log(email);
        this.deleteSingleEmailForEver(email);
      }
      store.commit('clearSelecteEmails');
    },
    deleteAllSelectedEmails(){
      let allEmailsIds = "";
      let first = true;
      for(const email of this.getSelectedEmails){
        if(first){
          allEmailsIds += `${email.getId()}`;
          first = false;
        }
        else allEmailsIds += `,${email.getId()}`;
      }

      axios.delete(`http://localhost:8080//deleteMultipleEmails`,{
          params: { 
            userId: store.getters.getUserId,
            emailsIds: allEmailsIds,
            folderName: store.getters.getActiveFolder,
          }
        })
        .then( () => {
          store.commit('clearSelecteEmails');
          this.refresh();
        })
        .catch( error => console.log(error)); 
    },
    deleteSingleEmailForEver(email){
      axios.delete(`http://localhost:8080//deleteForever`,{
          params: { 
            userId: store.getters.getUserId,
            emailId: email.getId(),
          }
        })
        .then( () => {
          this.refresh();
        })
        .catch( error => console.log(error)); 
    },
	}
}
</script>

<style scoped>
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
  user-select: none;
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

#delete-selected-emails{
  margin-left: 2rem;
}
</style>