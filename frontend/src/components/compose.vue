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

      <div id="attachments-area"></div>

      <div id="email-control">
        <div id="move-to-draft">Move to Draft</div>
        <div id="priority-select" :class="`${(emailData.priority).toLowerCase()}`" @click="toggleMenu()">
          <label for="priority-select" class="menu-label">{{emailData.priority}}</label> &#9679;
        </div>
        <div  id="prioirity-menu" 
              :class="priorityMenuVisisble ? 'visible-menu': 'hidden-menu'">
          <div class="menu-item urgent" id="urgent" @click=" setPriority('Urgent')">
            <label for="urgent" class="menu-label">Urgent</label> &#9679;
          </div>
          <div class="menu-item high" id="high" @click="setPriority('High')">
            <label for="high" class="menu-label">High</label> &#9679;
          </div>
          <div class="menu-item normal" id="normal" @click="setPriority('Normal')">
            <label for="normal" class="menu-label">Normal</label> &#9679;
          </div>
          <div class="menu-item low" id="low" @click="setPriority('Low')">
            <label for="low" class="menu-label">Low</label> &#9679;
          </div>
        </div>
        <input type="file" id="attachment-file" @change="attachFile()">
        <div id="attach-btn" @click="chooseAttachment()">
          <img src="../assets/compose/attach.png" width="23px">
        </div>
        <div id="send-btn" @click="send()">Send</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {mapGetters} from 'vuex';
export default {
  name: 'compose',
  data(){
    return{
      expanded: false,
      minimized: false,
      priorityMenuVisisble: false,
      attachmentCardIdCount: 0,
      emailData: {
        recievers: new Map(),
        subject: "",
        body: "",
        priority: "Normal",
        attachments: new Map(),
      },
      composedEmailData: {
        recieversCards: new Map(),
      }
    }
  },
  computed: mapGetters(['getUserId']),
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
      this.reset();
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
        if(this.emailData.recievers.has(recieverEmail)) return;
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
      // recieverEmailCard.id = `reciver-card-${this.recieversCardIdCount++}`;
      recieverEmailCard.id = `${recieverEmail}`;
      //style
      recieverEmailCard.style.border = "1px solid rgb(224,224,224)";
      recieverEmailCard.style.borderRadius = "1rem";
      recieverEmailCard.style.padding = "0rem 0.5rem";
      recieverEmailCard.style.marginLeft = "0.5rem";
      recieverEmailCard.style.marginBottom = "0.5rem";
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
      if(this.emailData.attachments.has(fileName)) return;
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
      // attachmentCard.id = `attachment-${this.attachmentCardIdCount++}`;
      attachmentCard.id = fileName;
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
      // const fileUplaodData = new FormData();
      // fileUplaodData.append("file", file);
      this.emailData.attachments.set(id, file);
    },
    toggleMenu(){
      this.priorityMenuVisisble = !this.priorityMenuVisisble;
    },
    setPriority(priority){
      this.emailData.priority = priority;
      this.priorityMenuVisisble = false;
    },
    send(){
      if(this.emailData.recievers.size == 0) return; 
      const formData = new FormData();
      for (let file of this.emailData.attachments.values()){
        formData.append("files", file);
      } 

      const email = this.collectEmailData();
      console.log(email);
      axios.post('http://localhost:8080//sendEmail', null,
      {params: {
        emailData: email,
      }})
      .then(() => {
        axios.post('http://localhost:8080//uploadMultipleFiles', formData)
        .catch( error => console.log(error));
      })
      .catch( error => console.log(error));   
      this.closeComposePage();
    },
    collectEmailData(){
      this.emailData.subject = document.getElementById("subject-input").value;
      this.emailData.body = document.getElementById("text-body").value;

      let allReceivers = "";
      let first = true;
      for(const recieverEmail of this.emailData.recievers.values()){
        if(first) {
          allReceivers += `${recieverEmail}`;
          first = false;
        }
        else allReceivers += `,${recieverEmail}`;
      }

      let allAttachmentsNames = "";
      first = true;
      for(const attachment of this.emailData.attachments.values()){
        console.log(attachment.name);
        if(first){
          allAttachmentsNames += `${attachment.name}`;
          first = false;
        }
        else allAttachmentsNames += `,${attachment.name}`;
      }

      const email = `{"userId":${this.getUserId},"receivers":"${allReceivers}","title":"${this.emailData.subject}","body":"${this.emailData.body}","priority":"${this.emailData.priority}","attachments":"${allAttachmentsNames}"}`;

      return email;
    },
    reset(){
      //reset receivers
      for(const receiverCard of this.composedEmailData.recieversCards.values()){
        receiverCard.remove();
      }
      this.composedEmailData.recieversCards.clear();
      this.emailData.recievers.clear();
      const recieverEmailInput = document.getElementById("to-input"); 
      recieverEmailInput.value = "";
      document.getElementById("subject-input").value = "";
      //reset attachments
      this.emailData.attachments.clear();
      document.getElementById("attachments-area").innerHTML = "";
      //textbody
      document.getElementById("text-body").value = "";
      //priority
      this.emailData.priority = "Normal";
      //data
      this.attachmentCardIdCount = 0;
      this.emailData.subject = "";
      this.emailData.body = "";
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
  z-index: 2;
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
  overflow-y: auto;
}

