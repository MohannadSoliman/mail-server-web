<template>
  <div id="control-bar">
		<div id="refresh-btn" class="control-btn">
			<img src="../assets/controlBar/refresh-gray.png" width="20px">
		</div>
		<form id="select-all-container">
			<input type="checkbox" id="select-all" value="selectAll">
			<label for="select-all">Select all</label>
		</form>
		<div id="emails-nav">
			<div id="backward-btn" class="control-btn">
				<img src="../assets/controlBar/backward.png" width="17px">
			</div>
			<div id="forward-btn" class="control-btn">
				<img src="../assets/controlBar/forward.png" width="17px">
			</div>
		</div>
  </div>
</template>

<script>
export default {
	name: 'controlBar',
	methods:{

	}
}
</script>

<style scoped>
#control-bar{
	display: flex;
	flex-direction: row;
	justify-content: flex-start;
	align-items: center;
	height: 3rem;
	border-bottom: 1px solid rgb(224, 224, 224, 0.6);
	padding-left: 1rem;
}
.control-btn{
	display: flex;
	justify-content: center;
	align-items: center;
	height: 2rem;
	width: 2rem;
	border-radius: 50%;
}
.control-btn:hover{
	background-color: rgb(224, 224, 224, 0.6);
}
#emails-nav{
	height: 2rem;
	display: flex;
	justify-content: space-evenly;
	align-items: center;
	width: 5rem;
	margin-left: auto;
	margin-right: 2rem;
}

input[type=checkbox] + label {
  display: block;
  /* margin: 0.2em; */
  /* padding: 0.2em; */
	user-select: none;
}

input[type=checkbox] {
  display: none;
}
label{
	color: #767676;
	font-size: medium;
	font-weight: normal;
}

input[type=checkbox] + label:before {
  content: "\2714";
  border: 2px solid #767676;
  border-radius: 0.3rem;
  display: inline-block;
  width: 0.8rem;
  height: 0.8rem;
  padding: 0.2rem;
  margin-right: 0.4rem;
  vertical-align: bottom;
  color: transparent;
  transition: .2s;
}

input[type=checkbox] + label:active:before {
  transform: scale(0);
}

input[type=checkbox]:checked + label:before {
  background-color: #3474E0;
  border-color: #3474E0;
}

input[type=checkbox]:disabled + label:before {
  transform: scale(1);
  border-color: #767676;
}

input[type=checkbox]:checked:disabled + label:before {
  transform: scale(1);
  background-color: #3474E0;
  border-color: #3474E0;
}

#select-all-container{
	display: flex;
	justify-content: center;
	align-items: center;
	margin-left: 1.5rem;
}
</style>