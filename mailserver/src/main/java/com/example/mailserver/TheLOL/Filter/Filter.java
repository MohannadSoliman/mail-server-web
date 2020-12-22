package com.example.mailserver.TheLOL.Filter;

import com.example.mailserver.TheLOL.Email;
import com.example.mailserver.TheLOL.JsonEmailConverter;
import com.example.mailserver.TheLOL.Folders.*;

public class Filter implements Criteria {

    private FoldersMap foldersMap;
    private JsonEmailConverter jsonEmailConverter = JsonEmailConverter.getInstance();

    public Filter(FoldersMap foldersMap){
        this.foldersMap = foldersMap;
    }

    public String filterFile(String priority, String fileName, String criteria){
        Email[] allEmails =  foldersMap.getFolder(fileName).getAllEmailsArray();
        if(allEmails.length == 0) return "[]";
        String requiredEmails = meetCriteria(priority, allEmails, criteria);
        if(requiredEmails.equals("]")) return "[]";
        return requiredEmails;
    }

    @Override
    public String meetCriteria(String required, Email[] emails, String criteria) {
        String requiredEmails = "[";
        for(Email email : emails){
            if(required.equalsIgnoreCase(email.getPriority())){
                requiredEmails += jsonEmailConverter.emailToJsonString(email) + ",";
            }
        }
        requiredEmails = requiredEmails.substring(0, requiredEmails.length()-1) + "]";
        return requiredEmails;
    }
    
}