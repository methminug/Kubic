package com.example.cruddemo.wish;

public class DisplayItem {
        private String title;
        private String description;
        private String category;
        private String image;

    public DisplayItem(String title, String description, String category, String image) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
