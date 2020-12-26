package com.example.mailserver.Logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

import com.example.mailserver.Logic.Folders.TrashFolder;
import com.example.mailserver.Logic.Proxy.SignIn;
import com.example.mailserver.Logic.Proxy.SignUp;

public class Test {
    public static void main(String args[]) {
        // Queue<String> receivers = new LinkedList<String>();
        // Queue<String> attachments = new LinkedList<String>();
        // receivers.add("testREC@g");

        String result = SignUp.getInstance().signUpUser("asasdAAasda@lol.com", "111AA1alitest");
        System.out.println(result);

    }
}