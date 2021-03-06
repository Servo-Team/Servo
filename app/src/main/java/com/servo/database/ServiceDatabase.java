package com.servo.database;


import com.servo.utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceDatabase extends Database {
    /**
     * Inserts an object
     * which is a record into
     * the specified database
     *
     * @param obj object to be inserted
     * @throws Exception
     */
    @Override
    public void insertObj(Object obj) throws Exception {
        Service service = (Service)obj;
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO SERVICES VALUES (?,?,?,?,?,?)");
        statement.setString(1, service.getTitle());
        statement.setString(2, service.getDescription());
        statement.setInt(3, service.getAssignerID());
        statement.setInt(4, service.getWorkerID());
        statement.setString(5, service.getTags());
        statement.setInt(6, service.getStateID());
        statement.execute();
        connection.close();

    }

    /**
     * Gets all the records
     * off the specified database
     *
     * @return returns all records
     * @throws Exception
     */
    @Override
    public List<Object> getObjs() throws Exception {
        List<Object> services = new ArrayList();
        Connection connection = connect();

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM SERVICES;");

        while(rs.next()){
            services.add(inner_service(rs));
        }

        connection.close();
        return services;
    }

    /**
     * Updates the targeted
     * object given from
     * the ID
     *
     * @param ID ID of object
     * @return the newly updated object
     * @throws Exception
     * @deprecated USE updateObj(ID,WorkerID)
     */
    @Deprecated
    @Override
    public Object updateObj(int ID) throws Exception {
        throw new UnsupportedOperationException("USE updateObj(ID,WorkerID)");
    }

    /**
     * Updates the targeted
     * object given from
     * the ID and the WorkerID
     * who will work on the service
     * @param ID ID of object
     * @throws Exception
     */
    public void updateObj(int ID, int WorkerID) throws Exception {
        Service service = new Service();
        Connection connection = connect();


        PreparedStatement statement = connection.prepareStatement("UPDATE SERVICES SET WORKER = ?, STATE_ID = 2 WHERE ID = ?");
        statement.setInt(1, WorkerID);
        statement.setInt(2,ID);
        statement.execute();
        connection.close();

    }

    public boolean isFinished(int ID) throws Exception{
        Service service = new Service();
        Connection connection = connect();

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(String.format("SELECT STATE_ID FROM SERVICES WHERE ID = %d;",ID));

        if(rs.next()){
            return (rs.getInt("STATE_ID") == Constants.SERVICE_FINISHED);
        }
        return false;
    }

    public void setFinishedOrUnfinished(int ID, int status) throws Exception{
        Service service = new Service();
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement("UPDATE SERVICES SET STATE_ID = ? WHERE ID = ?");
        statement.setInt(2, ID);


        if(status == Constants.SERVICE_FINISHED){
            statement.setInt(1,Constants.SERVICE_FINISHED);
        } else{
            statement.setInt(1,Constants.SERVICE_PENDING);
        }

        statement.execute();
        connection.close();
    }

    /**
     * Gets the specific record
     * with that ID which is
     * unique
     *
     * @param ID ID of that record
     * @return returns the unique record
     * @throws Exception
     */
    @Override
    public Object getObj(int ID) throws Exception {
        Service service = new Service();
        Connection connection = connect();

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM SERVICES WHERE ID = %d;",ID));

        if(rs.next()){
            service = inner_service(rs);
        }

        connection.close();
        return service;
    }


    /**
     * Gives services related to
     * the user
     * @param ID ID Of user
     * @return all services related
     * @throws Exception
     */
    public List<Object> servicesRelated(int ID) throws Exception{
        List<Object> services = new ArrayList();
        Connection connection = connect();

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM SERVICES WHERE WORKER = %d OR ASSIGNER = %d",ID,ID));

        while(rs.next()){
            services.add(inner_service(rs));
        }

        connection.close();
        return services;
    }





    private Service inner_service(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setID(rs.getInt("ID"));
        service.setTitle(rs.getString("TITLE"));
        service.setDescription(rs.getString("DESCR"));
        service.setAssignerID(rs.getInt("ASSIGNER"));
        service.setWorkerID(rs.getInt("WORKER"));
        service.setTags(rs.getString("TAGS"));
        service.setStateID(rs.getInt("STATE_ID"));
        return service;
    }

}
