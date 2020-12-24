import axios from 'axios';
import store from '..';

const state = {
  activeFolder: "inbox",
  currentStartIndex: 0,
  currentEmailsList: null, //json
  sortingParam: {
    sortType: 1,
    sortIdntifier: 0,
  }
};
    
const getters = {
  getEmailsList: state => state.currentEmailsList,
};
    
const actions = {
  setActiveFolder: ({commit}, activeFolderName) => commit('changeActiveFolder', activeFolderName),
  incrementStartIndex: ({commit}) => {
    let newStartIndex = state.currentStartIndex + 10;
    commit('setStartIndex', newStartIndex);
  },
  decrementStartIndex: ({commit}) => {
    let newStartIndex = state.currentStartIndex - 10;
    commit('setStartIndex', newStartIndex);
  },
  updateEmailsList: ({commit}) => {
    axios.get(`http://localhost:8080//getEmailsList`, {
        params: { 
          userId: store.getters.getUserId,
          folderName: state.activeFolder,
          sortType: state.sortingParam.sortType,
          sortIdntifier: state.sortingParam.sortIdntifier,
          start: state.currentStartIndex,
        }
      })
      .then( response => {
        console.log(response.data);
        commit('setEmailsList', response.data);
        return response.data;
      })
      .catch( error => console.log(error)); 
  },
  sortNewer: ({commit}) => commit('setSortingIdentifier', 0),
  sortOlder: ({commit}) => commit('setSortingIdentifier', 1),

  sortByPriority: ({commit}) => commit('setSortType', 0),
  sortByDate: ({commit}) => commit('setSortType', 1),
}
    
const mutations = {
  changeActiveFolder: (state, activeFolderName) => state.activeFolder = activeFolderName,
  setStartIndex: (state, startIndex) => state.startIndex = startIndex,
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