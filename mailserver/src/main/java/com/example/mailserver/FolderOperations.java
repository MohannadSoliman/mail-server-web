package com.example.mailserver;

import java.io.File;
import java.io.IOException;

public class FolderOperations {
   
    private static FolderOperations instance;

    private FolderOperations(){}

    public static FolderOperations getInstance(){
        if(instance == null) instance = new FolderOperations();
        return instance;
    }

    public void createFolder(String path){
        File file = new File(path);
        try {
          file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
  
    }
    public void deleteFolder(String path){
        File file = new File(path);
        file.delete();
    }
    // WIP
    public void editFolder(){

    }
}
