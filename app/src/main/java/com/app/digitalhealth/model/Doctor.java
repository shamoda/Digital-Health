package com.app.digitalhealth.model;

public class Doctor {

    private String name;
    private String phone;
    private String address;
    private String nic;
    private String image;
    private String specialization;
    private String slmcRegNo;

    public Doctor() {
    }

    public Doctor(String name, String phone, String address, String nic, String image, String specialization, String slmcRegNo) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.nic = nic;
        this.image = image;
        this.specialization = specialization;
        this.slmcRegNo = slmcRegNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSlmcRegNo() {
        return slmcRegNo;
    }

    public void setSlmcRegNo(String slmcRegNo) {
        this.slmcRegNo = slmcRegNo;
    }
}
