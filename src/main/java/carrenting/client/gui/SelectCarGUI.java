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

@SuppressWarnings("serial")
public class SelectCarGUI extends JFrame {

	private JPanel contentPane;
	private JTable tableCars;
	private Controller controller;
	private JFrame frame;
	private Rent rent;
	private String garageOrigin;

	
	public SelectCarGUI(Controller controller, Rent rent,String garageOrigin) throws RemoteException{
		this.controller=controller;
		this.garageOrigin= garageOrigin;
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
		frame.setBounds(100, 100, 556, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectACar = new JLabel("Select a car");
		lblSelectACar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblSelectACar.setBounds(206, 22, 94, 22);
		frame.getContentPane().add(lblSelectACar);
		
		JLabel lblTotalPrice = new JLabel("Total price:");
		lblTotalPrice.setBounds(28, 223, 76, 14);
		frame.getContentPane().add(lblTotalPrice);
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		textPane.setBounds(88, 217, 116, 20);
		frame.getContentPane().add(textPane);
		
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
				false,false, false
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
//	        ArrayList<Car> carsAvailable =controller.getCarsAvailable(rent.getGarageOrigin(), rent.getStartingDate(), rent.getFinishingDate());
		 	ArrayList<Car> carsAvailable =controller.getCars(garageOrigin);
	        Object rowData[] = new Object[3];
	        for(int i = 0; i < carsAvailable.size(); i++)
	        {
	            rowData[0] = carsAvailable.get(i).getBrand();
	            rowData[1] = carsAvailable.get(i).getModel();
	            rowData[2] = carsAvailable.get(i).getPricePerDay();

	            model.addRow(rowData);
	        }
		scrollPane.setViewportView(tableCars);
		tableCars.setShowGrid(false);
		tableCars.setShowVerticalLines(false);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					new WelcomeGUI(controller, rent);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBack.setBounds(412, 240, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnNext = new JButton("Next");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					new ClientDataGUI(controller,rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNext.setBounds(313, 240, 89, 23);
		frame.getContentPane().add(btnNext);
	}
}
