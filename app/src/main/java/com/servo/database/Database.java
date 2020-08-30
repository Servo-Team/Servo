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

    /**
     * Inserts an object
     * which is a record into
     * the specified database
     * @param obj object to be inserted
     * @throws Exception
     */
    public abstract void insertObj(Object obj) throws Exception;

    /**
     * Gets the specific record
     * with that ID which is
     * unique
     * @param ID ID of that record
     * @return returns the unique record
     * @throws Exception
     */
    public abstract Object getObj(int ID) throws Exception;

    /**
     * Gets all the records
     * off the specified database
     * @return returns all records
     * @throws Exception
     */
    public abstract List<Object> getObjs() throws Exception;


    /**
     * Updates the targeted
     * object given from
     * the ID
     * @param ID ID of object
     * @return the newly updated object
     * @throws Exception
     */
    public abstract Object updateObj(int ID) throws Exception;

    /**
     * General connection method
     * implemented in the base class
     * can be overriden in the subclasses
     * @return returns the Connection object
     *         after successfull connection
     *         or NULL otherwise
     * @throws Exception
     */
    public Connection connect() throws Exception {
        Connection connection = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName(Constants.DRIVER_NET).newInstance();
            String ConnURL= "jdbc:jtds:sqlserver://" + Constants.IP_ADDR +":"+Constants.PORT_NO+";"
                    + "databaseName=" + Constants.DB_NAME + ";";
            connection = DriverManager.getConnection(ConnURL,Constants.DB_USERNAME,Constants.DB_PASSWORD);

        } catch(Exception e){
            Log.e("SERVO:CONNECTION_SQL", e.getMessage());
        }

        return connection;
    }
}
