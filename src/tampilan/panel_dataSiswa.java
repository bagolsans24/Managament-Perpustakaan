package tampilan;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
/**
 *
 * @author angge
 */
public class panel_dataSiswa extends javax.swing.JPanel {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;

    public panel_dataSiswa() {
        initComponents();
        setupTable();
        kosong("TAMBAH");
        aktif();
        datatable();
        generateIDSiswa();
        initEvent();
        txtid.setEditable(false);
        txtid.setFocusable(false);
        txtidU.setEditable(false);
        txtidU.setFocusable(false);
    }

    private void setupTable() {
    tblsw.setFont(new Font("SansSerif", Font.PLAIN, 14));
    tblsw.setRowHeight(30);

    // Header styling
    JTableHeader header = tblsw.getTableHeader();
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
    int columnCount = tblsw.getColumnModel().getColumnCount();
    for (int i = 0; i < columnCount; i++) {
        tblsw.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Optional: garis grid halus
    tblsw.setShowHorizontalLines(true);
    tblsw.setShowVerticalLines(true);
    tblsw.setGridColor(new Color(220, 220, 220));
}

    private void initEvent() {
        tblsw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int baris = tblsw.getSelectedRow();
                if (baris != -1) {
                    isiFormUbahDariTabel(baris);
                }
            }
        });

        txtcari.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { datatable(); }
            public void removeUpdate(DocumentEvent e) { datatable(); }
            public void changedUpdate(DocumentEvent e) { datatable(); }
        });
    }

    private void isiFormUbahDariTabel(int baris) {
        txtidU.setText(tblsw.getValueAt(baris, 0).toString());
        txtnmU.setText(tblsw.getValueAt(baris, 1).toString());
        String jk = tblsw.getValueAt(baris, 2).toString();
        cbjurusanU.setSelectedItem(tblsw.getValueAt(baris, 3).toString());
        cbkelasU.setSelectedItem(tblsw.getValueAt(baris, 4).toString());
        txttelpU.setText(tblsw.getValueAt(baris, 5).toString());

        if (jk.equalsIgnoreCase("Laki-laki")) {
            rlakiU.setSelected(true);
        } else {
            rperempuanU.setSelected(true);
        }

        btambah.setText("UBAH");
        bhapus.setVisible(true);
        bbatalUbah.setVisible(true);
    }

    protected void aktif() {
        txtid.setRequestFocusEnabled(true);
        txtidU.setRequestFocusEnabled(true);
        bhapus.setVisible(false);
        bbatalUbah.setVisible(false);
    }

  
    protected void kosong(String mode) {
        if (mode.equals("TAMBAH")) {
            txtnm.setText("");
            cbkelas.setSelectedIndex(0);
            cbjurusan.setSelectedIndex(0);
            txttelp.setText("");
            buttonGroup1.clearSelection();
            txtid.setText("");
        } else if (mode.equals("UBAH")) {
            txtidU.setText("");
            txtnmU.setText("");
            cbkelasU.setSelectedIndex(0);
            cbjurusanU.setSelectedIndex(0);
            txttelpU.setText("");
        }
    }

    private void generateIDSiswa() {
        try {
            String sql = "SELECT MAX(CAST(SUBSTRING(id_siswa, 2) AS UNSIGNED)) AS nomor FROM siswa";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next() && rs.getString("nomor") != null) {
                int nomor = Integer.parseInt(rs.getString("nomor")) + 1;
                String kode = String.format("S%03d", nomor);
                txtid.setText(kode);
                txtidU.setText(kode);
            } else {
                txtid.setText("S001");
                txtidU.setText("S001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal generate ID: " + e.getMessage());
        }
    }

    protected void datatable() {
        Object[] Baris = {"Id Siswa", "Nama Siswa", "Jenis Kelamin", "Jurusan", "Kelas", "No. Hp"};
        tabmode = new DefaultTableModel(null, Baris);
        tblsw.setModel(tabmode);

        String sql = "SELECT * FROM siswa WHERE id_siswa LIKE ? OR nama_siswa LIKE ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            String keyword = "%" + txtcari.getText() + "%";
            stat.setString(1, keyword);
            stat.setString(2, keyword);
            ResultSet hasil = stat.executeQuery();
            while (hasil.next()) {
                String[] data = {
                    hasil.getString("id_siswa"),
                    hasil.getString("nama_siswa"),
                    hasil.getString("jenis_klmn"),
                    hasil.getString("jurusan"),
                    hasil.getString("kelas"),
                    hasil.getString("no_hp")
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

        buttonGroup1 = new javax.swing.ButtonGroup();
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
        tblsw = new javax.swing.JTable();
        panelAdd = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bsimpan = new pallete.ButtonRounded();
        bbatal = new pallete.ButtonRounded();
        jLabel3 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtnm = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttelp = new javax.swing.JTextField();
        rlaki = new javax.swing.JCheckBox();
        rperempuan = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        cbjurusan = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbkelas = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        panelUbah = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        bubah = new pallete.ButtonRounded();
        bbatal1 = new pallete.ButtonRounded();
        jLabel10 = new javax.swing.JLabel();
        txtidU = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtnmU = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txttelpU = new javax.swing.JTextField();
        rlakiU = new javax.swing.JCheckBox();
        rperempuanU = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        cbjurusanU = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbkelasU = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        panel_main.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));
        panelView.setLayout(new java.awt.BorderLayout());

        panelAtas.setBackground(new java.awt.Color(255, 255, 255));
        panelAtas.setMinimumSize(new java.awt.Dimension(250, 250));

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel22.setText("Data Siswa");

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

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/siswa 70.png"))); // NOI18N

        jLabel24.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText("Master > Data Siswa");

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
                            .addGap(6, 6, 6)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tblsw.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblsw);

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
        jLabel2.setText("Tambahkan Siswa");

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
        jLabel3.setText("ID Siswa");

        txtid.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("Nama Siswa");

        txtnm.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setText("Nomor Telepon");

        txttelp.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txttelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroup1.add(rlaki);
        rlaki.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        rlaki.setText("LAKI-LAKI");

        buttonGroup1.add(rperempuan);
        rperempuan.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        rperempuan.setText("PEREMPUAN");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setText("Jenis Kelamin");

        cbjurusan.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbjurusan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AKUTANSI ", "MULTIMEDIA ", "TEKNIK KENDARAAN RINGAN ", "TEKNIK KOMPUTER DAN JARINGAN" }));
        cbjurusan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText("Jurusan");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setText("Kelas");

        cbkelas.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kelas 10", "Kelas 11", "Kelas 12" }));
        cbkelas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/siswa 70.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("Master > Data Siswa > Tambahkan Siswa");

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
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addComponent(rlaki)
                                .addGap(18, 18, 18)
                                .addComponent(rperempuan))
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(cbjurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtid, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                            .addComponent(txtnm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                        .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttelp, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(227, 227, 227))))
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
                .addGap(18, 18, 18)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAddLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txttelp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addComponent(jLabel6)
                .addGap(35, 35, 35)
                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rlaki)
                    .addComponent(rperempuan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(cbjurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        panel_main.add(panelAdd, "card2");

        panelUbah.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel9.setText("Ubah Data Siswa");

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
        jLabel10.setText("ID Siswa");

        txtidU.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidU.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel11.setText("Nama Siswa");

        txtnmU.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnmU.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel12.setText("Nomor Telepon");

        txttelpU.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txttelpU.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroup1.add(rlakiU);
        rlakiU.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        rlakiU.setText("LAKI-LAKI");

        buttonGroup1.add(rperempuanU);
        rperempuanU.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        rperempuanU.setText("PEREMPUAN");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel13.setText("Jenis Kelamin");

        cbjurusanU.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbjurusanU.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AKUTANSI ", "MULTIMEDIA ", "TEKNIK KENDARAAN RINGAN ", "TEKNIK KOMPUTER DAN JARINGAN" }));
        cbjurusanU.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel14.setText("Jurusan");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel15.setText("Kelas");

        cbkelasU.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbkelasU.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kelas 10", "Kelas 11", "Kelas 12" }));
        cbkelasU.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/siswa 70.png"))); // NOI18N

        jLabel21.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 153, 153));
        jLabel21.setText("Master > Data Siswa > Ubah Data Siswa");

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
                        .addComponent(bbatal1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addComponent(rlakiU)
                                .addGap(18, 18, 18)
                                .addComponent(rperempuanU))
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(cbjurusanU, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidU, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                            .addComponent(txtnmU))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15)
                            .addComponent(cbkelasU, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttelpU, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(227, 227, 227))))
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelUbahLayout.setVerticalGroup(
            panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel21)
                .addGap(28, 28, 28)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtidU, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtnmU, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(cbkelasU, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txttelpU, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addComponent(jLabel13)
                .addGap(35, 35, 35)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rlakiU)
                    .addComponent(rperempuanU, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(cbjurusanU, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
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
        // Ambil nilai jenis kelamin dari radio button
        String jk = rlaki.isSelected() ? "Laki-Laki" : rperempuan.isSelected() ? "Perempuan" : "";

        // Cek apakah semua input sudah diisi
        if (txtnm.getText().isEmpty() || 
            buttonGroup1.getSelection() == null ||  // radio button tidak dipilih
            cbjurusan.getSelectedIndex() == -1 ||  
            cbkelas.getSelectedIndex() == -1 || 
            txttelp.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Query SQL insert
        String sql = "INSERT INTO siswa (id_siswa, nama_siswa, jenis_klmn, jurusan, kelas, no_hp) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtid.getText());
            stat.setString(2, txtnm.getText());
            stat.setString(3, jk);
            stat.setString(4, cbjurusan.getSelectedItem().toString());
            stat.setString(5, cbkelas.getSelectedItem().toString());
            stat.setString(6, txttelp.getText());

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            kosong("TAMBAH");
            generateIDSiswa();      // generate ID baru
            txtid.requestFocus();   // fokus ke ID
            datatable();            // refresh tabel
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + e.getMessage());
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
            String jkU = rlakiU.isSelected() ? "Laki-Laki" : "Perempuan";
            String sql = "UPDATE siswa SET nama_siswa=?, jenis_klmn=?, jurusan=?, kelas=?, no_hp=? WHERE id_siswa=?";

            // Validasi input
            if (txtnmU.getText().isEmpty() || 
                buttonGroup1.getSelection() == null ||
                cbjurusanU.getSelectedIndex() == -1 ||  
                cbkelasU.getSelectedIndex() == -1 || 
                txttelpU.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return; // Hentikan proses jika ada input yang kosong
            }

            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtnmU.getText());
                stat.setString(2, jkU);
                stat.setString(3, cbjurusanU.getSelectedItem().toString());
                stat.setString(4, cbkelasU.getSelectedItem().toString());
                stat.setString(5, txttelpU.getText());
                stat.setString(6, txtidU.getText());

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");
                generateIDSiswa();
                txtid.requestFocus();
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
        String sql = "DELETE FROM siswa WHERE id_siswa = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtidU.getText()); // gunakan ID dari panelUbah
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
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
        btambah.setText("TAMBAH");
    }//GEN-LAST:event_bbatalUbahActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakActionPerformed
        try{
        String path="./src/laporan/dataSiswa.jasper";
        HashMap parameter = new HashMap();
        //parameter.put("id_nota",txtidnota.getText());
        JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);
        JasperViewer.viewReport(print, false);
            
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan: " + ex.getMessage());
        }
    }//GEN-LAST:event_bcetakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pallete.ButtonRounded bbatal;
    private pallete.ButtonRounded bbatal1;
    private pallete.ButtonRounded bbatalUbah;
    private pallete.ButtonRounded bcetak;
    private pallete.ButtonRounded bhapus;
    private pallete.ButtonRounded bsimpan;
    private pallete.ButtonRounded btambah;
    private pallete.ButtonRounded bubah;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbjurusan;
    private javax.swing.JComboBox<String> cbjurusanU;
    private javax.swing.JComboBox<String> cbkelas;
    private javax.swing.JComboBox<String> cbkelasU;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
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
    private javax.swing.JPanel panelUbah;
    private javax.swing.JPanel panelView;
    private javax.swing.JPanel panel_main;
    private javax.swing.JCheckBox rlaki;
    private javax.swing.JCheckBox rlakiU;
    private javax.swing.JCheckBox rperempuan;
    private javax.swing.JCheckBox rperempuanU;
    private javax.swing.JTable tblsw;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtidU;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtnmU;
    private javax.swing.JTextField txttelp;
    private javax.swing.JTextField txttelpU;
    // End of variables declaration//GEN-END:variables
}
