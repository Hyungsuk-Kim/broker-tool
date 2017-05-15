package trader.junit.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import trader.BrokerException;
import trader.Customer;
import trader.db.BrokerDAO;
import trader.db.BrokerDAOImpl;

public class BrokerDAOTest2 {
	private BrokerDAO brokerDao;
	
	@Before
	public void setUpBeforeClass() throws BrokerException {
		brokerDao = new BrokerDAOImpl();
	}

	@After
	public void tearDownAfterClass() {
		brokerDao = null;
	}

	@Test
	public void testRemoveCustomer() throws BrokerException {
		Customer customer = new Customer("111-11-1112", "New Customer", "2112 Easy Street, South Beach AZ");
		brokerDao.removeCustomer(customer);
		customer = brokerDao.getCustomer("111-11-1112");
		assertNull(customer);
	}
}
