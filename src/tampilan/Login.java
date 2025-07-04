package tampilan;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.sql.*;
import javax.swing.*;
import javax.swing.UIManager;
import koneksi.koneksi;
/**
 *
 * @author angge
 */
public class Login extends javax.swing.JFrame {
    private Connection conn = new koneksi().connect();
    boolean showPassword = false;
    public static String loggedInUsername;
    int xx,xy;
    public Login() {
        try{
          UIManager.setLookAndFeel(new FlatLightLaf());
          UIManager.put( "Component.arc", 20 );
        } catch (Exception e) {
        e.printStackTrace();
        }
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    protected void kosong() {
        txtuser.setText("");
        txtpass.setText("");
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        blogin = new javax.swing.JButton();
        bdaftar = new javax.swing.JButton();
        btntogglepass = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        buttonRounded1 = new pallete.ButtonRounded();
        txtuser = new pallete.JTextfield();
        txtpass = new pallete.JPassField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1060, 942));

        jPanel1.setBackground(new java.awt.Color(169, 225, 144));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Gambar Login.png"))); // NOI18N
        jLabel8.setText("jLabel8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(201, 201, 201))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        blogin.setBackground(new java.awt.Color(169, 225, 144));
        blogin.setFont(new java.awt.Font("Swis721 BT", 1, 18)); // NOI18N
        blogin.setForeground(new java.awt.Color(255, 255, 255));
        blogin.setText("Login");
        blogin.setBorder(null);
        blogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        blogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bloginActionPerformed(evt);
            }
        });

        bdaftar.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        bdaftar.setForeground(new java.awt.Color(102, 204, 0));
        bdaftar.setText("daftar di sini");
        bdaftar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bdaftar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bdaftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdaftarActionPerformed(evt);
            }
        });

        btntogglepass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/eye.png"))); // NOI18N
        btntogglepass.setBorder(null);
        btntogglepass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntogglepassActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel5.setText("Halo, Pustakawan!");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("SansSerif", 2, 28)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Selamat Datang");

        jLabel7.setText("Belum punya akun?");

        buttonRounded1.setBackground(new java.awt.Color(255, 255, 255));
        buttonRounded1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cross-small.png"))); // NOI18N
        buttonRounded1.setFillClick(new java.awt.Color(204, 51, 0));
        buttonRounded1.setFillOriginal(new java.awt.Color(255, 255, 255));
        buttonRounded1.setFillOver(new java.awt.Color(255, 51, 0));
        buttonRounded1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRounded1ActionPerformed(evt);
            }
        });

        txtuser.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        txtuser.setLabelOffsetY(-10);
        txtuser.setLabelText("Username");

        txtpass.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        txtpass.setLabelOffsetY(-10);
        txtpass.setLabelText("Password");
        txtpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtpass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blogin, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntogglepass, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bdaftar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(buttonRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntogglepass, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(blogin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bdaftar)
                    .addComponent(jLabel7))
                .addContainerGap(220, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1066, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bdaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdaftarActionPerformed
       daftarAkun a = new daftarAkun();
       a.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_bdaftarActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void btntogglepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntogglepassActionPerformed
       if (showPassword) {
           txtpass.setEchoChar('*');
           showPassword = false;
       }else {
           txtpass.setEchoChar((char)0);
           showPassword = true;
       }
    }//GEN-LAST:event_btntogglepassActionPerformed

    private void bloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bloginActionPerformed
         try {
    String username = txtuser.getText();
    String password = new String(txtpass.getPassword());
    loggedInUsername = txtuser.getText();

    String sql = "SELECT * FROM akun WHERE username = ? AND password = ?";
    PreparedStatement pst = conn.prepareStatement(sql);
    pst.setString(1, username);
    pst.setString(2, password);
    ResultSet hasil = pst.executeQuery();

    if (hasil.next()) {
        String level = hasil.getString("level");

        if (level.equalsIgnoreCase("admin")) {
            JOptionPane.showMessageDialog(null, "Login berhasil, sebagai ADMIN");
            this.dispose();
            new DashboardAdmin().setVisible(true); // Ganti dengan form admin
        } else if (level.equalsIgnoreCase("user")) {
            JOptionPane.showMessageDialog(null, "Login berhasil!!!");
            this.dispose();
            new DashboardUser().setVisible(true); // Ganti dengan form user
        } else {
            JOptionPane.showMessageDialog(null, "Level tidak dikenali!");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Username atau Password salah!");
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Gagal Login: " + e.getMessage());
}

    }//GEN-LAST:event_bloginActionPerformed

    private void buttonRounded1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRounded1ActionPerformed
        dispose();
    }//GEN-LAST:event_buttonRounded1ActionPerformed

    private void txtpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpassActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
    UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
    // Tambahkan style khusus JTable (opsional)
    UIManager.put("Table.showHorizontalLines", true);
    UIManager.put("Table.showVerticalLines", true);
    UIManager.put("Table.selectionBackground", new Color(169,225,144));
    UIManager.put("Table.selectionForeground", Color.WHITE);
    UIManager.put("Table.background", Color.WHITE);
    UIManager.put("Table.alternateRowColor", Color.DARK_GRAY);
    UIManager.put( "Component.arc", 0 );
    UIManager.put( "TextComponent.arc",0 );
} catch (Exception e) {
    e.printStackTrace();
}
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bdaftar;
    private javax.swing.JButton blogin;
    private javax.swing.JButton btntogglepass;
    private pallete.ButtonRounded buttonRounded1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private pallete.JPassField txtpass;
    private pallete.JTextfield txtuser;
    // End of variables declaration//GEN-END:variables
}
