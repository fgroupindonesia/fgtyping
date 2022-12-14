/**
 * Project : FGTyping
 * File : StatisticDialog.java
 * Description : Penggunaan menampilkan overall data timer
 */
package frames;

import bean.Stat;
import helpers.storages.DBStorage;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FGroupIndonesia
 */
public class StatisticDialog extends javax.swing.JDialog {

    /**
     * Creates new form StatisticDialog
     */
    public StatisticDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        readData();
    }

    DBStorage db;

    private void readData() {
        db = new DBStorage();
        db.readAll();
        
        DefaultTableModel dfm = (DefaultTableModel) tableData.getModel();
        dfm.setRowCount(0);
        rowNum = 0;

        if (db.hasData()) {
            // render into jtable

            for (Stat s : db.getAllData()) {
                Object newData[] = asData(s);
                dfm.addRow(newData);
                rowNum++;
            }
        }

    }

    int rowNum;
    private Object[] asData(Stat in) {
        Object[] n = {rowNum, in.getName(), in.getStartTime(), in.getEndTime(), in.getDuration()};
        return n;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Overall Data");

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Project Name", "Starting Time", "Ending Time", "Duration"
            }
        ));
        jScrollPane1.setViewportView(tableData);
        if (tableData.getColumnModel().getColumnCount() > 0) {
            tableData.getColumnModel().getColumn(0).setMinWidth(30);
            tableData.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableData.getColumnModel().getColumn(0).setMaxWidth(30);
            tableData.getColumnModel().getColumn(2).setMinWidth(200);
            tableData.getColumnModel().getColumn(2).setPreferredWidth(200);
            tableData.getColumnModel().getColumn(2).setMaxWidth(200);
            tableData.getColumnModel().getColumn(3).setMinWidth(200);
            tableData.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableData.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(StatisticDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StatisticDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StatisticDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StatisticDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StatisticDialog dialog = new StatisticDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableData;
    // End of variables declaration//GEN-END:variables
}
