const state = {
  customFolders:{
    beingEdited: null,
    focused: null,
    allcustomFolders: [],
  },
  homePage: null,
  emailsListPage: {
    emailsList: [],
  },
  selectedEmails: [],
};
    
  const getters = {
    getCustomFoldersInfo: state => state.customFolders,
    getEmailsListPageInfo: state => state.emailsListPage,
    getHomePage: state => state.homePage,
    getSelectedEmails: state => state.selectedEmails,
  };
    
  const actions = {
    fetchCustomFolderEdited: ({commit}, folder) => commit('setCustomFolderEdited', folder),
  }
    
  const mutations = {
    setCustomFolderEdited: (state, folder) => state.customFolders.beingEdited = folder,
    setCustomFolderFocused: (state, folder) => state.customFolders.focused = folder,
    addCustomFolder: (state, folder) => state.customFolders.allcustomFolders.push(folder),
    removeCustomFolder: (state, folder) => {
      const folderIndex = state.customFolders.allcustomFolders.indexOf(folder);
      state.customFolders.allcustomFolders.splice(folderIndex, 1);
    },

    setHomePage: (state, homePage) => state.homePage = homePage,

    addEmail: (state, email) => state.emailsListPage.emailsList.push(email),
    removeEmail: (state, email) => {
      const emailIndex = state.emailsListPage.emailsList.indexOf(email);
      state.emailsListPage.emailsList.splice(emailIndex, 1);
    },
    clearEmailList: state => state.emailsListPage.emailsList.length = 0,

    addSelectedEmail: (state, email) => state.selectedEmails.push(email),
    removeSelectEmail: (state, email) => {
      const emailIndex = state.selectedEmails.indexOf(email);
      state.selectedEmails.splice(emailIndex, 1);
    },
    clearSelecteEmails: state => state.selectedEmails.length = 0,
  };
    
  export default{
      state,
      getters,
      actions,
      mutations,
  };