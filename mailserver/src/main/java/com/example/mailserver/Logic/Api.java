package com.example.mailserver.Logic;

import com.example.mailserver.Logic.Proxy.SignIn;
import com.example.mailserver.Logic.Proxy.SignUp;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
public class Api {
    @GetMapping("/signIn")
    public String verifySignIn(@RequestParam String emailAddress, @RequestParam String password){
        //"false" if new user, userId if current user
        return  SignIn.getInstance().signInUser(emailAddress, password);
    }

    @GetMapping("/signUp")
    public String verifySignUp(@RequestParam String emailAddress, String password) {
        //false if current user, id if new user
        return SignUp.getInstance().signUpUser(emailAddress, password);
    }
    
    @GetMapping("/getEmailsList")
    public String getEmailsList(int userId, String folderName, int sortType, int sortIdntifier, int start){
        Email[] emailsList =  Session.getInstance().getUser(userId).getEmailsList(folderName, sortType, sortIdntifier, start);
        return JsonEmailConverter.getInstance().arrayOfEmailsToJson(emailsList);
    }
}
