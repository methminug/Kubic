package com.example.cruddemo.wish;

public class Wish {
    private String wishName;
    private String wishDesc;
    private String wishCategory;
    private String wishOwner;

    public Wish() {
    }

    public String getWishOwner() {
        return wishOwner;
    }

    public void setWishOwner(String wishOwner) {
        this.wishOwner = wishOwner;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public String getWishDesc() {
        return wishDesc;
    }

    public void setWishDesc(String wishDesc) {
        this.wishDesc = wishDesc;
    }

    public String getWishCategory() {
        return wishCategory;
    }

    public void setWishCategory(String wishCategory) {
        this.wishCategory = wishCategory;
    }
}

