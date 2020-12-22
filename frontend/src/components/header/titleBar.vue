<template>
  <div id="title-bar">
    <div id="sender-reciever-title" class="title-label">From</div>
    <div id="subject-title" class="title-label">Subject</div>
    <div id="priority-title" class="title-label" @click="sottByPriority()">
      {{`Priority (${priotitySorting[`${priotitySortingCond}`]})`}}
      <img src="../../assets/header/sort.png" width="18px" class="sort-img">
    </div>
    <div id="date-title" class="title-label" @click="sortByDate()">
      {{`Date (${dateSorting[`${dateSortingCond}`]})`}}
      <img src="../../assets/header/sort.png" width="18px" class="sort-img">
    </div>
  </div>
</template>

<script>
export default {
  name: 'titleBar',
  data(){
    return{
      priotitySortingCond: 1,
      priotitySorting:{
        1: "high to low",
        2: "low to high",
      },
      dateSortingCond: 1,
      dateSorting:{
        1: "newest to oldest",
        2: "oldest to newest",
      }
    }
  }
}
</script>

<style scoped>
#title-bar{
  height: 2rem;
  width: 100%;
  display: grid;
  grid-template-columns: 5% 20% 35% 15% 20% 5%;
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
#date-title:hover{
  background-color: rgb(224, 224, 224);
}
.sort-img{
  margin-left: 0.5rem;
}
.title-label{
  height: 2rem;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  vertical-align: middle;
  line-height: 1.5rem;
}
</style>