package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import carrenting.client.Controller;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class WelcomeGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private JTextField tfUsername;
	private JTextField tfPassword;
	private JButton boton;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					WelcomeGUI frame = new WelcomeGUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
	
//		});
//	}
	
	public WelcomeGUI(Controller controller) {
		initialize();
		frame.setVisible(true);
		this.controller=controller;
	}

	/**
	 * Create the frame.
	 */
	public void initialize() {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		frame.getContentPane().setLayout(null);
		
		
		tfUsername = new JTextField();
		tfUsername.setBounds(47, 69, 86, 20);
		frame.getContentPane().add(tfUsername);
		tfUsername.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setBounds(47, 136, 86, 20);
		frame.getContentPane().add(tfPassword);
		tfPassword.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(47, 44, 85, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(48, 111, 46, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tfUsername.getText().equals("")) {
					
					
				}else {
					try {
						controller.register(tfUsername.getText());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnSignIn.setBounds(79, 188, 89, 23);
		frame.getContentPane().add(btnSignIn);
	}
}
