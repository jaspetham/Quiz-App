package com.example.jaspe.fypquiz.Model;

public class User_Info {
    //private username,password,email so that it is not public for other class
    private String userName;
    private String pwd;
    private String email;

    public User_Info() {

    }

    public User_Info(String username, String pwd, String email) {
        this.userName = username;
        this.pwd = pwd;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

