package tampilan;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.*;
import java.awt.Font;
import koneksi.koneksi;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class formDashboard extends javax.swing.JPanel {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;

    

        public formDashboard() {
        initComponents();
        tampilkanTotal();
        tampilkanTotalBuku();
        tampilkanTotalPinjam();
        tampilkanTotalKembali();
        setupTable();
        datatable();
        }
        private void setupTable() {
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(30);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 34));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        // Isi tabel rata tengah
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        // Terapkan ke semua kolom
        int columnCount = table.getColumnModel().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        }
        
        private void tampilkanTotal() {
        try {
        String sql = "SELECT COUNT(*) AS total FROM siswa";
        Statement stat = conn.createStatement();
        ResultSet hasil = stat.executeQuery(sql);

        if (hasil.next()) {
            int total = hasil.getInt("total");
            jLabel3.setText(String.valueOf(total));
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal mengambil data: " + e.getMessage());
    }
    }
        private void tampilkanTotalBuku() {
        try {
        String sql = "SELECT COUNT(*) AS total FROM buku";
        Statement stat = conn.createStatement();
        ResultSet hasil = stat.executeQuery(sql);

        if (hasil.next()) {
            int total = hasil.getInt("total");
            jLabel6.setText(String.valueOf(total));
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal mengambil data: " + e.getMessage());
    }
    }
        private void tampilkanTotalPinjam() {
        try {
        String sql = "SELECT COUNT(*) AS total FROM peminjaman";
        Statement stat = conn.createStatement();
        ResultSet hasil = stat.executeQuery(sql);

        if (hasil.next()) {
            int total = hasil.getInt("total");
            jLabel9.setText(String.valueOf(total));
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal mengambil data: " + e.getMessage());
    }
    }
        private void tampilkanTotalKembali() {
        try {
        String sql = "SELECT COUNT(*) AS total FROM pengembalian";
        Statement stat = conn.createStatement();
        ResultSet hasil = stat.executeQuery(sql);

        if (hasil.next()) {
            int total = hasil.getInt("total");
            jLabel12.setText(String.valueOf(total));
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal mengambil data: " + e.getMessage());
    }
    }
        protected void datatable() {
    Object[] Baris = {
        "Kode Buku", "Judul Buku", "Pengarang", "Penerbit",
        "Kategori", "Tahun Terbit", "Stok", "Rak", "Lokasi"
    };
    tabmode = new DefaultTableModel(null, Baris);
    table.setModel(tabmode);

    String sql = """
        SELECT b.kode_buku, b.judul_buku, b.pengarang, b.penerbit, b.kategori,
               b.tahun_terbit, b.stok, r.nama_rak, r.lokasi
        FROM buku b
        JOIN rak r ON b.kode_rak = r.kode_rak
    """;

    try (PreparedStatement stat = conn.prepareStatement(sql);
         ResultSet hasil = stat.executeQuery()) {

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
        setupTable();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e.getMessage());
    }
}

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cardPengunjung = new pallete.PanelRounded();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cardPengunjung1 = new pallete.PanelRounded();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cardPengunjung2 = new pallete.PanelRounded();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cardPengunjung3 = new pallete.PanelRounded();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1528, 942));

        cardPengunjung.setBackground(new java.awt.Color(255, 255, 255));
        cardPengunjung.setFillColor(new java.awt.Color(169, 225, 144));
        cardPengunjung.setRoundedCorner(50);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pengunjung 150.png"))); // NOI18N

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("SISWA");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 50)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("0");

        javax.swing.GroupLayout cardPengunjungLayout = new javax.swing.GroupLayout(cardPengunjung);
        cardPengunjung.setLayout(cardPengunjungLayout);
        cardPengunjungLayout.setHorizontalGroup(
            cardPengunjungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardPengunjungLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(54, 54, 54))
            .addGroup(cardPengunjungLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(cardPengunjungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardPengunjungLayout.setVerticalGroup(
            cardPengunjungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPengunjungLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        cardPengunjung1.setBackground(new java.awt.Color(255, 255, 255));
        cardPengunjung1.setFillColor(new java.awt.Color(169, 225, 144));
        cardPengunjung1.setPreferredSize(new java.awt.Dimension(260, 222));
        cardPengunjung1.setRoundedCorner(50);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buku 120.png"))); // NOI18N

        jLabel5.setBackground(new java.awt.Color(153, 153, 153));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("BUKU");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 50)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("0");

        javax.swing.GroupLayout cardPengunjung1Layout = new javax.swing.GroupLayout(cardPengunjung1);
        cardPengunjung1.setLayout(cardPengunjung1Layout);
        cardPengunjung1Layout.setHorizontalGroup(
            cardPengunjung1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardPengunjung1Layout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addGroup(cardPengunjung1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardPengunjung1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardPengunjung1Layout.createSequentialGroup()
                        .addGroup(cardPengunjung1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(100, 100, 100))))
        );
        cardPengunjung1Layout.setVerticalGroup(
            cardPengunjung1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPengunjung1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        cardPengunjung2.setBackground(new java.awt.Color(255, 255, 255));
        cardPengunjung2.setFillColor(new java.awt.Color(169, 225, 144));
        cardPengunjung2.setPreferredSize(new java.awt.Dimension(260, 222));
        cardPengunjung2.setRoundedCorner(50);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/peminjaman 120.png"))); // NOI18N

        jLabel8.setBackground(new java.awt.Color(153, 153, 153));
        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("PEMINJAMAN");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 50)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("0");

        javax.swing.GroupLayout cardPengunjung2Layout = new javax.swing.GroupLayout(cardPengunjung2);
        cardPengunjung2.setLayout(cardPengunjung2Layout);
        cardPengunjung2Layout.setHorizontalGroup(
            cardPengunjung2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardPengunjung2Layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addGroup(cardPengunjung2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(64, 64, 64))
            .addGroup(cardPengunjung2Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardPengunjung2Layout.setVerticalGroup(
            cardPengunjung2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPengunjung2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        cardPengunjung3.setBackground(new java.awt.Color(255, 255, 255));
        cardPengunjung3.setFillColor(new java.awt.Color(169, 225, 144));
        cardPengunjung3.setPreferredSize(new java.awt.Dimension(260, 222));
        cardPengunjung3.setRoundedCorner(50);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pengembalian 120.png"))); // NOI18N

        jLabel11.setBackground(new java.awt.Color(153, 153, 153));
        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("PENGEMBALIAN");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 50)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("0");

        javax.swing.GroupLayout cardPengunjung3Layout = new javax.swing.GroupLayout(cardPengunjung3);
        cardPengunjung3.setLayout(cardPengunjung3Layout);
        cardPengunjung3Layout.setHorizontalGroup(
            cardPengunjung3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPengunjung3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(69, 69, 69))
            .addGroup(cardPengunjung3Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel11)
                .addGap(0, 53, Short.MAX_VALUE))
            .addGroup(cardPengunjung3Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardPengunjung3Layout.setVerticalGroup(
            cardPengunjung3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPengunjung3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        table.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table);

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel13.setText("Buku yang tersedia");

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel14.setText("Dashboard");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dashboard.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cardPengunjung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(cardPengunjung1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(cardPengunjung2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(cardPengunjung3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)))
                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel14)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cardPengunjung3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardPengunjung2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardPengunjung1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cardPengunjung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pallete.PanelRounded cardPengunjung;
    private pallete.PanelRounded cardPengunjung1;
    private pallete.PanelRounded cardPengunjung2;
    private pallete.PanelRounded cardPengunjung3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
