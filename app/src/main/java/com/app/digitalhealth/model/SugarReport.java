package com.app.digitalhealth.model;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class SugarReport {

    private String ReportID;
    private String customerID;
    private String patientName;
    private String glucoseLevel;
    private String currentDate;


    public SugarReport() {
    }

    public SugarReport(String reportID, String customerID, String patientName, String glucoseLevel,String currentDate) {
        ReportID = reportID;
        this.customerID = customerID;
        this.patientName = patientName;
        this.glucoseLevel = glucoseLevel;
        this.currentDate = currentDate;
    }

    public String getReportID() {
        return ReportID;
    }

    public String getcustomerID() {
        return customerID;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getGlucoseLevel() {
        return glucoseLevel;
    }

    public String getCurrentDate() {
        return currentDate;
    }
}
