package tampilan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import koneksi.koneksi;

public class panel_dataRak extends javax.swing.JPanel {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;

    public panel_dataRak() {
        initComponents();
        kosong();
        aktif();
        datatable();
        generateIDRak();
        setupTable();

        txtkode.setEditable(false);
        txtkode.setFocusable(false);
        txtkode1.setEditable(false);
        txtkode1.setFocusable(false);

        tblrak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int baris = tblrak.getSelectedRow();
                if (baris != -1) {
                    String id = tblrak.getValueAt(baris, 0).toString();
                    String nama = tblrak.getValueAt(baris, 1).toString();
                    String lokasi = tblrak.getValueAt(baris, 2).toString();
                    String max = tblrak.getValueAt(baris, 3).toString();
                    String keterangan = tblrak.getValueAt(baris, 4).toString();

                    txtkode1.setText(id);
                    txtnm1.setText(nama);
                    cblokasi1.setSelectedItem(lokasi);
                    txtmax1.setText(max);
                    txtket1.setText(keterangan);

                    btambah.setText("UBAH");
                    bhapus.setVisible(true);
                    bbatalUbah.setVisible(true);
                }
            }
        });

        txtcari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                datatable();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                datatable();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                datatable();
            }
        });
    }

    private void generateIDRak() {
        try {
            String sql = "SELECT MAX(RIGHT(kode_rak, 3)) AS nomor FROM rak";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next() && rs.getString("nomor") != null) {
                int nomor = Integer.parseInt(rs.getString("nomor")) + 1;
                String kode = String.format("RK%03d", nomor);
                txtkode.setText(kode);
                txtkode1.setText(kode);
            } else {
                txtkode.setText("RK001");
                txtkode1.setText("RK001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal generate ID: " + e.getMessage());
        }
    }

    private void setupTable() {
     tblrak.setFont(new Font("SansSerif", Font.PLAIN, 14));
     tblrak.setRowHeight(30);

     // Header styling
     JTableHeader header = tblrak.getTableHeader();
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
     int columnCount = tblrak.getColumnModel().getColumnCount();
     for (int i = 0; i < columnCount; i++) {
         tblrak.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
     }

     // Optional: garis grid halus
     tblrak.setShowHorizontalLines(true);
     tblrak.setShowVerticalLines(true);
     tblrak.setGridColor(new Color(220, 220, 220));
 }

    protected void aktif() {
        txtkode.requestFocus();
        txtkode1.requestFocus();
        bhapus.setVisible(false);
        bbatalUbah.setVisible(false);
    }

    protected void kosong() {
        txtnm.setText("");
        txtket.setText("");
        cblokasi.setSelectedIndex(0);
        txtmax.setText("");

        txtnm1.setText("");
        txtket1.setText("");
        cblokasi1.setSelectedIndex(0);
        txtmax1.setText("");
    }

    protected void datatable() {
        Object[] Baris = {"Kode Rak", "Nama Rak", "Lokasi", "Kapasitas Max", "Keterangan"};
        tabmode = new DefaultTableModel(null, Baris);
        tblrak.setModel(tabmode);

        String sql = "SELECT * FROM rak WHERE kode_rak LIKE ? OR nama_rak LIKE ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            String keyword = "%" + txtcari.getText() + "%";
            stat.setString(1, keyword);
            stat.setString(2, keyword);
            ResultSet hasil = stat.executeQuery();
            while (hasil.next()) {
                String[] data = {
                    hasil.getString("kode_rak"),
                    hasil.getString("nama_rak"),
                    hasil.getString("lokasi"),
                    hasil.getString("max"),
                    hasil.getString("keterangan")
                };
                tabmode.addRow(data);
            }
            setupTable(); // tambahkan ini agar styling aktif setelah update model
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
        bcari = new pallete.ButtonRounded();
        panelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblrak = new javax.swing.JTable();
        panelAdd = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bsimpan = new pallete.ButtonRounded();
        bbatal = new pallete.ButtonRounded();
        jLabel3 = new javax.swing.JLabel();
        txtkode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtnm = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtmax = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cblokasi = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtket = new javax.swing.JTextArea();
        panelUbah = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        bubah = new pallete.ButtonRounded();
        bbatal1 = new pallete.ButtonRounded();
        jLabel16 = new javax.swing.JLabel();
        txtkode1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtnm1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtmax1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cblokasi1 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtket1 = new javax.swing.JTextArea();

        setLayout(new java.awt.CardLayout());

        panel_main.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));
        panelView.setLayout(new java.awt.BorderLayout());

        panelAtas.setBackground(new java.awt.Color(255, 255, 255));
        panelAtas.setMinimumSize(new java.awt.Dimension(250, 250));

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel22.setText("Data Rak");

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

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/rak 70.png"))); // NOI18N

        jLabel24.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText("Master > Data Rak");

        txtcari.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

        bcari.setForeground(new java.awt.Color(0, 0, 0));
        bcari.setText("CARI");
        bcari.setFillClick(new java.awt.Color(143, 169, 152));
        bcari.setFillOriginal(new java.awt.Color(169, 225, 144));
        bcari.setFillOver(new java.awt.Color(169, 225, 144));
        bcari.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAtasLayout = new javax.swing.GroupLayout(panelAtas);
        panelAtas.setLayout(panelAtasLayout);
        panelAtasLayout.setHorizontalGroup(
            panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtasLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelAtasLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btambah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bbatalUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 439, Short.MAX_VALUE)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAtasLayout.createSequentialGroup()
                    .addGap(54, 54, 54)
                    .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelAtasLayout.createSequentialGroup()
                            .addGap(76, 76, 76)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(987, Short.MAX_VALUE)))
        );
        panelAtasLayout.setVerticalGroup(
            panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtasLayout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btambah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatalUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAtasLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(40, 40, 40)
                    .addComponent(jLabel22)
                    .addContainerGap(107, Short.MAX_VALUE)))
        );

        panelView.add(panelAtas, java.awt.BorderLayout.NORTH);

        panelTable.setBackground(new java.awt.Color(255, 255, 255));

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
        jScrollPane1.setViewportView(tblrak);

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
        jLabel2.setText("Tambahkan Rak");

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
        jLabel3.setText("Kode Rak");

        txtkode.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtkode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("Nama Rak");

        txtnm.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setText("Kapasitas Max");

        txtmax.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtmax.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setText("Keterangan");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setText("Lokasi");

        cblokasi.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cblokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Blok A", "Blok B", "Blok C", "Blok D", "Blok E", "Blok F", "Blok G", "Blok H", "Blok I", "Blok J" }));
        cblokasi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/rak 70.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("Master > Data Rak > Tambahkan Rak");

        txtket.setColumns(20);
        txtket.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtket.setRows(5);
        txtket.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane3.setViewportView(txtket);

        javax.swing.GroupLayout panelAddLayout = new javax.swing.GroupLayout(panelAdd);
        panelAdd.setLayout(panelAddLayout);
        panelAddLayout.setHorizontalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addGap(0, 1133, Short.MAX_VALUE))
            .addGroup(panelAddLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(1161, Short.MAX_VALUE))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(txtkode)
                            .addComponent(txtnm)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(cblokasi, 0, 323, Short.MAX_VALUE)
                            .addComponent(txtmax))
                        .addGap(346, 346, 346))))
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
                .addGap(40, 40, 40)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtkode, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(cblokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtmax, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        panel_main.add(panelAdd, "card2");

        panelUbah.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel7.setText("Ubah Data Rak");

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

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel16.setText("Kode Rak");

        txtkode1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtkode1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel19.setText("Nama Rak");

        txtnm1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel25.setText("Kapasitas Max");

        txtmax1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtmax1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel26.setText("Keterangan");

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel27.setText("Lokasi");

        cblokasi1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cblokasi1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Blok A", "Blok B", "Blok C", "Blok D", "Blok E", "Blok F", "Blok G", "Blok H", "Blok I", "Blok J" }));
        cblokasi1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/rak 70.png"))); // NOI18N

        jLabel29.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(153, 153, 153));
        jLabel29.setText("Master > Data Rak > Ubah Data Rak");

        txtket1.setColumns(20);
        txtket1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtket1.setRows(5);
        txtket1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane4.setViewportView(txtket1);

        javax.swing.GroupLayout panelUbahLayout = new javax.swing.GroupLayout(panelUbah);
        panelUbah.setLayout(panelUbahLayout);
        panelUbahLayout.setHorizontalGroup(
            panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addGap(0, 1156, Short.MAX_VALUE))
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bbatal1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(1161, Short.MAX_VALUE))
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19)
                            .addComponent(jLabel26)
                            .addComponent(txtkode1)
                            .addComponent(txtnm1)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27)
                            .addComponent(cblokasi1, 0, 323, Short.MAX_VALUE)
                            .addComponent(txtmax1))
                        .addGap(346, 346, 346))))
        );
        panelUbahLayout.setVerticalGroup(
            panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel29)
                .addGap(34, 34, 34)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28))
                .addGap(40, 40, 40)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtkode1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(txtnm1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(cblokasi1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(txtmax1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        panel_main.add(panelUbah, "card2");

        add(panel_main, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        panel_main.removeAll();
        panel_main.add(panelView);
        panel_main.repaint();
        panel_main.revalidate();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        // Cek apakah semua input sudah diisi
        if (txtnm.getText().isEmpty() || 
            cblokasi.getSelectedIndex() == -1 ||  
            txtket.getText().isEmpty() || 
            txtmax.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Query SQL insert
        String sql = "INSERT INTO rak (kode_rak, nama_rak, lokasi, max, keterangan) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtkode.getText());
            stat.setString(2, txtnm.getText());
            stat.setString(3, cblokasi.getSelectedItem().toString());
            stat.setString(4, txtmax.getText());
            stat.setString(5, txtket.getText());
            

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            kosong();               // reset form
            generateIDRak();      // generate ID baru
            txtkode.requestFocus();   // fokus ke ID
            datatable();            // refresh tabel
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
            int ok = JOptionPane.showConfirmDialog(null, "Ingin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (ok == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM rak WHERE kode_rak = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtkode1.getText()); // gunakan ID dari panelUbah
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            kosong();        // reset form
            datatable(); 
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
        btambah.setText("TAMBAH");
    }//GEN-LAST:event_bbatalUbahActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
        String sql = "UPDATE rak SET nama_rak=?, lokasi=?, max=?, keterangan=? WHERE kode_rak=?";
        if (txtnm1.getText().isEmpty() || 
            cblokasi1.getSelectedIndex() == -1 ||  
            txtmax1.getText().isEmpty() || 
            txtket1.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtnm1.getText());
                stat.setString(2, cblokasi1.getSelectedItem().toString());
                stat.setString(3, txtmax1.getText());
                stat.setString(4, txtket1.getText());
                stat.setString(5, txtkode1.getText());

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");

                kosong();
                generateIDRak();
                txtkode1.requestFocus();
                datatable();

                // Ganti tampilan kembali ke panelView
                panel_main.removeAll();
                panel_main.add(panelView);
                panel_main.repaint();
                panel_main.revalidate();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data gagal diubah: " + e.getMessage());
            }
    }//GEN-LAST:event_bubahActionPerformed

    private void bbatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatal1ActionPerformed
        panel_main.removeAll();
        panel_main.add(panelView);
        panel_main.repaint();
        panel_main.revalidate();
    }//GEN-LAST:event_bbatal1ActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        datatable();
    }//GEN-LAST:event_bcariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pallete.ButtonRounded bbatal;
    private pallete.ButtonRounded bbatal1;
    private pallete.ButtonRounded bbatalUbah;
    private pallete.ButtonRounded bcari;
    private pallete.ButtonRounded bhapus;
    private pallete.ButtonRounded bsimpan;
    private pallete.ButtonRounded btambah;
    private pallete.ButtonRounded bubah;
    private javax.swing.JComboBox<String> cblokasi;
    private javax.swing.JComboBox<String> cblokasi1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel panelAdd;
    private javax.swing.JPanel panelAtas;
    private javax.swing.JPanel panelTable;
    private javax.swing.JPanel panelUbah;
    private javax.swing.JPanel panelView;
    private javax.swing.JPanel panel_main;
    private javax.swing.JTable tblrak;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextArea txtket;
    private javax.swing.JTextArea txtket1;
    private javax.swing.JTextField txtkode;
    private javax.swing.JTextField txtkode1;
    private javax.swing.JTextField txtmax;
    private javax.swing.JTextField txtmax1;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtnm1;
    // End of variables declaration//GEN-END:variables
}
