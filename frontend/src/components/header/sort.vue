<template>
  <div id="sort-bar">
    <div id="sort-select" @click="toggleSortMenu()" :class="[sortMenuVisible ? 'open-menu' : '']">
      <div id="sortBy-label">{{sortParameter}}</div>
      <img src="../../assets/header/sort.png" width="13px" class="sort-icon">
    </div>

    <div  id="sort-menu" 
          :class="sortMenuVisible ? 'visible-menu': 'hidden-menu'">
      <div class="menu-item" @click="sortBy('all')">
        show all
      </div>
      <div class="sub-title">sort by priority</div>
      <div class="menu-item" @click="sortBy('high to low')">
        high to low
      </div>
      <div class="menu-item" @click="sortBy('low to high')">
        low to high
      </div>
      <div class="sub-title">sort by date</div>
      <div class="menu-item" @click="sortBy('newest to oldest')">
        newest to oldest
      </div>
      <div class="menu-item" @click="sortBy('oldest to newest')">
        oldest to newest
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'searchBar',
  data(){
    return{
      sortParameter: "show all",
      sortMenuVisible: false,
    }
  },
  methods:{
    sortBy(sortBy){
      this.sortParameter = sortBy;
    },
    toggleSortMenu(){
      this.sortMenuVisible = !this.sortMenuVisible;
    }
  }
}
</script>

<style scoped>
#sort-bar{
  display: flex;
  align-items: center;
  margin-left: 4rem;
}
#sort-select{
  height: 2rem;
  width: 8rem;
  background-color: rgb(233, 233, 233);
  color: #767676;
  text-align: center;
  vertical-align: middle;
  line-height: 2rem;
  font-size: 14px;
  font-weight: bold;
  user-select: none;
  border: 1px solid #767676;
  border-radius: 0.4rem;

  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;

  padding-left: 0.5rem;
  padding-right: 0.5rem;
}
#sort-select:hover{
  background-color: rgb(224, 224, 224);
}
.sort-icon{
  margin-left: auto;
}

#sort-menu{
  position: absolute;
  width: 9rem;
  color: #767676;
  background-color: rgb(233, 233, 233);
  border-bottom-left-radius: 0.3rem;
  border-bottom-right-radius: 0.3rem;
  border-left: 1px solid #767676;
  border-right: 1px solid #767676;
  border-bottom: 1px solid #767676;
  top: 3rem;
  margin-left: 0rem;
  z-index: 2;
}
.menu-item{
  height: 2rem;
  text-align: center;
  vertical-align: middle;
  line-height: 2rem;
  user-select: none;
}
.menu-item:hover{
  background-color: rgb(224, 224, 224);
  border-bottom-left-radius: 0.3rem;
  border-bottom-right-radius: 0.3rem;
  user-select: none;
}
.sub-title{
  height: 1rem;
  width: 8.6rem;
  text-align: left;
  vertical-align: middle;
  line-height: 1rem;
  font-size: 11px;
  margin-left: 0.2rem;
  border-bottom: 1px solid #767676;
}

.hidden-menu{
  visibility: hidden;
  display: none;
}
.visible-menu{
  visibility: visible;
  display: block;
}

.open-menu{
  border-bottom-left-radius: 0 !important;
  border-bottom-right-radius: 0 !important;
}
#searchBy-label{
  width: 5rem;
  text-align: center;
}
</style>