package com.fong.play.data.bean;

/**
 * Created by FANGDINGJIE
 * 2018/3/30.
 */

public class LoginBean extends BaseEntity {

    private User user;

    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
