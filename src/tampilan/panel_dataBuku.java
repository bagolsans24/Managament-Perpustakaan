package tampilan;
import java.sql.*;
import javax.swing.*;
import java.awt.Font;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.*;
import java.util.HashMap;
import com.toedter.calendar.JDateChooser; // Untuk JDateChooser
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;          // Untuk exception saat parse tanggal
import java.text.SimpleDateFormat;        // Untuk format tanggal
import java.util.Date;                    // Untuk class Date
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;


public class panel_dataBuku extends javax.swing.JPanel {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;

    public panel_dataBuku() {
        initComponents();
        setupTable();
        aktif();
        kosong();
        generateIDBuku();
        getRak();
        datatable();
        setupListeners();
        addRakListener();
        txtkode.setEditable(false);
        txtkode.setFocusable(false);
        txtkode1.setEditable(false);
        txtkode1.setFocusable(false);
        txtlokasi.setEditable(false);
        txtlokasi1.setEditable(false);
        txtlokasi.setFocusable(false);
        txtlokasi1.setFocusable(false);
        ((JTextField) dctahun.getDateEditor().getUiComponent()).setEditable(false);
        ((JTextField) dctahun1.getDateEditor().getUiComponent()).setEditable(false);
    }

    private void setupTable() {
    tblbuku.setFont(new Font("SansSerif", Font.PLAIN, 14));
    tblbuku.setRowHeight(30);

    // Header styling
    JTableHeader header = tblbuku.getTableHeader();
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
    int columnCount = tblbuku.getColumnModel().getColumnCount();
    for (int i = 0; i < columnCount; i++) {
        tblbuku.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Optional: garis grid halus
    tblbuku.setShowHorizontalLines(true);
    tblbuku.setShowVerticalLines(true);
    tblbuku.setGridColor(new Color(220, 220, 220));
}

    private void setupListeners() {
        tblbuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int baris = tblbuku.getSelectedRow();
                if (baris != -1) {
                    tampilData(baris);
                }
            }
        });

        txtcari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(DocumentEvent e) { datatable(); }
            public void removeUpdate(DocumentEvent e) { datatable(); }
            public void changedUpdate(DocumentEvent e) { datatable(); }
        });
    }

    private void tampilData(int baris) {
        try {
            String kode = tblbuku.getValueAt(baris, 0).toString();
            String judul = tblbuku.getValueAt(baris, 1).toString();
            String pengarang = tblbuku.getValueAt(baris, 2).toString();
            String penerbit = tblbuku.getValueAt(baris, 3).toString();
            String kategori = tblbuku.getValueAt(baris, 4).toString();
            String tahun = tblbuku.getValueAt(baris, 5).toString();
            String stok = tblbuku.getValueAt(baris, 6).toString();
            String rak = tblbuku.getValueAt(baris, 7).toString();

            txtkode1.setText(kode);
            txtnm1.setText(judul);
            txtpengarang1.setText(pengarang);
            txtpenerbit1.setText(penerbit);
            cbkategori1.setSelectedItem(kategori);
            cbrak1.setSelectedItem(rak);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tanggal = sdf.parse(tahun);
            dctahun1.setDate(tanggal);

            sstok1.setValue(Integer.parseInt(stok));

            btambah.setText("UBAH");
            bhapus.setVisible(true);
            bbatalUbah.setVisible(true);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah: " + e.getMessage());
        }
    }

    private void generateIDBuku() {
        try {
            String sql = "SELECT MAX(RIGHT(kode_buku, 3)) AS nomor FROM buku";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String kode = "BK001";
            if (rs.next() && rs.getString("nomor") != null) {
                int nomor = Integer.parseInt(rs.getString("nomor")) + 1;
                kode = String.format("BK%03d", nomor);
            }
            txtkode.setText(kode);
            txtkode1.setText(kode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal generate ID: " + e.getMessage());
        }
    }

    private void getRak() {
        try {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Pilih Rak");
            String sql = "SELECT nama_rak FROM rak";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                model.addElement(rs.getString("nama_rak"));
            }
            cbrak.setModel(model);
            cbrak1.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load rak: " + e.getMessage());
        }
    }

    private void addRakListener() {
        cbrak.addActionListener(e -> isiLokasiDariRak((String) cbrak.getSelectedItem(), txtlokasi));
        cbrak1.addActionListener(e -> isiLokasiDariRak((String) cbrak1.getSelectedItem(), txtlokasi1));
    }

    private void isiLokasiDariRak(String namaRak, JTextField txtFieldLokasi) {
        if (namaRak != null && !namaRak.equals("Pilih Rak")) {
            try {
                String sql = "SELECT lokasi FROM rak WHERE nama_rak = ?";
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, namaRak);
                ResultSet rs = stat.executeQuery();
                if (rs.next()) {
                    txtFieldLokasi.setText(rs.getString("lokasi"));
                } else {
                    txtFieldLokasi.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal mengambil lokasi: " + ex.getMessage());
            }
        } else {
            txtFieldLokasi.setText("");
        }
    }
    private boolean isFormValid(String judul, String pengarang, String penerbit,
                                Object kategori, Object namaRak, Date tanggal) {
        if (judul.trim().isEmpty() || pengarang.trim().isEmpty() || penerbit.trim().isEmpty()) {
            return false;
        }
        if (kategori == null || kategori.toString().toLowerCase().contains("pilih")) {
            return false;
        }
        if (namaRak == null || namaRak.toString().toLowerCase().contains("pilih")) {
            return false;
        }
        if (tanggal == null) {
            return false;
        }
        return true;
    }

    private String getKodeRakByNama(String namaRak) {
        try {
            String sql = "SELECT kode_rak FROM rak WHERE nama_rak = ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, namaRak);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                return rs.getString("kode_rak");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mendapatkan kode_rak: " + e.getMessage());
        }
        return null;
    }
    protected void aktif() {
        txtkode.requestFocus();
        txtkode1.requestFocus();
        bhapus.setVisible(false);
        bbatalUbah.setVisible(false);
    }

    protected void kosong() {
    txtnm.setText("");
    txtpengarang.setText("");
    txtpenerbit.setText("");
    if (cbkategori.getItemCount() > 0) cbkategori.setSelectedIndex(0);
    if (cbrak.getItemCount() > 0) cbrak.setSelectedIndex(0);
    dctahun.setDate(null);
    sstok.setValue(0);

    txtnm1.setText("");
    txtpengarang1.setText("");
    txtpenerbit1.setText("");
    if (cbkategori1.getItemCount() > 0) cbkategori1.setSelectedIndex(0);
    if (cbrak1.getItemCount() > 0) cbrak1.setSelectedIndex(0);
    dctahun1.setDate(null);
    sstok1.setValue(0);
}


    protected void datatable() {
        Object[] Baris = {
            "Kode Buku", "Judul Buku", "Pengarang", "Penerbit",
            "Kategori", "Tahun Terbit", "Stok", "Rak", "Lokasi"
        };
        tabmode = new DefaultTableModel(null, Baris);
        tblbuku.setModel(tabmode);

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
                setupTable();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_mainBuku = new javax.swing.JPanel();
        panelViewBuku = new javax.swing.JPanel();
        panelAtasBuku = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        btambah = new pallete.ButtonRounded();
        bhapus = new pallete.ButtonRounded();
        bbatalUbah = new pallete.ButtonRounded();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        bcetak = new pallete.ButtonRounded();
        panelTableBuku = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbuku = new javax.swing.JTable();
        panelAddBuku = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bsimpan = new pallete.ButtonRounded();
        bbatal = new pallete.ButtonRounded();
        jLabel3 = new javax.swing.JLabel();
        txtkode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtnm = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtpenerbit = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbkategori = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtpengarang = new javax.swing.JTextField();
        dctahun = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        sstok = new com.toedter.components.JSpinField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtlokasi = new javax.swing.JTextField();
        cbrak = new javax.swing.JComboBox<>();
        panelUbahBuku = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        bubah = new pallete.ButtonRounded();
        bbatal3 = new pallete.ButtonRounded();
        jLabel12 = new javax.swing.JLabel();
        txtkode1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtnm1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtpenerbit1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cbkategori1 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtpengarang1 = new javax.swing.JTextField();
        dctahun1 = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        sstok1 = new com.toedter.components.JSpinField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtlokasi1 = new javax.swing.JTextField();
        cbrak1 = new javax.swing.JComboBox<>();

        setLayout(new java.awt.CardLayout());

        panel_mainBuku.setLayout(new java.awt.CardLayout());

        panelViewBuku.setBackground(new java.awt.Color(255, 255, 255));
        panelViewBuku.setLayout(new java.awt.BorderLayout());

        panelAtasBuku.setBackground(new java.awt.Color(255, 255, 255));
        panelAtasBuku.setMinimumSize(new java.awt.Dimension(250, 250));

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel22.setText("Data Buku");

        btambah.setForeground(new java.awt.Color(0, 0, 0));
        btambah.setText("TAMBAH");
        btambah.setFillClick(new java.awt.Color(143, 169, 152));
        btambah.setFillOriginal(new java.awt.Color(169, 225, 144));
        btambah.setFillOver(new java.awt.Color(169, 225, 144));
        btambah.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btambahActionPerformed(evt);
            }
        });

        bhapus.setForeground(new java.awt.Color(0, 0, 0));
        bhapus.setText("HAPUS");
        bhapus.setFillClick(new java.awt.Color(143, 169, 152));
        bhapus.setFillOriginal(new java.awt.Color(169, 225, 144));
        bhapus.setFillOver(new java.awt.Color(169, 225, 144));
        bhapus.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbatalUbah.setForeground(new java.awt.Color(0, 0, 0));
        bbatalUbah.setText("BATAL");
        bbatalUbah.setFillClick(new java.awt.Color(143, 169, 152));
        bbatalUbah.setFillOriginal(new java.awt.Color(169, 225, 144));
        bbatalUbah.setFillOver(new java.awt.Color(169, 225, 144));
        bbatalUbah.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bbatalUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalUbahActionPerformed(evt);
            }
        });

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buku 70.png"))); // NOI18N

        jLabel24.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText("Master > Data Buku");

        txtcari.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout panelAtasBukuLayout = new javax.swing.GroupLayout(panelAtasBuku);
        panelAtasBuku.setLayout(panelAtasBukuLayout);
        panelAtasBukuLayout.setHorizontalGroup(
            panelAtasBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtasBukuLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btambah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bbatalUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1009, Short.MAX_VALUE))
            .addGroup(panelAtasBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAtasBukuLayout.createSequentialGroup()
                    .addGap(54, 54, 54)
                    .addGroup(panelAtasBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelAtasBukuLayout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addGap(6, 6, 6)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelAtasBukuLayout.createSequentialGroup()
                            .addGap(903, 903, 903)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(55, Short.MAX_VALUE)))
        );
        panelAtasBukuLayout.setVerticalGroup(
            panelAtasBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtasBukuLayout.createSequentialGroup()
                .addContainerGap(178, Short.MAX_VALUE)
                .addGroup(panelAtasBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btambah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatalUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(panelAtasBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAtasBukuLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(40, 40, 40)
                    .addGroup(panelAtasBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22))
                    .addGap(35, 35, 35)
                    .addGroup(panelAtasBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAtasBukuLayout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(21, Short.MAX_VALUE)))
        );

        panelViewBuku.add(panelAtasBuku, java.awt.BorderLayout.NORTH);

        panelTableBuku.setBackground(new java.awt.Color(255, 255, 255));

        tblbuku.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblbuku);

        javax.swing.GroupLayout panelTableBukuLayout = new javax.swing.GroupLayout(panelTableBuku);
        panelTableBuku.setLayout(panelTableBukuLayout);
        panelTableBukuLayout.setHorizontalGroup(
            panelTableBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTableBukuLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        panelTableBukuLayout.setVerticalGroup(
            panelTableBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTableBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelViewBuku.add(panelTableBuku, java.awt.BorderLayout.CENTER);

        panel_mainBuku.add(panelViewBuku, "card5");

        panelAddBuku.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel2.setText("Tambahkan Buku");

        bsimpan.setForeground(new java.awt.Color(0, 0, 0));
        bsimpan.setText("SIMPAN");
        bsimpan.setFillClick(new java.awt.Color(143, 169, 152));
        bsimpan.setFillOriginal(new java.awt.Color(169, 225, 144));
        bsimpan.setFillOver(new java.awt.Color(169, 225, 144));
        bsimpan.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bbatal.setForeground(new java.awt.Color(0, 0, 0));
        bbatal.setText("BATAL");
        bbatal.setFillClick(new java.awt.Color(143, 169, 152));
        bbatal.setFillOriginal(new java.awt.Color(169, 225, 144));
        bbatal.setFillOver(new java.awt.Color(169, 225, 144));
        bbatal.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel3.setText("Kode Buku");

        txtkode.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtkode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("Judul Buku");

        txtnm.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setText("Penerbit");

        txtpenerbit.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpenerbit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setText("Pengarang");

        cbkategori.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbkategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kategori", "Karya Umum & Komputer", "Filsafat & Psikologi", "Agama", "Ilmu-ilmu Sosial", "Bahasa ", "Ilmu Pengetahuan Alam & Matematika", "Teknologi & Ilmu Terapan", "Seni, Hiburan, & Olahraga", "Sastra", "Geografi & Sejarah", " " }));
        cbkategori.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText("Stok Buku");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setText("Kategori");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buku 70.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("Master > Data Buku > Tambahkan Buku");

        txtpengarang.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpengarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dctahun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dctahun.setDateFormatString("dd-MM-yyyy");

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel16.setText("Tahun Terbit");

        sstok.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setText("Nama Rak");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setText("Lokasi");

        txtlokasi.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtlokasi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbrak.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbrak.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbrak.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelAddBukuLayout = new javax.swing.GroupLayout(panelAddBuku);
        panelAddBuku.setLayout(panelAddBukuLayout);
        panelAddBukuLayout.setHorizontalGroup(
            panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddBukuLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAddBukuLayout.createSequentialGroup()
                        .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAddBukuLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6)
                                .addComponent(txtkode, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                                .addComponent(txtnm))
                            .addComponent(txtpengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbkategori, javax.swing.GroupLayout.Alignment.LEADING, 0, 323, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10)
                            .addComponent(sstok, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel16)
                            .addComponent(txtlokasi)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddBukuLayout.createSequentialGroup()
                                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dctahun, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(24, 24, 24))
                            .addComponent(cbrak, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(394, 394, 394))))
            .addGroup(panelAddBukuLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(panelAddBukuLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelAddBukuLayout.setVerticalGroup(
            panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddBukuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel20)
                .addGap(34, 34, 34)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dctahun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtkode, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sstok, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddBukuLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtpengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddBukuLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(12, 12, 12)
                        .addComponent(cbrak, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(panelAddBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddBukuLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtpenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAddBukuLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtlokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(cbkategori, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        panel_mainBuku.add(panelAddBuku, "card2");

        panelUbahBuku.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel11.setText("Ubah Data Buku");

        bubah.setForeground(new java.awt.Color(0, 0, 0));
        bubah.setText("UBAH");
        bubah.setFillClick(new java.awt.Color(143, 169, 152));
        bubah.setFillOriginal(new java.awt.Color(169, 225, 144));
        bubah.setFillOver(new java.awt.Color(169, 225, 144));
        bubah.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahActionPerformed(evt);
            }
        });

        bbatal3.setForeground(new java.awt.Color(0, 0, 0));
        bbatal3.setText("BATAL");
        bbatal3.setFillClick(new java.awt.Color(143, 169, 152));
        bbatal3.setFillOriginal(new java.awt.Color(169, 225, 144));
        bbatal3.setFillOver(new java.awt.Color(169, 225, 144));
        bbatal3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bbatal3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatal3ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel12.setText("Kode Buku");

        txtkode1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtkode1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel13.setText("Judul Buku");

        txtnm1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel14.setText("Penerbit");

        txtpenerbit1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpenerbit1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel15.setText("Pengarang");

        cbkategori1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbkategori1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kategori", "Karya Umum & Komputer", "Filsafat & Psikologi", "Agama", "Ilmu-ilmu Sosial", "Bahasa ", "Ilmu Pengetahuan Alam & Matematika", "Teknologi & Ilmu Terapan", "Seni, Hiburan, & Olahraga", "Sastra", "Geografi & Sejarah", " " }));
        cbkategori1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel18.setText("Stok");

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel21.setText("Kategori");

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/buku 70.png"))); // NOI18N

        jLabel35.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(153, 153, 153));
        jLabel35.setText("Master > Data Buku > Ubah Data Buku");

        txtpengarang1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpengarang1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dctahun1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dctahun1.setDateFormatString("dd-MM-yyyy");

        jLabel36.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel36.setText("Tahun Terbit");

        sstok1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel37.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel37.setText("Nama Rak");

        jLabel38.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel38.setText("Lokasi");

        txtlokasi1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtlokasi1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbrak1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelUbahBukuLayout = new javax.swing.GroupLayout(panelUbahBuku);
        panelUbahBuku.setLayout(panelUbahBukuLayout);
        panelUbahBukuLayout.setHorizontalGroup(
            panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbahBukuLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelUbahBukuLayout.createSequentialGroup()
                        .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbahBukuLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13)
                                .addComponent(jLabel15)
                                .addComponent(txtkode1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                                .addComponent(txtnm1))
                            .addComponent(txtpengarang1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpenerbit1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbkategori1, javax.swing.GroupLayout.Alignment.LEADING, 0, 323, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel38)
                            .addComponent(sstok1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel36)
                            .addComponent(txtlokasi1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUbahBukuLayout.createSequentialGroup()
                                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dctahun1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel37))
                                .addGap(24, 24, 24))
                            .addComponent(cbrak1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(394, 394, 394))))
            .addGroup(panelUbahBukuLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addGroup(panelUbahBukuLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelUbahBukuLayout.setVerticalGroup(
            panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbahBukuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel35)
                .addGap(34, 34, 34)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel36))
                .addGap(18, 18, 18)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dctahun1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtkode1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sstok1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnm1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUbahBukuLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtpengarang1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUbahBukuLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(18, 18, 18)
                        .addComponent(cbrak1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(panelUbahBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUbahBukuLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(txtpenerbit1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbahBukuLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(18, 18, 18)
                        .addComponent(txtlokasi1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(cbkategori1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        panel_mainBuku.add(panelUbahBuku, "card2");

        add(panel_mainBuku, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        panel_mainBuku.removeAll();
        panel_mainBuku.add(panelViewBuku);
        panel_mainBuku.repaint();
        panel_mainBuku.revalidate();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
    String kode = txtkode.getText();
    String judul = txtnm.getText();
    String pengarang = txtpengarang.getText();
    String penerbit = txtpenerbit.getText();
    String lokasi = txtlokasi.getText();
    String kategori = cbkategori.getSelectedItem().toString();
    String namaRak = cbrak.getSelectedItem().toString();
    Date tanggal = dctahun.getDate();
    int stok = (int) sstok.getValue();

     // Validasi
    if (!isFormValid(judul, pengarang, penerbit, kategori, namaRak, tanggal)) {
        JOptionPane.showMessageDialog(this, "Semua data harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String kodeRak = getKodeRakByNama(namaRak);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tahun = sdf.format(tanggal);

    try {
        String sql = "INSERT INTO buku (kode_buku, judul_buku, pengarang, penerbit, kategori, tahun_terbit, stok, kode_rak, lokasi_buku) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1, kode);
        stat.setString(2, judul);
        stat.setString(3, pengarang);
        stat.setString(4, penerbit);
        stat.setString(5, kategori);
        stat.setString(6, tahun);
        stat.setInt(7, stok);
        stat.setString(8, kodeRak);
        stat.setString(9, lokasi);
        stat.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data buku berhasil disimpan.");
        kosong();
        datatable();
        generateIDBuku();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
    }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void btambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambahActionPerformed
        String aksi = btambah.getText();

        if ("TAMBAH".equalsIgnoreCase(aksi)) {
            panel_mainBuku.removeAll();
            panel_mainBuku.add(panelAddBuku);
        } else if ("UBAH".equalsIgnoreCase(aksi)) {
            panel_mainBuku.removeAll();
            panel_mainBuku.add(panelUbahBuku);
        }

        panel_mainBuku.repaint();
        panel_mainBuku.revalidate();
    }//GEN-LAST:event_btambahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        String kode = txtkode1.getText();
    int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (konfirmasi == JOptionPane.YES_OPTION) {
        try {
            String sql = "DELETE FROM buku WHERE kode_buku = ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, kode);
            stat.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data buku berhasil dihapus.");
            kosong();
            datatable();
            btambah.setText("TAMBAH");
            bhapus.setVisible(false);
            bbatalUbah.setVisible(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalUbahActionPerformed
        panel_mainBuku.removeAll();
        panel_mainBuku.add(panelViewBuku);
        panel_mainBuku.repaint();
        panel_mainBuku.revalidate();
        bhapus.setVisible(false);
        bbatalUbah.setVisible(false);
        btambah.setText("TAMBAH");
    }//GEN-LAST:event_bbatalUbahActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakActionPerformed
        try{
        String path="./src/laporan/dataBuku.jasper";
        HashMap parameter = new HashMap();
        //parameter.put("id_nota",txtidnota.getText());
        JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);
        JasperViewer.viewReport(print, false);
            
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan: " + ex.getMessage());
        }
    }//GEN-LAST:event_bcetakActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
        String kode = txtkode1.getText();
    String judul = txtnm1.getText();
    String pengarang = txtpengarang1.getText();
    String penerbit = txtpenerbit1.getText();
    String lokasi = txtlokasi1.getText();
    String kategori = cbkategori1.getSelectedItem().toString();
    String namaRak = cbrak1.getSelectedItem().toString();
    Date tanggal = dctahun1.getDate();
    int stok = (int) sstok1.getValue();

    if (judul.isEmpty() || pengarang.isEmpty() || penerbit.isEmpty() ||
        kategori.equals("Pilih Kategori") || namaRak.equals("Pilih Rak") || tanggal == null) {
        JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
        return;
    }

    String kodeRak = getKodeRakByNama(namaRak);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tahun = sdf.format(tanggal);

    try {
        String sql = "UPDATE buku SET judul_buku=?, pengarang=?, penerbit=?, kategori=?, tahun_terbit=?, stok=?, kode_rak=?, lokasi_buku=? WHERE kode_buku=?";
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1, judul);
        stat.setString(2, pengarang);
        stat.setString(3, penerbit);
        stat.setString(4, kategori);
        stat.setString(5, tahun);
        stat.setInt(6, stok);
        stat.setString(7, kodeRak);
        stat.setString(8, lokasi);
        stat.setString(9, kode);
        stat.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data buku berhasil diubah.");
        kosong();
        datatable();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
    }
        panel_mainBuku.removeAll();
        panel_mainBuku.add(panelViewBuku);
        panel_mainBuku.repaint();
        panel_mainBuku.revalidate();
    }//GEN-LAST:event_bubahActionPerformed

    private void bbatal3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatal3ActionPerformed
        panel_mainBuku.removeAll();
        panel_mainBuku.add(panelViewBuku);
        panel_mainBuku.repaint();
        panel_mainBuku.revalidate();
    }//GEN-LAST:event_bbatal3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pallete.ButtonRounded bbatal;
    private pallete.ButtonRounded bbatal3;
    private pallete.ButtonRounded bbatalUbah;
    private pallete.ButtonRounded bcetak;
    private pallete.ButtonRounded bhapus;
    private pallete.ButtonRounded bsimpan;
    private pallete.ButtonRounded btambah;
    private pallete.ButtonRounded bubah;
    private javax.swing.JComboBox<String> cbkategori;
    private javax.swing.JComboBox<String> cbkategori1;
    private javax.swing.JComboBox<String> cbrak;
    private javax.swing.JComboBox<String> cbrak1;
    private com.toedter.calendar.JDateChooser dctahun;
    private com.toedter.calendar.JDateChooser dctahun1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAddBuku;
    private javax.swing.JPanel panelAtasBuku;
    private javax.swing.JPanel panelTableBuku;
    private javax.swing.JPanel panelUbahBuku;
    private javax.swing.JPanel panelViewBuku;
    private javax.swing.JPanel panel_mainBuku;
    private com.toedter.components.JSpinField sstok;
    private com.toedter.components.JSpinField sstok1;
    private javax.swing.JTable tblbuku;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtkode;
    private javax.swing.JTextField txtkode1;
    private javax.swing.JTextField txtlokasi;
    private javax.swing.JTextField txtlokasi1;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtnm1;
    private javax.swing.JTextField txtpenerbit;
    private javax.swing.JTextField txtpenerbit1;
    private javax.swing.JTextField txtpengarang;
    private javax.swing.JTextField txtpengarang1;
    // End of variables declaration//GEN-END:variables
}
