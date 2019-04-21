package carrenting.client.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PaypalGUINO extends JFrame{
	private JTextField textFieldEmail;
	private JTextField textFieldPass;
	private JFrame frame;
	private Controller controller;
	private Rent rent;


	public PaypalGUINO(Controller controller, Rent rent) {
		this.controller=controller;
		this.rent=rent;
		initialize();
		frame.setVisible(true);
	}
	
	public void initialize(){
		
	      frame = new JFrame(controller.getResourcebundle().getString("paypal_frame"));
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
		
		JLabel lblPaypalPayment = new JLabel(controller.getResourcebundle().getString("paypal_payment"));
		lblPaypalPayment.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPaypalPayment.setBounds(75, 47, 123, 22);
		panelCentre.add(lblPaypalPayment);
		
		JLabel lblEmail = new JLabel(controller.getResourcebundle().getString("email"));
		lblEmail.setBounds(75, 104, 66, 14);
		panelCentre.add(lblEmail);
		
		JLabel lblPassword = new JLabel(controller.getResourcebundle().getString("password"));
		lblPassword.setBounds(75, 132, 66, 14);
		panelCentre.add(lblPassword);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(165, 101, 110, 20);
		panelCentre.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		textFieldPass = new JTextField();
		textFieldPass.setBounds(165, 129, 110, 20);
		panelCentre.add(textFieldPass);
		textFieldPass.setColumns(10);
		
		JButton btnFinishPay = new JButton(controller.getResourcebundle().getString("finish_and_pay"));
		btnFinishPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldEmail.getText().equals("")){
					JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("email_confirm_dialogue"), controller.getResourcebundle().getString("careful"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else if(textFieldPass.getText().equals("")){
					JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("password_confirm_dialogue"), controller.getResourcebundle().getString("careful"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, controller.getResourcebundle().getString("payment_correctly"));

				}
				
			}
		});
		btnFinishPay.setBounds(165, 179, 110, 23);
		panelCentre.add(btnFinishPay);
	}
	
	
//	 public static void main(String[] args) {
//	      
//	  
//	      
//	    }
}
