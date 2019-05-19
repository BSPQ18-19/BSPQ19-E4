package carrenting.client.gui;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.Date;

import javax.swing.ButtonGroup;
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
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class PaymentGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEmail;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textFieldCardNumber;
	private JTextField textFieldNameCard;
	private JTextField textFieldCVV;
	private Controller controller;
	private Rent rent;
	private JFrame frame;
	private JPanel panelVisa;
	private JPanel panelPaypal;
	private JButton btnFinishAndPay;
	private String paymentType="visa";
	private JDateChooser dateChooserExpirationDate;
	private JButton buttonBack;
	private JPasswordField textFieldPassword;



	public PaymentGUI(Controller controller, Rent rent) {
		this.controller=controller;
		this.rent=rent;
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public void initialize() {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 468, 385);
		contentPane = new JPanel();
		contentPane.setBackground(Color.decode(controller.getBackgroundColor()));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.getContentPane().setLayout(null);
		panelPaypal = new JPanel();
		panelPaypal.setBackground(Color.decode(controller.getBackgroundColor()));
		panelPaypal.setBounds(39, 93, 363, 108);
		frame.getContentPane().add(panelPaypal);
		panelPaypal.setLayout(null);

		
		JLabel lblEmail = new JLabel(controller.getResourcebundle().getString("email"));
		lblEmail.setBounds(10, 24, 111, 14);
		panelPaypal.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(131, 21, 182, 20);
		panelPaypal.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel(controller.getResourcebundle().getString("password"));
		lblPassword.setBounds(10, 64, 109, 14);
		panelPaypal.add(lblPassword);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(131, 61, 182, 20);
		panelPaypal.add(textFieldPassword);
		
		JLabel lblPaymentType = new JLabel(controller.getResourcebundle().getString("payment_type"));
		lblPaymentType.setBounds(46, 65, 141, 18);
		frame.getContentPane().add(lblPaymentType);
		
		JRadioButton rdbtnVisa = new JRadioButton(controller.getResourcebundle().getString("visa"));
		rdbtnVisa.setBackground(Color.decode(controller.getBackgroundColor()));
		rdbtnVisa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelPaypal.setVisible(false);
				panelVisa.setVisible(true);
				btnFinishAndPay.setBounds(261, 315, 181, 23);
				buttonBack.setBounds(106, 315, 124, 23);
				frame.setBounds(100, 100, 468, 385);
				paymentType="visa";
			}
		});
		buttonGroup.add(rdbtnVisa);
		rdbtnVisa.setSelected(true);
		rdbtnVisa.setBounds(193, 63, 124, 23);
		frame.getContentPane().add(rdbtnVisa);
		
		JRadioButton rdbtnPaypal = new JRadioButton("Paypal"); //It isnt worth it to use the controller here because it is the same in the 3 laguages
		rdbtnPaypal.setBackground(Color.decode(controller.getBackgroundColor()));
		rdbtnPaypal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelPaypal.setVisible(true);
				panelVisa.setVisible(false);
				btnFinishAndPay.setBounds(261,205,181,23);
				buttonBack.setBounds(106, 205, 124, 23);
				frame.setBounds(100, 100, 468, 300);
				paymentType="paypal";

			}
		});
		buttonGroup.add(rdbtnPaypal);
		rdbtnPaypal.setBounds(319, 63, 109, 23);
		frame.getContentPane().add(rdbtnPaypal);
		
		JLabel lblPayment = new JLabel(controller.getResourcebundle().getString("payment"));
		lblPayment.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblPayment.setBounds(171, 29, 164, 24);
		frame.getContentPane().add(lblPayment);
		
		btnFinishAndPay = new JButton(controller.getResourcebundle().getString("finish_and_pay"));
		btnFinishAndPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(paymentType.equalsIgnoreCase("paypal")) {
					if(textFieldEmail.getText().equals("")|| textFieldPassword.getText().equals("")) {
						JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("all_fields_filled"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					}
					else {
						try {
							if(controller.checkPayment(paymentType, rent.getTotalPrice())) {
								rent.setPaymentSystem(paymentType);
								try {
									JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("rent_successful"), controller.getResourcebundle().getString("successful"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
									controller.storeRent(rent.getUserId(),rent.getNumberPlate(), rent.getStartingDate(), rent.getFinishingDate(), rent.getGarageOrigin(), rent.getGarageDestination(), rent.getPaymentSystem(), rent.getTotalPrice());
									frame.dispose();
								} catch (RemoteException e) {
									e.printStackTrace();
								}
								controller.getLogger().debug(rent.toString());
							}
						} catch (RemoteException e) {
							e.printStackTrace();
						}

						
					}
				}else if(paymentType.equalsIgnoreCase("visa")) {
					if(textFieldNameCard.getText().equals("")|| textFieldCardNumber.getText().equals("")|| textFieldCVV.getText().equals("")||
							dateChooserExpirationDate.getDate()==(null)){
						JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("all_fields_filled"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					}
					else {
						try {
							if(controller.checkPayment(paymentType, rent.getTotalPrice())) {
								rent.setPaymentSystem(paymentType);
								controller.getLogger().debug(rent.toString());
								try {
									JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("rent_successful"), controller.getResourcebundle().getString("successful"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
									controller.storeRent(rent.getUserId(),rent.getNumberPlate(), rent.getStartingDate(), rent.getFinishingDate(), rent.getGarageOrigin(), rent.getGarageDestination(), rent.getPaymentSystem(), rent.getTotalPrice());
									frame.dispose();
								} catch (RemoteException e) {
									e.printStackTrace();
								}
							}
						} catch (HeadlessException | RemoteException e) {
							e.printStackTrace();
						}	
					}
				}
			}
		});
		btnFinishAndPay.setBounds(261, 312, 181, 23);
		frame.getContentPane().add(btnFinishAndPay);
		
		buttonBack = new JButton(controller.getResourcebundle().getString("back"));
		buttonBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					new ClientDataGUI(controller, rent);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		buttonBack.setBounds(106, 312, 124, 23);
		contentPane.add(buttonBack);
		
		panelVisa = new JPanel();
		panelVisa.setBackground(Color.decode(controller.getBackgroundColor()));
		panelVisa.setBounds(39, 93, 363, 193);
		frame.getContentPane().add(panelVisa);
		panelVisa.setLayout(null);
		
		JLabel lblCardNumber = new JLabel(controller.getResourcebundle().getString("card_number"));
		lblCardNumber.setBounds(10, 21, 127, 14);
		panelVisa.add(lblCardNumber);
		
		textFieldCardNumber = new JTextField();
		textFieldCardNumber.setBounds(155, 18, 198, 20);
		panelVisa.add(textFieldCardNumber);
		textFieldCardNumber.setColumns(10);
		
		JLabel lblNameOnCard = new JLabel(controller.getResourcebundle().getString("card_name"));
		lblNameOnCard.setBounds(10, 69, 108, 14);
		panelVisa.add(lblNameOnCard);
		
		textFieldNameCard = new JTextField();
		textFieldNameCard.setBounds(155, 66, 198, 20);
		panelVisa.add(textFieldNameCard);
		textFieldNameCard.setColumns(10);
		
		dateChooserExpirationDate = new JDateChooser();
		Date date = new Date(System.currentTimeMillis());  
		dateChooserExpirationDate.setMinSelectableDate(date);
		dateChooserExpirationDate.setBounds(155, 108, 95, 20);
		panelVisa.add(dateChooserExpirationDate);
		
		JLabel lblExpirationDate = new JLabel(controller.getResourcebundle().getString("expiration_date"));
		lblExpirationDate.setBounds(10, 114, 127, 14);
		panelVisa.add(lblExpirationDate);
		
		JLabel lblCvv = new JLabel(controller.getResourcebundle().getString("cvv"));
		lblCvv.setBounds(10, 156, 108, 14);
		panelVisa.add(lblCvv);
		
		textFieldCVV = new JTextField();
		textFieldCVV.setBounds(155, 153, 143, 20);
		panelVisa.add(textFieldCVV);
		textFieldCVV.setColumns(10);
		
		panelVisa.setVisible(true);
		panelPaypal.setVisible(false);
	}
}
