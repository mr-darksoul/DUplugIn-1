package com.cocodev.university.delhi.duplugin.Utility;

/**
 * Created by Sudarshan on 16-06-2017.
 */

public class Notice {

    private String uid;
    //private String department;
    private long time;
    private long deadline;
    private String description;
    private String title;
    public Notice(){
        //default constructor
    }

//    public Notice(String department, long time, long deadline, String description) {
//       //this.department = department;
//        this.time = time;
//        this.deadline = deadline;
//        this.description = description;
//    }
    public Notice(String title,String department, long time, long deadline, String description) {
        //this.department = department;
        this.title = title;
        this.time = time;
        this.deadline = deadline;
        this.description = description;
    }

//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
