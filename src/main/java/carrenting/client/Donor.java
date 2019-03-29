package carrenting.client;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carrenting.server.ICarRenting;
import es.deusto.computing.sd.util.gui.WindowManager;

public class Donor implements ActionListener {
	private JFrame frame;
	private JButton buttonEnd;
	private JButton buttonDonate;
	private JTextField donation;
	private JTextField total;
	private JLabel message;
	private ICarRenting don_collector;
	private DonorRemoteObserver remoteDonor;

	public Donor() {				
		this.buttonDonate = new JButton("Donate");
		this.buttonDonate.addActionListener(this);
		this.buttonEnd = new JButton("End Process");
		this.buttonEnd.addActionListener(this);
		this.donation = new JTextField(10);
		this.total = new JTextField(10);
		this.total.setEnabled(false);
		this.message = new JLabel("Welcome to the RMI Donor Client");
		this.message.setOpaque(true);
		this.message.setForeground(Color.yellow);
		this.message.setBackground(Color.gray);

		JPanel panelDonativos = new JPanel();
		panelDonativos.add(new JLabel("Donation: "));
		panelDonativos.add(this.donation);
		panelDonativos.add(new JLabel("Total Amount: "));
		panelDonativos.add(this.total);

		JPanel panelBotones = new JPanel();
		panelBotones.add(this.buttonDonate);
		panelBotones.add(this.buttonEnd);

		this.frame = new JFrame("Donor: RMI Client");
		this.frame.setSize(400, 125);
		this.frame.setResizable(false);		
		this.frame.getContentPane().add(panelDonativos, "North");
		this.frame.getContentPane().add(panelBotones);
		this.frame.getContentPane().add(this.message, "South");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowManager.middleLeft(this.frame);
		this.frame.setVisible(true);
	}

	public void start(String[] args) {
		this.connect2Collector(args);
		
		try {
			this.remoteDonor = new DonorRemoteObserver(this.don_collector, this);
		} catch (RemoteException e) {
			System.err.println(" # Error creating Remote Donor: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void connect2Collector(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String URL = "//" + args[0] + ":" + args[1] + "/" + args[2];
			this.don_collector = (ICarRenting) Naming.lookup(URL);
		} catch (Exception e) {
			System.err.println(" *# Error connecting to Donation Collector: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		JButton target = (JButton) e.getSource();
		if (target == this.buttonEnd) {
			try {
				this.remoteDonor.end();
				System.exit(0);
			} catch (Exception exc) {
				System.err.println(" # Error ending Remote Donor: " + exc.getMessage());
				System.exit(-1);
			}
		}

		if (target == this.buttonDonate) {
			try {
				int donation = Integer.parseInt(this.donation.getText());
				this.message.setText("Sending donation ...");
				this.don_collector.getDonation(donation);
				this.message.setText("Donation of " + donation + " � already sent...");
			} catch (Exception exc) {
				this.message.setText(" # Error sending donation.");
			}
		}
	}

	public void notifyTotalAmount(Integer don) {
		this.message.setText("Receiving total donation amount...");
		this.total.setText(don.toString());
		this.message.setText("Total Amount received...");
	}

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Donor donor = new Donor();
			donor.start(args);
		} catch (Exception e) {
			System.err.println(" # Error starting Donor: " + e.getMessage());
			e.printStackTrace();
		}
	}
}