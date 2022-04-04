package utils.MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * Main class which controls trades and implements action listeners for buttons
 *
 * @author Jun Shao
 * @since 2022-03-30
 */
public class TradeController extends WindowAdapter implements ActionListener, TableModelListener {
    private TradeModel model;
    private TradeView view;
    private GraphCreator gc;

    /**
     * Constructor for TradeController class which initializes its fields
     * 
     * @param Nothing
     * @return Nothing
     */
    public TradeController() {
        model = new TradeModel();
        gc = new GraphCreator();
        view = new TradeView(model, gc);
        view.getTradeButton().addActionListener(this);
        view.getRemButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getTable().getModel().addTableModelListener(this);
        view.addWindowListener(this);
    }

    /**
     * Handles action events by invoking their associated methods
     *
     * @param e is an ActionEvent object
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        DefaultTableModel dtm = view.getDTM();
        int selectedRow = view.getTable().getSelectedRow();
        String name = null;

        if ("refresh".equals(command)) {
            saveTable(dtm);
        } else if ("addTableRow".equals(command)) {
            dtm.addRow(new String[3]);
            if (selectedRow != -1) {
                Object val = view.getTable().getValueAt(selectedRow, 0);
                if (val != null) {
                    name = val.toString();
                }
                model.addBrokerInTable(name);
            }
        } else if ("remTableRow".equals(command)) {
            if (selectedRow != -1) {
                Object val = view.getTable().getValueAt(selectedRow, 0);
                if (val != null) {
                    name = val.toString();
                }
                model.removeBroker(name);
                model.removeBrokerInTable(name);
                dtm.removeRow(selectedRow);
            }
        }
    }

    /**
     * Checks if a broker already exists after a table cell was edited
     * 
     * @param e the TableModelEvent
     * @return void
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int col = e.getColumn();
        if (col == 0) {
            String name = view.getTable().getModel().getValueAt(row, col).toString();
            if (model.hasDupe(name)) {
                JOptionPane.showConfirmDialog(null, name + " already exists.",
                        "Duplicate Broker", JOptionPane.DEFAULT_OPTION);
                view.getTable().getModel().setValueAt("", row, col);
            }
        }
    }

    /**
     * Invokes the methods required to save the broker table
     * to the embedded database and start the trading process
     * 
     * @param e the TableModelEvent
     * @return void
     */
    private void saveTable(DefaultTableModel dtm) {
        for (int count = 0; count < dtm.getRowCount(); count++) {
            Object traderObject = dtm.getValueAt(count, 0);
            if (traderObject == null) {
                view.emptyRowError(count, "broker name");
                return;
            }
            Object coinObject = dtm.getValueAt(count, 1);
            if (coinObject == null || coinObject.equals("")) {
                view.emptyRowError(count, "coin list");
                return;
            }
            Object strategyObject = dtm.getValueAt(count, 2);
            if (strategyObject == null) {
                view.emptyRowError(count, "strategy name");
                return;
            }
            String coins = coinObject.toString().replaceAll("\\{|\\}|=|\\s", "");
            String name = traderObject.toString();
            String strat = strategyObject.toString();
            model.getBrokers().put(name, model.newBroker(name, coins, strat));
        }
        model.notifyObservers();
        model.setDataTable();
        model.setDataMap();
        gc.createCharts(model.getDataMap(), model.getDataTable(), model.isMissingInfo());
        model.logTrade();
    }

    /**
     * Invokes the methods required to exit the system:
     * Disconnects from the database and closes the view component
     * 
     * @param e the WindowEvent
     * @return void
     */
    @Override
    public void windowClosing(WindowEvent e) {
        model.closeConnection();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.exit(0);
    }
}
