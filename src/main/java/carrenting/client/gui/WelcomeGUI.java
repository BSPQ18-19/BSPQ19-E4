package carrenting.client.gui;



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;


/*TODO
 * Make the dates only selectable from today's date. And make sure return is after start
 * 
 */
@SuppressWarnings("serial")
public class WelcomeGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame welcomeFrame;
	private JScrollPane scrollPaneDestination;
	JButton btnChange= new JButton();
	JLabel lblHi=new JLabel();
	JComboBox selectLanguage = new JComboBox<>();
	private Rent rent;
	private JDateChooser dateChooserFinish = new JDateChooser();
	private JDateChooser dateChooserStart = new JDateChooser();
	


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
	public WelcomeGUI(Controller controller, Rent rent) throws RemoteException{
		this.controller=controller;
		this.rent=rent;
		initialize();
		welcomeFrame.setVisible(true);
		//initComponents(); (descomentar con lo de abajo(alternativa para el tema del idioma))
		
	}
	
	
	

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public void initialize() throws RemoteException {
		welcomeFrame = new JFrame();
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeFrame.setBounds(100, 100, 740, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		welcomeFrame.setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JLabel lblWelcomeToCar = new JLabel(controller.getResourcebundle().getString("welcome_car_msg"));
		lblWelcomeToCar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblWelcomeToCar.setBounds(253, 51, 230, 22);
		welcomeFrame.getContentPane().add(lblWelcomeToCar);
		
		JLabel lblStaffArea = new JLabel("<html><u>STAFF area");
		lblStaffArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				welcomeFrame.dispose();
				try {
					new LogInStaffGUI(controller,rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblStaffArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		lblStaffArea.setForeground(Color.BLUE);
		lblStaffArea.setBounds(569, 11, 170, 22);
		welcomeFrame.getContentPane().add(lblStaffArea);
		
		JLabel lblSelectAGarage = new JLabel(controller.getResourcebundle().getString("Select_garage_origin"));
		lblSelectAGarage.setBounds(88, 106, 230, 14);
		welcomeFrame.getContentPane().add(lblSelectAGarage);
		
		JLabel lblSelectAGarage_1 = new JLabel(controller.getResourcebundle().getString("Select_garage_destination"));
		lblSelectAGarage_1.setBounds(390, 106, 183, 14);
		welcomeFrame.getContentPane().add(lblSelectAGarage_1);
		
		JScrollPane scrollPaneOrigin = new JScrollPane();
		scrollPaneOrigin.setBounds(88, 131, 185, 80);
		contentPane.add(scrollPaneOrigin);
		
		JList<Object> listGarageOrigin = new JList<Object>();
		scrollPaneOrigin.setViewportView(listGarageOrigin);
		listGarageOrigin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		
		scrollPaneDestination = new JScrollPane();
		scrollPaneDestination.setBounds(390, 131, 194, 77);
		contentPane.add(scrollPaneDestination);
		
		JList<String> listGarageDestination = new JList<String>();
		scrollPaneDestination.setViewportView(listGarageDestination);
		listGarageDestination.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGarageDestination.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			ArrayList<String> values = controller.getGarages();
			public int getSize() {
				return values.size();
			}
			public String getElementAt(int index) {
				return values.get(index);
			}
		});
		listGarageDestination.setSelectedIndex(0);
		

		
		

		Date date = new Date(System.currentTimeMillis());  
		dateChooserStart.setMinSelectableDate(date);
		dateChooserStart.getCalendarButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dateChooserStart.setMaxSelectableDate(dateChooserFinish.getDate());
			}
		});
		dateChooserStart.setBounds(88, 286, 151, 20);
		contentPane.add(dateChooserStart);

		dateChooserFinish.getCalendarButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dateChooserFinish.setMinSelectableDate(dateChooserStart.getDate());

			}
		});

		dateChooserFinish.setBounds(390, 286, 151, 20);
		contentPane.add(dateChooserFinish);
		
		JLabel lblSelectAS = new JLabel(controller.getResourcebundle().getString("Select_starting_date"));
		lblSelectAS.setBounds(88, 265, 216, 14);
		contentPane.add(lblSelectAS);
		
		JLabel lblSelectFinishingDate = new JLabel(controller.getResourcebundle().getString("Select_finishing_date"));
		lblSelectFinishingDate.setBounds(390, 265, 293, 14);
		contentPane.add(lblSelectFinishingDate);
		
		JButton btnContinue = new JButton("Next");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dateChooserStart.getDate()==(null)|| dateChooserFinish.getDate()==(null)) {
					JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("date_confirm_dialogue"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				else {
					welcomeFrame.dispose();
					String garageOrigin = (String) listGarageOrigin.getSelectedValue();
					String garageDestination=(String) listGarageDestination.getSelectedValue();
					java.util.Date startingDate=  dateChooserStart.getDate();
					java.util.Date finishingDate =  dateChooserFinish.getDate();
					try {
						rent.setGarageOrigin(garageOrigin);
						rent.setGarageDestination(garageDestination);
						rent.setStartingDate(startingDate);
						System.out.println(startingDate);
						System.out.println(startingDate);
						rent.setFinishingDate(finishingDate);
						System.out.println(rent.toString());
						new SelectCarGUI(controller, rent);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnContinue.setBounds(537, 353, 151, 23);
		contentPane.add(btnContinue);
		
		JCheckBox chckbxSameGarage = new JCheckBox("<html>Same as garage <p> of origin<html>");
		chckbxSameGarage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (chckbxSameGarage.isSelected()) {
					rent.setGarageDestination((String) listGarageOrigin.getSelectedValue());
					listGarageDestination.setSelectedIndex(listGarageOrigin.getSelectedIndex());
					listGarageDestination.setEnabled(false);
				}
				else {
					listGarageDestination.setEnabled(true);
				}
				
			}
		});
		chckbxSameGarage.setBounds(598, 129, 145, 37);
		contentPane.add(chckbxSameGarage);


	}
}