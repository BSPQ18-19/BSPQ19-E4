package carrenting.client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import carrenting.client.Controller;
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
		frame.setBounds(100, 100, 624, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectACar = new JLabel("Select a car");
		lblSelectACar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblSelectACar.setBounds(206, 22, 94, 22);
		frame.getContentPane().add(lblSelectACar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 53, 561, 139);
		frame.getContentPane().add(scrollPane);
		
		tableCars = new JTable();
		tableCars.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Garage of origin", "Garage of destination", "NumberPlate", "Brand", "Model", "Price per day"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, true, false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableCars.getColumnModel().getColumn(0).setPreferredWidth(115);
		tableCars.getColumnModel().getColumn(1).setPreferredWidth(130);
		tableCars.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableCars.getColumnModel().getColumn(3).setMinWidth(30);
		tableCars.getColumnModel().getColumn(5).setPreferredWidth(76);
		tableCars.getColumnModel().getColumn(5).setMinWidth(30);
		DefaultTableModel model = (DefaultTableModel) tableCars.getModel();
		 ArrayList<String> garages = controller.getGarages();
		 for(String garage:garages) {
			 cars.addAll(controller.getCars(garage, 0));
		 }
	        Object rowData[] = new Object[6];
	        for(int i = 0; i < cars.size(); i++)
	        {
	            rowData[0] = cars.get(i).getGarage();
	            rowData[1] = controller.getGarageDestination(cars.get(i).getNumPlate());
	            rowData[2] = cars.get(i).getNumPlate();
	            rowData[3] = cars.get(i).getBrand();
	            rowData[4] = cars.get(i).getModel();
	            rowData[5] = cars.get(i).getPricePerDay();
//TODO EN EL CONTROLLER SACAR CON LA MATRICULA EL GRAGE DE DESTINATION
	         //   controller.getGarageDestination(cars.get(i).getNumPlate())

	            
	            model.addRow(rowData);
	        }
		scrollPane.setViewportView(tableCars);
		tableCars.setShowGrid(false);
		tableCars.setShowVerticalLines(false);
		
		JButton btnReturnedCar = new JButton("Car returned");
		btnReturnedCar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO UPDATE DE LA DB garage de destino y availability 1
			}
		});
		btnReturnedCar.setBounds(390, 232, 109, 23);
		frame.getContentPane().add(btnReturnedCar);
	}
}
