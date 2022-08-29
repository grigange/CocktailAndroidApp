package com.example.cocktailandroidapp;

public class CommentsInfo {
    private String comment;
    private int id;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public CommentsInfo(String comment) {
        this.comment = comment;
    }

}
