/**
 * Project : FGTyping
 * File : TextHelper.java
 * Description : Penggunaan efek beragam untuk JLabel
 */
package helpers;

import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author FGroupIndonesia
 */
public class TextHelper {

    static JLabel label;
    static Font f ;

    private static void applyFont(){
       f  = label.getFont();
    }
    
    public static void setJLabel (JLabel in){
        label = in;
        applyFont();
    }
    
    public static void asBold() {
        // bold
        label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    }

    public static void asNormal() {
        label.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
    }

}
