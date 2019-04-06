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

	
	public StaffPanelGUI(Controller controller, String StaffType) throws RemoteException{
		this.controller=controller;
		this.staffType=staffType;
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
		frame.setBounds(100, 100, 559, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectACar = new JLabel("Select a car");
		lblSelectACar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblSelectACar.setBounds(206, 22, 94, 22);
		frame.getContentPane().add(lblSelectACar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 53, 471, 139);
		frame.getContentPane().add(scrollPane);
		
		tableCars = new JTable();
		tableCars.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Brand", "Model", "Price per day"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableCars.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableCars.getColumnModel().getColumn(0).setMinWidth(30);
		tableCars.getColumnModel().getColumn(1).setPreferredWidth(80);
		tableCars.getColumnModel().getColumn(2).setPreferredWidth(76);
		tableCars.getColumnModel().getColumn(2).setMinWidth(30);
		DefaultTableModel model = (DefaultTableModel) tableCars.getModel();
		 ArrayList<String> garages = controller.getGarages();
		 for(String garage:garages) {
			 cars.addAll(controller.getCars(garage, 0));
		 }
	        Object rowData[] = new Object[3];
	        for(int i = 0; i < cars.size(); i++)
	        {
	            rowData[0] = cars.get(i).getBrand();
	            rowData[1] = cars.get(i).getModel();
	            rowData[2] = cars.get(i).getPricePerDay();
	            model.addRow(rowData);
	        }
		scrollPane.setViewportView(tableCars);
		tableCars.setShowGrid(false);
		tableCars.setShowVerticalLines(false);
		
		JButton btnReturnedCar = new JButton("Car returned");
		btnReturnedCar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					new ClientDataGUI(controller);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReturnedCar.setBounds(390, 232, 109, 23);
		frame.getContentPane().add(btnReturnedCar);
	}
}
