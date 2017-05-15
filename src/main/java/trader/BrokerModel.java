package trader;

public interface BrokerModel {
	// Listener Support Methods called by view 
	/** 생성된 BrokerView를 Model에 등록하는 메서드.*/
	public abstract void addChangeListener(BrokerView view) throws BrokerException;
	// State Change Methods called by controller
	/** Customer를 등록한다.*/
	public abstract void addCustomer(Customer cust) throws BrokerException;
	/** Customer를 삭제한다.*/
	public abstract void deleteCustomer(Customer cust) throws BrokerException;
	/** 등록된 Customer의 정보를 수정한다.*/
	public abstract void updateCustomer(Customer cust) throws BrokerException;
	// State Query Methods called by view
	/** id로 해당 Customer를 검색하여 리턴한다.*/
	public abstract Customer getCustomer(String id) throws BrokerException;
	/** 등록된 모든 Customer들을 배열에 담아서 리턴한다.*/
	public abstract Customer[] getAllCustomers() throws BrokerException;
	/** */
	public abstract Stock[] getAllStocks() throws BrokerException;
}
