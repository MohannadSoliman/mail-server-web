package com.example.mailserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class Utilities {
    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            saveFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveFile(MultipartFile  file) throws Exception{
        String folder = "mailserver/Database/Users";
        byte[] bytes = file.getBytes();
        Path path = Paths.get(folder, file.getOriginalFilename());
        Files.write(path, bytes);
    }

    public String readFile(String path){
        String emailsInJson = "";
        try{
            File myObj = new File(path);
            Scanner reader = new Scanner(myObj);
            while(reader.hasNextLine()) emailsInJson += reader.nextLine();
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return emailsInJson;
    }
    public void writeFile(String path, Email emails[]){
        JsonConverter jsonConverter = JsonConverter.getInstance();
        String emailsString = jsonConverter.ArrayOfEmailsToJson(emails);
        try{
            FileWriter writer = new FileWriter(path);
            writer.write(emailsString);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
