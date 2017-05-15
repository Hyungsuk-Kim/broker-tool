package trader.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trader.*;

public class CustomerPanel implements BrokerPanel {

    private BrokerModel model;
    private BrokerController controller;

    // CustomerPanel components
    protected JPanel custPan = new JPanel();
    protected JLabel nameLb = new JLabel("Customer Name");
    protected JLabel idLb = new JLabel("Customer Identity");
    protected JLabel addrLb = new JLabel("Customer Address");
    protected JTextField nameTf = new JTextField(25);
    protected JTextField idTf = new JTextField(25);
    protected JTextField addrTf = new JTextField(25);
    protected JButton getBt = new JButton("Get Customer");
    protected JButton updBt = new JButton("Update Customer");
    protected JButton addBt = new JButton("Add Customer");
    protected JButton delBt = new JButton("Delete Customer");

    /** Creates a new instance of CustomerPanel */
    public CustomerPanel(BrokerModel model) {
        this.model = model;
        buildPanel();
        addCustomerPanelListeners();  // Uncomment later
    }
    @Override
    public void registerController(BrokerController controller) {
        this.controller = controller;
    }
    @Override
    public void refresh() {
        Customer cust = null;
        try {
            // Get customer id from idTf text field
            String custId = getCustId();
            // (if not blank) get the corresponding customer object from the model
            if (!custId.equals("")) {
                //**1 Get customer object corresponding to idTf from model and
                //    assign to cust.
            	cust = model.getCustomer(getCustId());
            	//**2 Update nameTf from value in cust
            	nameTf.setText(cust.getName());
                //**3 Update addrTf from value in cust
            	addrTf.setText(cust.getAddr());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Override
    public void display(Object obj) {
        Customer cust;
        if (obj instanceof Customer) {
            cust = (Customer) obj;
            //** if cust.custId equals custId on the CustomerPanel
            //   then display the customer object on the CustomerPanel
            if (cust.getId().equals(getCustId())) {
            	//refresh();
            	nameTf.setText(cust.getName());
            	addrTf.setText(cust.getAddr());
            }
        }
    }
    @Override
    public JPanel getPanel() {
        return custPan;
    }

    // helper methods
    protected String getCustId() {
        return idTf.getText().trim();
    }

    protected String getCustName() {
        return nameTf.getText().trim();
    }

    protected String getCustAddr() {
        return addrTf.getText().trim();
    }

    void buildPanel() {
        //** build the customer panel.
    	custPan.setLayout(new GridLayout(5, 2));
    	custPan.add(nameLb);
    	custPan.add(nameTf);
    	custPan.add(idLb);
    	custPan.add(idTf);
    	custPan.add(addrLb);
    	custPan.add(addrTf);
    	custPan.add(getBt);
    	custPan.add(updBt);
    	custPan.add(addBt);
    	custPan.add(delBt);
    }

    // Event handling method needed later
    protected void addCustomerPanelListeners() {
    	
    	ActionListener handler = (new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == getBt) {
					controller.handleGetCustomerGesture(getCustId());
				} else if (e.getSource() == updBt) {
					controller.handleUpdateCustomerGesture(createCustomer());
				} else if (e.getSource() == addBt) {
					controller.handleAddCustomerGesture(createCustomer());
				} else if (e.getSource() == delBt) {
					controller.handleDeleteCustomerGesture(createCustomer());
				}
			}
			
			private Customer createCustomer() {
				return new Customer(getCustId(), getCustName(), getCustAddr());
			}
			
		});
    	
    	getBt.addActionListener(handler);
    	addBt.addActionListener(handler);
    	updBt.addActionListener(handler);
    	delBt.addActionListener(handler);
    }
}
