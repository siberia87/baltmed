package ru.baltclinic.lliepmah.models;


public class Action {

    int id;
    String title;
    String descr;
    long from_date;
    long to_date;
    String subtitle;
    String image_url;
    String preview_url;

    String mFromDate;
    String mToDate;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescr() {
        return descr;
    }

    public String getFromDateString() {
        return mFromDate;
    }

    public String getToDateString() {
        return mToDate;
    }

    public long getFromDate() {
        return from_date;
    }

    public long getToDate() {
        return to_date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageUrl() {
        return image_url;
    }

    public String getPreviewUrl() {
        return preview_url;
    }

    public void setToDateText(String toDateText) {
        this.mToDate = toDateText;
    }

    public void setFromDateText(String fromDateText) {
        this.mFromDate = fromDateText;
    }
}
