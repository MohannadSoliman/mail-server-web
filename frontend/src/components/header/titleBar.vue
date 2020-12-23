<template>
  <div id="title-bar">
    <div id="sender-reciever-title" class="title-label">From</div>
    <div id="subject-title" class="title-label">Subject</div>
    <div id="priority-title" :class="['title-label', priorityMenuVisible ? 'focused' : '']" @click="togglePriorityFilterMenu()">
      <div class="filter-label">
        priority
        <img src="../../assets/header/filter.png" width="15px">
      </div>
      <div  id="prioirity-menu" 
            :class="priorityMenuVisible ? 'visible-menu': 'hidden-menu'">
          <div class="menu-item" id="urgent" @click=" setPriorityFilter()">
            <label for="urgent" class="menu-label">show all</label> &#9679;
          </div>
          <div class="menu-item urgent" id="urgent" @click=" setPriorityFilter()">
            <label for="urgent" class="menu-label">Urgent</label> &#9679;
          </div>
          <div class="menu-item high" id="high" @click="setPriorityFilter()">
            <label for="high" class="menu-label">High</label> &#9679;
          </div>
          <div class="menu-item normal" id="normal" @click="setPriorityFilter()">
            <label for="normal" class="menu-label">Normal</label> &#9679;
          </div>
          <div class="menu-item low" id="low" @click="setPriorityFilter()">
            <label for="low" class="menu-label">Low</label> &#9679;
          </div>
      </div>
    </div>
    <div id="date-title" class="title-label">Date</div>
    <div id="attach-title" :class="['title-label',attachMenuVisible  ? 'focused' : '']" @click="toggleAttachFilterMenu()">
      <div class="filter-label">
        <img src="../../assets/compose/attach.png" width="15px">
        <img src="../../assets/header/filter.png" width="15px">
      </div>
      <div  id="attach-menu" 
            :class="attachMenuVisible ? 'visible-menu': 'hidden-menu'">
          <div class="menu-item" @click=" setAttachFilter()">
            show all
          </div>
          <div class="menu-item" @click=" setAttachFilter()">
            with attachments
          </div>
          <div class="menu-item" @click="setAttachFilter()">
            with no attachments
          </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'titleBar',
  data(){
    return{
      priorityMenuVisible: false,
      attachMenuVisible: false,
    }
  },
  methods:{
    togglePriorityFilterMenu(){
      this.priorityMenuVisible = !this.priorityMenuVisible;
      this.attachMenuVisible = false;
    },
    toggleAttachFilterMenu(){
      this.attachMenuVisible = !this.attachMenuVisible;
      this.priorityMenuVisible = false;
    },
    setPriorityFilter(){
      this.priorityMenuVisible = true;
    },
    setAttachFilter(){
      this.attachMenuVisible = true;
    }
  }
}
</script>

<style scoped>
#title-bar{
  height: 1.7rem;
  width: 100%;
  display: grid;
  grid-template-columns: 5% 20% 35% 15% 15% 10%;
  grid-template-rows: 1.5rem;
  justify-items: center;
  align-items: center;
  justify-content: center;
  align-content: center;
  border-bottom: 1px solid rgb(224, 224, 224);
  background-color: white;
  font-size: 13px;
  color: #767676;
  user-select: none;
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
#priority-title:hover{
  background-color: rgb(224, 224, 224);
}
#date-title{
  grid-column: 5 / 6;
}
#attach-title{
  grid-column: 6 / 7;
}
#attach-title:hover{
  background-color: rgb(224, 224, 224);
}
.title-label{
  height: 1.7rem;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  vertical-align: middle;
  line-height: 1.7rem;
  font-weight: bold;
}

#prioirity-menu{
  position: absolute;
  width: 14%;
  color: #767676;
  background-color: rgb(224, 224, 224);
  margin-top: 10.3rem;
  z-index: 2;
}

#attach-menu{
  position: absolute;
  width: 14%;
  color: #767676;
  background-color: rgb(224, 224, 224);
  margin-top: 6.8rem;
  right: 0;
  z-index: 2;
}

.menu-item{
  height: 1.7rem;
  text-align: center;
  vertical-align: middle;
  line-height: 1.7rem;
}
.menu-item:hover{
  background-color: rgb(233, 233, 233);
}

.urgent{
  color:#fc033d;
}
.high{
  color:#fc6703;
}
.normal{
  color:#ffd900;
}
.low{
  color:#1da355;
}
.menu-label{
  color: #767676;
  font-weight: bold;
}

.hidden-menu{
  visibility: hidden;
  display: none;
  background-color: rgb(224, 224, 224);
}
.visible-menu{
  visibility: visible;
  display: block;
  background-color: rgb(224, 224, 224);
}
.focused{
  background-color: rgb(224, 224, 224);
}
.filter-label{
  width: 60%;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
</style>