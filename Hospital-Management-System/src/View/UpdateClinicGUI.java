package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Clinic;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateClinicGUI extends JFrame {

    private JPanel contentPane;
    private JTextField fld_updateClinicName;
    private static Clinic clinic;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
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
    public UpdateClinicGUI(Clinic clinic) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 263, 150);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbl_1_1 = new JLabel("Poliklinik Ad\u0131");
        lbl_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        lbl_1_1.setBounds(10, 10, 230, 28);
        contentPane.add(lbl_1_1);

        fld_updateClinicName = new JTextField();
        fld_updateClinicName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
        fld_updateClinicName.setColumns(10);
        fld_updateClinicName.setBounds(10, 42, 230, 28);
        fld_updateClinicName.setText(clinic.getName());
        contentPane.add(fld_updateClinicName);

        JButton btn_updateClinic = new JButton("D\u00FCzenle");
        btn_updateClinic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("sure")) {
                    try {
                        clinic.updateClinic(clinic.getId(), fld_updateClinicName.getText());
                        Helper.showMsg("success");
                        dispose();
                    } catch (Exception e2) {
                        System.out.println(e2.getMessage());
                    }

                }
            }
        });
        btn_updateClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
        btn_updateClinic.setBounds(10, 74, 230, 28);
        contentPane.add(btn_updateClinic);
    }

}
