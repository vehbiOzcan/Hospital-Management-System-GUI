package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.Appointment;
import Model.Bashekim;
import Model.Clinic;
import Model.Doctor;
import Model.User;
import Helper.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {

    private static Bashekim bashekim = new Bashekim();
    private Clinic clinic = new Clinic();
    private JPanel w_pane;
    private JTextField fld_dName;
    private JTextField fld_dTc;
    private JTextField fld_dPass;
    private JTextField fld_doctorId;
    private JTable table_doctor;
    private DefaultTableModel doctorModel = null;
    private Object[] doctorData = null;
    private JTable table_clinic;
    private JTextField fld_clinicName;
    private DefaultTableModel clinicModel = null;
    private Object[] clinicData = null;
    private JPopupMenu clinicMenu;
    private JTable table_worker;
    private JTable table_doctorList;
    private DefaultTableModel doctorListModel = null;
    private Object[] doctorListData = null;
    private JTextField fld_doctorApppoint;
    private JTextField fld_doctorAppointId;
    private JTextField fld_appointDate;
    private JTable table_appointList;
    private Object[] appointData = null;
    private DefaultTableModel appointModel = null;
    private Appointment appoint = new Appointment();

    /**
     * Launch the application.
     */

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BashekimGUI frame = new BashekimGUI(bashekim);
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
    public BashekimGUI(Bashekim bashekim) throws SQLException {
        //Doktor modeli tabloya eklenme kýsmý
        doctorModel = new DefaultTableModel();
        Object[] colDoctorName = new Object[4];
        colDoctorName[0] = "ID";
        colDoctorName[1] = "Ad Soyad";
        colDoctorName[2] = "T.C. No";
        colDoctorName[3] = "Þifre";
        doctorModel.setColumnIdentifiers(colDoctorName);
        doctorData = new Object[4];
        ArrayList<User> doctorlist = bashekim.getDoctorList();
        for (int i = 0; i < doctorlist.size(); i++) {
            doctorData[0] = doctorlist.get(i).getId();
            doctorData[1] = doctorlist.get(i).getName();
            doctorData[2] = doctorlist.get(i).getTcno();
            doctorData[3] = doctorlist.get(i).getPassword();
            doctorModel.addRow(doctorData);
        }
        //Klinik modeli tabloya eklenme kýsmý
        clinicModel = new DefaultTableModel();
        Object[] colClinic = new Object[2];
        colClinic[0] = "ID";
        colClinic[1] = "Poliklinik Adý";
        clinicModel.setColumnIdentifiers(colClinic);
        clinicData = new Object[2];
        ArrayList<Clinic> clinicList = clinic.getClinicList();
        for (int j = 0; j < clinicList.size(); j++) {
            clinicData[0] = clinicList.get(j).getId();
            clinicData[1] = clinicList.get(j).getName();
            clinicModel.addRow(clinicData);
        }

        doctorListModel = new DefaultTableModel();
        Object[] colDoctorList = new Object[2];
        colDoctorList[0] = "Doktor ID";
        colDoctorList[1] = "Doktor Adý";
        doctorListModel.setColumnIdentifiers(colDoctorList);
        doctorListData = new Object[2];
        ArrayList<User> drlist = bashekim.getDoctorList();
        for (int i = 0; i < drlist.size(); i++) {
            doctorListData[0] = drlist.get(i).getId();
            doctorListData[1] = drlist.get(i).getName();
            doctorListModel.addRow(doctorListData);
        }

        DefaultTableModel workerModel = new DefaultTableModel();
        Object[] colWorker = new Object[2];
        colWorker[0] = "ID";
        colWorker[1] = "Ad Soyad";
        workerModel.setColumnIdentifiers(colWorker);
        Object[] workerData = new Object[2];

        appointModel = new DefaultTableModel();
        Object[] colAppoint = new Object[2];
        colAppoint[0] = "Hasta Adý";
        colAppoint[1] = "Tarih";
        appointModel.setColumnIdentifiers(colAppoint);

        setTitle("Hastane Y\u00F6netim Sistemi / Ba\u015Fhekim \u0130\u015Flemleri");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        w_pane = new JPanel();
        w_pane.setBackground(Color.WHITE);
        w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Hoþgeldiniz Sayýn, " + bashekim.getName());
        lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
        lblNewLabel.setBounds(10, 8, 310, 25);
        w_pane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Çýkýþ Yap");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bashekim.closeDb();
                appoint.closeDb();
                clinic.closeDb();
                LoginGUI login = new LoginGUI();
                login.setVisible(true);
                dispose();
            }
        });
        btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btnNewButton.setBounds(605, 9, 109, 25);
        w_pane.add(btnNewButton);

        JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
        w_tab.setBounds(10, 83, 716, 370);
        w_pane.add(w_tab);

        JPanel w_doctor = new JPanel();
        w_doctor.setBackground(Color.WHITE);
        w_tab.addTab("Doktor Yönetimi", null, w_doctor, null);
        w_doctor.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setBounds(473, 10, 228, 323);
        w_doctor.add(panel);
        panel.setLayout(null);

        JLabel lbl_1 = new JLabel("Ad Soyad");
        lbl_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_1.setBounds(10, 10, 209, 28);
        panel.add(lbl_1);

        fld_dName = new JTextField();
        fld_dName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        fld_dName.setColumns(10);
        fld_dName.setBounds(10, 36, 209, 28);
        panel.add(fld_dName);

        JLabel lbl_2 = new JLabel("T.C. Numara");
        lbl_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_2.setBounds(10, 59, 230, 28);
        panel.add(lbl_2);

        fld_dTc = new JTextField();
        fld_dTc.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        fld_dTc.setColumns(10);
        fld_dTc.setBounds(10, 84, 209, 28);
        panel.add(fld_dTc);

        JLabel lbl_3 = new JLabel("\u015Eifre");
        lbl_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_3.setBounds(10, 110, 230, 28);
        panel.add(lbl_3);

        fld_dPass = new JTextField();
        fld_dPass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        fld_dPass.setColumns(10);
        fld_dPass.setBounds(10, 134, 209, 28);
        panel.add(fld_dPass);

        JButton btn_addDoctor = new JButton("Ekle");
        btn_addDoctor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_dName.getText().length() == 0 || fld_dTc.getText().length() == 0 || fld_dPass.getText().length() == 0) {
                    Helper.showMsg("fillup");
                } else {
                    try {
                        boolean control = bashekim.addDoctor(fld_dTc.getText(), fld_dPass.getText(), fld_dName.getText());
                        if (control) {
                            Helper.showMsg("success");
                            fld_dName.setText(null);
                            fld_dTc.setText(null);
                            fld_dPass.setText(null);
                            updateDoctorModel();
                        } else {
                            Helper.showMsg("fail");
                        }
                    } catch (Exception e2) {
                        System.out.println(e2.getMessage());
                    }
                }
            }
        });
        btn_addDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_addDoctor.setBounds(10, 172, 209, 28);
        panel.add(btn_addDoctor);

        JLabel lbl_4 = new JLabel("Kullan\u0131c\u0131 ID");
        lbl_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_4.setBounds(10, 222, 209, 28);
        panel.add(lbl_4);

        fld_doctorId = new JTextField();
        fld_doctorId.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        fld_doctorId.setColumns(10);
        fld_doctorId.setBounds(10, 247, 209, 28);
        panel.add(fld_doctorId);

        JButton btn_delDoctor = new JButton("Sil");
        btn_delDoctor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_doctorId.getText().length() == 0) {
                    Helper.showMsg("Lütfen geçerli bir id deðeri seçiniz/giriniz !");
                } else {
                    if (Helper.confirm("sure")) {
                        int selectID = Integer.parseInt(fld_doctorId.getText());
                        //try {
                        boolean control = bashekim.deleteDoctor(selectID);
                        boolean controlW = bashekim.deleteWorker(selectID);
                        if (control && controlW) {
                            Helper.showMsg("success");
                            fld_doctorId.setText(null);
                            updateDoctorModel();
                        }
                       

                    }
                }
            }
        });
        btn_delDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_delDoctor.setBounds(10, 285, 209, 28);
        panel.add(btn_delDoctor);

        JScrollPane w_scrollDoctor = new JScrollPane();
        w_scrollDoctor.setBounds(10, 10, 453, 323);
        w_doctor.add(w_scrollDoctor);

        table_doctor = new JTable(doctorModel);
        w_scrollDoctor.setViewportView(table_doctor);

        table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
           

            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    fld_doctorId.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
                } catch (Exception e2) {
                    // TODO: handle exception
                }
                
            }

        });

        table_doctor.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {

                if (e.getType() == TableModelEvent.UPDATE) {
                    int selectID = Integer.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
                    String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
                    String selectTcno = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
                    String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();
                    if (Helper.confirm("sure")) {
                        try {
                            bashekim.updateDoctor(selectID, selectName, selectPass, selectTcno);
                            updateDoctorModel();
                        } catch (Exception e2) {
                            System.out.println(e2.getMessage());
                        }
                    }
                }

            }

        });

        JPanel w_clinic = new JPanel();
        w_clinic.setBackground(Color.WHITE);
        w_tab.addTab("Poliklinikler", null, w_clinic, null);
        w_clinic.setLayout(null);

        JScrollPane w_scrollClinic = new JScrollPane();
        w_scrollClinic.setBounds(10, 10, 246, 323);
        w_clinic.add(w_scrollClinic);

        clinicMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        clinicMenu.add(updateMenu);
        clinicMenu.add(deleteMenu);

        updateMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectId = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
                Clinic selectClinic = clinic.getFetch(selectId);
                UpdateClinicGUI updateClinicGUI = new UpdateClinicGUI(selectClinic);
                updateClinicGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                updateClinicGUI.setVisible(true);
                updateClinicGUI.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        updateClinicModel();
                    }
                });
            }

        });

        deleteMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectId = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
                if (Helper.confirm("sure")) {
                    try {
                        if (clinic.deleteClinic(selectId)) {
                            Helper.showMsg("success");
                            updateClinicModel();
                        } else {
                            Helper.showMsg("fail");
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

            }

        });

        table_clinic = new JTable(clinicModel);
        table_clinic.setComponentPopupMenu(clinicMenu); 
        table_clinic.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint(); 
                int selectedRow = table_clinic.rowAtPoint(point);
                table_clinic.setRowSelectionInterval(selectedRow, selectedRow);

            }
        });

        w_scrollClinic.setViewportView(table_clinic);

        JLabel lbl_1_1 = new JLabel("Poliklinik Ad\u0131");
        lbl_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_1_1.setBounds(266, 10, 179, 28);
        w_clinic.add(lbl_1_1);

        fld_clinicName = new JTextField();
        fld_clinicName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        fld_clinicName.setColumns(10);
        fld_clinicName.setBounds(266, 36, 179, 28);
        w_clinic.add(fld_clinicName);

        JButton btn_addClinic = new JButton("Ekle");
        btn_addClinic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_clinicName.getText().length() == 0) {
                    Helper.showMsg("fillup");
                } else {
                    try {
                        if (clinic.addClinic(fld_clinicName.getText())) {
                            Helper.showMsg("success");
                            fld_clinicName.setText(null);
                        }
                    } catch (Exception e2) {
                        System.out.println(e2.getMessage());
                    }
                }
                updateClinicModel();
            }
        });
        btn_addClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_addClinic.setBounds(266, 74, 179, 28);
        w_clinic.add(btn_addClinic);

        JScrollPane w_scrollWorker = new JScrollPane();
        w_scrollWorker.setBounds(455, 10, 246, 323);
        w_clinic.add(w_scrollWorker);

        table_worker = new JTable();
        w_scrollWorker.setViewportView(table_worker);

        JComboBox select_doctor = new JComboBox();
        select_doctor.setBounds(266, 218, 179, 36);
        for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
            select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
        }
        select_doctor.addActionListener(e -> {
            JComboBox c = (JComboBox) e.getSource();
            Item item = (Item) c.getSelectedItem();
            System.out.println(item.getKey() + " : " + item.getValue());
        });

        w_clinic.add(select_doctor);

        JButton btn_addWorker = new JButton("Ekle");
        btn_addWorker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table_clinic.getSelectedRow();
                if (selRow >= 0) {//18.13
                    String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
                    int selClinicId = Integer.parseInt(selClinic);
                    Item doctorItem = (Item) select_doctor.getSelectedItem();
                    boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicId);
                    if (control) {
                        Helper.showMsg("success");

                    } else {
                        Helper.showMsg("fail");
                    }
 
                    DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
                    clearModel.setRowCount(0);
                    try {
                        ArrayList<User> clinicDoctorList = bashekim.getClinicDoctorList(selClinicId);
                        for (int i = 0; i < clinicDoctorList.size(); i++) {
                            workerData[0] = clinicDoctorList.get(i).getId();
                            workerData[1] = clinicDoctorList.get(i).getName();
                            workerModel.addRow(workerData);
                        }

                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }

                } else {
                    Helper.showMsg("Lütfen doktora poliklinik seçiniz !");
                }
            }
        });
        btn_addWorker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_addWorker.setBounds(266, 267, 179, 28);
        w_clinic.add(btn_addWorker);

        JLabel lbl_selClinicName = new JLabel("Poliklinik Ad\u0131");
        lbl_selClinicName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_selClinicName.setBounds(266, 123, 179, 28);
        w_clinic.add(lbl_selClinicName);

        JButton btn_workerSelect = new JButton("Seç");
        btn_workerSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table_clinic.getSelectedRow();
                if (selRow >= 0) {
                    String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
                    int selClinicId = Integer.parseInt(selClinic);
                    DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
                    clearModel.setRowCount(0);
                    try {
                        ArrayList<User> clinicDoctorList = bashekim.getClinicDoctorList(selClinicId);
                        for (int i = 0; i < clinicDoctorList.size(); i++) {
                            workerData[0] = clinicDoctorList.get(i).getId();
                            workerData[1] = clinicDoctorList.get(i).getName();
                            workerModel.addRow(workerData);
                        }

                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }
                    table_worker.setModel(workerModel);
                } else {
                    Helper.showMsg("Lütfen bir poliklinik seçiniz!");
                }
            }
        });
        btn_workerSelect.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_workerSelect.setBounds(266, 161, 179, 28);
        w_clinic.add(btn_workerSelect);

        JButton btn_delWorker = new JButton("\u00C7\u0131kar");
        btn_delWorker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table_worker.getSelectedRow();
                if (selRow >= 0) {
                    String selDoctor = table_worker.getModel().getValueAt(selRow, 0).toString();
                    int selDoctorId = Integer.parseInt(selDoctor);
                    boolean control = bashekim.deleteWorker(selDoctorId);
                    if (control) {
                        Helper.showMsg("success");
                    } else {
                        Helper.showMsg("fail");
                    }
                }
            }
        });
        btn_delWorker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_delWorker.setBounds(266, 305, 179, 28);
        w_clinic.add(btn_delWorker);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        w_tab.addTab("Randevular", null, panel_1, null);
        panel_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Se\u00E7ili Doktor :");
        lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(524, 5, 177, 19);
        panel_1.add(lblNewLabel_1);

        JScrollPane w_scrollAppoint = new JScrollPane();
        w_scrollAppoint.setBounds(10, 27, 250, 309);
        panel_1.add(w_scrollAppoint);

        table_doctorList = new JTable(doctorListModel);
        w_scrollAppoint.setViewportView(table_doctorList);

        JScrollPane w_scrollAppoint_1 = new JScrollPane();
        w_scrollAppoint_1.setBounds(264, 27, 250, 309);
        panel_1.add(w_scrollAppoint_1);

        table_appointList = new JTable(appointModel);
        w_scrollAppoint_1.setViewportView(table_appointList);

        JLabel lblNewLabel_1_1_2 = new JLabel("Doktor Ad\u0131");
        lblNewLabel_1_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_1_2.setBounds(10, 5, 177, 19);
        panel_1.add(lblNewLabel_1_1_2);

        JLabel lblNewLabel_1_1_3 = new JLabel("Randevu Tarihleri");
        lblNewLabel_1_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_1_3.setBounds(264, 5, 177, 19);
        panel_1.add(lblNewLabel_1_1_3);

        JLabel lblNewLabel_1_1 = new JLabel("Doktor Ad\u0131");
        lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_1.setBounds(526, 27, 177, 19);
        panel_1.add(lblNewLabel_1_1);

        fld_doctorApppoint = new JTextField();
        fld_doctorApppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        fld_doctorApppoint.setEditable(false);
        fld_doctorApppoint.setColumns(10);
        fld_doctorApppoint.setBounds(524, 56, 179, 28);
        panel_1.add(fld_doctorApppoint);

        JLabel lblNewLabel_1_1_1 = new JLabel("Doktor ID");
        lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_1_1.setBounds(526, 86, 177, 19);
        panel_1.add(lblNewLabel_1_1_1);

        fld_doctorAppointId = new JTextField();
        fld_doctorAppointId.setEditable(false);
        fld_doctorAppointId.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        fld_doctorAppointId.setColumns(10);
        fld_doctorAppointId.setBounds(524, 115, 179, 28);
        panel_1.add(fld_doctorAppointId);

        JLabel lblNewLabel_1_2 = new JLabel("Tarih");
        lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_2.setBounds(524, 241, 177, 19);
        panel_1.add(lblNewLabel_1_2);

        fld_appointDate = new JTextField();
        fld_appointDate.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        fld_appointDate.setEditable(false);
        fld_appointDate.setColumns(10);
        fld_appointDate.setBounds(524, 267, 179, 28);
        panel_1.add(fld_appointDate);

        JButton btn_delAppoint = new JButton("Randevuyu Sil");
        btn_delAppoint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = fld_doctorAppointId.getText();
                String date = fld_appointDate.getText();
                if (id.length() != 0 && date.length() != 0) {
                    if (Helper.confirm("sure")) {
                        int selId = Integer.parseInt(id);
                        boolean control = appoint.deleteAppointment(selId, date);
                        if (control) {
                            Helper.showMsg("Randevu baþarýyla silindi.");
                            updateAppointModel(selId);
                            fld_appointDate.setText("");
                        } else {
                            Helper.showMsg("fail");
                        }
                    }
                }
            }
        });
        btn_delAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_delAppoint.setBounds(524, 305, 177, 28);
        panel_1.add(btn_delAppoint);

        JLabel lblNewLabel_1_2_1 = new JLabel("Se\u00E7ili Randevu Bilgileri :");
        lblNewLabel_1_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lblNewLabel_1_2_1.setBounds(524, 216, 177, 19);
        panel_1.add(lblNewLabel_1_2_1);
        
        JButton btn_AppointList = new JButton("Randevular\u0131 Listele");
        btn_AppointList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table_doctorList.getSelectedRow();
                fld_appointDate.setText(null);
                if (row >= 0) {
                    int selDoctorId = (int) table_doctorList.getValueAt(row, 0);
                    DefaultTableModel clearModel = new DefaultTableModel();
                    clearModel = (DefaultTableModel) table_appointList.getModel();
                    clearModel.setRowCount(0);
                    appointData = new Object[3];
                    fld_doctorApppoint.setText(table_doctorList.getValueAt(row, 1).toString());
                    fld_doctorAppointId.setText(String.valueOf(selDoctorId));
                    ArrayList<Appointment> appointList = appoint.getDoctorAppointmentList(selDoctorId);
                    for (int i = 0; i < appointList.size(); i++) {
                        appointData[0] = appointList.get(i).getHastaName();
                        appointData[1] = appointList.get(i).getAppDate();
                        appointModel.addRow(appointData);
                    }
                }
            }
        });

        table_appointList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    int row = (int) table_appointList.getSelectedRow();
                    if (row >= 0) {
                        String date = table_appointList.getValueAt(row, 1).toString();
                        fld_appointDate.setText(date);
                    }
                } catch (ArrayIndexOutOfBoundsException e1) {

                }

            }

        });

        btn_AppointList.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_AppointList.setBounds(524, 153, 177, 28);
        panel_1.add(btn_AppointList);
    }

    public void updateDoctorModel() {//doktor listesini güncelleyen fonksiyon
        DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel(); 
        clearModel.setRowCount(0);
        try {
            ArrayList<User> doctorList = bashekim.getDoctorList();
            for (int i = 0; i < doctorList.size(); i++) {
                doctorData[0] = doctorList.get(i).getId();
                doctorData[1] = doctorList.get(i).getName();
                doctorData[2] = doctorList.get(i).getTcno();
                doctorData[3] = doctorList.get(i).getPassword();
                doctorModel.addRow(doctorData);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void updateClinicModel() {//Klinik listesini güncelleyen fonksiyon
        DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel(); 
        clearModel.setRowCount(0);
        try {
            ArrayList<Clinic> clinicList = clinic.getClinicList();
            for (int i = 0; i < clinicList.size(); i++) {
                clinicData[0] = clinicList.get(i).getId();
                clinicData[1] = clinicList.get(i).getName();
                clinicModel.addRow(clinicData);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateAppointModel(int doctorId) {
        DefaultTableModel clearModel = (DefaultTableModel) table_appointList.getModel();
        clearModel.setRowCount(0);
        ArrayList<Appointment> appointList = appoint.getDoctorAppointmentList(doctorId);
        for (int i = 0; i < appointList.size(); i++) {
            appointData[0] = appointList.get(i).getHastaName();
            appointData[1] = appointList.get(i).getAppDate();
            appointModel.addRow(appointData);
        }
        table_appointList.setModel(appointModel);

    }
}
