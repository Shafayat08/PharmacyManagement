import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

public class dashboard extends JFrame {

	public JPanel contentPane;
	public JTextField textName;
	public JTextField textCompany;
	public JTextField textPrice;
	public JTextField textAmount;
	public JTextField textProID;
	public JTable table;

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
	
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from product");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public dashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 681, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblChemPherma = new JLabel("Chem Pharma");
		lblChemPherma.setBounds(20, 11, 635, 42);
		lblChemPherma.setFont(new Font("Ebrima", Font.BOLD, 26));
		lblChemPherma.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblChemPherma);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 64, 323, 194);
		panel.setBorder(new TitledBorder(null, "Add Product", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Arial", Font.BOLD, 14));
		lblName.setBounds(21, 35, 98, 22);
		panel.add(lblName);
		
		JLabel lblCompany = new JLabel("Company");
		lblCompany.setFont(new Font("Arial", Font.BOLD, 14));
		lblCompany.setBounds(21, 71, 98, 22);
		panel.add(lblCompany);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Arial", Font.BOLD, 14));
		lblPrice.setBounds(21, 104, 62, 22);
		panel.add(lblPrice);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Arial", Font.BOLD, 14));
		lblAmount.setBounds(21, 137, 62, 22);
		panel.add(lblAmount);
		
		textName = new JTextField();
		textName.setBounds(144, 37, 155, 20);
		panel.add(textName);
		textName.setColumns(10);
		
		textCompany = new JTextField();
		textCompany.setColumns(10);
		textCompany.setBounds(144, 73, 155, 20);
		panel.add(textCompany);
		
		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(144, 106, 155, 20);
		panel.add(textPrice);
		
		textAmount = new JTextField();
		textAmount.setColumns(10);
		textAmount.setBounds(144, 139, 155, 20);
		panel.add(textAmount);
		
		
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(139, 269, 91, 39);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textName.setText("");
				textCompany.setText("");
				textPrice.setText("");
				textAmount.setText("");
				textProID.setText("");
				
				textName.requestFocus();
			}
		});
		btnClear.setFont(new Font("Calibri", Font.BOLD, 14));
		getContentPane().add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(252, 269, 91, 39);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Calibri", Font.BOLD, 14));
		getContentPane().add(btnExit);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(20, 269, 91, 39);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String medName, medCompany, medPrice, medAmount;
				
				medName = textName.getText();
				medCompany = textCompany.getText();
				medPrice = textPrice.getText();
				medAmount = textAmount.getText();
				
				try {
					pst = con.prepareStatement("insert into product(name,company,price,amount)values(?,?,?,?)");
					pst.setString(1, medName);
					pst.setString(2, medCompany);
					pst.setString(3, medPrice);
					pst.setString(4, medAmount);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Product Added");
					table_load();
					
					textName.setText("");
					textCompany.setText("");
					textPrice.setText("");
					textAmount.setText("");
					
					textName.requestFocus();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSave.setFont(new Font("Calibri", Font.BOLD, 14));
		getContentPane().add(btnSave);
		
		JPanel panelSearch = new JPanel();
		panelSearch.setBounds(20, 319, 323, 48);
		panelSearch.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelSearch);
		panelSearch.setLayout(null);
		
		JLabel lblProID = new JLabel("Product ID");
		lblProID.setBounds(10, 18, 124, 17);
		lblProID.setFont(new Font("Arial", Font.BOLD, 14));
		panelSearch.add(lblProID);
		
		textProID = new JTextField();
		textProID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = textProID.getText();
					
					pst = con.prepareStatement("select name,company,price,amount from product where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==true) {
						String name = rs.getString(1);
						String company = rs.getString(2);
						String price = rs.getString(3);
						String amount = rs.getString(4);
						
						textName.setText(name);
						textCompany.setText(company);
						textPrice.setText(price);
						textAmount.setText(amount);
					}
					else {
						textName.setText("");
						textCompany.setText("");
						textPrice.setText("");
						textAmount.setText("");
					}
				}
				catch(SQLException ex) {
					
				}
			}
		});
		textProID.setBounds(144, 17, 155, 20);
		textProID.setColumns(10);
		panelSearch.add(textProID);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(564, 328, 91, 39);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String medID;
				medID = textProID.getText();
				
				try {
					pst = con.prepareStatement("delete from product where id = ?");
					
					pst.setString(1, medID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Product Updated");
					table_load();
					
					textName.setText("");
					textCompany.setText("");
					textPrice.setText("");
					textAmount.setText("");
					
					textName.requestFocus();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Calibri", Font.BOLD, 14));
		getContentPane().add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(463, 328, 91, 39);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String medID, medName, medCompany, medPrice, medAmount;
				
				medName = textName.getText();
				medCompany = textCompany.getText();
				medPrice = textPrice.getText();
				medAmount = textAmount.getText();
				medID = textProID.getText();
				
				try {
					pst = con.prepareStatement("Update product set name = ?,company = ?,price = ?,amount = ? where id = ?");
					pst.setString(1, medName);
					pst.setString(2, medCompany);
					pst.setString(3, medPrice);
					pst.setString(4, medAmount);
					pst.setString(5, medID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Product Updated");
					table_load();
					
					textName.setText("");
					textCompany.setText("");
					textPrice.setText("");
					textAmount.setText("");
					
					textName.requestFocus();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Calibri", Font.BOLD, 14));
		getContentPane().add(btnUpdate);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(361, 69, 294, 248);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
	}
}
