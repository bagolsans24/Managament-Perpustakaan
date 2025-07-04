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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;        // Untuk format tanggal
import java.util.Calendar;
import java.util.Date;                    // Untuk class Date
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;


public class panel_pengembalian extends javax.swing.JPanel {
    public String idpinjam, idsiswa, nama, kdbuku, jdlbuku,jpinjam;

    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;

    public panel_pengembalian() {
        initComponents();        
        setupTable();
        aktif();
        kosong(); 
        getPenjaga();
        generateIDKembali();
        setTanggalKembali();
        datatable();
        setupListeners();
        hitungDenda();
        txtidkembali.setEditable(false);
        txtidkembali.setFocusable(false);
        ((JTextField) dpengembalian.getDateEditor().getUiComponent()).setEditable(false);
        ((JTextField) dpengembalian.getDateEditor().getUiComponent()).setFocusable(false);
        
    }


   private void setTanggalKembali() {
        Date hariIni = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hariIni);
        dpengembalian.setDate(cal.getTime());
    }


    private void setupTable() {
        tblpengembalian.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblpengembalian.setRowHeight(30);
        JTableHeader header = tblpengembalian.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 34));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        for (int i = 0; i < tblpengembalian.getColumnModel().getColumnCount(); i++) {
            tblpengembalian.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblpengembalian.setShowHorizontalLines(true);
        tblpengembalian.setShowVerticalLines(true);
        tblpengembalian.setGridColor(new Color(220, 220, 220));
    }

    private void setupListeners() {
        tblpengembalian.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblpengembalian.getSelectedRow();
                if (row != -1) tampilData(row);
            }
            });
            dpengembalian.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
            hitungDenda();
            }
        });


        txtcari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(DocumentEvent e) { datatable(); }
            public void removeUpdate(DocumentEvent e) { datatable(); }
            public void changedUpdate(DocumentEvent e) { datatable(); }
        });
    }
    private void hitungDenda() {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date tglKembali = dpengembalian.getDate();
        String idPinjam = txtidpinjam.getText();

        if (tglKembali != null && !idPinjam.isEmpty()) {
            String sql = "SELECT tanggal_pinjam FROM detailpinjam WHERE id_peminjaman = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idPinjam);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Date tglPinjam = rs.getDate("tanggal_pinjam");

                long selisih = (tglKembali.getTime() - tglPinjam.getTime()) / (1000 * 60 * 60 * 24);
                int batasHari = 7;
                int dendaPerHari = 1000;
                int denda = 0;

                if (selisih > batasHari) {
                    denda = (int)(selisih - batasHari) * dendaPerHari;
                }

                txtdenda.setText(String.valueOf(denda));
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menghitung denda: " + e.getMessage());
    }
}


    private void tampilData(int baris) {
        try {
            txtidkembali.setText(tblpengembalian.getValueAt(baris, 0).toString());
            txtidpinjam.setText(tblpengembalian.getValueAt(baris, 1).toString());            
            txtdenda.setText(tblpengembalian.getValueAt(baris, 3).toString());                                             
            bhapus.setVisible(true);
            bbatalUbah.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Salah Ubah Data: " + e.getMessage());
        }
    }

    private boolean formValid() {
        try {
            if (txtidsiswa.getText().trim().isEmpty()) throw new Exception("ID Siswa harus diisi.");
            if (txtnm.getText().trim().isEmpty()) throw new Exception("Nama Siswa harus diisi.");
            if (txtkdbuku.getText().trim().isEmpty()) throw new Exception("Kode Buku harus diisi.");
            if (txtjdlbuku.getText().trim().isEmpty()) throw new Exception("Judul Buku harus diisi.");
            if (txtjkembali.getText().trim().isEmpty()) throw new Exception("Jumlah Buku harus diisi.");
            int jumlah = Integer.parseInt(txtjkembali.getText().trim());
            if (jumlah <= 0) throw new Exception("Jumlah harus lebih dari 0.");
            if (dpengembalian.getDate() == null) throw new Exception("Tanggal kembali belum dipilih.");
            if (cbpenjaga.getSelectedIndex() == 0) throw new Exception("Silakan pilih penjaga.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }


    private void generateIDKembali() {
        try {
            String sql = "SELECT MAX(RIGHT(id_pengembalian, 3)) AS nomor FROM pengembalian";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            String kode = "K001";
            if (rs.next() && rs.getString("nomor") != null) {
                kode = String.format("K%03d", Integer.parseInt(rs.getString("nomor")) + 1);
            }
            txtidkembali.setText(kode);            
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load penjaga: " + e.getMessage());
        }
    }

    protected void aktif() {
        txtjkembali.requestFocus();       
        bhapus.setVisible(false);
        bbatalUbah.setVisible(false);
    }

    protected void kosong() {
    txtidpinjam.setText("");
    txtidsiswa.setText("");
    txtnm.setText("");
    txtkdbuku.setText("");
    txtjdlbuku.setText("");
    txtjpinjam.setText("");
    txtjkembali.setText("");
    txtdenda.setText("");   
    dpengembalian.setDate(null);
    if (cbpenjaga.getItemCount() > 0) cbpenjaga.setSelectedIndex(0);  
    }
    

    public void itemTerpilihPinjam() {
        PopUpDetailPinjam Dp = new PopUpDetailPinjam();
        Dp.pinjam = this;
        txtidpinjam.setText(idpinjam);
        txtidsiswa.setText(idsiswa);
        txtnm.setText(nama);
        txtkdbuku.setText(kdbuku);
        txtjdlbuku.setText(jdlbuku);
        txtjpinjam.setText(jpinjam);           
    }

     protected void datatable() {
        Object[] Baris = {"ID Pengembalian", "ID Peminjaman", "Nama Siswa", "Judul Buku", "Tanggal Pengembalian", "Denda", "Penjaga"};
        tabmode = new DefaultTableModel(null, Baris);
        tblpengembalian.setModel(tabmode);

        String sql = "SELECT * FROM pengembalian WHERE id_pengembalian LIKE ? OR id_peminjaman LIKE ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            String keyword = "%" + txtcari.getText() + "%";
            stat.setString(1, keyword);
            stat.setString(2, keyword);
            ResultSet hasil = stat.executeQuery();
            while (hasil.next()) {
                String[] data = {
                    hasil.getString("id_pengembalian"),
                    hasil.getString("id_peminjaman"),
                    hasil.getString("nama"),
                    hasil.getString("judul_buku"),
                    hasil.getString("tanggal_pengembalian"),
                    hasil.getString("denda"),                  
                    hasil.getString("nm_pjg")                   
                };
                tabmode.addRow(data);
            }
            setupTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e);
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
        tblpengembalian = new javax.swing.JTable();
        panelAdd = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bsimpan = new pallete.ButtonRounded();
        bbatal = new pallete.ButtonRounded();
        jLabel3 = new javax.swing.JLabel();
        txtidpinjam = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtidsiswa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtnm = new javax.swing.JTextField();
        dpengembalian = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtjdlbuku = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        bcariid = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtkdbuku = new javax.swing.JTextField();
        txtdenda = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtjkembali = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtjpinjam = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtidkembali = new javax.swing.JTextField();
        cbpenjaga = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        panel_main.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));
        panelView.setLayout(new java.awt.BorderLayout());

        panelAtas.setBackground(new java.awt.Color(255, 255, 255));
        panelAtas.setMinimumSize(new java.awt.Dimension(250, 250));

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel22.setText("Data Pengembalian");

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
        jLabel24.setText("Transaksi > Data Pengembalian");

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

        tblpengembalian.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblpengembalian);

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
        jLabel2.setText("Tambahkan Pengembalian");

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

        txtidpinjam.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidpinjam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("ID Siswa");

        txtidsiswa.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidsiswa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setText("Nama Siswa");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText("Denda");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/peminjaman 70.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("Transaksi > Data Pengembalian > Tambahkan Pengembalian");

        txtnm.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dpengembalian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        dpengembalian.setDateFormatString("dd-MM-yyyy");

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel16.setText("Tanggal Dikembalikan");

        txtjdlbuku.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtjdlbuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel25.setText("Judul Buku");

        bcariid.setBackground(new java.awt.Color(169, 225, 144));
        bcariid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search 30.png"))); // NOI18N
        bcariid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bcariid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariidActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel26.setText("Kode Buku");

        txtkdbuku.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtkdbuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtdenda.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtdenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtdenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdendaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setText("Jumlah Kembali");

        txtjkembali.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtjkembali.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtjkembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjkembaliActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setText("Jumlah Pinjam");

        txtjpinjam.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtjpinjam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtjpinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjpinjamActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setText("ID Pengembalian");

        txtidkembali.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidkembali.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbpenjaga.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbpenjaga.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbpenjaga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel27.setText("Penjaga");

        javax.swing.GroupLayout panelAddLayout = new javax.swing.GroupLayout(panelAdd);
        panelAdd.setLayout(panelAddLayout);
        panelAddLayout.setHorizontalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtkdbuku)
                                .addGroup(panelAddLayout.createSequentialGroup()
                                    .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel26))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(txtnm)
                                .addComponent(txtidsiswa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addComponent(txtidpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bcariid, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)
                            .addComponent(txtidkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtjdlbuku, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dpengembalian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtdenda, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtjkembali, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtjpinjam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel25))
                        .addGap(103, 103, 103)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(cbpenjaga, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(112, Short.MAX_VALUE))
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addGap(0, 0, Short.MAX_VALUE))
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
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtjdlbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbpenjaga, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtidkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtjpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dpengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtjkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtdenda, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtidpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bcariid, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtidsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(txtkdbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        panel_main.add(panelAdd, "card2");

        add(panel_main, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void btambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambahActionPerformed
            panel_main.removeAll();
            panel_main.add(panelAdd);
            panel_main.repaint();
            panel_main.revalidate();            
    }//GEN-LAST:event_btambahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Ingin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (ok == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM pengembalian WHERE id_pengembalian = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtidkembali.getText()); 
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            kosong();        // reset form
            datatable();     // refresh tabel
            btambah.setText("TAMBAH");
            bhapus.setVisible(false);
            bbatalUbah.setVisible(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal dihapus: " + e.getMessage());
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
    }//GEN-LAST:event_bbatalUbahActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakActionPerformed
        PopUpCetak c = new PopUpCetak();
        c.setVisible(true);
        c.setResizable(false);
    }//GEN-LAST:event_bcetakActionPerformed

    private void txtdendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdendaActionPerformed

    private void bcariidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariidActionPerformed
        PopUpDetailPinjam Dp = new PopUpDetailPinjam();
        Dp.pinjam = this;
        Dp.setVisible(true);
        Dp.setResizable(false);
    }//GEN-LAST:event_bcariidActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        panel_main.removeAll();
        panel_main.add(panelView);
        panel_main.repaint();
        panel_main.revalidate();
        kosong();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        if (!formValid()) return;

        int jumlah = Integer.parseInt(txtjpinjam.getText());
        int stok = Integer.parseInt(txtjkembali.getText());
        if (stok > jumlah) {
            JOptionPane.showMessageDialog(null, "Jumlah kembali melebihi jumlah pinjam!");
            return;
        }

        try {
            String sql = "INSERT INTO pengembalian (id_pengembalian, id_peminjaman, nama, judul_buku, tanggal_pengembalian, denda, nm_pjg) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtidkembali.getText());
            pst.setString(2, txtidpinjam.getText());
            pst.setString(3, txtnm.getText());
            pst.setString(4, txtjdlbuku.getText());
            pst.setDate(5, new java.sql.Date(dpengembalian.getDate().getTime()));
            pst.setInt(6, Integer.parseInt(txtdenda.getText()));
            pst.setString(7, cbpenjaga.getSelectedItem().toString());
            pst.executeUpdate();

            // Update status peminjaman
            PreparedStatement pst2 = conn.prepareStatement("UPDATE detailpinjam SET status = 'RETURNED' WHERE id_peminjaman = ?");
            pst2.setString(1, txtidpinjam.getText());
            pst2.executeUpdate();

            // Tambahkan stok kembali
            String sqlStok = "UPDATE buku SET stok = stok + ? WHERE kode_buku = ?";
            PreparedStatement pst3 = conn.prepareStatement(sqlStok);
            pst3.setInt(1, Integer.parseInt(txtjkembali.getText())); // Jumlah yang dikembalikan
            pst3.setString(2, txtkdbuku.getText());                   // Kode buku
            pst3.executeUpdate();

            JOptionPane.showMessageDialog(null, "Pengembalian berhasil disimpan.");
            // resetField(); // Buat method untuk mereset field jika perlu
            kosong();
            generateIDKembali();
            datatable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan pengembalian: " + e.getMessage());
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void txtjkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjkembaliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjkembaliActionPerformed

    private void txtjpinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjpinjamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjpinjamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pallete.ButtonRounded bbatal;
    private pallete.ButtonRounded bbatalUbah;
    private javax.swing.JButton bcariid;
    private pallete.ButtonRounded bcetak;
    private pallete.ButtonRounded bhapus;
    private pallete.ButtonRounded bsimpan;
    private pallete.ButtonRounded btambah;
    private javax.swing.JComboBox<String> cbpenjaga;
    private com.toedter.calendar.JDateChooser dpengembalian;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAdd;
    private javax.swing.JPanel panelAtas;
    private javax.swing.JPanel panelTable;
    private javax.swing.JPanel panelView;
    private javax.swing.JPanel panel_main;
    private javax.swing.JTable tblpengembalian;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtdenda;
    private javax.swing.JTextField txtidkembali;
    private javax.swing.JTextField txtidpinjam;
    private javax.swing.JTextField txtidsiswa;
    private javax.swing.JTextField txtjdlbuku;
    private javax.swing.JTextField txtjkembali;
    private javax.swing.JTextField txtjpinjam;
    private javax.swing.JTextField txtkdbuku;
    private javax.swing.JTextField txtnm;
    // End of variables declaration//GEN-END:variables

}
