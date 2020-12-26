<template>
  <div id="menu" class="sideMenu">
		<div class="menu-open" id="menu-open-btn" @click="toggleMenu()">
			<img src="../assets/sideMenu/menu-gray.png" width="25px" class="icon-img-active" id="menu-open-img-closed">
			<img src="../assets/sideMenu/menu.png" width="25px" class="icon-img-nonActive" id="menu-open-img-opened">
		</div>
		<div id="buttons-group">
			<div class="menu-btn" id="inbox-btn" @click="menuBtnClickEvent(buttons.inbox)">
				<img src="../assets/sideMenu/inbox-gray.png" width="20px" class="icon-img-nonActive" id="inbox-img-nonActive">
				<img src="../assets/sideMenu/inbox.png" width="20px" class="icon-img-active" id="inbox-img-active">
				<div class="hidden-label" id="inbox-label">Inbox</div>
			</div>
			<div class="menu-btn" id="sent-btn" @click="menuBtnClickEvent(buttons.sent)">
				<img src="../assets/sideMenu/sent-gray.png" width="20px" class="icon-img-active" id="sent-img-nonActive">
				<img src="../assets/sideMenu/sent.png" width="20px" class="icon-img-nonActive" id="sent-img-active">
				<div class="hidden-label" id="sent-label">Sent</div>
			</div>
			<div class="menu-btn" id="draft-btn" @click="menuBtnClickEvent(buttons.draft)">
				<img src="../assets/sideMenu/draft-gray.png" width="20px" class="icon-img-active" id="draft-img-nonActive">
				<img src="../assets/sideMenu/draft.png" width="20px" class="icon-img-nonActive" id="draft-img-active">
				<div class="hidden-label" id="draft-label">Draft</div>
			</div>
			<div class="menu-btn" id="contacts-btn" @click="menuBtnClickEvent(buttons.contacts)">
				<img src="../assets/sideMenu/contacts-gray.png" width="20px" class="icon-img-active" id="contacts-img-nonActive">
				<img src="../assets/sideMenu/contacts.png" width="20px" class="icon-img-nonActive" id="contacts-img-active">
				<div class="hidden-label" id="contacts-label">Contacts</div>
			</div>
			<div class="menu-btn" id="trash-btn" @click="menuBtnClickEvent(buttons.trash)">
				<img src="../assets/sideMenu/trash-gray.png" width="20px" class="icon-img-active" id="trash-img-nonActive">
				<img src="../assets/sideMenu/trash.png" width="20px" class="icon-img-nonActive" id="trash-img-active">
				<div class="hidden-label" id="trash-label">Trash</div>
			</div>
			<div id="side-menu-seperator" class="side-menu-seperator-closed"></div>
			<div class="menu-btn" id="folders-btn" @click="folderBtnClickEvent()">
				<img src="../assets/sideMenu/folders-gray.png" class="icon-img-active" id="folders-img-nonActive" width="20px">
				<img src="../assets/sideMenu/folders.png" class="icon-img-nonActive" id="folders-img-active" width="20px">
				<div class="hidden-label" id="folders-label">Folders</div>
			</div>
			<div id="folders-area" class="folders-area-hidden" ref="customFoldersContainer">
				<div id="add-folder-btn" class="in-folders-area" @click="addNewCustomFolder()">
					<img src="../assets/sideMenu/compose-gray.png" width="15px">
					<div id="add-folder-label" >add folder</div>
				</div>
			</div>
		</div>
		<div class="menu-btn" id="compose-btn"
				@mouseover="composeBtnMouseOver()"
				@mouseleave="composeBtnMouseLeave()"
				@click="composeBtnOnClick()">
			<img src="../assets/sideMenu/compose-gray.png" class="icon-img-active" id="compose-img-nonActive" width="20px">
			<img src="../assets/sideMenu/compose.png" class="icon-img-nonActive" id="compose-img-active" width="20px">
			<div class="hidden-label" id="compose-label">Compose</div>
		</div>
  </div>
