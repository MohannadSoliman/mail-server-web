<template>
  <div  :id="`${emailInfo.id}`" class="email-card" 
        @mouseover="showQuickMenu()" @mouseleave="hideQuickMenu()" @click="showSelf()">
		<div :id="`select-email-${emailInfo.id}`" class="unChecked" @click="toggleCheckBox()"></div>
    <div id="sender-recivers" class="content">{{inbox ? email.receivers: email.sender}}</div>
    <div id="subject" class="content">{{email.subject}}</div>
    <div id="priority" :class="['content', `${email.priority}`]">
      <label for="priority-select" class="priority-label">{{email.priority}}</label> &#9679;
    </div>
    <div id="date" class="content">{{email.date}}</div>
    <div id="attachments" class="content">
      <img src="../../assets/compose/attach.png" width="20px" :class="showHideAttachemntIndicator()">
    </div>
    <div :class="['quick-menu', activeQuickMenu ? 'visible': 'hidden', chechBoxChecked ? 'mouse-on': 'mouse-leave']">
      <div class="menu-btn" :id="`delete-btn-${emailInfo.id}`">
        <img src="../../assets/general/moveFolder.png" width="18px" :id="`delete-img-${emailInfo.id}`">
      </div>
      <div class="menu-btn" @click="deleteEmail()" :id="`move-btn-${emailInfo.id}`">
        <img src="../../assets/sideMenu/trash-gray.png" width="18px" :id="`move-img-${emailInfo.id}`">
      </div>
    </div>
  </div>
  
</template>

<script>
import store from '../../store'
import axios from 'axios';
import {fireInfoPopUp} from '../../alerts.js'
export default {
  name: 'emailCard',
  props: {
    inbox: Boolean,
    emailInfo: Object,
  },
  data(){
    return{
      email: {
        id: "",
        sender: "",
        receivers: "",
        subject: "",
        priority: "",
        date: "",
        attachments: [],
        body: ","
      },
      activeQuickMenu: false,
      chechBoxChecked: false,
    }
  },
  methods: {
    toggleCheckBox(){
      if(this.chechBoxChecked){ //un select Email
        this.unSelectSelf();
        store.commit('removeSelectEmail', this);
      }
      else {//select Email
        this.selectSelf();
        store.commit('addSelectedEmail', this);
      }
      this.chechBoxChecked = !this.chechBoxChecked;
    },
    selectSelf(){
      const checkBox = document.getElementById(`select-email-${this.emailInfo.id}`);
      const emailCard = document.getElementById(`${this.emailInfo.id}`);
      checkBox.className = "checked";
      emailCard.style.backgroundColor = "rgb(224,224,224)"
      // store.commit('addSelectedEmail', this);
    },
    unSelectSelf(){
      const checkBox = document.getElementById(`select-email-${this.emailInfo.id}`);
      const emailCard = document.getElementById(`${this.emailInfo.id}`);
      checkBox.className = "unChecked";
      emailCard.style.backgroundColor = "white";
    },
    showHideAttachemntIndicator(){
      if(this.email.attachments.length > 0) return "has-attachments";
      return "no-attachments";
    },
    showQuickMenu(){
      this.activeQuickMenu = true;
    },
    hideQuickMenu(){
      this.activeQuickMenu = false;
    },
    removeSelf(){
      document.getElementById(this.email.id).remove();
    },
    showSelf(){
      let itemClicked = event.target;
      if(itemClicked.id == `select-email-${this.email.id}` ||
        itemClicked.id == `quick-menu-${this.email.id}` || 
        itemClicked.id == `delete-btn-${this.email.id}` ||
        itemClicked.id == `move-btn-${this.email.id}` ||
        itemClicked.id == `delete-img-${this.email.id}` ||
        itemClicked.id == `move-img-${this.email.id}`) return;

      store.commit('setCurrentEmail', this.email);
      store.commit('setActiveEmail', true);
    },
    deleteEmail(){
      const popUpBackground = document.createElement('div');
      popUpBackground.className = 'pop-up-background';
      document.body.appendChild(popUpBackground);

      const popUp = document.createElement('div');
      popUp.className = 'pop-up';
      popUpBackground.appendChild(popUp);

      const msg = document.createElement('h2');
      if(store.getters.getActiveFolder === "trash") msg.innerText = "this email will be deleted permanently";
      else msg.innerText = "Sure to move this email to Trash?";
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
        if(store.getters.getActiveFolder === "trash") this.deleteSelfForever();
        else this.deleteSelf();
        document.body.removeChild(popUpBackground); 
      }

      popUp.appendChild(cancelBtn);
      popUp.appendChild(confrimBtn);
    },
    refreshHome(){
      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//getEmailsList`, {
            params: { 
              userId: store.getters.getUserId,
              folderName: store.getters.getActiveFolder,
              sortType: store.getters.getSortingParam.sortType,
              sortIdntifier: store.getters.getSortingParam.sortIdntifier,
              start: 0,
            }
          })
          .then( response => {
            homePage.reset();
            homePage.addEmails(response.data);
            store.dispatch('updateEmails', response.data);
          })
          .catch( error => console.log(error));    
    },
    deleteSelf(){
      axios.delete(`http://localhost:8080//deleteEmail`,{
          params: { 
            userId: store.getters.getUserId,
            emailId: this.email.id,
            folderName: store.getters.getActiveFolder,
          }
        })
        .then( () => {
          fireInfoPopUp("email has been moved to trash");
          this.refreshHome();
        })
        .catch( error => console.log(error)); 
    },
    deleteSelfForever(){
      axios.delete(`http://localhost:8080//deleteForever`,{
          params: { 
            userId: store.getters.getUserId,
            emailId: this.email.id,
          }
        })
        .then( () => {
          fireInfoPopUp("email has been moved to trash");
          this.refreshHome();
        })
        .catch( error => console.log(error)); 
    },
    getId(){
      return this.email.id;
    }
  },
  mounted(){
    this.email.id = this.emailInfo.id;
    const receiversList = this.emailInfo.receivers;
    let recieversStr = "";
    for(const reciever of receiversList){
      recieversStr += reciever;
    }
    this.email.receivers = recieversStr;
    this.email.sender = this.emailInfo.sender;
    this.email.subject = this.emailInfo.title;
    this.email.priority = this.emailInfo.priority.toLowerCase();
    this.email.date = this.emailInfo.time.split(' ')[0];
    this.email.attachments = this.emailInfo.attachments;
    this.email.body = this.emailInfo.body;
  },
}
</script>

