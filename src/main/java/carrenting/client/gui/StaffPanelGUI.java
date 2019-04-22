package carrenting.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class StaffPanelGUI extends JFrame {
	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private String staffType;
	private Rent rent;
	private JTable table;

	
	public StaffPanelGUI(Controller controller, String staffType, Rent rent) throws RemoteException{
		this.controller=controller;
		this.staffType=staffType;
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
		frame.setBounds(100, 100, 1084, 500);
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
		btnReturnToStartpage.setBounds(726, 398, 332, 23);
		contentPane.add(btnReturnToStartpage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(70, 114, 923, 273);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"NumberPlate", "StartingDate", "FinishingDate", "GarageOrigin", "GarageDestination", "PaymentSystem", "TotalPrice", "UserID"
			}
		));
		table.getColumnModel().getColumn(5).setPreferredWidth(85);
		scrollPane.setViewportView(table);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		 ArrayList<Rent> rents = controller.getRents();

	        Object rowData[] = new Object[11];
	        for(int i = 0; i < rents.size(); i++)
	        {
	        	rowData[0] = rents.get(i).getNumberPlate();
	        	rowData[1] = rents.get(i).getStartingDate();	      
	        	rowData[2] = rents.get(i).getFinishingDate();
	        	rowData[3] = rents.get(i).getGarageOrigin();
	        	rowData[4] = rents.get(i).getGarageDestination();
	            rowData[5] = rents.get(i).getPaymentSystem();
	            rowData[6] = rents.get(i).getTotalPrice();
	            rowData[7] = rents.get(i).getUserId();
	            model.addRow(rowData);
	        }
		scrollPane.setViewportView(table);
		
	
		JPanel panelAdmin = new JPanel();
		panelAdmin.setBounds(24, 11, 984, 48);
		contentPane.add(panelAdmin);
		panelAdmin.setLayout(null);
		
		JButton btnAddCars = new JButton("Add cars");
		btnAddCars.setBounds(10, 11, 144, 23);
		panelAdmin.add(btnAddCars);
		
		JButton btnAddGarages = new JButton("Add garages");
		btnAddGarages.setBounds(407, 11, 144, 23);
		panelAdmin.add(btnAddGarages);
		
		JButton btnRemoveCars = new JButton("Remove cars");
		btnRemoveCars.setBounds(185, 11, 160, 23);
		panelAdmin.add(btnRemoveCars);
		
		JButton btnNewButton = new JButton("Remove garages");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(588, 11, 160, 23);
		panelAdmin.add(btnNewButton);
		
		JButton btnViewStatistics = new JButton("View statistics");
		btnViewStatistics.setBounds(814, 11, 160, 23);
		panelAdmin.add(btnViewStatistics);
		
		JLabel lblRents = new JLabel(controller.getResourcebundle().getString("rents"));
		lblRents.setBounds(482, 81, 245, 22);
		contentPane.add(lblRents);
		lblRents.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		if (staffType.equalsIgnoreCase("employee")) {
			panelAdmin.setVisible(false);
		}else {
			panelAdmin.setVisible(true);
		}
		
	}
}
