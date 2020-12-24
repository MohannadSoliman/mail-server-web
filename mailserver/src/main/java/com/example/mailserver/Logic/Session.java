package com.example.mailserver.Logic;

import java.util.HashMap;


public class Session {
    private static Session instance;
    private static HashMap<Integer, User> sessions = new HashMap<Integer, User>();
    private Integer userId = 0;

    private Session(){}

    public static Session getInstance(){
        if(instance == null) instance = new Session();
        return instance;
    }
    
    public Integer addUserSession(User user){
        sessions.put(userId, user);
        return userId++;
    }
    public void deleteUserSession(Integer id){
        sessions.remove(id);
    }
    public HashMap<Integer, User> getAllSessions(){
        return sessions;
    }
    public User getUser(Integer key){
        return sessions.get(key);
    }
}
