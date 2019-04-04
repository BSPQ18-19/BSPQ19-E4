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
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;

public class LogInStaffGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private JTextField tfUsername;
	private JTextField tfPassword;
	private JButton boton;
	private final ButtonGroup buttonGroup = new ButtonGroup();

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
	
	public LogInStaffGUI(Controller controller) {
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
		frame.setBounds(100, 100, 303, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		
		tfUsername = new JTextField();
		tfUsername.setBounds(21, 143, 108, 20);
		frame.getContentPane().add(tfUsername);
		tfUsername.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setBounds(21, 199, 108, 20);
		frame.getContentPane().add(tfPassword);
		tfPassword.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(21, 118, 85, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(21, 174, 64, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tfUsername.getText().equals("")) {

				}else {
//					controller.register(tfUsername.getText());
					try {
						controller.storeGarage(tfUsername.getText());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnSignIn.setBounds(171, 239, 89, 23);
		frame.getContentPane().add(btnSignIn);
		
		JRadioButton rdbtnAdministrator = new JRadioButton("Administrator");
		buttonGroup.add(rdbtnAdministrator);
		rdbtnAdministrator.setBounds(26, 84, 109, 23);
		contentPane.add(rdbtnAdministrator);
		
		JRadioButton rdbtnEmployee = new JRadioButton("Employee");
		rdbtnEmployee.setSelected(true);
		buttonGroup.add(rdbtnEmployee);
		rdbtnEmployee.setBounds(151, 84, 109, 23);
		contentPane.add(rdbtnEmployee);
		
		JLabel lblStaffLoginArea = new JLabel("Staff login");
		lblStaffLoginArea.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblStaffLoginArea.setBounds(93, 27, 142, 20);
		contentPane.add(lblStaffLoginArea);
		
		JLabel lblIAmAn = new JLabel("I am an:");
		lblIAmAn.setBounds(26, 63, 46, 14);
		contentPane.add(lblIAmAn);
	}
}
