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
        
    }
}
