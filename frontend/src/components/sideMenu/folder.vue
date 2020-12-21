<template>
  <div class="custom-folder">
    <div class="actions-btn">
      <img src="../../assets/sideMenu/actions.png" width="20px">
    </div>
    <input type="text" class="folder-name"  @keyup.enter="setFileName()">
  </div>
</template>

<script>
export default {
  name: "customFolder",
  data(){
    return{
      folderName: "",
      fileNameInput: null,
      ForbiddenFileNames: ["sent", "inbox", "draft", "trash", "contacts"],
    }
  },
  methods:{
    setFileName(e){
      this.fileNameInput = e.target;
      this.fileName = this.fileNameInput.value;
      if(this.ForbiddenFileNames.includes(this.fileName.toLowerCase())){
        this.fileName = "";
        //show error msg
      }
      fileNameInput.disabled = true;
    },
    changeFileName(){
      this.fileNameInput.disabled = false;
      this.fileNameInput.focus();
    },

  },
  mounted(){
    
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
}
.actions-btn:hover{
  background-color: rgb(224, 224, 224, 0.6);
}

.folder-name{
  box-sizing: border-box;
  border: none;
  height: 1.5rem;
  margin-left: 0.5rem;
}

.customfolder > input[type=text]:focus{
  outline-style: none;
  border: none;
}
</style>