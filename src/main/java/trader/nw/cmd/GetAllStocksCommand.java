package trader.nw.cmd;

import trader.BrokerModel;

public class GetAllStocksCommand extends Command{
	private static final long serialVersionUID = -2516274598808455797L;

	@Override
	public void execute(BrokerModel broker) {
		try {
            result = broker.getAllStocks();
        } catch (Exception ex) {
            this.excep = ex;
        }
	}

}
