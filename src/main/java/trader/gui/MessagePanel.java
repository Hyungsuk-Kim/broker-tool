package trader.gui;

import java.awt.*;
import javax.swing.*;
import trader.*;

public class MessagePanel implements BrokerPanel {

    private BrokerModel model;
    private BrokerController controller;

    // MessagePanel components
    protected JPanel msgPan = new JPanel();
    protected JLabel msgLabel;

    /** Creates a new instance of MessagePanel */
    public MessagePanel(BrokerModel model, String msg) {
        this.model = model;
        msgLabel = new JLabel(msg);
        buildPanel();
    }

    public void registerController(BrokerController controller) {
        this.controller = controller;
    }

    public void refresh() {
        msgPan.validate();
    }

    public void display(Object obj) {
        refresh();
    }

    public JPanel getPanel() {
        return msgPan;
    }

    // helper methods
    void buildPanel() {
        msgPan.setLayout(new BorderLayout());
        msgPan.add(msgLabel, BorderLayout.CENTER);
        msgPan.setBackground(Color.yellow);
    }
}
