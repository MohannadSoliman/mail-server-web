package com.example.mailserver.Logic.Folders;

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
        Email deletedEmial = deleteEmail(emailId);
        String userEmail = getUserEmail();
        deleteRelatedAttachments(deletedEmial.getId(), userEmail);
    }

    private void deleteRelatedAttachments(String emailId, String emailAddress){
        //some logic to delete it
    }
}