package tampilan;
import java.sql.*;
import javax.swing.*;
import java.awt.Font;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.*;
import java.util.HashMap;
import com.toedter.calendar.JDateChooser; // Untuk JDateChooser
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.text.ParseException;          // Untuk exception saat parse tanggal
import java.text.SimpleDateFormat;        // Untuk format tanggal
import java.util.Calendar;
import java.util.Date;                    // Untuk class Date
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;


public class panel_peminjaman extends javax.swing.JPanel {
    public String idsiswa, nama, jenis, jurusan, kelas, telp;
    public String kdbuku, jdlbuku, rang, bit, tt, gori, stok, kdrak, lokasi;

    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;

    public panel_peminjaman() {
        initComponents();
        itemTerpilih();
        itemTerpilihBk();
        setupTable();
        aktif();
        kosong();
        getPenjaga();
        generateIDPinjam();
        setTanggalPinjamDanKembali();
        datatable();
        setupListeners();
        aturKomponen();
    }

    private void aturKomponen() {
        JTextField[] fields = {
            txtid, txtid1, txtidsiswa, txtidsiswa1, txtkdbuku, txtkdbuku1, txtstok, txtstok1
        };
        for (JTextField f : fields) {
            f.setEditable(false);
            f.setFocusable(false);
        }
        dpinjam.setEnabled(false);
        dkembali.setEnabled(false);
        dpinjam1.setEnabled(false);
        dkembali1.setEnabled(false);
    }

