<template>
  <div id="compose-container" class="compose-container-normal">
    <div id="compose-page" class="compose-page-normal">
      <div id="comopse-header">
        <div>New message</div>

        <div id="compose-controls">
          <div id="min-max-btn" class="compose-btn" @click="min_maxComposePage()">
            <img src="../assets/compose/min-max.png" width="15px">
          </div>
          <div id="shrik-expand-btn" class="compose-btn" @click="expand_shrinkCompose()">
            <img src="../assets/compose/expand.png" width="20px" class="visible-img" id="expand-icon">
            <img src="../assets/compose/shrink.png" width="20px" class="hidden-img" id="shrink-icon">
          </div>
          <div id="compose-cancel-btn" class="compose-btn" @click="closeComposePage()">
            <img src="../assets/compose/cancel.png" width="15px">
          </div>
        </div>
      </div>

      <div id="email-body">
        <div id="to-section" @click="triggerToInput()">
          <div class="label">To</div>
          <input type="text" id="to-input" class="compose-input" @change="addRecieverEmail()">
        </div>
        <div id="subject-section" @click="triggerSubjectInput()">
          <div class="label">subject</div>
          <input type="text" id="subject-input" class="compose-input">
        </div>
        <textarea id="text-body"></textarea>
      </div>
<!-- <form method = "post" enctype="multipart/form-data" action="http://localhost:8085/uploadFile">
<input type="file" name="file">
<input type="submit" value="/upload"/>
</form> -->
      <div id="attachments-area"></div>

      <div id="email-control">
        <div id="move-to-draft">Move to Draft</div>
        <input type="file" id="attachment-file" @change="attachFile()">
        <div id="attach-btn" @click="chooseAttachment()">
          <img src="../assets/compose/attach.png" width="23px">
        </div>
        <div id="send-btn">Send</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'compose',
  data(){
    return{
      expanded: false,
      minimized: false,
      recieversCardIdCount: 0,
      attachmentCardIdCount: 0,
      emailData: {
        recievers: new Map(),
        subject: "",
        body: "",
        attachments: new FormData(),
      },
      composedEmailData: {
        recieversCards: new Map(),
      }
    }
  },
  methods: {
    closeComposePage(){
      const composeContainer = document.getElementById("compose-container");
      const composePage = document.getElementById("compose-page");
      const expandIcon = document.getElementById("expand-icon");
      const shrinkIcon = document.getElementById("shrink-icon");
      composeContainer.className = "compose-container-normal";
      composePage.className = "compose-page-normal";
			composeContainer.style.visibility = "hidden";
      composeContainer.style.display = "none";
      expandIcon.className = "visible-img";
      shrinkIcon.className = "hidden-img";
      this.expanded = false;
    },
    expand_shrinkCompose(){
      if(this.expanded) this.shrinkComposePage();
      else this.expandComposePage();
      this.expanded = !this.expanded;
    },
    expandComposePage(){
      const composeContainer = document.getElementById("compose-container");
      const composePage = document.getElementById("compose-page");
      const expandIcon = document.getElementById("expand-icon");
      const shrinkIcon = document.getElementById("shrink-icon");
      composeContainer.className = "compose-container-expanded";
      composePage.className = "compose-page-expanded";
      expandIcon.className = "hidden-img";
      shrinkIcon.className = "visible-img";
    },
    shrinkComposePage(){
      const composeContainer = document.getElementById("compose-container");
      const composePage = document.getElementById("compose-page");
      const expandIcon = document.getElementById("expand-icon");
      const shrinkIcon = document.getElementById("shrink-icon");
      composeContainer.className = "compose-container-normal";
      composePage.className = "compose-page-normal";
      expandIcon.className = "visible-img";
      shrinkIcon.className = "hidden-img";
    },
    min_maxComposePage(){
      if(this.minimized) this.maxComposePage();
      else this.minComposePage();
      this.minimized = !this.minimized;
    },
    minComposePage(){
      const composeContainer = document.getElementById("compose-container");
      const composePage = document.getElementById("compose-page");
      const expandIcon = document.getElementById("expand-icon");
      const shrinkIcon = document.getElementById("shrink-icon");
      composeContainer.className = "compose-container-min";
      composePage.className = "compose-page-normal";
      expandIcon.className = "visible-img";
      shrinkIcon.className = "hidden-img";
      this.expanded = false;
    },
    maxComposePage(){
      const composeContainer = document.getElementById("compose-container");
      composeContainer.className = "compose-container-normal";
    },
    addRecieverEmail(){
      const recieverEmailInput = document.getElementById("to-input");
      const recieverEmail = recieverEmailInput.value;
      if(this.validEmailInput(recieverEmail)) {
        const newRecieverEmailCard = this.appendReciver(recieverEmail);
        const mapKey = newRecieverEmailCard.id;
        this.composedEmailData.recieversCards.set(mapKey, newRecieverEmailCard);
        this.emailData.recievers.set(mapKey, recieverEmail);
        recieverEmailInput.value = "";
      }
    },
    appendReciver(recieverEmail){
      const recievers = document.getElementById("to-section");
      const recieverEmailInput = document.getElementById("to-input"); 
      //creating the email card
      const recieverEmailCard = document.createElement("div");
      recieverEmailCard.innerHTML = recieverEmail;
      recieverEmailCard.id = `reciver-card-${this.recieversCardIdCount++}`;
      //style
      recieverEmailCard.style.border = "1px solid rgb(224,224,224)";
      recieverEmailCard.style.borderRadius = "1rem";
      recieverEmailCard.style.padding = "0rem 0.5rem";
      recieverEmailCard.style.marginLeft = "0.5rem";
      recieverEmailCard.style.height = "1rem";
      recieverEmailCard.style.textAlign = "center";
      recieverEmailCard.style.verticalAlign = "center";
      recieverEmailCard.style.lineHeight = "1rem";
      recieverEmailCard.style.fontSize = "14px";
      recieverEmailCard.style.color = "rgb(63, 63, 63)";
      recieverEmailCard.style.display = "flex";
      recieverEmailCard.style.flexDirection = "row";
      recieverEmailCard.style.justifyContent = "center";
      recieverEmailCard.style.alignItems = "center";
      //adding the delete button
      const removebutton = document.createElement('div');
      removebutton.innerHTML = "x"
      //style
      removebutton.style.marginLeft = "3px";
      removebutton.style.userSelect = "none";
      removebutton.style.width = "0.6rem";
      removebutton.style.height = "0.6rem";
      removebutton.style.textAlign = "center";
      removebutton.style.verticalAlign = "center";
      removebutton.style.lineHeight = "0.5rem";
      removebutton.style.color = "rgb(63, 63, 63)"
      removebutton.style.cursor = "default"
      //events
      removebutton.onmouseover = () =>{
        removebutton.style.color = "black";
      };
      removebutton.onmouseleave = () => {
        removebutton.style.color = "rgb(63, 63, 63)"
      };
      removebutton.onclick = () => {
        const emailKeyToBeDeleted = recieverEmailCard.id;
        this.composedEmailData.recieversCards.delete(emailKeyToBeDeleted);
        this.emailData.recievers.delete(emailKeyToBeDeleted)
        recieverEmailCard.remove();
      };
      recieverEmailCard.appendChild(removebutton);

      recievers.insertBefore(recieverEmailCard, recieverEmailInput);

      return recieverEmailCard
    },
    validEmailInput(emailInput){
      let mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
      if(emailInput.match(mailformat)) return true;
      return false
    },
    triggerToInput(){
      const recieverEmailInput = document.getElementById("to-input");
      recieverEmailInput.focus();      
    },
    triggerSubjectInput(){
      const subjectInput = document.getElementById("subject-input");
      subjectInput.focus();  
    },
    chooseAttachment(){
      const fileChooser = document.getElementById("attachment-file");
      fileChooser.click();
    },
    attachFile(){
      const fileChooser = document.getElementById("attachment-file");
      if ('files' in fileChooser && fileChooser.files.length > 0){
        const file = fileChooser.files[0];
        const fileName = file.name;
        this.addNewAttachment(fileName, file);
      }
    },
    addNewAttachment(fileName, file){
      const attachmentCard = this.appendAttachemnt(fileName);
      const fileId = attachmentCard.id;
      this.storeFileChoosed(fileId, file);
      const fileChooser = document.getElementById("attachment-file");
      fileChooser.value = "";
    },
    appendAttachemnt(fileName){
      const attachmentArea = document.getElementById("attachments-area");
      //creating the email card
      const attachmentCard = document.createElement("div");
      attachmentCard.innerHTML = fileName;
      attachmentCard.id = `attachment-${this.attachmentCardIdCount++}`;
      //style
      attachmentCard.style.borderRadius = "1rem";
      attachmentCard.style.padding = "0rem 0.5rem";
      attachmentCard.style.marginRight = "0.5rem";
      attachmentCard.style.height = "2rem";
      attachmentCard.style.textAlign = "center";
      attachmentCard.style.verticalAlign = "center";
      attachmentCard.style.lineHeight = "2rem";
      attachmentCard.style.backgroundColor = "rgb(224,224,224)";
      attachmentCard.style.color = "rgb(63, 63, 63)";
      attachmentCard.style.display = "flex";
      attachmentCard.style.flexDirection = "row";
      attachmentCard.style.justifyContent = "center";
      attachmentCard.style.alignItems = "center";
      //adding the delete button
      const removebutton = document.createElement('div');
      removebutton.innerHTML = "x"
      //style
      removebutton.style.marginLeft = "3px";
      removebutton.style.userSelect = "none";
      removebutton.style.width = "0.6rem";
      removebutton.style.height = "0.6rem";
      removebutton.style.textAlign = "center";
      removebutton.style.verticalAlign = "center";
      removebutton.style.lineHeight = "0.5rem";
      removebutton.style.color = "rgb(63, 63, 63)"
      removebutton.style.cursor = "default"
      //events
      removebutton.onmouseover = () =>{
        removebutton.style.color = "black";
      };
      removebutton.onmouseleave = () => {
        removebutton.style.color = "rgb(63, 63, 63)"
      };
      removebutton.onclick = () => {
        const attachmentKeyToBeDeleted = attachmentCard.id;
        this.emailData.attachments.delete(attachmentKeyToBeDeleted)
        attachmentCard.remove();
      };
      attachmentCard.appendChild(removebutton);

      attachmentArea.appendChild(attachmentCard);

      return attachmentCard;   
    },
    storeFileChoosed(id, file){
      const formData = new FormData();
      formData.append("file", file);
      axios.post('http://localhost:8085/uploadFile', formData)
      .then( response => console.log(response))
      .catch( error => console.log(error));
    }
  },
  mounted(){
    const recieverEmailInput = document.getElementById("to-input"); 
    recieverEmailInput.onkeydown = () => {
      const key = event.keyCode || event.charCode;
      if(key === 8){
        if(recieverEmailInput.value !== '') return;
        if(this.emailData.recievers.length > 0){
          const len = this.composedEmailData.recieversCards.length;
          const recieverCardToBeDeleted = this.composedEmailData.recieversCards[len -1];
          const keyToBeDeleted = recieverCardToBeDeleted.id;
          this.composedEmailData.recieversCards.delete(keyToBeDeleted);
          this.emailData.recievers.delete(keyToBeDeleted);
          recieverCardToBeDeleted.remove();
        }
      }
    }
  }
}
</script>

