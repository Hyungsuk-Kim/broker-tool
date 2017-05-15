package trader.db;

import java.sql.*;
import java.util.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import trader.*;

@Component("brokerDao")
public class BrokerDAOImpl implements BrokerDAO {
    /*private String driver = "org.apache.derby.jdbc.ClientDriver";
    private String url = "jdbc:derby://localhost:1527/StockMarket";
	private String user = "sku";
	private String password = "sku";*/
	ConnectionManager connectionManager = null;

    public BrokerDAOImpl() throws BrokerException {
    	connectionManager = new ConnectionManager();
        /*try {
            //** 1 Register a JDBC driver
            //**   (The name of the derby driver class is "org.apache.derby.jdbc.ClientDriver")
        	Class.forName(driver);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerDAOImpl.constructor", e);
        }*/
    }

    /** ---------------------------------------------------------
     * This is a helper method used by many methods.
     * It returns a Connection object.
     * A future subclass of this class can override this method 
	 * and obtain the connection object from a connection pool.
     */
    protected Connection obtainConnection() throws SQLException {
        //** 1 Return a connection
        //**   (Invoking the getConnection method on the DriverManager class with url, user and password)
    	return connectionManager.getConnection();
    }

    /** ---------------------------------------------------------
     * This is a helper method used by many methods
     * It returns true if the ssn (ie customer id) is found in the Customer table
     */
    public boolean ssnExists(String ssn) throws BrokerException {
        boolean isExists = false;
        // The following creates the required SELECT SQL string
    	//String request = "SELECT ssn FROM Customer WHERE ssn='" + ssn + "'";
    	String sql = "SELECT ssn FROM customer WHERE ssn = ?";
        
    	Connection con = null;
    	//Statement stmt = null;
    	PreparedStatement pstmt = null;
    	ResultSet result = null;

        try {
            // Assign con by invoking the obtainConnection()
            con = obtainConnection();
            // Assign stmt by invoking createStatement() on con
            pstmt = con.prepareStatement(sql);
            // Assign result by invoking executeQuery(request) on stmt
            //result = stmt.executeQuery(request);
            pstmt.setString(1, ssn);
            result = pstmt.executeQuery();
            // Assign isExists by invoking next() on result
            isExists = result.next();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerDAOImpl.ssnExists", e);
        } finally {
        	this.close(con, pstmt, result);
        }

        // return isExists
        return isExists;
    }

    /**----------------------------------------------------------
     * Inserts the Customer to the Customer table
     */
    public void createCustomer(Customer cust) throws BrokerException {
        //** 1 Assign id with id from cust object
        String id = cust.getId();
        //** 2 Assign name with name from cust object
        String name = cust.getName();
        //** 3 Assign addr with addr from cust object
        String addr = cust.getAddr();

        // The following creates the required INSERT SQL string
        /*String request = "INSERT INTO Customer (ssn, cust_name, address)"
                        + " VALUES ('" + id + "', '" + name + "', '" + addr + "')";*/
        String sql = "INSERT INTO customer VALUES (?, ?, ?)";

        Connection con = null;
        //Statement stmt = null;
        PreparedStatement pstmt = null;

        try {
            //** 4 Assign con by invoking the obtainConnection()
        	con = this.obtainConnection();

            //** 5 Assign stmt by invoking createStatement on con
        	//stmt = con.createStatement();
        	pstmt = con.prepareStatement(sql);
        	pstmt.setString(1, id);
        	pstmt.setString(2, name);
        	pstmt.setString(3, addr);

            //** 6 Invoking executeUpdate(request) method on the stmt object
        	//int rowCount = stmt.executeUpdate(request);
        	pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerDAOImpl.createCustomer", e);
        } finally {
        	this.close(con, pstmt);
        }
    }

