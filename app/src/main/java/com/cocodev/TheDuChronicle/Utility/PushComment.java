package com.cocodev.TheDuChronicle.Utility;

/**
 * Created by manav on 7/4/17.
 */

public class PushComment {
    String articleUID;
    String timeStamp;
    String value;
    String likesCount;
    String dislikeCount;

    public PushComment() {
    }

    public PushComment(String articleUID, String timeStamp, String value, String likes, String dislike) {
        this.articleUID = articleUID;
        this.timeStamp = timeStamp;
        this.value = value;
        this.likesCount = likes;
        this.dislikeCount = dislike;
    }

    public String getArticleUID() {
        return articleUID;
    }

    public void setArticleUID(String articleUID) {
        this.articleUID = articleUID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likes) {
        this.likesCount = likes;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(String dislike) {
        this.dislikeCount = dislike;
    }
}
