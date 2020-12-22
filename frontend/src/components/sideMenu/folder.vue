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
      <div class="menu-item" @click="removeFolder()">delete</div>
    </div>
  </div>
</template>

<script>
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
      ForbiddenFolderNames: ["sent", "inbox", "draft", "trash", "contacts"],
      mouseOn: false,
      enabledActionsMenu: false,
      isBeingEdited: false,
      actionsMenuVisible: false,
      focused: false,
    }
  },
  methods:{
    setFolderName(){
      if(this.ForbiddenFolderNames.includes(this.folderName.toLowerCase())){
        this.folderName = "";
        //show error msg
      }
      this.folderId = this.folderName;
      this.isBeingEdited = false;
      this.set = true;
    },
    changeFolderName(){
      this.actionsMenuVisible = false;
      const folderNameInput = document.getElementById(`folder-name-${this.folderId}`);
      this.isBeingEdited = true;
      folderNameInput.focus();
    },
    removeFolder(){
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
      this.focused = !this.focused;
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
      if(this.mouseOn || !this.isBeingEdited || this.set) {
        return;
      }
      this.setFolderName();
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

input[type=text]:focus{
  outline-style: none;
  border: none;
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