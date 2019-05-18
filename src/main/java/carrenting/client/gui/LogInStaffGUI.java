package carrenting.client.gui;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;

@SuppressWarnings("serial")
public class LogInStaffGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private JTextField tfUsername;
	private JTextField tfPassword;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Rent rent;
	private String type;
	/**
	 * Launch the application.
	 */
	
	public LogInStaffGUI(Controller controller,Rent rent) throws RemoteException{
		this.controller=controller;
		this.rent=rent;
		initialize();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public void initialize () throws RemoteException {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 385, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		
		tfUsername = new JTextField();
		tfUsername.setBounds(21, 143, 164, 20);
		frame.getContentPane().add(tfUsername);
		tfUsername.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setBounds(21, 199, 164, 20);
		frame.getContentPane().add(tfPassword);
		tfPassword.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(21, 118, 85, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(21, 174, 64, 14);
		frame.getContentPane().add(lblPassword);
		
		JRadioButton rdbtnAdministrator = new JRadioButton(controller.getResourcebundle().getString("admin"));
		buttonGroup.add(rdbtnAdministrator);
		rdbtnAdministrator.setBounds(26, 84, 109, 23);
		contentPane.add(rdbtnAdministrator);
		
		JRadioButton rdbtnEmployee = new JRadioButton(controller.getResourcebundle().getString("employee"));
		rdbtnEmployee.setSelected(true);
		buttonGroup.add(rdbtnEmployee);
		rdbtnEmployee.setBounds(151, 84, 109, 23);
		contentPane.add(rdbtnEmployee);
		
		JButton btnSignIn = new JButton(controller.getResourcebundle().getString("sign_in"));
		btnSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(rdbtnAdministrator.isSelected())
					type = "administrator";
				else
					type = "employee";
				
				
				try {
					if(controller.loginStaff(tfUsername.getText(), tfPassword.getText(), type)) {
						frame.dispose();
						controller.getLogger().info(controller.getResourcebundle().getString("staff_login_successful"));		
						new StaffPanelGUI(controller,type,rent);
					}else {
						JOptionPane.showMessageDialog(new JFrame(), controller.getResourcebundle().getString("login_unsuccessful"), "Dialog", JOptionPane.ERROR_MESSAGE);
						controller.getLogger().info(controller.getResourcebundle().getString("login_unsuccessful"));
					}
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnSignIn.setBounds(180, 327, 179, 23);
		frame.getContentPane().add(btnSignIn);
		
		JLabel lblStaffLoginArea = new JLabel(controller.getResourcebundle().getString("staff_login"));
		lblStaffLoginArea.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblStaffLoginArea.setBounds(30, 23, 230, 29);
		contentPane.add(lblStaffLoginArea);
		
		JLabel lblIAmAn = new JLabel(controller.getResourcebundle().getString("i_am_an"));
		lblIAmAn.setBounds(26, 63, 119, 14);
		contentPane.add(lblIAmAn);
		
		JButton btnCancel = new JButton(controller.getResourcebundle().getString("cancel"));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					new WelcomeGUI(controller,rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnCancel.setBounds(21, 327, 128, 23);
		contentPane.add(btnCancel);
	}
}
