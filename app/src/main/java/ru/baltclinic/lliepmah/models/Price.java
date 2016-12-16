package ru.baltclinic.lliepmah.models;


public class Price {

    int id;
    int service_id;
    String title;
    int value;

    public int getId() {
        return id;
    }

    public int getServiceId() {
        return service_id;
    }

    public String getTitle() {
        return title;
    }

    public int getValue() {
        return value;
    }
}
