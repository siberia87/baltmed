package ru.baltclinic.lliepmah.models;

import java.io.Serializable;


public class News implements Serializable {

    int id;
    String title;
    long date;

    String descr;
    String full_descr;
    String image_url;

    String mTextDate;

    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getTextDate() {
        return mTextDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescr() {
        return descr;
    }

    public String getFullDescr() {
        return full_descr;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setTextDate(String textDate) {
        mTextDate = textDate;
    }
}
