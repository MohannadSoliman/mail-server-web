<template>
  <div  :id="folderId" :class="['custom-folder', (focused && set) ? 'focused': 'unfocused']" 
        @mouseover="mouseOn = true" 
        @mouseleave="mouseOn = false"
        @click="showEmails()">
    <div  :class="['actions-btn', enabledActionsMenu ? 'enabled' : 'disabled', mouseOn ? 'icon-visible': 'icon-hidden']"
          @click="toggleActionsMenu()">
      <img src="../../assets/sideMenu/actions.png" width="16px">
    </div>
    <input  type="text" :id="`folder-name-${folderId}`" 
            :class="['folder-name', focused&&set ? 'bold': 'normal']"  @keyup.enter="setFolderName()" 
            :disabled="!isBeingEdited" maxlength="13" v-model="folderName">
    <div :class="['actions-menu', actionsMenuVisible ? 'visible': 'hidden']" >
      <div class="menu-item" @click="changeFolderName()">rename</div>
      <div class="menu-item" @click="deleteFolder()">delete</div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import store from "../../store";

export default {
  name: "customFolder",
  props: {
    existingFolderName: String,
    customFolderNumber: Number,
  },
  data(){
    return{
      folderId: "new-custom-folder",
      set: false,
      folderName: "New folder",
      mouseOn: false,
      enabledActionsMenu: false,
      isBeingEdited: false,
      actionsMenuVisible: false,
      focused: false,

      rename: false,
      oldFolderName: "",
    }
  },
  methods:{
    setFolderName(){
      if(this.rename){
        this.setFolderNameAfterRenaming();
        this.rename = false;
      }
      else{
        this.setNewFolderName();
      }
      this.enabledActionsMenu = true;
      const folderNameInput = document.getElementById(`folder-name-${this.folderId}`);
      folderNameInput.className = "folder-name";
    },
    setNewFolderName(){
      axios.get(`http://localhost:8080//createNewCustomFolder`,
      {
        params: { 
          userId: store.getters.getUserId,
          folderName: this.folderName.toLowerCase(),
        }
      })
      .then( response => {
        const canBeCreated = response.data;
        if(canBeCreated){
          this.folderId = this.folderName.toLowerCase();
          this.isBeingEdited = false;
          this.set = true;
        }
        else{
          //show error msg
          this.removeFolder();
        }
      })
      .catch( error => console.log(error)); 
    },
    setFolderNameAfterRenaming(){
      axios.get(`http://localhost:8080//renameCustomFolder`,
      {
        params: { 
          userId: store.getters.getUserId,
          oldFolderName: this.oldFolderName.toLowerCase(),
          newFolderName: this.folderName.toLowerCase(),
        }
      })
      .then( response => {
        const canBeRenamed = response.data;
        if(!canBeRenamed){
          this.folderName = this.oldFolderName;
          //show error msg
        }
        this.folderId = this.folderName.toLowerCase();
        this.isBeingEdited = false;
        this.set = true;
      })
      .catch( error => console.log(error)); 
    },
    deleteFolder(){
      axios.delete(`http://localhost:8080//deleteCustomFolder`,
      {
        params: { 
          userId: store.getters.getUserId,
          folderName: this.folderName.toLowerCase(),
        }
      })
      .then( () => this.removeFolder())
      .catch( error => console.log(error)); 
    },
    changeFolderName(){
      this.oldFolderName = this.folderName;
      this.actionsMenuVisible = false;
      const folderNameInput = document.getElementById(`folder-name-${this.folderId}`);
      this.isBeingEdited = true;
      this.rename = true;
      folderNameInput.focus();
      folderNameInput.className = "folder-name-edited";
      store.commit('setCustomFolderEdited', this);
    },
    removeFolder(){
      store.commit('removeCustomFolder', this);
      document.getElementById(this.folderId).remove();
    },
    toggleActionsMenu(){
      const folderNameInput = document.getElementById(`folder-name-${this.folderId}`);
      folderNameInput.disabled = true;
      this.actionsMenuVisible = !this.actionsMenuVisible;
    },
    showEmails(){
      let itemClicked = event.target;
      if(itemClicked.id !== this.folderId && itemClicked.id !== `folder-name-${this.folderId}`) return;
      for(const folderBtn of store.getters.getCustomFoldersInfo.allcustomFolders){
        folderBtn.focused = false;
      }
      this.focused = !this.focused;
      this.getEmails();
    },
    getEmails(){
      store.commit('changeActiveFolder', this.folderName.toLowerCase());
      store.commit('setStartIndex', 0);
      const homePage = store.getters.getHomePage;
      axios.get(`http://localhost:8080//getEmailsList`, {
        params: { 
          userId: store.getters.getUserId,
          folderName: this.folderName.toLowerCase(),
          sortType: 1,
          sortIdntifier: 0,
          start: 0,
        }
      })
      .then( response => {
        homePage.reset();
        homePage.addEmails(response.data);
        store.commit('setEmailsList', response.data);
      })
      .catch( error => console.log(error)); 
    }
  },
  mounted(){
    if(this.existingFolderName !== null){
      this.folderId = this.existingFolderName;
      this.folderName = this.existingFolderName;
      this.enabledActionsMenu = true;
      this.set = true;
    }
    else {
      this.folderName += this.customFolderNumber;
      this.isBeingEdited = true; 
      this.enabledActionsMenu = false;
      this.mouseOn = true;
    }
    document.onclick = () => {
      const currentCustomFolder = store.getters.getCustomFoldersInfo.beingEdited;
      if(currentCustomFolder === null) return;
      console.log(currentCustomFolder.mouseOn, currentCustomFolder.isBeingEdited, currentCustomFolder.folderId)
      if(currentCustomFolder.mouseOn || !currentCustomFolder.isBeingEdited) {
        return;
      }
      console.log("set");
      currentCustomFolder.setFolderName();
    }
  }
}
</script>

