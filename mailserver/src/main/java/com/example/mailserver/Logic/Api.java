package com.example.mailserver.Logic;

import java.io.IOException;

import com.example.mailserver.Logic.Proxy.SignIn;
import com.example.mailserver.Logic.Proxy.SignUp;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
// @CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin
public class Api {
    // verfication
    @GetMapping("/signIn")
    public String verifySignIn(@RequestParam String emailAddress, @RequestParam String password) {
        // "false" if new user, userId if current user
        return SignIn.getInstance().signInUser(emailAddress, password);
    }

    @GetMapping("/signUp")
    public String verifySignUp(@RequestParam String emailAddress, String password) {
        // false if current user, id if new user
        return SignUp.getInstance().signUpUser(emailAddress, password);
    }

    // fetching emails
    @GetMapping("/getEmailsList")
    public String getEmailsList(int userId, String folderName, int sortType, int sortIdntifier, int start) {
        Email[] emailsList = Session.getInstance().getUser(userId).getEmailsList(folderName, sortType, sortIdntifier,
                start);
        return JsonEmailConverter.getInstance().arrayOfEmailsToJson(emailsList);
    }

    @GetMapping("/getAllCustomFolders")
    public String getCustomFolders(int userId) {
        String[] customFoldersNames = Session.getInstance().getUser(userId).getAllCustomFolders();
        return JsonEmailConverter.getInstance().stringArrayToJson(customFoldersNames);
    }

    // custom folders operations
    @GetMapping("/createNewCustomFolder")
    public boolean createNewCustomFolder(int userId, String folderName) throws IOException {
        return Session.getInstance().getUser(userId).createNewCustomFolder(folderName);
    }

    @GetMapping("/renameCustomFolder")
    public boolean renameCustomFolder(int userId, String oldFolderName, String newFolderName) throws IOException {
        return Session.getInstance().getUser(userId).renameCustomFolder(oldFolderName, newFolderName);
    }

    @DeleteMapping("/deleteCustomFolder")
    public void deleteCustomFolder(int userId, String folderName){
        Session.getInstance().getUser(userId).deleteCustomFolder(folderName);
    }
}
