package com.example.mailserver.TheLOL.Filter;


import com.example.mailserver.TheLOL.Email;

public interface Criteria {
    public String meetCriteria(String required, Email[] emails, String criteria);
}
