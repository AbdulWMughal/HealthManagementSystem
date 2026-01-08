package com.healthcare.model;

public class Prescription {
    private String prescriptionID;
    private String patientID;
    private String clinicianID;
    private String date;
    private String medication;
    private String dosage;
    private String frequency;
    private String duration; // in days
    private String pharmacy;
    private String status;

    public Prescription(String prescriptionID, String patientID, String clinicianID, String date, 
                        String medication, String dosage, String frequency, String duration, 
                        String pharmacy, String status) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.date = date;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.pharmacy = pharmacy;
        this.status = status;
    }

    // Getters
    public String getPrescriptionID() { return prescriptionID; }
    public String getPatientID() { return patientID; }
    public String getClinicianID() { return clinicianID; }
    public String getDate() { return date; }
    public String getMedication() { return medication; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public String getDuration() { return duration; }
    public String getPharmacy() { return pharmacy; }
    public String getStatus() { return status; }

    public String toCSV() {
        // ID, Patient, Doc, ApptID(empty), Date, Med, Dose, Freq, Dur, Qty(calc), Instr(generic), Pharm, Status...
        return String.join(",", 
            prescriptionID, patientID, clinicianID, "", date, 
            medication, dosage, frequency, duration, duration + " units", 
            "Take as directed", "\"" + pharmacy + "\"", status, date, ""
        );
    }
}