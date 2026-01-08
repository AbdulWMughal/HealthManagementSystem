package com.healthcare.view;

import com.healthcare.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;

public class MainViewGUI extends JFrame {
    // 1. Patients Tab
    private JTable patientTable;
    private DefaultTableModel patientModel;
    private JButton btnAddPatient;

    // 2. Clinicians Tab
    private JTable clinicianTable;
    private DefaultTableModel clinicianModel;
    private JButton btnAddClinician;

    // 3. Appointments Tab
    private JTable apptTable;
    private DefaultTableModel apptModel;
    private JButton btnDeleteAppt;
    private JComboBox<String> cmbBookPatient, cmbBookDoctor, cmbBookType;
    private JTextField txtBookLocation, txtBookDate, txtBookTime;
    private JTextArea txtBookNotes;
    private JButton btnBookAppt;

    // 4. Referrals Tab
    private JTable referralTable;
    private DefaultTableModel referralModel;
    private JButton btnDeleteReferral;
    private JComboBox<String> cmbRefPatient, cmbRefDoctor, cmbUrgency;
    private JComboBox<String> cmbRefReason;
    private JTextArea txtRefNotes;
    private JButton btnSubmitReferral;

    // 5. Prescriptions Tab
    private JTable rxTable;
    private DefaultTableModel rxModel;
    private JButton btnDeleteRx;
    private JComboBox<String> cmbRxPatient, cmbRxDoctor;
    private JTextField txtRxMedication, txtRxDosage, txtRxFrequency, txtRxDuration, txtRxPharmacy, txtRxDate;
    private JButton btnIssueRx;

