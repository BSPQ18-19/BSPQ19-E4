package carrenting.client;

import java.rmi.RemoteException;

import carrenting.server.ICarRenting;
import es.deusto.computing.sd.util.observer.RMI.RemoteObserver;

public class DonorRemoteObserver extends RemoteObserver {
	
	private static final long serialVersionUID = 1L;
	private ICarRenting collector;
	private Donor donor;

	public DonorRemoteObserver(ICarRenting collector, Donor donor) throws RemoteException {
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