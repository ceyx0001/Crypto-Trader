package cryptoTrader.utils.trade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class TradeModel {
    public boolean setBrokers(HashMap<String, List<String>> brokers) {
        try {
            File f = new File("cryptoTrader/src/main/java/cryptoTrader/storage/Brokers.txt");
            if (!f.exists()) {
                f.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
            int l = brokers.size();
            for (Entry<String, List<String>> e : brokers.entrySet()) {
                writer.write(e.getKey() + " ");
                for (String s : e.getValue()) {
                    writer.write(s + " ");
                }
                writer.newLine();
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private HashMap<String, List<String>> getBrokers() {
        HashMap<String, List<String>> brokers = new HashMap<String, List<String>>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("cryptoTrader/src/main/java/cryptoTrader/storage/Brokers.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] info = line.split(" ");
                brokers.put(info[0], Arrays.asList(info[1], info[2]));
            }

            reader.close();
            return brokers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return brokers;
    }

    private void getRows() {

    }
}
