package com.example.mailserver.TheLOL.Filter;

import com.example.mailserver.TheLOL.Folders.*;
import com.example.mailserver.TheLOL.operationsHandlers.FolderHandler;

import java.util.ArrayList;

import com.example.mailserver.TheLOL.Email;
import com.example.mailserver.TheLOL.JsonEmailConverter;


public class Search implements Criteria{
    private FoldersMap foldersMap;
    private FolderHandler folderHandler;
    private JsonEmailConverter jsonEmailConverter = JsonEmailConverter.getInstance();

    public Search(FoldersMap foldersMap, FolderHandler folderHandler){
        this.foldersMap = foldersMap;
        this.folderHandler = folderHandler;
    }
    

    public String searchFile(String sentence, String fileName, String criteria){
        String[] searchArray = sentence.split(" ");
        Email[] allEmails = foldersMap.getFolder(fileName).getAllEmailsArray();
        //
        System.out.println(allEmails.length);
        //
        if(allEmails.length == 0) return "[]";
        
        String requiredEmails = "[";

        for(String word : searchArray) requiredEmails += meetCriteria(word, allEmails, criteria);

        requiredEmails = requiredEmails.substring(0, requiredEmails.length()-1) + "]";
        if(requiredEmails.equals("]")) return "[]";
        return requiredEmails;
    }

    public String searchAllFiles(String sentence, String criteria){
        String requiredEmails = "[";
        String resultingEmails = "";
        ArrayList<String> files = new ArrayList<String>();
        String[] customFiles = folderHandler.getExistingCustomFolderNames();

        files.add("inbox");
        files.add("sent");
        files.add("draft");
        files.add("trash");

        for(String word : customFiles) files.add(word);


        for(String file : files){
            resultingEmails = searchFile(sentence, file, criteria).substring(1, resultingEmails.length()-1);
            //
            System.out.println("resulting email: " + resultingEmails);
            //
            if(resultingEmails.equals("")) continue;
            requiredEmails += resultingEmails + ",";
        }
        requiredEmails = requiredEmails.substring(0, requiredEmails.length()-1) + "]";
        return requiredEmails;
    }

    @Override
    public String meetCriteria(String required, Email[] emails, String criteria) {
        required = required.toLowerCase();
        String requiredEmails = "";
        String emailTitle = "";
        String emailBody = "";
        String emailSender = "";
        String[] emailReceivers = null;
        for(Email email : emails){

            if(criteria.equalsIgnoreCase("all") || criteria.equalsIgnoreCase("body")) emailBody = email.getBody();
            if(criteria.equalsIgnoreCase("all") || criteria.equalsIgnoreCase("subject")) emailTitle = email.getTitle();
            if(criteria.equalsIgnoreCase("all") || criteria.equalsIgnoreCase("sender")) emailSender = email.getSender();
            if(criteria.equalsIgnoreCase("all") || criteria.equalsIgnoreCase("receiver")) emailReceivers = email.getReceivers().toArray(new String[email.getReceivers().size()]);
            
            //
            System.out.println("meetCriteria");
            System.out.println(emailTitle + ", " + emailBody);
            //
            
            if((criteria.equalsIgnoreCase("subject") || criteria.equalsIgnoreCase("all")) && emailTitle.contains(required)){
                requiredEmails += jsonEmailConverter.emailToJsonString(email) + ",";
                //
                System.out.println("sub");
                //
                continue;
            }
            if((criteria.equalsIgnoreCase("body") || criteria.equalsIgnoreCase("all")) && emailBody.contains(required)){
                requiredEmails += jsonEmailConverter.emailToJsonString(email) + ",";
                //
                System.out.println("bod");
                //
                continue;
            }
            if((criteria.equalsIgnoreCase("sender") || criteria.equalsIgnoreCase("all")) && emailSender.contains(required)){
                requiredEmails += jsonEmailConverter.emailToJsonString(email) + ",";
                //
                System.out.println("sen");
                //
                continue;
            }
            if(criteria.equalsIgnoreCase("receiver") || criteria.equalsIgnoreCase("all")){
                for(String receiver : emailReceivers){
                    if(required.equalsIgnoreCase(receiver)){
                        requiredEmails += jsonEmailConverter.emailToJsonString(email) + ",";
                        continue;
                    }
                }
            }

        }
        return requiredEmails;
    }
}
