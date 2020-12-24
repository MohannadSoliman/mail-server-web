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
    public Integer verifySignIn(@RequestParam String emailAddress, @RequestParam String password){
        //null if new user, userId if current user
        return SignIn.getInstance().signInUser(emailAddress, password);
    }

    @GetMapping("/signUp")
    public Integer verifySignUp(@RequestParam String emailAddress, String password) {
        //null if current user, id if new user
        return SignUp.getInstance().signUpUser(emailAddress, password);
    }
    
}
