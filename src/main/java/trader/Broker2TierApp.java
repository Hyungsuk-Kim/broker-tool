package trader;

import trader.db.*;

public class Broker2TierApp {

    public static void main(String[] args) {
        try {
            BrokerDAO dao = new trader.db.BrokerDAOImpl();
            BrokerModel model = new trader.BrokerModelImpl(dao);
            
            BrokerView view1 = new BrokerViewImpl(model);
            BrokerController con1 = new BrokerControllerImpl(model, view1);
            
            BrokerView view2 = new BrokerViewImpl(model);
            BrokerController con2 = new BrokerControllerImpl(model, view2);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
