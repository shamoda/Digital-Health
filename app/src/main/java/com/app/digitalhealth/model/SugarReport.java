package com.app.digitalhealth.model;

import com.google.firebase.database.DatabaseReference;

public class SugarReport {

   String ReportID;
   String customerID;
   String patientName;
   String glucoseLevel;


    public SugarReport() {
    }

    public SugarReport(String reportID, String customerID, String patientName, String glucoseLevel) {
        this.ReportID = reportID;
        this.customerID = customerID;
        this.patientName = patientName;
        this.glucoseLevel = glucoseLevel;
    }

    public String getReportID() {
        return ReportID;
    }

    public void setReportID(String reportID) {
        ReportID = reportID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(String glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }
}
