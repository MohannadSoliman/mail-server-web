package com.example.mailserver;

import java.util.HashMap;

public class Session {
    private static Session instance;

    private Session(){}

    public static Session getInstance(){
        if(instance == null) instance = new Session();
        return instance;
    }

    private Integer counter = 0;
    private HashMap<Integer, User> presentUsers = new HashMap<Integer, User>();

    public Integer addUser(Integer number, User user){
        this.presentUsers.put(number, user);
        return this.counter++;
    }
    public void removeUser(Integer number){
        this.presentUsers.remove(number);
    }
    public HashMap<Integer, User> getPresentUsers(){
        return this.presentUsers;
    }
    public User getUser(Integer number){
        return this.presentUsers.get(number);
    }
}
