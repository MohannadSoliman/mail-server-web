package com.example.mailserver.TheLOL.Folders;

import com.example.mailserver.TheLOL.Folders.Folder;

public class TrashFolder extends Folder {

    public TrashFolder(String fileName, boolean isImmutable, String userEmail) {
        super(fileName, isImmutable, userEmail);
        checkForOutDatedEmail();
    }

    private void checkForOutDatedEmail(){
        //some work :(
    }
}
