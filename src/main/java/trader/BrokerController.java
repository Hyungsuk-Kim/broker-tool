package trader;

public interface BrokerController {
	// User Gesture Callback Methods called by view
	/** Model에게 id로 Customer 조회를 지시하는 메서드.*/
	public abstract void handleGetCustomerGesture(String id);
	/** Model에게 해당 Customer를 등록하도록 지시하는 메서드.*/
	public abstract void handleAddCustomerGesture(Customer cust);
	/** Model에게 해당 Customer를 삭제하도록 지시하는 메서드.*/
	public abstract void handleDeleteCustomerGesture(Customer cust);
	/** Model에게 해당 Customer를 수정하도록 지시하는 메서드.*/
	public abstract void handleUpdateCustomerGesture(Customer cust);
	/** Model에게 모든 Customer들을 조회하도록 지시하는 메서드.*/
	public abstract void handleGetAllCustomersGesture();
	/** */
	public abstract void handleGetAllStocksGesture();
}
