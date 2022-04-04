package utils.MVC;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraphCreator extends Subject {
	JScrollPane scrollPane;
	ChartPanel time;
	ChartPanel scatter;
	ChartPanel bar;

	public GraphCreator() {
		scrollPane = new JScrollPane();
		time = new ChartPanel(null);
		scatter = new ChartPanel(null);
		bar = new ChartPanel(null);
	}

	public void createCharts(String[][] data) {
		createTableOutput(data);
		createBar(data);
		notifyObservers();
	}

	private void createTableOutput(String[][] data) {
		Object[] columnNames = { "Broker", "Strategy", "Required Coin List", "CryptoCoin", "Action", "Quantity", "Price (CAD)",
				"Date (dd-MM-YY)" };
		JTable table = new JTable(data, columnNames);

		scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Broker Actions",
				TitledBorder.CENTER,
				TitledBorder.TOP));

		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);
	}

	private void tally(String broker, String strat, HashMap<String, HashMap<String, Integer>> map) {
		HashMap<String, Integer> brokerStrats;
		if (map.get(broker) == null) {
			brokerStrats = new HashMap<String, Integer>();
			brokerStrats.put(strat, 1);
			map.put(broker, brokerStrats);
		} else {
			brokerStrats = map.get(broker);
			brokerStrats.put(strat, brokerStrats.get(strat) + 1);
			map.put(broker, brokerStrats);
		}
	}

	private void createBar(String[][] data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();

		boolean missingInfo = false;
		for (int row = 0; row < data.length; row++) {
			tally(data[row][0], data[row][1], map);
			if (data[row][5].equals("Null")) {
				missingInfo = true;
			}
		}

		if (missingInfo) {
			JOptionPane.showConfirmDialog(null,
					"Brokers are missing the required coins for the strategies. Refer to the Trader Actions table for more information.",
					"Error While Performing Trade", JOptionPane.DEFAULT_OPTION);
		}
		

		for (String broker : map.keySet()) {
			HashMap<String, Integer> brokerStrats = map.get(broker);
			for (String strat : brokerStrats.keySet()) {
				int count = brokerStrats.get(strat);
				dataset.setValue(count, broker, strat);
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

	public ChartPanel getBar() {
		return bar;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}
}
