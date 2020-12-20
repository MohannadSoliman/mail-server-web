package com.example.mailserver;

import java.util.Arrays;
import java.util.Queue;

// Filtering and sorting still remain !!
public class EmailOperations {
    private static EmailOperations instance;
    Utilities util = new Utilities();

    private EmailOperations(){}

    public static EmailOperations getInstance(){
        if(instance == null) instance = new EmailOperations();
        return instance;
    }
    JsonConverter jsonConverter = JsonConverter.getInstance();
    
    public void sendEmail(String emailAddress, String emailString){

        Email email = jsonConverter.JsonToEmail(emailString);
        this.addEmail("mailserver/Database/Users/"+emailAddress+"/sent.json", email);

        Queue<String> receivers = email.getReceivers();
        int size = receivers.size();
        for(int i=0; i<size; i++){
            String receiver = receivers.poll();
            this.addEmail("mailserver/Database/Users/"+receiver+"/inbox.json", email);
        }

    }
    private void addEmail(String path, Email email){
        String fileEmails = util.readFile(path);
        Email[] emailsOld = jsonConverter.JsonToEmailArray(fileEmails);
        Email[] emailsNew = null;
        if(emailsOld == null){
            emailsNew = new Email[1];
        }else{
            emailsNew = Arrays.copyOf(emailsOld, emailsOld.length+1);
        }
        emailsNew[emailsNew.length - 1] = email;
        util.writeFile(path, emailsNew);
    }
}
