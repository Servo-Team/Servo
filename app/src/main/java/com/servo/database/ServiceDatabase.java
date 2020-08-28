package com.servo.database;


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

    /*
    SELECT *
FROM SERVICES
WHERE WORKER=13 OR ASSIGNER=13
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
        service.setTitle(rs.getString("TITLE"));
        service.setDescription(rs.getString("DESCR"));
        service.setAssignerID(rs.getInt("ASSIGNER"));
        service.setWorkerID(rs.getInt("WORKER"));
        service.setTags(rs.getString("TAGS"));
        service.setStateID(rs.getInt("STATE_ID"));
        return service;
    }

}
