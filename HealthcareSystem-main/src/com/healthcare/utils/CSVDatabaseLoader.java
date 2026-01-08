package com.healthcare.utils;

import com.healthcare.model.*;
import java.io.*;
import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;

public class CSVDatabaseLoader {
    private List<Patient> patients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<Clinician> clinicians = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>();
    private List<Referral> referrals = new ArrayList<>();
    
    private static final String PATIENT_FILE = "data/patients.csv";
    private static final String APPT_FILE = "data/appointments.csv";
    private static final String CLINICIAN_FILE = "data/clinicians.csv";
    private static final String RX_FILE = "data/prescriptions.csv";
    private static final String REF_FILE = "data/referrals.csv";

    public String[] getUniqueReferralReasons() {
        Set<String> reasons = referrals.stream()
                .map(Referral::getReason)
                .filter(r -> r != null && !r.isEmpty())
                .collect(Collectors.toSet());
        return reasons.toArray(new String[0]);
    }

    public void loadData() {
        loadPatients();
        loadAppointments();
        loadClinicians();
        loadPrescriptions();
        loadReferrals();
    }

    // --- Load Methods ---
    private void loadPatients() {
        patients.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_FILE))) {
            String line; br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if(data.length > 13) patients.add(new Patient(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8].replace("\"", ""), data[9], data[10], data[11], data[12], data[13]));
            }
        } catch (IOException e) {}
    }

    private void loadAppointments() {
        appointments.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(APPT_FILE))) {
             String line; br.readLine();
             while ((line = br.readLine()) != null) {
                 String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                 if(data.length > 10) appointments.add(new Appointment(data[0], data[1], data[2], data[4], data[5], data[7], data[8], data[10].replace("\"", "")));
             }
        } catch (IOException e) {}
    }

    private void loadClinicians() {
        clinicians.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(CLINICIAN_FILE))) {
             String line; br.readLine();
             while ((line = br.readLine()) != null) {
                 String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                 if(data.length > 11) clinicians.add(new Clinician(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11]));
             }
        } catch (IOException e) {}
    }

    private void loadPrescriptions() {
        prescriptions.clear();
         try (BufferedReader br = new BufferedReader(new FileReader(RX_FILE))) {
             String line; br.readLine();
             while ((line = br.readLine()) != null) {
                 String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                 if(data.length > 11) {
                     String status = (data.length > 12) ? data[12] : "Issued";
                     prescriptions.add(new Prescription(data[0], data[1], data[2], data[4], data[5], data[6], data[7], data[8], data[11].replace("\"", ""), status));
                 }
             }
        } catch (IOException e) {}
    }

    private void loadReferrals() {
        referrals.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(REF_FILE))) {
            String line; br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (data.length > 13) {
                    // ID(0), Pat(1), RefDoc(2), TarDoc(3), RefFac(4), TarFac(5), Date(6), Urg(7), Reason(8), Summary(9)... Status(11)... Notes(13)
                    referrals.add(new Referral(
                        data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7],
                        data[8], data[9].replace("\"", ""), data[11], data[13].replace("\"", "")
                    ));
                }
            }
        } catch (IOException e) { System.out.println("Error loading referrals: " + e.getMessage()); }
    }

    // --- Getters ---
    public List<Patient> getPatients() { return patients; }
    public List<Appointment> getAppointments() { return appointments; }
    public List<Clinician> getClinicians() { return clinicians; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public List<Referral> getReferrals() { return referrals; }

    public Clinician getClinicianById(String id) {
        for(Clinician c : clinicians) if(c.getClinicianID().equalsIgnoreCase(id)) return c;
        return null;
    }
    public Patient getPatientById(String id) {
        for(Patient p : patients) if(p.getPatientID().equals(id)) return p;
        return null;
    }

    // --- CRUD OPERATIONS ---
    public void addPatient(Patient p) { patients.add(p); appendToFile(PATIENT_FILE, p.toCSV()); }
    public void updatePatient(Patient u) { for(int i=0; i<patients.size(); i++) if(patients.get(i).getPatientID().equals(u.getPatientID())) { patients.set(i, u); break; } rewritePatientFile(); }
    public void deletePatient(String id) { patients.removeIf(p -> p.getPatientID().equals(id)); rewritePatientFile(); }
    private void rewritePatientFile() { try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_FILE))) { bw.write("header..."); bw.newLine(); for(Patient p : patients) { bw.write(p.toCSV()); bw.newLine(); } } catch (IOException e) {} }
    
    public void addClinician(Clinician c) { clinicians.add(c); appendToFile(CLINICIAN_FILE, c.toCSV()); }
    public void updateClinician(Clinician u) { for(int i=0; i<clinicians.size(); i++) if(clinicians.get(i).getClinicianID().equals(u.getClinicianID())) { clinicians.set(i, u); break; } rewriteClinicianFile(); }
    public void deleteClinician(String id) { clinicians.removeIf(c -> c.getClinicianID().equals(id)); rewriteClinicianFile(); }
    private void rewriteClinicianFile() { try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLINICIAN_FILE))) { bw.write("header..."); bw.newLine(); for(Clinician c : clinicians) { bw.write(c.toCSV()); bw.newLine(); } } catch (IOException e) {} }

    public void addAppointment(Appointment a) { appointments.add(a); appendToFile(APPT_FILE, a.toCSV()); }
    public void deleteAppointment(String id) { appointments.removeIf(a -> a.getAppointmentID().equals(id)); rewriteApptFile(); }
    private void rewriteApptFile() { try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPT_FILE))) { bw.write("header..."); bw.newLine(); for(Appointment a : appointments) { bw.write(a.toCSV()); bw.newLine(); } } catch (IOException e) {} }

    public void addPrescription(Prescription p) { prescriptions.add(p); appendToFile(RX_FILE, p.toCSV()); }
    public void deletePrescription(String id) { prescriptions.removeIf(p -> p.getPrescriptionID().equals(id)); rewriteRxFile(); }
    private void rewriteRxFile() { try (BufferedWriter bw = new BufferedWriter(new FileWriter(RX_FILE))) { bw.write("header..."); bw.newLine(); for(Prescription p : prescriptions) { bw.write(p.toCSV()); bw.newLine(); } } catch (IOException e) {} }

    public void addReferral(Referral r) {
        referrals.add(r);
        appendToFile(REF_FILE, r.toCSV());
    }

    public void deleteReferral(String id) {
        referrals.removeIf(r -> r.getReferralID().equals(id));
        rewriteReferralFile();
    }

    private void rewriteReferralFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REF_FILE))) {
            bw.write("referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary,requested_investigations,status,appointment_id,notes,created_date,last_updated");
            bw.newLine();
            for (Referral r : referrals) {
                bw.write(r.toCSV());
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void appendToFile(String filename, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.newLine();
            bw.write(content);
        } catch (IOException e) { e.printStackTrace(); }
    }
}