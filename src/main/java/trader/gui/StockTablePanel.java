package trader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import trader.*;

public class StockTablePanel implements BrokerPanel {
	
	private BrokerModel model;
    private BrokerController controller;
    
    protected JPanel allStockPan = new JPanel();
    protected JLabel allStockLbl = new JLabel("All Stocks", SwingConstants.CENTER);
    protected String[] tableHeaders = {"Symbol", "Price"};
    protected DefaultTableModel tableModel;
    protected JTable table;
    protected JScrollPane tablePane;
    
    public StockTablePanel(BrokerModel model) {
    	this.model = model;
    	buildStockPanel();
    	refresh();
    }
    
    public void buildStockPanel() {
    	allStockPan.setLayout(new BorderLayout());
    	allStockPan.add(allStockLbl, BorderLayout.NORTH);
    	
    	tableModel = new DefaultTableModel();
    	table = new JTable(tableModel);
    	tablePane = new JScrollPane(table);
    	
    	allStockPan.add(tablePane, BorderLayout.CENTER);
    	Dimension dim = new Dimension(500, 150);
        table.setPreferredScrollableViewportSize(dim);
    }

	@Override
	public void registerController(BrokerController controller) {
		this.controller = controller;
	}

	@Override
	public void display(Object obj) {
		if (obj instanceof Stock[]) {
			refreshAllStocks((Stock[]) obj);
		}
	}

	@Override
	public void refresh() {
		try {
			Stock[] stocks = model.getAllStocks();
			refreshAllStocks(stocks);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void refreshAllStocks(Stock[] allStocks) {
		String[][] newData = new String[allStocks.length][2];
		for (int i = 0; i < newData.length; i++) {
			newData[i][0] = allStocks[i].getSymbol();
			newData[i][1] =  ((Float) allStocks[i].getPrice()).toString();
		}
		tableModel.setDataVector(newData, tableHeaders);
	}

	@Override
	public JPanel getPanel() {
		return allStockPan;
	}

}
