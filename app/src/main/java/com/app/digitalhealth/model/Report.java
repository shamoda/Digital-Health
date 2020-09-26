package com.app.digitalhealth.model;

public class Report {

    private String reportID;
    private String customerID;
    private String patientName;
    private String currentDate;

    public Report() {
    }

    public Report(String reportID, String customerID, String patientName, String currentDate) {
        this.reportID = reportID;
        this.customerID = customerID;
        this.patientName = patientName;
        this.currentDate = currentDate;
    }

    public String getReportID() {
        return reportID;
    }

    public String getcustomerID() {
        return customerID;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getCurrentDate() {
        return currentDate;
    }
}
