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
    

    public String searchFile(String sentence, String fileName){
        String[] searchArray = sentence.split(" ");
        Email[] allEmails =  foldersMap.getFolder(fileName).getAllEmailsArray();

        if(allEmails.length == 0) return "[]";
        
        String requiredEmails = "[";

        for(String word : searchArray) requiredEmails += meetCriteria(word, allEmails);

        requiredEmails = requiredEmails.substring(0, requiredEmails.length()-1) + "]";
        return requiredEmails;
    }

    public String searchAllFiles(String sentence){
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
            resultingEmails = searchFile(sentence, file).substring(1, resultingEmails.length()-1);
            if(resultingEmails.equals("")) continue;
            requiredEmails += resultingEmails + ",";
        }
        requiredEmails = requiredEmails.substring(0, requiredEmails.length()-1) + "]";
        return requiredEmails;
    }

    @Override
    public String meetCriteria(String required, Email[] emails) {
        String requiredEmails = "";
        for(Email email : emails){
            String[] emailTitle = email.getTitle().split(" ");
            String[] emailBody = email.getBody().split(" ");

            boolean found = false;
            for(int j=0; j<emailTitle.length; j++){
                if(required.equalsIgnoreCase(emailTitle[j])){
                    requiredEmails += jsonEmailConverter.emailToJsonString(email) + ",";
                    found = true;
                    break;
                }
            }
            if(found) continue;
            for(int k=0; k<emailBody.length; k++){
                if(required.equalsIgnoreCase(emailBody[k])){
                    requiredEmails += jsonEmailConverter.emailToJsonString(email) + ",";
                    break;
                }
            }
        }
        return requiredEmails;
    }
}
