package tampilan;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.Timer;
import java.awt.Dimension;
import java.sql.*;
import koneksi.koneksi;
import javax.swing.*;



public class DashboardUser extends javax.swing.JFrame {
private Connection conn = new koneksi().connect();

    public DashboardUser() {
        initComponents();
        setLocationRelativeTo(null);
        setJam();
        setTanggal();
        nama();
        
    }
    private void setJam() {
    Timer timer = new Timer(1000, (e) -> {
        Date now = new Date();
        SimpleDateFormat jamFormat = new SimpleDateFormat("HH:mm:ss");
        LabelJam.setText(jamFormat.format(now));
    });
    timer.start();
}
    private void setTanggal() {
    Locale locale = new Locale("id", "ID"); // Bahasa Indonesia
    SimpleDateFormat formatTanggal = new SimpleDateFormat("EEEE, dd MMMM yyyy", locale);
    LabelHari.setText(formatTanggal.format(new Date()));
}
    protected void nama() {
    try {
        String sql = "SELECT nama FROM akun WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, Login.loggedInUsername); // ambil dari static var

        ResultSet hasil = ps.executeQuery();
        if (hasil.next()) {
            LabelLevel.setText(hasil.getString("nama"));
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e.getMessage());
    }
}


    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_kiri = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        peminjaman = new javax.swing.JPanel();
        jLine = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        bpinjam = new javax.swing.JLabel();
        pengembalian = new javax.swing.JPanel();
        kemLine = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        bblkn = new javax.swing.JLabel();
        LabelLevel = new javax.swing.JLabel();
        LabelJam = new javax.swing.JLabel();
        LabelHari = new javax.swing.JLabel();
        dashboard = new javax.swing.JPanel();
        dLine = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bdashboard = new javax.swing.JLabel();
        panel_kanan = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        buttonRounded1 = new pallete.ButtonRounded();
        pn_dasar = new javax.swing.JPanel();
        panel_utama = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panel_kiri.setBackground(new java.awt.Color(255, 255, 255));
        panel_kiri.setPreferredSize(new java.awt.Dimension(350, 1080));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(67, 67, 67));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Perpustakaan");

        jLabel9.setBackground(new java.awt.Color(102, 102, 102));
        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 27)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(67, 67, 67));
        jLabel9.setText("Transaksi");

        peminjaman.setBackground(new java.awt.Color(255, 255, 255));

        jLine.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jLineLayout = new javax.swing.GroupLayout(jLine);
        jLine.setLayout(jLineLayout);
        jLineLayout.setHorizontalGroup(
            jLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        jLineLayout.setVerticalGroup(
            jLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pinjam 35.png"))); // NOI18N

        bpinjam.setBackground(new java.awt.Color(102, 102, 102));
        bpinjam.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bpinjam.setForeground(new java.awt.Color(102, 102, 102));
        bpinjam.setText("Peminjaman");
        bpinjam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bpinjamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bpinjamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bpinjamMouseExited(evt);
            }
        });

        javax.swing.GroupLayout peminjamanLayout = new javax.swing.GroupLayout(peminjaman);
        peminjaman.setLayout(peminjamanLayout);
        peminjamanLayout.setHorizontalGroup(
            peminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peminjamanLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        peminjamanLayout.setVerticalGroup(
            peminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peminjamanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(peminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10)
                    .addComponent(bpinjam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pengembalian.setBackground(new java.awt.Color(255, 255, 255));

        kemLine.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout kemLineLayout = new javax.swing.GroupLayout(kemLine);
        kemLine.setLayout(kemLineLayout);
        kemLineLayout.setHorizontalGroup(
            kemLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        kemLineLayout.setVerticalGroup(
            kemLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/kembali 35.png"))); // NOI18N

        bblkn.setBackground(new java.awt.Color(102, 102, 102));
        bblkn.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bblkn.setForeground(new java.awt.Color(102, 102, 102));
        bblkn.setText("Pengembalian");
        bblkn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bblknMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bblknMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bblknMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pengembalianLayout = new javax.swing.GroupLayout(pengembalian);
        pengembalian.setLayout(pengembalianLayout);
        pengembalianLayout.setHorizontalGroup(
            pengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pengembalianLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(kemLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bblkn, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pengembalianLayout.setVerticalGroup(
            pengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pengembalianLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kemLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addComponent(bblkn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        LabelLevel.setBackground(new java.awt.Color(102, 102, 102));
        LabelLevel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        LabelLevel.setForeground(new java.awt.Color(102, 102, 102));
        LabelLevel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelLevel.setText("LabelLevel");
        LabelLevel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelLevelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LabelLevelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LabelLevelMouseExited(evt);
            }
        });

        LabelJam.setBackground(new java.awt.Color(102, 102, 102));
        LabelJam.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        LabelJam.setForeground(new java.awt.Color(102, 102, 102));
        LabelJam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelJam.setText("LabelJam");
        LabelJam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelJamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LabelJamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LabelJamMouseExited(evt);
            }
        });

        LabelHari.setBackground(new java.awt.Color(102, 102, 102));
        LabelHari.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        LabelHari.setForeground(new java.awt.Color(102, 102, 102));
        LabelHari.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelHari.setText("LabelHari");
        LabelHari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelHariMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LabelHariMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LabelHariMouseExited(evt);
            }
        });

        dashboard.setBackground(new java.awt.Color(255, 255, 255));

        dLine.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout dLineLayout = new javax.swing.GroupLayout(dLine);
        dLine.setLayout(dLineLayout);
        dLineLayout.setHorizontalGroup(
            dLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        dLineLayout.setVerticalGroup(
            dLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dashboard 35.png"))); // NOI18N

        bdashboard.setBackground(new java.awt.Color(102, 102, 102));
        bdashboard.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bdashboard.setForeground(new java.awt.Color(102, 102, 102));
        bdashboard.setText("Dashboard");
        bdashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bdashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bdashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bdashboardMouseExited(evt);
            }
        });

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(dLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bdashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(bdashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_kiriLayout = new javax.swing.GroupLayout(panel_kiri);
        panel_kiri.setLayout(panel_kiriLayout);
        panel_kiriLayout.setHorizontalGroup(
            panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kiriLayout.createSequentialGroup()
                .addGroup(panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_kiriLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(peminjaman, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pengembalian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel_kiriLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_kiriLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(panel_kiriLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelHari, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelJam, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_kiriLayout.setVerticalGroup(
            panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_kiriLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(peminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191)
                .addComponent(LabelLevel, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelJam, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(LabelHari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(162, 162, 162))
        );

        getContentPane().add(panel_kiri, java.awt.BorderLayout.LINE_START);

        panel_kanan.setBackground(new java.awt.Color(255, 255, 255));
        panel_kanan.setLayout(new java.awt.BorderLayout());

        header.setBackground(new java.awt.Color(169, 225, 144));

        buttonRounded1.setForeground(new java.awt.Color(51, 51, 51));
        buttonRounded1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logout hitam 40px.png"))); // NOI18N
        buttonRounded1.setText("LogOut");
        buttonRounded1.setFillClick(new java.awt.Color(204, 0, 51));
        buttonRounded1.setFillOriginal(new java.awt.Color(169, 225, 144));
        buttonRounded1.setFillOver(new java.awt.Color(169, 225, 144));
        buttonRounded1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        buttonRounded1.setRoundedCorner(0);
        buttonRounded1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRounded1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap(1401, Short.MAX_VALUE)
                .addComponent(buttonRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(buttonRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        panel_kanan.add(header, java.awt.BorderLayout.PAGE_START);

        pn_dasar.setBackground(new java.awt.Color(246, 255, 248));

        panel_utama.setBackground(new java.awt.Color(255, 255, 255));
        panel_utama.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bg panel utama.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 1215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(276, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        panel_utama.add(jPanel1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout pn_dasarLayout = new javax.swing.GroupLayout(pn_dasar);
        pn_dasar.setLayout(pn_dasarLayout);
        pn_dasarLayout.setHorizontalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dasarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panel_utama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        pn_dasarLayout.setVerticalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dasarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panel_utama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        panel_kanan.add(pn_dasar, java.awt.BorderLayout.CENTER);

        getContentPane().add(panel_kanan, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRounded1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRounded1ActionPerformed
        Login a = new Login();
        a.setVisible(true);
        dispose();
    }//GEN-LAST:event_buttonRounded1ActionPerformed

    private void bpinjamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpinjamMouseClicked
        peminjaman.setBackground(new Color(169,225,144));
        jLine.setBackground(new Color(0,0,0));
        
        panel_utama.removeAll();
        panel_utama.add(new panel_peminjaman());
        panel_utama.repaint();
        panel_utama.revalidate();
    }//GEN-LAST:event_bpinjamMouseClicked

    private void bpinjamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpinjamMouseEntered
        peminjaman.setBackground(new Color(169,225,144));
        jLine.setBackground(new Color(0,0,0));
    }//GEN-LAST:event_bpinjamMouseEntered

    private void bpinjamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpinjamMouseExited
        peminjaman.setBackground(new Color(255,255,255));
        jLine.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_bpinjamMouseExited

    private void bblknMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bblknMouseClicked
        pengembalian.setBackground(new Color(169,225,144));
        kemLine.setBackground(new Color(0,0,0));
        
        panel_utama.removeAll();
        panel_utama.add(new panel_pengembalian());
        panel_utama.repaint();
        panel_utama.revalidate();
    }//GEN-LAST:event_bblknMouseClicked

    private void bblknMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bblknMouseEntered
        pengembalian.setBackground(new Color(169,225,144));
        kemLine.setBackground(new Color(0,0,0));
    }//GEN-LAST:event_bblknMouseEntered

    private void bblknMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bblknMouseExited
        pengembalian.setBackground(new Color(255,255,255));
        kemLine.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_bblknMouseExited

    private void LabelLevelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelLevelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelLevelMouseClicked

    private void LabelLevelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelLevelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelLevelMouseEntered

    private void LabelLevelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelLevelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelLevelMouseExited

    private void LabelJamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelJamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelJamMouseClicked

    private void LabelJamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelJamMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelJamMouseEntered

    private void LabelJamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelJamMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelJamMouseExited

    private void LabelHariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelHariMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelHariMouseClicked

    private void LabelHariMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelHariMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelHariMouseEntered

    private void LabelHariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelHariMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelHariMouseExited

    private void bdashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bdashboardMouseClicked
        dashboard.setBackground(new Color(169,225,144));
        dLine.setBackground(new Color(0,0,0));

        panel_utama.removeAll();
        panel_utama.add(new formDashboard());
        panel_utama.repaint();
        panel_utama.revalidate();
    }//GEN-LAST:event_bdashboardMouseClicked

    private void bdashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bdashboardMouseEntered
        dashboard.setBackground(new Color(169,225,144));
        dLine.setBackground(new Color(0,0,0));
    }//GEN-LAST:event_bdashboardMouseEntered

    private void bdashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bdashboardMouseExited
        dashboard.setBackground(new Color(255,255,255));
        dLine.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_bdashboardMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       try {
    UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
    // Tambahkan style khusus JTable (opsional)
    UIManager.put("Table.alternateRowColor", true); // striping
    UIManager.put("Table.showHorizontalLines", true);
    UIManager.put("Table.showVerticalLines", true);
    UIManager.put("Table.selectionBackground", new Color(169,225,144));
    UIManager.put("Table.selectionForeground", Color.BLACK);
    UIManager.put("Table.background", Color.WHITE);
    UIManager.put( "Component.arc", 0 );
    UIManager.put( "TextComponent.arc",0 );
} catch (Exception e) {
    e.printStackTrace();
}
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelHari;
    private javax.swing.JLabel LabelJam;
    private javax.swing.JLabel LabelLevel;
    private javax.swing.JLabel bblkn;
    private javax.swing.JLabel bdashboard;
    private javax.swing.JLabel bpinjam;
    private pallete.ButtonRounded buttonRounded1;
    private javax.swing.JPanel dLine;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jLine;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel kemLine;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JPanel panel_kiri;
    private javax.swing.JPanel panel_utama;
    private javax.swing.JPanel peminjaman;
    private javax.swing.JPanel pengembalian;
    private javax.swing.JPanel pn_dasar;
    // End of variables declaration//GEN-END:variables
}
