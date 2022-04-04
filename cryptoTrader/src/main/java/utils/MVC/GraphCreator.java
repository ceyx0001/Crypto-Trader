package utils.MVC;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Class which creates and manages graphs used to visualize trades
 *
 * @author Jun Shao
 * @since 2022-04-02
 */
public class GraphCreator extends Subject {
	//initializing variables
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel dtm;
	private ChartPanel bar;
	private final int quantityCol = 5;
	private final int stratCol = 1;
	private final int nameCol = 0;

	/**
	 * Constructor method
	 */
	public GraphCreator() {
		scrollPane = new JScrollPane();
		Object[] headers = { "Broker", "Strategy", "Coin List Needed", "CryptoCoin", "Action", "Quantity",
				"Price (CAD)",
				"Date (dd-MM-YY)" };
		dtm = new DefaultTableModel(headers, 0);
		table = new JTable(dtm);
		bar = new ChartPanel(null);
	}

	/**
	 * Main graph method which is used to call other methods that create charts and graphics on the user interface
	 * @param map is a hashmap of trade and broker data
	 * @param data is a table of data in form String[][]
	 * @param missingInfo is a boolean representing whether or not there is missing info in the broker table
	 */
	public void createCharts(HashMap<String, HashMap<String, Integer>> map, String[][] data, boolean missingInfo) {
		createTableOutput(data);
		createBar(map, data, missingInfo);
		notifyObservers();
	}

	/**
	 * Method which creates table that outputs data from trade results
	 * @param data is a table of type String[][] that represents trade data
	 */
	private void createTableOutput(String[][] data) {
		for (int row = 0; row < data.length; row++) {
			dtm.addRow(data[row]);
		}

		scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Broker Actions",
				TitledBorder.CENTER,
				TitledBorder.TOP));

		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);
	}

	/**
	 * Method which creates a bar chart and shows it on the user interface
	 * @param map is a hashmap of trade and broker data
	 * @param data is a table of data in form String[][]
	 * @param missingInfo is a boolean representing whether or not information is missing
	 */
	private void createBar(HashMap<String, HashMap<String, Integer>> map, String[][] data, boolean missingInfo) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		//check if brokers are missing information
		if (missingInfo) {
			JOptionPane.showConfirmDialog(null,
					"Brokers are missing the required coins for the strategies. Refer to the Trader Actions table for more information.",
					"Error While Performing Trade", JOptionPane.DEFAULT_OPTION);
		}

		for (int r = 0; r < data.length; r++) {
			HashMap<String, Integer> brokerStrats = map.get(data[r][nameCol]);
			for (int c = 0; c < data.length; c++) {
				if (!(data[r][quantityCol].equals("Null") || data[r][quantityCol].equals("0"))) {
					int count = brokerStrats.get(data[r][stratCol]);
					dataset.setValue(count, data[r][nameCol], data[r][stratCol]);
				}
			}
		}

		JFreeChart barChart = ChartFactory.createBarChart(
				"Actions Performed By Traders So Far",
				"Strategy",
				"Actions (Purchases and Sales)",
				dataset,
				PlotOrientation.VERTICAL,
				true, false, false);

		bar = new ChartPanel(barChart);
		bar.setPreferredSize(new Dimension(600, 300));
		bar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		bar.setBackground(Color.white);
	}

	/**
	 * Getter method which returns the bar chart panel
	 * @return ChartPanel for bar
	 */
	public ChartPanel getBar() {
		return bar;
	}

	/**
	 * Getter method which returns the scroll pane
	 * @return JScrollPane
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
}
