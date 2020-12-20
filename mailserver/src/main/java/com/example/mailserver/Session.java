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

    public Integer addUser(Integer key, User user){
        this.presentUsers.put(key, user);
        return this.counter++;
    }
    public void removeUser(Integer key){
        this.presentUsers.remove(key);
    }
    public HashMap<Integer, User> getPresentUsers(){
        return this.presentUsers;
    }
    public User getUser(Integer key){
        return this.presentUsers.get(key);
    }
}
