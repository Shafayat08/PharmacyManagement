import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.sql.*;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField jtfUserName;
	private JTextField jtfPassword;


	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbluserName = new JLabel("Username :");
		lbluserName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbluserName.setBounds(32, 37, 115, 25);
		contentPane.add(lbluserName);
		
		jtfUserName = new JTextField();
		jtfUserName.setBounds(152, 41, 222, 20);
		contentPane.add(jtfUserName);
		jtfUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(32, 104, 115, 25);
		contentPane.add(lblPassword);
		
		jtfPassword = new JTextField();
		jtfPassword.setColumns(10);
		jtfPassword.setBounds(152, 108, 222, 20);
		contentPane.add(jtfPassword);
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							
							try {
								Class.forName("com.mysql.jdbc.Driver");
								Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dashboard","root","");
								Statement stmt = con.createStatement();
								
								String dbUsername = jtfUserName.getText();
								String dbPassword = jtfPassword.getText();
								
								String sql = "select * from user where UserName='"+jtfUserName.getText()+"'and password='"+jtfPassword.getText().toString()+"'";
								ResultSet rs=stmt.executeQuery(sql);
								if(rs.next()) {
									try {
										dashboard frame = new dashboard();
										frame.setVisible(true);
										frame.Connect();
										frame.table_load();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}else {
									JOptionPane.showMessageDialog(null, "Incorrect username or password..");
							}
							con.close();
							} catch(Exception e) {
							System.out.print(e);
							}
							
							
							
						}
					});
				}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setBounds(328, 221, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnSignup = new JButton("Signup");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							signup sg = new signup();
							sg.Connect();
							sg.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnSignup.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSignup.setBounds(212, 221, 89, 23);
		contentPane.add(btnSignup);
	}

}
