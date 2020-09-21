package com.app.digitalhealth.model;

public class Users {

    private String name;
    private String password;
    private String phone;
    private String profileImage;
    private String address;

    public Users() {
    }

    public Users(String name, String password, String phone, String profileImage, String address) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.profileImage = profileImage;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
