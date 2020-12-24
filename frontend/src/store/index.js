import Vuex from 'vuex';
import Vue from 'vue';
import userSettings from './modules/userSettings.js';
import emailControl from './modules/emailControl.js';
//load Vuex
Vue.use(Vuex);

//Create the store
export default new Vuex.Store({
  modules: {
    userSettings,
    emailControl,
  }
});