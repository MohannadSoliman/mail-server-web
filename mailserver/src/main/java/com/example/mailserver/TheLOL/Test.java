package com.example.mailserver.TheLOL;

import java.util.LinkedList;
import java.util.Queue;

public class Test {
    public static void main(String args[]){
        Queue<String> receivers = new LinkedList<String>();
        Queue<String> attachments = new LinkedList<String>();
        receivers.add("testREC@g");

       Email email = new Email(null, "test@g", receivers, "titleLOLtimeTest", "bodyLOL", null, attachments);
        User user = new User("test@g");

        user.sendEmail(email);
        // user.deleteEmail("7bc7bb58-99bb-4db2-b464-80be7a52afe1", "sent");
    }
}