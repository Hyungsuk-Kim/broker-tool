package trader;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import trader.*;
import trader.db.BrokerDAO;

@Component("brokerModel")
@Transactional
public class BrokerModelImpl implements BrokerModel {
    private BrokerDAO brokerDAO;
    private ArrayList<BrokerView> changeListeners = new ArrayList<BrokerView>(10);

    /** Creates a new instance of BrokerModelImpl */
    @Autowired
    public BrokerModelImpl(BrokerDAO brokerDAO) {
        this.brokerDAO = brokerDAO;
    }

    // Broker model state change listener registration methods
    public void addChangeListener(BrokerView view) throws BrokerException {
        //** 1 add view to changeListeners using the add method
        if (!(changeListeners.add(view))) {
        	throw new BrokerException("BrokerModelImpl.addChangeListener");
        }
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
    /**----------------------------------------------------------
     * Adds the Customer to the broker model
     */
    public void addCustomer(Customer cust) throws BrokerException {
        //** 1 Check if the record to be added is present
        if (brokerDAO.ssnExists(cust.getId())) {
            throw new BrokerException("Duplicate Id : " + cust.getId());
        }
        //** Add the customer if not present in the database
        try {
            //** 2 Use the brokerDAO instance to add the Customer to the customer table.
        	brokerDAO.createCustomer(cust);
            //** 3 Invoke the fireModelChangeEvent with cust as param.
            //**   To keep the registered views updated with the recent changes in the model.
        	fireModelChangeEvent(cust);
        	
        	

        } catch (BrokerException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerModelImpl.addCustomer", e);
        }
    }

    /**-------------------------------------------------------------
     * Deletes the customer from the broker model
     */
    public void deleteCustomer(Customer cust) throws BrokerException {
        //** 1 Check if the record to be deleted is not present
        if (!brokerDAO.ssnExists(cust.getId())) {
            throw new BrokerException("Record for Id " + cust.getId() + " not found");
        }
        //** Remove the customer if present in the database        
        try {
            //** 2 Use the brokerDAO instance to delete the customer
            //** from the Shares and Customer tables.
        	brokerDAO.removeCustomer(cust);

            //** 3 Using the setName method set the string
            //**   "- customer deleted -" to the name field of the cust object.
        	cust.setName("- customer deleted -");

            //** 4 Invoke the fireModelChangeEvent with cust as param.
            //** To keep the registered views updated with the recent changes in the model.
        	fireModelChangeEvent(cust);
            
        } catch (BrokerException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerModelImpl.deleteCustomer", e);
        }
    }

    /**-------------------------------------------------------------
     * Updates the customer in the broker model
     */
    public void updateCustomer(Customer cust) throws BrokerException {
        //** 1 Check if the record to be updated is not present
    	if (!brokerDAO.ssnExists(cust.getId())) {
            throw new BrokerException("Record for Id " + cust.getId() + " not found");
        }
        
        //** Update the customer if present in the database        
        try {
            //** 2 Use the brokerDAO instance to update the Customer table.
        	brokerDAO.changeCustomerInfo(cust);
            //** 3 Invoke the fireModelChangeEvent with cust as param.
            //** To keep the registered views updated with the recent changes in the model.
        	fireModelChangeEvent(cust);
            
        } catch (BrokerException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerModelImpl.updateCustomer", e);
        }
    }

    // Customer segment state query methods
    /**-------------------------------------------------------------
     * Given an id, returns the Customer from the model
     */
    public Customer getCustomer(String id) throws BrokerException {
        Customer cust = null;        
        try {
            //** 1 Use the brokerDAO instance to retrieve the record
            //** which returns null if no matching customer entity
            //** is found or the customer object once a match is found.
        	cust = brokerDAO.getCustomer(id);

        } catch (BrokerException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerModelImpl.getCustomer", e);
        }

        // The following statement checks if no matching customer entity is found
        if (cust == null) {
            throw new BrokerException("Record for Id " + id + " not found");
        }
        // return cust if match found
        return cust;
    }

    /**-------------------------------------------------------------
     * Returns all customers in the broker model
     */
    public Customer[] getAllCustomers() throws BrokerException {
        Customer[] all = null;
        try {
            //** 1 Use brokerDAO instance to call the getAllCustomers()
            //** method and populate the Array.
        	all = brokerDAO.getAllCustomers();
            
        } catch (BrokerException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerModelImpl.getAllCustomers", e);
        }
        // return all
        return all;
    }

	@Override
	public Stock[] getAllStocks() throws BrokerException {
		Stock[] allStocks = null;
		try {
			allStocks = brokerDAO.getAllStocks();
		} catch (BrokerException e) {
			e.printStackTrace();
			throw new BrokerException("BrokerModelImple.getAllStocks",e);
		}
		return allStocks;
	}
}
