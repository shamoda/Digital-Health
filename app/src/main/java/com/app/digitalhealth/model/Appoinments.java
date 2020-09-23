package com.app.digitalhealth.model;

public class Appoinments {

    private String Name;
    private String Email;
    private String Phone;
    private String Note;
    private String id;
    private String Gender;
    private String session;
    private String doctor;
    private String Date;

    public Appoinments() {
    }

    public Appoinments(String name, String email, String phone, String note, String id, String gender, String session, String doctor, String date) {
        Name = name;
        Email = email;
        Phone = phone;
        Note = note;
        this.id = id;
        Gender = gender;
        this.session = session;
        this.doctor = doctor;
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getNote() {
        return Note;
    }

    public String getId() {
        return id;
    }

    public String getGender() {
        return Gender;
    }

    public String getSession() {
        return session;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getDate() {
        return Date;
    }
}
