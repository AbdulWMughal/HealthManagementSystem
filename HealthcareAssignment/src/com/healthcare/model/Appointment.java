package com.healthcare.model;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String clinicianID;
    private String date;
    private String time;
    private String type;
    private String status;
    private String notes;

    public Appointment(String appointmentID, String patientID, String clinicianID, String date, String time, String type, String status, String notes) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.date = date;
        this.time = time;
        this.type = type;
        this.status = status;
        this.notes = notes;
    }

    // Getters
    public String getAppointmentID() { return appointmentID; }
    public String getPatientID() { return patientID; }
    public String getClinicianID() { return clinicianID; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }

    public String toCSV() {
        return String.join(",", appointmentID, patientID, clinicianID, "S001", date, time, "15", type, status, "Reason", "\"" + notes + "\"", date, date);
    }
}