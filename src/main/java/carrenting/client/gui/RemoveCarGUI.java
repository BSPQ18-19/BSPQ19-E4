package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import carrenting.client.Controller;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.Rent;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class RemoveCarGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JFrame frame;
	private Controller controller;
	private String staffType;
	private Rent rent;
	/**
	 * Launch the application.
	 * @throws RemoteException 
	 */
	public RemoveCarGUI(Controller controller, String staffType, Rent rent) throws RemoteException {
		this.controller=controller;
		this.staffType=staffType;
		this.rent=rent;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public void initialize() throws RemoteException {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 642, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRemoveCars = new JLabel("Remove cars");
		lblRemoveCars.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblRemoveCars.setBounds(265, 35, 209, 24);
		contentPane.add(lblRemoveCars);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 84, 556, 237);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Number plate", "Brand", "Garage", "Model", "Price per day"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		ArrayList<Car> cars = controller.getAllCars();

	        Object rowData[] = new Object[5];
	        for(int i = 0; i < cars.size(); i++)
	        {
	        	rowData[0] = cars.get(i).getNumPlate();
	        	rowData[1] = cars.get(i).getBrand();	      
	        	rowData[2] = cars.get(i).getGarage();
	        	rowData[3] = cars.get(i).getModel();
	        	rowData[4] = cars.get(i).getPricePerDay();
	            model.addRow(rowData);
	        }
		scrollPane.setViewportView(table);
		
		JButton btnRemove = new JButton("Remove");
//		btnRemove.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//			}
//		});


		btnRemove.setBounds(478, 343, 108, 23);
		contentPane.add(btnRemove);
		
		JButton btnBackToStaff = new JButton("Back to staff panel");
		btnBackToStaff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				try {
					new StaffPanelGUI(controller, staffType, rent);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnBackToStaff.setBounds(234, 343, 218, 23);
		contentPane.add(btnBackToStaff);
	}
}
