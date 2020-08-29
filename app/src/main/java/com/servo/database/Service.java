package com.servo.database;

/**
 * <h1>Service</h1>
 * <p>
 *  A structure defined
 *  for all service objects
 *  in this application.
 * </p>
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-28
 */

public class Service {

    /**
     * All properties
     * in a service
     */
    private int ID;
    private String title;
    private String description;
    private String tags;
    private int AssignerID;
    private int workerID;
    private int stateID;

    public Service(){};


    public Service(String title, String description, String tags, int assignerID, int workerID, int stateid, int ID) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        AssignerID = assignerID;
        this.workerID = workerID;
        this.stateID = stateid;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getAssignerID() {
        return AssignerID;
    }

    public void setAssignerID(int assignerID) {
        AssignerID = assignerID;
    }

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateid) {
        this.stateID = stateid;
    }
}
