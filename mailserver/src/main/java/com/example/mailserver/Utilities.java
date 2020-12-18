package com.example.mailserver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class Utilities {
    // WIP
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String returnValue = "success";
        try {
            saveFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            returnValue = "failed";
        }
        return returnValue;
    }
    public void saveFile(MultipartFile  file) throws Exception{
        String folder = "mailserver\\src\\main\\java\\com\\example\\mailserver\\Database\\Users\\";
        byte[] bytes = file.getBytes();
        Path path = Paths.get(folder, file.getOriginalFilename());
        Files.write(path, bytes);
    }
}
