<template>
  <div  :id="`${emailInfo.id}`" 
        :class="['email-card', `${emailInfo.priority}`]" 
        @mouseover="showQuickMenu()" @mouseleave="hideQuickMenu()">
		<div :id="`select-email-${emailInfo.id}`" class="unChecked" @click="toggleCheckBox()"></div>
    <div id="sender-recivers" class="content">{{inbox ? email.receivers: email.sender}}</div>
    <div id="subject" class="content">{{email.subject}}</div>
    <div id="priority" class="content">{{email.priority}}</div>
    <div id="date" class="content">{{email.date}}</div>
    <div id="attachemtns">
      <img src="../../assets/compose/attach.png" width="20px" :class="showHideAttachemntIndicator()">
    </div>
    <div :class="['quick-menu', activeQuickMenu ? 'visible': 'hidden', chechBoxChecked ? 'mouse-on': 'mouse-leave']">
      <div class="menu-btn">
        <img src="../../assets/general/moveFolder.png" width="18px">
      </div>
      <div class="menu-btn">
        <img src="../../assets/sideMenu/trash-gray.png" width="18px">
      </div>
    </div>
  </div>
  
</template>

<script>
export default {
  name: 'emailCard',
  props: {
    inbox: Boolean,
    emailInfo: Object,
  },
  data(){
    return{
      email: {
        id: String,
        sender: String,
        receivers: "",
        subject: String,
        priority: String,
        date: String,
        attachments: Array,
      },
      activeQuickMenu: false,
      chechBoxChecked: false,
    }
  },
  methods: {
    toggleCheckBox(){
      const checkBox = document.getElementById(`select-email-${this.emailInfo.id}`);
      const emailCard = document.getElementById(`${this.emailInfo.id}`);
      if(this.chechBoxChecked){
        checkBox.className = "unChecked";
        emailCard.style.backgroundColor = "white";
      }
      else {
        checkBox.className = "checked";
        emailCard.style.backgroundColor = "rgb(224,224,224)"
      }
      this.chechBoxChecked = !this.chechBoxChecked;
    },
    showHideAttachemntIndicator(){
      if(this.email.attachments.length > 0) return "visible";
      return "hidden";
    },
    showQuickMenu(){
      this.activeQuickMenu = true;
    },
    hideQuickMenu(){
      this.activeQuickMenu = false;
    },
    iamhere(){console.log("i am alive")}
  },
  mounted(){
    this.email.id = this.emailInfo.id;
    if(this.inbox) this.email.receivers = this.emailInfo.receivers;
    else this.email.sender = this.emailInfo.sender;
    this.email.subject = this.emailInfo.subject;
    this.email.priority = this.emailInfo.priority;
    this.email.date = this.emailInfo.date;
    this.email.attachments = this.emailInfo.attachments;
  },
}
</script>

<style scoped>
.email-card{
  width: 100%;
  height: 2.5rem;
  display: grid;
  grid-template-columns: 5% 20% 35% 15% 20% 5%;
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
  border-left: 4px solid #fc033d;
}
.high{
  border-left: 4px solid #fca103;
}
.normal{
  border-left: 4px solid #f7ef4a;
}
.low{
  border-left: 4px solid #1da355;
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
}
.has-attachments{
  visibility: hidden;
  display: none;
}
.no-attachments{
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
  z-index: 2;
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
</style>