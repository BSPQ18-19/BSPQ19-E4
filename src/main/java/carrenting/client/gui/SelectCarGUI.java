package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextPane;

public class SelectCarGUI extends JFrame {

	private JPanel contentPane;
	private JTable tableCars;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectCarGUI frame = new SelectCarGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SelectCarGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectACar = new JLabel("Select a car");
		lblSelectACar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
		lblSelectACar.setBounds(206, 22, 94, 22);
		contentPane.add(lblSelectACar);
		
		tableCars = new JTable();
		tableCars.setShowGrid(false);
		tableCars.setShowVerticalLines(false);
		tableCars.setBounds(29, 55, 471, 139);
		contentPane.add(tableCars);
		
		JLabel lblTotalPrice = new JLabel("Total price:");
		lblTotalPrice.setBounds(28, 223, 76, 14);
		contentPane.add(lblTotalPrice);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(88, 217, 94, 20);
		contentPane.add(textPane);
	}
}
