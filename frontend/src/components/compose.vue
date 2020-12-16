<template>
  <div id="compose-container" class="compose-container-normal">
    <div id="compose-page" class="compose-page-normal">
      <div id="comopse-header">
        <div>New message</div>

        <div id="compose-controls">
          <div id="min-max-btn" class="compose-btn" @click="min_maxComposePage()">
            <img src="../assets/compose/min-max.png" width="15px">
          </div>
          <div id="shrik-expand-btn" class="compose-btn" @click="expand_shrinkCompose()">
            <img src="../assets/compose/expand.png" width="20px" class="visible-img" id="expand-icon">
            <img src="../assets/compose/shrink.png" width="20px" class="hidden-img" id="shrink-icon">
          </div>
          <div id="compose-cancel-btn" class="compose-btn" @click="closeComposePage()">
            <img src="../assets/compose/cancel.png" width="15px">
          </div>
        </div>
      </div>

      <div id="email-body">
        <div id="to-section">
          <div class="label">To</div>
          <input type="text" id="to-input" class="compose-input">
        </div>
        <div id="subject-section">
          <div class="label">subject</div>
          <input type="text" id="subject-input" class="compose-input">
        </div>
        <textarea id="text-body"></textarea>
      </div>

      <div id="email-control">
        <div id="send-btn">Send</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'compose',
  data(){
    return{
      expanded: false,
      minimized: false,
    }
  },
  methods: {
    closeComposePage(){
      const composeContainer = document.getElementById("compose-container");
      const composePage = document.getElementById("compose-page");
      const expandIcon = document.getElementById("expand-icon");
      const shrinkIcon = document.getElementById("shrink-icon");
      composeContainer.className = "compose-container-normal";
      composePage.className = "compose-page-normal";
			composeContainer.style.visibility = "hidden";
      composeContainer.style.display = "none";
      expandIcon.className = "visible-img";
      shrinkIcon.className = "hidden-img";
      this.expanded = false;
    },
    expand_shrinkCompose(){
      if(this.expanded) this.shrinkComposePage();
      else this.expandComposePage();
      this.expanded = !this.expanded;
    },
    expandComposePage(){
      const composeContainer = document.getElementById("compose-container");
      const composePage = document.getElementById("compose-page");
      const expandIcon = document.getElementById("expand-icon");
      const shrinkIcon = document.getElementById("shrink-icon");
      composeContainer.className = "compose-container-expanded";
      composePage.className = "compose-page-expanded";
      expandIcon.className = "hidden-img";
      shrinkIcon.className = "visible-img";
    },
    shrinkComposePage(){
      const composeContainer = document.getElementById("compose-container");
      const composePage = document.getElementById("compose-page");
      const expandIcon = document.getElementById("expand-icon");
      const shrinkIcon = document.getElementById("shrink-icon");
      composeContainer.className = "compose-container-normal";
      composePage.className = "compose-page-normal";
      expandIcon.className = "visible-img";
      shrinkIcon.className = "hidden-img";
    },
    min_maxComposePage(){
      if(this.minimized) this.maxComposePage();
      else this.minComposePage();
      this.minimized = !this.minimized;
    },
    minComposePage(){
      const composeContainer = document.getElementById("compose-container");
      const composePage = document.getElementById("compose-page");
      const expandIcon = document.getElementById("expand-icon");
      const shrinkIcon = document.getElementById("shrink-icon");
      composeContainer.className = "compose-container-min";
      composePage.className = "compose-page-normal";
      expandIcon.className = "visible-img";
      shrinkIcon.className = "hidden-img";
      this.expanded = false;
    },
    maxComposePage(){
      const composeContainer = document.getElementById("compose-container");
      composeContainer.className = "compose-container-normal";
    }
  }
}
</script>

<style scoped>
#compose-container{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  visibility: hidden;
  display: none;
}
.compose-container-normal{
  position: fixed;
  bottom: 0;
  width: 38rem;
  height: 35rem;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
  box-shadow: 3px 10px 3px 6px rgb(224, 224, 224);
  right: 5%;
  transform: scale(1);
  transition: opacity 0.25s 0s, transform 0.25s;
}
.compose-container-expanded{
  position: fixed;
  left: 0;
  top: 0;        
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  transform: scale(1.1);
  transition: transform 0.25s;
  z-index: 2;
}
.compose-container-min{
  position: fixed;
  bottom: 0;
  width: 18rem;
  height: 2.5rem;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
  box-shadow: 3px 10px 3px 6px rgb(224, 224, 224);
  right: 5%;
}
#compose-page{
  position: relative;
  background-color: white;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
  user-select: none;
}

.compose-page-normal{
  width: 100%;
  height: 100%;
}
.compose-page-expanded{
  width: 70%;
  height: 75%;
}

#comopse-header{
  background-color:rgb(63, 63, 63);
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  height: 2.5rem;
  color: white;
  font-weight: bold;
  font: large;
  border-top-left-radius: 0.5rem;
  border-top-right-radius: 0.5rem;
  padding-left: 1rem;
}

.compose-btn{
  width: 1.8rem;
  height: 1.8rem;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.1s;
}
.compose-btn:hover{
  background-color: rgba(243, 243, 243, 0.6);
}
#compose-controls{
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  width: 8rem;
  margin-left: auto;
}

#email-body{
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 0.7rem;
  height: 80%;
}

#to-section,
#subject-section{
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 95%;
  height: 2.5rem;
  border-bottom: 1px solid rgb(224, 224, 224);
  color: #767676;
  font-weight: 500;
}

.compose-input{
  box-sizing: border-box;
  border: none;
  height: 2rem;
  font-size: 15px;
  width: 89%;
  margin-left: 0.5rem;
}
#to-section > input[type=text]:focus,
#subject-section > input[type=text]:focus{
  outline-style: none;
  border: none;
}
.label{
  height: 2rem;
  width: 10%;
  vertical-align: center;
  line-height: 2rem;
  text-align: left;
}

#text-body{
  margin-top: 0.5rem;
  resize: none;
  box-sizing: border-box;
  border: none;
  font-size: large;
  width: 95%;
  height: 100%;
  border-bottom: 1px solid rgb(224, 224, 224);
}
#email-body > textarea:focus{
  outline-style: none;
  border: none;
  border-bottom: 1px solid rgb(224, 224, 224);
}
#email-control{
  width: 95%;
  height: 2.5rem;
  margin-left: 2.5%;
  margin-top: auto;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#send-btn{
  width: 5rem;
  height: 2rem;
  color: white;
  background-color: #3474E0;
  margin-left: auto;
  margin-right: 2.5%;
  font-weight: bold;
  text-align: center;
  vertical-align: center;
  line-height: 2rem;
  cursor: pointer;
  border-radius: 0.3rem;
  user-select: none;
  margin-top: auto;
}
#send-btn:hover{
  background-color: #437ada;
}

.hidden-img{
  visibility: hidden;
  display: none;
}
visible-img{
  visibility: hidden;
  display: block;
}
</style>