</template>

<script>
import customFolder from '../components/sideMenu/folder.vue';
import Vue from 'vue';
import axios from 'axios';
import {mapGetters ,mapActions} from 'vuex';
import store from '../store'
export default {
	name: 'sideMenu',
	data(){
		return{
			closedMenu: true,
      mainFolders: ['inbox', 'sent', 'draft', 'contacts', 'trash', 'folders'],
      customFolders: [],
			buttons: {
				inbox: {
					id: 0,
					name: 'Inbox',
					active: true,
				},
				sent: {
					id: 1,
					name: 'Sent',
					active: false,
				},
				draft: {
					id: 2,
					name: 'Draft',
					active: false,
				},
				contacts: {
					id: 3,
					name: 'Contacts',
					active: false,
				},
				trash: {
					id: 4,
					name: 'Trash',
					active: false,
				},
				folders: {
					id: 5,
					name: 'Folders',
					active: false,
					hiddenFolders: true,
					subFolders: [],
				},
			}
		}
  },
  computed: mapGetters(['getUserId']),
	methods: {
    ...mapActions(['setActiveFolder', 'updateEmails']),
		//menu-open/close-btn actions
		toggleMenu(){
			if(this.closedMenu) this.openMenu();
			else this.closeMenu();
			this.closedMenu = !this.closedMenu;
		},
		openMenu() {
			document.getElementById("menu").style.width = "11rem";
			document.getElementById("main").style.marginLeft = "11rem";
			document.getElementById("current-email-view").style.marginLeft = "11rem";
			const closedImage = document.getElementById("menu-open-img-closed");
			closedImage.className = "icon-img-nonActive";
			const openedImage = document.getElementById("menu-open-img-opened");
			openedImage.className = "icon-img-active";

			document.getElementById("side-menu-seperator").className = "side-menu-seperator-opened";

			for(let folder of this.mainFolders){
				const folderBtn = this.buttons[folder];
				folderBtn.label.className = "active-label";
				folderBtn.button.className = "menu-btn-expanded";
				if(folderBtn.active) folderBtn.button.className = 'menu-btn-expanded-focus';
			}

			const composeBtn = document.getElementById("compose-btn");
			composeBtn.className = "menu-btn-expanded";
			composeBtn.style.marginLeft = '0rem ';
      document.getElementById("compose-label").className = "active-label";
      
      this.showFoldersArea();
		},
		closeMenu() {
			document.getElementById("menu").style.width = "4rem";
			document.getElementById("main").style.marginLeft = "4rem";
			document.getElementById("current-email-view").style.marginLeft = "4rem";
			const closedImage = document.getElementById("menu-open-img-closed");
			closedImage.className = "icon-img-active";
			const openedImage = document.getElementById("menu-open-img-opened");
			openedImage.className = "icon-img-nonActive";

			document.getElementById("side-menu-seperator").className = "side-menu-seperator-closed";

			for(let folder of this.mainFolders){
				const folderBtn = this.buttons[folder];
				folderBtn.label.className = "hidden-label";
				folderBtn.button.className = "menu-btn";
			}

			const composeBtn = document.getElementById("compose-btn");
			composeBtn.className = "menu-btn";
			composeBtn.style.marginLeft = '0.75rem';
			document.getElementById("compose-label").className = "hidden-label";
			document.getElementById("folders-area").className = "folders-area-hidden";
			this.buttons.folders.hiddenFolders = true;
		},
		//buttons actions
		enableButton(btn){
			btn.active = true;
			btn.image.active.className = "icon-img-active";
			btn.image.nonActive.className = "icon-img-nonActive";
			for(let folder of this.mainFolders){
				const folderBtn = this.buttons[folder];
				if(folderBtn.id === btn.id) continue;
				this.disableButton(folderBtn);
			}
			if(btn.id !== 5) this.hideFoldersArea();
			if(!this.closedMenu) btn.button.className = 'menu-btn-expanded-focus'
		},
		disableButton(btn){
			btn.image.active.className = "icon-img-nonActive";
			btn.image.nonActive.className = "icon-img-active";
			if(!this.closedMenu) btn.button.className = 'menu-btn-expanded';
			btn.active = false;
		},
		//button
		menuBtnClickEvent(btn){
			store.commit('setSubOpActive', false);
			store.commit('setActiveEmail', false);
			this.enableButton(btn);
			document.getElementById("current-folder-name").innerHTML = btn.name;
			
			this.showFolderEmails(btn)    
    },
    showFolderEmails(btn){
      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//getEmailsList`, {
        params: { 
          userId: this.getUserId,
          folderName: btn.name.toLowerCase(),
          sortType: 1,
          sortIdntifier: 0,
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
		//folders button
		folderBtnClickEvent(){
			const folderBtn = this.buttons.folders;
			this.enableButton(folderBtn);
			if(this.closedMenu) {
        this.openMenu();
        this.closedMenu = false;
      }
      else{
        if(folderBtn.hiddenFolders) this.showFoldersArea();
        else this.hideFoldersArea();
      }
    },
    showFoldersArea(){
      const folderBtn = this.buttons.folders;
      const foldersArea = document.getElementById("folders-area");
      foldersArea.className = "folders-area-visible";
      folderBtn.hiddenFolders = false;
    },
    hideFoldersArea(){
      const folderBtn = this.buttons.folders;
      const foldersArea = document.getElementById("folders-area");
      foldersArea.className = "folders-area-hidden";
      folderBtn.hiddenFolders = true;
    },
		//compose
		composeBtnMouseOver(){
			document.getElementById("compose-img-nonActive").className = "icon-img-nonActive";
			document.getElementById("compose-img-active").className = "icon-img-active";
		},
		composeBtnMouseLeave(){
			document.getElementById("compose-img-nonActive").className = "icon-img-active";
			document.getElementById("compose-img-active").className = "icon-img-nonActive";
		},
		composeBtnOnClick(){
			const composeContainer = document.getElementById("compose-container");
			const composePage = document.getElementById("compose-page");
			composeContainer.className = "compose-container-normal";
			composePage.className = "compose-page-normal";
			composeContainer.style.visibility = "visible";
			composeContainer.style.display = "flex";
    },
    addCustomFolder(folderName){
      let CustomFolder = Vue.extend(customFolder);
      const newCustomFolder = new CustomFolder({
        propsData: { existingFolderName: folderName, customFolderNumber: 1 }
      })
      newCustomFolder.$mount();
      const addFolderBtn = document.getElementById("add-folder-btn");
      this.$refs.customFoldersContainer.insertBefore(newCustomFolder.$el, addFolderBtn);
      store.commit('addCustomFolder', newCustomFolder);
    },
    addNewCustomFolder(){
      this.addCustomFolder(null)
    },
    fitchAllCustomFolders(){
      axios.get(`http://localhost:8080//getAllCustomFolders`, {
        params: { userId: this.getUserId }
      })
      .then( response => {
        this.customFolders = response.data;
        for(let folder of this.customFolders){
          this.addCustomFolder(folder);
        }
      })
      .catch( error => console.log(error)); 
    }
	},
	mounted(){
		for(let folder of this.mainFolders){
			const folderBtn = this.buttons[folder];
			folderBtn.button = document.getElementById(`${folder}-btn`);
			folderBtn.image = {};
			folderBtn.image.active = document.getElementById(`${folder}-img-active`);
			folderBtn.image.nonActive = document.getElementById(`${folder}-img-nonActive`);
			folderBtn.label = document.getElementById(`${folder}-label`);
    }
      this.fitchAllCustomFolders();
  }
}
</script>

<style scoped>
.sideMenu{
  height: 100%; /* 100% Full-height */
  width: 4rem; /* 0 width - change this with JavaScript */
  position: fixed; /* Stay in place */
  z-index: 1; /*Stay on top*/
  top: 0; /* Stay at the top */
  left: 0;
  background-color: white;
	border-right: 1px solid rgb(224, 224, 224, 0.6);
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 20px; 
  transition: 0.4s ease; /* 0.5 second transition effect to slide in the sidenav */
}

.menu-btn{
	user-select: none;
	display: flex;
	justify-content: center;
	align-items: center;
	transition: 0.2s ease;
	width: 2.5rem;
	height: 2.5rem;
	border-radius: 50%;
}
.menu-btn:hover{
	background-color: rgb(224, 224, 224, 0.6);
	transition: 0.2s ease;
}

.menu-btn-expanded{
	user-select: none;
	display: flex;
	justify-content: flex-start;
	align-items: center;
	transition: 0.2s ease;
	width: 11rem;
	height: 2.5rem;
}
.menu-btn-expanded-focus{
	user-select: none;
	display: flex;
	justify-content: flex-start;
	align-items: center;
	transition: 0.2s ease;
	width: 11rem;
	height: 2.5rem;
	background-color: rgb(170, 201, 253);
	font-weight: bold;
	color: #3474E0;
}
.menu-btn-expanded-focus img{
	margin-left: 2rem;
	margin-right: 1rem;
}
.menu-btn-expanded img{
	margin-left: 2rem;
	margin-right: 1rem;
}
.menu-btn-expanded:hover{
	background-color: rgb(224, 224, 224);
	transition: 0.2s ease;
}
.icon-img-active{
	pointer-events: none;
	visibility: visible;
	display: block;
}
.icon-img-nonActive{
	pointer-events: none;
	visibility: hidden;
	display: none;
}
.hidden-label{
	visibility: hidden;
	display: none;
	transition-delay: 0.6s;
}
.visible-label{
	visibility: visible;
	display: block;
	transition-delay: 0.6s;
}
.menu-open{
	margin-bottom: 2rem;
	margin-left: 1rem;
	width: 2rem;
	height: 2rem;
	display: flex;
	justify-content: center;
	align-items: center;
	user-select: none;
}

.side-menu-seperator-closed{
	width: 3rem;
	margin-left: 0.5rem;
	margin-top: 5px;
	margin-bottom: 5px;
	border-bottom: 1px solid rgb(224, 224, 224);
	transition: 0.4s ease-out;
}

.side-menu-seperator-opened{
	width: 10rem;
	margin-left: 0.5rem;
	margin-top: 5px;
	margin-bottom: 5px;
	border-bottom: 1px solid rgb(224, 224, 224);
	transition: 0.4s ease-in;
}

#buttons-group{
	display: flex;
	flex-direction: column;
	align-items: center;
	color: #767676;
	font-size: medium;
	font-weight: 400;
}

#compose-btn{
	position: absolute;
	font-size: medium;
	font-weight: 400;
	color: #767676;
	bottom: 4rem;
	margin-left: 0.75rem;
}
#compose-btn:hover{
	background-color: rgb(170, 201, 253);
	color: #3474E0;
	font-weight: bold;
}

#folders-area{
	display: flex;
	flex-direction: column;
	justify-content: flex-start;
	align-content: center;
	width: 100%;
}
.folders-area-hidden{
	visibility: hidden;
	display: none;
	transition: 0.1s ease;
}
.folder-area-visible{
	visibility: visible;
	display: flex;
	transition: 0.1s ease;
}
.in-folders-area{
	user-select: none;
	display: flex;
	justify-content: flex-start;
	align-items: center;
	height: 2rem;
	padding-left: 3rem;
}
#add-folder-btn:hover{
	background-color: rgb(224, 224, 224);
}
#add-folder-label{
	margin-left: 0.5rem;
}
/* #767676 */
/* #3474E0 */
</style>