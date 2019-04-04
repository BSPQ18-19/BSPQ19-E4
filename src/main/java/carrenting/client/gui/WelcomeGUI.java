package carrenting.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class WelcomeGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeGUI frame = new WelcomeGUI();
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
	public WelcomeGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcomeToCar = new JLabel("Welcome to MyCarRenting");
		lblWelcomeToCar.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 16));
		lblWelcomeToCar.setBounds(22, 11, 244, 22);
		contentPane.add(lblWelcomeToCar);
		
		JLabel lblStaffArea = new JLabel("<html><u>STAFF area");
		lblStaffArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
		lblStaffArea.setForeground(Color.BLUE);
		lblStaffArea.setBounds(369, 14, 55, 22);
		contentPane.add(lblStaffArea);
	}
}
