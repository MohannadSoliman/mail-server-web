package com.example.mailserver.Logic.Folders;

import java.io.File;

import com.example.mailserver.Logic.Email;

//change
public class TrashFolder extends Folder {

    public TrashFolder(String fileName, boolean isImmutable, String userEmail) {
        super(fileName, isImmutable, userEmail);
        checkForOutDatedEmail();
    }

    private void checkForOutDatedEmail(){
        //some work :(
    }

    public void deleteForever(String emailId){
        Email deletedEmail = deleteEmail(emailId);
        String userEmail = getUserEmail();
        deleteRelatedAttachments(deletedEmail.getId(), userEmail);
    }

    private void deleteRelatedAttachments(String emailId, String emailAddress){
        File folder = new File("mailserver/Database/Users/" + emailAddress + "/Attachments/" + emailId);
        String files[] = folder.list();
        for(String file : files){
            File currentFile = new File(folder.getPath(), file);
            currentFile.delete();
        }
        folder.delete();
    }
}