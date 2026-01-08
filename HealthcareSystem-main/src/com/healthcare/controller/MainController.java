package com.healthcare.controller;

import com.healthcare.model.*;
import com.healthcare.service.*;
import com.healthcare.view.MainView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainController {
    private MainView view;
    private CSVDatabase db;

    public MainController(MainView view, CSVDatabase db) {
        this.view = view;
        this.db = db;
        refreshTables();
        populateDropdowns();

        // Listeners
        this.view.addPatientListener(new AddPatientListener());
        this.view.addPatientTableListener(new PatientTableMouseListener());
        this.view.addClinicianListener(new AddClinicianListener());
        this.view.addClinicianTableListener(new ClinicianTableMouseListener());

        this.view.addReferralListener(new CreateReferralListener());
        this.view.addDeleteReferralListener(new DeleteReferralListener());
        this.view.addRefDoctorListener(new RefDoctorSelectionListener());

        this.view.addBookApptListener(new BookApptListener());
        this.view.addBookDoctorListener(new BookDoctorSelectionListener());
        this.view.addDeleteApptListener(new DeleteApptListener());

        this.view.addIssueRxListener(new IssueRxListener());
        this.view.addDeleteRxListener(new DeleteRxListener());
    }

    private void refreshTables() {
        // Patients
        view.getPatientModel().setRowCount(0);
        for (Patient p : db.getPatients()) view.getPatientModel().addRow(new Object[]{ p.getPatientID(), p.getFullName() });
        
        // Clinicians
        view.getClinicianModel().setRowCount(0);
        for (Clinician c : db.getClinicians()) view.getClinicianModel().addRow(new Object[]{ c.getClinicianID(), c.getFullName() });
        
        // Appointments
        view.getApptModel().setRowCount(0);
        for (Appointment a : db.getAppointments()) {
            Clinician c = db.getClinicianById(a.getClinicianID());
            view.getApptModel().addRow(new Object[]{ a.getAppointmentID(), a.getPatientID(), a.getClinicianID(), a.getDate(), a.getTime(), a.getStatus(), (c != null ? c.getWorkplaceID() : "Unknown") });
        }
        
        // Referrals
        view.getReferralModel().setRowCount(0);
        for (Referral r : db.getReferrals()) {
            view.getReferralModel().addRow(new Object[]{
                r.getReferralID(), r.getPatientID(), r.getReferringDocID(), r.getDate(), r.getUrgency(), r.getReason(), r.getStatus()
            });
        }

        // Prescriptions
        view.getRxModel().setRowCount(0);
        for (Prescription rx : db.getPrescriptions()) view.getRxModel().addRow(new Object[]{ rx.getPrescriptionID(), rx.getPatientID(), rx.getClinicianID(), rx.getMedication(), rx.getDate() });
    }

    private void populateDropdowns() {
        List<Patient> patients = db.getPatients();
        String[] patientArr = new String[patients.size()];
        for(int i=0; i<patients.size(); i++) patientArr[i] = patients.get(i).getPatientID() + " - " + patients.get(i).getFullName();
        view.setPatientLists(patientArr);

        List<Clinician> docs = db.getClinicians();
        String[] docArr = new String[docs.size()];
        for(int i=0; i<docs.size(); i++) docArr[i] = docs.get(i).toString();
        view.setDoctorLists(docArr);
    }

    // --- Listeners ---
    class AddPatientListener implements ActionListener { public void actionPerformed(ActionEvent e) { String newID = "P" + String.format("%03d", db.getPatients().size() + 1); MainView.PatientFormDialog d = view.new PatientFormDialog(view, new Patient(newID,"","","","","M","","","","","","","",""), true); d.setVisible(true); if (d.isSaved()) { db.addPatient(d.getPatientFromForm()); refreshTables(); populateDropdowns(); } } }
    class PatientTableMouseListener extends MouseAdapter { public void mouseClicked(MouseEvent e) { if (e.getClickCount() == 2) { int row = view.getPatientTable().getSelectedRow(); if (row != -1) { Patient p = db.getPatientById((String)view.getPatientTable().getValueAt(row, 0)); MainView.PatientFormDialog d = view.new PatientFormDialog(view, p, false); d.setVisible(true); if (d.isDeleteRequested()) db.deletePatient(p.getPatientID()); else if (d.isSaved()) db.updatePatient(d.getPatientFromForm()); refreshTables(); populateDropdowns(); } } } }
    class AddClinicianListener implements ActionListener { public void actionPerformed(ActionEvent e) { String newID = "C" + String.format("%03d", db.getClinicians().size() + 1); MainView.ClinicianFormDialog d = view.new ClinicianFormDialog(view, new Clinician(newID,"","","Dr.","","","","","S001","GP","FT","2025"), true); d.setVisible(true); if (d.isSaved()) { db.addClinician(d.getClinicianFromForm()); refreshTables(); populateDropdowns(); } } }
    class ClinicianTableMouseListener extends MouseAdapter { public void mouseClicked(MouseEvent e) { if (e.getClickCount() == 2) { int row = view.getClinicianTable().getSelectedRow(); if (row != -1) { Clinician c = db.getClinicianById((String)view.getClinicianTable().getValueAt(row, 0)); MainView.ClinicianFormDialog d = view.new ClinicianFormDialog(view, c, false); d.setVisible(true); if (d.isDeleteRequested()) db.deleteClinician(c.getClinicianID()); else if (d.isSaved()) db.updateClinician(d.getClinicianFromForm()); refreshTables(); populateDropdowns(); } } } }

    // Referral Listener
    class CreateReferralListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String pStr = view.getRefPatient();
            String dStr = view.getRefDoctor();

            if(pStr != null && dStr != null) {
                String pid = pStr.split(" ")[0];
                String did = dStr.split(" ")[0];
                Patient p = db.getPatientById(pid);
                Clinician c = db.getClinicianById(did);
                String pName = (p != null) ? p.getFullName() : "Unknown";
                String email = (p != null) ? p.getEmail() : "No Email";
                String dName = (c != null) ? c.getFullName() : "Unknown";
                
                String newID = "R" + String.format("%03d", db.getReferrals().size() + 1);
                String today = java.time.LocalDate.now().toString();

                // 1. Add to CSV Database
                // (ID, PID, DID, TargetDoc(empty), RefFac(WorkID), TarFac(empty), Date, Urgency, Reason, Summary, Status, Notes)
                Referral r = new Referral(
                    newID, pid, did, "", c != null ? c.getWorkplaceID() : "", "", 
                    today, view.getRefUrgency(), view.getRefSpecialty(), view.getRefNotes(), "New", "Generated via App"
                );
                db.addReferral(r);

                // 2. Generate Text File (Singleton)
                ReferralManager.getInstance().createReferral(pid, pName, email, did, dName, view.getRefSpecialty(), view.getRefUrgency(), view.getRefNotes());
                
                refreshTables();
                view.showMessage("Referral generated and saved!");
            }
        }
    }

    // Delete Referral Listener
    class DeleteReferralListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int row = view.getReferralTable().getSelectedRow();
            if (row == -1) {
                view.showMessage("Select a referral to delete.");
                return;
            }
            String id = (String) view.getReferralTable().getValueAt(row, 0);
            if(JOptionPane.showConfirmDialog(view, "Delete Referral " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                db.deleteReferral(id);
                refreshTables();
            }
        }
    }

    class IssueRxListener implements ActionListener { public void actionPerformed(ActionEvent e) { String pStr = view.getRxPatient(), dStr = view.getRxDoctor(); if(pStr!=null && dStr!=null && !view.getRxMedication().isEmpty()) { String pid = pStr.split(" ")[0], did = dStr.split(" ")[0]; Patient p = db.getPatientById(pid); Clinician c = db.getClinicianById(did); String rxID = "RX" + String.format("%03d", db.getPrescriptions().size() + 1); db.addPrescription(new Prescription(rxID, pid, did, view.getRxDate(), view.getRxMedication(), view.getRxDosage(), view.getRxFrequency(), view.getRxDuration(), view.getRxPharmacy(), "Issued")); PrescriptionManager.getInstance().issuePrescription(rxID, pid, (p!=null?p.getFullName():""), (p!=null?p.getEmail():""), did, (c!=null?c.getFullName():""), view.getRxMedication(), view.getRxDosage(), view.getRxFrequency(), view.getRxPharmacy()); refreshTables(); view.showMessage("Prescription issued and forwarded to patient!"); } } }
    
    // Other simple listeners
    class RefDoctorSelectionListener implements ActionListener { public void actionPerformed(ActionEvent e) { if(view.getRefDoctor()!=null) { Clinician c = db.getClinicianById(view.getRefDoctor().split(" ")[0]); if(c!=null) view.setRefSpecialty(c.getSpecialty()); } } }
    class BookDoctorSelectionListener implements ActionListener { public void actionPerformed(ActionEvent e) { if(view.getBookDoctor()!=null) { Clinician c = db.getClinicianById(view.getBookDoctor().split(" ")[0]); if(c!=null) view.setBookLocation(c.getWorkplaceID()); } } }
    class BookApptListener implements ActionListener { public void actionPerformed(ActionEvent e) { if(view.getBookPatient()!=null) { db.addAppointment(new Appointment("A"+(db.getAppointments().size()+1), view.getBookPatient().split(" ")[0], view.getBookDoctor().split(" ")[0], view.getBookDate(), view.getBookTime(), view.getBookType(), "Scheduled", view.getBookNotes())); refreshTables(); view.showMessage("Booked!"); } } }
    class DeleteApptListener implements ActionListener { public void actionPerformed(ActionEvent e) { int row = view.getApptTable().getSelectedRow(); if(row!=-1 && JOptionPane.showConfirmDialog(view,"Delete Appointment?","Confirm",0)==0) { db.deleteAppointment((String)view.getApptTable().getValueAt(row,0)); refreshTables(); } } }
    class DeleteRxListener implements ActionListener { public void actionPerformed(ActionEvent e) { int row = view.getRxTable().getSelectedRow(); if(row!=-1 && JOptionPane.showConfirmDialog(view,"Delete Prescription?","Confirm",0)==0) { db.deletePrescription((String)view.getRxTable().getValueAt(row,0)); refreshTables(); } } }
}