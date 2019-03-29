package carrenting.server;

import java.rmi.RemoteException;

import es.deusto.computing.sd.util.observer.RMI.IRemoteObservable;


public interface ICarRenting extends IRemoteObservable {
	void getDonation(int donation) throws RemoteException;
}