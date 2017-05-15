package trader.junit.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import trader.BrokerException;
import trader.Customer;
import trader.Stock;
import trader.db.BrokerDAO;
import trader.db.BrokerDAOImpl;

public class BrokerDAOTest {
	private static BrokerDAO brokerDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws BrokerException {
		brokerDao = new BrokerDAOImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		brokerDao = null;
	}
	/*	
	private BrokerDAO brokerDao;
	
	@Before 
	public void setUp() throws BrokerException {
		brokerDao = new BrokerDAOImpl();
	}

	@After
	public void tearDown() {
		brokerDao = null;
	}
	 */
	@Test
	public void testSsnExists() throws BrokerException {
		boolean exists = brokerDao.ssnExists("JUNK");
		assertFalse(exists);
		exists = brokerDao.ssnExists("111-11-1111");
		assertTrue(exists);
	}
	
	@Test
	public void testGetCustomer() throws BrokerException {
		Customer customer = brokerDao.getCustomer("JUNK");
		assertNull(customer);
		customer = brokerDao.getCustomer("111-11-1111");
		assertNotNull(customer);
		assertEquals(customer.getId(), "111-11-1111");
	}
	
	//@Ignore
	@Test
	public void testCreateCustomer() throws BrokerException {
		Customer customer = new Customer("111-11-1112", "New Customer", "2112 Easy Street, South Beach AZ");
		brokerDao.createCustomer(customer);
		customer = brokerDao.getCustomer("111-11-1112");
		assertNotNull(customer);
		assertEquals(customer.getName(), "New Customer");
		assertEquals(customer.getAddr(), "2112 Easy Street, South Beach AZ");
	}
	
	@Ignore
	@Test
	public void testRemoveCustomer() throws BrokerException {
		Customer customer = new Customer("111-11-1112", "New Customer", "2112 Easy Street, South Beach AZ");
		brokerDao.removeCustomer(customer);
		customer = brokerDao.getCustomer("111-11-1112");
		assertNull(customer);
	}	

	@Ignore
	@Test
	public void testCreateRemoveCustomer() throws BrokerException {
		Customer customer = new Customer("111-11-1112", "New Customer", "2112 Easy Street, South Beach AZ");
		brokerDao.createCustomer(customer);		
		customer = brokerDao.getCustomer("111-11-1112");
		assertNotNull(customer);
		assertEquals(customer.getName(), "New Customer");
		assertEquals(customer.getAddr(), "2112 Easy Street, South Beach AZ");
		
		brokerDao.removeCustomer(customer);
		customer = brokerDao.getCustomer("111-11-1112");
		assertNull(customer);		
	}
	
	@Test
	public void testChangeCustomerInfo() throws BrokerException {
		long ts = System.currentTimeMillis();
		Customer customer = new Customer("111-11-1111");
		customer.setName("name_" + ts);
        customer.setAddr("address_" + ts);
		brokerDao.changeCustomerInfo(customer);
		
		customer = brokerDao.getCustomer("111-11-1111");
		assertNotNull(customer);
		assertEquals(customer.getName(), "name_" + ts);
		assertEquals(customer.getAddr(), "address_" + ts);
	}
	
	@Test
	public void testGetAllCustomers() throws BrokerException {
		Customer[] customers = brokerDao.getAllCustomers();
		assertNotNull(customers);
		
		for (Customer customer : customers) {
			assertNotNull(customer);
			//System.out.println("GetAllCustomers => " + customer);
		}
	}

	@Test
	public void testGetAllStocks() throws BrokerException {
		Stock[] stocks = brokerDao.getAllStocks();
		assertNotNull(stocks);
		
		for (Stock stock : stocks) {
			assertNotNull(stock);
			//System.out.println("GetAllStocks => " + stock);
		}
	}
}
