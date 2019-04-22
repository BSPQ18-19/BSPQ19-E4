package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;
import carrenting.server.jdo.Staff;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AddCarGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNumPlate;
	private JTextField textFieldBrand;
	private JTextField textFieldPpd;
	private JTextField textFieldModel;
	private JFrame frame;
	private Controller controller;
	private String staffType;
	private Rent rent;
	private ArrayList<String> garages;
	private JList<String> listGarages;
	
	public AddCarGUI(Controller controller, String staffType, Rent rent) {
		this.controller= controller;
		this.staffType= staffType;
		this.rent= rent;
		initialize();
		frame.setVisible(true);
	}

	
	/**
	 * Create the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 382, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelTitle = new JLabel("Add a car");
		labelTitle.setBounds(125, 40, 207, 22);
		labelTitle.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		contentPane.add(labelTitle);
		
		JLabel lblNewLabel = new JLabel("Number plate");
		lblNewLabel.setBounds(10, 87, 98, 14);
		contentPane.add(lblNewLabel);
		
		textFieldNumPlate = new JTextField();
		textFieldNumPlate.setBounds(124, 84, 185, 20);
		contentPane.add(textFieldNumPlate);
		textFieldNumPlate.setColumns(10);
		
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setBounds(10, 121, 98, 14);
		contentPane.add(lblBrand);
		
		textFieldBrand = new JTextField();
		textFieldBrand.setBounds(125, 115, 185, 20);
		contentPane.add(textFieldBrand);
		textFieldBrand.setColumns(10);
		
		JLabel lblGarage = new JLabel("Garage");
		lblGarage.setBounds(10, 190, 98, 14);
		contentPane.add(lblGarage);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setBounds(10, 155, 98, 14);
		contentPane.add(lblModel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(125, 188, 185, 80);
		contentPane.add(scrollPane);
		
		JList<Object> listGarage = new JList<Object>();
		scrollPane.setViewportView(listGarage);
		listGarage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			listGarage.setModel(new AbstractListModel<Object>() {
				private static final long serialVersionUID = 1L;
				ArrayList<String> values = controller.getGarages();
				public int getSize() {
					return values.size();
				}
				public Object getElementAt(int index) {
					return values.get(index);
				}
			});
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		listGarage.setSelectedIndex(0);
		

		
		
		JLabel lblPricePerDay = new JLabel("Price per day");
		lblPricePerDay.setBounds(10, 284, 98, 14);
		contentPane.add(lblPricePerDay);
		
		textFieldPpd = new JTextField();
		textFieldPpd.setBounds(125, 281, 183, 20);
		contentPane.add(textFieldPpd);
		textFieldPpd.setColumns(10);
		
		textFieldModel = new JTextField();
		textFieldModel.setBounds(125, 146, 185, 20);
		contentPane.add(textFieldModel);
		textFieldModel.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				try {
					new StaffPanelGUI(controller, staffType, rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCancel.setBounds(125, 340, 89, 23);
		contentPane.add(btnCancel);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		btnAdd.setBounds(250, 340, 89, 23);
		contentPane.add(btnAdd);
	}
}
