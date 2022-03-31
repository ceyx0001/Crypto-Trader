package cryptoTrader.utils.trade;

import cryptoTrader.utils.api.DataVisualizationCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class TradeController implements ActionListener, TableModelListener {
    private TradeModel model;
    private TradeView view;

    public TradeController() {
        model = new TradeModel();
        view = TradeView.getInstance(model.getBrokersTable());
        addListeners();
    }

    public void addListeners() {
        view.getTradeButton().addActionListener(this);
        view.getRemButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
        view.getTable().getModel().addTableModelListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        DefaultTableModel dtm = view.getDTM();

        if ("refresh".equals(command)) {
            for (int count = 0; count < dtm.getRowCount(); count++) {
                Object traderObject = dtm.getValueAt(count, 0);
                if (traderObject == null) {
                    JOptionPane.showMessageDialog(view, "please fill in Trader name on line " + (count + 1));
                    return;
                }
                String traderName = traderObject.toString();
                Object coinObject = dtm.getValueAt(count, 1);
                if (coinObject == null) {
                    JOptionPane.showMessageDialog(view, "please fill in cryptocoin list on line " + (count + 1));
                    return;
                }
                String[] coinNames = coinObject.toString().split(",");
                Object strategyObject = dtm.getValueAt(count, 2);
                if (strategyObject == null) {
                    JOptionPane.showMessageDialog(view, "please fill in strategy name on line " + (count + 1));
                    return;
                }
                String strategyName = strategyObject.toString();
                model.getBrokers().put(traderName, Arrays.asList(Arrays.toString(coinNames), strategyName));
            }
            view.getStats().removeAll();
            DataVisualizationCreator creator = new DataVisualizationCreator();
            creator.createCharts();
            model.saveBrokers();
        } else if ("addTableRow".equals(command)) {
            dtm.addRow(new String[3]);
        } else if ("remTableRow".equals(command)) {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow != -1) {
                dtm.removeRow(selectedRow);
            }
        }
    }

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
}
