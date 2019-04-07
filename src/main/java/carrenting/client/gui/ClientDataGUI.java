package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.rmi.RemoteException;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientDataGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private Rent rent;
	private final ButtonGroup buttonGroupClient = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField personID;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField companyID;
	private JTextField companyAddress;
	private JTextField companyPhone;
	private JTextField emailCompany;
	private JPanel panelCompany = new JPanel();
	private JPanel panelPerson = new JPanel();

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ClientDataGUI frame = new ClientDataGUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	public ClientDataGUI(Controller controller,Rent rent) throws RemoteException{
		this.controller=controller;
		initialize();
		frame.setVisible(true);
		
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public void initialize() throws RemoteException {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 378, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		panelCompany.setVisible(false);
		
		JLabel lblPersonalDetails = new JLabel("Personal details");
		lblPersonalDetails.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblPersonalDetails.setBounds(98, 23, 143, 24);
		contentPane.add(lblPersonalDetails);
		
		JRadioButton rdbtnPerson = new JRadioButton("Person");
		rdbtnPerson.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelCompany.setVisible(false);
				panelPerson.setVisible(true);
				frame.setBounds(100, 100, 378, 504);
			}
		});
		rdbtnPerson.setSelected(true);
		if (rdbtnPerson.isSelected()){
			contentPane.add(lblPersonalDetails);
		}
		buttonGroupClient.add(rdbtnPerson);
		rdbtnPerson.setBounds(91, 54, 80, 23);
		contentPane.add(rdbtnPerson);
		
		JRadioButton rdbtnCompany = new JRadioButton("Company");
		rdbtnCompany.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelPerson.setVisible(false);
				panelCompany.setVisible(true);
				frame.setBounds(100, 100, 378, 400);
				
			}
		});
		buttonGroupClient.add(rdbtnCompany);
		rdbtnCompany.setBounds(173, 54, 109, 23);
		contentPane.add(rdbtnCompany);
		
		JLabel lblIAmA = new JLabel("I am a:");
		lblIAmA.setBounds(37, 58, 46, 14);
		contentPane.add(lblIAmA);
		
		
		panelCompany.setLayout(null);
		panelCompany.setBounds(25, 97, 331, 226);
		contentPane.add(panelCompany);
		
		JLabel label = new JLabel("Name");
		label.setBounds(10, 8, 85, 14);
		panelCompany.add(label);
		
		companyID = new JTextField();
		companyID.setColumns(10);
		companyID.setBounds(125, 5, 149, 20);
		panelCompany.add(companyID);
		
		JLabel label_1 = new JLabel("Address");
		label_1.setBounds(10, 39, 85, 14);
		panelCompany.add(label_1);
		
		companyAddress = new JTextField();
		companyAddress.setColumns(10);
		companyAddress.setBounds(125, 36, 149, 20);
		panelCompany.add(companyAddress);
		
		JLabel label_2 = new JLabel("Phone");
		label_2.setBounds(10, 70, 85, 14);
		panelCompany.add(label_2);
		
		companyPhone = new JTextField();
		companyPhone.setColumns(10);
		companyPhone.setBounds(125, 67, 121, 20);
		panelCompany.add(companyPhone);
		
		JLabel label_3 = new JLabel("Email \r\naddress");
		label_3.setBounds(10, 101, 105, 14);
		panelCompany.add(label_3);
		
		emailCompany = new JTextField();
		emailCompany.setColumns(10);
		emailCompany.setBounds(125, 98, 121, 20);
		panelCompany.add(emailCompany);
		
		JButton nextCompany = new JButton("Next");
		nextCompany.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rent.setUserId(companyID.getText());
			}
		});
		nextCompany.setBounds(185, 146, 74, 23);
		panelCompany.add(nextCompany);
		panelPerson.setBounds(25, 97, 331, 357);
		contentPane.add(panelPerson);
		panelPerson.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 8, 61, 14);
		panelPerson.add(lblName);
		
		textField = new JTextField();
		textField.setBounds(118, 8, 149, 20);
		panelPerson.add(textField);
		textField.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 36, 74, 14);
		panelPerson.add(lblSurname);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(118, 36, 149, 20);
		panelPerson.add(textField_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(118, 64, 95, 20);
		panelPerson.add(dateChooser);
		
		JLabel lblDateOfBirth = new JLabel("Date of birth");
		lblDateOfBirth.setBounds(10, 70, 95, 14);
		panelPerson.add(lblDateOfBirth);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 98, 61, 14);
		panelPerson.add(lblAddress);
		
		textField_2 = new JTextField();
		textField_2.setBounds(118, 98, 149, 20);
		panelPerson.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 129, 46, 14);
		panelPerson.add(lblId);
		
		personID = new JTextField();
		personID.setBounds(118, 129, 121, 20);
		panelPerson.add(personID);
		personID.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(10, 154, 74, 14);
		panelPerson.add(lblPhone);
		
		textField_4 = new JTextField();
		textField_4.setBounds(118, 154, 121, 20);
		panelPerson.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblEmailAddress = new JLabel("Email \r\naddress");
		lblEmailAddress.setBounds(10, 182, 118, 14);
		panelPerson.add(lblEmailAddress);
		
		textField_5 = new JTextField();
		textField_5.setBounds(118, 185, 121, 20);
		panelPerson.add(textField_5);
		textField_5.setColumns(10);
		
		JButton button = new JButton("Next");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rent.setUserId(personID.getText());
			}
		});
		button.setBounds(247, 312, 74, 23);
		panelPerson.add(button);
	}
}
