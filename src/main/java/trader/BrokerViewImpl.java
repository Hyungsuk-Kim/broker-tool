package trader;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import trader.gui.*;

@Component("brokerView")
public class BrokerViewImpl implements BrokerView, Serializable {
	
    private BrokerModel brokerModel;
    private BrokerController brokerController;
    private transient BrokerGui gui;

    /** Creates a new instance of BrokerViewImpl */
    @Autowired
    public BrokerViewImpl(BrokerModel model) {
        System.out.println("Creating BrokerViewImpl");
        try {
            //** 1 Assign model to the attribute brokerModel
            this.brokerModel = model;
            //** 2 Invoke the addChangeListener method of the model with this instance
        	brokerModel.addChangeListener(this);
        } catch (Exception e) {
            System.out.println("BrokerViewImpl constructor " + e);
        }
        //** 3  Create and assign a BrokerGui object to gui
        this.gui = new BrokerGui(this.brokerModel);
    }

    //user gesture listener registration methods
    public void addUserGestureListener(BrokerController con) throws BrokerException {
        System.out.println("BrokerViewImpl.addUserGestureListener " + con);
        //** 1 Assign con to the attribute brokerController
        this.brokerController = con;
        //** 2 Inform the gui of the controller brokerController
        //     Hint: call the gui's addController method
        gui.addController(this.brokerController);
    }

    //display selection request service methods
    public void showDisplay(Object display) throws BrokerException {
        System.out.println("BrokerViewImpl.showDisplay " + display);
        //** 1 Forward the parameter display to the displayObject(obj) method of the gui
        gui.displayObject(display);
    }

    // callback method to handle customer state change notification from the broker model
    public void handleCustomerChange(Customer cust) throws BrokerException {
        System.out.println("BrokerViewImpl.processCustomer " + cust);
        //** 1 Forward the parameter cust to the displayObject(obj) method of the gui
        gui.displayObject(cust);
    }

}
