<template>
  <div id="sign-up">
    <div id="sign-up-form-container">
      <div id="sign-up-form">
        <h1 id="sign-up-label">Sign Up</h1>
        <input id="email" placeholder="Enter Email" type="text">
        <div class="alert" id="signUp-email-alert"></div>
        <input id="original-password" placeholder="Enter password" type="password">
        <div class="alert" id="signUp-password-alert"></div>
        <input id="check-password" placeholder="Re-Enter password" type="password">
        <div class="alert" id="signUp-confirm-password-alert"></div>
        <div class="nav">
          <div id="sign-in-instead" @click="goToSignIn()">sign in instead?</div>
          <div id="sign-up-btn" @click="signUp()">sign up</div>
        </div>
      </div>   
    </div>
  </div> 
</template>

<script>
import axios from 'axios';
import {mapActions} from 'vuex'
export default {
  name: 'signIn',
  data(){
    return{

    }
  },
  methods:{
    ...mapActions(['setCurrentUserId']),
    goToSignIn(){
      this.$router.push('/signIn');
    },
    goTohome(){
      this.$router.push('/home');
    },
    validate(){
      const emailInput = document.getElementById("email");
      const originalPassInput = document.getElementById("original-password");
      const confirmPassInput = document.getElementById("check-password");

      let email = this.getEmailInputValue()
      let originalPass = this.getOriginalPassword();
      let confirm_password = this.getConfirmPassword();

      let pattern = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

      let correctEmail = true;
      let correctPassword = true;

      if(email.length == 0){
        this.setEmailAlert("this field is required");
        emailInput.className = "input-alert";
        correctEmail = false;
      }
      else if (email.match(pattern)){
        this.setEmailAlert("");
        emailInput.className = "";
      }
      else{
        this.setEmailAlert("invalid");
        emailInput.className = "input-alert";
        correctEmail = false;
      }
    
      if(originalPass.length < 8){
        this.setOriginalPassAlert("password must be at least 8 characters");
        originalPassInput.className = "input-alert";
        correctPassword = false;
      }else{
        this.setOriginalPassAlert("");
        originalPassInput.className = "";
      }

      if(originalPass.length == 0){
        this.setOriginalPassAlert("this field is required");
        originalPassInput.className = "input-alert";
        correctPassword = false;
      }

      if((confirm_password == originalPass) && originalPass.length != 0){
        this.setConfirmPassAlert("");
        this.setOriginalPassAlert("");
        originalPassInput.className = "";
        confirmPassInput.className = "";
      }
      else{
        this.setOriginalPassAlert(""); 
        this.setConfirmPassAlert("please re enter password")
        originalPassInput.className = "input-alert";
        confirmPassInput.className = "input-alert";
        correctPassword = false;
      }

      if(correctEmail && correctPassword) return true;
      return false;
    },
    resetEmailInput(){
      const emailInput = document.getElementById("email");
      emailInput.value = "";
    },
    getEmailInputValue(){
      const emailInput = document.getElementById("email");
      return emailInput.value;
    },

    resetOriginalPassword(){
      const originalPassInput = document.getElementById("original-password");
      originalPassInput.value = "";
    },
    getOriginalPassword(){
      const originalPassInput = document.getElementById("original-password");
      return originalPassInput.value;
    },

    resetConfirmPassword(){
      const confirmPassInput = document.getElementById("check-password");
      confirmPassInput.value = "";
    },
    getConfirmPassword(){
      const confirmPassInput = document.getElementById("check-password");
      return confirmPassInput.value;
    },

    setEmailAlert(msg){
      const email_alert= document.getElementById("signUp-email-alert")
      email_alert.innerHTML = msg;
    },
    setOriginalPassAlert(msg){
      const original_passsword_alert= document.getElementById("signUp-password-alert")
      original_passsword_alert.innerHTML = msg;
    },
    setConfirmPassAlert(msg){
      let confirm_password_alert= document.getElementById("signUp-confirm-password-alert")
      confirm_password_alert.innerHTML = msg;
    },
    reset(){
      this.resetEmailInput();
      this.resetPassInput();
      this.setEmailAlert("");
      this.setPassAlert("");
    },
    signUp(){
      const email = this.getEmailInputValue();
      const pass = this.getOriginalPassword();

      if(!this.validate(email, pass)) return;

      axios.get(`http://localhost:8080//signUp`, {
        params: { 
          emailAddress: email,
          password: pass,
        }
      })
      .then( response => {
        if(response.data === false){
          this.setEmailAlert("this email already exists!");
          this.setOriginalPassAlert("");
          this.setConfirmPassAlert("");
        }
        else{
          this.setCurrentUserId(response.data);
          this.goTohome();
          this.reset();
        }
      })
      .catch( error => console.log(error)); 
    },
  }
}
</script>

<style scoped>
#sign-up{
  width: 100%;
  height: 90vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
#sign-up-form-container{
  width: 25rem;
  height: 30rem;
  border: 2px solid rgb(224, 224, 224);
  border-radius: 0.3rem;
}
#sign-up-form{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#sign-up-label{
  margin-top: 4rem;
  font-weight: 500;
  color: rgb(52, 116, 224);
  user-select: none;
}

input{
  box-sizing: border-box;
  border: none;
  width: 80%;
  height: 2rem;
  margin-top: 2rem;
  border-bottom: 2px solid rgb(224, 224, 224);
  font-size: 15px;
}
.input-alert{
  box-sizing: border-box;
  border: none;
  width: 80%;
  height: 2rem;
  margin-top: 2rem;
  border-bottom: 2px solid #eb4034;
  font-size: 15px;
}
input:focus{
  outline-style: none;
  border-bottom: 2px solid rgb(52, 116, 224);
}


#sign-up-btn{
  width: 4.5rem;
  height: 2.2rem;
  text-align: center;
  vertical-align: center;
  line-height: 2.2rem;
  color: white;
  font-size: medium;
  font-weight: bolder;
  background-color: rgb(52, 116, 224);
  border-radius: 0.3rem;
  cursor: pointer;
  user-select: none;
  margin-left: auto;
  margin-right: 0.5rem;
}
#sign-up-btn:hover{
  background-color: rgb(52, 116, 224, 0.98);
}
#sign-in-instead {
  color: rgb(52, 116, 224);
  user-select: none;
  cursor: pointer;
  font-weight: bold;
  text-decoration: underline;
  margin-left: 0.5rem;
}

.alert{
  width: 80%;
  font-size: 12px;
  text-align: left;
  margin-top: 3px;
  padding-left: 5px;
  color: #eb4034;
}

.nav{
  margin-top: 2rem;
  width: 80%;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
}
</style>
