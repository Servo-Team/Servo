package com.servo.database;

import android.media.Image;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

/**
 * <h1>User</h1>
 * <p>
 *  A structure defined
 *  for all user objects
 *  in this application.
 * </p>
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-24
 */
public class User {
    /**
     * All the properties
     * of a user in Servo
     */
    private String Username             = null;
    private String Email                = null;
    private String Password             = null;
    private Date   DOB                  = null;
    private String Phone_NO             = null;
    private File Avatar      = null;
    private String Description          = null;
    private int Following               = 0;
    private int Followers               = 0;

    public User(){};

    public User(String username, String email, String password,
                Date DOB, String phone_NO, File avatar,
                String description, int following, int followers) {
        Username = username;
        Email = email;
        Password = password;
        this.DOB = DOB;
        Phone_NO = phone_NO;
        Avatar = avatar;
        Description = description;
        Following = following;
        Followers = followers;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public Date getDOB() {
        return DOB;
    }

    public String getPhone_NO() {
        return Phone_NO;
    }

    public File getAvatar() {
        return Avatar;
    }

    public String getDescription() {
        return Description;
    }

    public int getFollowing() {
        return Following;
    }

    public int getFollowers() {
        return Followers;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public void setPhone_NO(String phone_NO) {
        Phone_NO = phone_NO;
    }

    public void setAvatar(File avatar) {
        Avatar = avatar;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setFollowing(int following) {
        Following = following;
    }

    public void setFollowers(int followers) {
        Followers = followers;
    }
}
