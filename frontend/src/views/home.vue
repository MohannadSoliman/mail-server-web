<template>
  <div id="home">
    <sideMenu/>
    <div id="main">
      <headerBar/>
      <controlBar/>
      <titleBar/>
      <div id="emails-viewer" ref="emailsContainer"></div>
      <compose/>
    </div>
    <currentEmail/>
  </div>
</template>

<script>
import sideMenu from '../components/sideNav.vue';
import headerBar from '../components/header.vue';
import controlBar from '../components/controlBar.vue';
import compose from '../components/compose.vue';
import titleBar from '../components/header/titleBar.vue';
import currentEmail from '../components/currentEmail.vue';

import emailCard from '../components/compose/emailCard.vue';
import Vue from 'vue';

export default {
  name: 'home',
  components: {
    sideMenu,
    headerBar,
    controlBar,
    compose,
    titleBar,
    currentEmail,
  },
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
</style>