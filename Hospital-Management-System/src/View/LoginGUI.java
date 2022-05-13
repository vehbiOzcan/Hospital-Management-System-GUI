package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;
import javax.swing.JList;

public class LoginGUI extends JFrame {

    private JPanel w_pane;
    private JTextField fld_hastaTc;
    private JTextField fld_doctorTc;
    private JPasswordField fld_doctorPass;
    private JPasswordField fld_hastaPass;
    private DbConnection conn = new DbConnection();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginGUI frame = new LoginGUI();
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
    public LoginGUI() {
        setResizable(false);
        setTitle("Hastane Y\u00F6netim Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 465);
        w_pane = new JPanel();
        w_pane.setBackground(Color.WHITE);
        w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(w_pane);
        w_pane.setLayout(null);

        JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("/hastaneLogo.png")));
        lbl_logo.setBounds(173, 10, 128, 89);
        w_pane.add(lbl_logo);

        JLabel lblNewLabel = new JLabel("Hastane Y\u00F6netim Sistemi");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
        lblNewLabel.setBounds(48, 84, 377, 43);
        w_pane.add(lblNewLabel);

        JTabbedPane w_tabbPane = new JTabbedPane(JTabbedPane.TOP);
        w_tabbPane.setBounds(10, 137, 466, 266);
        w_pane.add(w_tabbPane);

        JPanel w_hastaLogin = new JPanel();
        w_hastaLogin.setBackground(Color.WHITE);
        w_tabbPane.addTab("Hasta Giriþi", null, w_hastaLogin, null);
        w_hastaLogin.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("T.C. Numaran\u0131z:");
        lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        lblNewLabel_1.setBounds(25, 31, 156, 35);
        w_hastaLogin.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("\u015Eifre:");
        lblNewLabel_1_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        lblNewLabel_1_1.setBounds(25, 85, 156, 35);
        w_hastaLogin.add(lblNewLabel_1_1);

        fld_hastaTc = new JTextField();
        fld_hastaTc.setFont(new Font("Tahoma", Font.PLAIN, 15));
        fld_hastaTc.setBounds(191, 31, 248, 35);
        w_hastaLogin.add(fld_hastaTc);
        fld_hastaTc.setColumns(10);

        JButton btn_register = new JButton("Kay\u0131t Ol");
        btn_register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegisterGUI register = new RegisterGUI();
                register.setVisible(true);
                dispose();
            }
        });
        btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
        btn_register.setBounds(25, 163, 200, 46);
        w_hastaLogin.add(btn_register);

        JButton btn_hastaLogin = new JButton("Giri\u015F Yap");
        btn_hastaLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fld_hastaTc.getText().length() == 0 || fld_hastaPass.getPassword().length == 0) {
                    Helper.showMsg("fillup");
                } else {
                    boolean key = true;
                    try {
                        Connection connect = conn.connectDb();
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM user");
                        System.out.println("Baðlantý saðlandý");

                        while (rs.next()) {
                            if (fld_hastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password")) && rs.getString("type").equals("hasta")) {
                                Hasta hasta = new Hasta(
                                        rs.getInt("id"),
                                        rs.getString("tcno"),
                                        rs.getString("name"),
                                        rs.getString("password"),
                                        rs.getString("type"));

                                System.out.println(hasta.getName());
                                HastaGUI hGUI = new HastaGUI(hasta);
                                hGUI.setVisible(true);
                                dispose();
                                key = false;
                            }

                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    if (key) {
                        Helper.showMsg("Böyle bir hasta bulunamadý. Lütfen kayýt olunuz !");
                    }
                }
            }
        });
        btn_hastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
        btn_hastaLogin.setBounds(239, 163, 200, 46);
        w_hastaLogin.add(btn_hastaLogin);

        fld_hastaPass = new JPasswordField();
        fld_hastaPass.setBounds(191, 85, 248, 35);
        w_hastaLogin.add(fld_hastaPass);

        JPanel w_doctorLogin = new JPanel();
        w_doctorLogin.setLayout(null);
        w_doctorLogin.setBackground(Color.WHITE);
        w_tabbPane.addTab("Doktor Giriþi", null, w_doctorLogin, null);

        JLabel lblNewLabel_1_2 = new JLabel("T.C. Numaran\u0131z:");
        lblNewLabel_1_2.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        lblNewLabel_1_2.setBounds(25, 31, 156, 35);
        w_doctorLogin.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_1_1 = new JLabel("\u015Eifre:");
        lblNewLabel_1_1_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        lblNewLabel_1_1_1.setBounds(25, 85, 156, 35);
        w_doctorLogin.add(lblNewLabel_1_1_1);

        fld_doctorTc = new JTextField();
        fld_doctorTc.setFont(new Font("Tahoma", Font.PLAIN, 15));
        fld_doctorTc.setColumns(10);
        fld_doctorTc.setBounds(191, 31, 248, 35);
        w_doctorLogin.add(fld_doctorTc);

        JButton btn_doctorLogin = new JButton("Giri\u015F Yap");
        btn_doctorLogin.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                if (fld_doctorTc.getText().length() == 0 || fld_doctorPass.getPassword().length == 0) {
                    Helper.showMsg("fillup");
                } else {
                    try {
                        Connection connect = conn.connectDb();
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM user");
                        System.out.println("Baðlantý saðlandý");
                        while (rs.next()) {
                            if (fld_doctorTc.getText().equals(rs.getString("tcno")) && fld_doctorPass.getText().equals(rs.getString("password")) && rs.getString("type").equals("bashekim")) {
                                Bashekim bhekim = new Bashekim(
                                        rs.getInt("id"),
                                        rs.getString("tcno"),
                                        rs.getString("name"),
                                        rs.getString("password"),
                                        rs.getString("type"));

                                System.out.println(bhekim.getName());
                                BashekimGUI bGUI = new BashekimGUI(bhekim);
                                bGUI.setVisible(true);
                                dispose();
                            }
                            if (fld_doctorTc.getText().equals(rs.getString("tcno")) && fld_doctorPass.getText().equals(rs.getString("password")) && rs.getString("type").equals("doktor")) {
                                Doctor doctor = new Doctor(
                                        rs.getInt("id"),
                                        rs.getString("tcno"),
                                        rs.getString("name"),
                                        rs.getString("password"),
                                        rs.getString("type"));

                                System.out.println(doctor.getName());
                                DoctorGUI dGUI = new DoctorGUI(doctor);
                                dGUI.setVisible(true);
                                dispose();
                            }

                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btn_doctorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
        btn_doctorLogin.setBounds(25, 166, 414, 46);
        w_doctorLogin.add(btn_doctorLogin);

        fld_doctorPass = new JPasswordField();
        fld_doctorPass.setBounds(191, 85, 248, 35);
        w_doctorLogin.add(fld_doctorPass);

        JButton btn_exit = new JButton("\u00C7\u0131k\u0131\u015F");
        btn_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
                dispose();
            }
        });
        btn_exit.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_exit.setBounds(10, 10, 75, 23);
        w_pane.add(btn_exit);

        JLabel lblNewLabel_2 = new JLabel("\u00A9 2022 developed by Vehbi Ozcan");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(10, 405, 466, 23);
        w_pane.add(lblNewLabel_2);
    }
}
