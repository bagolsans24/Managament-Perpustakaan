package tampilan;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import koneksi.koneksi;

public class PopUpRak extends javax.swing.JFrame {

    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    // Tambahkan di atas class:
        private JPanel targetPanel;

        public PopUpRak(JPanel targetPanel) {
            this.targetPanel = targetPanel;
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

    private void datatable() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Rak");
    model.addColumn("Nama Rak");
    model.addColumn("Lokasi Rak");

    try {
        String sql = "SELECT * FROM rak WHERE kode_rak LIKE ? OR nama_rak LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        String keyword = "%" + txtcari.getText() + "%";
        ps.setString(1, keyword);
        ps.setString(2, keyword);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("kode_rak"),
                rs.getString("nama_rak"),
                rs.getString("lokasi")
            });
        }
        tblrak.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal load data rak: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtcari = new javax.swing.JTextField();
        bcari = new pallete.ButtonRounded();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblrak = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(169, 225, 144));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setText("Data Rak");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/rak 70.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(373, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)))
                .addContainerGap(29, Short.MAX_VALUE))
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

        tblrak.setModel(new javax.swing.table.DefaultTableModel(
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
        tblrak.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tblrakComponentAdded(evt);
            }
        });
        tblrak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblrakMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblrak);

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

    }//GEN-LAST:event_bcariActionPerformed

    private void tblrakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblrakMouseClicked
   /*int row = tblrak.getSelectedRow();
    String id = tblrak.getValueAt(row, 0).toString();
    String nama = tblrak.getValueAt(row, 1).toString();
    String lokasi = tblrak.getValueAt(row, 2).toString();

    if (targetPanel instanceof panel_dataBuku.panel_AddBuku) {
        panel_dataBuku.PanelAddBuku p = (panel_dataBuku.PanelAddBuku) targetPanel;
        p.txtidrak.setText(id);
        p.txtnmrak.setText(nama);
        p.txtlokasi.setText(lokasi);
    } else if (targetPanel instanceof panel_dataBuku.PanelUbahBuku) {
        panel_dataBuku.PanelUbahBuku p = (panel_dataBuku.PanelUbahBuku) targetPanel;
        p.txtidrak1.setText(id);
        p.txtnmrak1.setText(nama);
        p.txtlokasi1.setText(lokasi);
    }

    this.dispose();*/
    }//GEN-LAST:event_tblrakMouseClicked

    private void tblrakComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tblrakComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblrakComponentAdded

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
            java.util.logging.Logger.getLogger(PopUpRak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopUpRak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopUpRak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpRak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblrak;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
