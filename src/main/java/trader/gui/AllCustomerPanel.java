package trader.gui;

import java.awt.*;
import javax.swing.*;
import trader.*;

public class AllCustomerPanel implements BrokerPanel {

    private BrokerModel model;
    private BrokerController controller;

    // AllCustomersPanel components
    protected JPanel allCustPan = new JPanel();
    protected JLabel allCustLb = new JLabel("All Customers", SwingConstants.CENTER);
    protected JTextArea allCustTa = new JTextArea();
    protected JScrollPane allCustSp = new JScrollPane(allCustTa);

    /** Creates a new instance of AllCustomerPanel */
    public AllCustomerPanel(BrokerModel model) {
        this.model = model;
        buildAllCustPanel();
		refresh();
    }
    @Override
    public void registerController(BrokerController controller) {
        this.controller = controller;
    }

    // build all customer panel
    void buildAllCustPanel() {
        allCustPan.setLayout(new BorderLayout());
        allCustPan.add(allCustLb, BorderLayout.NORTH);
        allCustPan.add(allCustSp, BorderLayout.CENTER);
        //allCustTa.setText("all customer display TBD using JTable");
    }
    @Override
    public void refresh() {
        try {
            Customer[] customers = model.getAllCustomers();
            refreshAllCustPan(customers);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    @Override
    public void display(Object obj) {
        if (obj instanceof Customer[]) {
            refreshAllCustPan((Customer[]) obj);
        }
    }
    @Override
    public JPanel getPanel() {
        return allCustPan;
    }

    void refreshAllCustPan(Customer[] custs) {
        allCustTa.setText("");
    	for (Customer cust : custs) {
    		allCustTa.append(cust.toString() + "\n");
    	}
    	
    }
}
