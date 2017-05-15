package trader.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import trader.*;

public class AllCustomerTablePanel implements BrokerPanel {

    private BrokerModel model;
    private BrokerController controller;

    // AllCustomersPanel components
    protected JPanel allCustPan = new JPanel();
    protected JLabel allCustLb = new JLabel("All Customers", SwingConstants.CENTER);
    // JTable related attributes
    //** 1 Declare attribute tableHeaders of type String[]
    //**   and initialize to "Customer Id", "Name", "Address"
    protected String[] tableHeaders = {"Customer ID", "Name", "Address"};
    //** 2 Declare attribute tableModel of type DefaultTableModel
    protected DefaultTableModel tableModel;
    //** 3 Declare attribute table of type JTable
    protected JTable table;
    //** 4 Declare attribute tablePane of type JScrollPane    
    protected JScrollPane tablePane;
    
    /** Creates a new instance of AllCustomerPanel */
    public AllCustomerTablePanel(BrokerModel model) {
        this.model = model;
        buildAllCustPanel();
        refresh();
    }

    public void registerController(BrokerController controller) {
        this.controller = controller;
    }

    // build all customer panel
    void buildAllCustPanel() {
        // this method builds the customer panel.
        allCustPan.setLayout(new BorderLayout());
        allCustPan.add(allCustLb, BorderLayout.NORTH);
        //** 1 Create a DefaultTableModel and assign it to tableModel
        tableModel = new DefaultTableModel(tableHeaders, 1);
        //** 2 Create a JTable and assign it to table
        table = new JTable(tableModel);
        //** 3 Create a JScrollPane object to scroll the table and assign it to tablePane
        tablePane = new JScrollPane(table);
        //** 4 Add the tablePane to CENTER region of allCustPan
        allCustPan.add(tablePane, BorderLayout.CENTER);
        // set table view port properties
        Dimension dim = new Dimension(500, 150);
        table.setPreferredScrollableViewportSize(dim);
    }

    public void refresh() {
        try {
            Customer[] customers = model.getAllCustomers();
            refreshAllCustPan(customers);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void display(Object obj) {
        if (obj instanceof Customer[]) {
            refreshAllCustPan((Customer[])obj);
        }
    }

    public JPanel getPanel() {
        return allCustPan;
    }

    void refreshAllCustPan(Customer[] custs) {
        //** 1 Create a 2-dimensional string array with no of rows
        //**   equal to custs.length and no of columns set to 3,
        //**   and assign to newData.
		String[][] newData = new String[custs.length][3];
        //** 2 Write a for loop to populate the newData array with
        //**   customer id, name, and addr obtained from custs array
		for (int i = 0; i < newData.length; i++) {
			newData[i][0] = custs[i].getId();
			newData[i][1] = custs[i].getName();
			newData[i][2] = custs[i].getAddr();
		}
        //** 3 Invoke the setDataVector method on the tableModel
        //**   passing it newData and tableHeaders arrays.
		tableModel.setDataVector(newData, tableHeaders);
    }
}
