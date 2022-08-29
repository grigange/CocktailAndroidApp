package com.example.cocktailandroidapp;

public class CocktailModelClass {
    String id;
    String title;
    String img;
    String desc;

    public CocktailModelClass(String id, String title, String img, String desc) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.desc = desc;
    }

    public CocktailModelClass() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
