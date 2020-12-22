<template>
  <div id="sign-up">
    <div id="sign-up-form-container">
      <div id="sign-up-form">
        <h1 id="sign-up-label">Sign Up</h1>
        <input id="email" placeholder="Enter Email" type="text">
        <div class="alert" id="signUp-email-alert">alert</div>
        <input id="original-password" placeholder="Enter password" type="text">
        <div class="alert" id="signUp-password-alert">alert</div>
        <input id="check-password" placeholder="Re-Enter password" type="text">
        <div class="alert" id="signUp-confirm-password-alert">alert</div>
        <div class="nav">
          <div id="sign-in-instead" @click="goToSignIn()">sign in instead?</div>
          <div id="sign-up-btn">sign up</div>
        </div>
      </div>   
    </div>
  </div> 
</template>

<script>
export default {
  name: 'signIn',
  data(){
    return{

    }
  },
  methods:{
    goToSignIn(){
      this.$router.push('/signIn');
    },
    validate(){
      var email = document.getElementById("email").value
      var originalPass = document.getElementById("original-password").value
      var confirm_password = document.getElementById("check-password").value
      var email_alert= document.getElementById("signUp-email-alert")
      var original_passsword_alert= document.getElementById("signUp-password-alert")
      var confirm_password_alert= document.getElementById("signUp-confirm-password-alert")
      var pattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/

      if(email.length == 0){
        email_alert.innerHTML = "this field is required"
      }else if (email.match(pattern)){

      }
    }
     
    
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
