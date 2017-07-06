package com.cocodev.TheDuChronicle.Utility;

import java.util.ArrayList;

/**
 * Created by manav on 7/3/17.
 */

public class Comment{
    private String articleUid;
    private ArrayList<String> likesUid;
    private ArrayList<String> disLikesUid;
    private int likeCount;
    private int disLikeCount;
    private long timestamp;
    private String comment;
    private String commentUid;
    public Comment(){}

    public Comment(String articleUid, ArrayList<String> likesUid, ArrayList<String> disLikesUid, int likeCount, int disLikeCount, long timestamp, String comment,String commentUid) {
        this.articleUid = articleUid;
        this.likesUid = likesUid;
        this.disLikesUid = disLikesUid;
        this.likeCount = likeCount;
        this.disLikeCount = disLikeCount;
        this.timestamp = timestamp;
        this.comment = comment;
        this.commentUid = commentUid;
    }

    public String getArticleUid() {
        return articleUid;
    }

    public void setArticleUid(String articleUid) {
        this.articleUid = articleUid;
    }

    public ArrayList<String> getLikesUid() {
        return likesUid;
    }

    public void setLikesUid(ArrayList<String> likesUid) {
        this.likesUid = likesUid;
    }

    public ArrayList<String> getDisLikesUid() {
        return disLikesUid;
    }

    public void setDisLikesUid(ArrayList<String> disLikesUid) {
        this.disLikesUid = disLikesUid;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDisLikeCount() {
        return disLikeCount;
    }

    public void setDisLikeCount(int disLikeCount) {
        this.disLikeCount = disLikeCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(String commentUid) {
        this.commentUid = commentUid;
    }
}
