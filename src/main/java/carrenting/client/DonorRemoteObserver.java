package donations.client;

import java.rmi.RemoteException;

import donations.server.ICollector;
import es.deusto.computing.sd.util.observer.RMI.RemoteObserver;

public class DonorRemoteObserver extends RemoteObserver {
	
	private static final long serialVersionUID = 1L;
	private ICollector collector;
	private Donor donor;

	public DonorRemoteObserver(ICollector collector, Donor donor) throws RemoteException {
		super();
		this.collector = collector;
		this.donor = donor;
		this.collector.addRemoteObserver(this);
	}

	public void end() throws RemoteException {
		this.collector.deleteRemoteObserver(this);
	}

	public void update(Object arg) {
		if (arg instanceof Integer) {
			this.donor.notifyTotalAmount((Integer) arg);
		}
	}
}