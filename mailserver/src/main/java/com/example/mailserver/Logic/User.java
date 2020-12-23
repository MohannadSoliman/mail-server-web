package com.example.mailserver.Logic;


import java.io.IOException;

import com.example.mailserver.Logic.Filter.Filter;
import com.example.mailserver.Logic.Filter.Search;
import com.example.mailserver.Logic.Folders.FoldersMap;
import com.example.mailserver.Logic.operationsHandlers.EmailHandler;
import com.example.mailserver.Logic.operationsHandlers.FilesHandler;
import com.example.mailserver.Logic.operationsHandlers.FolderHandler;
//change
public class User {
    private FoldersMap foldersMap;
    private FilesHandler filesHandler;
    private FolderHandler folderHandler;
    private EmailHandler emailHandler;
    private Search search;
    private Filter filter;

    public User(String emailAddress){
        this.foldersMap = new FoldersMap();
        this.filesHandler = new FilesHandler();
        this.folderHandler = new FolderHandler(foldersMap, emailAddress, filesHandler);
        this.emailHandler = new EmailHandler(foldersMap, filesHandler);
        this.search = new Search(foldersMap, folderHandler);
        this.filter = new Filter(foldersMap);
        
        folderHandler.createExistingFolders();
    }
    //email
    public void sendEmail(Email email){
        emailHandler.sendEmail(email);
    }

    public void deleteEmail(String emailId, String folderName){
        emailHandler.deleteEmail(emailId, folderName);
    }

    public boolean moveEmail(String emailId, String sourceFolderName, String distFolderName){
        return emailHandler.moveEmail(emailId, sourceFolderName, distFolderName);
    }
    //folders
    public boolean createNewCustomFolder(String folderName) throws IOException {
        return folderHandler.createNewCustomFolder(folderName);
    }

    public boolean deleteCustomFolder(String folderName){
        return folderHandler.deleteFolder(folderName);
    }

    public boolean renameCustomFolder(String oldFolderName, String newFolderName) throws IOException {
        return folderHandler.renameFolder(oldFolderName, newFolderName);
    }
    //view emails
    public Email[] getAllEmailsAsArrayFrom(String folderName){
        return foldersMap.getFolder(folderName).getAllEmailsArray();
    }
    public String getAllEmailsAsJsonFrom(String folderName){
        Email[] allEmailsArray = getAllEmailsAsArrayFrom(folderName);
        return JsonEmailConverter.getInstance().arrayOfEmailsToJson(allEmailsArray);
    }
    //search and filter
    public String searchFile(String required, String fileName, String criteria){
        return search.searchFile(required, fileName, criteria);
    }
    public String searchAllFiles(String required, String criteria){
        return search.searchAllFiles(required, criteria);
    }
    public String filterFile(String priority, String fileName, String criteria){
        return filter.filterFile(priority, fileName, criteria);
    }
}