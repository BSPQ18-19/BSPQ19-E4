package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;

import javax.swing.JPanel;

public class VisaGUINO extends JFrame{

	private JTextField textFieldCreditCard;
	private JTextField textFieldExpirationMonth;
	private JTextField textFieldExpirationYear;
	private Controller controller;
	private Rent rent;
	private JFrame frame;
	
	public VisaGUINO(Controller controller, Rent rent) {
		this.controller=controller;
		this.rent=rent;
		initialize();
		frame.setVisible(true);
		
	}
	public void initialize(){
		  frame = new JFrame("PayPalFrame");
	      frame.setSize(250, 250);
	      frame.setLocation(300,200);
	      frame.getContentPane().add(BorderLayout.CENTER, new JTextArea(10, 40));
	      frame.setVisible(true);
	      
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		getContentPane().add(panelNorth, BorderLayout.NORTH);
		
		JPanel panelSouth = new JPanel();
		getContentPane().add(panelSouth, BorderLayout.SOUTH);
		
		JPanel panelEast = new JPanel();
		getContentPane().add(panelEast, BorderLayout.EAST);
		
		JPanel panelWest = new JPanel();
		getContentPane().add(panelWest, BorderLayout.WEST);
		
		JPanel panelCentre = new JPanel();
		getContentPane().add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(null);
		
		JLabel lblVisaPayment = new JLabel(controller.getResourcebundle().getString("visa_payment"));
		lblVisaPayment.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVisaPayment.setBounds(68, 40, 245, 22);
		panelCentre.add(lblVisaPayment);
		
		JLabel lblCreditCard = new JLabel(controller.getResourcebundle().getString("credit_card_number"));
		lblCreditCard.setBounds(20, 101, 138, 14);
		panelCentre.add(lblCreditCard);
		
		JLabel lblExpirationDate = new JLabel(controller.getResourcebundle().getString("expiration_date"));
		lblExpirationDate.setBounds(20, 129, 138, 14);
		panelCentre.add(lblExpirationDate);
		
		JButton btnFinishPay = new JButton(controller.getResourcebundle().getString("finish_and_pay"));
		btnFinishPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldCreditCard.getText().equals("")){
					JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("credit_card_number_confirm_dialogue"), controller.getResourcebundle().getString("careful"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else if(textFieldExpirationMonth.getText().equals("")){
					JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("expiration_month_confirm_dialogue"), controller.getResourcebundle().getString("careful"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else if(textFieldExpirationYear.getText().equals("")){
					JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("expiration_year_confirm_dialogue"), controller.getResourcebundle().getString("careful"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, controller.getResourcebundle().getString("payment_correctly"));
				}
			}
		});
		btnFinishPay.setBounds(168, 179, 189, 23);
		panelCentre.add(btnFinishPay);
		
		textFieldCreditCard = new JTextField();
		textFieldCreditCard.setBounds(168, 101, 145, 20);
		panelCentre.add(textFieldCreditCard);
		textFieldCreditCard.setColumns(10);
		
		textFieldExpirationMonth = new JTextField();
		textFieldExpirationMonth.setBounds(168, 129, 32, 20);
		panelCentre.add(textFieldExpirationMonth);
		textFieldExpirationMonth.setColumns(10);
		
		textFieldExpirationYear = new JTextField();
		textFieldExpirationYear.setBounds(215, 129, 32, 20);
		panelCentre.add(textFieldExpirationYear);
		textFieldExpirationYear.setColumns(10);
		
		JLabel label = new JLabel("/");
		label.setBounds(206, 132, 13, 14);
		panelCentre.add(label);
	}
	

}
