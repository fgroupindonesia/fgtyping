/**
 * Project : FGTyping
 * File : PopupHelper.java
 * Description : Penggunaan Popup dari JOptionPane untuk lebih mudah
 */
package helpers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author asus
 */
public class PopupHelper {
    
    static JFrame rootFrame = null;
    static String jawaban;
    
    public static void setRootFrame(JFrame frm){
        rootFrame = frm;
    }
    
    public static void showInfo(String title, String pesan){
        JOptionPane.showMessageDialog(rootFrame, pesan, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showError(String title, String pesan){
        JOptionPane.showMessageDialog(rootFrame, pesan, title, JOptionPane.ERROR_MESSAGE);       
    }
    
    public static void showInput(String title, String pesan){
       jawaban = JOptionPane.showInputDialog(rootFrame, pesan, title);               
    }
    
    public static String getInput(){
        return jawaban;
    }
    
    public static boolean isInputBlank(){
        return jawaban.isBlank();
    }
    
}
