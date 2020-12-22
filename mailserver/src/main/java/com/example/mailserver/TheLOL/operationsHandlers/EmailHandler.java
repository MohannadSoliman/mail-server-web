package com.example.mailserver.TheLOL.operationsHandlers;

import com.example.mailserver.TheLOL.Email;
import com.example.mailserver.TheLOL.Folders.FoldersMap;
import com.example.mailserver.TheLOL.JsonEmailConverter;

import java.util.HashMap;
import java.util.Queue;
//change
public class EmailHandler {
    private FoldersMap foldersMap;
    private FilesHandler filesHandler;

    public EmailHandler(FoldersMap foldersMap, FilesHandler filesHandler){
        this.foldersMap = foldersMap;
        this.filesHandler = filesHandler;
    }

    public void sendEmail(Email email){
        this.foldersMap.getFolder("sent").appendEmail(email);

        Queue<String> receivers = email.getReceivers();
        int size = receivers.size();
        for(int i=0; i < size; i++){
            String receiver = receivers.poll();
            String receiverInboxPath = "mailserver/Database/Users/"+receiver+"/inbox.json";
            this.sendToReceivers(receiverInboxPath, email);
        }
    }

    private void sendToReceivers(String receiverInboxPath, Email email){
        String receiverInboxJson = filesHandler.readFile(receiverInboxPath);
        HashMap<String, Email> receiverInboxEmails = JsonEmailConverter.getInstance().jsonToEmailMap(receiverInboxJson);
        receiverInboxEmails.put(email.getId(), email);
        filesHandler.writeFileFromMap(receiverInboxPath, receiverInboxEmails);
    }

    public boolean moveEmail(String emailId, String sourceFolderName, String distFolderName){
        if(!foldersMap.hasFolder(sourceFolderName)) return false;
        if(!foldersMap.hasFolder(distFolderName)) return false;

        Email emailToBeMoved = foldersMap.getFolder(sourceFolderName).deleteEmail(emailId);
        foldersMap.getFolder(distFolderName).appendEmail(emailToBeMoved);
        return true;
    }

    public void deleteEmail(String emailId, String folderName){
        moveEmail(emailId, folderName, "trash");
    }
}