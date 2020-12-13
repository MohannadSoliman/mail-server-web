<template>
  <div class="loginSettings">
      <h1 class="loginStyle"> Login </h1>
      <link rel="stylesheet" href= "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <form >
        <div>
          <img src="img_avatar2.png" alt="avatar">
        </div><br><br>
        <i class="fa fa-user icon"></i>
        <input placeholder="Email" type="text"><br><br><br><br>
        <i class="fa fa-key icon"></i>
        <input placeholder="Password (8 characters minimum)" type="password"><br><br><br>
        <button type="submit" class="btn">Login</button><br><br>
        <span class="psw">No account? <a href="#">Sign Up</a></span>
      </form>
    </div> 
</template>

<script>
export default {
  
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.loginSettings{
  background: cover;
  display:flexbox ;
  justify-content: center;
  align-items: center;
  border: wheat ;
}
.loginStyle{
  font-size: 50px ;
  font-family: "Times New Roman", Times, serif;
  color: blue;
}
.username{
  font-family: "Times New Roman", Times, serif;
  font-size: 25px;
}
input{
  font-size: 15px;
  outline: 0;
  width: 35%;
  border-width: 0 0 2px;
  border-color: green;
}
input:focus{
  border-color: blue;
}
button[type = submit]{
  background-color: dodgerblue;
  color: white;
  padding: 15px 20px;
  border: none;
  cursor: pointer;
  width: 5%;
  opacity: 0.9;
  font-size: 15px;
  border-radius: 38px;
}
button:hover{
  opacity: 1;
}
.imgcontainer {
  text-align: center;
  margin: 24px 0 12px 0;
}
span.psw {
  float: center;
  padding-top: 16px;
}
/*.border /*> input[type = text] :focus{
  width: 35%;
  padding: 5px 15px;
  border: none;
  border-bottom: 2px solid green;
  font-family: "New Time Romans", "Courier New", monospace;
}*/
</style>
