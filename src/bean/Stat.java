/**
 * Project : FGTyping
 * File : Stat.java
 * Description : Object Bean sesuai properties yang di tampilkan pada layout JTable milik 
 * StatisticDialog
 */
package bean;

import java.util.Date;

/**
 *
 * @author FGroupIndonesia
 */
public class Stat {
    private int no;
    private String name;
    private String startTime;
    private String endTime;
    private String duration;

    public void setDuration(String started, String ended){
        Date d1, d2;
        
    }
    
    public String toString(){
        StringBuffer stb = new StringBuffer();
        
        stb.append(no);
        stb.append(";");
        stb.append(name);
        stb.append(";");
        stb.append(startTime);
        stb.append(";");
        stb.append(endTime);
        stb.append(";");
        stb.append(duration);
        
        return stb.toString();
    }
    
    /**
     * @return the no
     */
    public int getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(int no) {
        this.no = no;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
