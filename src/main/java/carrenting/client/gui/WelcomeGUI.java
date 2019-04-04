package carrenting.client.gui;



import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import carrenting.client.Controller;

import javax.swing.JLabel;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JList;

@SuppressWarnings("serial")
public class WelcomeGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame welcomeFrame;

	/**
	 * Launch the application.
	 * @throws RemoteException 
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
	public WelcomeGUI(Controller controller) throws RemoteException{
		this.controller=controller;
		initialize();
		welcomeFrame.setVisible(true);
		
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public void initialize() throws RemoteException {
		welcomeFrame = new JFrame();
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeFrame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		welcomeFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblWelcomeToCar = new JLabel("Welcome to MyCarRenting");
		lblWelcomeToCar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblWelcomeToCar.setBounds(100, 10, 230, 22);
		welcomeFrame.getContentPane().add(lblWelcomeToCar);
		
		JLabel lblStaffArea = new JLabel("<html><u>STAFF area");
		lblStaffArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		lblStaffArea.setForeground(Color.BLUE);
		lblStaffArea.setBounds(365, 11, 69, 22);
		welcomeFrame.getContentPane().add(lblStaffArea);
		
		JList<Object> listGarageOrigin = new JList<Object>();
		listGarageOrigin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGarageOrigin.setBounds(20, 79, 133, 63);
		listGarageOrigin.setModel(new AbstractListModel<Object>() {
			private static final long serialVersionUID = 1L;
			ArrayList<String> values = controller.getGarages();
			public int getSize() {
				return values.size();
			}
			public Object getElementAt(int index) {
				return values.get(index);
			}
		});
		listGarageOrigin.setSelectedIndex(0);
		scrollPane.setViewportView(listGarageOrigin);
		welcomeFrame.getContentPane().add(listGarageOrigin);
		
		JLabel lblSelectAGarage = new JLabel("Select a garage of origin");
		lblSelectAGarage.setBounds(20, 57, 133, 14);
		welcomeFrame.getContentPane().add(lblSelectAGarage);
		
		JList listGarageDestination = new JList();
		listGarageDestination.setBounds(235, 79, 133, 63);
		welcomeFrame.getContentPane().add(listGarageDestination);
		
		JLabel lblSelectAGarage_1 = new JLabel("Select a garage of destination");
		lblSelectAGarage_1.setBounds(235, 57, 151, 14);
		welcomeFrame.getContentPane().add(lblSelectAGarage_1);
	}
}
