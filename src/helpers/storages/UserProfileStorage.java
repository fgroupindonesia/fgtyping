/**
 * Project : FGTyping
 * File : UserProfileHelper.java
 * Description : Penggunaan Pembuatan Menu Profile yg digandengkan dengan nomor telepon
 */
package helpers.storages;

import bean.Stat;
import bean.UserProfile;
import helpers.PathHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 *
 * @author fgroupindonesia
 */
public class UserProfileStorage {

    ArrayList<UserProfile> data;
    UserProfile orang;

    public UserProfileStorage() {
        data = new ArrayList<UserProfile>();
    }

    public ArrayList<UserProfile> getAllData() {
        return data;
    }

    public boolean hasData() {
        return data.size() > 0;
    }

    public void changeUserProfile(String username) {
        // switch to that person
        for (UserProfile us : data) {
            if (us.getNama().equalsIgnoreCase(username)) {
                orang = us;
                break;
            }
        }
    }

    private UserProfile parseData(String in) {
        UserProfile n = null;
        String dataOrder[] = null;

        // if the data is valid and not empty
        if (in != null) {
            if (in.length() > 0) {
                if (in.split(";").length > 0) {
                    dataOrder = in.split(";");
                    // 1. nama
                    // 2. nomerhp
                    if (dataOrder != null) {
                        n = new UserProfile();
                        n.setNama(dataOrder[0]);
                        n.setNomerTelepon(dataOrder[1]);

                    }
                }
            }

        }

        return n;
    }

    private void writeAll() {
        BufferedWriter bf = null;
        try {
            File file = new File(PathHelper.getUserDBPath());
            bf = new BufferedWriter(new FileWriter(file));
            for (UserProfile s : data) {
                bf.write(s.toString());
                bf.newLine();
            }

            bf.close();
        } catch (Exception ex) {
            System.err.println("Error while writing all data UserProfileHelper!");
        }
    }

    public void readAll() {
        BufferedReader br = null;
        try {
            File f = new File(PathHelper.getUserDBPath());
            if (!f.exists()) {
                // we create first the file
                f.createNewFile();
            }

            br = new BufferedReader(new FileReader(f));

            String st;

            while ((st = br.readLine()) != null) {
                UserProfile ob = parseData(st);
                if (ob != null) {
                    data.add(ob);
                }
            }

            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error while reading data of UserProfile FGTyping!");
        }

    }

    public void addData(UserProfile in) {

        if (!data.contains(in)) {
            // then do the adding
            data.add(in);
        }

        // flushing to the file
        writeAll();
    }

    public UserProfile getCurrentUser() {
        return orang;
    }

}
