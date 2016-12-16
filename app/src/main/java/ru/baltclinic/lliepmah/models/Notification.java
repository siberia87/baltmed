package ru.baltclinic.lliepmah.models;

import android.os.Bundle;


public class Notification {

    private static final String KEY_ID = "id";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATE = "date";

    int id;
    String message;
    long date;

    private String mTextDate;

    public Notification(Bundle bundle) {
        this.id = bundle.getInt(KEY_ID);
        this.message = bundle.getString(KEY_MESSAGE);
        this.date = bundle.getLong(KEY_DATE);
    }

    public Notification(int id, String message, long date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;

    }

    public String getTextDate() {
        return mTextDate;
    }

    public void setTextDate(String textDate) {
        this.mTextDate = textDate;
    }

}
