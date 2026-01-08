package com.healthcare.model;

public class Clinician {
    private String clinicianID;
    private String firstName;
    private String lastName;
    private String title;
    private String specialty;
    private String gmcNumber;
    private String phone;
    private String email;
    private String workplaceID;
    private String workplaceType;
    private String empStatus;
    private String startDate;

    public Clinician(String clinicianID, String firstName, String lastName, String title, String specialty, 
                     String gmcNumber, String phone, String email, String workplaceID, 
                     String workplaceType, String empStatus, String startDate) {
        this.clinicianID = clinicianID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.specialty = specialty;
        this.gmcNumber = gmcNumber;
        this.phone = phone;
        this.email = email;
        this.workplaceID = workplaceID;
        this.workplaceType = workplaceType;
        this.empStatus = empStatus;
        this.startDate = startDate;
    }

    // Getters
    public String getClinicianID() { return clinicianID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return title + " " + firstName + " " + lastName; } // e.g. Dr. John Smith
    public String getTitle() { return title; }
    public String getSpecialty() { return specialty; }
    public String getGmcNumber() { return gmcNumber; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getWorkplaceID() { return workplaceID; }
    public String getWorkplaceType() { return workplaceType; }
    public String getEmpStatus() { return empStatus; }
    public String getStartDate() { return startDate; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setTitle(String title) { this.title = title; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public void setGmcNumber(String gmcNumber) { this.gmcNumber = gmcNumber; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setWorkplaceID(String workplaceID) { this.workplaceID = workplaceID; }
    public void setWorkplaceType(String workplaceType) { this.workplaceType = workplaceType; }
    public void setEmpStatus(String empStatus) { this.empStatus = empStatus; }

    @Override
    public String toString() { return clinicianID + " - " + getFullName(); }

    public String toCSV() {
        return String.join(",", 
            clinicianID, firstName, lastName, title, specialty, gmcNumber, phone, email, 
            workplaceID, workplaceType, empStatus, startDate
        );
    }
}