<style scoped>
.email-card{
  width: 100%;
  height: 2.5rem;
  display: grid;
  grid-template-columns: 5% 20% 35% 15% 15% 10%;
  grid-template-rows: 2rem;
  justify-items: start;
  align-items: center;
  justify-content: center;
  align-content: center;
  border-bottom: 1px solid rgb(224, 224, 224);
  background-color: white;
}
.email-card:hover{
  box-shadow: 1px 0px 5px 1px rgb(224, 224, 224);
  transform: scale(1);
  transition: tranafrom 0.2s ease, box-shadow 0.2s ease;
}
.urgent{
  color: #fc033d;
}
.high{
  color: #fc6703;
}
.normal{
  color: #ffd900;
}
.low{
  color: #1da355;
}
.content{
  height: 2rem;
  width: 100%;
  text-align: center;
  vertical-align: middle;
  line-height: 2rem;
  font-size: 15px;
  grid-row: 1 / 2;
}

#sender-recivers{
  overflow-x: auto;
  font-weight: bold;
  grid-column: 2 / 3;
}
#subject{
  font-weight: bold;
  grid-column: 3 / 4;
}
#priority{
  grid-column: 4 / 5;
}
#date{
  grid-column: 5 / 6;
}
#attachments{
  grid-column: 6 / 7;
  display: flex;
  justify-content: center;
  align-items: center;
}
.no-attachments{
  visibility: hidden;
  display: none;
}
.has-attachments{
  visibility: visible;
  display: block;
}

.checked{
  grid-column: 1/2;
  width: 0.9rem;
  height: 0.9rem;
  margin-left: 0.5rem;
  border: 2px solid #3474E0;
  border-radius: 0.3rem;
  background-color: #3474E0;
  transition: 0.2s ease;
}
.unChecked{
  grid-column: 1/2;
  width: 0.9rem;
  height: 0.9rem;
  border: 2px solid rgb(224, 224, 224);
  border-radius: 0.3rem;
  margin-left: 0.5rem;
  transition: 0.2s ease;
}

.quick-menu{
  position: absolute;
  height: 2.5rem;
  width: 5rem;
  right: 0rem;
  padding-right: 1.5rem;
  grid-column: 5 / 7;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  z-index: 3;
}
.mouse-on{
  background-color: rgb(224, 224, 224);
}
.mouse-leave{
  background-color: white;
}
.menu-btn{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 2rem;
  width: 2rem;
  border-radius: 50%;
  z-index: 5;
}
.menu-btn:hover{
  background-color: rgb(224, 224, 224, 0.6);
}

.visible{
  visibility: visible;
  display: flex;
}
.hidden{
  visibility: hidden;
  display: flex;
}

.priority-label{
  font-weight: bold;
}
</style>