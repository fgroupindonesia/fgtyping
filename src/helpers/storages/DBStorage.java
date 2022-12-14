/**
 * Project : FGTyping
 * File : DBStorage.java
 * Description : Penyimpanan data store locally
 */
package helpers.storages;

import bean.Stat;
import helpers.PathHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author asus
 */
public class DBStorage {

    ArrayList<Stat> arrayData;

    public DBStorage() {
        arrayData = new ArrayList<Stat>();
    }

    public ArrayList<Stat> getAllData() {
        return arrayData;
    }

    public boolean hasData() {
        return arrayData.size() > 0;
    }

    private Stat parseData(String in) {
        Stat n = null;
        String dataOrder[] = null;

        // if the data is valid and not empty
        if (in != null) {
            if (in.length() > 0) {
                if (in.split(";").length > 0) {
                    dataOrder = in.split(";");
                    // 1. number
                    // 2. proj. name
                    // 3. start time
                    // 4. end time
                    // 5. duration
                    if (dataOrder != null) {
                        n = new Stat();
                        n.setNo(Integer.parseInt(dataOrder[0]));
                        n.setName((dataOrder[1]));
                        n.setStartTime((dataOrder[2]));
                        n.setEndTime((dataOrder[3]));
                        n.setDuration((dataOrder[4]));

                    }
                }
            }

        }

        return n;
    }

    private void writeAll() {
        BufferedWriter bf = null;
        try {
            File file = new File(PathHelper.getDBPath());
            bf = new BufferedWriter(new FileWriter(file));
            for (Stat s : arrayData) {
                bf.write(s.toString());
                bf.newLine();
            }

            bf.close();
        } catch (Exception ex) {
            System.err.println("Error while writing all data DBStorage!");
        }
    }

    public void readAll() {
        BufferedReader br = null;
        try {
            File f = new File(PathHelper.getDBPath());
            if (!f.exists()) {
                // we create first the file
                f.createNewFile();
            }

            br = new BufferedReader(new FileReader(f));

            String st;

            while ((st = br.readLine()) != null) {
                Stat ob = parseData(st);
                if (ob != null) {
                    arrayData.add(ob);
                }
            }

            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error while reading data of FGTyping!");
        }

    }

    public void addData(Stat in) {

        if (!arrayData.contains(in)) {
            // then do the adding
            arrayData.add(in);
        }

        // flushing to the file
        writeAll();
    }

}
