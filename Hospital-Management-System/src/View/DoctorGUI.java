package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.Appointment;
import Model.Doctor;
import Model.Workh;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JFormattedTextField;
import javax.swing.DropMode;

public class DoctorGUI extends JFrame {

    private JPanel w_pane;
    private static Doctor doctor = new Doctor();
    private JTable table_workh;
    private DefaultTableModel workhModel;
    private Object[] workhData = null;
    private JTextField fld_patient;
    private JTextField fld_patientId;
    private JTextField fld_appoint;
    private JTable table_appoint;
    private Appointment appointment = new Appointment();
    private Object[] appointData = null;
    private DefaultTableModel appointModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DoctorGUI frame = new DoctorGUI(doctor);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public DoctorGUI(Doctor doctor) {

        workhModel = new DefaultTableModel();
        Object[] colWorkh = new Object[2];
        colWorkh[0] = "ID";
        colWorkh[1] = "Tarih";
        workhModel.setColumnIdentifiers(colWorkh);
        workhData = new Object[2];
        ArrayList<Workh> workhList = doctor.getWorkhList(doctor.getId());
        for (int i = 0; i < workhList.size(); i++) {
            workhData[0] = workhList.get(i).getId();
            workhData[1] = workhList.get(i).getWdate();
            workhModel.addRow(workhData);
        }

        appointModel = new DefaultTableModel();
        Object[] colAppoint = new Object[3];
        colAppoint[0] = "ID";
        colAppoint[1] = "Hasta Adý";
        colAppoint[2] = "Randevu Tarihi";
        appointModel.setColumnIdentifiers(colAppoint);
        appointData = new Object[3];
        ArrayList<Appointment> appointList = appointment.getDoctorAppointmentList(doctor.getId());
        for (int i = 0; i < appointList.size(); i++) {
            appointData[0] = appointList.get(i).getId();
            appointData[1] = appointList.get(i).getHastaName();
            appointData[2] = appointList.get(i).getAppDate();
            appointModel.addRow(appointData);
        }

        setResizable(false);
        setTitle("Hastane Y\u00F6netim Sistemi / Doktor \u0130\u015Flemleri");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        w_pane = new JPanel();
        w_pane.setBackground(Color.WHITE);
        w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Hoþgeldiniz Sayýn, " + doctor.getName());
        lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        lblNewLabel.setBounds(10, 10, 310, 25);
        w_pane.add(lblNewLabel);

        JButton btnNewButton = new JButton("\u00C7\u0131k\u0131\u015F Yap");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doctor.closeDb();
                appointment.closeDb();
                LoginGUI login = new LoginGUI();
                login.setVisible(true);
                dispose();
            }
        });
        btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btnNewButton.setBounds(605, 11, 109, 25);
        w_pane.add(btnNewButton);

        JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
        w_tab.setBounds(10, 83, 716, 370);
        w_pane.add(w_tab);

        JPanel w_workh = new JPanel();
        w_workh.setBackground(Color.WHITE);
        w_tab.addTab("Çalýþma Saatleri", null, w_workh, null);
        w_workh.setLayout(null);

        JDateChooser select_date = new JDateChooser();
        select_date.setBounds(10, 10, 150, 30);
        w_workh.add(select_date);

        JComboBox select_hour = new JComboBox();
        select_hour.setFont(new Font("Tahoma", Font.PLAIN, 12));
        select_hour.setModel(new DefaultComboBoxModel(new String[]{"06", "07", "08", "09", "10", "10", "11", "11", "12", "12", "13", "13", "14", "14", "15", "15", "16", "16", "17", "18"}));
        select_hour.setBounds(185, 10, 44, 30);
        w_workh.add(select_hour);

        JComboBox select_minute = new JComboBox();
        select_minute.setModel(new DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        select_minute.setFont(new Font("Tahoma", Font.PLAIN, 12));
        select_minute.setBounds(246, 10, 44, 30);
        w_workh.add(select_minute);

        JButton btn_addDate = new JButton("Ekle");
        btn_addDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // alacaðýmýz tarihin formatýný giriyoruz
                String date = "", selectDate = "";
                try {
                    date = sdf.format(select_date.getDate());
                } catch (Exception e2) {

                }
                if (date.length() == 0) {//tarih seçmemiþse
                    Helper.showMsg("Lütfen geçerli bir tarih giriniz !");
                } else {
                    String hour = " " + select_hour.getSelectedItem().toString();
                    String minute = select_minute.getSelectedItem().toString();
                    String time = hour + ":" + minute + ":" + "00";
                    selectDate = date + time;
                }
                try {
                    boolean control = doctor.addWorkh(doctor.getId(), doctor.getName(), selectDate);
                    if (control) {
                        Helper.showMsg("success");
                        updateWorkhModel(doctor);
                    } else {
                        Helper.showMsg("fail");
                    }
                } catch (Exception e1) {

                }
            }
        });
        btn_addDate.setBounds(300, 10, 106, 30);
        w_workh.add(btn_addDate);

        JScrollPane w_scrollWorkh = new JScrollPane();
        w_scrollWorkh.setBounds(0, 60, 711, 283);
        w_workh.add(w_scrollWorkh);

        table_workh = new JTable(workhModel);
        w_scrollWorkh.setViewportView(table_workh);

        JButton btn_delDate = new JButton("Sil");
        btn_delDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table_workh.getSelectedRow();
                if (selRow >= 0) {
                    String selectRow = table_workh.getValueAt(selRow, 0).toString();
                    int selID = Integer.parseInt(selectRow);
                    boolean control = doctor.deleteWorkh(selID);
                    if (control) {
                        Helper.showMsg("success");
                        updateWorkhModel(doctor);
                    } else {
                        Helper.showMsg("fail");
                    }

                } else {
                    Helper.showMsg("Lütfen geçerli bir tarih seçiniz!");
                }
            }
        });
        btn_delDate.setBounds(595, 10, 106, 30);
        w_workh.add(btn_delDate);

        JLabel lblNewLabel_1 = new JLabel(":");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(235, 10, 16, 30);
        w_workh.add(lblNewLabel_1);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        w_tab.addTab("Randevular", null, panel, null);
        panel.setLayout(null);

        JScrollPane scroll_appoint = new JScrollPane();
        scroll_appoint.setBounds(10, 10, 502, 323);
        panel.add(scroll_appoint);

        table_appoint = new JTable(appointModel);
        scroll_appoint.setViewportView(table_appoint);

        table_appoint.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                // TODO Auto-generated method stub
                int row = table_appoint.getSelectedRow();
                if (row >= 0) {
                    String id = table_appoint.getValueAt(row, 0).toString();
                    String name = table_appoint.getValueAt(row, 1).toString();
                    String date = table_appoint.getValueAt(row, 2).toString();

                    fld_patientId.setText(id);
                    fld_patient.setText(name);
                    fld_appoint.setText(date);

                } else {
                    fld_patientId.setText("");
                    fld_patient.setText("");
                    fld_appoint.setText("");
                }

            }

        });

        JLabel lblNewLabel_1_1 = new JLabel("Hasta Ad\u0131");
        lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_1.setBounds(524, 38, 177, 19);
        panel.add(lblNewLabel_1_1);

        fld_patient = new JTextField();
        fld_patient.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        fld_patient.setEditable(false);
        fld_patient.setColumns(10);
        fld_patient.setBounds(522, 67, 179, 28);
        panel.add(fld_patient);

        JLabel lblNewLabel_1_1_1 = new JLabel("Hasta ID");
        lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_1_1.setBounds(524, 97, 177, 19);
        panel.add(lblNewLabel_1_1_1);

        fld_patientId = new JTextField();
        fld_patientId.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        fld_patientId.setEditable(false);
        fld_patientId.setColumns(10);
        fld_patientId.setBounds(522, 126, 179, 28);
        panel.add(fld_patientId);

        JLabel lblNewLabel_1_2_1 = new JLabel("Se\u00E7ili Randevu Bilgileri :");
        lblNewLabel_1_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_2_1.setBounds(522, 10, 177, 19);
        panel.add(lblNewLabel_1_2_1);

        JLabel lblNewLabel_1_2 = new JLabel("Tarih");
        lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_2.setBounds(522, 164, 177, 19);
        panel.add(lblNewLabel_1_2);

        fld_appoint = new JTextField();
        fld_appoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        fld_appoint.setEditable(false);
        fld_appoint.setColumns(10);
        fld_appoint.setBounds(522, 193, 179, 28);
        panel.add(fld_appoint);

        JButton btn_delAppoint = new JButton("Randevuyu Sil");
        btn_delAppoint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = fld_appoint.getText();
                if (date.length() != 0 && Helper.confirm("sure")) {
                    boolean control = appointment.deleteAppointment(doctor.getId(), date);
                    if (control) {
                        Helper.showMsg("success");
                        updateAppointModel(doctor);
                    } else {
                        Helper.showMsg("fail");
                    }
                }
            }
        });
        btn_delAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_delAppoint.setBounds(522, 231, 177, 28);
        panel.add(btn_delAppoint);
    }

    public void updateWorkhModel(Doctor doctor) {//doktor çalýþma saatlerini güncelleyen fonksiyon
        DefaultTableModel clearModel = (DefaultTableModel) table_workh.getModel();
        clearModel.setRowCount(0);//burada tüm satýrlarý sildik referans ile aslýnda
        ArrayList<Workh> workhList = doctor.getWorkhList(doctor.getId());
        for (int i = 0; i < workhList.size(); i++) {
            workhData[0] = workhList.get(i).getId();
            workhData[1] = workhList.get(i).getWdate();
            workhModel.addRow(workhData);
        }
    }

    public void updateAppointModel(Doctor doctor) { //randevu saatlerini güncelleyen metod
        DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
        clearModel.setRowCount(0);
        ArrayList<Appointment> appointList = appointment.getDoctorAppointmentList(doctor.getId());
        for (int i = 0; i < appointList.size(); i++) {
            appointData[0] = appointList.get(i).getId();
            appointData[1] = appointList.get(i).getHastaName();
            appointData[2] = appointList.get(i).getAppDate();
            appointModel.addRow(appointData);
        }
    }
}
