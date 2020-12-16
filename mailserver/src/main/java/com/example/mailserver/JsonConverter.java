package com.example.mailserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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
        String jsonStr = "{\n";
        try {
            jsonStr += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(email);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
