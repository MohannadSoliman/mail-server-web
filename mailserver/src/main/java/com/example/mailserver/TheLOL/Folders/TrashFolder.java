package com.example.mailserver.TheLOL.Folders;
//change
public class TrashFolder extends Folder {

    public TrashFolder(String fileName, boolean isImmutable, String userEmail) {
        super(fileName, isImmutable, userEmail);
        checkForOutDatedEmail();
    }

    private void checkForOutDatedEmail(){
        //some work :(
    }
}