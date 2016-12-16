package ru.baltclinic.lliepmah.models;

import java.io.Serializable;


public class Appointment implements Serializable {

    String date_text;
    String time_text;
    String last_name;
    String first_name;
    String middle_name;
    String email;
    String phone;
    String comment;

    public Appointment(String date_text, String time_text, String last_name, String first_name, String middle_name, String email, String phone, String comment) {
        this.date_text = date_text;
        this.time_text = time_text;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.email = email;
        this.phone = phone;
        this.comment = comment;
    }


    public String getDate() {
        return date_text;
    }

    public String getTime() {
        return time_text;
    }

    public String getLastName() {
        return last_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getMiddleName() {
        return middle_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getComment() {
        return comment;
    }
}
