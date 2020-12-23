package com.example.mailserver.Logic.Proxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.mailserver.Logic.User;

public class SignUp {
    public Integer Signup(String emailAddress){
        if(exists(emailAddress)) return null;
        createUser(emailAddress);
        User user = new User(emailAddress);
        // Session.getInstance().addUser(emailAddress);
        // return Session.getInstance().getId();

        return null;
    }

    private boolean exists(String emailAddress){

        return false;
    }
    //creation
    private void createUser(String emailAddress){
        try {

            Path path = Paths.get("mailserver/Database/Users/"+emailAddress);
        
            Files.createDirectories(path);
        
            } catch (IOException e) {
        
            System.err.println("Failed to create directory!" + e.getMessage());
        
            }
        
            File inbox = new File("mailserver/Database/Users/"+emailAddress+"/inbox.json");
            try {
            inbox.createNewFile();
            } catch (IOException e) {
            e.printStackTrace();
            }
    
            File sent = new File("mailserver/Database/Users/"+emailAddress+"/sent.json");
            try {
            sent.createNewFile();
            } catch (IOException e) {
            e.printStackTrace();
            }
    
            File draft = new File("mailserver/Database/Users/"+emailAddress+"/draft.json");
            try {
            draft.createNewFile();
            } catch (IOException e) {
            e.printStackTrace();
            }
    
            File trash = new File("mailserver/Database/Users/"+emailAddress+"/trash.json");
            try {
            trash.createNewFile();
            } catch (IOException e) {
            
            e.printStackTrace();
            }
    }
}
