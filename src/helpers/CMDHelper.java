/**
 * Project : FGTyping
 * File : CMDHelper.java
 * Description : Penggunaan eksekusi Command Prompt
 */
package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author FGroupIndonesia
 */
public class CMDHelper {

    private static ArrayList<String> data = new ArrayList<String>();

    public static ArrayList<String> getData() {
        return data;
    }

    public static String getDataAsString() {
        StringBuffer stb = new StringBuffer();

        for (String s : data) {
            stb.append(s.trim() + ";");
        }

        return stb.toString();
    }

    static int seconds = 3 * 1000;

    public static void killTyperShark() {
        try {
            execute("taskkill.exe /F /IM WinTS.exe", false);
            Thread.sleep(3000);
        } catch (Exception ex) {

        }
    }

    public static void executeTyperShark() {

        // call the taskkil first then execute the typershark from the Program Data
        // command used :
        // taskkill /F /IM iexplore.exe
        // ProgramData
        killTyperShark();

        System.out.println("Found typer shark at  " + PathHelper.getTyperSharkPath());
        execute(PathHelper.getTyperSharkPath(), false);

    }

    private static void execute(String cmdCommand, boolean needData) {

        System.out.println("Executing " + cmdCommand);

        Runtime runtime = Runtime.getRuntime();
        try {
            String line = null;
            Process proc = runtime.exec(cmdCommand);
            //proc.waitFor();
            if (needData) {
                BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                while ((line = input.readLine()) != null) {
                    //System.out.println(line); //<-- Parse data here.
                    if (line.length() > 0) {
                        if (!data.contains(line)) {
                            data.add(line);
                        }
                    }
                }
                input.close();
            }
        } catch (Exception ex) {
            System.err.println("Error at CMDHelper.");
            System.err.print(ex.getMessage());
        }
    }

}
