package com.example.mailserver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
