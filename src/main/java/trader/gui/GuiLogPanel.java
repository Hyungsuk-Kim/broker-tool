package trader.gui;

import javax.swing.*;
import java.awt.*;

public class GuiLogPanel extends JPanel {

    // LogPanel Components
    protected JLabel logLb;
    protected JTextArea logTa = new JTextArea(7, 50);
    protected JScrollPane logSp = new JScrollPane(logTa);
    private String logName;

    /** Creates a new instance of LogPanel */
    public GuiLogPanel(String logName) {
        this.logName = logName;
        buildLogPanel();
    }

    void buildLogPanel() {
        logLb = new JLabel(logName, SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(logLb, BorderLayout.NORTH);
        this.add(logSp, BorderLayout.CENTER);
    }

    public void setLog(String log) {
        logTa.setText(log);
    }

    public void addToLog(String newLog) {
        logTa.append(newLog + "\n");
    }
}
