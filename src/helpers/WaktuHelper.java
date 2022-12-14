/**
 * Project : FGTyping
 * File : Waktu.java
 * Description : Penggunaan logic perdetik untuk mengupdate Jlabel dari JFrame
 */
package helpers;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 * @author FGroupIndonesia
 */
public class WaktuHelper extends TimerTask {

    Instant before;
    Instant current;
    Duration duration;

    public WaktuHelper(JLabel jlb, Instant ins) {
        this.setLabel(jlb);
        before = ins;

    }

    private JLabel jlabel;

    public void setLabel(JLabel jlb) {
        jlabel = jlb;
    }

    public void run() {
        System.out.println("Time's started!");
        updateDetik();
    }

    int s, m, h;

    public String asTime(){
        return twoDigit(h) + ":" + twoDigit(m) + ":" + twoDigit(s);
    }
    
    public String twoDigit(int n){
        if(n<10){
            return "0" + n;
        }
        return String.valueOf(n);
        
    }
    
    private void updateDetik() {
        System.out.println("staring... ");
        current = Instant.now();
        duration = Duration.between(before, current);
        s = (duration.toSecondsPart());
        h = (duration.toHoursPart());
        m = (duration.toMinutesPart());
        jlabel.setText(asTime());
    }
}