<style scoped>
#compose-container{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  visibility: hidden;
  display: none;
}
.compose-container-normal{
  position: fixed;
  bottom: 0;
  width: 38rem;
  height: 35rem;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
  box-shadow: 3px 10px 3px 6px rgb(224, 224, 224);
  right: 5%;
  transform: scale(1);
  transition: opacity 0.25s 0s, transform 0.25s;
}
.compose-container-expanded{
  position: fixed;
  left: 0;
  top: 0;        
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  border-radius: 0.5rem;
  transform: scale(1.1);
  transition: transform 0.25s;
  z-index: 2;
}
.compose-container-min{
  position: fixed;
  bottom: 0;
  width: 18rem;
  height: 2.5rem;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
  box-shadow: 3px 10px 3px 6px rgb(224, 224, 224);
  right: 5%;
}
#compose-page{
  position: relative;
  background-color: white;
  user-select: none;
}

.compose-page-normal{
  width: 100%;
  height: 100%;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
}
.compose-page-expanded{
  width: 70%;
  height: 75%;
  border-radius: 0.5rem;
}

#comopse-header{
  background-color:rgb(63, 63, 63);
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  height: 2.5rem;
  color: white;
  font-weight: bold;
  font: large;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
  padding-left: 1rem;
}

.compose-btn{
  width: 1.8rem;
  height: 1.8rem;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.1s;
}
.compose-btn:hover{
  background-color: rgba(243, 243, 243, 0.6);
}
#compose-controls{
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  width: 8rem;
  margin-left: auto;
}

