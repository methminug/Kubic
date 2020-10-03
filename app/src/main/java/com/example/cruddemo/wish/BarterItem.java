package com.example.cruddemo.wish;

public class BarterItem {

    //TODO Add userName
    private String  name, description, category, offeredBy, iImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return offeredBy;
    }

    public void setUserName(String userName) {
        this.offeredBy = userName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getiImage() {
        return iImage;
    }

    public void setiImage(String iImage) {
        this.iImage = iImage;
    }

}
