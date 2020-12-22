<template>
  <div id="sign-in" class="sign-in-page">
    <div id="sign-in-form-container">
      <div id="sign-in-form">
        <h1 id="login-label">Sign in</h1>
        <input id ="email" placeholder="Email" type="text" @keydown="Check()">
        <div class="alert" id="signIn-email-alert">alert</div>
        <input id="pass" placeholder="Password" type="password">
        <div class="alert" id="signIn-password-alert">alert</div>
        <div class="nav">
          <div id="create-account" @click="goToSignUp()">Create account</div>
          <div id="sign-in-btn" >sign in</div>
        </div>
      </div>   
    </div>
    <div class="testing"  @click="testing()">go to home (Testing purposes)</div>
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
    goToSignUp(){
      this.$router.push('/signUp');
    },
    testing(){
      this.$router.push("/home");
    },
    Check(){
      const email = document.getElementById("email").value;
      var al = document.getElementById("signIn-email-alertt")
      var pattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
      if(email.match(pattern)){
        al.classList.add("valid");
        al.classList.remove("invalid");
      }
      else {
        al.classList.add("invalid");
        al.classList.remove("valid");
      }
    }
  }
}
</script>

<style scoped>
#sign-in{
  width: 100%;
  height: 90vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
#sign-in-form-container{
  width: 25rem;
  height: 25rem;
  border: 2px solid rgb(224, 224, 224);
  border-radius: 0.3rem;
}
#sign-in-form{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#login-label{
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


#sign-in-btn{
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
#sign-in-btn:hover{
  background-color: rgb(52, 116, 224, 0.98);
}
#create-account {
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

.testing{
  cursor: pointer;
  user-select: none;
}
</style>
