package utils.MVC;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
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

public class GraphCreator extends Subject {
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel dtm;
	ChartPanel time;
	ChartPanel scatter;
	ChartPanel bar;

	public GraphCreator() {
		scrollPane = new JScrollPane();
		Object[] headers = { "Broker", "Strategy", "Required Coin List", "CryptoCoin", "Action", "Quantity",
				"Price (CAD)",
				"Date (dd-MM-YY)" };
		dtm = new DefaultTableModel(headers, 0);
		table = new JTable(dtm);
		time = new ChartPanel(null);
		scatter = new ChartPanel(null);
		bar = new ChartPanel(null);
	}

	public void createCharts(HashMap<String, HashMap<String, Integer>> map, String[][] data, boolean missingInfo) {
		createTableOutput(data);
		createBar(map, missingInfo);
		notifyObservers();
	}

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


	private void createBar(HashMap<String, HashMap<String, Integer>> map, boolean missingInfo) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

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
