package com.healthcare.model;

public class Referral {
    private String referralID;
    private String patientID;
    private String referringDocID;
    private String targetDocID;
    private String refFacilityID;
    private String targetFacilityID;
    private String date;
    private String urgency;
    private String reason;
    private String summary;
    private String status;
    private String notes;

    public Referral(String referralID, String patientID, String referringDocID, String targetDocID,
                    String refFacilityID, String targetFacilityID, String date, String urgency,
                    String reason, String summary, String status, String notes) {
        this.referralID = referralID;
        this.patientID = patientID;
        this.referringDocID = referringDocID;
        this.targetDocID = targetDocID;
        this.refFacilityID = refFacilityID;
        this.targetFacilityID = targetFacilityID;
        this.date = date;
        this.urgency = urgency;
        this.reason = reason;
        this.summary = summary;
        this.status = status;
        this.notes = notes;
    }

    // Getters
    public String getReferralID() { return referralID; }
    public String getPatientID() { return patientID; }
    public String getReferringDocID() { return referringDocID; }
    public String getDate() { return date; }
    public String getUrgency() { return urgency; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public String getSummary() { return summary; }

    public String toCSV() {
        // ID, Pat, RefDoc, TarDoc, RefFac, TarFac, Date, Urg, Reason, Summary, Investigations(empty), Status, Appt(empty), Notes, Created, Updated
        return String.join(",",
            referralID, patientID, referringDocID, targetDocID, refFacilityID, targetFacilityID,
            date, urgency, reason, "\"" + summary + "\"", "", status, "", "\"" + notes + "\"", date, date
        );
    }
}