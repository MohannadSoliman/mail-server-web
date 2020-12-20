package com.example.mailserver;

import java.util.Queue;


public class Operations {
    private static Operations instance;
    Utilities util = new Utilities();

    private Operations(){}

    public static Operations getInstance(){
        if(instance == null) instance = new Operations();
        return instance;
    }
    JsonConverter jsonConverter = JsonConverter.getInstance();
    public void sendEmail(String email_address, String emailString){
        
        Email email = jsonConverter.JsonToEmail(emailString)[0];
        String sent = util.readFile("mailserver/Database/Users/"+ email_address +"/sent.json");
        Email[]emailsFromSent = jsonConverter.JsonToEmail(sent);
        Email[]newSent = new Email[emailsFromSent.length+1];
        for(int i=0; i<emailsFromSent.length; i++) newSent[i] = emailsFromSent[i];
        newSent[newSent.length-1] = email;
        util.writeFile("mailserver/Database/Users/"+ email_address + "/sent.json", newSent);

        Queue<String> receivers = email.getReceivers();
        int size = receivers.size();
        for(int i=0; i<size; i++){
            String receiver = receivers.poll();
            String inbox = util.readFile("mailserver/Database/Users/"+receiver+"/inbox.json");

            Email[]emailsFromInbox = jsonConverter.JsonToEmail(inbox);
            Email[]newInbox = new Email[emailsFromInbox.length+1];

            for(int j=0; j<emailsFromInbox.length; j++) newInbox[j] = emailsFromInbox[j];

            newInbox[newInbox.length-1] = email;
            util.writeFile("mailserver/Database/Users/"+receiver+"/inbox.json", newInbox);
        }
    }
}
