package com.example.mailserver.TheLOL.Folders;

import com.example.mailserver.TheLOL.Email;
import com.example.mailserver.TheLOL.JsonEmailConverter;
import com.example.mailserver.TheLOL.operationsHandlers.FilesHandler;

import java.util.HashMap;

public class Folder {
    private String id;
    private boolean isImmutable;
    private String folderPath = "Database/Users/";
    private FilesHandler filesHandler;
    private String userEmail;
    private HashMap<String, Email> allEmails = new HashMap<String, Email>();

    public Folder(String folderName, boolean isImmutable, String userEmail){
        this.id = folderName;
        this.isImmutable = isImmutable;
        this.userEmail = userEmail;
        this.folderPath += (userEmail + "/" + folderName +".json");
    }

    public void setFilesHandler(FilesHandler filesHandler){
        this.filesHandler = filesHandler;
    }

    public boolean isImmutable(){return isImmutable;}

    public void setFileName(String folderName){
        this.id = folderName;
        this.folderPath = "mailserver/Database/Users/";
        this.folderPath += (userEmail + "/" + folderName +".json");
    }

    public String getFileName() {return id;}

    public Email[] getAllEmailsArray(){
        HashMap<String, Email> tempEmailsMap = getAllEmailsMap();
        return (Email[]) tempEmailsMap.values().toArray();
    }

    private HashMap<String, Email> getAllEmailsMap(){
        if(allEmails != null) return allEmails;
        String emailsJsonStr = filesHandler.readFile(folderPath);
        allEmails = JsonEmailConverter.getInstance().jsonToEmailMap(emailsJsonStr);
        return allEmails;
    }

    public void appendEmail(Email email){
               //
               System.out.println("append");
               System.out.println(email);
               //
        getAllEmailsMap().put(email.getId(), email);
        refreshFolder();
    }

    public Email deleteEmail(String id){
        Email deletedEmail = getAllEmailsMap().remove(id);
        refreshFolder();
        return deletedEmail;
    }

    private void refreshFolder(){
        filesHandler.writeFileFromMap(folderPath, allEmails);
    }
}
