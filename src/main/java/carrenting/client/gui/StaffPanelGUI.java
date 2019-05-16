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
import carrenting.server.jdo.Car;
import carrenting.server.jdo.Rent;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class StaffPanelGUI extends JFrame {
	private JPanel contentPane;
	private Controller controller;
	private JFrame frame;
	private String staffType;
	private Rent rent;
	private JTable table;
	private JFormattedTextField textFieldPpd= new JFormattedTextField(getMaskFormatter("###.#"));
	private double pricePerDay=0.0;
	private boolean allOK = true;
	private ArrayList<String> garages;
	private JTextField textFieldNumPlate;
	private JTextField textFieldBrand;
	private JTextField textFieldModel;
	private JTable tableRemoveCars;
	private JTextField textFieldLocation;
	private ArrayList<Car> cars;
	private boolean allGaragesOk= true;

	
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
				controller.getResourcebundle().getString("number_plate"), controller.getResourcebundle().getString("starting_date"), controller.getResourcebundle().getString("finishing_date"), controller.getResourcebundle().getString("garage_origin"), controller.getResourcebundle().getString("garage_destination"), controller.getResourcebundle().getString("payment_system"), controller.getResourcebundle().getString("total_price"), controller.getResourcebundle().getString("user_ID")
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
		
				
				
				JPanel panelManageCars = new JPanel();
				tabbedPane.addTab(controller.getResourcebundle().getString("add_car"), null, panelManageCars, null);
				panelManageCars.setLayout(null);
				
				JPanel panelAddCars = new JPanel();
				panelAddCars.setBounds(31, 33, 406, 408);
				panelManageCars.add(panelAddCars);
				panelAddCars.setLayout(null);
				
				JLabel labelAddACar = new JLabel(controller.getResourcebundle().getString("add_car"));
				labelAddACar.setBounds(170, 27, 207, 22);
				panelAddCars.add(labelAddACar);
				labelAddACar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
				
				JLabel labelNumberPlate = new JLabel(controller.getResourcebundle().getString("number_plate"));
				labelNumberPlate.setBounds(55, 74, 98, 14);
				panelAddCars.add(labelNumberPlate);
				
				textFieldNumPlate = new JTextField();
				textFieldNumPlate.setBounds(170, 71, 185, 20);
				panelAddCars.add(textFieldNumPlate);
				textFieldNumPlate.setColumns(10);
				
				JLabel labelBrand = new JLabel(controller.getResourcebundle().getString("brand"));
				labelBrand.setBounds(55, 125, 98, 14);
				panelAddCars.add(labelBrand);
				
				textFieldBrand = new JTextField();
				textFieldBrand.setBounds(170, 119, 185, 20);
				panelAddCars.add(textFieldBrand);
				textFieldBrand.setColumns(10);
				
				JLabel labelModel = new JLabel(controller.getResourcebundle().getString("model"));
				labelModel.setBounds(55, 167, 98, 14);
				panelAddCars.add(labelModel);
				
				textFieldModel = new JTextField();
				textFieldModel.setBounds(170, 164, 185, 20);
				panelAddCars.add(textFieldModel);
				textFieldModel.setColumns(10);
				
				JLabel labelGarage = new JLabel(controller.getResourcebundle().getString("garage"));
				labelGarage.setBounds(55, 216, 98, 14);
				panelAddCars.add(labelGarage);
				
				JComboBox comboBoxGarages = new JComboBox();
				comboBoxGarages.setBounds(170, 213, 186, 20);
				panelAddCars.add(comboBoxGarages);
//				comboBoxGarages.setModel(new DefaultComboBoxModel(garages.toArray()));
//				DefaultComboBoxModel modelListGarages = (DefaultComboBoxModel) comboBoxGarages.getModel();
				DefaultComboBoxModel comboBoxGaragesModel= new DefaultComboBoxModel(garages.toArray());
				comboBoxGarages.setModel(comboBoxGaragesModel);
//				comboBoxGarages.setModel(new DefaultComboBoxModel(garages.toArray()));
				
				JLabel labelPricePerDsy = new JLabel(controller.getResourcebundle().getString("price_per_day"));
				labelPricePerDsy.setBounds(55, 271, 98, 14);
				panelAddCars.add(labelPricePerDsy);
				textFieldPpd.setBounds(172, 268, 183, 20);
				panelAddCars.add(textFieldPpd);
				textFieldPpd.setColumns(10);
				
				JButton buttonAdd = new JButton(controller.getResourcebundle().getString("add"));
				buttonAdd.setBounds(266, 323, 89, 23);
				panelAddCars.add(buttonAdd);
				
				JPanel panelRemoveCars= new JPanel();
				panelRemoveCars.setBounds(494, 33, 621, 408);
				panelManageCars.add(panelRemoveCars);
				panelRemoveCars.setLayout(null);
				
				
				JLabel labelRemoveCars = new JLabel(controller.getResourcebundle().getString("remove_cars"));
				labelRemoveCars.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
				labelRemoveCars.setBounds(114, 38, 209, 24);
				panelRemoveCars.add(labelRemoveCars);
				
				JScrollPane scrollPaneRemoveCars = new JScrollPane();
				scrollPaneRemoveCars.setBounds(30, 72, 556, 237);
				panelRemoveCars.add(scrollPaneRemoveCars);
				
				tableRemoveCars = new JTable();
				tableRemoveCars.setShowVerticalLines(false);
				tableRemoveCars.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						controller.getResourcebundle().getString("number_plate"), controller.getResourcebundle().getString("brand"), controller.getResourcebundle().getString("garage"), controller.getResourcebundle().getString("model"), controller.getResourcebundle().getString("price_per_day")
					}
				) {
					boolean[] columnEditableRemoveCarss = new boolean[] {
						false, false, false, false, false
					};
					@SuppressWarnings("unused")
					public boolean isCellEditableRemoveCars(int row, int column) {
						return columnEditableRemoveCarss[column];
					}
				});
				tableRemoveCars.getColumnModel().getColumn(0).setPreferredWidth(90);
				DefaultTableModel modelRemoveCars = (DefaultTableModel) tableRemoveCars.getModel();
				cars = controller.getAllCars();
		
			        Object rowRemoveCars[] = new Object[5];
			        for(int i = 0; i < cars.size(); i++)
			        {
			        	rowRemoveCars[0] = cars.get(i).getNumPlate();
			        	rowRemoveCars[1] = cars.get(i).getBrand();	      
			        	rowRemoveCars[2] = cars.get(i).getGarage();
			        	rowRemoveCars[3] = cars.get(i).getModel();
			        	rowRemoveCars[4] = cars.get(i).getPricePerDay();
			            modelRemoveCars.addRow(rowRemoveCars);
			        }
				scrollPaneRemoveCars.setViewportView(tableRemoveCars);
				
				JButton buttonRemoveCar = new JButton(controller.getResourcebundle().getString("remove"));
				buttonRemoveCar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(tableRemoveCars.getSelectedRow()!=-1) {
							try {
								controller.deleteCar(cars.get(tableRemoveCars.getSelectedRow()).getNumPlate());
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							cars.remove(tableRemoveCars.getSelectedRow());
							modelRemoveCars.removeRow(tableRemoveCars.getSelectedRow());
							modelRemoveCars.fireTableDataChanged();
							tableRemoveCars.addNotify();
						}
						else {
							JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("choose_car"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				buttonRemoveCar.setBounds(422, 358, 164, 23);
				panelRemoveCars.add(buttonRemoveCar);
				
				
				
				buttonAdd.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						allOK=true;
						try {
							if(!controller.numberPlateAvailable(textFieldNumPlate.getText())){
								JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("number_plate_exists"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
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
							JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("price_higher_than_0"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
							allOK=false;
						}
						if(textFieldBrand.getText().equals("") || textFieldModel.getText().equals("") || textFieldNumPlate.getText().equals("")) {
							JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("all_fields_filled"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
							allOK=false;
						}
						if(allOK) {
								try {
									//TODO
									JOptionPane.showConfirmDialog(null, "Car added successfuly", "Successful", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
									Car addedCar= new Car(comboBoxGarages.getSelectedItem().toString(), textFieldNumPlate.getText(), textFieldBrand.getText(), textFieldModel.getText(), (int) pricePerDay);
									controller.storeCar(comboBoxGarages.getSelectedItem().toString(), textFieldNumPlate.getText(), textFieldBrand.getText(), textFieldModel.getText(), (int) pricePerDay);
									//TODO
									controller.getLogger().debug("Added car" + controller.getCar(textFieldNumPlate.getText()).toString());
									cars.add(addedCar);

						        	rowRemoveCars[0] = addedCar.getNumPlate();
						        	rowRemoveCars[1] = addedCar.getBrand();	      
						        	rowRemoveCars[2] = addedCar.getGarage();
						        	rowRemoveCars[3] = addedCar.getModel();
						        	rowRemoveCars[4] = addedCar.getPricePerDay();
						            modelRemoveCars.addRow(rowRemoveCars);
									modelRemoveCars.fireTableDataChanged();
									tableRemoveCars.addNotify();
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						}
					}
				});

		
		JPanel panelManageGarages = new JPanel();
		//TODO
		tabbedPane.addTab("Garage Management", null, panelManageGarages, null);
		panelManageGarages.setLayout(null);
		
		JPanel panelAddGarage = new JPanel();
		panelAddGarage.setBounds(42, 94, 410, 200);
		panelManageGarages.add(panelAddGarage);
		panelAddGarage.setLayout(null);
		//TODO
		JLabel lblAddNewGarages = new JLabel("Add a new garage");
		lblAddNewGarages.setBounds(110, 22, 232, 24);
		panelAddGarage.add(lblAddNewGarages);
		lblAddNewGarages.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		
		textFieldLocation = new JTextField();
		textFieldLocation.setBounds(162, 82, 156, 20);
		panelAddGarage.add(textFieldLocation);
		textFieldLocation.setColumns(10);
		
		//TODO
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setBounds(68, 85, 84, 14);
		panelAddGarage.add(lblLocation);
		
		//TODO
		JButton btnAddGarage = new JButton("Add garage");
		btnAddGarage.setBounds(267, 137, 110, 23);
		panelAddGarage.add(btnAddGarage);
		btnAddGarage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				allGaragesOk=true;
				if(textFieldLocation.getText().equals("")) {
					//TODO
					JOptionPane.showConfirmDialog(null, "You must enter a location", "Be careful", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
					allGaragesOk=false;
				}
				try {
					if(!controller.newGarageAvailable(textFieldLocation.getText())){
						//TODO
						JOptionPane.showConfirmDialog(null, "This garage already exists, you can't add an already existing garage", "Error", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
						allGaragesOk=false;
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}if(allGaragesOk) {
					try {
						controller.storeGarage(textFieldLocation.getText());
						//TODO
						JOptionPane.showConfirmDialog(null, "Garage added successfuly", "Successful", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
						comboBoxGaragesModel.notify();
						
						//TODO  Que en el tab de añadir coches aparezca el nuevo garage
						
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		
		if(staffType.equalsIgnoreCase("employee")) {
			// Disable the tab
		    tabbedPane.setEnabledAt(1, false);
		    tabbedPane.setEnabledAt(2, false);
		}
		
	}
}
