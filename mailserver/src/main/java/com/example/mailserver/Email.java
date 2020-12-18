package com.example.mailserver;

import java.util.Queue;
// import java.util.LinkedList;
import java.util.UUID;

import java.time.LocalDateTime;


public class Email{
    private String sender;
    private Queue<String> receivers;
    private String title;
    private String body;
    private String id;
    private String time;
    private Queue<String> attachments;

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