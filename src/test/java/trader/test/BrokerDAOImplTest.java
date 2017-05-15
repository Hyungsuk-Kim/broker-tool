package trader.test;

import trader.*;
import trader.db.*;

public class BrokerDAOImplTest {

    public static void main(String[] args) {
        System.out.println("BrokerDAOImplTest.main: testing BrokerDAOImpl");

        BrokerDAO dao = null;
        Customer customer = null;

        try {
            dao = new BrokerDAOImpl();
        } catch (Exception e) {
            System.out.println("BrokerDAOImplTest.main:  exception");
            e.printStackTrace();
        }

        try {
            // testing retrieving all customers
            System.out.println("BrokerDAOImplTest.main: retrieving all Customers");
            Customer[] allCustomers = dao.getAllCustomers();

            System.out.println("BrokerDAOImplTest.main: printing all Customers");
            for (Customer cust : allCustomers) {
                System.out.println(cust);
            }

        } catch (Exception e) {
            System.out.println("BrokerDAOImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // testing getting a valid Customer
            System.out.println("BrokerDAOImplTest.main: retrieving valid customer Test Customer");
            customer = dao.getCustomer("111-11-1111");
            System.out.println(customer);

        } catch (Exception e) {
            System.out.println("BrokerDAOImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // testing getting an invalid customer
            System.out.println("BrokerDAOImplTest.main: retrieving invalid customer JUNK");
            customer = dao.getCustomer("JUNK");
            System.out.println(customer);
            
        } catch (Exception e) {
            System.out.println("BrokerDAOImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // testing updating a valid customer
            System.out.println("BrokerDAOImplTest.main: changing exisiting customer Test Customer ");
            customer = dao.getCustomer("111-11-1111");
            customer.setAddr("3333 Easy Street, East Beach AZ");

            dao.changeCustomerInfo(customer);
            System.out.println(dao.getCustomer("111-11-1111"));

        } catch (Exception e) {
            System.out.println("BrokerDAOImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // inserting a new customer record
            System.out.println("BrokerDAOImplTest.main:  inserting a new customer");
            customer = new Customer("111-11-1112", "New Customer", "2112 Easy Street, South Beach AZ");

            dao.createCustomer(customer);

        } catch (Exception e) {
            System.out.println("BrokerDAOImplTest.main:  exception");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        try {
            // deleting previously inserted new customer record
            System.out.println("BrokerDAOImplTest.main:  deleting new customer");
            customer = new Customer("111-11-1112", "New Customer", "2112 Easy Street, South Beach AZ");

            dao.removeCustomer(customer);

        } catch (Exception e) {
            System.out.println("BrokerDAOImplTest.main:  exception");
            e.printStackTrace();
        }
    }
}


