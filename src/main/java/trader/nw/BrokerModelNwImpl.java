package trader.nw;

import trader.*;
import trader.nw.cmd.*;

import java.nio.channels.NetworkChannel;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.DelayQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("brokerNwModel")
public class BrokerModelNwImpl implements BrokerModel {
    private ArrayList<BrokerView> changeListeners = new ArrayList<BrokerView>(10);
    private NwClient nwClient;

    /** Creates new BrokerNwImpl */
    @Autowired
    public BrokerModelNwImpl(NwClient nwClient) {
        //** 1 Assign the parameter variable nwClient to the attribute nwClient
        this.nwClient = nwClient;
    }

    // Broker model state change listener registration methods
    public void addChangeListener(BrokerView view) throws BrokerException {
        //** 1 add view to changeListeners using add method
    	this.changeListeners.add(view);
    }

    /**-----------------------------------------------------------
     * This method notifies all registered BrokerView listeners
     * that a customer object has changed.
     */
    private void fireModelChangeEvent(Customer cust) {
        for (BrokerView view : changeListeners) {
            try {
                view.handleCustomerChange(cust);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Customer segment state change methods
    /** Adds the Customer to the broker model */
    public void addCustomer(Customer cust) throws BrokerException {
        Command cmd;
        Object result;
        try {
            cmd = new AddCustomerCommand(cust);
            nwClient.send(cmd);
            cmd = (Command) nwClient.receive();
            result = cmd.getResult();

         this.fireModelChangeEvent(cust);
        } catch (Exception e) {
            throw new BrokerException(e.getMessage(), e);
        }
    }

    /** deletes the customer from the broker model */
    public void deleteCustomer(Customer cust) throws BrokerException {
        Command cmd;
        Object result;
        try {
            //** 1 Create a DeleteCustomerCommand using cust and
            //**   assign it to cmd
            cmd = new DeleteCustomerCommand(cust);
            //** 2, 3, 4 Remaining 3 lines of code are identical to
            //**         the addCustomer method
            nwClient.send(cmd);
            cmd = (Command) nwClient.receive();
            result = cmd.getResult();
            cust.setName("- customer deleted -");
         //** 5. Invoke the fireModelChangeEvent with cust as param.
           this.fireModelChangeEvent(cust);
        } catch (Exception e) {
            throw new BrokerException(e.getMessage(), e);
        }
    }

    /** Updates the customer in the broker model */
    public void updateCustomer(Customer cust) throws BrokerException {
        Command cmd;
        Object result;
        try {
            //** 1 Create a UpdateCustomerCommand using cust and
            //**   assign it to cmd
            cmd = new UpdateCustomerCommand(cust);
            //** 2, 3, 4 Remaining 3 lines of code are identical to
            //**         the addCustomer method
            nwClient.send(cmd);
            cmd = (Command) nwClient.receive();
            result = cmd.getResult();
         //** 5. Invoke the fireModelChangeEvent with cust as param.
                   this.fireModelChangeEvent(cust);
        } catch (Exception e) {
            throw new BrokerException(e.getMessage(), e);
        }
    }

    // Customer segment state query methods
    /** Given an id, returns the Customer from the model */
    public Customer getCustomer(String id) throws BrokerException {
        Command cmd;
        Object result;
        Customer cust = null;
        try {
            //** 1 Create a GetCustomerCommand using id and
            //**   assign it to cmd
            cmd = new GetCustomerCommand(id);
            //** 2, 3, 4 Remaining 3 lines of code are identical to
            //**         the addCustomer method
            nwClient.send(cmd);
            cmd = (Command) nwClient.receive();
            result = cmd.getResult();
            
            //** 5 cast result to Customer and assign to cust
            cust = (Customer) result;
        } catch (Exception e) {
            throw new BrokerException(e.getMessage(), e);
        }
        return cust;
    }

    /** Returns all customers in the broker model */
    public Customer[] getAllCustomers() throws BrokerException {
        Command cmd;
        Object result;
        Customer[] customers = null;
        try {
            //** 1 Create a GetAllCustomersCommand and
            //**   assign it to cmd
            cmd = new GetAllCustomersCommand();
            //** 2, 3, 4 Remaining 3 lines of code are identical to
            //**         the addCustomer method
            nwClient.send(cmd);
            cmd = (Command) nwClient.receive();
            result = cmd.getResult();
            //** 5 cast result to Customer[] and assign to customers
            customers = (Customer[]) result;
        } catch (Exception e) {
            throw new BrokerException(e.getMessage(), e);
        }
        return customers;
    }

	@Override
	public Stock[] getAllStocks() throws BrokerException {
		Command cmd;
		Object result;
		Stock[] stocks = null;
		try {
			cmd = new GetAllStocksCommand();
			nwClient.send(cmd);
			cmd = (Command) nwClient.receive();
			result = cmd.getResult();
			stocks = (Stock[]) result;
		} catch (Exception e) {
			throw new BrokerException(e.getMessage(), e);
		}
		return stocks;
	}
}