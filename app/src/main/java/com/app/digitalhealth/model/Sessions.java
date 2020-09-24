package com.app.digitalhealth.model;

public class Sessions {

    private String session_Id;
    private String date;
    private String time;
    private String cusID;
    private String noOfPatients;
    private String specialization;
    private String name;


    public Sessions() {
    }

    public Sessions(String session_Id, String date, String time, String cusID, String noOfPatients, String specialization, String name) {
        this.session_Id = session_Id;
        this.date = date;
        this.time = time;
        this.cusID = cusID;
        this.noOfPatients = noOfPatients;
        this.specialization = specialization;
        this.name = name;
    }

    public String getSession_Id() {
        return session_Id;
    }

    public void setSession_Id(String session_Id) {
        this.session_Id = session_Id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getNoOfPatients() {
        return noOfPatients;
    }

    public void setNoOfPatients(String noOfPatients) {
        this.noOfPatients = noOfPatients;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
