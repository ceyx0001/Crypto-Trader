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
	private DataVisualizationCreator vc;

	private JPanel stats;
	private JButton trade;
	private JButton addRow;
	private JButton remRow;
	private DefaultTableModel dtm;
	private JTable table;

	public JButton getTradeButton() {
		return trade;
	}

	public JButton getAddButton() {
		return addRow;
	}

	public JButton getRemButton() {
		return remRow;
	}

	public JTable getTable() {
		return table;
	}

	public JPanel getStats() {
		return stats;
	}

	public DefaultTableModel getDTM() {
		return dtm;
	}

	protected TradeView(DefaultTableModel dtm, TradeModel model, DataVisualizationCreator vc) {
		// Set window title
		super("Crypto Trading Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.model = model;
		this.vc = vc;
		model.attach(this);
		vc.attach(this);

		// Set top bar


		JPanel north = new JPanel();

//		north.add(strategyList);

		// Set bottom bar
//		JLabel from = new JLabel("From");
//		UtilDateModel dateModel = new UtilDateModel();
//		Properties p = new Properties();
//		p.put("text.today", "Today");
//		p.put("text.month", "Month");
//		p.put("text.year", "Year");
//		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
//		@SuppressWarnings("serial")
//		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new AbstractFormatter() {
//			private String datePatern = "dd/MM/yyyy";
//
//			private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePatern);
//
//			@Override
//			public Object stringToValue(String text) throws ParseException {
//				return dateFormatter.parseObject(text);
//			}
//
//			@Override
//			public String valueToString(Object value) throws ParseException {
//				if (value != null) {
//					Calendar cal = (Calendar) value;
//					return dateFormatter.format(cal.getTime());
//				}
//
//				return "";
//			}
//		});
		this.dtm = dtm;
		trade = new JButton("Perform Trade");
		trade.setActionCommand("refresh");


		JPanel south = new JPanel();
		
		south.add(trade);

		table = new JTable(dtm);
		//table.setPreferredSize(new Dimension(600, 300));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trading Client Actions",
				TitledBorder.CENTER, TitledBorder.TOP));
		Vector<String> strategyNames = new Vector<String>();
		strategyNames.add("None");
		strategyNames.add("Buy ADA");
		strategyNames.add("Buy BTC");
		strategyNames.add("Buy LUNA");
		strategyNames.add("Sell ETH");

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
//		east.setLayout();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
//		east.add(table);
		east.add(scrollPane);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(addRow);
		buttons.add(remRow);
		east.add(buttons);
//		east.add(selectedTickerListLabel);
//		east.add(selectedTickersScrollPane);

		// Set charts region
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(850, 650));
		stats = new JPanel();
		stats.setLayout(new GridLayout(2, 2));

		west.add(stats);

		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(west, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
//		getContentPane().add(west, BorderLayout.WEST);
		pack();
		setVisible(true);
	}

	public void updateStats(JComponent component) {
		stats.add(component);
		stats.revalidate();
		super.revalidate();
	}

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
