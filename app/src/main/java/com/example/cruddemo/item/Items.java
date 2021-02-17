package com.example.cruddemo.item;

public class Items {
    private String id;
    private String name;
    private String category;
    private String description;
    private String exchangeFor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExchangeFor() {
        return exchangeFor;
    }

    public void setExchangeFor(String exchangeFor) {
        this.exchangeFor = exchangeFor;
    }

    public String getiImage() {
        return iImage;
    }

    public void setiImage(String iImage) {
        this.iImage = iImage;
    }

    public String getOfferedBy() {
        return offeredBy;
    }

    public void setOfferedBy(String offeredBy) {
        this.offeredBy = offeredBy;
    }

    private String iImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String offeredBy;

    public Items(){

    }

    public Items(String name, String category, String description, String exchangeFor, String iImage, String offeredBy){
        this.name = name;
        this.category = category;
        this.description = description;
        this.exchangeFor = exchangeFor;
        this.iImage = iImage;
        this.offeredBy = offeredBy;
    }

    public Items(String name){
        this.name = name;
    }

}
