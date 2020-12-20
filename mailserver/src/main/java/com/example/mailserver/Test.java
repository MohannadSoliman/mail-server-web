package com.example.mailserver;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors


public class Test {
    public static void main(String[]args){
        Queue<String> receivers = new LinkedList<String>();
        Queue<String> attachments = new LinkedList<String>();
        JsonConverter jsonConverter = JsonConverter.getInstance();

        receivers.add("testREC@g");
        attachments.add("attach1");
        // // public Email(String id, String sender, Queue<String> receivers, String title, String body, String time, Queue<String> attachments)
        Email email = new Email(null, "test@g", receivers, "titletest", "bodytest", null, attachments);
        //User user = new User();
        // user.createNewUser("test@g");
        // String jsonStr = jsonConverter.emailToJsonString(email);
        // //System.out.println(jsonStr);
        
        // try{
        //     FileWriter writer = new FileWriter("mailserver\\src\\main\\java\\com\\example\\mailserver\\Database\\Users\\test@g\\inbox.json", true);
        //     writer.write(jsonStr);
        //     writer.close();
        //     System.out.println("Done!");

        // }catch(IOException e){
        //     e.printStackTrace();
        // }
        // String emailsJson = "";
        // Utilities util = new Utilities();
        // emailsJson = util.readFile("mailserver/Database/Users/test@g/inbox.json");
        // System.out.println(emailsJson);
        // Gson gson = new Gson();
        // Email[] emails = gson.fromJson(emailsJson, Email[].class);

        // for(Email email : emails) {
        //     System.out.println(email.getId());
        //     System.out.println(email.getReceivers());
        //     System.out.println("\n");
        // }
        //Queue<String> hello = {"hi", "bye"};
        // {
        //     "sender" : "sender@g",
        //     "receivers" : [ "rec1@g", "rec2@g" ],
        //     "title" : "titletestDRAFT2",
        //     "body" : "bodytestD2",
        //     "id" : "60bc752b-5c7b-4cfb-8efc-5052944bbdb2",
        //     "time" : "2020-12-19T22:41:32.469346800",
        //     "attachments" : [ "attach1" ]
        // }
        // User user = new User();
        // user.createNewUser("testREC@g");
        // EmailOperations emailOp = EmailOperations.getInstance();
        // String emailString = jsonConverter.emailToJsonString(email);
        //System.out.println(emailString);
        // emailOp.sendEmail("test@g", emailString);
        FolderOperations folderOps = FolderOperations.getInstance();
        // folderOps.createFolder("mailserver/Database/Users/test@g/created.json");
        folderOps.deleteFolder("mailserver/Database/Users/test@g/created.json");
    }
}
