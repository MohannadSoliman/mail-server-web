const state = {
  userId: null,
};
  
const getters = {
  getUserId: state => state.userId,
};
  
const actions = {
  setCurrentUserId: ({commit}, userId) => {
    if(state.userId === null) {
      commit('setUserId', userId);
      console.log(state.userId);
    }
    else console.log("user id has already been set");
  },
}
  
const mutations = {
  setUserId: (state, userId) => state.userId = userId,
};
  
export default{
    state,
    getters,
    actions,
    mutations,
};