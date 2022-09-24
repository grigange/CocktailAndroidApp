package com.example.cocktailandroidapp;

public class NoteInfo {
    private String comment;
    private String card_id;
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


    public String getCardId() {
        return card_id;
    }

    public void setCardId(String card_id) {
        this.card_id = card_id;
    }
    public NoteInfo(String card_id , String comment) {
        this.card_id = card_id;
        this.comment = comment;
    }

}
