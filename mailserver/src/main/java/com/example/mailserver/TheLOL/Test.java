package com.example.mailserver.TheLOL;

import java.util.LinkedList;
import java.util.Queue;

public class Test {
    public static void main(String args[]){
        Queue<String> receivers = new LinkedList<String>();
        Queue<String> attachments = new LinkedList<String>();
        receivers.add("testREC@g");

//        Email email = new Email(null, "test@g", receivers, "titleLOL", "bodyLOL", null, attachments);
        User user = new User("test@g");

//        user.sendEmail(email);
        user.moveEmail("2dd9d200-08bc-49f6-a75a-98e1bc573459", "trash", "draft");
    }
}
