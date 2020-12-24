package com.example.mailserver.Logic.Proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import com.example.mailserver.Logic.JsonEmailConverter;
import com.example.mailserver.Logic.Session;
import com.example.mailserver.Logic.User;

public class SignUp {
  private static SignUp instance;

  private SignUp(){}

  public static SignUp getInstance(){
      if(instance == null) instance = new SignUp();
      return instance;
  }

    JsonEmailConverter jsonEmailConverter = JsonEmailConverter.getInstance();

    public Integer signUpUser(String emailAddress, String password){
      emailAddress = emailAddress.toLowerCase();
      UserInfo[] usersInfo = readUsersFile();
      if(SignIn.getInstance().signInUser(emailAddress, password) != null) return null;
      createUser(emailAddress, password, usersInfo);
      return Session.getInstance().addUserSession(new User(emailAddress));
    }

    //get all users in database
    public UserInfo[] readUsersFile(){
      String jsonStr = "";
      try {
          File myObj = new File("mailserver/Database/users.json");
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            jsonStr += myReader.nextLine();
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        return jsonEmailConverter.jsonToUserInfo(jsonStr);
    }

    //creation
    private void createUser(String emailAddress, String password,UserInfo[] usersInfo){
        try {

            Path path = Paths.get("mailserver/Database/Users/"+emailAddress);
        
            Files.createDirectories(path);
        
            } catch (IOException e) {
        
            System.err.println("Failed to create directory!" + e.getMessage());
        
            }
            try {

            Path pathAttachments = Paths.get("mailserver/Database/Users/"+emailAddress+"/Attachments");
        
            Files.createDirectories(pathAttachments);
        
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

            File contacts = new File("mailserver/Database/Users/"+emailAddress+"/contacts.json");
            try {
            contacts.createNewFile();
            } catch (IOException e) {
            e.printStackTrace();
            }
    
            File trash = new File("mailserver/Database/Users/"+emailAddress+"/trash.json");
            try {
            trash.createNewFile();
            } catch (IOException e) {
            
            e.printStackTrace();
            }

            File folders = new File("mailserver/Database/Users/"+emailAddress+"/folders.json");
            try {
            folders.createNewFile();
            } catch (IOException e) {
            e.printStackTrace();
            }
            UserInfo userInfo = new UserInfo(emailAddress, password);
            usersInfo = Arrays.copyOf(usersInfo, usersInfo.length + 1);
            usersInfo[usersInfo.length-1] = userInfo;
            String jsonStr = jsonEmailConverter.arrayOfUserInfoToJson(usersInfo);
            writeUsersFile(jsonStr);
    }
    private void writeUsersFile(String jsonStr){
      try {
        FileWriter myWriter = new FileWriter("mailserver/Database/users.json");
        myWriter.write(jsonStr);
        myWriter.close();
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
}
