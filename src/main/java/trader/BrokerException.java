package trader;

public class BrokerException extends Exception{
	private static final long serialVersionUID = 6635280795369100416L;
	
	public BrokerException() {
		
	}
	public BrokerException(String message) {
		super(message);
	}
	public BrokerException(Throwable cause) {
		super(cause);
	}
	public BrokerException(String message, Throwable cause) {
		super(message, cause);
	}
}
