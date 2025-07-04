package tampilan;

import java.sql.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tampilan.panel_peminjaman;

public class PopUpCetak extends javax.swing.JFrame {

    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    public panel_peminjaman siswa = null;
        public PopUpCetak() {            
            initComponents();
            setLocationRelativeTo(null);
        }
            
    protected void kosong() {
    txtid.setText("");
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        bcetak = new pallete.ButtonRounded();
        bcari1 = new pallete.ButtonRounded();
        cbdata = new javax.swing.JCheckBox();
        cblaporan = new javax.swing.JCheckBox();
        txtid = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(169, 225, 144));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setText("Cetak Laporan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(jLabel1)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        bcetak.setForeground(new java.awt.Color(0, 0, 0));
        bcetak.setText("CETAK");
        bcetak.setFillClick(new java.awt.Color(143, 169, 152));
        bcetak.setFillOriginal(new java.awt.Color(169, 225, 144));
        bcetak.setFillOver(new java.awt.Color(169, 225, 144));
        bcetak.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bcetak.setPreferredSize(new java.awt.Dimension(61, 24));
        bcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcetakActionPerformed(evt);
            }
        });

        bcari1.setForeground(new java.awt.Color(0, 0, 0));
        bcari1.setText("BATAL");
        bcari1.setFillClick(new java.awt.Color(143, 169, 152));
        bcari1.setFillOriginal(new java.awt.Color(169, 225, 144));
        bcari1.setFillOver(new java.awt.Color(169, 225, 144));
        bcari1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bcari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcari1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(cbdata);
        cbdata.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        cbdata.setText("Semua Data Peminjaman");

        buttonGroup1.add(cblaporan);
        cblaporan.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        cblaporan.setText("Laporan berdasarkan ID :");

        txtid.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(bcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbdata)
                            .addComponent(cblaporan)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 183, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(cbdata)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(cblaporan)
                .addGap(18, 18, 18)
                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakActionPerformed
            try {
         HashMap<String, Object> parameter = new HashMap<>();
         String path;

         if (cbdata.isSelected()) {
             path = "./src/laporan/dataPengembalian.jasper"; // Cetak semua data
             // Tidak perlu parameter

         } else if (cblaporan.isSelected()) {
             String id = txtid.getText().trim();
             if (id.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Masukkan ID Pengembalian.");
                 return;
             }

             // Validasi ID di database sebelum cetak
             String sql = "SELECT COUNT(*) FROM pengembalian WHERE id_pengembalian = ?";
             PreparedStatement ps = conn.prepareStatement(sql);
             ps.setString(1, id);
             ResultSet rs = ps.executeQuery();
             rs.next();
             if (rs.getInt(1) == 0) {
                 JOptionPane.showMessageDialog(this, "ID Pengembalian tidak ditemukan.");
                 return;
             }

             path = "./src/laporan/BeritaAcara.jasper"; // Cetak berdasarkan ID
             parameter.put("id_pengembalian", id);

         } else {
             JOptionPane.showMessageDialog(this, "Pilih salah satu opsi cetak.");
             return;
         }

         JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);
         JasperViewer.viewReport(print, false);

     } catch (JRException | SQLException ex) {
         JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan: " + ex.getMessage());
     }
    }//GEN-LAST:event_bcetakActionPerformed

    private void bcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcari1ActionPerformed
        dispose();
        kosong();
    }//GEN-LAST:event_bcari1ActionPerformed

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
            java.util.logging.Logger.getLogger(PopUpCetak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopUpCetak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopUpCetak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpCetak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new PopUpRak().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pallete.ButtonRounded bcari1;
    private pallete.ButtonRounded bcetak;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbdata;
    private javax.swing.JCheckBox cblaporan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
}
