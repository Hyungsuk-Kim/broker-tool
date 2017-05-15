package trader.nw.cmd;

import trader.*;

public class GetAllCustomersCommand extends Command {
	private static final long serialVersionUID = -4226187082656392080L;

	public void execute(BrokerModel broker) {
        try {
            result = broker.getAllCustomers();
        } catch (Exception ex) {
            this.excep = ex;
        }
    }
}