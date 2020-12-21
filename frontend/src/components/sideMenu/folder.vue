<template>
  <div :id="folderId" class="custom-folder" @mouseover="mouseOn = true" @mouseleave="mouseOn = false">
    <div :class="['actions-btn', enabledActionsMenu ? 'enabled' : 'disabled']">
      <img src="../../assets/sideMenu/actions.png" width="16px">
    </div>
    <input type="text" :id="`folder-name-${folderId}`" class="folder-name"  @keyup.enter="setFileName()" 
          :value="folderName" :disabled="!isBeingEdited">
  </div>
</template>

<script>
export default {
  name: "customFolder",
  props: {
    existingFolderName: String,
  },
  data(){
    return{
      folderId: "new-custom-folder",
      folderName: "",
      folderNameInput: null,
      ForbiddenFolderNames: ["sent", "inbox", "draft", "trash", "contacts"],
      mouseOn: false,
      enabledActionsMenu: false,
      isBeingEdited: false,
    }
  },
  methods:{
    setFolderName(){
      this.folderName = this.folderNameInput.value;
      if(this.ForbiddenFolderNames.includes(this.fileName.toLowerCase())){
        this.folderName = "";
        //show error msg
      }
      this.folderId = this.folderName;
      this.folderNameInput.disabled = true;
    },
    changeFolderName(){
      this.enabledActionsMenu = false;
      this.folderNameInput.disabled = false;
      this.folderNameInput.focus();
    },
    removeFolder(){
      document.getElementById(this.folderId).remove();
    },
    showEl(){
      console.log(document.getElementById(`folder-name-${this.folderId}`))
    }
  },
  mounted(){
    if(this.existingFolderName !== null){
      this.folderId = this.existingFolderName;
      this.folderName = this.existingFolderName;
    }
    else this.folderNameInput = document.getElementById(`folder-name-${this.folderId}`);
    document.body.onclick = () => {
      if(this.mouseOn || !this.isBeingEdited) return;
      this.removeFolder();
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
  background-color: rgb(224, 224, 224);
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
  margin-left: 0.5rem;
}

input[type=text]:focus{
  outline-style: none;
  border: none;
}
</style>