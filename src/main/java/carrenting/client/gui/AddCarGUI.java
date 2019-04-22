package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import carrenting.client.Controller;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.Rent;
import carrenting.server.jdo.Staff;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class AddCarGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNumPlate;
	private JTextField textFieldBrand;
	private JTextField textFieldModel;
	private JFrame frame;
	private Controller controller;
	private String staffType;
	private Rent rent;
	private ArrayList<String> numPlates;
	private ArrayList<String> garages;
	private boolean allOK = true;
	private JFormattedTextField textFieldPpd= new JFormattedTextField(getMaskFormatter("###.#"));
	private double pricePerDay=0.0;
	

	
	private MaskFormatter getMaskFormatter(String format) {
	    MaskFormatter mask = null;
	    try {
	        mask = new MaskFormatter(format);
	        mask.setPlaceholderCharacter('0');
	    }catch (ParseException ex) {
	        ex.printStackTrace();
	    }
	    return mask;
	}

	
	public AddCarGUI(Controller controller, String staffType, Rent rent) throws RemoteException {
		this.controller= controller;
		this.staffType= staffType;
		this.rent= rent;
		numPlates=controller.getAllNumPlates();
		garages = controller.getGarages();
		initialize();
		frame.setVisible(true);


	}

	
	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize() throws RemoteException {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 382, 340);
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
		lblGarage.setBounds(10, 180, 98, 14);
		contentPane.add(lblGarage);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setBounds(10, 155, 98, 14);
		contentPane.add(lblModel);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(garages.toArray()));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(125, 177, 186, 20);
		contentPane.add(comboBox);
		
		JLabel lblPricePerDay = new JLabel("Price per day");
		lblPricePerDay.setBounds(10, 213, 98, 14);
		contentPane.add(lblPricePerDay);
		
		textFieldPpd.setBounds(125, 210, 183, 20);
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
		btnCancel.setBounds(125, 259, 89, 23);
		contentPane.add(btnCancel);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				allOK=true;
				try {
					if(!controller.checkExistingNumPlate(textFieldNumPlate.getText())){
						JOptionPane.showConfirmDialog(null,"The number plate entered already exists", controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
						allOK=false;
					}
				} catch (HeadlessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				pricePerDay= Double.parseDouble(textFieldPpd.getText());
				if ((pricePerDay<=0)){
					JOptionPane.showConfirmDialog(null, "The price needs to be higher than 0", controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					allOK=false;
				}
				if(textFieldBrand.getText().equals("") || textFieldModel.getText().equals("") || textFieldNumPlate.getText().equals("")) {
					JOptionPane.showConfirmDialog(null, "All fields must be filled", controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					allOK=false;
				}
				if(allOK) {
					frame.dispose();
					try {
						new StaffPanelGUI(controller, staffType, rent);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						controller.storeCar("Bilbao", textFieldNumPlate.getText(), textFieldBrand.getText(), textFieldModel.getText(), (int) pricePerDay);
						System.out.println(controller.getCar(textFieldNumPlate.getText()));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//				listGarage.getSelectedValue().toString(),
				
			}
		});
		btnAdd.setBounds(243, 259, 89, 23);
		contentPane.add(btnAdd);
		


	}
}
