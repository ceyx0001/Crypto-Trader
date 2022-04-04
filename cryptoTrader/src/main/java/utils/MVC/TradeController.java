package utils.MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
public class TradeController implements ActionListener, TableModelListener {
    private TradeModel model;
    private TradeView view;
    private DataVisualizationCreator vc;

    /**
     * Constructor for TradeController class which initializes its fields
     */
    public TradeController() {
        model = new TradeModel();
        vc = new DataVisualizationCreator();
        view = new TradeView(model.getBrokersTable(), model, vc);
        view.getTradeButton().addActionListener(this);
        view.getRemButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getTable().getModel().addTableModelListener(this);
    }

    /**
     * Method which takes inputs and executes their respective actions.
     *
     * @param e is an ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        DefaultTableModel dtm = view.getDTM();

        if ("refresh".equals(command)) {
            saveTable(dtm);
        } else if ("addTableRow".equals(command)) {
            dtm.addRow(new String[3]);
        } else if ("remTableRow".equals(command)) {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow != -1) {
                Object val = view.getTable().getValueAt(selectedRow, 0);
                String name;
                if (val != null) {
                    name = val.toString();
                    model.removeBroker(name);
                }
                dtm.removeRow(selectedRow);
            }
        }
    }

    /**
     *
     * @param e
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int col = e.getColumn();
        if (col == 0) {
            String name = view.getTable().getModel().getValueAt(row, col).toString();
            if (model.getBrokers().containsKey(name)) {
                JOptionPane.showConfirmDialog(null, name + " already exists.",
                        "Duplicate Broker", JOptionPane.DEFAULT_OPTION);
                view.getTable().getModel().setValueAt("", row, col);
            }
        }
    }

    private void saveTable(DefaultTableModel dtm) {
        for (int count = 0; count < dtm.getRowCount(); count++) {
            Object traderObject = dtm.getValueAt(count, 0);
            if (traderObject == null) {
                JOptionPane.showMessageDialog(view, "please fill in Trader name on line " + (count + 1));
                return;
            }
            String name = traderObject.toString();
            Object coinObject = dtm.getValueAt(count, 1);
            if (coinObject == null) {
                JOptionPane.showMessageDialog(view, "please fill in cryptocoin list on line " + (count + 1));
                return;
            }

            String coins = coinObject.toString().replaceAll("\\[|\\]|\\s", "");
            Object strategyObject = dtm.getValueAt(count, 2);
            if (strategyObject == null) {
                JOptionPane.showMessageDialog(view, "please fill in strategy name on line " + (count + 1));
                return;
            }
            String strat = strategyObject.toString();
            model.getBrokers().put(name, model.newBroker(name, coins, strat));
            model.getNeededCoins().addAll(Arrays.asList(coins.split(",")));
            //System.out.println(model.getBrokers().get(name).getStrat().printStrat());
        }

        view.getStats().removeAll();
        model.notifyObservers();
        vc.createCharts(model.getResults());
        model.saveBrokers();
    }
}
