/**
 * Project : FGTyping
 * File : TimerFrame.java
 * Description : Penggunaan Timer animated yang tampil pojok kanan bawah Screen
 */
package frames;

import bean.Stat;
import bean.UserProfile;
import helpers.CMDHelper;
import helpers.storages.DBStorage;
import helpers.PathHelper;
import helpers.PopupHelper;
import helpers.TextHelper;
import helpers.storages.UserProfileStorage;
import helpers.WaktuHelper;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Menu;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author FGroupIndonesia
 */
public class TimerFrame extends javax.swing.JFrame {

    /**
     * Creates new form TimerFrame
     */
    public TimerFrame() {
        initComponents();
        createTray();
        applyBottomCorner();
        prepareData();
        labelStop.setVisible(false);
    }

    DBStorage db;

    private void prepareData() {

        db = new DBStorage();
        db.readAll();
    }

    boolean currentlyWorking = false;

    private void applyBottomCorner() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
        int y = (int) rect.getMaxY() - this.getHeight() - 40;
        this.setLocation(x, y);
    }

    Menu userProfileItem = new Menu("User Profile");
    MenuItem addNewItem = new MenuItem("Add New...");

    MenuItem timerItem = new MenuItem("Start Timer");
    MenuItem displayItem = new MenuItem("Display Timer");
    MenuItem typerSharkItem = new MenuItem("TyperShark");

    UserProfileStorage usp = new UserProfileStorage();

    private void verifyTyperShark() {
        boolean found = false;

        File f = new File(PathHelper.getTyperSharkPath(PathHelper.MODE_PORTABLE));

        // if not portable        
        if (!f.exists()) {

            // check with a different location
            // if not x64 from x86
            f = new File(PathHelper.getTyperSharkPath(PathHelper.MODE_X86));

            if (!f.exists()) {

                // check with a normal location
                // if under ProgramFiles only
                f = new File(PathHelper.getTyperSharkPath(PathHelper.MODE_NORMAL));

                if (f.exists()) {
                    found = true;
                    PathHelper.setTyperSharkPath(PathHelper.getTyperSharkPath(PathHelper.MODE_NORMAL));
                } else {
                    found = false;
                }

            } else {
                found = true;
                PathHelper.setTyperSharkPath(PathHelper.getTyperSharkPath(PathHelper.MODE_X86));
            }

        } else {
            found = true;
            PathHelper.setTyperSharkPath(PathHelper.getTyperSharkPath(PathHelper.MODE_PORTABLE));
        }

        if (!found) {
            typerSharkItem.setEnabled(false);
        }

    }

    private boolean changeUserTo(String namaPilihan) {
        boolean terganti = false;

        if (!usp.getCurrentUser().getNama().equalsIgnoreCase(namaPilihan)) {
            // show the popup
            String nomer = JOptionPane.showInputDialog(this, "Please input nomor telephone...", "");

            if (!nomer.equalsIgnoreCase(usp.getCurrentUser().getNomerTelepon())) {
                JOptionPane.showMessageDialog(this, "Input nomer telepon salah!", "Error", JOptionPane.ERROR_MESSAGE);
                terganti = false;
            } else {
                JOptionPane.showMessageDialog(this, "User Profile sukses terupdate!", "Info", JOptionPane.INFORMATION_MESSAGE);
                terganti = true;
            }
        }

        return terganti;

    }

    private void createMyAction(MenuItem menu) {
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // change the user profile accordingly
                if (changeUserTo(menu.getLabel())) {
                    usp.changeUserProfile(menu.getLabel());
                }

            }
        });
    }

    private void createDynamicUserProfileMenu() {

        MenuItem oneName;

        if (usp.hasData()) {
            for (UserProfile d : usp.getAllData()) {
                oneName = new MenuItem(d.getNama());
                createMyAction(oneName);
                userProfileItem.add(oneName);
            }

            userProfileItem.addSeparator();
            userProfileItem.add(addNewItem);
        } else {
            userProfileItem.add(addNewItem);
        }

    }

    UserProfile dataBaruUser;

    private void createTray() {

        // ensure you have the folder first
        System.out.println(PathHelper.getAppIconPath());

        try {

            File objFile = new File(PathHelper.getAppIconPath());
            ImageIcon img = new ImageIcon(objFile.getPath());
            Image imageLogo = img.getImage();

            if (!SystemTray.isSupported()) {
                System.out.println("SystemTray is not supported");
                return;
            }

            PopupMenu popup = new PopupMenu();
            TrayIcon trayIcon = new TrayIcon(imageLogo);

            displayItem.setEnabled(false);

            SystemTray tray = SystemTray.getSystemTray();

            // Create a popup menu components
            MenuItem overallItem = new MenuItem("Overall Data");
            MenuItem exitItem = new MenuItem("Exit");
            // Add components to popup menu

            popup.add(overallItem);
            popup.add(timerItem);
            popup.addSeparator();
            popup.add(displayItem);
            popup.addSeparator();
            popup.add(typerSharkItem);
            popup.addSeparator();
            // create Dynamic menu
            createDynamicUserProfileMenu();
            popup.add(userProfileItem);

            popup.addSeparator();

            popup.add(exitItem);

            // verify whether typershark is exist?
            verifyTyperShark();

            trayIcon.setPopupMenu(popup);

            tray.add(trayIcon);

            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null,
                            "This dialog box is run from System Tray");
                }
            });

            addNewItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int putaranKe = 1;

                    PopupHelper.setRootFrame(TimerFrame.this);

                    dataBaruUser = new UserProfile();

                    while (true) {
                        if (putaranKe == 1) {
                            PopupHelper.showInput("Input", "Masukkan nama lengkap anda :");
                        } else if (putaranKe == 2) {
                            PopupHelper.showInput("Input", "Masukkan nomer telepon anda :");
                        } else {
                            break;
                        }

                        if (!PopupHelper.isInputBlank()) {
                            if (putaranKe == 1) {
                                dataBaruUser.setNama(PopupHelper.getInput());
                            } else if (putaranKe == 2) {
                                dataBaruUser.setNomerTelepon(PopupHelper.getInput());
                            }
                            putaranKe++;
                        }

                    }
                }
            });

            exitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CMDHelper.killTyperShark();

                    System.exit(0);
                }
            });

            typerSharkItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CMDHelper.executeTyperShark();
                }
            });

            displayItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TimerFrame.this.setVisible(true);
                    displayItem.setEnabled(false);
                }
            });

            overallItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    StatisticDialog f = new StatisticDialog(TimerFrame.this, true);
                    f.setVisible(true);
                }
            });

            timerItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String n = timerItem.getLabel();
                    if (n.contains("Stop")) {

                        stopTimer();
                    } else {
                        // start the timer activities
                        // once he typed the activities name
                        showInput("type here...");

                    }

                }
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.err.println("TrayIcon could not be added.");
            return;
        }

    }

    Timer timer;
    int seconds = 1;
    String projectName;

    String dateNow;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
    Date objDate;

    private String getDateTimeNow() {
        objDate = new Date();
        dateNow = sdf.format(objDate);
        return dateNow;
    }

    private void startTimer() {

        this.setVisible(true);
        labelStop.setVisible(true);

        // start from bright color
        labelTime.setForeground(new java.awt.Color(255, 0, 0));

        timer = new Timer();
        Instant instant = Instant.now();
        timer.schedule(new WaktuHelper(labelTime, instant), 0, seconds * 1000);

        // local object ref
        currentData = new Stat();
        currentData.setName(labelTitle.getText());
        currentData.setStartTime(getDateTimeNow());

    }

    public void showInput(String pesan) {
        projectName = JOptionPane.showInputDialog(this, "Input your project name : ", pesan);

        boolean validName = false;

        if (projectName != null) {

            if (!projectName.equalsIgnoreCase("type here...")) {
                validName = true;
            }

        } else if (projectName.isEmpty()) {
            // if empty
            validName = false;
        }

        if (validName) {

            currentlyWorking = true;
            timerItem.setLabel("Stop Timer");

            labelTitle.setText(projectName);

            startTimer();
        } else {
            JOptionPane.showMessageDialog(this, "Not valid name!");
        }

    }

    Stat currentData;

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }

        // stop the timer activities
        timerItem.setLabel("Start Timer");

        labelTitle.setText(labelTitle.getText() + " [stopped]");

        // back to dimmed color
        labelTime.setForeground(new java.awt.Color(51, 0, 0));

        // store the recorded into data locally
        currentData.setEndTime(getDateTimeNow());
        currentData.setDuration(labelTime.getText());

        db.addData(currentData);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTime = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelStop = new javax.swing.JLabel();
        labelHide = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setType(java.awt.Window.Type.UTILITY);

        labelTime.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        labelTime.setForeground(new java.awt.Color(102, 0, 51));
        labelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTime.setText("00:00:00");
        labelTime.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        getContentPane().add(labelTime, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(4, 20));
        jPanel1.setLayout(new java.awt.BorderLayout());

        labelTitle.setText("-");
        jPanel1.add(labelTitle, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        labelStop.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frames/close.png"))); // NOI18N
        labelStop.setText("Close");
        labelStop.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 15));
        labelStop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelStopMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelStopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelStopMouseExited(evt);
            }
        });
        jPanel2.add(labelStop);

        labelHide.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelHide.setText("Hide");
        labelHide.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 15));
        labelHide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelHideMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelHideMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelHideMouseExited(evt);
            }
        });
        jPanel2.add(labelHide);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void labelHideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHideMouseClicked

        displayItem.setEnabled(true);
        this.setVisible(false);

    }//GEN-LAST:event_labelHideMouseClicked

    private void labelHideMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHideMouseEntered
        TextHelper.setJLabel(labelHide);
        TextHelper.asBold();
    }//GEN-LAST:event_labelHideMouseEntered

    private void labelHideMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelHideMouseExited
        TextHelper.setJLabel(labelHide);
        TextHelper.asNormal();
    }//GEN-LAST:event_labelHideMouseExited

    private void labelStopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelStopMouseClicked

        stopTimer();
        this.setVisible(false);
        labelStop.setVisible(false);
    }//GEN-LAST:event_labelStopMouseClicked

    private void labelStopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelStopMouseEntered
        TextHelper.setJLabel(labelStop);
        TextHelper.asBold();
    }//GEN-LAST:event_labelStopMouseEntered

    private void labelStopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelStopMouseExited
        TextHelper.setJLabel(labelHide);
        TextHelper.asNormal();
    }//GEN-LAST:event_labelStopMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TimerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelHide;
    private javax.swing.JLabel labelStop;
    private javax.swing.JLabel labelTime;
    private javax.swing.JLabel labelTitle;
    // End of variables declaration//GEN-END:variables
}
