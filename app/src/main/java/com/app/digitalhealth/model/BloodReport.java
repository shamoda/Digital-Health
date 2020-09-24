package com.app.digitalhealth.model;

public class BloodReport {

    private String reportID;
    private String CustomerID;
    private String patientNAME;
    private String heam;
    private String pcv;
    private String rbc;
    private String lympho;
    private String mono;
    private String eoso;
    private String myel;
    private String band;
    private String blast;
    private String platelet;
    private String comment;


    public BloodReport() {
    }

    public BloodReport(String reportID,String customerID, String patientNAME, String heam, String pcv, String rbc,
                       String lympho, String mono, String eoso, String myel,
                       String band, String blast, String platelet, String comment) {
        this.reportID = reportID;
        this.CustomerID = customerID;
        this.patientNAME = patientNAME;
        this.heam = heam;
        this.pcv = pcv;
        this.rbc = rbc;
        this.lympho = lympho;
        this.mono = mono;
        this.eoso = eoso;
        this.myel = myel;
        this.band = band;
        this.blast = blast;
        this.platelet = platelet;
        this.comment = comment;
    }

    public String getReportID() {
        return reportID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public String getPatientNAME() {
        return patientNAME;
    }

    public String getHeam() {
        return heam;
    }

    public String getPcv() {
        return pcv;
    }

    public String getRbc() {
        return rbc;
    }

    public String getLympho() {
        return lympho;
    }

    public String getMono() {
        return mono;
    }

    public String getEoso() {
        return eoso;
    }

    public String getMyel() {
        return myel;
    }

    public String getBand() {
        return band;
    }

    public String getBlast() {
        return blast;
    }

    public String getPlatelet() {
        return platelet;
    }

    public String getComment() {
        return comment;
    }
}