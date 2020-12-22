package com.example.mailserver.TheLOL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.HashMap;
//change
public class JsonEmailConverter {
    private static JsonEmailConverter instance;

    private JsonEmailConverter(){}

    public static JsonEmailConverter getInstance(){
        if(instance == null) instance = new JsonEmailConverter();
        return instance;
    }

    public String emailToJsonString(Email email){
        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = "";
        try {
            jsonStr += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(email);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        jsonStr += "\n";
        return jsonStr;
    }

    public String arrayOfEmailsToJson(Email[] emails){
        String jsonStr = "[\n";
        int len = emails.length;
        for(int i = 0; i < len - 1; i++){
            jsonStr += emailToJsonString(emails[i]) + ",\n";
        }
        jsonStr += emailToJsonString(emails[len -1]);
        jsonStr += "\n]";
        return jsonStr;
    }

    public String mapOfEmailsToJson(HashMap<String, Email> emails){
        String jsonStr = "[\n";
        boolean first = true;
        for (Email email : emails.values()) {
            if(!first) jsonStr += ",\n";
            else first = false;

            jsonStr += emailToJsonString(email);
        }
        jsonStr += "\n]";
        return jsonStr;
    }

    public Email[] jsonToEmailArray(String jsonStrArray){
        Gson gson = new Gson();
        return gson.fromJson(jsonStrArray, Email[].class);
    }

    public Email JsonToEmail(String jsonStrArray){
        Gson gson = new Gson();
        return gson.fromJson(jsonStrArray, Email.class);
    }

    public HashMap<String, Email> jsonToEmailMap(String jsonStrArray){
        HashMap<String, Email> emailsMap = new HashMap<>();
        Email[] allEmails = jsonToEmailArray(jsonStrArray);
        if(allEmails != null){
            for(Email email: allEmails){
                emailsMap.put(email.getId(), email);
            }
        }
        return emailsMap;
    }

    public String[] jsonArrayToStringArray(String jsonStrArray){
        Gson gson = new Gson();
        return gson.fromJson(jsonStrArray, String[].class);
    }

    public String stringArrayToJson(String[] strArray){
        String jsonStr = "[";
        for(int i = 0; i < strArray.length -1; i++){
            jsonStr += "\""+ strArray[i] + "\",";
        }
        jsonStr += "\""+ strArray[strArray.length -1] + "\"]";
        return jsonStr;
    }
}