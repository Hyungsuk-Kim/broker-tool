package trader;

public interface BrokerView {
	// Listener Support Methods called by controller
	/** 생성된 BrokerController를 View에 등록하는 메서드.*/
	public abstract void addUserGestureListener(BrokerController con) throws BrokerException;
	// View Selection Methods called by controller
	/** View를 변경하기 위한 메서드.*/
	public abstract void showDisplay(Object display) throws BrokerException;
	// Change Notification Methods called by model
	/** 변경 사항을 받아 반영하는 메서드.*/
	public abstract void handleCustomerChange(Customer cust) throws BrokerException;
}
