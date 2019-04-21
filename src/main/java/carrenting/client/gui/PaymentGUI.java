package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import com.toedter.calendar.JDateChooser;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PaymentGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEmail;
	private JTextField textFieldPassword;
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.getContentPane().setLayout(null);
		panelPaypal = new JPanel();
		panelPaypal.setBounds(39, 93, 363, 108);
		frame.getContentPane().add(panelPaypal);
		panelPaypal.setLayout(null);

		
		JLabel lblEmail = new JLabel("email");
		lblEmail.setBounds(10, 24, 111, 14);
		panelPaypal.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(131, 21, 182, 20);
		panelPaypal.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(10, 64, 109, 14);
		panelPaypal.add(lblPassword);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(129, 61, 184, 20);
		panelPaypal.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JLabel lblPaymentType = new JLabel("Payment type:");
		lblPaymentType.setBounds(46, 65, 141, 18);
		frame.getContentPane().add(lblPaymentType);
		
		JRadioButton rdbtnVisa = new JRadioButton("Visa");
		rdbtnVisa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelPaypal.setVisible(false);
				panelVisa.setVisible(true);
				btnFinishAndPay.setBounds(261, 315, 181, 23);
				frame.setBounds(100, 100, 468, 385);
				paymentType="visa";
				
			}
		});
		buttonGroup.add(rdbtnVisa);
		rdbtnVisa.setSelected(true);
		rdbtnVisa.setBounds(193, 63, 74, 23);
		frame.getContentPane().add(rdbtnVisa);
		
		JRadioButton rdbtnPaypal = new JRadioButton("Paypal");
		rdbtnPaypal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelPaypal.setVisible(true);
				panelVisa.setVisible(false);
				btnFinishAndPay.setBounds(261,205,181,23);
				frame.setBounds(100, 100, 468, 300);
				paymentType="paypal";
			}
		});
		buttonGroup.add(rdbtnPaypal);
		rdbtnPaypal.setBounds(284, 63, 109, 23);
		frame.getContentPane().add(rdbtnPaypal);
		
		JLabel lblPayment = new JLabel("Payment");
		lblPayment.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblPayment.setBounds(171, 29, 164, 24);
		frame.getContentPane().add(lblPayment);
		
		btnFinishAndPay = new JButton("Finish and pay");
		btnFinishAndPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(paymentType.equalsIgnoreCase("paypal")) {
					if(textFieldEmail.getText().equals("")|| textFieldPassword.getText().equals("")) {
						JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("All fields must be filled"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					}
				}else if(paymentType.equalsIgnoreCase("visa")) {
					
				}
				else {
					rent.setPaymentSystem(paymentType);

				}
				
			}
		});
		btnFinishAndPay.setBounds(261, 312, 181, 23);
		frame.getContentPane().add(btnFinishAndPay);
		
		panelVisa = new JPanel();
		panelVisa.setBounds(39, 93, 363, 193);
		frame.getContentPane().add(panelVisa);
		panelVisa.setLayout(null);
		
		JLabel lblCardNumber = new JLabel("Card number");
		lblCardNumber.setBounds(10, 21, 127, 14);
		panelVisa.add(lblCardNumber);
		
		textFieldCardNumber = new JTextField();
		textFieldCardNumber.setBounds(155, 18, 198, 20);
		panelVisa.add(textFieldCardNumber);
		textFieldCardNumber.setColumns(10);
		
		JLabel lblNameOnCard = new JLabel("Name on card");
		lblNameOnCard.setBounds(10, 69, 108, 14);
		panelVisa.add(lblNameOnCard);
		
		textFieldNameCard = new JTextField();
		textFieldNameCard.setBounds(155, 66, 198, 20);
		panelVisa.add(textFieldNameCard);
		textFieldNameCard.setColumns(10);
		
		JDateChooser dateChooserExpirationDate = new JDateChooser();
		dateChooserExpirationDate.setBounds(155, 108, 95, 20);
		panelVisa.add(dateChooserExpirationDate);
		
		JLabel lblExpirationDate = new JLabel("Expiration date");
		lblExpirationDate.setBounds(10, 114, 127, 14);
		panelVisa.add(lblExpirationDate);
		
		JLabel lblCvv = new JLabel("CVV");
		lblCvv.setBounds(10, 156, 46, 14);
		panelVisa.add(lblCvv);
		
		textFieldCVV = new JTextField();
		textFieldCVV.setBounds(155, 153, 143, 20);
		panelVisa.add(textFieldCVV);
		textFieldCVV.setColumns(10);
		
		panelVisa.setVisible(true);
		panelPaypal.setVisible(false);
	}
}
