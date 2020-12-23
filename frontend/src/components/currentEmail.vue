<template>
  <div id="current-email-view" :class="[active ? 'to-front' : 'to-back']">
    <div id="title">{{emailInfo.subject}}</div>
    <div id="meta-data">
      <div id="sender-reciever">
        <label for="sender-reciever" id="sr-label">{{`${sent? 'To:': 'From:'}`}}</label>
        {{`${sent ? emailInfo.receivers: emailInfo.sender}`}}
      </div>
      <div id="date">
        {{emailInfo.date}}
      </div>
    </div>
    <div id="body">
      {{emailInfo.body}}
    </div>
    <div id="attachments-area">
      <div class="attachment-card" v-for="attachment of emailInfo.attachments" :key="attachment"
           @click="downloadAttachment(attachment)">
        {{attachment}}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'currentEmail',
  data(){
    return{
      active: true, //to hide or show the email view page
      sent: false,
      emailInfo:{
        sender: "alihassan@lol.com",
        receivers: "loloooo@lol.com, moAli@mo.com, rrrrrr@rrr.com",
        subject: "A test to view an email title",
        title: "",
        body: "",
        date: "12 Dec 2021",
        attachments: ["naive.png", "assignment2.png", "report.pdf"],
      },
    }
  },
  methods:{
    downloadAttachment(attachmentName){
      console.log(attachmentName)
    }
  },
}
</script>

<style scoped>
#current-email-view{
  top: 7.1rem;
  position: absolute;
  width: 95.4%;
  height: 86.3%;
  background-color: white;
  margin-left: 4.05rem;
  transition: margin-left .4s ease-out;
  font-size: medium;

  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
}

.to-back{
  visibility: hidden;
  display: none;
}
.to-front{
  visibility: visible;
  display: block;
}

#title{
  height: 3rem;
  width: 96.6%;
  padding: 1rem;
  padding-left: 2rem;
  font-size: x-large;
  text-align: left;
  vertical-align: middle;
  line-height: 3rem;
}

#sender-reciever{
  height: 2rem;
  padding: 0.5rem;
  padding-left: 2rem;
  overflow-x: auto;
  text-align: left;
  vertical-align: middle;
  line-height: 2rem;
  width: 80%;
}

#sr-label{
  color: #767676;
  font-weight: bold;
  margin-right: 0.5rem;
}

#meta-data{
  width: 96.6%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid rgb(224, 224, 224);
}

#date{
  height: 2rem;
  vertical-align: middle;
  text-align: center;
  line-height: 2rem;
  margin-right: 2rem;
  color: #767676;
  font-weight: bold;
  font-size: small;
}

#body{
  width: 95%;
  height: 70%;
  margin-top: 0.5rem;
  background-color: rgb(224, 224, 224);
}

#attachments-area{
  border-top: 1px solid rgb(224, 224, 224);
  overflow-x: auto;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  width: 95%;
  height: 3rem;
  margin-top: auto;
  margin-bottom: 1rem;
}

.attachment-card{
  border-radius: 1rem;
  padding: 0rem 0.5rem;
  margin-right: 0.5rem;
  height: 2rem;
  text-align: center;
  vertical-align: middle;
  line-height: 2rem;
  background-color: rgb(224,224,224);
  color: rgb(63, 63, 63);
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  user-select: none;
}
</style>