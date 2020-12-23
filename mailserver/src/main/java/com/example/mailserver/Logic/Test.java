package com.example.mailserver.Logic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.example.mailserver.Logic.Proxy.SignIn;
import com.example.mailserver.Logic.Proxy.SignUp;

public class Test {
    public static void main(String args[]) {
        Queue<String> receivers = new LinkedList<String>();
        Queue<String> attachments = new LinkedList<String>();
        receivers.add("testREC@g");

         Email email = new Email(null, "test@g", receivers, "titleLOLtimeTest", "bodyLOL", null, attachments, "Urgent");

        // user.moveEmail("713efe52-3540-434d-9d4c-57020591c53a", "sent", "testFolder");
        // user.sendEmail(email);
        // user.deleteEmail("7bc7bb58-99bb-4db2-b464-80be7a52afe1", "sent");
        // try {
        //     user.createNewCustomFolder("testFolder2");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // try {
        //     user.renameCustomFolder("testFolder2", "testFolder2NEW");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // user.deleteCustomFolder("testFolder2NEW");
        // String help = "[]";
        // System.out.println(help.substring(1, help.length()-1));
        //System.out.println(user.searchFile("sender", "draft", "subject"));
        //user.sendEmail(email);
        //System.out.println(user.searchAllFiles("test", "sender"));
        // System.out.println(signUp.signUpUser("sign2314@g", "pass3442@g"));
        // System.out.println(signUp.signUpUser("sign2263@g", "pass3362@g"));
        SignUp signUp = SignUp.getInstance();
        signUp.signUpUser("sign23@g", "pass32@g");
        Session session = Session.getInstance();
        SignIn signIn = SignIn.getInstance();
        int userId = signIn.signInUser("sign23@g", "pass32@g");
        session.getAllSessions().get(userId).sendEmail(email);
        
    }
}