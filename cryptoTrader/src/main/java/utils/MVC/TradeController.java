package utils.MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private GraphCreator vc;

    /**
     * Constructor for TradeController class which initializes its fields
     * 
     * @param Nothing
     * @return Nothing
     */
    public TradeController() {
        model = new TradeModel();
        vc = new GraphCreator();
        view = new TradeView(model.getBrokersTable(), model, vc);
        view.getTradeButton().addActionListener(this);
        view.getRemButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getTable().getModel().addTableModelListener(this);
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
     * Checks if a broker is a duplicate after a table cell was edited
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
            if (model.getBrokers().containsKey(name)) {
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
                view.emptyRowError(count);
                return;
            }
            Object coinObject = dtm.getValueAt(count, 1);
            if (coinObject == null) {
                view.emptyRowError(count);
                return;
            }
            Object strategyObject = dtm.getValueAt(count, 2);
            if (strategyObject == null) {
                view.emptyRowError(count);
                return;
            }

            String coins = coinObject.toString().replaceAll("\\{|\\}|=|\\s", "");
          
                String name = traderObject.toString();
                String strat = strategyObject.toString();
                model.getBrokers().put(name, model.newBroker(name, coins, strat));
                String target = model.getBrokers().get(name).getStrat().getTarget();
                model.getNeededCoins().add(target);
                String[] temp = coins.split(",");
                for (int i = 0; i < temp.length; i++) {
                    model.getNeededCoins().add(temp[i]);
                }
            
        }

        model.notifyObservers();
        vc.createCharts(model.getResults());
        model.saveBrokers();
    }
}
