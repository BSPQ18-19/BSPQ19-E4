package carrenting.client.gui;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;
import java.awt.Color;


@SuppressWarnings("serial")
public class ClientDataGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private Rent rent;
	private final ButtonGroup buttonGroupClient = new ButtonGroup();
	private JTextField personName;
	private JTextField personSurname;
	private JTextField personAddress;
	private JTextField personID;
	private JTextField personPhone;
	private JTextField personEmail;
	private JTextField companyID;
	private JTextField companyAddress;
	private JTextField companyPhone;
	private JTextField emailCompany;
	private JPanel panelCompany = new JPanel();
	private JPanel panelPerson = new JPanel();
	private String clientType="person";
	private JDateChooser dateChooserBirth = new JDateChooser();



	public ClientDataGUI(Controller controller,Rent rent) throws RemoteException{
		this.controller=controller;
		this.rent=rent;
		initialize();
		frame.setVisible(true);
		
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public void initialize() throws RemoteException {
		frame=new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 481, 416);
		contentPane = new JPanel();
		contentPane.setBackground(Color.decode(controller.getBackgroundColor()));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton buttonNext = new JButton(controller.getResourcebundle().getString("next"));
		buttonNext.setBounds(295, 346, 160, 23);
		contentPane.add(buttonNext);
		buttonNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(clientType.equalsIgnoreCase("person")) {
					if(personName.getText().equals("") || personSurname.getText().equals("")|| personAddress.getText().equals("")||
							personID.getText().equals("")|| personPhone.getText().equals("")|| personEmail.getText().equals("")|| 
							dateChooserBirth.getDate()==(null)) {
						JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("all_fields_filled"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				
					}
					else {
						rent.setUserId(personID.getText());
						frame.dispose();
						controller.getLogger().debug(rent.toString());
						new PaymentGUI(controller, rent);
					}
				}
				if(clientType.equalsIgnoreCase("company")) {
					if(companyID.getText().equals("") || companyAddress.getText().equals("")||companyPhone.getText().equals("")||
							emailCompany.getText().equals("")){					
						JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("all_fields_filled"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					}else {
						rent.setUserId(companyID.getText());
						frame.dispose();
						new PaymentGUI(controller, rent);
						controller.getLogger().debug(rent.toString());
					}	
				}
			}
		});
		
		JLabel lblPersonalDetails = new JLabel(controller.getResourcebundle().getString("personal_details"));
		lblPersonalDetails.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblPersonalDetails.setBounds(154, 23, 168, 24);
		contentPane.add(lblPersonalDetails);
		
		JRadioButton rdbtnPerson = new JRadioButton(controller.getResourcebundle().getString("person"));
		rdbtnPerson.setBackground(Color.decode(controller.getBackgroundColor()));
		rdbtnPerson.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelCompany.setVisible(false);
				panelPerson.setVisible(true);
				frame.setBounds(100, 100, 481, 416);
				buttonNext.setBounds(295, 346, 160, 23);
				clientType="person";
				
			
			}
		});
		rdbtnPerson.setSelected(true);
		if (rdbtnPerson.isSelected()){
			contentPane.add(lblPersonalDetails);
		}
		buttonGroupClient.add(rdbtnPerson);
		rdbtnPerson.setBounds(131, 54, 124, 23);
		contentPane.add(rdbtnPerson);
		
		JRadioButton rdbtnCompany = new JRadioButton(controller.getResourcebundle().getString("company"));
		rdbtnCompany.setBackground(Color.decode(controller.getBackgroundColor()));
		rdbtnCompany.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelPerson.setVisible(false);
				panelCompany.setVisible(true);
				frame.setBounds(100, 100, 481, 319);
				buttonNext.setBounds(264, 232, 160, 23);
				clientType="company";	
			}
		});
		buttonGroupClient.add(rdbtnCompany);
		rdbtnCompany.setBounds(257, 54, 112, 23);
		contentPane.add(rdbtnCompany);
		
		JLabel lblIAmA = new JLabel(controller.getResourcebundle().getString("i_am_an"));
		lblIAmA.setBounds(37, 58, 88, 14);
		contentPane.add(lblIAmA);
		panelPerson.setBackground(Color.decode(controller.getBackgroundColor()));
		panelPerson.setBounds(25, 97, 434, 238);
		contentPane.add(panelPerson);
		panelPerson.setLayout(null);
		
		JLabel lblName = new JLabel(controller.getResourcebundle().getString("name"));
		lblName.setBounds(10, 11, 160, 14);
		panelPerson.add(lblName);
		
		personName = new JTextField();
		personName.setBounds(200, 11, 149, 20);
		panelPerson.add(personName);
		personName.setColumns(10);
		
		JLabel lblSurname = new JLabel(controller.getResourcebundle().getString("surname"));
		lblSurname.setBounds(10, 36, 180, 14);
		panelPerson.add(lblSurname);
		
		personSurname = new JTextField();
		personSurname.setColumns(10);
		personSurname.setBounds(200, 39, 149, 20);
		panelPerson.add(personSurname);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		calendar.add(Calendar.YEAR, -18);
		Date date = new Date(calendar.getTimeInMillis());
		//dateChooserFinish.setMaxSelectableDate(date);
		dateChooserBirth.setMaxSelectableDate(date);
		
		dateChooserBirth.setBounds(200, 67, 149, 20);
		panelPerson.add(dateChooserBirth);
		
		JLabel lblDateOfBirth = new JLabel(controller.getResourcebundle().getString("date_birth"));
		lblDateOfBirth.setBounds(10, 70, 149, 14);
		panelPerson.add(lblDateOfBirth);
		
		JLabel lblAddress = new JLabel(controller.getResourcebundle().getString("address"));
		lblAddress.setBounds(10, 98, 160, 14);
		panelPerson.add(lblAddress);
		
		personAddress = new JTextField();
		personAddress.setBounds(200, 101, 149, 20);
		panelPerson.add(personAddress);
		personAddress.setColumns(10);
		
		JLabel lblId = new JLabel(controller.getResourcebundle().getString("id"));
		lblId.setBounds(10, 129, 180, 14);
		panelPerson.add(lblId);
		
		personID = new JTextField();
		personID.setBounds(200, 132, 149, 20);
		panelPerson.add(personID);
		personID.setColumns(10);
		
		JLabel lblPhone = new JLabel(controller.getResourcebundle().getString("phone"));
		lblPhone.setBounds(10, 154, 180, 14);
		panelPerson.add(lblPhone);
		
		personPhone = new JTextField();
		personPhone.setBounds(200, 157, 149, 20);
		panelPerson.add(personPhone);
		personPhone.setColumns(10);
		
		JLabel lblEmailAddress = new JLabel(controller.getResourcebundle().getString("email_address"));
		lblEmailAddress.setBounds(10, 182, 180, 14);
		panelPerson.add(lblEmailAddress);
		
		personEmail = new JTextField();
		personEmail.setBounds(200, 188, 149, 20);
		panelPerson.add(personEmail);
		personEmail.setColumns(10);
		panelCompany.setBackground(Color.decode(controller.getBackgroundColor()));
		panelCompany.setBounds(25, 97, 434, 138);
		contentPane.add(panelCompany);
		panelCompany.setVisible(false);
		
		
		panelCompany.setLayout(null);
		
		JLabel label = new JLabel(controller.getResourcebundle().getString("name"));
		label.setBounds(10, 8, 130, 14);
		panelCompany.add(label);
		
		companyID = new JTextField();
		companyID.setColumns(10);
		companyID.setBounds(185, 5, 149, 20);
		panelCompany.add(companyID);
		
		JLabel label_1 = new JLabel(controller.getResourcebundle().getString("address"));
		label_1.setBounds(10, 39, 149, 14);
		panelCompany.add(label_1);
		
		companyAddress = new JTextField();
		companyAddress.setColumns(10);
		companyAddress.setBounds(185, 36, 149, 20);
		panelCompany.add(companyAddress);
		
		JLabel label_2 = new JLabel(controller.getResourcebundle().getString("phone"));
		label_2.setBounds(10, 70, 85, 14);
		panelCompany.add(label_2);
		
		companyPhone = new JTextField();
		companyPhone.setColumns(10);
		companyPhone.setBounds(185, 67, 121, 20);
		panelCompany.add(companyPhone);
		
		JLabel label_3 = new JLabel(controller.getResourcebundle().getString("email_address"));
		label_3.setBounds(10, 101, 105, 14);
		panelCompany.add(label_3);
		emailCompany = new JTextField();
		emailCompany.setColumns(10);
		emailCompany.setBounds(185, 98, 121, 20);
		panelCompany.add(emailCompany);
		

		
		ImageIcon imageIcon = new ImageIcon("img/details.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(78, 87,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		JLabel lbldetails=new JLabel();
		lbldetails.setIcon(imageIcon);
		lbldetails.setBounds(373, 11, 102, 87);
		contentPane.add(lbldetails);
		

		

		
	}
}
