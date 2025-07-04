package tampilan;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import koneksi.koneksi;
import tampilan.panel_peminjaman;

public class PopUpBuku extends javax.swing.JFrame {

    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    public panel_peminjaman buku = null;
        public PopUpBuku() {            
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
            "Kode Buku", "Judul Buku", "Pengarang", "Penerbit",
            "Kategori", "Tahun Terbit", "Stok", "Rak", "Lokasi"
        };
        tabmode = new DefaultTableModel(null, Baris);
        tblpopup.setModel(tabmode);

        String sql = """
        SELECT b.kode_buku, b.judul_buku, b.pengarang, b.penerbit, b.kategori,
               b.tahun_terbit, b.stok, r.nama_rak, r.lokasi
        FROM buku b
        JOIN rak r ON b.kode_rak = r.kode_rak
        WHERE b.kode_buku LIKE ? OR b.judul_buku LIKE ?
        """;

        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            String keyword = "%" + txtcari.getText() + "%";
            stat.setString(1, keyword);
            stat.setString(2, keyword);

            try (ResultSet hasil = stat.executeQuery()) {
                while (hasil.next()) {
                    String[] data = {
                        hasil.getString("kode_buku"),
                        hasil.getString("judul_buku"),
                        hasil.getString("pengarang"),
                        hasil.getString("penerbit"),
                        hasil.getString("kategori"),
                        hasil.getString("tahun_terbit"),
                        hasil.getString("stok"),
                        hasil.getString("nama_rak"),
                        hasil.getString("lokasi")
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
        jLabel1.setText("Data Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(jLabel1)
                .addContainerGap(265, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addContainerGap(46, Short.MAX_VALUE))
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
        int tabelSiswa = tblpopup.getSelectedRow(); 
        buku.kdbuku = tblpopup.getValueAt   (tabelSiswa, 0).toString(); 
        buku.jdlbuku = tblpopup.getValueAt   (tabelSiswa, 1).toString(); 
        buku.rang = tblpopup.getValueAt   (tabelSiswa, 2).toString(); 
        buku.bit = tblpopup.getValueAt   (tabelSiswa, 3).toString(); 
        buku.tt = tblpopup.getValueAt   (tabelSiswa, 4).toString(); 
        buku.gori = tblpopup.getValueAt   (tabelSiswa, 5).toString(); 
        buku.stok = tblpopup.getValueAt   (tabelSiswa, 6).toString(); 
        buku.kdrak = tblpopup.getValueAt   (tabelSiswa, 7).toString(); 
        buku.lokasi = tblpopup.getValueAt   (tabelSiswa, 8).toString(); 
       
        buku.itemTerpilihBk(); 
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
            java.util.logging.Logger.getLogger(PopUpBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopUpBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopUpBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private pallete.ButtonRounded bcari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblpopup;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
