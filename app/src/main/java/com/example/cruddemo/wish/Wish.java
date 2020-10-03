package com.example.cruddemo.wish;

import android.os.Parcel;
import android.os.Parcelable;

public class Wish implements Parcelable {
    private String wishName;
    private String wishDesc;
    private String wishCategory;
    private String wishOwner;
    private String imageURL = "null";
    private String wishKey;

    public Wish() {
    }

    protected Wish(Parcel in) {
        wishName = in.readString();
        wishDesc = in.readString();
        wishCategory = in.readString();
        wishOwner = in.readString();
        imageURL = in.readString();
        wishKey = in.readString();
    }

    public static final Creator<Wish> CREATOR = new Creator<Wish>() {
        @Override
        public Wish createFromParcel(Parcel in) {
            return new Wish(in);
        }

        @Override
        public Wish[] newArray(int size) {
            return new Wish[size];
        }
    };

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getWishKey() {
        return wishKey;
    }

    public void setWishKey(String wishKey) {
        this.wishKey = wishKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wishName);
        parcel.writeString(wishDesc);
        parcel.writeString(wishCategory);
        parcel.writeString(wishOwner);
        parcel.writeString(imageURL);
        parcel.writeString(wishKey);
    }
}

