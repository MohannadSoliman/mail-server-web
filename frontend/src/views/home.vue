<template>
  <div id="home">
    <sideMenu/>
    <div id="main">
      <headerBar/>
      <controlBar/>
      <div id="title-bar">
        <div id="sender-reciever-title">From</div>
        <div id="subject-title">Subject</div>
        <div id="priority-title">Priority</div>
        <div id="data-title">Date</div>
      </div>
      <div id="emails-viewer" ref="emailsContainer"></div>
      <compose/>
    </div>
  </div>
</template>

<script>
import sideMenu from '../components/sideNav.vue';
import headerBar from '../components/header.vue';
import controlBar from '../components/controlBar.vue';
import compose from '../components/compose.vue';

import emailCard from '../components/compose/emailCard.vue';
import Vue from 'vue';

export default {
  name: 'home',
  data(){
    return{
      testingObject: [
        { 
          id: "1234",
          sender: "alihassan200061@gmail.com",
          receivers: "mido113@hotmail.com thelol@lol.com",
          subject: "email vieweing test1",
          priority: "urgent",
          date: "13 Dec 2020",
          attachments: [],
        },
        {
          id: "1235",
          sender: "alihassan200061@gmail.com",
          receivers: "mido113@hotmail.com thelol@lol.com",
          subject: "email vieweing test2",
          priority: "high",
          date: "13 Dec 2020",
          attachments: ["hello"],
        },
      ]
    }
  },
  components: {
    sideMenu,
    headerBar,
    controlBar,
    compose,
  },
  methods: {
    addEmails(){
      let EmailCard = Vue.extend(emailCard);
      for(const email of this.testingObject){
        const newEmailCard = new EmailCard({
          propsData: { inbox: true, emailInfo: email}
        })
        newEmailCard.$mount();
        this.$refs.emailsContainer.appendChild(newEmailCard.$el);
      }
    }
  },
  mounted(){
    this.addEmails();
  }
}
</script>

<style scoped>
html,body{
  height: 100%;
}

#home{
  height: 100%;
}

#main {
  transition: margin-left .4s ease-out;
  margin-left: 4rem;
}

#emails-viewer{
  background-color: white;
  width: 100%;
}

#title-bar{
  height: 1.5rem;
  width: 100%;
  display: grid;
  grid-template-columns: 5% 20% 35% 15% 20% 5%;
  grid-template-rows: 1.5rem;
  justify-items: center;
  align-items: center;
  justify-content: center;
  align-content: center;
  border-bottom: 1px solid rgb(224, 224, 224);
  background-color: white;
  font-size: 13px;
}
#sender-reciever-title{
  grid-column: 2 / 3;
}
#subject-title{
  grid-column: 3 / 4;
}
#priority-title{
  grid-column: 4 / 5;
}
#date-title{
  grid-column: 5 / 6;
}
</style>