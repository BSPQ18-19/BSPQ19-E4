package es.deusto.rmi.carrenting;


import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import carrenting.server.CarRenting;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class DAOTest {
	
	static CarRenting c;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(DAOTest.class);
	}
	
	@BeforeClass
	public static void setUp() throws RemoteException {
		c = new CarRenting();
	}
	
	
	@Test
	public void testLoginStaff() throws RemoteException {
		assertEquals(c.loginStaff("admin1", "admin1", "administrator"), true);
	}
	
	@Test
	public void testStoreGarage() throws RemoteException {
		c.storeGarage("Portugalete");
		
		ArrayList<String> garages = c.getGarages();
		
		for(String s : garages) {
			if(s.equals("Portugalete")){
				assert(true);
			}
		}
		
	}
	
}
