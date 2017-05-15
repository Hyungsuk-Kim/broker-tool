package trader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("brokerController")
public class BrokerControllerImpl implements BrokerController {

    private BrokerModel brokerModel;
    private BrokerView brokerView;

    /** Creates new BrokerControllerImpl */
    @Autowired
    public BrokerControllerImpl(BrokerModel model, BrokerView view) {
        try {
            //** 1 Assign model to brokerModel
        	this.brokerModel = model;
            //** 2 Assign view to brokerView
        	this.brokerView = view;
            //** 3 Register this object as a user gesture listener with the brokerView object
            //**   Hint - invoke addUserGestureListener
            brokerView.addUserGestureListener(this);
        } catch (Exception e) {
            reportException(e);
        }
    }

    private void reportException(Object o) {
        // The responsibility of this method is to report exceptions
        try {
            brokerView.showDisplay(o);
        } catch (Exception e) {
            System.out.println("BrokerControllerImpl reportException " + e);
        }
    }

    //user gesture call back methods
    /** get customer user gesture handle method called by the broker view */
    @Override
    public void handleGetCustomerGesture(String id) {
        System.out.println("handleGetCustomerGesture " + id);
        Customer cust = null;
        try {
            //** 1 Set cust to the object returned as a result of
            //**   invoking the getCustomer method on brokerModel
        	cust = brokerModel.getCustomer(id);
            //** 2 Invoke showDisplay method of brokerView with cust as parameter
            brokerView.showDisplay(cust);
        } catch (Exception e) {
            reportException(e);
        }
    }

    /** add new customer user gesture handle method called by the broker view */
    @Override
    public void handleAddCustomerGesture(Customer cust) {
        System.out.println("handleAddCustomerGesture " + cust);
        try {
            //** 1 Invoke addCustomer method of brokerModel with cust as parameter
            brokerModel.addCustomer(cust);
        } catch (Exception e) {
            reportException(e);
        }
    }

    /** delete customer user gesture  handle method called by the broker view */
    @Override
    public void handleDeleteCustomerGesture(Customer cust) {
        System.out.println("handleDeleteCustomerGesture " + cust);
        try {
            //** 1 Invoke deleteCustomer method of brokerModel with cust as parameter
            brokerModel.deleteCustomer(cust);
        } catch (Exception e) {
            reportException(e);
        }
    }

    /** update customer user gesture callback method called by the broker view */
    @Override
    public void handleUpdateCustomerGesture(Customer cust) {
        System.out.println("handleUpdateCustomerGesture " + cust);
        try {
            //** 1 Invoke updateCustomer method of brokerModel with cust as parameter
            brokerModel.updateCustomer(cust);
        } catch (Exception e) {
            reportException(e);
        }
    }

    /** get all customers user gesture callback method called the broker view */
    @Override
    public void handleGetAllCustomersGesture() {
        System.out.println("handleGetAllCustomersGesture ");
        Customer[] custs;
        try {
            //** 1 Invoke getAllCustomers method of brokerModel
        	//** and assign the return value from this method to custs
        	custs = brokerModel.getAllCustomers();
            //** 2 Invoke showDisplay method of brokerView with custs as parameter
            brokerView.showDisplay(custs);
        } catch (Exception e) {
            reportException(e);
        }
    }

	@Override
	public void handleGetAllStocksGesture() {
		System.out.println("handleGetAllStocksGesture");
		Stock[] stocks;
		try {
			stocks = brokerModel.getAllStocks();
			brokerView.showDisplay(stocks);
		} catch (Exception e) {
			reportException(e);
		}
	}
}
