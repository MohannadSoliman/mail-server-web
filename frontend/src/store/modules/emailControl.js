const state = {
  activeFolder: "inbox",
  currentStartIndex: 0,
  currentEmailsList: "", //json
  sortingParam: {
    sortType: 1,
    sortIdntifier: 0,
  }
};
    
const getters = {
  getEmailsList: state => state.currentEmailsList,
  getSortingParam: state => state.sortingParam,
  getStartIndex: state => state.currentStartIndex,
  getActiveFolder: state => state.activeFolder,
};
    
const actions = {
  setActiveFolder: ({commit}, activeFolderName) => {
    commit('changeActiveFolder', activeFolderName)
    commit('setStartIndex', 0);
  },
  //increase / decrease starting index
  incrementStartIndex: ({commit}) => {
    let newStartIndex = state.currentStartIndex + 10;
    commit('setStartIndex', newStartIndex);
  },
  decrementStartIndex: ({commit}) => {
    let newStartIndex = state.currentStartIndex - 10;
    commit('setStartIndex', newStartIndex);
  },
  //sorting
  sortNewer: ({commit}) => commit('setSortingIdentifier', 0),
  sortOlder: ({commit}) => commit('setSortingIdentifier', 1),

  sortByPriority: ({commit}) => commit('setSortType', 0),
  sortByDate: ({commit}) => commit('setSortType', 1),
  //set emailsList
  updateEmails: ({commit}, list) => commit('setEmailsList', list),
}
    
const mutations = {
  changeActiveFolder: (state, activeFolderName) => state.activeFolder = activeFolderName,
  setStartIndex: (state, startIndex) => state.currentStartIndex = startIndex,
  setSortingIdentifier: (state, identifier) => state.sortingParam.sortIdntifier = identifier,
  setSortType: (state, type) => state.sortingParam.sortType = type,
  setEmailsList: (state, list) => state.currentEmailsList = list,
};
    
export default{
  state,
  getters,
  actions,
  mutations,
};