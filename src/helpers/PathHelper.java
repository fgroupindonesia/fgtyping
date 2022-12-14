/**
 * Project : FGTyping
 * File : PathHelper.java
 * Description : Penggunaan mempermudah lokasi file dalam ProgramData
 */

package helpers;

import java.io.File;

/**
 *
 * @author FGroupIndonesia
 */

public  class PathHelper {

    static String companyName = "fgroupindonesia";
    static String appName = "FGTyping";
    static String lokasi = System.getenv("APPDATA") + File.separator
            + companyName + File.separator + appName;

    static String app3rdParty = "TyperShark";
    static String fileWaiting = "logo_waiting.png";
    static String fileStarted = "logo_startedx.gif";
    static String fileTyper = "WinTS.exe";
    static String fileDB = "stats.db";
    static String fileUserDB = "users.db";

    static String lokasiDB = lokasi + File.separator + fileDB;
    static String lokasiIconTarget = lokasi + File.separator + fileStarted;
    static String lokasiUserDB = lokasi + File.separator + fileUserDB;
    static String lokasiTyperTarget = null;
    static String lokasiTyperTargetPortable = lokasi + File.separator + app3rdParty + File.separator + fileTyper;
    static String lokasiTyperTargetx86 = "C:\\Program Files (x86)\\Typer Shark";
    static String lokasiTyperTargetNormal = "C:\\Program Files\\Typer Shark";
    
    public static int MODE_PORTABLE = 1, MODE_X86 = 2, MODE_NORMAL = 3;
    
    public static String getUserDBPath(){
        return lokasiUserDB;
    }
    
    public static String getDBPath(){
        return lokasiDB;
    }
    
    public static String getAppIconPath(){
        return lokasiIconTarget;
    }
    
    public static String getTyperSharkPath(int mode){
        if(mode == MODE_PORTABLE){
            // the portable version
            return lokasiTyperTargetPortable ;
        }else if(mode == MODE_X86){
            // the x86 folder from x64 machine
            return lokasiTyperTargetx86 + File.separator + fileTyper;
        }else if(mode == MODE_NORMAL){
            // the 32bit without x64
            return lokasiTyperTargetNormal + File.separator + fileTyper;
        }
        
        return null;
    }
    
    public static String getTyperSharkPath(){
        return lokasiTyperTarget;
    }
    
    public static void setTyperSharkPath(String val){
        lokasiTyperTarget = val;
    }
    
}
