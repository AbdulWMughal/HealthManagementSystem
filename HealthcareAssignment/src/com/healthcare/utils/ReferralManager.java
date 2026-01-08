package com.healthcare.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ReferralManager {
    private static ReferralManager instance;
    private static final String REFERRAL_FILE = "referral_output.txt";

    private ReferralManager() {}

    public static synchronized ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    public void createReferral(String patientID, String patientName, String email, String doctorID, String doctorName, String specialty, String urgency, String notes) {
        String timestamp = LocalDateTime.now().toString();
        
        String patientDisplay = String.format("%s (%s)", patientName, patientID);
        String doctorDisplay = String.format("%s (%s)", doctorName, doctorID);

        String referralRecord = String.format(
            "REFERRAL ID: REF-%d\nDate: %s\nPatient: %s\nPatient Email: %s\nReferring Dr: %s\nTarget Specialty: %s\nUrgency: %s\nNotes: %s\n---------------------------------------------------",
            System.currentTimeMillis(), timestamp, patientDisplay, email, doctorDisplay, specialty, urgency, notes
        );

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REFERRAL_FILE, true))) {
            bw.write(referralRecord);
            bw.newLine();
            System.out.println("Referral generated.");
        } catch (IOException e) {
            System.err.println("Failed to write referral: " + e.getMessage());
        }
    }
}