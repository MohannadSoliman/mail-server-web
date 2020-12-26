import axios from 'axios';

const state = {
  userId: null,
  activeFolder: "inbox",
  currentStartIndex: 0,
  activeFolderEmailsNum: 0,
  currentEmailsList: "", //json
  sortingParam: {
    sortType: 1,
    sortIdntifier: 0,
  },
  currentEmailInfo: {
    id: "",
    sender: "",
    receivers: "",
    subject: "",
    priority: "String",
    date: "String",
    attachments: [],
  },
  subOperation:{
    active: false,
    start: 0,
    required: "",
    type: "",
    sortType: 1,
    sortIdntifier: 0,
    ops:{
      search: false,
      sort: false,
      filter: false,
    },
    searchBy: "all",
    word: "inbox",
  },
  activeEmail: false,
};
    
const getters = {
  getUserId: state => state.userId,
  getEmailsList: state => state.currentEmailsList,
  getSortingParam: state => state.sortingParam,
  getStartIndex: state => state.currentStartIndex,
  getActiveFolder: state => state.activeFolder,
  getEmailsNum: state => state.activeFolderEmailsNum,
  getCurrentEmailInfo: state => state.currentEmailInfo,
  getActiveStatus: state => state.activeEmail,
  getSubOperation: state => state.subOperation,
  getOpsConds: state => state.subOperation.ops,
};
    
const actions = {
  setActiveFolder: ({commit}, activeFolderName) => {
    commit('changeActiveFolder', activeFolderName);
    commit('setStartIndex', 0);
    commit('setActiveFolderEmailsNum');
  },
  //increase / decrease starting index
  incrementStartIndex: ({commit}) => {
    let newStartIndex = state.currentStartIndex + 15;
    commit('setStartIndex', newStartIndex);
  },
  decrementStartIndex: ({commit}) => {
    let newStartIndex = state.currentStartIndex - 15;
    commit('setStartIndex', newStartIndex);
  },
  //sorting
  sortNewer: ({commit}) => commit('setSortingIdentifier', 0),
  sortOlder: ({commit}) => commit('setSortingIdentifier', 1),

  sortByPriority: ({commit}) => commit('setSortType', 0),
  sortByDate: ({commit}) => commit('setSortType', 1),
  //set emailsList
  updateEmails: ({commit}, list) => {
    commit('setEmailsList', list);
    commit('setActiveFolderEmailsNum');
  },
  //user id
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
  changeActiveFolder: (state, activeFolderName) => state.activeFolder = activeFolderName,
  setStartIndex: (state, startIndex) => state.currentStartIndex = startIndex,
  setSortingIdentifier: (state, identifier) => state.sortingParam.sortIdntifier = identifier,
  setSortType: (state, type) => state.sortingParam.sortType = type,
  setEmailsList: (state, list) => state.currentEmailsList = list,
  setActiveFolderEmailsNum: (state) => {
    axios.get(`http://localhost:8080//getEmailsNumber`, {
      params: { 
        userId: state.userId,
        folderName: state.activeFolder,
      }
    })
    .then( response => {
      state.activeFolderEmailsNum = response.data;
    })
    .catch( error => console.log(error)); 
  },
  setCurrentEmail: (state, email) => state.currentEmailInfo = email,
  setActiveEmail: (state, active) => state.activeEmail = active,
  setSubOpActive: (state, active) => state.subOperation.active = active,
  setSubOpStart: (state, start) => state.subOperation.start = start,
  setSubOpRequired: (state, required) => state.subOperation.required = required,
  setSubOpType: (state, type) => state.subOperation.type = type,
  setSubOpSortType: (state, type) => state.subOperation.sortType = type,
  setSubOpSortIdentifier: (state, idn) => state.subOperation.sortIdntifier = idn,

  setSearchCond: (state, status) => state.subOperation.ops.search = status,
  setFilterCond: (state, status) => state.subOperation.ops.filter = status,
  setSortCond: (state, status) => state.subOperation.ops.sort = status,
  
  setSearchBy: (state, searchBy) => state.subOperation.searchBy = searchBy,
  setSearchWord: (state, word) => state.subOperation.word = word,
};
    
export default{
  state,
  getters,
  actions,
  mutations,
};