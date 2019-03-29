package es.deusto.computing.sd.util.observer.RMI;

import java.util.ArrayList;
import java.util.List;

public class RemoteObservable {

	/**
	 * List for storing remote observers
	 */
	private List<IRemoteObserver> remoteObservers;

	public RemoteObservable() {
		this.remoteObservers = new ArrayList<IRemoteObserver>();
	}

	public void addRemoteObserver(IRemoteObserver observer) {
		if (observer != null) {
			this.remoteObservers.add(observer);
		}
	}

	public void deleteRemoteObserver(IRemoteObserver observer) {
		if (observer != null) {
			this.remoteObservers.remove(observer);
		}
	}

	
	public void deleteRemoteObservers() {
		this.remoteObservers.clear();
	}

	
	public int countRemoteObservers() {
		return this.remoteObservers.size();
	}

	
	public void notifyRemoteObservers(Object arg) {
		for (IRemoteObserver observer : remoteObservers) {
			try {
				observer.update(arg);
			} catch (Exception ex) {
				System.err.println(this.getClass().getName() + ".notifyRemoteObservers(): " + ex);
				ex.printStackTrace();
			}
		}
	}

	/*
	 * INSTRUCTIONS: - The remote server will keep a reference to a
	 * RemoteObservable object (this class) to which will delegate every
	 * subscription and call when updates are needed.
	 * The RemoteObservable object will not inherit any remote interface because it's
	 * not a remote object. 
	 */

}