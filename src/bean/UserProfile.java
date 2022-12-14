/**
 * Project : FGTyping
 * File : UserProfile.java
 * Description : Bean untuk pemetaan User Object
 */
package bean;

/**
 *
 * @author asus
 */
public class UserProfile {
  
    private String nama;
    private String nomerTelepon;

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the nomerTelepon
     */
    public String getNomerTelepon() {
        return nomerTelepon;
    }

    /**
     * @param nomerTelepon the nomerTelepon to set
     */
    public void setNomerTelepon(String nomerTelepon) {
        this.nomerTelepon = nomerTelepon;
    }
    
}
