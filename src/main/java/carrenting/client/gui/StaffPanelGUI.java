package carrenting.client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import carrenting.client.Controller;
import carrenting.client.RMIServiceLocator;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.Rent;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class StaffPanelGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private String staffType;
	private ArrayList<Car> cars = new ArrayList<>();
	private Rent rent;

	
	public StaffPanelGUI(Controller controller, String StaffType, Rent rent) throws RemoteException{
		this.controller=controller;
		this.staffType=staffType;
		this.rent=rent;
		initialize();
		frame.setVisible(true);

	}
	
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public void initialize () throws RemoteException {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1084, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JButton btnReturnToStartpage = new JButton(controller.getResourcebundle().getString("return_startpage"));
		btnReturnToStartpage.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					new WelcomeGUI(controller, rent);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnReturnToStartpage.setForeground(Color.BLACK);
		btnReturnToStartpage.setBounds(900, 398, 158, 23);
		contentPane.add(btnReturnToStartpage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 139, 923, 234);
		contentPane.add(scrollPane);
		
		JLabel lblRents = new JLabel(controller.getResourcebundle().getString("rents"));
		lblRents.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblRents.setBounds(529, 52, 81, 22);
		contentPane.add(lblRents);
		
		JPanel panel = new JPanel();
		panel.setBounds(24, 11, 388, 108);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
}
