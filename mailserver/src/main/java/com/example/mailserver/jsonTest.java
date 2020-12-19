package com.example.mailserver;

// import java.io.FileWriter;
// import java.io.IOException;
import com.google.gson.*;
import java.util.LinkedList;
import java.util.Queue;

public class jsonTest {
    public static void main(String[] args) {
        Queue<String> receivers = new LinkedList<String>();
        Queue<String> attachments = new LinkedList<String>();
        JsonConverter jsonConverter = JsonConverter.getInstance();

        receivers.add("rec1@g");
        receivers.add("rec2@g");
        attachments.add("attach1");
        // public Email(String id, String sender, Queue<String> receivers, String title, String body, String time, Queue<String> attachments)
        Email email1 = new Email(null, "sender@g", receivers, "titletest", "bodytest", null, attachments);
        Email email2 = new Email(null, "sender@g", receivers, "titletest", "bodytest", null, attachments);

        String email1JsonStr = jsonConverter.emailToJsonString(email1);
        String email2JsonStr = jsonConverter.emailToJsonString(email2);

        //not that i added [ here and at the end too
        String jsonArray = "[\n" + email1JsonStr + email2JsonStr.substring(0, email2JsonStr.length()-2) + "\n]";
        System.out.println(jsonArray);

        Gson gson = new Gson();

        //Email[] emails = gson.fromJson(jsonArray, Email[].class);

        // for(Email email : emails) {
        //     System.out.println(email.getId());
        //     System.out.println(email.getReceivers());
        //     System.out.println("\n");
        // }
    }
}
