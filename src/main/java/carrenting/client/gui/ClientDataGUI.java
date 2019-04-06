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

import javax.swing.JButton;

public class ClientDataGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private final ButtonGroup buttonGroupClient = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_8;
	private JTextField textField_10;
	private JTextField textField_11;

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
	public ClientDataGUI(Controller controller) throws RemoteException{
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
		frame.setBounds(100, 100, 361, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPersonalDetails = new JLabel("Personal details");
		lblPersonalDetails.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblPersonalDetails.setBounds(98, 23, 143, 24);
		contentPane.add(lblPersonalDetails);
		
		JRadioButton rdbtnPerson = new JRadioButton("Person");
		rdbtnPerson.setSelected(true);
		if (rdbtnPerson.isSelected()){
			contentPane.add(lblPersonalDetails);
		}
		buttonGroupClient.add(rdbtnPerson);
		rdbtnPerson.setBounds(91, 54, 59, 23);
		contentPane.add(rdbtnPerson);
		
		JRadioButton rdbtnCompany = new JRadioButton("Company");
		buttonGroupClient.add(rdbtnCompany);
		rdbtnCompany.setBounds(173, 54, 109, 23);
		contentPane.add(rdbtnCompany);
		
		JLabel lblIAmA = new JLabel("I am a:");
		lblIAmA.setBounds(37, 58, 46, 14);
		contentPane.add(lblIAmA);
		
		JPanel panelPerson = new JPanel();
		panelPerson.setBounds(25, 97, 286, 255);
		contentPane.add(panelPerson);
		panelPerson.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 8, 46, 14);
		panelPerson.add(lblName);
		
		textField = new JTextField();
		textField.setBounds(81, 5, 149, 20);
		panelPerson.add(textField);
		textField.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 36, 46, 14);
		panelPerson.add(lblSurname);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(81, 33, 149, 20);
		panelPerson.add(textField_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(107, 64, 95, 20);
		panelPerson.add(dateChooser);
		
		JLabel lblDateOfBirth = new JLabel("Date of birth");
		lblDateOfBirth.setBounds(10, 70, 61, 14);
		panelPerson.add(lblDateOfBirth);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 98, 46, 14);
		panelPerson.add(lblAddress);
		
		textField_2 = new JTextField();
		textField_2.setBounds(81, 92, 149, 20);
		panelPerson.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 129, 46, 14);
		panelPerson.add(lblId);
		
		textField_3 = new JTextField();
		textField_3.setBounds(81, 126, 121, 20);
		panelPerson.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(10, 154, 61, 14);
		panelPerson.add(lblPhone);
		
		textField_4 = new JTextField();
		textField_4.setBounds(81, 151, 121, 20);
		panelPerson.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblEmailAddress = new JLabel("Email \r\naddress");
		lblEmailAddress.setBounds(10, 182, 74, 14);
		panelPerson.add(lblEmailAddress);
		
		textField_5 = new JTextField();
		textField_5.setBounds(81, 179, 121, 20);
		panelPerson.add(textField_5);
		textField_5.setColumns(10);
		
		JButton button = new JButton("Next");
		button.setBounds(202, 221, 74, 23);
		panelPerson.add(button);
		
		JPanel panelCompany = new JPanel();
		panelCompany.setBounds(0, 0, 259, 163);
		panelPerson.add(panelCompany);
		panelCompany.setLayout(null);
		
		JLabel label = new JLabel("Name");
		label.setBounds(10, 8, 46, 14);
		panelCompany.add(label);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(81, 5, 149, 20);
		panelCompany.add(textField_6);
		
		JLabel label_3 = new JLabel("Address");
		label_3.setBounds(10, 39, 46, 14);
		panelCompany.add(label_3);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(81, 36, 149, 20);
		panelCompany.add(textField_8);
		
		JLabel label_5 = new JLabel("Phone");
		label_5.setBounds(10, 70, 61, 14);
		panelCompany.add(label_5);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(81, 67, 121, 20);
		panelCompany.add(textField_10);
		
		JLabel label_6 = new JLabel("Email \r\naddress");
		label_6.setBounds(10, 101, 74, 14);
		panelCompany.add(label_6);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(81, 98, 121, 20);
		panelCompany.add(textField_11);
		
		JButton btnNext = new JButton("Next");
		btnNext.setBounds(175, 129, 74, 23);
		panelCompany.add(btnNext);
	}
}
