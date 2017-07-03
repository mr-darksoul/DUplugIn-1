package com.cocodev.TheDuChronicle.Utility;

/**
 * Created by manav on 7/3/17.
 */

public class Comment{
    private String name;
    private String review;

    public Comment() {
    }

    public Comment(String name, String review) {
        this.name = name;
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
