package com.servo.sql;

import android.media.Image;

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
    public String Username    = null;
    public String Email       = null;
    public String Password    = null;
    public Date   DOB         = null;
    public String Phone_NO    = null;
    public Image  Avatar      = null;
    public String Description = null;
    public int Following      = 0;
    public int Followers      = 0;

    public User(){};

    public User(String username, String email, String password,
                Date DOB, String phone_NO, Image avatar,
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
}
