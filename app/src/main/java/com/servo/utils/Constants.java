package com.servo.utils;


import com.servo.auth.R;

/**
 * <h1>Constants</h1>
 * <p>
 *  Used as a local storage of all
 *  important constants used in this
 *  application.
 *  Simmilar to 'applications.properties'
 * </p>
 *
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-24
 */
public final class Constants {

    private Constants(){};

    /**
     * Database CONSTANTS
     */
    public static final String IP_ADDR = "192.168.1.197";
    public static final String PORT_NO = "1433";
    public static final String DRIVER_NET = "net.sourceforge.jtds.jdbc.Driver";
    public static final String DB_NAME = "Servo";
    public static final String DB_USERNAME = "aman";
    public static final String DB_PASSWORD = "root";
    public static final String DB_URL = "jdbc:jtds:sqlserver://"+IP_ADDR+":"+PORT_NO+"/"+DB_NAME;


    /**
     * Method ERROR CODES
     */
    public static final int SUCCESS = 100;
    public static final int ERROR   = 50;

    public static String[] profile_list_title = {
            "My Profile",
            "Messages",
            "Bookmarks",
            "Moments",
            "Settings"
    };

    public static int[] profile_list_image = {
            R.drawable.ic_person,
            R.drawable.ic_message,
            R.drawable.ic_black_bookmark,
            R.drawable.ic_moments_black,
            R.drawable.ic_settings_black
    };


    public static String[] image_urls_profile = {
            "https://cdn2.iconfinder.com/data/icons/people-80/96/Picture1-512.png",
            "https://cdn0.iconfinder.com/data/icons/google-material-design-3-0/48/ic_chat_48px-512.png",
            "https://www.materialui.co/materialIcons/action/bookmark_black_192x192.png",
            "https://i.ibb.co/9hMD0d8/moment-removebg-preview.png",
            "https://www.materialui.co/materialIcons/action/settings_black_192x192.png"
    };

    public static String[] image_options = {
            "https://i.imgur.com/Xy9oA9F.png",
            "https://i.imgur.com/YdG8UEm.png",
            "https://i.imgur.com/Cdad8hc.png",
            "https://i.imgur.com/yO7hepD.png",
            "https://i.imgur.com/bliEn4L.png",
            "https://i.imgur.com/U7ecrIL.png"
    };

}
