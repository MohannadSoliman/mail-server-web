package com.example.mailserver;

import java.util.Queue;
import java.util.LinkedList;
import java.util.UUID;

import java.time.LocalDateTime;


public class Email{
    private String sender = new String();
    private Queue<String> receivers = new LinkedList<String>();
    private String title = new String();
    private String body = new String();
    private String id = new String();
    private String time = new String();
    private Queue<String> attachments = new LinkedList<String>();

    public Email(String id, String sender, Queue<String> receivers, String title, String body, String time, Queue<String> attachments){
        this.id = id;
        this.sender = sender;
        this.receivers = receivers;
        this.title = title;
        this.body = body;
        this.time = time;
        this.attachments = attachments;

        if(id == null) this.id = UUID.randomUUID().toString();
        if(time == null) this.time = String.valueOf(LocalDateTime.now());
    }

    public String getSender() {
        return sender;
    }
    
    public Queue<String> getReceivers() {
        return receivers;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getId() {
        return id;
    }
    
    public String getTime() {
        return time;
    }

    public Queue<String> getAttachments() {
        return attachments;
    }
}