import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class signup extends JFrame {

	public JPanel contentPane;
	public JTextField textFullname;
	public JTextField textUsername;
	public JPasswordField passwordField;
	public JPasswordField passwordFieldConf;

	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/dashboard","root","");
			
		}
		catch(ClassNotFoundException ex){
			
		}
		catch(SQLException ex) {
			
		}
	}
	
	public void userTable_load() {
		try {
			pst = con.prepareStatement("select * from user");
			rs = pst.executeQuery();
//			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public signup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 438, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSignUp = new JLabel("Registration Form");
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblSignUp.setBounds(10, 11, 402, 41);
		contentPane.add(lblSignUp);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 63, 402, 209);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblFullname = new JLabel("Full Name");
		lblFullname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFullname.setBounds(26, 27, 115, 20);
		panel.add(lblFullname);
		
		textFullname = new JTextField();
		textFullname.setBounds(177, 27, 215, 20);
		panel.add(textFullname);
		textFullname.setColumns(10);
		
		JLabel lblUsername = new JLabel("User Name");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(26, 72, 115, 20);
		panel.add(lblUsername);
		
		textUsername = new JTextField();
		textUsername.setColumns(10);
		textUsername.setBounds(177, 72, 215, 20);
		panel.add(textUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(26, 120, 115, 20);
		panel.add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblConfirmPassword.setBounds(26, 164, 133, 20);
		panel.add(lblConfirmPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(177, 120, 215, 20);
		panel.add(passwordField);
		
		passwordFieldConf = new JPasswordField();
		passwordFieldConf.setBounds(177, 164, 215, 20);
		panel.add(passwordFieldConf);
		
		JButton btnSignup = new JButton("Signup");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String fullName, userName, passWord;
				
				fullName = textFullname.getText();
				userName = textUsername.getText();
				passWord = passwordField.getText();
				
				
				try {
					pst = con.prepareStatement("insert into user(name,username,password)values(?,?,?)");
					pst.setString(1, fullName);
					pst.setString(2, userName);
					pst.setString(3, passWord);
					
					signup sg = new signup();
					
					
					pst.executeUpdate();
					
					userTable_load();
					JOptionPane.showMessageDialog(null, "Signup Complete");
					
					textFullname.setText("");
					textUsername.setText("");
					passwordField.setText("");
					passwordFieldConf.setText("");

					textFullname.requestFocus();
					
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSignup.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSignup.setBounds(311, 275, 89, 41);
		contentPane.add(btnSignup);
	}
}
