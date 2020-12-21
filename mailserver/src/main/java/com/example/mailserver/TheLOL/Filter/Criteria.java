package com.example.mailserver.TheLOL.Filter;

import java.util.ArrayList;

import com.example.mailserver.TheLOL.Email;

public interface Criteria {
    public String meetCriteria(String required, Email[] emails);
}