<style scoped>
.custom-folder{
  user-select: none;
	display: flex;
	justify-content: flex-start;
	align-items: center;
	height: 2rem;
	width: 100%;
	padding-left: 3rem;
}
.custom-folder:hover{
	background-color: rgb(224, 224, 224);
}

.actions-btn{
  width: 1.5rem;
  height: 1.5rem;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.actions-btn:hover{
  background-color: rgb(211, 210, 210);
}

.disabled{
  pointer-events: none;
}
.enabled{
  pointer-events: all;
}


.folder-name{
  box-sizing: border-box;
  border: none;
  height: 1.5rem;
  margin-left: 1rem;
  width: 50%;
  font-size: medium;
  color: #767676;
  background-color: transparent;
}

.folder-name-edited{
  box-sizing: border-box;
  border: 1px solid #767676;
  height: 1.5rem;
  margin-left: 1rem;
  width: 50%;
  font-size: medium;
  color: #767676;
  background-color: transparent;
}

input[type=text]:focus{
  outline-style: none;
  border: 1px solid #767676;
}

.actions-menu{
  position: absolute;
  margin-top: 5rem;
  width: 50%;
  height: 4rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
  z-index: 2;
  box-shadow: 3px 4px 5px 1px rgb(224, 224, 224);
}

.menu-item{
  width: 100%;
  height: 2rem;
  border-bottom: 1px solid rgb(224, 224, 224);
  text-align: center;
  vertical-align: middle;
  line-height: 2rem;
}
.menu-item:hover{
  background-color: rgb(224, 224, 224);
}

.visible{
  visibility: visible;
  display: flex;
}
.icon-viisble{
  visibility: visible;
}
.hidden{
  visibility: hidden;
  display: none;
}
.icon-hidden{
  visibility: hidden;
}

.focused{
  background-color: rgb(209, 206, 206);
}
.unfocused{
  background-color: white;
}

.bold{
  font-weight: bold;
}
.normal{
  font-weight: normal;
}
</style>