.compose-input{
  box-sizing: border-box;
  border: none;
  height: 2rem;
  font-size: 15px;
  width: 40%;
  margin-left: 0.5rem;
}

#to-section .compose-input{
  margin-bottom: 0.5rem;
}
#to-section > input[type=text]:focus,
#subject-section > input[type=text]:focus{
  outline-style: none;
  border: none;
}
.label{
  float: left;
  height: 2rem;
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
  display: grid;
  grid-template-rows: 2.5rem;
  grid-template-columns: 30% 20% 20% 10% 20%;
  justify-items: center;
  align-items: center;
  justify-content: center;
  align-content: center;
}

#send-btn{
  width: 5rem;
  height: 2rem;
  grid-column: 5 / 6;
  color: white;
  background-color: #3474E0;
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
  grid-column: 1 / 2;
  color: #767676;
  text-align: center;
  vertical-align: center;
  line-height: 1.5rem;
  cursor: pointer;
  border-radius: 0.3rem;
  user-select: none;
}

#move-to-draft:hover{
  background-color: rgb(224, 224, 224, 0.6);
  font-weight: 500;
}

#attach-btn{
  width: 2.5rem;
  height: 2.5rem;
  grid-column: 4 / 5;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  user-select: none;
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

#priority-select{
  width: 100%;
  height: 2rem;
  grid-column: 2 / 3;
  background-color: rgb(224, 224, 224);
  font-weight: bold;
  text-align: center;
  vertical-align: center;
  line-height: 2rem;
  border-radius: 0.3rem;
  user-select: none;
  z-index: 2;
}

#prioirity-menu{
  position: absolute;
  width: 18%;
  color: #767676;
  background-color: rgb(224, 224, 224, 0.6);
  left: 32%;
  bottom: 2.5rem;
  border-top-left-radius: 0.3rem;
  border-top-right-radius: 0.3rem;
  padding-bottom: 0.4rem;
}
.menu-item{
  height: 2rem;
  text-align: center;
  vertical-align: middle;
  line-height: 2rem;
}
.menu-item:hover{
  background-color: rgb(224, 224, 224, 0.8);
}

.urgent{
  color:#fc033d;
}
.high{
  color:#fc6703;
}
.normal{
  color:#ffd900;
}
.low{
  color:#1da355;
}
.menu-label{
  color: #767676;
  font-weight: bold;
}

.hidden-menu{
  visibility: hidden;
  display: none;
}
.visible-menu{
  visibility: visible;
  display: block;
}
</style>