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

/**
 * Class which mainly visualizes data and trade results with graphics and tables
 *
 * @author Jun Shao
 */
public class DataVisualizationCreator extends Subject {
	JScrollPane scrollPane;
	ChartPanel time;
	ChartPanel scatter;
	ChartPanel bar;

	/**
	 * Constructor class for DataVisualizationCreator which initializes its fields, and it takes no parameters
	 */
	public DataVisualizationCreator() {
		scrollPane = new JScrollPane();
		time = new ChartPanel(null);
		scatter = new ChartPanel(null);
		;
		bar = new ChartPanel(null);
		;
	}

	/**
	 * Main class which is called to create various charts using methods in the DataVisualizationCreator class
	 * @param data is a 2D array of type String which represents data from trades
	 */
	public void createCharts(String[][] data) {
		// createTextualOutput();
		createTableOutput(data);
		// createTimeSeries();
		// createScatter();
		createBar(data);
		notifyObservers();
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

	/**
	 * ADD INFORMATION IDK!!!!!!!!!!!
	 * ADD INFORMATION IDK!!!!!!!!!!!
	 * ADD INFORMATION IDK!!!!!!!!!!!
	 * ADD INFORMATION IDK!!!!!!!!!!!
	 *
	 * @param name
	 * @param strat
	 * @param map
	 */
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

	/**
	 * Class which creates a bar chart when given data and displays it on the user interface
	 * @param data is a 2D array of type String which represents data from trades
	 */
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

	/**
	 * Getter method which returns a ChartPanel
	 * @return a ChartPanel
	 */
	public ChartPanel getBar() {
		return bar;
	}

	/**
	 * Getter method which returns the scroll pane
	 * @return JScrollPane which represents the scrolling pane in the user interface
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}
}
