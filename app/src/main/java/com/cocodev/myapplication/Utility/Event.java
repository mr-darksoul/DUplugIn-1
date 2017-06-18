package com.cocodev.myapplication.Utility;

/**
 * Created by Sudarshan on 18-06-2017.
 */

public class Event {
    private String UID;
    private String venue;
    private String time;
    private String Description;
    private String url;

    public Event(){}

    public Event(String venue, String time, String description, String url) {
        this.venue = venue;
        this.time = time;
        Description = description;
        this.url = url;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
