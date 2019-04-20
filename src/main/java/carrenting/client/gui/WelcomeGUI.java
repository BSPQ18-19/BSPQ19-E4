package carrenting.client.gui;



import javax.swing.AbstractListModel;

import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;



import carrenting.client.Controller;
import carrenting.server.jdo.Rent;

import javax.swing.JLabel;
import java.awt.Font;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

import java.awt.Color;
import javax.swing.JList;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JCalendar;
import javax.swing.JCheckBox;


/*TODO
 * Make the dates only selectable from today's date. And make sure return is after start
 * 
 */
@SuppressWarnings("serial")
public class WelcomeGUI extends JFrame {

	private JPanel contentPane;
	private Controller controller;
	private JFrame welcomeFrame;
	private JScrollPane scrollPaneDestination;
	JButton btnChange= new JButton();
	JLabel lblHi=new JLabel();
	JComboBox selectLanguage = new JComboBox<>();
	private Rent rent;
	private JDateChooser dateChooserFinish = new JDateChooser();
	private JDateChooser dateChooserStart = new JDateChooser();
	


	/**
	 * Launch the application.
	 * @throws RemoteException 
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					WelcomeGUI frame = new WelcomeGUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	public WelcomeGUI(Controller controller, Rent rent) throws RemoteException{
		this.controller=controller;
		this.rent=rent;
		initialize();
		welcomeFrame.setVisible(true);
		//initComponents(); (descomentar con lo de abajo(alternativa para el tema del idioma))
		
	}
	
	//ALTERNATIVA PARA EL TEMA DEL IDIOMA JUNTO CON LA CLASE LANGUAGE	
//	public void cambiarIdioma(String nameLanguage){
//		 
//        Language language=new Language(nameLanguage);
// 
//        btnChange.setText(language.getProperty("change"));
//        this.setTitle(language.getProperty("title"));
//        lblHi.setText(language.getProperty("saludo"));
// 
//        selectLanguage.removeAllItems();
// 
//        String languages[]={
//                          language.getProperty("espanol"),
//                          language.getProperty("ingles"),
//                          language.getProperty("frances")
//                          };
// 
//        for(int i=0;i<languages.length;i++){
//            selectLanguage.addItem(languages[i]);
//        }
// 
//    }
//
//	private void initComponents() {
//		// TODO Auto-generated method stub		
//		
//		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//		 
//        btnChange.setText("Prueba");
//        btnChange.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnChangeActionPerformed(evt);
//            }
//        });
// 
//        lblHi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        lblHi.setText("jLabel1");
// 
//        btnChange.setModel((ButtonModel) new DefaultComboBoxModel(new String[] { "Español", "Ingles", "Euskera" }));
//        
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addGap(145, 145, 145)
//                .addComponent(btnChange)
//                .addContainerGap(120, Short.MAX_VALUE))
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                    .addComponent(lblHi, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
//                    .addComponent(selectLanguage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                .addGap(75, 75, 75))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addGap(35, 35, 35)
//                .addComponent(lblHi, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(44, 44, 44)
//                .addComponent(selectLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(35, 35, 35)
//                .addComponent(btnChange)
//                .addContainerGap(67, Short.MAX_VALUE))
//        );
// 
//        pack();
//    }
//		
//
//	private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {                                          
//		 
//	    switch(selectLanguage.getSelectedIndex()){
//	
//	        case 0:
//	            cambiarIdioma("Español");
//	            break;
//	        case 1:
//	            cambiarIdioma("Ingles");
//	            break;
//	        case 2:
//	            cambiarIdioma("Euskera");
//	            break;
//	
//	    }
//	
//	}
//	
//	
//	public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(WelcomeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(WelcomeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(WelcomeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(WelcomeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
// 
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new WelcomeGUI(controller).setVisible(true);
//            }
//        });
//    }
	

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public void initialize() throws RemoteException {
		welcomeFrame = new JFrame();
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeFrame.setBounds(100, 100, 686, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		welcomeFrame.setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JLabel lblWelcomeToCar = new JLabel("Welcome to MyCarRenting");
		lblWelcomeToCar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblWelcomeToCar.setBounds(208, 50, 230, 22);
		welcomeFrame.getContentPane().add(lblWelcomeToCar);
		
		JLabel lblStaffArea = new JLabel("<html><u>STAFF area");
		lblStaffArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				welcomeFrame.dispose();
				try {
					new LogInStaffGUI(controller,rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblStaffArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
		lblStaffArea.setForeground(Color.BLUE);
		lblStaffArea.setBounds(564, 11, 69, 22);
		welcomeFrame.getContentPane().add(lblStaffArea);
		
		JLabel lblSelectAGarage = new JLabel("Select a garage of origin");
		lblSelectAGarage.setBounds(143, 106, 166, 14);
		welcomeFrame.getContentPane().add(lblSelectAGarage);
		
		JLabel lblSelectAGarage_1 = new JLabel("Select a garage of destination");
		lblSelectAGarage_1.setBounds(342, 106, 183, 14);
		welcomeFrame.getContentPane().add(lblSelectAGarage_1);
		
		JScrollPane scrollPaneOrigin = new JScrollPane();
		scrollPaneOrigin.setBounds(143, 128, 151, 80);
		contentPane.add(scrollPaneOrigin);
		
		JList<Object> listGarageOrigin = new JList<Object>();
		scrollPaneOrigin.setViewportView(listGarageOrigin);
		listGarageOrigin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGarageOrigin.setModel(new AbstractListModel<Object>() {
			private static final long serialVersionUID = 1L;
			ArrayList<String> values = controller.getGarages();
			public int getSize() {
				return values.size();
			}
			public Object getElementAt(int index) {
				return values.get(index);
			}
		});
		listGarageOrigin.setSelectedIndex(0);
		
		scrollPaneDestination = new JScrollPane();
		scrollPaneDestination.setBounds(342, 131, 141, 77);
		contentPane.add(scrollPaneDestination);
		
		JList<String> listGarageDestination = new JList<String>();
		scrollPaneDestination.setColumnHeaderView(listGarageDestination);
		listGarageDestination.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGarageDestination.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			ArrayList<String> values = controller.getGarages();
			public int getSize() {
				return values.size();
			}
			public String getElementAt(int index) {
				return values.get(index);
			}
		});
		listGarageDestination.setSelectedIndex(0);
		

		
		

		Date date = new Date(System.currentTimeMillis());  
		dateChooserStart.setMinSelectableDate(date);
		dateChooserStart.getCalendarButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dateChooserStart.setMaxSelectableDate(dateChooserFinish.getDate());
			}
		});
		dateChooserStart.setBounds(143, 286, 151, 20);
		contentPane.add(dateChooserStart);
		
		
		dateChooserFinish.getCalendarButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dateChooserFinish.setMinSelectableDate(dateChooserStart.getDate());
			}
		});

		dateChooserFinish.setBounds(342, 286, 151, 20);
		contentPane.add(dateChooserFinish);
		
		JLabel lblSelectAS = new JLabel("Select starting date:");
		lblSelectAS.setBounds(140, 265, 133, 14);
		contentPane.add(lblSelectAS);
		
		JLabel lblSelectFinishingDate = new JLabel("Select finishing date:");
		lblSelectFinishingDate.setBounds(342, 265, 133, 14);
		contentPane.add(lblSelectFinishingDate);
		
		JButton btnContinue = new JButton("Next");
		btnContinue.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				welcomeFrame.dispose();
				String garageOrigin = (String) listGarageOrigin.getSelectedValue();
				String garageDestination=(String) listGarageDestination.getSelectedValue();
				java.util.Date startingDate=  dateChooserStart.getDate();
				java.util.Date finishingDate =  dateChooserFinish.getDate();
				try {
					rent.setGarageOrigin(garageOrigin);
					rent.setGarageDestination(garageDestination);
					rent.setStartingDate(startingDate);
					System.out.println(startingDate);
					System.out.println(startingDate);
					rent.setFinishingDate(finishingDate);
					System.out.println(rent.toString());
					new SelectCarGUI(controller, garageOrigin,rent);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnContinue.setBounds(495, 365, 89, 23);
		contentPane.add(btnContinue);
		
		JCheckBox chckbxSameGarage = new JCheckBox("<html>Same as garage <p> of origin<html>");
		chckbxSameGarage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (chckbxSameGarage.isSelected()) {
					rent.setGarageDestination((String) listGarageOrigin.getSelectedValue());
					listGarageDestination.setSelectedIndex(listGarageOrigin.getSelectedIndex());
					listGarageDestination.setEnabled(false);
				}
				else {
					listGarageDestination.setEnabled(true);
				}
				
			}
		});
		chckbxSameGarage.setBounds(488, 114, 145, 60);
		contentPane.add(chckbxSameGarage);


	}
}