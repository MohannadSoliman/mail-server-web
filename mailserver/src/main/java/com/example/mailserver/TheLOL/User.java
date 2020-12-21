package com.example.mailserver.TheLOL;

import com.example.mailserver.TheLOL.Folders.FoldersMap;
import com.example.mailserver.TheLOL.operationsHandlers.EmailHandler;
import com.example.mailserver.TheLOL.operationsHandlers.FilesHandler;
import com.example.mailserver.TheLOL.operationsHandlers.FolderHandler;

import java.io.IOException;

public class User {
    private FoldersMap foldersMap;
    private FilesHandler filesHandler;
    private FolderHandler folderHandler;
    private EmailHandler emailHandler;

    public User(String emailAddress){
        this.foldersMap = new FoldersMap();
        this.filesHandler = new FilesHandler();
        this.folderHandler = new FolderHandler(foldersMap, emailAddress);
        this.emailHandler = new EmailHandler(foldersMap, filesHandler);

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
}
