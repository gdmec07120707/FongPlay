package com.fong.play.data.bean.requestbean;

/**
 * Created by FANGDINGJIE
 * 2018/3/30.
 */

public class LoginRequestBean {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
