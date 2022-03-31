package cryptoTrader.utils.trade;

import cryptoTrader.utils.api.DataVisualizationCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class TradeController implements ActionListener {
    private TradeModel model;
    private TradeView view;

    public TradeController() {
        model = new TradeModel();
        view = TradeView.getInstance(model.getBrokers());
        addListeners();
    }

    public void addListeners() {
        view.getTradeButton().addActionListener(this);
        view.getRemButton().addActionListener(this);
        view.getAddButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        DefaultTableModel dtm = view.getDTM();
        
        if ("refresh".equals(command)) {
            HashMap<String, List<String>> brokers = new HashMap<String, List<String>>();
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
                brokers.put(traderName, Arrays.asList(Arrays.toString(coinNames), strategyName));
            }
            view.getStats().removeAll();
            DataVisualizationCreator creator = new DataVisualizationCreator();
            creator.createCharts();
            model.saveBrokers(brokers);
        } else if ("addTableRow".equals(command)) {
            dtm.addRow(new String[3]);
        } else if ("remTableRow".equals(command)) {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow != -1)
                dtm.removeRow(selectedRow);
        }
    }
}
