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

public class StaffPanelGUI extends JFrame {

	private JPanel contentPane;
	private JTable tableCars;
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
		frame.setBounds(100, 100, 1084, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectACar = new JLabel("Select a car");
		lblSelectACar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblSelectACar.setBounds(333, 21, 94, 22);
		frame.getContentPane().add(lblSelectACar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 53, 1037, 139);
		frame.getContentPane().add(scrollPane);
		
		tableCars = new JTable();
		tableCars.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Start date", "Return date", "UserID", "Payment system", "Total price", "Garage of origin", "Garage of destination", "NumberPlate", "Brand", "Model", "Price per day"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, false, false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableCars.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableCars.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableCars.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableCars.getColumnModel().getColumn(5).setPreferredWidth(115);
		tableCars.getColumnModel().getColumn(6).setPreferredWidth(130);
		tableCars.getColumnModel().getColumn(7).setPreferredWidth(85);
		tableCars.getColumnModel().getColumn(8).setMinWidth(30);
		tableCars.getColumnModel().getColumn(10).setPreferredWidth(76);
		tableCars.getColumnModel().getColumn(10).setMinWidth(30);
		DefaultTableModel model = (DefaultTableModel) tableCars.getModel();
		 ArrayList<String> garages = controller.getGarages();
		 for(String garage:garages) {
			 cars.addAll(controller.getCars(garage, 0));
		 }
	        Object rowData[] = new Object[11];
	        for(int i = 0; i < cars.size(); i++)
	        {
	        	Rent latestRent=controller.getLatestRent(cars.get(i).getNumPlate());
	        	rowData[0] = latestRent.getStartingDate();
	        	rowData[1] = latestRent.getFinishingDate();
	        	rowData[2] = latestRent.getUserId();
	        	rowData[3] = latestRent.getPaymentSystem();
	        	rowData[4] = latestRent.getTotalPrice();
	            rowData[5] = cars.get(i).getGarage();
	            rowData[6] = latestRent.getGarageDestination();
	            rowData[7] = cars.get(i).getNumPlate();
	            rowData[8] = cars.get(i).getBrand();
	            rowData[9] = cars.get(i).getModel();
	            rowData[10] = cars.get(i).getPricePerDay();            
	            model.addRow(rowData);
	        }
		scrollPane.setViewportView(tableCars);
		tableCars.setShowGrid(false);
		tableCars.setShowVerticalLines(false);
		
		JButton btnReturnedCar = new JButton("Car returned");
		btnReturnedCar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectednumberPlate=cars.get(tableCars.getSelectedRow()).getNumPlate();
				try {
					controller.updateAvailability(selectednumberPlate, 1);
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReturnedCar.setBounds(693, 227, 109, 23);
		frame.getContentPane().add(btnReturnedCar);
	}
}
