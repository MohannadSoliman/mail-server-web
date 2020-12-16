package com.example.mailserver;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors


public class Test {
    public static void main(String[]args){
        Queue<String> receivers = new LinkedList<String>();
        Queue<String> attachments = new LinkedList<String>();
        JsonConverter jsonConverter = JsonConverter.getInstance();

        receivers.add("rec1@g");
        receivers.add("rec2@g");
        attachments.add("attach1");
        // public Email(String id, String sender, Queue<String> receivers, String title, String body, String time, Queue<String> attachments)
        Email email = new Email(null, "sender@g", receivers, "titletest", "bodytest", null, attachments);

        String jsonStr = jsonConverter.emailToJsonString(email);
        System.out.println(jsonStr);
        
        try{
            FileWriter writer = new FileWriter("mailserver\\src\\main\\java\\com\\example\\mailserver\\test.txt");
            writer.write(jsonStr);
            writer.close();
            System.out.println("Done!");
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
}
