package trader.junit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import trader.BrokerException;
import trader.Customer;
import trader.Stock;
import trader.db.BrokerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/dao-config.xml"})
public class BrokerSpringDAOTest {

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private BrokerDAO dao;
   
	@After
	public void rollback() throws SQLException {
		DataSource source = ctx.getBean(DataSource.class);
		source.getConnection().rollback();
	}
	
	@Test
	public void testSsnExists() throws BrokerException {
		boolean result = dao.ssnExists("999-90-9009");
		assertTrue(result);
		result = dao.ssnExists("JUNK");
		assertFalse(result);
	}
   
	@Test
	public void testGetCustomer() throws BrokerException {
		Customer customer = dao.getCustomer("111-11-1111");
		assertNotNull(customer);
		//assertEquals(customer.getName(), "Test Customer");
		customer = dao.getCustomer("JUNK");
		assertNull(customer);      
	}
      
	@Test
	public void testGetAllCustomers() throws BrokerException {
		Customer[] customers = dao.getAllCustomers();
		assertNotNull(customers);
		for (Customer customer : customers) {
			assertNotNull(customer);
			assertNotNull(customer.getId());
			assertNotNull(customer.getName());
			assertNotNull(customer.getAddr());
		}
	}
   
	@Test 
	public void testCreateRemoveCustomer() throws BrokerException {
		Customer customer = new Customer("111-11-1112", "New Customer", "2121 Easy Street, East Beach AZ");
      
		customer = new Customer("111-11-1112", "New Customer", "2121 Easy Street, East Beach AZ");
		dao.removeCustomer(customer);
      
		customer = dao.getCustomer("111-11-1112");
		assertNull(customer);
	}
	
	@Test
	public void testChangeCustomerInfo() throws BrokerException {
		long ts = System.currentTimeMillis();
		Customer customer = new Customer("111-11-1111");
		customer.setName("name_" + ts);
		customer.setAddr("address_" + ts);
		dao.changeCustomerInfo(customer);
      
		customer = dao.getCustomer("111-11-1111");
		assertEquals(customer.getName(), "name_" + ts);
		assertEquals(customer.getAddr(), "address_" + ts);
	}
   
	@Test
	public void testGetAllStocks() throws BrokerException {
		Stock[] stocks = dao.getAllStocks();
		assertNotNull(stocks);
		for (Stock stock : stocks) {
			assertNotNull(stock);
			assertNotNull(stock.getSymbol());
			assertTrue(stock.getPrice() >= 0);
		}
	}
   
}