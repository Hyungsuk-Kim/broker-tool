package trader.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trader.*;

public class BrokerGui {

    private BrokerController brokerController;
    private BrokerModel model;

    protected JFrame frame = new JFrame("BrokerTool");
    protected JPanel viewPan = new JPanel();
    /*protected JPanel selPan = new JPanel();
    protected JPanel cardPan = new JPanel();
    protected CardLayout card = new CardLayout();*/
    protected JTabbedPane tabs = new JTabbedPane();

    private BrokerPanel[] panels = new BrokerPanel[5];
	private GuiLogPanel logPan = new GuiLogPanel("BrokerTool Log");

    /** Creates a new instance of BrokerGUI */
    public BrokerGui(BrokerModel model) {
        this.model = model;
        buildDisplay();
    }

    public void addController(BrokerController controller) {
        brokerController = controller;
        for (int i = 0; panels[i] != null; i++) {
            panels[i].registerController(brokerController);
        }
    }

    protected void buildDisplay() {
       /* selPan.setLayout(new GridLayout(1, 0)); // arbitrary number of columns
        cardPan.setLayout(card);
        viewPan.setLayout(new BorderLayout());
        viewPan.add(selPan, BorderLayout.NORTH);
        viewPan.add(cardPan, BorderLayout.CENTER);*/
    	viewPan.setLayout(new BorderLayout());
    	viewPan.add(tabs, BorderLayout.CENTER);
        
        //* Code to create and add the customer panel to the panels array.
        //panels[0] = new MessagePanel(model, "Place Holder for CustomerPanel"); // temporary
        panels[0] = new CustomerPanel(model);
        //addCardView("Customer Details", panels[0].getPanel());
        tabs.add("Customer Details", panels[0].getPanel());

        //* Code to build AllCustomersPanel
        //panels[1] = new AllCustomerPanel(model);
        panels[1] = new AllCustomerTablePanel(model);
        //addCardView("All Customers", panels[1].getPanel());
        tabs.add("All Customers", panels[1].getPanel());

        //* Code to build the Stock Panel goes here
        //panels[2] = new MessagePanel(model, "Place Holder for StockPanel"); // temporary
        panels[2] = new StockTablePanel(model);
        //addCardView("Stock", panels[2].getPanel());
        tabs.add("All stocks", panels[2].getPanel());

        //* Code to build Portfolio Panel goes here
        panels[3] = new MessagePanel(model, "Place Holder for PortfolioPanel"); // temporary
        //addCardView("Portfolio", panels[3].getPanel());
        tabs.add("Portfolio", panels[3].getPanel());

        // build and display frame
        frame.setLayout(new BorderLayout());
        frame.add(viewPan, BorderLayout.CENTER);
        // build and add log panel to contentPane
        /*JLabel templogLb = new JLabel("Place Holder for Log Panel", SwingConstants.CENTER);
        frame.add(templogLb, BorderLayout.SOUTH);*/
        //** uncomment following line after
        frame.add(logPan, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /*private void addCardView(String label, JPanel panel) {
        cardPan.add(panel, label);
        JButton newButton = new JButton(label);
        selPan.add(newButton);    	
        newButton.addActionListener(selectionPanListener);
    }*/
        
    /*private ActionListener selectionPanListener = new ActionListener() {
        String buttonName;
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonName = e.getActionCommand();
            System.out.println("SelectionPanelListener buttonName = " + buttonName);
            card.show(cardPan, buttonName);
        }
    };*/

    public void displayObject(Object obj) {
        if (obj instanceof Customer) {
            panels[0].display(obj);
            panels[1].refresh();
        }

        if (obj instanceof Customer[]) {
            panels[1].display(obj);
        }

        if (obj instanceof Portfolio) {
            //TBD
        }
        if (obj instanceof Stock[]) {
            //TBD
        	panels[2].display(obj);
        }
        if (obj instanceof Exception) {
            // log the exception
        	if (obj != null) {
        		logPan.addToLog(((Exception) obj).getMessage());
        	}
        }
    }

    public static void main(String[] args) throws Exception {
        //BrokerModel model = null;
        BrokerModel model = new trader.BrokerModelImpl(new trader.db.BrokerDAOImpl());
        BrokerGui gui = new BrokerGui(model);
    }
}
