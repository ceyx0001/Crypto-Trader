package utils.MVC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/** 
 * This class is the view component of the trading system
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public class TradeView extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private TradeModel model;
	private GraphCreator vc;
	private JPanel stats;
	private JButton trade;
	private JButton addRow;
	private JButton remRow;
	private DefaultTableModel dtm;
	private JTable table;

	/**
	 * Method which returns trade button
	 * @return trade the button thats starts the trading process
	 */
	protected JButton getTradeButton() {
		return trade;
	}

	/**
	 * Method which returns add button
	 * @return add the button that adds rows
	 */
	protected JButton getAddButton() {
		return addRow;
	}

	/**
	 * Method which returns remove row button
	 * @return remove the button that removes rows
	 */
	protected JButton getRemButton() {
		return remRow;
	}

	/**
	 * Method which returns JTable object table
	 * @return table the table for user input
	 */
	protected JTable getTable() {
		return table;
	}

	/**
	 * Method which returns JPanel object
	 * @return stats the stats panel of the view
	 */
	protected JPanel getStats() {
		return stats;
	}

	/**
	 * Method which returns DefaulTableModel object
	 * @return default table model
	 */
	protected DefaultTableModel getDTM() {
		return dtm;
	}

	protected void emptyRowError(int row, String msg) {
		JOptionPane.showMessageDialog(this, "please fill in the " + msg + " on line " + (row + 1));
	}

	protected TradeView(TradeModel model, GraphCreator vc) {
		super("Crypto Trading Tool");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.model = model;
		this.vc = vc;
		model.attach(this);
		vc.attach(this);
		dtm = new DefaultTableModel(new Object[] { "Trading Client", "Coin List", "Strategy Name" }, 1);

		//add perform trade button
		trade = new JButton("Perform Trade");
		trade.setActionCommand("refresh");

		//add perform trade button panel
		JPanel south = new JPanel();
		south.add(trade);

		//add trading broker table and its panel
		table = new JTable(dtm);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setPreferredSize(new Dimension(600, 300));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Broker Table",
				TitledBorder.CENTER, TitledBorder.TOP));
		Vector<String> strategyNames = new Vector<String>();
		strategyNames.add("buy ADA");
		strategyNames.add("buy BTC");
		strategyNames.add("sell LUNA");
		strategyNames.add("sell BNB");

		TableColumn strategyColumn = table.getColumnModel().getColumn(2);
		JComboBox<String> comboBox = new JComboBox<String>(strategyNames);
		strategyColumn.setCellEditor(new DefaultCellEditor(comboBox));

		addRow = new JButton("Add Row");
		remRow = new JButton("Remove Row");
		addRow.setActionCommand("addTableRow");
		remRow.setActionCommand("remTableRow");

		scrollPane.setPreferredSize(new Dimension(600, 300));
		table.setFillsViewportHeight(true);
		

		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		east.add(scrollPane);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(addRow);
		buttons.add(remRow);
		east.add(buttons);

		//west JPanel for graphics and results
		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		west.setPreferredSize(new Dimension(850, 650));
		stats = new JPanel();
		stats.setLayout(new GridLayout(2, 2));

		west.add(stats);

		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(west, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}

	/**
	 * Method which updates adds component from parameter to current JPanel and refreshes the panel
	 *
	 * @param component is a JComponent object
	 */
	protected void updateStats(JComponent component) {
		stats.add(component);
		stats.revalidate();
		super.revalidate();
	}

	/**
	 * Method which updates the observer depending on what the user has changed
	 *
	 * @param changed object represents what has caused the update
	 */
	@Override
	public void updateObserver(Subject changed) {
		if (changed.equals(model)) {
			JOptionPane.showConfirmDialog(null, "Trading process starting",
					"Perform Trade", JOptionPane.DEFAULT_OPTION);
			stats.removeAll();
		} else if (changed.equals(vc)) {
			updateStats(vc.getScrollPane());
			updateStats(vc.getBar());
		}
	}
}
