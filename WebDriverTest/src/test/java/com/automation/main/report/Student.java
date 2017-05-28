package com.automation.main.report;


public class Student implements TegrityUser {

    String userName;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
