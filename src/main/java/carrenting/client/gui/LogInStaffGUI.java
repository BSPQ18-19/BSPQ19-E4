package carrenting.client.gui;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class LogInStaffGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private JTextField tfUsername;
	private JTextField tfPassword;
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
	
	public LogInStaffGUI(Controller controller) throws RemoteException{
		this.controller=controller;
		initialize();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public void initialize () throws RemoteException {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 297, 324);
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
		
		JRadioButton rdbtnAdministrator = new JRadioButton("Administrator");
		buttonGroup.add(rdbtnAdministrator);
		rdbtnAdministrator.setBounds(26, 84, 109, 23);
		contentPane.add(rdbtnAdministrator);
		
		JRadioButton rdbtnEmployee = new JRadioButton("Employee");
		rdbtnEmployee.setSelected(true);
		buttonGroup.add(rdbtnEmployee);
		rdbtnEmployee.setBounds(151, 84, 109, 23);
		contentPane.add(rdbtnEmployee);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type;
				if(rdbtnAdministrator.isSelected())
					type = "administrator";
				else
					type = "employee";
				
				try {
					if(controller.loginStaff(tfUsername.getText(), tfPassword.getText(), type)) {
						//TODO - Que Staff Panel reciba Controller
						frame.dispose();
						try {
							new StaffPanelGUI(controller,type);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						System.out.println("Staff login successfull");
						//new StaffPanel();
					}else {
						System.out.println("Login unsuccesfull");
					}
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}		
				
			}
		});
		btnSignIn.setBounds(75, 254, 89, 23);
		frame.getContentPane().add(btnSignIn);
		
		JLabel lblStaffLoginArea = new JLabel("Staff login");
		lblStaffLoginArea.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblStaffLoginArea.setBounds(99, 23, 85, 29);
		contentPane.add(lblStaffLoginArea);
		
		JLabel lblIAmAn = new JLabel("I am an:");
		lblIAmAn.setBounds(26, 63, 46, 14);
		contentPane.add(lblIAmAn);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					new WelcomeGUI(controller);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnCancel.setBounds(186, 254, 85, 23);
		contentPane.add(btnCancel);
	}
}
