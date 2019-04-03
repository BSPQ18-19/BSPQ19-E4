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

import javax.swing.JPanel;

public class VisaPayment extends JFrame{

	private JTextField textFieldCreditCard;
	private JTextField textFieldExpirationMonth;
	private JTextField textFieldExpirationYear;
	private Controller controller;
	private JFrame frame;
	
	public VisaPayment(Controller controller) {
		initialize();
		frame.setVisible(true);
		this.controller=controller;
		
	}
	public void initialize(){
		JFrame f = new JFrame("PayPalFrame");
	      f.setSize(250, 250);
	      f.setLocation(300,200);
	      f.getContentPane().add(BorderLayout.CENTER, new JTextArea(10, 40));
	      f.setVisible(true);
	      
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
		
		JLabel lblVisaPayment = new JLabel("Visa payment");
		lblVisaPayment.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVisaPayment.setBounds(64, 47, 123, 22);
		panelCentre.add(lblVisaPayment);
		
		JLabel lblCreditCard = new JLabel("Credit card number:");
		lblCreditCard.setBounds(64, 104, 104, 14);
		panelCentre.add(lblCreditCard);
		
		JLabel lblExpirationDate = new JLabel("Expiration Date:");
		lblExpirationDate.setBounds(64, 132, 104, 14);
		panelCentre.add(lblExpirationDate);
		
		JButton btnFinishPay = new JButton("Finish and pay");
		btnFinishPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldCreditCard.getText().equals("")){
					JOptionPane.showConfirmDialog(null, "You must enter the credit card number", "Careful!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else if(textFieldExpirationMonth.getText().equals("")){
					JOptionPane.showConfirmDialog(null, "You must enter the expiration month", "Careful!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else if(textFieldExpirationYear.getText().equals("")){
					JOptionPane.showConfirmDialog(null, "You must enter the expiration year", "Careful!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "Payment done correctly!");
					try {
						controller.register(textFieldCreditCard.getText());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnFinishPay.setBounds(168, 179, 110, 23);
		panelCentre.add(btnFinishPay);
		
		textFieldCreditCard = new JTextField();
		textFieldCreditCard.setBounds(168, 101, 110, 20);
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
	
//	 public static void main(String[] args) {
//	      
//	      
//	      
//	 }

}
