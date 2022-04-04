package utils.MVC;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class DataVisualizationCreator extends Subject {
	JScrollPane scrollPane;
	ChartPanel time;
	ChartPanel scatter;
	ChartPanel bar;

	public DataVisualizationCreator() {
		scrollPane = new JScrollPane();
		time = new ChartPanel(null);
		scatter = new ChartPanel(null);
		;
		bar = new ChartPanel(null);
		;
	}

	public void createCharts(String[][] data) {
		// createTextualOutput();
		createTableOutput(data);
		// createTimeSeries();
		// createScatter();
		createBar(data);
		notifyObservers();
	}

	private void createTextualOutput() {
		DefaultTableModel dtm = new DefaultTableModel(new Object[] { "Broker Name", "Ticker List", "Strategy Name" },
				1);
		JTable table = new JTable(dtm);
		table.setPreferredSize(new Dimension(600, 300));
		scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Broker Actions",
				TitledBorder.CENTER,
				TitledBorder.TOP));

		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);
	}

	private void createTableOutput(String[][] data) {
		// Dummy dates for demo purposes. These should come from selection menu
		Object[] columnNames = { "Trader", "Strategy", "CryptoCoin", "Action", "Quantity", "Price (CAD)",
				"Date (dd-MM-YY)" };
		JTable table = new JTable(data, columnNames);
		// table.setPreferredSize(new Dimension(600, 300));

		scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Trader Actions",
				TitledBorder.CENTER,
				TitledBorder.TOP));

		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);
	}

	private void createTimeSeries() {
		TimeSeries series1 = new TimeSeries("Bitcoin - Daily");
		series1.add(new Day(13, 9, 2021), 50368.67);
		series1.add(new Day(14, 9, 2021), 51552.05);
		series1.add(new Day(15, 9, 2021), 47228.30);
		series1.add(new Day(16, 9, 2021), 45263.90);
		series1.add(new Day(17, 9, 2021), 46955.41);

		TimeSeries series2 = new TimeSeries("Ethereum - Daily");
		series2.add(new Day(13, 9, 2021), 3912.28);
		series2.add(new Day(14, 9, 2021), 3927.27);
		series2.add(new Day(15, 9, 2021), 3460.48);
		series2.add(new Day(16, 9, 2021), 3486.09);
		series2.add(new Day(17, 9, 2021), 3550.29);

		TimeSeries series3 = new TimeSeries("Cardano - Daily");
		series3.add(new Day(13, 9, 2021), 2.87);
		series3.add(new Day(14, 9, 2021), 2.84);
		series3.add(new Day(15, 9, 2021), 2.41);
		series3.add(new Day(16, 9, 2021), 2.43);
		series3.add(new Day(17, 9, 2021), 2.59);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new LogAxis("Price(USD)"));

		// plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		// plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
		// plot.mapDatasetToRangeAxis(2, 2);// 3rd dataset to 3rd y-axis

		JFreeChart chart = new JFreeChart("Daily Price Line Chart", new Font("Serif", java.awt.Font.BOLD, 18), plot,
				true);

		time = new ChartPanel(chart);
		time.setPreferredSize(new Dimension(800, 300));
		time.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		time.setBackground(Color.white);
	}

	private void createScatter() {
		TimeSeries series1 = new TimeSeries("Bitcoin - Daily");
		series1.add(new Day(13, 9, 2021), 50368.67);
		series1.add(new Day(14, 9, 2021), 51552.05);
		series1.add(new Day(15, 9, 2021), 47228.30);
		series1.add(new Day(16, 9, 2021), 45263.90);
		series1.add(new Day(17, 9, 2021), 46955.41);

		TimeSeries series2 = new TimeSeries("Ethereum - Daily");
		series2.add(new Day(13, 9, 2021), 3912.28);
		series2.add(new Day(14, 9, 2021), 3927.27);
		series2.add(new Day(15, 9, 2021), 3460.48);
		series2.add(new Day(16, 9, 2021), 3486.09);
		series2.add(new Day(17, 9, 2021), 3550.29);

		TimeSeries series3 = new TimeSeries("Cardano - Daily");
		series3.add(new Day(13, 9, 2021), 2.87);
		series3.add(new Day(14, 9, 2021), 2.84);
		series3.add(new Day(15, 9, 2021), 2.41);
		series3.add(new Day(16, 9, 2021), 2.43);
		series3.add(new Day(17, 9, 2021), 2.59);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		XYPlot plot = new XYPlot();
		XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new LogAxis("Price(USD)"));

		// plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		// plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		JFreeChart scatterChart = new JFreeChart("Daily Price Scatter Chart",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		scatter = new ChartPanel(scatterChart);
		scatter.setPreferredSize(new Dimension(600, 300));
		scatter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scatter.setBackground(Color.white);
	}

	private void tally(String name, String strat, HashMap<String, HashMap<String, Integer>> map) {
		HashMap<String, Integer> brokerStrats;
		if (map.get(name) == null) {
			brokerStrats = new HashMap<String, Integer>();
			brokerStrats.put(strat, 1);
			map.put(name, brokerStrats);
		} else {
			brokerStrats = map.get(name);
			brokerStrats.put(strat, brokerStrats.get(strat) + 1);
			map.put(name, brokerStrats);
		}
	}

	private void createBar(String[][] data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
		for (int row = 0; row < data.length; row++) {
			tally(data[row][0], data[row][1], map);
		}

		for (String name : map.keySet()) {
			HashMap<String, Integer> brokerStrats = map.get(name);
			for (String strat : brokerStrats.keySet()) {
				int count = brokerStrats.get(strat);
				System.out.println(count);
				dataset.setValue(count, name, strat);
			}
		}

		JFreeChart barChart = ChartFactory.createBarChart(
				"Actions Performed By Traders So Far",
				"Strategy",
				"Actions (Buys and Sales)",
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
