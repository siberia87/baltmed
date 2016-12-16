package ru.baltclinic.lliepmah.models;


public class Doctor implements Ordered {

    int id;
    String first_name;
    String last_name;
    String middle_name;
    String photo_url;
    String descr;
    String first_position;
    String second_position;
    String third_position;
    int order;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getMiddleName() {
        return middle_name;
    }

    public String getPhotoUrl() {
        return photo_url;
    }

    public String getDescr() {
        return descr;
    }

    public String getFirstPosition() {
        return first_position;
    }

    public String getSecondPosition() {
        return second_position;
    }

    public String getThirdPosition() {
        return third_position;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public boolean hasString(String searchText) {

        searchText = searchText.toLowerCase();

        if (first_name != null && first_name.toLowerCase().contains(searchText)) {
            return true;
        }
        if (last_name != null && last_name.toLowerCase().contains(searchText)) {
            return true;
        }
        if (middle_name != null && middle_name.toLowerCase().contains(searchText)) {
            return true;
        }
        if (first_position != null && first_position.toLowerCase().contains(searchText)) {
            return true;
        }
        if (second_position != null && second_position.toLowerCase().contains(searchText)) {
            return true;
        }
        if (third_position != null && third_position.toLowerCase().contains(searchText)) {
            return true;
        }

//        if (descr != null && descr.toLowerCase().contains(searchText)) {
//            return true;
//        }

        return false;
    }
}