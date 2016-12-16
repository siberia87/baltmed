package ru.baltclinic.lliepmah.models;

import java.io.Serializable;


public class Call implements Serializable {

    String name;
    String phone;

    public Call(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
