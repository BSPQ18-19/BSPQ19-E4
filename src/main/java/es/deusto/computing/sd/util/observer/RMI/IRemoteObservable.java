package es.deusto.computing.sd.util.observer.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteObservable extends Remote {
	public void addRemoteObserver(IRemoteObserver observer) throws RemoteException;
	public void deleteRemoteObserver(IRemoteObserver observer) throws RemoteException;
}