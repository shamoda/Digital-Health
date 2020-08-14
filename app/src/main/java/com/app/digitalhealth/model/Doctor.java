package com.app.digitalhealth.model;

public class Doctor {

    private String name, Specialization, image;

    public Doctor() {
    }

    public Doctor(String name, String specialization, String image) {
        this.name = name;
        Specialization = specialization;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
