package tampilan;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import koneksi.koneksi;
import tampilan.panel_pengembalian;

public class PopUpDetailPinjam extends javax.swing.JFrame {

    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    public panel_pengembalian pinjam = null;
        public PopUpDetailPinjam() {            
            initComponents();
            setLocationRelativeTo(null);
            datatable();

            // Listener cari
            txtcari.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    datatable();
                }

                public void removeUpdate(DocumentEvent e) {
                    datatable();
                }

                public void changedUpdate(DocumentEvent e) {
                    datatable();
                }
            });
}

   protected void datatable() {
    Object[] Baris = {
        "ID Peminjaman", "ID Siswa", "Nama Siswa", "ID Buku", "Judul Buku", "Jumlah Pinjam"
    };
    tabmode = new DefaultTableModel(null, Baris);
    tblpopup.setModel(tabmode);

    String sql = "SELECT d.id_peminjaman, d.id_siswa, d.nmsiswa, d.id_buku, d.judul_buku, d.jumlah " +
             "FROM peminjaman d " +
             "JOIN detailpinjam p ON d.id_peminjaman = p.id_peminjaman " +
             "WHERE (d.id_peminjaman LIKE ? OR d.nmsiswa LIKE ?) " +
             "AND p.status = 'RENT'";

    try (PreparedStatement stat = conn.prepareStatement(sql)) {
        String keyword = "%" + txtcari.getText() + "%";
        stat.setString(1, keyword);
        stat.setString(2, keyword);

        try (ResultSet hasil = stat.executeQuery()) {
            while (hasil.next()) {
                String[] data = {
                    hasil.getString("id_peminjaman"),
                    hasil.getString("id_siswa"),
                    hasil.getString("nmsiswa"),
                    hasil.getString("id_buku"),
                    hasil.getString("judul_buku"),
                    hasil.getString("jumlah")
                };
                tabmode.addRow(data);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e.getMessage());
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtcari = new javax.swing.JTextField();
        bcari = new pallete.ButtonRounded();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpopup = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(169, 225, 144));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setText("Data Peminjaman");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(jLabel1)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        bcari.setForeground(new java.awt.Color(0, 0, 0));
        bcari.setText("CARI");
        bcari.setFillClick(new java.awt.Color(143, 169, 152));
        bcari.setFillOriginal(new java.awt.Color(169, 225, 144));
        bcari.setFillOver(new java.awt.Color(169, 225, 144));
        bcari.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bcari.setPreferredSize(new java.awt.Dimension(61, 24));
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        tblpopup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblpopup.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tblpopupComponentAdded(evt);
            }
        });
        tblpopup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpopupMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpopup);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        datatable();
    }//GEN-LAST:event_bcariActionPerformed

    private void tblpopupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpopupMouseClicked
        int tabelPinjam = tblpopup.getSelectedRow(); 
        pinjam.idpinjam = tblpopup.getValueAt   (tabelPinjam, 0).toString(); 
        pinjam.idsiswa = tblpopup.getValueAt   (tabelPinjam, 1).toString(); 
        pinjam.nama = tblpopup.getValueAt   (tabelPinjam, 2).toString(); 
        pinjam.kdbuku = tblpopup.getValueAt   (tabelPinjam, 3).toString(); 
        pinjam.jdlbuku = tblpopup.getValueAt   (tabelPinjam, 4).toString(); 
        pinjam.jpinjam = tblpopup.getValueAt   (tabelPinjam, 5).toString(); 
         
       
        pinjam.itemTerpilihPinjam(); 
        this.dispose();
    }//GEN-LAST:event_tblpopupMouseClicked

    private void tblpopupComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tblpopupComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblpopupComponentAdded

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
            java.util.logging.Logger.getLogger(PopUpDetailPinjam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopUpDetailPinjam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopUpDetailPinjam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpDetailPinjam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private pallete.ButtonRounded bcari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblpopup;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
