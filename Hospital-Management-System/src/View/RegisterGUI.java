package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Helper.Helper;
import Model.Hasta;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JLabel lbl;
	private JLabel lblifre;
	private JPasswordField fld_pass;
	private JPasswordField fld_passAgain;
	private Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setTitle("Hasta Kay\u0131t");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 406);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_1 = new JLabel("Ad Soyad");
		lbl_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_1.setBounds(10, 10, 209, 28);
		w_pane.add(lbl_1);
		
		fld_name = new JTextField();
		fld_name.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		fld_name.setColumns(10);
		fld_name.setBounds(10, 36, 266, 28);
		w_pane.add(fld_name);
		
		fld_tcno = new JTextField();
		fld_tcno.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 100, 266, 28);
		w_pane.add(fld_tcno);
		
		lbl = new JLabel("T.C. Kimlik Numaras\u0131");
		lbl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl.setBounds(10, 74, 209, 28);
		w_pane.add(lbl);
		
		lblifre = new JLabel("\u015Eifre");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblifre.setBounds(10, 138, 209, 28);
		w_pane.add(lblifre);
		
		fld_pass = new JPasswordField();
		fld_pass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fld_pass.setEchoChar('*');
		fld_pass.setBounds(10, 163, 266, 28);
		w_pane.add(fld_pass);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_name.getText().length() == 0 ||  fld_tcno.getText().length() == 0 || fld_pass.getText().length() == 0 || fld_passAgain.getText().length() == 0 ) {
					Helper.showMsg("fillup");
				}else{
					if(fld_pass.getText().equals(fld_passAgain.getText())) {
						boolean control = hasta.register(fld_name.getText(),fld_pass.getText(),fld_tcno.getText());
						if(control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();
						}else {
							Helper.showMsg("fail");
						}
					}else {
						Helper.showMsg("Şifreler aynı değil!");
					}
				}
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btn_register.setBounds(10, 293, 266, 28);
		w_pane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri Dön");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_backto.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btn_backto.setBounds(10, 331, 266, 28);
		w_pane.add(btn_backto);
		
		JLabel lblifre_1 = new JLabel("Şifre Tekrar");
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblifre_1.setBounds(10, 201, 209, 28);
		w_pane.add(lblifre_1);
		
		fld_passAgain = new JPasswordField();
		fld_passAgain.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fld_passAgain.setEchoChar('*');
		fld_passAgain.setBounds(10, 226, 266, 28);
		w_pane.add(fld_passAgain);
		
		JCheckBox cb_viewPass = new JCheckBox("\u015Eifreyi G\u00F6ster");
		cb_viewPass.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(cb_viewPass.isSelected()) {
					fld_pass.setEchoChar((char)0);
					fld_passAgain.setEchoChar((char)0);
				}else {
					fld_pass.setEchoChar('*');
					fld_passAgain.setEchoChar('*');
				}
				
			}
			
		});
		cb_viewPass.setForeground(Color.GRAY);
		cb_viewPass.setBackground(Color.WHITE);
		cb_viewPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cb_viewPass.setBounds(10, 260, 119, 28);
		w_pane.add(cb_viewPass);
	}
}
