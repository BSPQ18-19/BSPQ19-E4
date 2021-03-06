package carrenting.client.gui;

import java.awt.Color;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

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
	private JTable tableGarages;
	private JTable tablePaySys;
	private JTable tableModelCars;
	private JTable tableBrandCars;


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
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1170, 591);
		contentPane = new JPanel();
	
		contentPane.setBackground((Color.decode("#EDFCFC")));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		frame.setContentPane(contentPane);
		JButton btnReturnToStartpage = new JButton(controller.getResourcebundle().getString("return_startpage"));
		btnReturnToStartpage.setBounds(812, 518, 332, 23);
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
		tabbedPane.setBounds(0, 0, 1154, 495);
		contentPane.add(tabbedPane);
		
		JPanel panelrent = new JPanel();
		panelrent.setBackground(Color.WHITE);
		tabbedPane.addTab(controller.getResourcebundle().getString("rents"), null, panelrent, null);
		panelrent.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 121, 1100, 335);
		panelrent.add(scrollPane);
		

		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					controller.getResourcebundle().getString("number_plate"), controller.getResourcebundle().getString("starting_date"), controller.getResourcebundle().getString("finishing_date"), controller.getResourcebundle().getString("garage_origin"),
					controller.getResourcebundle().getString("garage_destination"), controller.getResourcebundle().getString("payment_system"), controller.getResourcebundle().getString("total_price"), controller.getResourcebundle().getString("user_ID")
			}
		));
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(140);
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
			lblRents.setBounds(500, 38, 210, 30);
			panelrent.add(lblRents);
			lblRents.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 22));
			

			ImageIcon imageRentIcon = new ImageIcon("img/rent.png"); // load the imageRent to a imageRentIcon
			Image imageRent = imageRentIcon.getImage(); // transform it 
			Image newimgRent = imageRent.getScaledInstance(140, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageRentIcon = new ImageIcon(newimgRent);  // transform it back
			JLabel lblImgRent=new JLabel();
			lblImgRent.setIcon(imageRentIcon);
			lblImgRent.setBounds(232, 11, 182, 100);
			panelrent.add(lblImgRent);
		
			
			
			
				JPanel panelManageCars = new JPanel();
				panelManageCars.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				panelManageCars.setBackground(Color.WHITE);
				tabbedPane.addTab(controller.getResourcebundle().getString("car_management"), null, panelManageCars, null);
				panelManageCars.setLayout(null);
				
				JPanel panelAddCars = new JPanel();
				panelAddCars.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				panelAddCars.setBackground(Color.WHITE);
				panelAddCars.setBounds(36, 104, 295, 337);
				panelManageCars.add(panelAddCars);
				panelAddCars.setLayout(null);
				
				JLabel labelAddACar = new JLabel(controller.getResourcebundle().getString("add_car"));
				labelAddACar.setBounds(65, 11, 207, 22);
				panelAddCars.add(labelAddACar);
				labelAddACar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
				
				JLabel labelNumberPlate = new JLabel(controller.getResourcebundle().getString("number_plate"));
				labelNumberPlate.setBounds(8, 47, 98, 14);
				panelAddCars.add(labelNumberPlate);
				
				textFieldNumPlate = new JTextField();
				textFieldNumPlate.setBounds(123, 44, 147, 20);
				panelAddCars.add(textFieldNumPlate);
				textFieldNumPlate.setColumns(10);
				
				JLabel labelBrand = new JLabel(controller.getResourcebundle().getString("brand"));
				labelBrand.setBounds(8, 78, 98, 14);
				panelAddCars.add(labelBrand);
				
				textFieldBrand = new JTextField();
				textFieldBrand.setBounds(123, 75, 147, 20);
				panelAddCars.add(textFieldBrand);
				textFieldBrand.setColumns(10);
				
				JLabel labelModel = new JLabel(controller.getResourcebundle().getString("model"));
				labelModel.setBounds(8, 109, 98, 14);
				panelAddCars.add(labelModel);
				
				textFieldModel = new JTextField();
				textFieldModel.setBounds(123, 106, 149, 20);
				panelAddCars.add(textFieldModel);
				textFieldModel.setColumns(10);
				
				JLabel labelGarage = new JLabel(controller.getResourcebundle().getString("garage"));
				labelGarage.setBounds(8, 143, 98, 14);
				panelAddCars.add(labelGarage);
				
				JComboBox comboBoxGarages = new JComboBox();
				comboBoxGarages.setBounds(123, 137, 149, 20);
				panelAddCars.add(comboBoxGarages);
				DefaultComboBoxModel comboBoxGaragesModel= new DefaultComboBoxModel(garages.toArray());
				comboBoxGarages.setModel(comboBoxGaragesModel);
				
				JLabel labelPricePerDsy = new JLabel(controller.getResourcebundle().getString("price_per_day"));
				labelPricePerDsy.setBounds(8, 168, 117, 14);
				panelAddCars.add(labelPricePerDsy);
				textFieldPpd.setBounds(123, 168, 149, 20);
				panelAddCars.add(textFieldPpd);
				textFieldPpd.setColumns(10);
				
				JButton buttonAdd = new JButton(controller.getResourcebundle().getString("add"));
				buttonAdd.setBounds(183, 215, 89, 23);
				panelAddCars.add(buttonAdd);
				
				JPanel panelRemoveCars= new JPanel();
				panelRemoveCars.setBackground(Color.WHITE);
				panelRemoveCars.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				panelRemoveCars.setBounds(364, 104, 538, 337);
				panelManageCars.add(panelRemoveCars);
				panelRemoveCars.setLayout(null);
				
				JLabel labelCarsInSystem = new JLabel(controller.getResourcebundle().getString("cars"));
				labelCarsInSystem.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
				labelCarsInSystem.setBounds(223, 11, 120, 24);
				panelRemoveCars.add(labelCarsInSystem);
				
				JScrollPane scrollPaneRemoveCars = new JScrollPane();
				scrollPaneRemoveCars.setBounds(27, 46, 481, 237);
				panelRemoveCars.add(scrollPaneRemoveCars);
				
				tableRemoveCars = new JTable();
				tableRemoveCars.setBackground(Color.WHITE);
				tableRemoveCars.setShowVerticalLines(false);
				tableRemoveCars.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
							controller.getResourcebundle().getString("number_plate"), controller.getResourcebundle().getString("brand"),
							controller.getResourcebundle().getString("garage"), controller.getResourcebundle().getString("model"), controller.getResourcebundle().getString("price_per_day")
					}
				));
				tableRemoveCars.getColumnModel().getColumn(0).setPreferredWidth(90);
				tableRemoveCars.getColumnModel().getColumn(4).setPreferredWidth(105);
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

				JButton buttonRemoveCar = new JButton(controller.getResourcebundle().getString("remove_cars"));
				
				buttonRemoveCar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(tableRemoveCars.getSelectedRow()!=-1) {
							try {
								controller.deleteCar(cars.get(tableRemoveCars.getSelectedRow()).getNumPlate());
							} catch (RemoteException e) {
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
				buttonRemoveCar.setBounds(344, 294, 164, 23);
				panelRemoveCars.add(buttonRemoveCar);
				
				JPanel panelRelocation = new JPanel();
				panelRelocation.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				panelRelocation.setBackground(Color.WHITE);
				panelRelocation.setBounds(912, 93, 227, 348);
				panelManageCars.add(panelRelocation);
				panelRelocation.setLayout(null);
				
				JButton btnRelocateCar = new JButton(controller.getResourcebundle().getString("relocate_car"));

				btnRelocateCar.setBounds(58, 243, 146, 23);
				panelRelocation.add(btnRelocateCar);
				
				JLabel lblGarageToMove = new JLabel(controller.getResourcebundle().getString("garage_to_move_the_car_to"));
				lblGarageToMove.setBounds(10, 63, 207, 14);
				panelRelocation.add(lblGarageToMove);
				
				JScrollPane scrollPaneRelocate = new JScrollPane();
				scrollPaneRelocate.setBounds(20, 88, 184, 144);
				panelRelocation.add(scrollPaneRelocate);
				final DefaultListModel<String> modelGarages = new DefaultListModel<String>();
				JList listGarages = new JList(modelGarages);
				listGarages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				ArrayList<String> garages = controller.getGarages();
				for(String garage: garages) {
					modelGarages.addElement(garage);
				}
				scrollPaneRelocate.setViewportView(listGarages);
				

				btnRelocateCar.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(tableRemoveCars.getSelectedRow()!=-1 && !listGarages.getSelectedValue().toString().equalsIgnoreCase("")) {
							try {
								controller.updateGarage(cars.get(tableRemoveCars.getSelectedRow()).getNumPlate(), listGarages.getSelectedValue().toString());
								cars.clear();				
								for (int i = tableRemoveCars.getRowCount() - 1; i >= 0; i--) {
									modelRemoveCars.removeRow(i);
								}
								modelRemoveCars.fireTableDataChanged();
								tableRemoveCars.addNotify();
								cars.addAll(controller.getAllCars());
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
								modelRemoveCars.fireTableDataChanged();
								tableRemoveCars.addNotify();
								tableRemoveCars.repaint();
								

							} catch (RemoteException e1) {

								e1.printStackTrace();
							}						
						}
						else {
							JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("select_car_garage"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
							
						}	
					}
				});
				
				
				
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
							
							e2.printStackTrace();
						} catch (RemoteException e2) {
							
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
									JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("car_added_successfuly"), controller.getResourcebundle().getString("successful"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
									Car addedCar= new Car(comboBoxGarages.getSelectedItem().toString(), textFieldNumPlate.getText(), textFieldBrand.getText(), textFieldModel.getText(), (int) pricePerDay);
									controller.storeCar(comboBoxGarages.getSelectedItem().toString(), textFieldNumPlate.getText(), textFieldBrand.getText(), textFieldModel.getText(), (int) pricePerDay);
									
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
									textFieldNumPlate.setText("");
									textFieldBrand.setText("");
									textFieldModel.setText("");
									textFieldLocation.setText("");
									
									
								} catch (RemoteException e1) {
	
									e1.printStackTrace();
								}
						}
					}
				});
		
		final DefaultListModel<String> modelDeleteGarages = new DefaultListModel<String>();
		ArrayList<String> values = controller.getGarages();
		for(String garage: values) {
			modelDeleteGarages.addElement(garage);
		}
		
				
				JPanel panelManageGarages = new JPanel();
				panelManageGarages.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				panelManageGarages.setBackground(Color.WHITE);
				tabbedPane.addTab(controller.getResourcebundle().getString("garage_management"), null, panelManageGarages, null);
				panelManageGarages.setLayout(null);
				
				JPanel panelAddGarage = new JPanel();
				panelAddGarage.setBounds(34, 85, 369, 200);
				panelAddGarage.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				panelAddGarage.setBackground(Color.WHITE);
				panelManageGarages.add(panelAddGarage);
				panelAddGarage.setLayout(null);
				
						JLabel lblAddNewGarages = new JLabel(controller.getResourcebundle().getString("add_new_garage"));
						lblAddNewGarages.setBounds(109, 11, 232, 24);
						panelAddGarage.add(lblAddNewGarages);
						lblAddNewGarages.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
						
						textFieldLocation = new JTextField();
						textFieldLocation.setBounds(162, 82, 156, 20);
						panelAddGarage.add(textFieldLocation);
						textFieldLocation.setColumns(10);
						
						JLabel lblLocation = new JLabel(controller.getResourcebundle().getString("location"));
						lblLocation.setBounds(68, 85, 84, 14);
						panelAddGarage.add(lblLocation);
						
						JButton btnAddGarage = new JButton(controller.getResourcebundle().getString("add_garage"));
						btnAddGarage.setBounds(229, 144, 125, 45);
						panelAddGarage.add(btnAddGarage);
						
						JPanel panelDeleteGarage = new JPanel();
						panelDeleteGarage.setBounds(626, 80, 464, 298);
						panelDeleteGarage.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
						panelDeleteGarage.setBackground(Color.WHITE);
						panelManageGarages.add(panelDeleteGarage);
						panelDeleteGarage.setLayout(null);
						
						JLabel labelDeleteGarage = new JLabel(controller.getResourcebundle().getString("delete_garage"));
						labelDeleteGarage.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
						labelDeleteGarage.setBounds(187, 24, 155, 24);
						panelDeleteGarage.add(labelDeleteGarage);
						
						JLabel lblGarage = new JLabel(controller.getResourcebundle().getString("garage"));
						lblGarage.setBounds(92, 78, 84, 14);
						panelDeleteGarage.add(lblGarage);
						
						JScrollPane scrollPaneDeleteGarage = new JScrollPane();
						scrollPaneDeleteGarage.setBounds(161, 76, 181, 138);
						panelDeleteGarage.add(scrollPaneDeleteGarage);
						JList listDeleteGarage = new JList(modelDeleteGarages);
						listDeleteGarage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						scrollPaneDeleteGarage.setViewportView(listDeleteGarage);
						
						
						
						
						JButton btnDeleteGarageCars = new JButton(controller.getResourcebundle().getString("delete_garage_cars"));
						btnDeleteGarageCars.addMouseListener(new MouseAdapter() {
							boolean deleteGarageOk;
							@Override
							public void mouseClicked(MouseEvent e) {
								deleteGarageOk =true;
								
									if(listDeleteGarage.getSelectedIndex()==-1) {
										JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("choose_garage"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
										deleteGarageOk=false;
									}
							
									if(deleteGarageOk) {
										try {
											controller.deleteGarageAndItsCars(listDeleteGarage.getSelectedValue().toString());

											cars.clear();				
											for (int i = tableRemoveCars.getRowCount() - 1; i >= 0; i--) {
												modelRemoveCars.removeRow(i);
											}
											modelRemoveCars.fireTableDataChanged();
											tableRemoveCars.addNotify();
											cars.addAll(controller.getAllCars());
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
											modelRemoveCars.fireTableDataChanged();
											tableRemoveCars.addNotify();
											tableRemoveCars.repaint();

											modelGarages.removeElementAt(listDeleteGarage.getSelectedIndex());
											modelDeleteGarages.removeElementAt(listDeleteGarage.getSelectedIndex());
											comboBoxGarages.removeItem(textFieldLocation.getText());

										} catch (RemoteException e1) {
			
											e1.printStackTrace();
										}
									}
							}
						});
						
						btnDeleteGarageCars.setBounds(289, 244, 165, 48);
						panelDeleteGarage.add(btnDeleteGarageCars);
						
						JButton btnDeleteGarage = new JButton(controller.getResourcebundle().getString("delete_garage"));
						btnDeleteGarage.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								boolean allOk=true;
								if(listDeleteGarage.getSelectedIndex()==-1) {
									JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("choose_garage"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
									allOk=false;
								}
								if(allOk) {
									try {
										controller.deleteGarage(listDeleteGarage.getSelectedValue().toString());
										modelDeleteGarages.removeElementAt(listDeleteGarage.getSelectedIndex());
										comboBoxGarages.removeItem(textFieldLocation.getText());
										listGarages.setModel(modelDeleteGarages);

										
										
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								}
								
								
							}
						});
						btnDeleteGarage.setBounds(92, 249, 145, 38);
						panelDeleteGarage.add(btnDeleteGarage);
						
						ImageIcon imageIconGarage = new ImageIcon("img/garage.png"); // load the imageGarage to a imageIconGarage
						Image imageGarage = imageIconGarage.getImage(); // transform it 
						Image newimgGarage = imageGarage.getScaledInstance(130, 130,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
						imageIconGarage = new ImageIcon(newimgGarage);  // transform it back
						JLabel lblImgGarage=new JLabel();
						lblImgGarage.setBounds(472, 69, 187, 155);
						lblImgGarage.setIcon(imageIconGarage);
						panelManageGarages.add(lblImgGarage);
						
						JLabel lblManageGarages = new JLabel(controller.getResourcebundle().getString("garage_management"));
						lblManageGarages.setBounds(440, 11, 260, 34);
						lblManageGarages.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
						panelManageGarages.add(lblManageGarages);
						

						
						
						
						btnAddGarage.addMouseListener(new MouseAdapter() {
							boolean allGaragesOk= true;
							@Override
							public void mouseClicked(MouseEvent e) {
								allGaragesOk=true;
								if(textFieldLocation.getText().equals("")) {
									JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("enter_location"), controller.getResourcebundle().getString("careful"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
									allGaragesOk=false;
								}
								try {
									if(!controller.newGarageAvailable(textFieldLocation.getText())){
										JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("already_exist_garage"), controller.getResourcebundle().getString("error"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
										allGaragesOk=false;
									}
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}if(allGaragesOk) {
									try {
										controller.storeGarage(textFieldLocation.getText());
										JOptionPane.showConfirmDialog(null, controller.getResourcebundle().getString("garage_added_successfuly"), controller.getResourcebundle().getString("successful"), JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
										comboBoxGarages.addItem(textFieldLocation.getText());
										modelDeleteGarages.addElement(textFieldLocation.getText());
										modelGarages.addElement(textFieldLocation.getText());
										
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								}
							}
						});
		
		JPanel panelStatistics = new JPanel();
		panelStatistics.setBackground(Color.WHITE);
		tabbedPane.addTab(controller.getResourcebundle().getString("statistics"), null, panelStatistics, null);
		panelStatistics.setLayout(null);
		JLabel lblGaragePopularity= new JLabel(controller.getResourcebundle().getString("garage_popularity"));
		lblGaragePopularity.setBounds(107, 51, 283, 14);
		panelStatistics.add(lblGaragePopularity);
		
		JScrollPane scrollPaneStatisticsGarages = new JScrollPane();
		scrollPaneStatisticsGarages.setBounds(105, 76, 331, 129);
		panelStatistics.add(scrollPaneStatisticsGarages);
		
       
		
		tableGarages = new JTable();
		tableGarages.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					controller.getResourcebundle().getString("garage"), controller.getResourcebundle().getString("times_as_origin"), controller.getResourcebundle().getString("times_as_destination")
			}
		));
		tableGarages.getColumnModel().getColumn(0).setPreferredWidth(110);
		tableGarages.getColumnModel().getColumn(1).setPreferredWidth(110);
		tableGarages.getColumnModel().getColumn(2).setPreferredWidth(140);
		tableGarages.setEnabled(false);
		tableGarages.setShowVerticalLines(false);
		tableGarages.setRowSelectionAllowed(false);
		
		 Object garagePopularity[][] = controller.garagePopularity();
        Object rowGaragePop[] = new Object[3];
        for(int j = 0; j < garagePopularity.length ; j++)
        {
        	for(int i=0; i<garagePopularity[0].length; i++) {
	        	rowGaragePop[i] = garagePopularity[j][i];
        	}
        	((DefaultTableModel) tableGarages.getModel()).addRow(rowGaragePop);
        }
		
		
		scrollPaneStatisticsGarages.setViewportView(tableGarages);
		
		JLabel labelStatistics = new JLabel(controller.getResourcebundle().getString("statistics"));
		labelStatistics.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		labelStatistics.setBounds(504, 11, 194, 22);
		panelStatistics.add(labelStatistics);
		
		JLabel lblPaymentSystemPopularity = new JLabel(controller.getResourcebundle().getString("payment_system_popularity"));
		lblPaymentSystemPopularity.setBounds(108, 253, 282, 14);
		panelStatistics.add(lblPaymentSystemPopularity);
		
		JScrollPane scrollPaneStatisticsPayment = new JScrollPane();
		scrollPaneStatisticsPayment.setBounds(107, 278, 329, 84);
		panelStatistics.add(scrollPaneStatisticsPayment);
		
		tablePaySys = new JTable();
		tablePaySys.setRowSelectionAllowed(false);
		tablePaySys.setEnabled(false);
		tablePaySys.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				controller.getResourcebundle().getString("payment_system"), controller.getResourcebundle().getString("times_used")
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tablePaySys.getColumnModel().getColumn(0).setPreferredWidth(110);
		Object paymentPopularity[][] = controller.paymentPopularity();

	        Object rowPaymentPopularity[] = new Object[2];
	        for(int j = 0; j < paymentPopularity.length ; j++)
	        {
	        	for(int i=0; i<paymentPopularity[0].length; i++) {
		        	rowPaymentPopularity[i] = paymentPopularity[j][i];
	        	}
	        	((DefaultTableModel) tablePaySys.getModel()).addRow(rowPaymentPopularity);
	        }
		scrollPaneRemoveCars.setViewportView(tableRemoveCars);	
		
		JLabel lblCarManagement = new JLabel("Car management");
		lblCarManagement.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblCarManagement.setBounds(473, 22, 207, 22);
		panelManageCars.add(lblCarManagement);
		
		
		

		ImageIcon imageCarIcon = new ImageIcon("img/car.png"); // load the imageCar to a imageCarIcon
		Image imageCar = imageCarIcon.getImage(); // transform it 
		Image newimgCar = imageCar.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageCarIcon = new ImageIcon(newimgCar);  // transform it back
		JLabel lblImgCar=new JLabel();
		lblImgCar.setIcon(imageCarIcon);
		lblImgCar.setBounds(212, 0, 239, 86);
		panelManageCars.add(lblImgCar);


	
		
		
		
		scrollPaneStatisticsPayment.setViewportView(tablePaySys);
		
		JLabel lblCarPopularity = new JLabel(controller.getResourcebundle().getString("car_popularity"));
		lblCarPopularity.setBounds(708, 50, 248, 14);
		panelStatistics.add(lblCarPopularity);
		
		JScrollPane scrollPaneStatisticsModelCars = new JScrollPane();
		scrollPaneStatisticsModelCars.setBounds(708, 75, 283, 155);
		panelStatistics.add(scrollPaneStatisticsModelCars);
		
		tableModelCars = new JTable();
		tableModelCars.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				controller.getResourcebundle().getString("model"), controller.getResourcebundle().getString("times_rented")
			}
		));
		tableModelCars.setRowSelectionAllowed(false);
		tableModelCars.setEnabled(false);
		
		Object carModelPopularity[][] = controller.carModelPopularity();
        Object rowCarModelPopularity[] = new Object[2];
        for(int j = 0; j < carModelPopularity.length ; j++)
        {
        	for(int i=0; i<carModelPopularity[0].length; i++) {
	        	rowCarModelPopularity[i] = carModelPopularity[j][i];
        	}
        	((DefaultTableModel) tableModelCars.getModel()).addRow(rowCarModelPopularity);
        }
		scrollPaneStatisticsModelCars.setViewportView(tableModelCars);
		
		JScrollPane scrollPaneStatisticsBrandCars = new JScrollPane();
		scrollPaneStatisticsBrandCars.setBounds(708, 275, 283, 129);
		panelStatistics.add(scrollPaneStatisticsBrandCars);
		
		tableBrandCars = new JTable();
		tableBrandCars.setEnabled(false);
		tableBrandCars.setRowSelectionAllowed(false);
		tableBrandCars.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				controller.getResourcebundle().getString("brand"), controller.getResourcebundle().getString("times_rented")
			}
		));
		Object carBrandPopularity[][] = controller.carBrandPopularity();
        Object rowCarBrandPopularity[] = new Object[2];
        for(int j = 0; j < carBrandPopularity.length ; j++)
        {
        	for(int i=0; i<carBrandPopularity[0].length; i++) {
	        	rowCarBrandPopularity[i] = carBrandPopularity[j][i];
        	}
        	((DefaultTableModel) tableBrandCars.getModel()).addRow(rowCarBrandPopularity);
        }
		
		scrollPaneStatisticsBrandCars.setViewportView(tableBrandCars);

		ImageIcon imageIcon = new ImageIcon("img/stat.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(170, 170,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		JLabel lblImgStat=new JLabel();
		lblImgStat.setIcon(imageIcon);
		lblImgStat.setBounds(465, 51, 168, 182);
		panelStatistics.add(lblImgStat);

		
		if(staffType.equalsIgnoreCase("employee")) {
			// Disable the tab
		    tabbedPane.setEnabledAt(1, false);
		    tabbedPane.setEnabledAt(2, false);
		    tabbedPane.setEnabledAt(3, false);
		}
		
	}
}
