package com.example.mailserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


public class JsonConverter {
    private static JsonConverter instance;

    private JsonConverter(){}

    public static JsonConverter getInstance(){
        if(instance == null) instance = new JsonConverter();
        return instance;
    }

    public String emailToJsonString(Email email){
        ObjectMapper mapper = new ObjectMapper();
        // REMEMBER THIS PART.. VERY IMPORTANT SO THAT BEFORE YOU READ FROM JSON
        // MAKE SURE TO ADD THE BRACKET AT THE BOTTOM
        String jsonStr = "";
        try {
            jsonStr += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(email);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        jsonStr += ",\n";
        return jsonStr;
    }

    public String ArrayOfEmailsToJson(Email[] emails){
        String jsonStr = "[\n";
        int len = emails.length;
        for(int i = 0; i < len - 1; i++){
            jsonStr += emailToJsonString(emails[i]) + ",\n";
        }
        jsonStr += emailToJsonString(emails[len -1]);
        jsonStr += "\n]";
        return jsonStr;
    }

    public Email[] JsonToEmail(String jsonStrArray){
        Gson gson = new Gson();
        return gson.fromJson(jsonStrArray, Email[].class);
    }
}
