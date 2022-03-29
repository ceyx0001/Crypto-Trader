package cryptoTrader.utils.LoginServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserActions {
    private String name;
    private String pass;

    public UserActions(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public boolean authenticate() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("cryptoTrader/src/main/data/Users.txt"));
            String line = reader.readLine();

            if (line == null) {
                reader.close();
                return false;
            }

            String details = name + " " + pass;
            while (line != null && !line.equals(details)) {
                line = reader.readLine();
                if (line == null) {
                    reader.close();
                    return false;
                }
            }
            reader.close();
            if (line.equals(details)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean register() {
        System.out.println(name + " " + pass);
        try {
            File f = new File("cryptoTrader/src/main/data/Users.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (!authenticate()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
                writer.write(name + " " + pass);
                writer.newLine();
                writer.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
