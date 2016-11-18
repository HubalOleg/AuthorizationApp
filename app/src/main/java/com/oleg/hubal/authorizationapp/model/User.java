package com.oleg.hubal.authorizationapp.model;

/**
 * Created by User on 17.11.2016.
 */

public class User {
    private String name;
    private String email;
    private String birthday;
    private String imageURL;

    public User() {

    }

    public User(String name, String email, String birthday, String imageURL) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
