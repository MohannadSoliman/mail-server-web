<template>
  <div id="current-email-view" :class="[isActive() ? 'to-front' : 'to-back']">
    <div id="title">{{getCurrentEmailInfo.subject}}</div>
    <div id="meta-data">
      <div id="sender-reciever">
        <label for="sender-reciever" id="sr-label">{{`${isSent()? 'To:': 'From:'}`}}</label>
        {{`${isSent() ? getCurrentEmailInfo.receivers: getCurrentEmailInfo.sender}`}}
      </div>
      <div id="priority">{{`priority: ${getCurrentEmailInfo.priority}`}}</div>
      <div id="date">
        {{getCurrentEmailInfo.date}}
      </div>
    </div>
    <div id="body">
      {{getCurrentEmailInfo.body}}
    </div>
    <div id="attachments-area" @click="printInfo()">
      <div class="attachment-card" v-for="attachment of getCurrentEmailInfo.attachments" :key="attachment"
           @click="downloadAttachment(attachment)">
        {{attachment}}
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters } from 'vuex';
export default {
  name: 'currentEmail',
  data(){
    return{
    }
  },
  computed: mapGetters(['getCurrentEmailInfo', 'getActiveFolder', 'getActiveStatus', 'getUserId']),
  methods:{
    downloadAttachment(attachmentName){
      const FileDownload = require('js-file-download');
      
      axios({
        url: `http://localhost:8080//downloadFile/${attachmentName}`, 
        method: 'GET',
        params: {
          fileName: attachmentName,
          userId: this.getUserId,
          emailId: this.getCurrentEmailInfo.id,
        },
        responseType: 'blob',
      })
      .then((response) => {
        FileDownload(response.data, `${attachmentName}`);
      })
      .catch(error => console.log(error));
    },
    isSent(){
      if(this.getActiveFolder !== "sent") return false; 
      return true;
    },
    isActive(){
      return this.getActiveStatus;
    },
    printInfo(){
      console.log(this.getCurrentEmailInfo);
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
  width: 10rem;
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
  text-align: left;
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
  text-decoration: underline;
  cursor: pointer;
}

#priority{
  width: 10rem;
  margin-right: 10rem;
}
</style>