    private void setTanggalPinjamDanKembali() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date returnDate = cal.getTime();
        dpinjam.setDate(today);
        dkembali.setDate(returnDate);
        dpinjam1.setDate(today);
        dkembali1.setDate(returnDate);
    }

    private void setupTable() {
        tblpeminjaman.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblpeminjaman.setRowHeight(30);
        JTableHeader header = tblpeminjaman.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 34));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        for (int i = 0; i < tblpeminjaman.getColumnModel().getColumnCount(); i++) {
            tblpeminjaman.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblpeminjaman.setShowHorizontalLines(true);
        tblpeminjaman.setShowVerticalLines(true);
        tblpeminjaman.setGridColor(new Color(220, 220, 220));
    }

    private void setupListeners() {
        tblpeminjaman.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblpeminjaman.getSelectedRow();
                if (row != -1) tampilData(row);
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
            txtid1.setText(tblpeminjaman.getValueAt(baris, 0).toString());
            txtidsiswa1.setText(tblpeminjaman.getValueAt(baris, 1).toString());
            txtnm1.setText(tblpeminjaman.getValueAt(baris, 2).toString());
            txtkdbuku1.setText(tblpeminjaman.getValueAt(baris, 3).toString());
            txtjdlbuku1.setText(tblpeminjaman.getValueAt(baris, 4).toString());
            txtjumlah1.setText(tblpeminjaman.getValueAt(baris, 5).toString());
            txtstok1.setText(tblpeminjaman.getValueAt(baris, 6).toString());
            cbpenjaga1.setSelectedItem(tblpeminjaman.getValueAt(baris, 10).toString());
            bcarisiswa1.setEnabled(false);           
            bcaribuku1.setEnabled(false);           
            btambah.setText("UBAH");
            bhapus.setVisible(true);
            bbatalUbah.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Salah Ubah Data: " + e.getMessage());
        }
    }

    private boolean formValid() {
    try {
        if (txtid.getText().trim().isEmpty()) {
            throw new Exception("ID Peminjaman tidak boleh kosong.");
        }

        if (txtidsiswa.getText().trim().isEmpty()) {
            throw new Exception("ID Siswa harus diisi.");
        }

        if (txtnm.getText().trim().isEmpty()) {
            throw new Exception("Nama siswa harus diisi.");
        }

        if (txtkdbuku.getText().trim().isEmpty()) {
            throw new Exception("Kode Buku tidak boleh kosong.");
        }

        if (txtjdlbuku.getText().trim().isEmpty()) {
            throw new Exception("Judul Buku harus diisi.");
        }

        if (txtjumlah.getText().trim().isEmpty()) {
            throw new Exception("Jumlah pinjam harus diisi.");
        }

        // Validasi jumlah harus angka positif
        int jumlah;
        try {
            jumlah = Integer.parseInt(txtjumlah.getText().trim());
        } catch (NumberFormatException e) {
            throw new Exception("Jumlah pinjam harus berupa angka.");
        }

        if (jumlah <= 0) {
            throw new Exception("Jumlah pinjam harus lebih dari 0.");
        }

        // Validasi stok
        int stok;
        try {
            stok = Integer.parseInt(txtstok.getText().trim());
        } catch (NumberFormatException e) {
            throw new Exception("Stok buku harus berupa angka.");
        }

        if (jumlah > stok) {
            throw new Exception("Jumlah pinjam melebihi stok buku yang tersedia.");
        }

        // Validasi tanggal
        if (dpinjam.getDate() == null) {
            throw new Exception("Tanggal pinjam belum dipilih.");
        }

        if (dkembali.getDate() == null) {
            throw new Exception("Tanggal kembali belum dipilih.");
        }

        if (dkembali.getDate().before(dpinjam.getDate())) {
            throw new Exception("Tanggal kembali tidak boleh sebelum tanggal pinjam.");
        }

        // Validasi petugas/penjaga
        if (cbpenjaga.getSelectedIndex() == 0) {
            throw new Exception("Silakan pilih nama penjaga.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    return true;
}


    private void generateIDPinjam() {
        try {
            String sql = "SELECT MAX(RIGHT(id_peminjaman, 3)) AS nomor FROM peminjaman";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            String kode = "P001";
            if (rs.next() && rs.getString("nomor") != null) {
                kode = String.format("P%03d", Integer.parseInt(rs.getString("nomor")) + 1);
            }
            txtid.setText(kode);
            txtid1.setText(kode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal generate ID: " + e.getMessage());
        }
    }

    private void getPenjaga() {
        try {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Pilih Penjaga");
            ResultSet rs = conn.prepareStatement("SELECT nama_penjaga FROM penjaga").executeQuery();
            while (rs.next()) model.addElement(rs.getString(1));
            cbpenjaga.setModel(model);
            cbpenjaga1.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load penjaga: " + e.getMessage());
        }
    }

    protected void aktif() {
        txtjumlah.requestFocus();
        txtjumlah1.requestFocus();
        bhapus.setVisible(false);
        bbatalUbah.setVisible(false);
    }

    protected void kosong() {
        JTextField[] fields = {
            txtidsiswa, txtnm, txtkdbuku, txtjdlbuku, txtstok, txtjumlah,
            txtidsiswa1, txtnm1, txtkdbuku1, txtjdlbuku1, txtstok1, txtjumlah1
        };
        for (JTextField f : fields) f.setText("");
        if (cbpenjaga.getItemCount() > 0) cbpenjaga.setSelectedIndex(0);
        if (cbpenjaga1.getItemCount() > 0) cbpenjaga1.setSelectedIndex(0);
    }

    public void itemTerpilih() {
        PopUpSiswa Pp = new PopUpSiswa();
        Pp.siswa = this;
        txtidsiswa.setText(idsiswa);
        txtnm.setText(nama);
        txtidsiswa1.setText(idsiswa);
        txtnm1.setText(nama);
    }

    public void itemTerpilihBk() {
        PopUpBuku Bk = new PopUpBuku();
        Bk.buku = this;
        txtkdbuku.setText(kdbuku);
        txtjdlbuku.setText(jdlbuku);
        txtstok.setText(stok);
        txtkdbuku1.setText(kdbuku);
        txtjdlbuku1.setText(jdlbuku);
        txtstok1.setText(stok);
    }

    protected void datatable() {
        String[] columns = {
            "ID Peminjaman", "ID Siswa", "Nama Siswa", "ID Buku", "Judul Buku",
            "Jumlah Pinjam", "Stok Buku", "Tanggal Pinjam", "Tanggal Kembali", "Status", "Nama Penjaga"
        };
        tabmode = new DefaultTableModel(null, columns);
        tblpeminjaman.setModel(tabmode);

        String sql = "SELECT p.id_peminjaman, p.id_siswa, p.nmsiswa, p.id_buku, p.judul_buku, p.jumlah, " +
             "b.stok, d.tanggal_pinjam, d.tanggal_kembali, d.status, d.nm_pjg " +
             "FROM peminjaman p " +
             "JOIN detailPinjam d ON d.id_peminjaman = p.id_peminjaman " +
             "JOIN buku b ON b.kode_buku = p.id_buku " +
             "WHERE p.id_peminjaman LIKE ? OR p.nmsiswa LIKE ?";
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            String keyword = "%" + txtcari.getText() + "%";
            stat.setString(1, keyword);
            stat.setString(2, keyword);
            ResultSet hasil = stat.executeQuery();
            while (hasil.next()) {
                tabmode.addRow(new String[] {
                    hasil.getString(1), hasil.getString(2), hasil.getString(3), hasil.getString(4),
                    hasil.getString(5), hasil.getString(6), hasil.getString(7), hasil.getString(8),
                    hasil.getString(9), hasil.getString(10), hasil.getString(11)
                });
            }
            setupTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_main = new javax.swing.JPanel();
        panelView = new javax.swing.JPanel();
        panelAtas = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        btambah = new pallete.ButtonRounded();
        bhapus = new pallete.ButtonRounded();
        bbatalUbah = new pallete.ButtonRounded();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        bcetak = new pallete.ButtonRounded();
        panelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpeminjaman = new javax.swing.JTable();
        panelAdd = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bsimpan = new pallete.ButtonRounded();
        bbatal = new pallete.ButtonRounded();
        jLabel3 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtidsiswa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtnm = new javax.swing.JTextField();
        dpinjam = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtjdlbuku = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        bcarisiswa = new javax.swing.JButton();
        bcaribuku = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtkdbuku = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        dkembali = new com.toedter.calendar.JDateChooser();
        txtjumlah = new javax.swing.JTextField();
        txtstok = new javax.swing.JTextField();
        cbpenjaga = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        panelUbah = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        bubah = new pallete.ButtonRounded();
        bbatal1 = new pallete.ButtonRounded();
        jLabel10 = new javax.swing.JLabel();
        txtid1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtidsiswa1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtnm1 = new javax.swing.JTextField();
        dpinjam1 = new com.toedter.calendar.JDateChooser();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtjdlbuku1 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        bcarisiswa1 = new javax.swing.JButton();
        bcaribuku1 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        txtkdbuku1 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        dkembali1 = new com.toedter.calendar.JDateChooser();
        txtjumlah1 = new javax.swing.JTextField();
        txtstok1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbpenjaga1 = new javax.swing.JComboBox<>();

        setLayout(new java.awt.CardLayout());

        panel_main.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));
        panelView.setLayout(new java.awt.BorderLayout());

        panelAtas.setBackground(new java.awt.Color(255, 255, 255));
        panelAtas.setMinimumSize(new java.awt.Dimension(250, 250));

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel22.setText("Data Peminjaman");

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
        jLabel24.setText("Transaksi > Data Peminjaman");

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

        javax.swing.GroupLayout panelAtasLayout = new javax.swing.GroupLayout(panelAtas);
        panelAtas.setLayout(panelAtasLayout);
        panelAtasLayout.setHorizontalGroup(
            panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtasLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btambah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bbatalUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1009, Short.MAX_VALUE))
            .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAtasLayout.createSequentialGroup()
                    .addGap(54, 54, 54)
                    .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelAtasLayout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel22))
                        .addGroup(panelAtasLayout.createSequentialGroup()
                            .addGap(903, 903, 903)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(55, Short.MAX_VALUE)))
        );
        panelAtasLayout.setVerticalGroup(
            panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtasLayout.createSequentialGroup()
                .addContainerGap(178, Short.MAX_VALUE)
                .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btambah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatalUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAtasLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(40, 40, 40)
                    .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22))
                    .addGap(35, 35, 35)
                    .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAtasLayout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(21, Short.MAX_VALUE)))
        );

        panelView.add(panelAtas, java.awt.BorderLayout.NORTH);

        panelTable.setBackground(new java.awt.Color(255, 255, 255));

        tblpeminjaman.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblpeminjaman);

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTableLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelView.add(panelTable, java.awt.BorderLayout.CENTER);

        panel_main.add(panelView, "card5");

        panelAdd.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel2.setText("Tambahkan Peminjaman");

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
        jLabel3.setText("ID Peminjaman");

        txtid.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("ID Siswa");

        txtidsiswa.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidsiswa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setText("Nama Siswa");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText("Stok Buku");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/peminjaman 70.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("Transaksi > Data Peminjaman > Tambahkan Peminjaman");

        txtnm.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dpinjam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dpinjam.setDateFormatString("dd-MM-yyyy");

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel16.setText("Tanggal Pinjam");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setText("Jumlah Pinjam");

        txtjdlbuku.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtjdlbuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel25.setText("Judul Buku");

        bcarisiswa.setBackground(new java.awt.Color(169, 225, 144));
        bcarisiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search 30.png"))); // NOI18N
        bcarisiswa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bcarisiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcarisiswaActionPerformed(evt);
            }
        });

        bcaribuku.setBackground(new java.awt.Color(169, 225, 144));
        bcaribuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search 30.png"))); // NOI18N
        bcaribuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bcaribuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcaribukuActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel26.setText("Kode Buku");

        txtkdbuku.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtkdbuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel28.setText("Tanggal Kembali");

        dkembali.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dkembali.setDateFormatString("dd-MM-yyyy");

        txtjumlah.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtjumlah.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjumlahActionPerformed(evt);
            }
        });

        txtstok.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtstok.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtstokActionPerformed(evt);
            }
        });

        cbpenjaga.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbpenjaga.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbpenjaga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel11.setText("Penjaga");

        javax.swing.GroupLayout panelAddLayout = new javax.swing.GroupLayout(panelAdd);
        panelAdd.setLayout(panelAddLayout);
        panelAddLayout.setHorizontalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panelAddLayout.createSequentialGroup()
                                    .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtkdbuku)
                                        .addComponent(txtjdlbuku)
                                        .addGroup(panelAddLayout.createSequentialGroup()
                                            .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel25)
                                                .addComponent(jLabel26))
                                            .addGap(0, 324, Short.MAX_VALUE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bcaribuku, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelAddLayout.createSequentialGroup()
                                    .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtnm, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtidsiswa))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bcarisiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addGap(158, 158, 158)
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel16)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                                            .addComponent(jLabel28)
                                            .addGap(194, 194, 194)))
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dkembali, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtstok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtjumlah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbpenjaga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addGap(0, 1005, Short.MAX_VALUE))
        );
        panelAddLayout.setVerticalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel20)
                .addGap(34, 34, 34)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17))
                .addGap(46, 46, 46)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(dkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtstok, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bcarisiswa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bcaribuku, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtkdbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(txtjdlbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(16, 16, 16)
                        .addComponent(cbpenjaga, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(131, 131, 131))
        );

        panel_main.add(panelAdd, "card2");

        panelUbah.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel8.setText("Ubah Data Peminjaman");

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

        bbatal1.setForeground(new java.awt.Color(0, 0, 0));
        bbatal1.setText("BATAL");
        bbatal1.setFillClick(new java.awt.Color(143, 169, 152));
        bbatal1.setFillOriginal(new java.awt.Color(169, 225, 144));
        bbatal1.setFillOver(new java.awt.Color(169, 225, 144));
        bbatal1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bbatal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatal1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setText("ID Peminjaman");

        txtid1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtid1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel29.setText("ID Siswa");

        txtidsiswa1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidsiswa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel31.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel31.setText("Nama Siswa");

        jLabel32.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel32.setText("Stok Buku");

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/peminjaman 70.png"))); // NOI18N

        jLabel39.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(153, 153, 153));
        jLabel39.setText("Transaksi > Data Peminjaman > Ubah Data Peminjaman");

        txtnm1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dpinjam1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dpinjam1.setDateFormatString("dd-MM-yyyy");

        jLabel40.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel40.setText("Tanggal Pinjam");

        jLabel41.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel41.setText("Jumlah Pinjam");

        txtjdlbuku1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtjdlbuku1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel43.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel43.setText("Judul Buku");

        bcarisiswa1.setBackground(new java.awt.Color(169, 225, 144));
        bcarisiswa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search 30.png"))); // NOI18N
        bcarisiswa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bcarisiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcarisiswa1ActionPerformed(evt);
            }
        });

        bcaribuku1.setBackground(new java.awt.Color(169, 225, 144));
        bcaribuku1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search 30.png"))); // NOI18N
        bcaribuku1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bcaribuku1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcaribuku1ActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel44.setText("Kode Buku");

        txtkdbuku1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtkdbuku1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel46.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel46.setText("Tanggal Kembali");

        dkembali1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dkembali1.setDateFormatString("dd-MM-yyyy");

        txtjumlah1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtjumlah1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtstok1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtstok1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel12.setText("Penjaga");

        cbpenjaga1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbpenjaga1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbpenjaga1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelUbahLayout = new javax.swing.GroupLayout(panelUbah);
        panelUbah.setLayout(panelUbahLayout);
        panelUbahLayout.setHorizontalGroup(
            panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelUbahLayout.createSequentialGroup()
                                        .addComponent(txtkdbuku1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bcaribuku1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelUbahLayout.createSequentialGroup()
                                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtnm1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtidsiswa1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bcarisiswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(152, 152, 152))
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel44)
                                    .addComponent(txtjdlbuku1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtid1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 217, Short.MAX_VALUE)))
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel32)
                                .addComponent(jLabel40)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUbahLayout.createSequentialGroup()
                                    .addComponent(jLabel46)
                                    .addGap(658, 658, 658)))
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(dpinjam1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel41)
                                            .addComponent(jLabel12)
                                            .addComponent(cbpenjaga1, 0, 396, Short.MAX_VALUE)
                                            .addComponent(txtjumlah1)
                                            .addComponent(txtstok1)))
                                    .addComponent(dkembali1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(397, 397, 397))))))
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelUbahLayout.setVerticalGroup(
            panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel39)
                .addGap(34, 34, 34)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33))
                .addGap(46, 46, 46)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addGap(333, 333, 333)
                                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bcaribuku1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelUbahLayout.createSequentialGroup()
                                        .addComponent(jLabel44)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtkdbuku1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(36, 36, 36)
                                .addComponent(jLabel43))
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtid1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel29)
                                .addGap(18, 18, 18)
                                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bcarisiswa1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtidsiswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addComponent(jLabel31)
                                .addGap(18, 18, 18)
                                .addComponent(txtnm1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(txtjdlbuku1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(18, 18, 18)
                        .addComponent(dpinjam1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel46)
                        .addGap(26, 26, 26)
                        .addComponent(dkembali1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel32)
                                .addGap(18, 18, 18)
                                .addComponent(txtstok1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addGap(137, 137, 137)
                                .addComponent(jLabel41)
                                .addGap(18, 18, 18)
                                .addComponent(txtjumlah1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(cbpenjaga1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        panel_main.add(panelUbah, "card2");

        add(panel_main, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        panel_main.removeAll();
        panel_main.add(panelView);
        panel_main.repaint();
        panel_main.revalidate(); 
        kosong();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        if (!formValid()) return;

        int jumlah = Integer.parseInt(txtjumlah.getText());
        int stok = Integer.parseInt(txtstok.getText());
        if (jumlah > stok) {
            JOptionPane.showMessageDialog(null, "Jumlah pinjam melebihi stok!");
            return;
        }

        String sqlPeminjaman = "INSERT INTO peminjaman VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO detailpinjam (id_peminjaman, tanggal_pinjam, tanggal_kembali, status, nm_pjg) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE buku SET stok = stok - ? WHERE kode_buku = ?";

        try {
            PreparedStatement pst1 = conn.prepareStatement(sqlPeminjaman);
            pst1.setString(1, txtid.getText());
            pst1.setString(2, txtidsiswa.getText());
            pst1.setString(3, txtnm.getText());
            pst1.setString(4, txtkdbuku.getText());
            pst1.setString(5, txtjdlbuku.getText());
            pst1.setInt(6, jumlah);
            pst1.executeUpdate();

            PreparedStatement pst2 = conn.prepareStatement(sqlDetail);
            pst2.setString(1, txtid.getText());
            pst2.setDate(2, new java.sql.Date(dpinjam.getDate().getTime()));
            pst2.setDate(3, new java.sql.Date(dkembali.getDate().getTime()));
            pst2.setString(4, "RENT");
            pst2.setString(5, cbpenjaga.getSelectedItem().toString());
            pst2.executeUpdate();

            PreparedStatement pst3 = conn.prepareStatement(sqlUpdateStok);
            pst3.setInt(1, jumlah);
            pst3.setString(2, txtkdbuku.getText());
            pst3.executeUpdate();

            kosong();
            generateIDPinjam();
            datatable();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + e.getMessage());
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void btambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambahActionPerformed
        String aksi = btambah.getText();

        if ("TAMBAH".equalsIgnoreCase(aksi)) {
            panel_main.removeAll();
            panel_main.add(panelAdd);
        } else if ("UBAH".equalsIgnoreCase(aksi)) {
            panel_main.removeAll();
            panel_main.add(panelUbah);
        }
        
        panel_main.repaint();
        panel_main.revalidate();
    }//GEN-LAST:event_btambahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String id = txtid1.getText();

                // Kembalikan stok
                String sqlGet = "SELECT id_buku, jumlah FROM peminjaman WHERE id_peminjaman = ?";
                PreparedStatement pstGet = conn.prepareStatement(sqlGet);
                pstGet.setString(1, id);
                ResultSet rs = pstGet.executeQuery();
                if (rs.next()) {
                    String kodeBuku = rs.getString("id_buku");
                    int jumlah = rs.getInt("jumlah");
                    String sqlStok = "UPDATE buku SET stok = stok + ? WHERE kode_buku = ?";
                    PreparedStatement pstStok = conn.prepareStatement(sqlStok);
                    pstStok.setInt(1, jumlah);
                    pstStok.setString(2, kodeBuku);
                    pstStok.executeUpdate();
                }

                PreparedStatement pst1 = conn.prepareStatement("DELETE FROM peminjaman WHERE id_peminjaman = ?");
                pst1.setString(1, id);
                pst1.executeUpdate();

                PreparedStatement pst2 = conn.prepareStatement("DELETE FROM detailpinjam WHERE id_peminjaman = ?");
                pst2.setString(1, id);
                pst2.executeUpdate();
                btambah.setText("TAMBAH");
                bhapus.setVisible(false);
                bbatalUbah.setVisible(false);
                datatable();
                kosong();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalUbahActionPerformed
        panel_main.removeAll();
        panel_main.add(panelView);
        panel_main.repaint();
        panel_main.revalidate();
        bhapus.setVisible(false);
        bbatalUbah.setVisible(false);
        btambah.setText("TAMBAH");
    }//GEN-LAST:event_bbatalUbahActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakActionPerformed
         try{
        String path="./src/laporan/dataPeminjaman.jasper";
        HashMap parameter = new HashMap();
        //parameter.put("id_nota",txtidnota.getText());
        JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);
        JasperViewer.viewReport(print, false);
            
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan: " + ex.getMessage());
        }
    }//GEN-LAST:event_bcetakActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
      try {
            String jumlahInput = txtjumlah1.getText().trim();
            if (!jumlahInput.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Jumlah harus berupa angka!");
                return;
            }
            int jumlahBaru = Integer.parseInt(jumlahInput);
            int stokSekarang = Integer.parseInt(txtstok1.getText());

            // Ambil jumlah lama dari database
            String sqlGetLama = "SELECT jumlah FROM peminjaman WHERE id_peminjaman = ?";
            PreparedStatement pstGet = conn.prepareStatement(sqlGetLama);
            pstGet.setString(1, txtid1.getText());
            ResultSet rs = pstGet.executeQuery();
            if (!rs.next()) return;
            int jumlahLama = rs.getInt(1);

            int selisih = jumlahBaru - jumlahLama;
            if (selisih > stokSekarang) {
                JOptionPane.showMessageDialog(null, "Jumlah tidak boleh lebih dari stok!");
                return;
            }

            // Update jumlah pinjam
            String sqlUpdate = "UPDATE peminjaman SET jumlah = ? WHERE id_peminjaman = ?";
            PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
            pstUpdate.setInt(1, jumlahBaru);
            pstUpdate.setString(2, txtid1.getText());
            pstUpdate.executeUpdate();

            // Update stok
            String sqlStok = "UPDATE buku SET stok = stok - ? WHERE kode_buku = ?";
            PreparedStatement pstStok = conn.prepareStatement(sqlStok);
            pstStok.setInt(1, selisih);
            pstStok.setString(2, txtkdbuku1.getText());
            pstStok.executeUpdate();

            datatable();
            kosong();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
        }
    }//GEN-LAST:event_bubahActionPerformed

    private void bbatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatal1ActionPerformed
        panel_main.removeAll();
        panel_main.add(panelView);
        panel_main.repaint();
        panel_main.revalidate();
        kosong();
    }//GEN-LAST:event_bbatal1ActionPerformed

    private void bcarisiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcarisiswa1ActionPerformed
        PopUpSiswa Pp = new PopUpSiswa();
        Pp.siswa = this;
        Pp.setVisible(true);
        Pp.setResizable(false);
    }//GEN-LAST:event_bcarisiswa1ActionPerformed

    private void bcaribuku1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcaribuku1ActionPerformed
        PopUpBuku Bk = new PopUpBuku();
        Bk.buku = this;
        Bk.setVisible(true);
        Bk.setResizable(false);
    }//GEN-LAST:event_bcaribuku1ActionPerformed

    private void bcarisiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcarisiswaActionPerformed
        PopUpSiswa Pp = new PopUpSiswa();
        Pp.siswa = this;
        Pp.setVisible(true);
        Pp.setResizable(false);
    }//GEN-LAST:event_bcarisiswaActionPerformed

    private void bcaribukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcaribukuActionPerformed
        PopUpBuku Bk = new PopUpBuku();
        Bk.buku = this;
        Bk.setVisible(true);
        Bk.setResizable(false);
    }//GEN-LAST:event_bcaribukuActionPerformed

    private void txtjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahActionPerformed

    private void txtstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtstokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstokActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pallete.ButtonRounded bbatal;
    private pallete.ButtonRounded bbatal1;
    private pallete.ButtonRounded bbatalUbah;
    private javax.swing.JButton bcaribuku;
    private javax.swing.JButton bcaribuku1;
    private javax.swing.JButton bcarisiswa;
    private javax.swing.JButton bcarisiswa1;
    private pallete.ButtonRounded bcetak;
    private pallete.ButtonRounded bhapus;
    private pallete.ButtonRounded bsimpan;
    private pallete.ButtonRounded btambah;
    private pallete.ButtonRounded bubah;
    private javax.swing.JComboBox<String> cbpenjaga;
    private javax.swing.JComboBox<String> cbpenjaga1;
    private com.toedter.calendar.JDateChooser dkembali;
    private com.toedter.calendar.JDateChooser dkembali1;
    private com.toedter.calendar.JDateChooser dpinjam;
    private com.toedter.calendar.JDateChooser dpinjam1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAdd;
    private javax.swing.JPanel panelAtas;
    private javax.swing.JPanel panelTable;
    private javax.swing.JPanel panelUbah;
    private javax.swing.JPanel panelView;
    private javax.swing.JPanel panel_main;
    private javax.swing.JTable tblpeminjaman;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtid1;
    private javax.swing.JTextField txtidsiswa;
    private javax.swing.JTextField txtidsiswa1;
    private javax.swing.JTextField txtjdlbuku;
    private javax.swing.JTextField txtjdlbuku1;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtjumlah1;
    private javax.swing.JTextField txtkdbuku;
    private javax.swing.JTextField txtkdbuku1;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtnm1;
    private javax.swing.JTextField txtstok;
    private javax.swing.JTextField txtstok1;
    // End of variables declaration//GEN-END:variables

}
