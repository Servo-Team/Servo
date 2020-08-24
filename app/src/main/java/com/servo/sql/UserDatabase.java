package com.servo.sql;

import android.widget.EditText;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Override
    protected void insertUser(User user) throws SQLException {
        Connection connection = connect();
        try{

            Statement statement = connection.createStatement();
            String insertStr = String.format("INSERT INTO Celebrity VALUES ('%s', 1, 2)", name);
            statement.executeUpdate(insertStr);
            connection.commit();

        } catch(Exception err){
            connection.rollback();
            connection.close();

            err.getStackTrace();
        }
        connection.close();
    }

    @Override
    protected List<User> getUser() {
        return null;
    }
}
