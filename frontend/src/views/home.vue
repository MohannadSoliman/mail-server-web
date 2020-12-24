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

import {mapGetters, mapActions} from 'vuex';

import axios from 'axios';

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
    }
  },
  computed: mapGetters(['getEmailsList, getUserId']),
  methods: {
    ...mapActions(['updateEmailsList']),
    addEmails(emailsList){
      let EmailCard = Vue.extend(emailCard);
      for(const email of emailsList){
        const newEmailCard = new EmailCard({
          propsData: { inbox: true, emailInfo: email}
        })
        newEmailCard.$mount();
        this.$refs.emailsContainer.appendChild(newEmailCard.$el);
      }
    },
    updateEmailsList(){
      axios.get(`http://localhost:8080//getEmailsList`, {
        params: { 
          userId: 1,
          folderName: "inbox",
          sortType: 1,
          sortIdntifier: 0,
          start: 0,
        }
      })
      .then( response => {
        console.log(response.data);
        this.addEmails(response.data);
      })
      .catch( error => console.log(error)); 
  },
  },
  mounted(){
    console.log(this.getUserId);
    this.updateEmailsList()
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