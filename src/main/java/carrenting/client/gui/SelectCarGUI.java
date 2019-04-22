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
	private double price;
	private double totalPrice;
	private String numberPlate;
	
	public SelectCarGUI(Controller controller, Rent rent) throws RemoteException{
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
		frame.setBounds(100, 100, 556, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		ArrayList<Car> carsAvailable =controller.getCarsAvailable(rent.getGarageOrigin(), rent.getStartingDate(), rent.getFinishingDate());
		JLabel lblSelectACar = new JLabel(controller.getResourcebundle().getString("select_car"));
		lblSelectACar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblSelectACar.setBounds(97, 22, 363, 22);
		frame.getContentPane().add(lblSelectACar);
		
		JLabel lblTotalPrice = new JLabel(controller.getResourcebundle().getString("total_price"));
		lblTotalPrice.setBounds(28, 223, 122, 14);
		frame.getContentPane().add(lblTotalPrice);
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		textPane.setBounds(123, 217, 107, 20);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 53, 471, 139);
		frame.getContentPane().add(scrollPane);
		
		tableCars = new JTable();
		tableCars.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				price=(double)tableCars.getValueAt(tableCars.getSelectedRow(), 2);
				System.out.println(price);
				totalPrice=controller.daysBetween(rent.getStartingDate(), rent.getFinishingDate())*price;
				textPane.setText(String.valueOf(totalPrice));
				frame.getContentPane().add(textPane);
				numberPlate=carsAvailable.get(tableCars.getSelectedRow()).getNumPlate();
				System.out.println("number plate  " + numberPlate);
				
				
			}
		});
		tableCars.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				controller.getResourcebundle().getString("brand"), controller.getResourcebundle().getString("model"), controller.getResourcebundle().getString("price_per_day")
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
		tableCars.changeSelection(0, 0, false, false);
		price=(double)tableCars.getValueAt(tableCars.getSelectedRow(), 2);
		totalPrice=controller.daysBetween(rent.getStartingDate(), rent.getFinishingDate())*price;
		textPane.setText(String.valueOf(totalPrice));
		frame.getContentPane().add(textPane);
		JButton btnBack = new JButton(controller.getResourcebundle().getString("back"));
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
		btnBack.setBounds(310, 240, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnNext = new JButton(controller.getResourcebundle().getString("next"));
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					rent.setNumberPlate(numberPlate);
					rent.setTotalPrice(totalPrice);
					new ClientDataGUI(controller,rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNext.setBounds(420, 240, 89, 23);
		frame.getContentPane().add(btnNext);
	}
}
