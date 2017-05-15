package trader.nw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import trader.*;

public class Broker3TierClient {

    public static void main(String[] args) {
        String modelHost = "localhost";
        try {
            if (args.length > 0) {
                modelHost = args[0];
            }
            /*NwClient nwClient = new NwClient(modelHost, 5280);
            BrokerModel model = new BrokerModelNwImpl(nwClient);
            BrokerView view = new BrokerViewImpl(model);
            BrokerController con = new BrokerControllerImpl(model, view);*/
            ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext_client.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
