<template>
  <div id="sign-in">
    <div id="sign-in-form">
      <div id="form">
        <h1 id="login-label">Sign in</h1>
        <input placeholder="Email" type="text">
        <input placeholder="Password" type="password">
        <div id="nav">
          <div id="create-account" @click="goToSignUp()">Create account</div>
          <div id="sign-in-btn">sign in</div>
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
    goToSignUp(){
      this.$router.push('/signUp');
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#sign-in{
  width: 100%;
  height: 90vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
#sign-in-form{
  width: 25rem;
  height: 30rem;
  border: 2px solid rgb(224, 224, 224);
  border-radius: 0.3rem;
}
#form{
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
  margin-top: 1rem;
  margin-bottom: 1rem;
  border-bottom: 2px solid rgb(224, 224, 224);
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

#nav{
  margin-top: 2rem;
  width: 80%;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
}
</style>
