package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.User;
import Model.Workh;

import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class HastaGUI extends JFrame {

    private JPanel w_pane;
    private static Hasta hasta = new Hasta();
    private static Workh workh = new Workh();
    private Clinic clinic = new Clinic();
    private JTable table_doctor;
    private DefaultTableModel doctorModel;
    private Object[] doctorData = null;
    private JTable table_workh;
    private DefaultTableModel workhModel;
    private Object[] workhData = null;
    private JTextField fld_selDrName;
    private JTextField fld_appointDate;
    private String selDoctorName = null;
    private int selDoctorId = 0;
    private String selClinicName = null;
    private JTable table_appoint;
    private DefaultTableModel appointModel;
    private Object[] appointData = null;
    private Appointment appoint = new Appointment();
    private JTextField fld_selDoc;
    private JTextField fld_selDate;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HastaGUI frame = new HastaGUI(hasta);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     *
     * @throws SQLException
     */
    public HastaGUI(Hasta hasta) throws SQLException {
        setTitle("Hastane Y\u00F6netim Sistemi / Hasta \u0130\u015Flemleri");

        doctorModel = new DefaultTableModel();
        Object[] colDoctor = new Object[2];
        colDoctor[0] = "ID";
        colDoctor[1] = "Ad Soyad";

        doctorModel.setColumnIdentifiers(colDoctor);
        doctorData = new Object[4];

        workhModel = new DefaultTableModel();
        Object[] colWorkh = new Object[2];
        colWorkh[0] = "ID";
        colWorkh[1] = "Tarih";

        workhModel.setColumnIdentifiers(colWorkh);
        workhData = new Object[2];

        appointModel = new DefaultTableModel();
        Object[] colAppoint = new Object[4];
        colAppoint[0] = "ID";
        colAppoint[1] = "Doktor ID";
        colAppoint[2] = "Doktor Adý";
        colAppoint[3] = "Tarih";
        appointModel.setColumnIdentifiers(colAppoint);
        appointData = new Object[4];
        ArrayList<Appointment> appointList = appoint.getHastaAppointmentList(hasta.getId());
        for (int i = 0; i < appointList.size(); i++) {
            appointData[0] = appointList.get(i).getId();
            appointData[1] = appointList.get(i).getDoctorId();
            appointData[2] = appointList.get(i).getDoctorName();
            appointData[3] = appointList.get(i).getAppDate();
            appointModel.addRow(appointData);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        w_pane = new JPanel();
        w_pane.setBackground(Color.WHITE);
        w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
        w_tab.setBounds(10, 83, 716, 370);
        w_pane.add(w_tab);

        JPanel W_appoientment = new JPanel();
        W_appoientment.setBackground(Color.WHITE);
        w_tab.addTab("Randevu Sistemi", null, W_appoientment, null);
        W_appoientment.setLayout(null);

        JScrollPane w_scrollDoctor = new JScrollPane();
        w_scrollDoctor.setBounds(10, 38, 252, 295);
        W_appoientment.add(w_scrollDoctor);

        table_doctor = new JTable(doctorModel);
        w_scrollDoctor.setViewportView(table_doctor);

        JLabel lblNewLabel = new JLabel("Doktor Listesi");
        lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel.setBounds(10, 10, 183, 28);
        W_appoientment.add(lblNewLabel);

        JLabel lbl_1_1 = new JLabel("Poliklinik Ad\u0131");
        lbl_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_1_1.setBounds(271, 10, 179, 28);
        W_appoientment.add(lbl_1_1);

        JComboBox select_clinic = new JComboBox();
        select_clinic.setBounds(272, 38, 165, 35);
        select_clinic.addItem("---Poliklinik Seçiniz---");
        for (int i = 0; i < clinic.getClinicList().size(); i++) {//Combo boxa klinikleri ekledik
            select_clinic.addItem(new Item(clinic.getClinicList().get(i).getId(), clinic.getClinicList().get(i).getName()));
        }
        select_clinic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (select_clinic.getSelectedIndex() != 0) {
                    JComboBox c = (JComboBox) e.getSource();
                    Item item = (Item) c.getSelectedItem();
                    selClinicName = item.getValue();

                    DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
                    clearModel.setRowCount(0);
                    try {
                        ArrayList<User> clinicDoctorList = clinic.getClinicDoctorList(item.getKey());
                        for (int j = 0; j < clinicDoctorList.size(); j++) {
                            doctorData[0] = clinicDoctorList.get(j).getId();
                            doctorData[1] = clinicDoctorList.get(j).getName();
                            doctorModel.addRow(doctorData);
                        }
                    } catch (Exception e2) {

                    }
                } else {
                    DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
                    clearModel.setRowCount(0);
                }
            }
        });
        W_appoientment.add(select_clinic);

        JLabel lbl_selClinicName = new JLabel("Doktor Se\u00E7imi:");
        lbl_selClinicName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_selClinicName.setBounds(272, 111, 106, 28);
        W_appoientment.add(lbl_selClinicName);

        JButton btn_selDoctor = new JButton("Se\u00E7");
        btn_selDoctor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table_doctor.getSelectedRow();
                fld_appointDate.setText(null);
                if (row >= 0) {
                    String name = "Dr. " + table_doctor.getModel().getValueAt(row, 1).toString();
                    fld_selDrName.setText(name);
                    String value = table_doctor.getModel().getValueAt(row, 0).toString();
                    int id = Integer.parseInt(value);
                    DefaultTableModel clearModel = (DefaultTableModel) table_workh.getModel();//bu iþlemler tablonun üzerine diðer poliklink doktorlarýnýda eklemesin diye
                    clearModel.setRowCount(0);
                    try {
                        ArrayList<Workh> workhList = workh.getWorkhList(id);
                        for (int i = 0; i < workhList.size(); i++) {
                            workhData[0] = workhList.get(i).getId();
                            workhData[1] = workhList.get(i).getWdate();
                            workhModel.addRow(workhData);
                        }
                        table_workh.setModel(workhModel);
                        selDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
                        selDoctorId = id;

                    } catch (Exception e2) {

                    }
                } else {
                    fld_selDrName.setText(null);
                    Helper.showMsg("Lütfen doktor listesinden bir doktor seçiniz !");
                }
            }
        });
        btn_selDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_selDoctor.setBounds(272, 168, 165, 28);
        W_appoientment.add(btn_selDoctor);

        JLabel lblRandevuSaatleri = new JLabel("Randevu Saatleri");
        lblRandevuSaatleri.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblRandevuSaatleri.setBounds(447, 10, 183, 28);
        W_appoientment.add(lblRandevuSaatleri);

        JScrollPane w_scrollWorkh = new JScrollPane();
        w_scrollWorkh.setBounds(447, 38, 254, 295);
        W_appoientment.add(w_scrollWorkh);

        table_workh = new JTable(workhModel);
        w_scrollWorkh.setViewportView(table_workh);
        table_workh.getColumnModel().getColumn(0).setPreferredWidth(5);

        table_workh.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    fld_appointDate.setText(table_workh.getValueAt(table_workh.getSelectedRow(), 1).toString());
                } catch (Exception e2) {
                    // TODO: handle exception
                }

            }

        });

        fld_selDrName = new JTextField();
        fld_selDrName.setEditable(false);
        fld_selDrName.setBounds(272, 137, 165, 28);
        W_appoientment.add(fld_selDrName);
        fld_selDrName.setColumns(10);

        JLabel lbl_appointName_1 = new JLabel("Randevu Tarihi:");
        lbl_appointName_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_appointName_1.setBounds(272, 219, 106, 28);
        W_appoientment.add(lbl_appointName_1);

        fld_appointDate = new JTextField();
        fld_appointDate.setEditable(false);
        fld_appointDate.setColumns(10);
        fld_appointDate.setBounds(272, 243, 165, 28);
        W_appoientment.add(fld_appointDate);

        JButton btn_addAppoint = new JButton("Randevu Al");
        btn_addAppoint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table_workh.getSelectedRow();
                if (selRow >= 0) {
                    String date = table_workh.getModel().getValueAt(selRow, 1).toString();
                    if (Helper.confirmAppoint(selClinicName, selDoctorName, date)) {
                        boolean control = hasta.addAppointment(selDoctorId, selDoctorName, hasta.getId(), hasta.getName(), date);
                        if (control) {
                            Helper.showMsg("Randevunuz oluþturuldu!"
                                    + "\nRandevu Bilgileriniz:\nPoliklinik: " + selClinicName
                                    + "\nDoktor: Dr." + selDoctorName + "\nTarih: " + date + "\nRandevu bilgilerini not alýnýz!");
                            hasta.updateWorkhStatus(selDoctorId, date);
                            updateWorkh(selDoctorId);
                            updateAppointModel(hasta.getId());
                        } else {
                            Helper.showMsg("fail");
                        }
                    }
                } else {
                    Helper.showMsg("Lütfen geçerli bir tarih seçiniz");
                }
            }
        });
        btn_addAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_addAppoint.setBounds(272, 274, 165, 28);
        W_appoientment.add(btn_addAppoint);

        JPanel w_appoint = new JPanel();
        w_appoint.setBackground(Color.WHITE);
        w_tab.addTab("Randevularým", null, w_appoint, null);
        w_appoint.setLayout(null);

        JScrollPane w_scrollAppoint = new JScrollPane();
        w_scrollAppoint.setBounds(10, 15, 504, 323);
        w_appoint.add(w_scrollAppoint);

        table_appoint = new JTable(appointModel);
        w_scrollAppoint.setViewportView(table_appoint);

        table_appoint.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    fld_selDoc.setText(table_appoint.getValueAt(table_appoint.getSelectedRow(), 2).toString());
                    fld_selDate.setText(table_appoint.getValueAt(table_appoint.getSelectedRow(), 3).toString());
                } catch (Exception e2) {
                    // TODO: handle exception
                }
            }

        });

        JButton btn_delAppoint = new JButton("Randevuyu Sil");
        btn_delAppoint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("sure")) {
                    int selId = Integer.parseInt(table_appoint.getValueAt(table_appoint.getSelectedRow(), 1).toString());
                    String date = table_appoint.getValueAt(table_appoint.getSelectedRow(), 3).toString();
                    boolean control = appoint.deleteAppointment(selId, date);
                    if (control) {
                        fld_selDoc.setText(null);
                        fld_selDate.setText(null);
                        Helper.showMsg("Randevu silindi");
                        System.out.println(hasta.getId());
                        updateAppointModel(hasta.getId());
                    } else {
                        Helper.showMsg("Silme baþarýsýz");
                    }
                }
            }
        });
        btn_delAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_delAppoint.setBounds(524, 183, 177, 28);
        w_appoint.add(btn_delAppoint);

        JLabel lblNewLabel_1 = new JLabel("Se\u00E7ili Randevu Bilgileri:");
        lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(524, 12, 177, 19);
        w_appoint.add(lblNewLabel_1);

        fld_selDoc = new JTextField();
        fld_selDoc.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        fld_selDoc.setEditable(false);
        fld_selDoc.setBounds(522, 81, 179, 28);
        w_appoint.add(fld_selDoc);
        fld_selDoc.setColumns(10);

        fld_selDate = new JTextField();
        fld_selDate.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        fld_selDate.setEditable(false);
        fld_selDate.setColumns(10);
        fld_selDate.setBounds(524, 145, 179, 28);
        w_appoint.add(fld_selDate);

        JLabel lblNewLabel_1_1 = new JLabel("Doktor Ad\u0131");
        lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_1.setBounds(524, 52, 177, 19);
        w_appoint.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Tarih");
        lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_2.setBounds(524, 119, 177, 19);
        w_appoint.add(lblNewLabel_1_2);

        JLabel lblHogeldinizSayn = new JLabel("Ho\u015Fgeldiniz Say\u0131n, " + hasta.getName());
        lblHogeldinizSayn.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        lblHogeldinizSayn.setBounds(10, 10, 310, 25);
        w_pane.add(lblHogeldinizSayn);

        JButton btn_exit = new JButton("\u00C7\u0131k\u0131\u015F Yap");
        btn_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hasta.closeDb();
                appoint.closeDb();
                clinic.closeDb();
                workh.closeDb();
                LoginGUI login = new LoginGUI();
                login.setVisible(true);
                dispose();
            }
        });
        btn_exit.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_exit.setBounds(617, 11, 109, 25);
        w_pane.add(btn_exit);
    }

    public void updateWorkh(int doctor_id) {
        DefaultTableModel clearModel = (DefaultTableModel) table_workh.getModel();
        clearModel.setRowCount(0);
        try {
            ArrayList<Workh> workhList = workh.getWorkhList(doctor_id);
            for (int i = 0; i < workhList.size(); i++) {
                workhData[0] = workhList.get(i).getId();
                workhData[1] = workhList.get(i).getWdate();
                workhModel.addRow(workhData);
            }

            table_workh.setModel(workhModel);
        } catch (Exception e) {

        }

    }

    public void updateAppointModel(int hasta_id) {
        DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
        clearModel.setRowCount(0);
        ArrayList<Appointment> appointList = appoint.getHastaAppointmentList(hasta_id);
        for (int i = 0; i < appointList.size(); i++) {
            appointData[0] = appointList.get(i).getId();
            appointData[1] = appointList.get(i).getDoctorId();
            appointData[2] = appointList.get(i).getDoctorName();
            appointData[3] = appointList.get(i).getAppDate();
            appointModel.addRow(appointData);
        }
        table_appoint.setModel(appointModel);

    }
}
