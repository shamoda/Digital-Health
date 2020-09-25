package com.app.digitalhealth.model;

import com.google.firebase.database.DatabaseReference;

public class SugarReport {

    private String ReportID;
    private String customerID;
    private String patientName;
    private String glucoseLevel;


    public SugarReport() {
    }

    public SugarReport(String reportID, String customerID, String patientName, String glucoseLevel) {
        ReportID = reportID;
        this.customerID = customerID;
        this.patientName = patientName;
        this.glucoseLevel = glucoseLevel;
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
}
