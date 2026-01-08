package com.healthcare.model;

public class Patient {
    private String patientID;
    private String firstName;
    private String lastName;
    private String dob;
    private String nhsNumber;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String postcode;
    private String emergencyName;
    private String emergencyPhone;
    private String regDate;
    private String gpID;

    public Patient(String patientID, String firstName, String lastName, String dob, String nhsNumber, 
                   String gender, String phone, String email, String address, String postcode, 
                   String emergencyName, String emergencyPhone, String regDate, String gpID) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.nhsNumber = nhsNumber;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.emergencyName = emergencyName;
        this.emergencyPhone = emergencyPhone;
        this.regDate = regDate;
        this.gpID = gpID;
    }

    // Getters
    public String getPatientID() { return patientID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return lastName + ", " + firstName; }
    public String getDob() { return dob; }
    public String getNhsNumber() { return nhsNumber; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPostcode() { return postcode; }
    public String getEmergencyName() { return emergencyName; }
    public String getEmergencyPhone() { return emergencyPhone; }
    public String getRegDate() { return regDate; }
    public String getGpID() { return gpID; }

    // Setters (For Editing)
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDob(String dob) { this.dob = dob; }
    public void setNhsNumber(String nhsNumber) { this.nhsNumber = nhsNumber; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    public void setEmergencyName(String emergencyName) { this.emergencyName = emergencyName; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }

    @Override
    public String toString() { return patientID + " - " + firstName + " " + lastName; }

    public String toCSV() {
        // Matches patients.csv structure
        return String.join(",", 
            patientID, firstName, lastName, dob, nhsNumber, gender, phone, email, 
            "\"" + address + "\"", postcode, emergencyName, emergencyPhone, regDate, gpID
        );
    }
}