    /**-------------------------------------------------------------
     * Deletes the customer from the Customer table
     */
    public void removeCustomer(Customer cust) throws BrokerException {
        //** 1 Assign id with id from cust object
        String id = cust.getId();
        // The following creates the required DELETE SQL string
        // to delete rows from the Shares table
        /*String request1 = "DELETE FROM Shares WHERE ssn='" + id + "'";*/
        String sql = "DELETE FROM shares WHERE ssn = ?";
        // to delete the customer from the Customers table
        /*String request2 = "DELETE FROM Customer WHERE ssn='" + id + "'";*/
        String sql2 = "DELETE FROM customer WHERE ssn = ?";

    	Connection con = null;
    	//Statement stmt = null;
    	PreparedStatement pstmt = null;

        try {
            //** 2 Assign con by invoking the obtainConnection()
        	con = this.obtainConnection();

            //** 3 Assign stmt by invoking createStatement on con
        	//stmt = con.createStatement();
        	pstmt = con.prepareStatement(sql);
        	pstmt.setString(1, id);

            //** 4 Invoking executeUpdate(request1) method on the stmt object
        	/*int rowCount = stmt.executeUpdate(request1);*/
        	pstmt.executeUpdate();
        	pstmt.close();

            //** 5 Invoking executeUpdate(request2) method on the stmt object
        	/*rowCount = stmt.executeUpdate(request2);*/
        	pstmt = con.prepareStatement(sql2);
        	pstmt.setString(1, id);
        	pstmt.executeUpdate();
        	
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerDAOImpl.removeCustomer", e);
        } finally {
        	this.close(con, pstmt);
        }
    }

    /**-------------------------------------------------------------
     * Updates the customer in the Customer table
     */
    public void changeCustomerInfo(Customer cust) throws BrokerException {
    	//** 1 Assign id with id from cust object
        String id = cust.getId();
        //** 2 Assign name with name from cust object
        String name = cust.getName();
        //** 3 Assign addr with addr from cust object
        String addr = cust.getAddr();
    	
        // The following creates the required UPDATE SQL string
        /*String request = "UPDATE Customer SET cust_name='" + name + "', address='" + addr + "'"
                        + " WHERE ssn='" + id + "'";*/
        String sql = "UPDATE customer SET cust_name = ?, address = ? WHERE ssn = ?";
        
        Connection con = null;
        /*Statement stmt = null;*/
        PreparedStatement pstmt = null;

        try {
            //** 4 Assign con by invoking the obtainConnection()
        	con = this.obtainConnection();
            //** 5 Assign stmt by invoking createStatement on con
        	/*stmt = con.createStatement();*/
        	pstmt = con.prepareStatement(sql);
        	pstmt.setString(1, name);
        	pstmt.setString(2, addr);
        	pstmt.setString(3, id);
            //** 6 Invoking executeUpdate(request) method on the stmt object
        	/*int rowCount = stmt.executeUpdate(request);*/
        	pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerDAOImpl.changeCustomerInfo", e);
        } finally {
            this.close(con, pstmt);
        }
    }

