package trader.db;

import trader.BrokerException;
import trader.*;

public interface BrokerDAO {
	/** 한 명의 고객 정보를 추가*/
	public void createCustomer(Customer cust) throws BrokerException;
	/** 기존의 고객 정보를 삭제*/
	public void removeCustomer(Customer cust) throws BrokerException;
	/** 기존의 고객 정보를 수정*/
	public void changeCustomerInfo(Customer cust) throws BrokerException;
	/** ssn 정보를 사용해 고객 정보를 조회*/
	public Customer getCustomer(String ssn) throws BrokerException;
	/** 모든 고객 정보를 조회*/
	public Customer[] getAllCustomers() throws BrokerException;
	/** 주어진 ssn 정보를 가진 고객이 있는지 검사*/
	public boolean ssnExists(String ssn) throws BrokerException;
	/** */
	public Stock[] getAllStocks() throws BrokerException;
}
