package trader.test;

import trader.*;
import trader.db.*;

public class BrokerModelImplTest {

    public static void main(String[] args) {
        System.out.println("BrokerModelImplTest.main: testing BrokerModelImplTest");

        BrokerModel model = null;
        BrokerDAO dao = null;
        Customer customer = null;

        try {
            dao = new BrokerDAOImpl();
            model = new BrokerModelImpl(dao);
        } catch (Exception e) {
            System.out.println("BrokerModelImplTest.main:  exception");
            e.printStackTrace();
        }

        try {
            // testing retrieving all customers
            System.out.println("BrokerModelImplTest.main: retrieving all Customers");
            Customer[] allCustomers = model.getAllCustomers();
            System.out.println("BrokerModelImplTest.main: printing all Customers");
            for (Customer cust : allCustomers) {
                System.out.println(cust);
            }

        } catch (Exception e) {
            System.out.println("BrokerModelImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // testing getting a valid customer
            System.out.println("BrokerModelImplTest.main: retrieving valid customer Test Customer");
            customer = model.getCustomer("111-11-1111");
            System.out.println(customer);

        } catch (Exception e) {
            System.out.println("BrokerModelImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // testing getting an invalid customer
            System.out.println("BrokerModelImplTest.main: retrieving invalid customer JUNK");
            customer = model.getCustomer("JUNK");
            System.out.println(customer);
            
        } catch (Exception e) {
            System.out.println("BrokerModelImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // testing updating a valid customer
            System.out.println("BrokerModelImplTest.main: updating exisiting customer Test Customer ");
            customer = model.getCustomer("111-11-1111");
            customer.setAddr("3333 Easy Street, East Beach AZ");

            model.updateCustomer(customer);
            System.out.println(model.getCustomer("111-11-1111"));

        } catch (Exception e) {
            System.out.println("BrokerModelImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // inserting a new customer record
            System.out.println("BrokerModelImplTest.main:  inserting a new customer");
            customer = new Customer("111-11-1112", "New Customer", "2112 Easy Street, South Beach AZ");

            model.addCustomer(customer);

        } catch (Exception e) {
            System.out.println("BrokerModelImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // deleting previously inserted new customer record
            System.out.println("BrokerModelImplTest.main:  deleting new customer");
            customer = new Customer("111-11-1112", "New Customer", "2112 Easy Street, South Beach AZ");

            model.deleteCustomer(customer);

        } catch (Exception e) {
            System.out.println("BrokerModelImplTest.main:  exception");
            e.printStackTrace();
        }
    }
}


