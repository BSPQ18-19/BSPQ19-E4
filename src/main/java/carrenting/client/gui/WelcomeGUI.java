package carrenting.client.gui;



import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
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
import carrenting.server.jdo.Car;
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
	private Rent rent;
	private JDateChooser dateChooserFinish = new JDateChooser();
	private JDateChooser dateChooserStart = new JDateChooser();
	

	public WelcomeGUI(Controller controller, Rent rent) throws RemoteException{
		this.controller=controller;
		this.rent=rent;
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
		welcomeFrame.setBounds(100, 100, 752, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		welcomeFrame.setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JLabel lblWelcomeToCar = new JLabel(controller.getResourcebundle().getString("welcome_car_msg"));
		lblWelcomeToCar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblWelcomeToCar.setBounds(253, 51, 230, 22);
		welcomeFrame.getContentPane().add(lblWelcomeToCar);
		
		
		
		JLabel lblStaffArea = new JLabel(controller.getResourcebundle().getString("staff_area"));
		lblStaffArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				welcomeFrame.dispose();
				try {
					new LogInStaffGUI(controller,rent);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		lblStaffArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		lblStaffArea.setForeground(Color.BLUE);
		lblStaffArea.setBounds(594, 11, 145, 22);
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
			ArrayList<String> values = controller.garagesWithCars();
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
		
		JButton btnContinue = new JButton(controller.getResourcebundle().getString("next"));
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String garageOrigin = (String) listGarageOrigin.getSelectedValue();
				String garageDestination=(String) listGarageDestination.getSelectedValue();
				java.util.Date startingDate=  dateChooserStart.getDate();
				java.util.Date finishingDate =  dateChooserFinish.getDate();
				ArrayList<Car> carTest = new ArrayList<>();
				try {
					carTest= controller.getCarsAvailable(garageOrigin, startingDate, finishingDate);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				if(carTest.isEmpty()) {
					JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("no_cars_available"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				if(dateChooserStart.getDate()==(null)|| dateChooserFinish.getDate()==(null)) {
					JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("date_confirm_dialogue"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				}

				else {
					welcomeFrame.dispose();

					try {
						rent.setGarageOrigin(garageOrigin);
						rent.setGarageDestination(garageDestination);
						rent.setStartingDate(startingDate);
						controller.getLogger().debug(startingDate.toString());
						controller.getLogger().debug(startingDate.toString());
						rent.setFinishingDate(finishingDate);
						controller.getLogger().debug(rent.toString());
						new SelectCarGUI(controller, rent);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnContinue.setBounds(537, 353, 151, 23);
		contentPane.add(btnContinue);
		
		JCheckBox chckbxSameGarage = new JCheckBox(controller.getResourcebundle().getString("same_as_garage_of_origin"));
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
		
		JLabel lblSeleccionDeIdioma = new JLabel(controller.getResourcebundle().getString("language"));
		lblSeleccionDeIdioma.setBounds(10, 0, 151, 14);
		contentPane.add(lblSeleccionDeIdioma);
		
		JLabel lblSpanish = new JLabel("<html><u>Español");
		lblSpanish.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.setLocale("es");
				welcomeFrame.dispose();
				try {
					new WelcomeGUI(controller, rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblSpanish.setForeground(Color.BLUE);
		lblSpanish.setBounds(20, 15, 51, 14);
		contentPane.add(lblSpanish);
		
		JLabel lblBasque = new JLabel("<html><u>Euskal");
		lblBasque.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.setLocale("eu");
				welcomeFrame.dispose();
				try {
					new WelcomeGUI(controller, rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblBasque.setForeground(Color.BLUE);
		lblBasque.setBounds(84, 15, 46, 14);
		contentPane.add(lblBasque);
		
		JLabel lblEnglish = new JLabel("<html><u>English");
		lblEnglish.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.setLocale("en");
				welcomeFrame.dispose();
				try {
					new WelcomeGUI(controller, rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblEnglish.setForeground(Color.BLUE);
		lblEnglish.setBounds(146, 15, 46, 14);
		contentPane.add(lblEnglish);
//		JLabel lblImage=new JLabel(new ImageIcon("img/carrenting.png"));
//	    ImageIcon background=new ImageIcon("img/carrenting.png");
//	    Image img=background.getImage();
//	    Image temp=img.getScaledInstance(500,600,Image.SCALE_SMOOTH);
//	    background=new ImageIcon(temp);
//	    JLabel back=new JLabel(background);
//	    back.setLayout(null);
//	    back.setBounds(0,0,500,600);
//		welcomeFrame.getContentPane().add(lblImage);
//		lblImage.setBounds(352, 0, 221, 95);
//		contentPane.add(lblImage);


	}
}