    public MainViewGUI() {
        setTitle("Healthcare Management System");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Tab 1: Patients ---
        JPanel patientPanel = new JPanel(new BorderLayout());
        String[] pCols = {"Patient ID", "Name"};
        patientModel = new DefaultTableModel(pCols, 0) { public boolean isCellEditable(int row, int col) { return false; } };
        patientTable = new JTable(patientModel);
        TableColumnModel pColModel = patientTable.getColumnModel(); pColModel.getColumn(0).setMaxWidth(100); pColModel.getColumn(0).setPreferredWidth(80);
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.LEFT)); JLabel pHint = new JLabel("Double-click to Edit/Delete."); pHint.setForeground(Color.GRAY); pTop.add(pHint);
        patientPanel.add(pTop, BorderLayout.NORTH); patientPanel.add(new JScrollPane(patientTable), BorderLayout.CENTER);
        JPanel pBtn = new JPanel(); btnAddPatient = new JButton("Register New Patient"); pBtn.add(btnAddPatient); patientPanel.add(pBtn, BorderLayout.SOUTH);
        tabbedPane.addTab("Patients", patientPanel);

        // --- Tab 2: Clinicians ---
        JPanel clinPanel = new JPanel(new BorderLayout());
        String[] cCols = {"Clinician ID", "Name"};
        clinicianModel = new DefaultTableModel(cCols, 0) { public boolean isCellEditable(int row, int col) { return false; } };
        clinicianTable = new JTable(clinicianModel);
        TableColumnModel cColModel = clinicianTable.getColumnModel(); cColModel.getColumn(0).setMaxWidth(100); cColModel.getColumn(0).setPreferredWidth(80);
        JPanel cTop = new JPanel(new FlowLayout(FlowLayout.LEFT)); JLabel cHint = new JLabel("Double-click to Edit/Delete."); cHint.setForeground(Color.GRAY); cTop.add(cHint);
        clinPanel.add(cTop, BorderLayout.NORTH); clinPanel.add(new JScrollPane(clinicianTable), BorderLayout.CENTER);
        JPanel cBtn = new JPanel(); btnAddClinician = new JButton("Register New Clinician"); cBtn.add(btnAddClinician); clinPanel.add(cBtn, BorderLayout.SOUTH);
        tabbedPane.addTab("Clinicians", clinPanel);

        // --- Tab 3: Appointments ---
        JPanel apptMainPanel = new JPanel(new BorderLayout());
        String[] aCols = {"Appt ID", "Patient", "Clinician", "Date", "Time", "Status", "Location"};
        apptModel = new DefaultTableModel(aCols, 0); apptTable = new JTable(apptModel);
        apptMainPanel.add(new JScrollPane(apptTable), BorderLayout.CENTER);
        JPanel apptAction = new JPanel(new FlowLayout(FlowLayout.RIGHT)); btnDeleteAppt = new JButton("Delete Selected"); btnDeleteAppt.setBackground(new Color(255,100,100)); btnDeleteAppt.setForeground(Color.WHITE); apptAction.add(btnDeleteAppt);
        JPanel bookingPanel = new JPanel(new GridLayout(4, 4, 5, 5)); bookingPanel.setBorder(BorderFactory.createTitledBorder("Book New Appointment"));
        bookingPanel.add(new JLabel("Patient:")); cmbBookPatient = new JComboBox<>(); bookingPanel.add(cmbBookPatient);
        bookingPanel.add(new JLabel("Clinician:")); cmbBookDoctor = new JComboBox<>(); bookingPanel.add(cmbBookDoctor);
        bookingPanel.add(new JLabel("Location:")); txtBookLocation = new JTextField(); txtBookLocation.setEditable(false); bookingPanel.add(txtBookLocation);
        bookingPanel.add(new JLabel("Type:")); String[] types = {"Routine", "Urgent", "Check-up"}; cmbBookType = new JComboBox<>(types); bookingPanel.add(cmbBookType);
        bookingPanel.add(new JLabel("Date:")); txtBookDate = new JTextField(LocalDate.now().plusDays(1).toString()); bookingPanel.add(txtBookDate);
        bookingPanel.add(new JLabel("Time:")); txtBookTime = new JTextField("09:00"); bookingPanel.add(txtBookTime);
        bookingPanel.add(new JLabel("Notes:")); txtBookNotes = new JTextArea(); bookingPanel.add(new JScrollPane(txtBookNotes));
        btnBookAppt = new JButton("Book Appointment"); bookingPanel.add(btnBookAppt);
        JPanel apptBottom = new JPanel(new BorderLayout()); apptBottom.add(apptAction, BorderLayout.NORTH); apptBottom.add(bookingPanel, BorderLayout.CENTER); apptMainPanel.add(apptBottom, BorderLayout.SOUTH);
        tabbedPane.addTab("Appointments", apptMainPanel);

        // --- Tab 4: Referrals ---
        JPanel refMainPanel = new JPanel(new BorderLayout());
        
        String[] rCols = {"Ref ID", "Patient", "Referring Dr", "Date", "Urgency", "Reason", "Status"};
        referralModel = new DefaultTableModel(rCols, 0);
        referralTable = new JTable(referralModel);
        refMainPanel.add(new JScrollPane(referralTable), BorderLayout.CENTER);

        // Delete Button Panel
        JPanel refAction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnDeleteReferral = new JButton("Delete Selected");
        btnDeleteReferral.setBackground(new Color(255,100,100));
        btnDeleteReferral.setForeground(Color.WHITE);
        refAction.add(btnDeleteReferral);

        JPanel refForm = new JPanel(new GridBagLayout());
        refForm.setBorder(BorderFactory.createTitledBorder("Generate New Referral"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; refForm.add(new JLabel("Patient:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; cmbRefPatient = new JComboBox<>(); refForm.add(cmbRefPatient, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; refForm.add(new JLabel("Referring Dr:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; cmbRefDoctor = new JComboBox<>(); refForm.add(cmbRefDoctor, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; refForm.add(new JLabel("Reason:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; 
        cmbRefReason = new JComboBox<>(); 
        cmbRefReason.setEditable(true);
        refForm.add(cmbRefReason, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; refForm.add(new JLabel("Urgency:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; String[] urgencies = {"Routine", "Urgent", "2-Week Wait", "Non-urgent"}; cmbUrgency = new JComboBox<>(urgencies); refForm.add(cmbUrgency, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; refForm.add(new JLabel("Summary/Notes:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.gridheight = 2; gbc.fill = GridBagConstraints.BOTH;
        txtRefNotes = new JTextArea(3, 20); refForm.add(new JScrollPane(txtRefNotes), gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        btnSubmitReferral = new JButton("Generate Referral"); refForm.add(btnSubmitReferral, gbc);

        JPanel refBottom = new JPanel(new BorderLayout());
        refBottom.add(refAction, BorderLayout.NORTH);
        refBottom.add(refForm, BorderLayout.CENTER);
        refMainPanel.add(refBottom, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Referrals", refMainPanel);

        // --- Tab 5: Prescriptions ---
        JPanel rxMainPanel = new JPanel(new BorderLayout());
        String[] rxCols = {"Rx ID", "Patient", "Clinician", "Medication", "Date"};
        rxModel = new DefaultTableModel(rxCols, 0); rxTable = new JTable(rxModel);
        rxMainPanel.add(new JScrollPane(rxTable), BorderLayout.CENTER);
        JPanel rxAction = new JPanel(new FlowLayout(FlowLayout.RIGHT)); btnDeleteRx = new JButton("Delete Selected"); btnDeleteRx.setBackground(new Color(255,100,100)); btnDeleteRx.setForeground(Color.WHITE); rxAction.add(btnDeleteRx);
        JPanel rxForm = new JPanel(new GridLayout(5, 4, 5, 5)); rxForm.setBorder(BorderFactory.createTitledBorder("Issue New Prescription"));
        rxForm.add(new JLabel("Patient:")); cmbRxPatient = new JComboBox<>(); rxForm.add(cmbRxPatient);
        rxForm.add(new JLabel("Clinician:")); cmbRxDoctor = new JComboBox<>(); rxForm.add(cmbRxDoctor);
        rxForm.add(new JLabel("Medication:")); txtRxMedication = new JTextField(); rxForm.add(txtRxMedication);
        rxForm.add(new JLabel("Dosage:")); txtRxDosage = new JTextField(); rxForm.add(txtRxDosage);
        rxForm.add(new JLabel("Frequency:")); txtRxFrequency = new JTextField("Once daily"); rxForm.add(txtRxFrequency);
        rxForm.add(new JLabel("Duration:")); txtRxDuration = new JTextField("7"); rxForm.add(txtRxDuration);
        rxForm.add(new JLabel("Pharmacy:")); txtRxPharmacy = new JTextField("Boots Pharmacy"); rxForm.add(txtRxPharmacy);
        rxForm.add(new JLabel("Date:")); txtRxDate = new JTextField(LocalDate.now().toString()); txtRxDate.setEditable(false); rxForm.add(txtRxDate);
        btnIssueRx = new JButton("Issue Prescription"); rxForm.add(new JLabel("")); rxForm.add(btnIssueRx);
        JPanel rxBottom = new JPanel(new BorderLayout()); rxBottom.add(rxAction, BorderLayout.NORTH); rxBottom.add(rxForm, BorderLayout.CENTER);
        rxMainPanel.add(rxBottom, BorderLayout.SOUTH);
        tabbedPane.addTab("Prescriptions", rxMainPanel);

        add(tabbedPane);
    }

    // --- Accessors ---
    public DefaultTableModel getPatientModel() { return patientModel; }
    public DefaultTableModel getClinicianModel() { return clinicianModel; }
    public DefaultTableModel getApptModel() { return apptModel; }
    public DefaultTableModel getReferralModel() { return referralModel; }
    public DefaultTableModel getRxModel() { return rxModel; }

    public JTable getPatientTable() { return patientTable; }
    public JTable getClinicianTable() { return clinicianTable; }
    public JTable getApptTable() { return apptTable; }
    public JTable getReferralTable() { return referralTable; }
    public JTable getRxTable() { return rxTable; }

    public void setPatientLists(String[] patients) { cmbBookPatient.setModel(new DefaultComboBoxModel<>(patients)); cmbRefPatient.setModel(new DefaultComboBoxModel<>(patients)); cmbRxPatient.setModel(new DefaultComboBoxModel<>(patients)); }
    public void setDoctorLists(String[] doctors) { cmbBookDoctor.setModel(new DefaultComboBoxModel<>(doctors)); cmbRefDoctor.setModel(new DefaultComboBoxModel<>(doctors)); cmbRxDoctor.setModel(new DefaultComboBoxModel<>(doctors)); }
    
    // --- Getters ---
    public String getBookPatient() { return (String) cmbBookPatient.getSelectedItem(); }
    public String getBookDoctor() { return (String) cmbBookDoctor.getSelectedItem(); }
    public String getBookLocation() { return txtBookLocation.getText(); }
    public String getBookType() { return (String) cmbBookType.getSelectedItem(); }
    public String getBookDate() { return txtBookDate.getText(); }
    public String getBookTime() { return txtBookTime.getText(); }
    public String getBookNotes() { return txtBookNotes.getText(); }
    public void setBookLocation(String loc) { txtBookLocation.setText(loc); }
    
    public String getRefPatient() { return (String) cmbRefPatient.getSelectedItem(); }
    public String getRefDoctor() { return (String) cmbRefDoctor.getSelectedItem(); }
    public String getRefUrgency() { return (String) cmbUrgency.getSelectedItem(); }
    public String getRefNotes() { return txtRefNotes.getText(); }
    
    public String getRxPatient() { return (String) cmbRxPatient.getSelectedItem(); }
    public String getRxDoctor() { return (String) cmbRxDoctor.getSelectedItem(); }
    public String getRxMedication() { return txtRxMedication.getText(); }
    public String getRxDosage() { return txtRxDosage.getText(); }
    public String getRxFrequency() { return txtRxFrequency.getText(); }
    public String getRxDuration() { return txtRxDuration.getText(); }
    public String getRxPharmacy() { return txtRxPharmacy.getText(); }
    public String getRxDate() { return txtRxDate.getText(); }

    public String getRefSpecialty() { return (String) cmbRefReason.getEditor().getItem(); }
    public void setRefSpecialty(String spec) { cmbRefReason.setSelectedItem(spec); }
    public void setReasonList(String[] reasons) {
        cmbRefReason.setModel(new DefaultComboBoxModel<>(reasons));
        cmbRefReason.setSelectedItem("");
    }

    // --- Listeners ---
    public void addPatientListener(ActionListener al) { btnAddPatient.addActionListener(al); }
    public void addPatientTableListener(MouseAdapter ma) { patientTable.addMouseListener(ma); }
    public void addClinicianListener(ActionListener al) { btnAddClinician.addActionListener(al); }
    public void addClinicianTableListener(MouseAdapter ma) { clinicianTable.addMouseListener(ma); }
    
    public void addReferralListener(ActionListener al) { btnSubmitReferral.addActionListener(al); }
    public void addDeleteReferralListener(ActionListener al) { btnDeleteReferral.addActionListener(al); }
    public void addRefDoctorListener(ActionListener al) { cmbRefDoctor.addActionListener(al); }
    
    public void addBookApptListener(ActionListener al) { btnBookAppt.addActionListener(al); }
    public void addBookDoctorListener(ActionListener al) { cmbBookDoctor.addActionListener(al); }
    public void addDeleteApptListener(ActionListener al) { btnDeleteAppt.addActionListener(al); }
    
    public void addIssueRxListener(ActionListener al) { btnIssueRx.addActionListener(al); }
    public void addDeleteRxListener(ActionListener al) { btnDeleteRx.addActionListener(al); }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

    // Patient Form Dialog
    public class PatientFormDialog extends JDialog { 
         private JTextField txtID, txtFName, txtLName, txtDOB, txtNHS, txtPhone, txtEmail, txtAddress, txtPostcode, txtEmergName, txtEmergPhone;
        private JComboBox<String> cmbGender; private JButton btnSave, btnCancel, btnDelete; private boolean saved = false, deleteRequested = false;
        public PatientFormDialog(JFrame parent, Patient p, boolean isNew) {
            super(parent, isNew ? "Register New Patient" : "Edit Patient Details", true); setLayout(new BorderLayout()); setSize(500, 600); setLocationRelativeTo(parent);
            JPanel form = new JPanel(new GridLayout(12, 2, 10, 10)); form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            form.add(new JLabel("ID:")); txtID = new JTextField(p.getPatientID()); txtID.setEditable(false); form.add(txtID);
            form.add(new JLabel("First:")); txtFName = new JTextField(p.getFirstName()); form.add(txtFName);
            form.add(new JLabel("Last:")); txtLName = new JTextField(p.getLastName()); form.add(txtLName);
            form.add(new JLabel("DOB:")); txtDOB = new JTextField(p.getDob()); form.add(txtDOB);
            form.add(new JLabel("NHS:")); txtNHS = new JTextField(p.getNhsNumber()); form.add(txtNHS);
            form.add(new JLabel("Gender:")); cmbGender = new JComboBox<>(new String[]{"M", "F", "Other"}); cmbGender.setSelectedItem(p.getGender()); form.add(cmbGender);
            form.add(new JLabel("Phone:")); txtPhone = new JTextField(p.getPhone()); form.add(txtPhone);
            form.add(new JLabel("Email:")); txtEmail = new JTextField(p.getEmail()); form.add(txtEmail);
            form.add(new JLabel("Address:")); txtAddress = new JTextField(p.getAddress()); form.add(txtAddress);
            form.add(new JLabel("Postcode:")); txtPostcode = new JTextField(p.getPostcode()); form.add(txtPostcode);
            form.add(new JLabel("Emerg Name:")); txtEmergName = new JTextField(p.getEmergencyName()); form.add(txtEmergName);
            form.add(new JLabel("Emerg Phone:")); txtEmergPhone = new JTextField(p.getEmergencyPhone()); form.add(txtEmergPhone);
            add(new JScrollPane(form), BorderLayout.CENTER);
            JPanel btnPanel = new JPanel(); btnSave = new JButton("Save"); btnCancel = new JButton("Cancel"); btnPanel.add(btnSave);
            if (!isNew) { btnDelete = new JButton("Delete"); btnDelete.setBackground(Color.RED); btnDelete.setForeground(Color.WHITE); btnPanel.add(btnDelete); btnDelete.addActionListener(e -> { if(JOptionPane.showConfirmDialog(this,"Delete?","Confirm",0)==0) { deleteRequested=true; dispose(); }}); }
            btnPanel.add(btnCancel); add(btnPanel, BorderLayout.SOUTH); btnSave.addActionListener(e -> { saved = true; dispose(); }); btnCancel.addActionListener(e -> dispose());
        }
        public boolean isSaved() { return saved; } public boolean isDeleteRequested() { return deleteRequested; } public Patient getPatientFromForm() { return new Patient(txtID.getText(), txtFName.getText(), txtLName.getText(), txtDOB.getText(), txtNHS.getText(), (String)cmbGender.getSelectedItem(), txtPhone.getText(), txtEmail.getText(), txtAddress.getText(), txtPostcode.getText(), txtEmergName.getText(), txtEmergPhone.getText(), LocalDate.now().toString(), "S001"); }
    }
    // Clinician Form Dialog
    public class ClinicianFormDialog extends JDialog { 
         private JTextField txtID, txtFName, txtLName, txtSpec, txtGMC, txtPhone, txtEmail, txtWorkID, txtStart; private JComboBox<String> cmbTitle, cmbWorkType, cmbStatus; private JButton btnSave, btnCancel, btnDelete; private boolean saved = false, deleteRequested = false;
        public ClinicianFormDialog(JFrame parent, Clinician c, boolean isNew) {
            super(parent, isNew ? "Register New Clinician" : "Edit Details", true); setLayout(new BorderLayout()); setSize(500, 600); setLocationRelativeTo(parent);
            JPanel form = new JPanel(new GridLayout(12, 2, 10, 10)); form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            form.add(new JLabel("ID:")); txtID = new JTextField(c.getClinicianID()); txtID.setEditable(false); form.add(txtID);
            form.add(new JLabel("Title:")); cmbTitle = new JComboBox<>(new String[]{"Dr.", "Nurse", "Mr.", "Mrs."}); cmbTitle.setSelectedItem(c.getTitle()); form.add(cmbTitle);
            form.add(new JLabel("First:")); txtFName = new JTextField(c.getFirstName()); form.add(txtFName);
            form.add(new JLabel("Last:")); txtLName = new JTextField(c.getLastName()); form.add(txtLName);
            form.add(new JLabel("Specialty:")); txtSpec = new JTextField(c.getSpecialty()); form.add(txtSpec);
            form.add(new JLabel("GMC:")); txtGMC = new JTextField(c.getGmcNumber()); form.add(txtGMC);
            form.add(new JLabel("Phone:")); txtPhone = new JTextField(c.getPhone()); form.add(txtPhone);
            form.add(new JLabel("Email:")); txtEmail = new JTextField(c.getEmail()); form.add(txtEmail);
            form.add(new JLabel("Work ID:")); txtWorkID = new JTextField(c.getWorkplaceID()); form.add(txtWorkID);
            form.add(new JLabel("Type:")); cmbWorkType = new JComboBox<>(new String[]{"Hospital", "GP Surgery"}); cmbWorkType.setSelectedItem(c.getWorkplaceType()); form.add(cmbWorkType);
            form.add(new JLabel("Status:")); cmbStatus = new JComboBox<>(new String[]{"Full-time", "Part-time"}); cmbStatus.setSelectedItem(c.getEmpStatus()); form.add(cmbStatus);
            form.add(new JLabel("Start Date:")); txtStart = new JTextField(c.getStartDate()); form.add(txtStart);
            add(new JScrollPane(form), BorderLayout.CENTER);
            JPanel btnPanel = new JPanel(); btnSave = new JButton("Save"); btnCancel = new JButton("Cancel"); btnPanel.add(btnSave);
            if (!isNew) { btnDelete = new JButton("Delete"); btnDelete.setBackground(Color.RED); btnDelete.setForeground(Color.WHITE); btnPanel.add(btnDelete); btnDelete.addActionListener(e -> { if(JOptionPane.showConfirmDialog(this,"Delete?","Confirm",0)==0) { deleteRequested=true; dispose(); }}); }
            btnPanel.add(btnCancel); add(btnPanel, BorderLayout.SOUTH); btnSave.addActionListener(e -> { saved = true; dispose(); }); btnCancel.addActionListener(e -> dispose());
        }
        public boolean isSaved() { return saved; } public boolean isDeleteRequested() { return deleteRequested; } public Clinician getClinicianFromForm() { return new Clinician(txtID.getText(), txtFName.getText(), txtLName.getText(), (String)cmbTitle.getSelectedItem(), txtSpec.getText(), txtGMC.getText(), txtPhone.getText(), txtEmail.getText(), txtWorkID.getText(), (String)cmbWorkType.getSelectedItem(), (String)cmbStatus.getSelectedItem(), txtStart.getText()); }
    }
}