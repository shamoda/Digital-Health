package com.app.digitalhealth.model;

public class Sessions {

    private String session_Id;
    private String date;
    private String time;
    private String cusID;
    private String noOfPatients;


    public Sessions() {
    }


    public Sessions(String session_Id, String date, String time, String cusID,String patientCount) {
        this.session_Id = session_Id;
        this.date = date;
        this.time = time;
        this.cusID = cusID;
        this.noOfPatients = patientCount;
    }

    public String getSession_Id() {
        return session_Id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCusID() {
        return cusID;
    }

    public String getNoOfPatients() {
        return noOfPatients;
    }
}
