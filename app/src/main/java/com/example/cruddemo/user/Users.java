package com.example.cruddemo.user;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
     private String Username;
     private String address;
     private String email;
     private String phone;
     private String Password;

    public Users() {
    }

    protected Users(Parcel in) {
        Username = in.readString();
        address = in.readString();
        email = in.readString();
        if (in.readByte() == 0) {
            phone = null;
        } else {
            phone = in.readString();
        }
        Password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Username);
        dest.writeString(address);
        dest.writeString(email);
        if (phone == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(phone);
        }
        dest.writeString(Password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
