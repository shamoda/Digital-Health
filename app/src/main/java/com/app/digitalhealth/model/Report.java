package com.app.digitalhealth.model;

public class Report {

    private String reportID;
    private String customerID;
    private String patientName;

    public Report() {
    }

    public Report(String reportID, String customerID, String patientName) {
        this.reportID = reportID;
        this.customerID = customerID;
        this.patientName = patientName;
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
}
