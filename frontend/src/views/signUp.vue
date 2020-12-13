<template>
  <div class="about">
    <h1>This is an about page</h1>
    <div @click="goToSignIn()">go to sign in</div>
  </div>
</template>

<script>
export default {
  name: 'signUp',
  methods:{
    goToSignIn(){
      this.$router.push('/');
    }
  }
}
</script>
