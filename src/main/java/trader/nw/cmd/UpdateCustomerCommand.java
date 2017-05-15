package trader.nw.cmd;

import trader.*;

public class UpdateCustomerCommand extends Command {
	private static final long serialVersionUID = -5390439414866200734L;
	private Customer cust;

    /** Creates new UpdateCustomerCommand */
    public UpdateCustomerCommand(Customer cust) {
        this.cust = cust;
    }

    public void execute(BrokerModel broker) {
        try {
            broker.updateCustomer(cust);
        } catch (Exception ex) {
            this.excep = ex;
        }
    }
}
