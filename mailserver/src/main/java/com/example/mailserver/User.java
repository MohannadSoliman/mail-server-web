package com.example.mailserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tomcat.util.http.fileupload.FileUtils;



public class User {


    public void createNewUser(String email_address) {
        
        try {

            Path path = Paths.get("mailserver/src/main/java/com/example/mailserver/Database/Users/"+email_address);
        
            Files.createDirectories(path);
        
          } catch (IOException e) {
        
            System.err.println("Failed to create directory!" + e.getMessage());
        
          }
        
          File inbox = new File("mailserver/src/main/java/com/example/mailserver/Database/Users/"+email_address+"/inbox.json");
          try {
            inbox.createNewFile();
          } catch (IOException e) {
            e.printStackTrace();
          }

          File sent = new File("mailserver/src/main/java/com/example/mailserver/Database/Users/"+email_address+"/sent.json");
          try {
            sent.createNewFile();
          } catch (IOException e) {
            e.printStackTrace();
          }

          File draft = new File("mailserver/src/main/java/com/example/mailserver/Database/Users/"+email_address+"/draft.json");
          try {
            draft.createNewFile();
          } catch (IOException e) {
            e.printStackTrace();
          }

          File trash = new File("mailserver/src/main/java/com/example/mailserver/Database/Users/"+email_address+"/trash.json");
          try {
            trash.createNewFile();
          } catch (IOException e) {
            
            e.printStackTrace();
          }
    }

    public void deleteUser(String email_address){

      try {
        FileUtils.deleteDirectory(new File("mailserver/src/main/java/com/example/mailserver/Database/Users/" + email_address));
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    public void addCustomFile(String email_address , String fileName) {

      File file = new File("mailserver/src/main/java/com/example/mailserver/Database/Users/"+email_address+"/"+fileName+".json");
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }


    }

    public void deleteFile(String email_address , String fileName) {

      File file = new File("mailserver/src/main/java/com/example/mailserver/Database/Users/"+email_address+"/"+fileName+".json");
      file.delete();

    }




}