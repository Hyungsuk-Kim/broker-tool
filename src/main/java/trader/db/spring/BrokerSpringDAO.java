package trader.db.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import trader.Customer;
import trader.Stock;
import trader.db.BrokerDAO;

@Repository
public class BrokerSpringDAO implements BrokerDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate npJdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setNpJdbcTemplate(NamedParameterJdbcTemplate npJdbcTemplate) {
		this.npJdbcTemplate = npJdbcTemplate;
	}

	class CustMapper implements RowMapper<Customer> {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Customer(rs.getString("ssn"), rs.getString("cust_name"), rs.getString("address"));
		}
		
	}
	
	@Override
	public void createCustomer(Customer cust) {
		//jdbcTemplate.update("INSERT INTO customer VALUES (?, ?, ?)", cust.getId(), cust.getName(), cust.getAddr());
		//npJdbcTemplate.update("INSERT INTO customer VALUES (:id, :name, :addr)", new MapSqlParameterSource().addValue("id", cust.getId()).addValue("name", cust.getName()).addValue("addr", cust.getAddr()));
		npJdbcTemplate.update("INSERT INTO customer VALUES (:id, :name, :addr)", new BeanPropertySqlParameterSource(cust));
	}

	@Override
	public void removeCustomer(Customer cust) {
		jdbcTemplate.update("DELETE FROM shares WHERE ssn = ?", cust.getId());
		jdbcTemplate.update("DELETE FROM customer WHERE ssn = ?", cust.getId());
	}

	@Override
	public void changeCustomerInfo(Customer cust) {
		jdbcTemplate.update("UPDATE customer SET cust_name = ?, address = ? WHERE ssn = ?", cust.getName(), cust.getAddr(), cust.getId());
	}

	@Override
	public Customer getCustomer(String ssn) {
		try {
			return (Customer) jdbcTemplate.queryForObject("SELECT * FROM customer WHERE ssn = ?", new CustMapper(), ssn);
		} catch (DataAccessException e) {
			return null;
		}
	}

	@Override
	public Customer[] getAllCustomers() {
		return jdbcTemplate.query("SELECT * FROM customer", new CustMapper()).toArray(new Customer[0]);
	}

	@Override
	public boolean ssnExists(String ssn) {
		return (this.getCustomer(ssn) != null);
	}

	@Override
	public Stock[] getAllStocks() {
		return jdbcTemplate.query("SELECT * FROM STOCK", new RowMapper<Stock>() {
			@Override
			public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Stock(rs.getString("symbol"), rs.getFloat("price"));
			}}).toArray(new Stock[0]);
	}

}
