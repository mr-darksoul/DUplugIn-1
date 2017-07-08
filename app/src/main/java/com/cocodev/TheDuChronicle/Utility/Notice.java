package com.cocodev.TheDuChronicle.Utility;

/**
 * Created by Sudarshan on 16-06-2017.
 */

public class Notice {

    private String UID;
    private String department;
    private long time;
    private long deadline;
    private String description;

    public Notice(){
        //default constructor
    }

    public Notice(String department, long time, long deadline, String description) {
        this.department = department;
        this.time = time;
        this.deadline = deadline;
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