#email-body{
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding-top: 0.7rem;
  /* padding: 0.7rem; */
  height: 75%;
}

#to-section,
#subject-section{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  width: 95%;
  border-bottom: 1px solid rgb(224, 224, 224);
  color: #767676;
  font-weight: 500;
  cursor: text;
}
#to-section{
  flex-wrap: wrap;
}

.compose-input{
  box-sizing: border-box;
  border: none;
  height: 2rem;
  font-size: 15px;
  width: 40%;
  margin-left: 0.5rem;
}
#to-section > input[type=text]:focus,
#subject-section > input[type=text]:focus{
  outline-style: none;
  border: none;
}
.label{
  float: left;
  height: 2rem;
  /* width: 10%; */
  vertical-align: center;
  line-height: 2rem;
  text-align: left;
  margin-left: 1rem;
}

#text-body{
  margin-top: 0.5rem;
  resize: none;
  box-sizing: border-box;
  border: none;
  font-size: large;
  width: 95%;
  height: 100%;
  border-bottom: 1px solid rgb(224, 224, 224);
}
#email-body > textarea:focus{
  outline-style: none;
  border: none;
  border-bottom: 1px solid rgb(224, 224, 224);
}
#email-control{
  width: 90%;
  height: 2.5rem;
  margin-left: 5%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#send-btn{
  width: 5rem;
  height: 2rem;
  color: white;
  background-color: #3474E0;
  margin-left: auto;
  font-weight: bold;
  text-align: center;
  vertical-align: center;
  line-height: 2rem;
  cursor: pointer;
  border-radius: 0.3rem;
  user-select: none;
}
#send-btn:hover{
  background-color: #437ada;
}

.hidden-img{
  visibility: hidden;
  display: none;
}
.visible-img{
  visibility: visible;
  display: block;
}

#move-to-draft{
  width: 7rem;
  height: 1.5rem;
  color: #767676;
  text-align: center;
  vertical-align: center;
  line-height: 1.5rem;
  cursor: pointer;
  border-radius: 0.3rem;
  user-select: none;
  left: 5%;
}

#move-to-draft:hover{
  background-color: rgb(224, 224, 224, 0.6);
  font-weight: 500;
}

#attach-btn{
  width: 2.5rem;
  height: 2.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  user-select: none;
  margin-left: 55%;
}
#attach-btn:hover{
  background-color: rgba(224, 224, 224);
}

#attachment-file{
  display: none;
}

#attachments-area{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  height: 2.5rem;
  width: 95%;
  margin-left: 2.5%;
  border-bottom: 1px solid rgb(224, 224, 224);
  overflow-x: auto;
}
</style>