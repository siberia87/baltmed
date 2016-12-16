package ru.baltclinic.lliepmah.models;


public class Service implements Ordered {
    int id;
    String title;
    String descr;
    String image_url;
    int order;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescr() {
        return descr;
    }

    public String getImageUrl() {
        return image_url;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public boolean hasString(String searchText) {
        searchText = searchText.toLowerCase();

        if (title != null && title.toLowerCase().contains(searchText)) {
            return true;
        }
        return false;
    }

}
