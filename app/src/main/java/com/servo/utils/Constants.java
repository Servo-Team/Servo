package com.servo.utils;

import java.sql.Connection;

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
public class Constants {

    private Constants(){};

    /**
     * Database CONSTANTS
     */
    public static final String IP_ADDR = "192.168.1.197";
    public static final String PORT_NO = "1433";
    public static final String DRIVER_NET = "net.sourceforge.jtds.jdbc.Driver";
    public static final String DB_NAME = "androidTestDB2";
    public static final String DB_USERNAME = "aman";
    public static final String DB_PASSWORD = "root";
    public static final String DB_URL = "jdbc:jtds:sqlserver://"+IP_ADDR+":"+PORT_NO+"/"+DB_NAME;

}
