package carrenting.client.gui;

import java.awt.Color;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import carrenting.client.Controller;
import carrenting.server.jdo.Rent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;
import javax.swing.JFormattedTextField.AbstractFormatter;


@SuppressWarnings("serial")
public class StaffPanelGUI extends JFrame {
	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private String staffType;
	private Rent rent;
	private JTable table;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JFormattedTextField textFieldPpd= new JFormattedTextField(getMaskFormatter("###.#"));
	private double pricePerDay=0.0;
	private boolean allOK = true;
	private ArrayList<String> garages;
	private ArrayList<String> numPlates;
	private JTextField textFieldNumPlate;
	private JTextField textFieldBrand;
	private JTextField textFieldModel;
	

	
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
	
	public StaffPanelGUI(Controller controller, String staffType, Rent rent) throws RemoteException{
		this.controller=controller;
		this.staffType=staffType;
		this.rent=rent;
		numPlates=controller.getAllNumPlates();
		garages = controller.getGarages();
		initialize();
		frame.setVisible(true);
	}
	
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize () throws RemoteException {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1164, 591);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		frame.setContentPane(contentPane);
		JButton btnReturnToStartpage = new JButton(controller.getResourcebundle().getString("return_startpage"));
		btnReturnToStartpage.setBounds(805, 525, 332, 23);
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
		contentPane.setLayout(null);
		btnReturnToStartpage.setForeground(Color.BLACK);
		contentPane.add(btnReturnToStartpage);
		ArrayList<Rent> rents = controller.getRents();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1144, 480);
		contentPane.add(tabbedPane);
		
		JPanel panelrent = new JPanel();
		tabbedPane.addTab("Rents", null, panelrent, null);
		panelrent.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 1100, 377);
		panelrent.add(scrollPane);
		
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
		table.getColumnModel().getColumn(1).setPreferredWidth(110);
		table.getColumnModel().getColumn(2).setPreferredWidth(110);
		table.getColumnModel().getColumn(5).setPreferredWidth(85);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
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
			
			JLabel lblRents = new JLabel(controller.getResourcebundle().getString("rents"));
			lblRents.setBounds(465, 11, 245, 22);
			panelrent.add(lblRents);
			lblRents.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		
				
				
				JPanel panelAddCars = new JPanel();
				tabbedPane.addTab("Add cars", null, panelAddCars, null);
				panelAddCars.setLayout(null);
				
				JLabel label = new JLabel("Add a car");
				label.setBounds(148, 33, 207, 22);
				label.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
				panelAddCars.add(label);
				
				JLabel label_1 = new JLabel("Number plate");
				label_1.setBounds(33, 80, 98, 14);
				panelAddCars.add(label_1);
				
				textFieldNumPlate = new JTextField();
				textFieldNumPlate.setBounds(148, 77, 185, 20);
				textFieldNumPlate.setColumns(10);
				panelAddCars.add(textFieldNumPlate);
				
				JLabel label_2 = new JLabel("Brand");
				label_2.setBounds(33, 114, 98, 14);
				panelAddCars.add(label_2);
				
				textFieldBrand = new JTextField();
				textFieldBrand.setBounds(148, 108, 185, 20);
				textFieldBrand.setColumns(10);
				panelAddCars.add(textFieldBrand);
				
				JLabel label_3 = new JLabel("Model");
				label_3.setBounds(33, 148, 98, 14);
				panelAddCars.add(label_3);
				
				textFieldModel = new JTextField();
				textFieldModel.setBounds(148, 139, 185, 20);
				textFieldModel.setColumns(10);
				panelAddCars.add(textFieldModel);
				
				JLabel label_4 = new JLabel("Garage");
				label_4.setBounds(33, 173, 98, 14);
				panelAddCars.add(label_4);
				
				JComboBox comboBox = new JComboBox();
				comboBox.setBounds(147, 170, 186, 20);
				comboBox.setModel(new DefaultComboBoxModel(garages.toArray()));
				panelAddCars.add(comboBox);
				
				JLabel label_5 = new JLabel("Price per day");
				label_5.setBounds(33, 206, 98, 14);
				panelAddCars.add(label_5);
				
				
				textFieldPpd.setBounds(148, 203, 183, 20);
				textFieldPpd.setColumns(10);
				panelAddCars.add(textFieldPpd);
				
				JButton button = new JButton("Add");
				button.setBounds(238, 285, 89, 23);
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
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
								try {
									controller.storeCar(comboBox.getSelectedItem().toString(), textFieldNumPlate.getText(), textFieldBrand.getText(), textFieldModel.getText(), (int) pricePerDay);
									System.out.println(controller.getCar(textFieldNumPlate.getText()));
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}


						}
					}
				});
				panelAddCars.add(button);


		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Remove cars", null, panel, null);
//		
//		if(staffType.equalsIgnoreCase("employee")) {
//			// Disable the tab
//		    tabbedPane.setEnabledAt(1, false);
//		    tabbedPane.setEnabledAt(2, false);
//		}
//		
	}
}
