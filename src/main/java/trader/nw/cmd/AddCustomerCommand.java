package trader.nw.cmd;

import trader.*;

public class AddCustomerCommand extends Command {
	private static final long serialVersionUID = -2796568875593463994L;
	private Customer cust;

    /** Creates new AddCustomerCommand */
    public AddCustomerCommand(Customer cust) {
        this.cust = cust;
    }

    public void execute(BrokerModel broker) {
        try {
            broker.addCustomer(cust);
        } catch (Exception ex) {
            this.excep = ex;
        }
    }
}