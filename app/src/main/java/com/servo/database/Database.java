package com.servo.database;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.servo.utils.Constants;

/**
 * <h1>Database</h1>
 * <p>
 *  Base class for
 *  all incoming databases.
 *
 *  All the databases will be able
 *  to connect to a database. Other
 *  functions are mostly dependant
 *  on the type of the database
 * </p>
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-24
 * @see     UserDatabase
 */
public abstract class Database {

    public abstract void insertObj(Object obj) throws Exception;
    public abstract List<Object> getObjs() throws Exception;
    public abstract Object getObj(int ID) throws Exception;

    public Connection connect() throws Exception {
        Connection connection = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
//            connection = DriverManager.getConnection(String.format("jdbc:jtds:sqlserver://%s:%s;databaseName=%s;", Constants.IP_ADDR,
//                                                                                                                   Constants.PORT_NO,
//                                                                                                                   Constants.DB_NAME),
//                                                                                                                   Constants.DB_USERNAME,
//                                                                                                                   Constants.DB_PASSWORD);
            String ConnURL;
            ConnURL = "jdbc:jtds:sqlserver://" + Constants.IP_ADDR +":"+Constants.PORT_NO+";"
                    + "databaseName=" + Constants.DB_NAME + ";";
            connection = DriverManager.getConnection(ConnURL,Constants.DB_USERNAME,Constants.DB_PASSWORD);

        } catch(Exception e){
            Log.e("SERVO:CONNECTION_SQL", e.getMessage());
        }

        return connection;
//        Connection connection = null;
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
//        String ConnURL;
//        ConnURL = "jdbc:jtds:sqlserver://" + Constants.IP_ADDR +":"+Constants.PORT_NO+";"
//                + "databaseName=" + Constants.DB_NAME + ";";
//        connection = DriverManager.getConnection(ConnURL,Constants.DB_USERNAME,Constants.DB_PASSWORD);
//
//        return connection;
    }
}
