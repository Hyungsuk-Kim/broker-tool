package trader.nw.cmd;

import trader.*;

public class GetCustomerCommand extends Command {
	private static final long serialVersionUID = 5177865983517691758L;
	private String id;

    /** Creates new GetCustomerCommand */
    public GetCustomerCommand(String ssn) {
        this.id = ssn;
    }

    public void execute(BrokerModel broker) {
        try {
            result = broker.getCustomer(id);
            System.out.println("GetCustomerCommand.execute " + result);
        } catch (Exception ex) {
            this.excep = ex;
        }
    }
}