    /**-------------------------------------------------------------
     * Given an id, returns the Customer from the Customer table
     */
    public Customer getCustomer(String ssn) throws BrokerException {
        Customer cust = null;

    	// The following creates the required SELECT SQL string
        /*String request = "SELECT cust_name, address FROM Customer"
                        + " WHERE ssn='" + ssn + "'";*/
        String sql = "SELECT cust_name, address FROM customer WHERE ssn = ?";

    	Connection con = null;
    	/*Statement stmt = null;*/
    	PreparedStatement pstmt = null;
    	ResultSet result = null;

        try {
            //** 1 Assign con by invoking the obtainConnection()
        	con = this.obtainConnection();

            //** 2 Assign stmt by invoking createStatement on con
        	/*stmt = con.createStatement();*/
        	pstmt =con.prepareStatement(sql);
        	pstmt.setString(1, ssn);

            //** 3 Assign result with the value returned by invoking
            //**   executeQuery(request) method on the stmt object
        	/*result = stmt.executeQuery(request);*/
        	result = pstmt.executeQuery();


            String name;
            String addr;
            // The following statement checks if query successful
            if (result.next()) {
                //** 4 Assign name with the value returned by invoking
                //**   getString(1) method on the result object
            	name = result.getString("cust_name");
                
                //** 5 Assign addr with the value returned by invoking
                //**   getString(2) method on the result object
            	addr = result.getString("address");
                
                //** 6 Create a Customer object using (ssn, name, addr)
                //**   and assign this object to cust
            	cust = new Customer(ssn, name, addr);
                
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerDAOImpl.getCustomer", e);
        } finally {
        	this.close(con, pstmt, result);
        }

        // return cust
        return cust;
    }

    /**-------------------------------------------------------------
     * Returns all customers in the Customer table
     */
    public Customer[] getAllCustomers() throws BrokerException {
        Customer[] allCustomers = null;

    	//** the following creates the required SELECT SQL string
    	/*String request = "SELECT * FROM CUSTOMER";*/
        String sql = "SELECT * FROM customer";

    	Connection con = null;
    	//Statement stmt = null;
    	PreparedStatement pstmt = null;
        ResultSet result = null;

        try {
            //** 1 assign con by invoking the obtainConnection()
        	con = this.obtainConnection();
            //** 2 assign stmt by invoking createStatement on con
        	/*stmt = con.createStatement();*/
        	pstmt = con.prepareStatement(sql);
            //** 3 assign result with the value returned by invoking
            //**   executeQuery(request) method on the stmt object
        	/*result = stmt.executeQuery(request);*/
        	result = pstmt.executeQuery();

            String id;
            String name;
            String addr;
            Customer cust = null;
            List<Customer> cList = new ArrayList<Customer>();

            while (result.next()) {
                //** 4 assign id with the value returned by invoking
                //**   getString(1) method on the result object
            	id = result.getString("ssn");
                //** 5 assign name with the value returned by invoking
                //**   getString(2) method on the result object
                name = result.getString("cust_name");
                //** 6 assign addr with the value returned by invoking
                //**   getString(3) method on the result object
                addr = result.getString("address");
                //** 7 create a Customer object using (id, name, addr)
                //**   and assign this object to cust
                cust = new Customer(id, name, addr);
                //** 8 use the ArrayList add method to add cust to cList
                cList.add(cust);

            }
            // copy customers of the ArrayList to Customer array
            allCustomers = cList.toArray(new Customer[0]);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerDAOImpl.getAllCustomers", e);
        } finally {
        	this.close(con, pstmt, result);
        }

        // return allCustomers
        return allCustomers;
    }

	@Override
	public Stock[] getAllStocks() throws BrokerException {
		Stock[] allStocks = null;
		
		Connection con = null;
		//Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		String sql = "SELECT * FROM STOCK";
		
		try {
			con = this.obtainConnection();
			/*stmt = con.createStatement();*/
			pstmt = con.prepareStatement(sql);
			/*result = stmt.executeQuery(sql);*/
			result = pstmt.executeQuery();
			
			List<Stock> stockList = new ArrayList<Stock>();
			String symbol;
			float price;
			
			while (result.next()) {
				symbol = result.getString("symbol");
				price = result.getFloat("price");
				stockList.add(new Stock(symbol, price));
			}
			allStocks = stockList.toArray(new Stock[0]);
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw new BrokerException("BrokerDAOImpl.getAllStocks", e);
        } finally {
            this.close(con, pstmt, result);
        }
		return allStocks;
	}
	
	private void close(Connection con, PreparedStatement pstmt, ResultSet result) {
		try { if (result != null) result.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
        try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
        try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
	}
	
	private void close(Connection con, PreparedStatement pstmt) {
		try { if (pstmt != null) pstmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
        try { if (con != null) con.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
	}

}
