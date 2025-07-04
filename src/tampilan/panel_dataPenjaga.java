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
public class panel_dataPenjaga extends javax.swing.JPanel {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    private void generateIDPenjaga() {
    try {
        String sql = "SELECT MAX(RIGHT(id_penjaga, 3)) AS nomor FROM penjaga";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next() && rs.getString("nomor") != null) {
            int nomor = Integer.parseInt(rs.getString("nomor")) + 1;
            String kode = String.format("PJ%03d", nomor);
            txtid.setText(kode);
            txtid1.setText(kode);
        } else {
            txtid.setText("PJ001");
            txtid1.setText("PJ001");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal generate ID: " + e.getMessage());
    }
}
    
  
    public panel_dataPenjaga() {
        initComponents();
        kosong();
        aktif();
        datatable();
        generateIDPenjaga();
        txtid.setEditable(false);
        txtid.setFocusable(false);
        txtid1.setEditable(false);
        txtid1.setFocusable(false);
        tbljaga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            int baris = tbljaga.getSelectedRow();
            if (baris != -1) {
                String id = tbljaga.getValueAt(baris, 0).toString();
                String nama = tbljaga.getValueAt(baris, 1).toString();
                String email = tbljaga.getValueAt(baris, 2).toString();
                String hp = tbljaga.getValueAt(baris, 3).toString();
                String alamat = tbljaga.getValueAt(baris, 4).toString();

                txtid1.setText(id);
                txtnm1.setText(nama);
                txtemail1.setText(email);
                txttelp1.setText(hp);
                txtalamat1.setText(alamat);

                
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
        private void setupTable() {
        tbljaga.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tbljaga.setRowHeight(30);

        // Header styling
        JTableHeader header = tbljaga.getTableHeader();
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
        int columnCount = tbljaga.getColumnModel().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            tbljaga.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Optional: garis grid halus
        tbljaga.setShowHorizontalLines(true);
        tbljaga.setShowVerticalLines(true);
        tbljaga.setGridColor(new Color(220, 220, 220));
        }
        protected void aktif() {
        txtid.requestFocus();
        txtid1.requestFocus();
        bhapus.setVisible(false);
        bbatalUbah.setVisible(false);
    }
        protected void kosong() {
        // Panel Add
        txtnm.setText("");
        txtemail.setText("");        
        txttelp.setText("");
        txtalamat.setText("");
        

        // Panel Ubah
        txtnm1.setText("");
        txtemail1.setText("");        
        txttelp1.setText("");
        txtalamat1.setText("");
    }
        protected void datatable() {
        Object[] Baris = {"ID Penjaga", "Nama Penjaga", "Email", "No. Hp", "Alamat"};
        tabmode = new DefaultTableModel(null, Baris);
        tbljaga.setModel(tabmode);

        String sql = "SELECT * FROM penjaga WHERE id_penjaga LIKE ? OR nama_penjaga LIKE ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            String keyword = "%" + txtcari.getText() + "%";
            stat.setString(1, keyword);
            stat.setString(2, keyword);
            ResultSet hasil = stat.executeQuery();
            while (hasil.next()) {
                String[] data = {
                    hasil.getString("id_penjaga"),
                    hasil.getString("nama_penjaga"),
                    hasil.getString("email"),
                    hasil.getString("no_hp"),
                    hasil.getString("alamat")
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
        tbljaga = new javax.swing.JTable();
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
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtalamat = new javax.swing.JTextArea();
        panelUbah = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        bubah = new pallete.ButtonRounded();
        bbatal2 = new pallete.ButtonRounded();
        jLabel7 = new javax.swing.JLabel();
        txtid1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtnm1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txttelp1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtemail1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtalamat1 = new javax.swing.JTextArea();

        setLayout(new java.awt.CardLayout());

        panel_main.setLayout(new java.awt.CardLayout());

        panelView.setBackground(new java.awt.Color(255, 255, 255));
        panelView.setLayout(new java.awt.BorderLayout());

        panelAtas.setBackground(new java.awt.Color(255, 255, 255));
        panelAtas.setMinimumSize(new java.awt.Dimension(250, 250));

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel22.setText("Data Penjaga");

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

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/staff 70.png"))); // NOI18N

        jLabel24.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText("Master > Data Penjaga");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 437, Short.MAX_VALUE)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(215, 215, 215))
            .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAtasLayout.createSequentialGroup()
                    .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAtasLayout.createSequentialGroup()
                            .addGap(54, 54, 54)
                            .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelAtasLayout.createSequentialGroup()
                                    .addComponent(jLabel23)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel22))))
                        .addGroup(panelAtasLayout.createSequentialGroup()
                            .addGap(1321, 1321, 1321)
                            .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(55, Short.MAX_VALUE)))
        );
        panelAtasLayout.setVerticalGroup(
            panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtasLayout.createSequentialGroup()
                .addContainerGap(178, Short.MAX_VALUE)
                .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btambah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatalUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAtasLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAtasLayout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(jLabel22))
                        .addGroup(panelAtasLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(35, 35, 35)
                    .addComponent(bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE)))
        );

        panelView.add(panelAtas, java.awt.BorderLayout.NORTH);

        panelTable.setBackground(new java.awt.Color(255, 255, 255));

        tbljaga.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbljaga);

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
        jLabel2.setText("Tambahkan Penjaga");

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
        jLabel3.setText("ID Penjaga");

        txtid.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("Nama Penjaga");

        txtnm.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setText("Nomor Telepon");

        txttelp.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txttelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setText("Alamat");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/staff 70.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("Master > Data Penjaga > Tambahkan Penjaga");

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel16.setText("Email");

        txtemail.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtemail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtalamat.setColumns(20);
        txtalamat.setRows(5);
        txtalamat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(txtalamat);

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
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtemail)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                                    .addComponent(txtnm, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(295, 295, 295))
                            .addGroup(panelAddLayout.createSequentialGroup()
                                .addGroup(panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel5)
                                    .addComponent(txttelp, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txttelp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
        );

        panel_main.add(panelAdd, "card2");

        panelUbah.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel6.setText("Ubah Data Penjaga");

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

        bbatal2.setForeground(new java.awt.Color(0, 0, 0));
        bbatal2.setText("BATAL");
        bbatal2.setFillClick(new java.awt.Color(143, 169, 152));
        bbatal2.setFillOriginal(new java.awt.Color(169, 225, 144));
        bbatal2.setFillOver(new java.awt.Color(169, 225, 144));
        bbatal2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        bbatal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatal2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText("ID Penjaga");

        txtid1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtid1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel19.setText("Nama Penjaga");

        txtnm1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnm1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel25.setText("Nomor Telepon");

        txttelp1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txttelp1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel26.setText("Alamat");

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/staff 70.png"))); // NOI18N

        jLabel28.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(153, 153, 153));
        jLabel28.setText("Master > Data Penjaga > Ubah Data Penjaga");

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel29.setText("Email");

        txtemail1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtemail1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtalamat1.setColumns(20);
        txtalamat1.setRows(5);
        txtalamat1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane3.setViewportView(txtalamat1);

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
                        .addComponent(bbatal2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtemail1)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtid1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                                    .addComponent(txtnm1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(295, 295, 295))
                            .addGroup(panelUbahLayout.createSequentialGroup()
                                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel25)
                                    .addComponent(txttelp1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelUbahLayout.setVerticalGroup(
            panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbahLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel28)
                .addGap(34, 34, 34)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bubah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(panelUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtid1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(txtnm1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbahLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(txtemail1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(txttelp1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
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
            txtemail.getText().isEmpty() ||  
            txttelp.getText().isEmpty() ||   
            txtalamat.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Query SQL insert
        String sql = "INSERT INTO penjaga (id_penjaga, nama_penjaga, email, no_hp, alamat) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtid.getText());
            stat.setString(2, txtnm.getText());
            stat.setString(3, txtemail.getText());
            stat.setString(4, txttelp.getText());
            stat.setString(5, txtalamat.getText());

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            kosong();               // reset form
            generateIDPenjaga();      // generate ID baru
            txtid.requestFocus();   // fokus ke ID
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
        String sql = "DELETE FROM penjaga WHERE id_penjaga = ?";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtid1.getText()); // gunakan ID dari panelUbah
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
        btambah.setText("TAMBAH");
    }//GEN-LAST:event_bbatalUbahActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void bcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakActionPerformed
       try{
        String path="./src/laporan/dataPenjaga.jasper";
        HashMap parameter = new HashMap();
        //parameter.put("id_nota",txtidnota.getText());
        JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);
        JasperViewer.viewReport(print, false);
            
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan: " + ex.getMessage());
        }
    }//GEN-LAST:event_bcetakActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
        String sql = "UPDATE penjaga SET nama_penjaga=?, email=?, no_hp=?, alamat=? WHERE id_penjaga=?";
        if (txtnm1.getText().isEmpty() || 
            txtemail1.getText().isEmpty() ||  
            txttelp1.getText().isEmpty() || 
            txtalamat1.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtnm1.getText());
                stat.setString(2, txtemail1.getText());
                stat.setString(3, txttelp1.getText());
                stat.setString(4, txtalamat1.getText());
                stat.setString(5, txtid1.getText());

                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");

                kosong();
                generateIDPenjaga();
                txtid1.requestFocus();
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

    private void bbatal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatal2ActionPerformed
        panel_main.removeAll();
        panel_main.add(panelView);
        panel_main.repaint();
        panel_main.revalidate();
    }//GEN-LAST:event_bbatal2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pallete.ButtonRounded bbatal;
    private pallete.ButtonRounded bbatal2;
    private pallete.ButtonRounded bbatalUbah;
    private pallete.ButtonRounded bcetak;
    private pallete.ButtonRounded bhapus;
    private pallete.ButtonRounded bsimpan;
    private pallete.ButtonRounded btambah;
    private pallete.ButtonRounded bubah;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelAdd;
    private javax.swing.JPanel panelAtas;
    private javax.swing.JPanel panelTable;
    private javax.swing.JPanel panelUbah;
    private javax.swing.JPanel panelView;
    private javax.swing.JPanel panel_main;
    private javax.swing.JTable tbljaga;
    private javax.swing.JTextArea txtalamat;
    private javax.swing.JTextArea txtalamat1;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtemail1;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtid1;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtnm1;
    private javax.swing.JTextField txttelp;
    private javax.swing.JTextField txttelp1;
    // End of variables declaration//GEN-END:variables
}
