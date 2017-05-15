package trader.nw.cmd;

import trader.BrokerModel;
import trader.Customer;

public class DeleteCustomerCommand extends Command{
	private static final long serialVersionUID = 5223922372008456440L;
	
	private Customer cust;
	
	public DeleteCustomerCommand(Customer cust) {
		this.cust = cust;
	}
	
	@Override
	public void execute(BrokerModel broker) {
		try {
			broker.deleteCustomer(cust);
		} catch (Exception e) {
			this.excep = e;
		}
	}

}
