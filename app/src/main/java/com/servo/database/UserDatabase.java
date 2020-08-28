package com.servo.database;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.servo.utils.Constants;
import com.servo.utils.Permission;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * <h1>UserDatabase</h1>
 * <p>
 *  Database meant specifically
 *  for users which extends off
 *  the base database class.
 * </p>
 *
 * <p>
 *  So far can insert a user
 *  and can get a user from
 *  a database
 * </p>
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-24
 * @see     Database
 */
public class UserDatabase extends Database{

    /**
     * Inserts a user into the database
     * as a record
     * @param obj : user to be added
     * @throws Exception
     */
    @Override
    public void insertObj(Object obj) throws Exception {
        User user = (User) obj;
        Connection connection = connect();

        StringBuffer dateFormatted = new StringBuffer();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        simpleDateFormat.format(user.getDOB(), dateFormatted, new FieldPosition(0));

        String insertQuery = "INSERT INTO USERS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        PreparedStatement statement = connection.prepareStatement(insertQuery);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());
        statement.setDate(4,   new java.sql.Date(user.getDOB().getTime()));
        statement.setString(5, user.getPhone_NO());
        statement.setBinaryStream(6, new FileInputStream(user.getAvatar()) , (int)user.getAvatar().length());
        statement.setString(7, user.getDescription());
        statement.setInt(8, user.getFollowing());
        statement.setInt(9, user.getFollowers());

        statement.execute();

        connection.close();

    }

    /**
     * Gets all users from the database
     * as a list of users.
     * @return the list of all users registered!
     * @throws Exception
     */
    @Override
    public List<Object> getObjs() throws Exception {

        List<Object> users = new ArrayList();
        Connection connection = connect();

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM USERS");

        while(rs.next()){
            User user = new User();
            user.setUsername(rs.getString("USERNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setEmail(rs.getString("EMAIL"));
            user.setDOB(rs.getDate("DOB"));
            user.setPhone_NO(rs.getString("PHONE_NO"));


            user.setDescription(rs.getString("DESCR"));
            user.setFollowing(rs.getInt("FOLLOWING_NO"));
            user.setFollowers(rs.getInt("FOLLOWERS_NO"));

            users.add(user);
        }

        connection.close();
        return users;
    }

    /**
     * Gets the specific user with the
     * specified ID
     * @param ID the ID of the user
     * @return returns the user with the ID
     * @throws Exception
     */
    @Override
    public Object getObj(int ID) throws Exception {
        User user = new User();
        Connection connection = connect();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM USERS WHERE ID = %d;",ID));

        if(rs.next()){
            user.setUsername(rs.getString("USERNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setEmail(rs.getString("EMAIL"));
            user.setDOB(rs.getDate("DOB"));
            user.setPhone_NO(rs.getString("PHONE_NO"));


            user.setDescription(rs.getString("DESCR"));
            user.setFollowing(rs.getInt("FOLLOWING_NO"));
            user.setFollowers(rs.getInt("FOLLOWERS_NO"));
        }

        connection.close();
        return user;
    }

    /**
     * Gets the ID of the user
     * with the given username
     * and password
     * @param username Username of user
     * @param password Password of user
     * @return  returns the ID of the user
     * @throws Exception
     */
    public int getIDUsername(String username, String password) throws Exception{
        User user = new User();
        Connection connection = connect();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM USERS WHERE USERNAME = '%s' AND   PASSWORD = '%s';",
                                                             username,
                                                             password));

        while(rs.next()){
            return rs.getInt("ID");
        }

        return -1;
    }

    /**
     * Gets the ID of the user
     * with the given email
     * and password
     * @param email    Email of user
     * @param password Password of user
     * @return  returns the ID of the user
     * @throws Exception
     */
    public int getIDEmail(String email, String password) throws Exception{
        User user = new User();
        Connection connection = connect();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM USERS WHERE EMAIL = '%s' AND   PASSWORD = '%s';",
                email,
                password));

        while(rs.next()){
            return rs.getInt("ID");
        }

        return -1;
    }

    public int isUsernameUnique(String username) throws Exception{
        User user = new User();
        Connection connection = connect();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM USERS WHERE USERNAME = '%s';", username));

        String other = null;
        while(rs.next()){
            other = rs.getString("USERNAME");
        }
        return (other!=null  && username.equals(other))?Constants.ERROR:Constants.SUCCESS;
    }

    public File getAvatar(String username, Activity act) throws Exception{
        User user = new User();
        Connection connection = connect();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(String.format("SELECT AVATAR FROM USERS WHERE USERNAME='%s';", username));

        byte[] fileBytes;
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/profilepage.jpg");
        if(!f.exists()) {
            Permission.verifyStoragePermissions(act);
            f.createNewFile();
        }
        FileOutputStream img = new FileOutputStream(f);
        if(rs.next()){
            fileBytes = rs.getBytes(1);
            img.write(fileBytes);
            img.close();
        }

        return f;
    }


}
