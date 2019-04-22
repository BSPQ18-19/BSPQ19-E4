package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import carrenting.client.Controller;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class StatisticsGUI extends JFrame {

	private JPanel contentPane;
	private JTable tableGarages;
	private JTable tablePaySys;
	private Controller controller;
	private JFrame frame;
	private JTable tableCars;

	public StatisticsGUI(Controller controller) {
		this.controller= controller;
		initialize();
		frame.setVisible(true);
	}
	

	public void initialize() {
		frame= new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 883, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGarage = new JLabel("Garage popularity");
		lblGarage.setBounds(39, 50, 173, 14);
		contentPane.add(lblGarage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 75, 307, 178);
		contentPane.add(scrollPane);
		
		tableGarages = new JTable();
		tableGarages.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Garage", "Times as origin", "Times as destination"
			}
		));
		tableGarages.getColumnModel().getColumn(1).setPreferredWidth(110);
		tableGarages.getColumnModel().getColumn(2).setPreferredWidth(110);
		tableGarages.setEnabled(false);
		tableGarages.setShowVerticalLines(false);
		tableGarages.setRowSelectionAllowed(false);
		scrollPane.setViewportView(tableGarages);
		
		JLabel labelStatistics = new JLabel("Statistics");
		labelStatistics.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		labelStatistics.setBounds(389, 11, 194, 22);
		contentPane.add(labelStatistics);
		
		JLabel lblPaymentSystemPopularity = new JLabel("Payment system popularity");
		lblPaymentSystemPopularity.setBounds(40, 275, 153, 14);
		contentPane.add(lblPaymentSystemPopularity);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(39, 300, 307, 128);
		contentPane.add(scrollPane_1);
		
		tablePaySys = new JTable();
		tablePaySys.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Payment System", "Times used"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tablePaySys.getColumnModel().getColumn(0).setPreferredWidth(110);
		scrollPane_1.setViewportView(tablePaySys);
		
		JLabel lblCarPopularity = new JLabel("Car popularity");
		lblCarPopularity.setBounds(399, 50, 106, 14);
		contentPane.add(lblCarPopularity);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(400, 75, 457, 250);
		contentPane.add(scrollPane_2);
		
		tableCars = new JTable();
		scrollPane_2.setViewportView(tableCars);
		
		JButton btnBackToStaff = new JButton("Back to Staff Panel");
		btnBackToStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
			
		});
		btnBackToStaff.setBounds(663, 417, 194, 23);
		contentPane.add(btnBackToStaff);
	}
}
