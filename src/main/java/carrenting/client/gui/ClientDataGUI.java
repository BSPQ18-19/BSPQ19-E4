package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

public class ClientDataGUI extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroupClient = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientDataGUI frame = new ClientDataGUI();
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
	public ClientDataGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 357, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPersonalDetails = new JLabel("Personal details");
		lblPersonalDetails.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblPersonalDetails.setBounds(83, 25, 143, 24);
		contentPane.add(lblPersonalDetails);
		
		JRadioButton rdbtnPerson = new JRadioButton("Person");
		rdbtnPerson.setSelected(true);
		buttonGroupClient.add(rdbtnPerson);
		rdbtnPerson.setBounds(92, 67, 59, 23);
		contentPane.add(rdbtnPerson);
		
		JRadioButton rdbtnCompany = new JRadioButton("Company");
		buttonGroupClient.add(rdbtnCompany);
		rdbtnCompany.setBounds(174, 67, 109, 23);
		contentPane.add(rdbtnCompany);
		
		JLabel lblIAmA = new JLabel("I am a:");
		lblIAmA.setBounds(38, 71, 46, 14);
		contentPane.add(lblIAmA);
		
		JPanel panelPerson = new JPanel();
		panelPerson.setBounds(23, 96, 286, 290);
		contentPane.add(panelPerson);
		panelPerson.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 11, 46, 14);
		panelPerson.add(lblName);
		
		textField = new JTextField();
		textField.setBounds(66, 8, 149, 20);
		panelPerson.add(textField);
		textField.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 39, 46, 14);
		panelPerson.add(lblSurname);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(66, 36, 149, 20);
		panelPerson.add(textField_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(92, 67, 95, 20);
		panelPerson.add(dateChooser);
		
		JLabel lblDateOfBirth = new JLabel("Date of birth");
		lblDateOfBirth.setBounds(10, 73, 61, 14);
		panelPerson.add(lblDateOfBirth);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 98, 46, 14);
		panelPerson.add(lblAddress);
		
		textField_2 = new JTextField();
		textField_2.setBounds(66, 95, 149, 20);
		panelPerson.add(textField_2);
		textField_2.setColumns(10);
	}
}
