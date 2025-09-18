/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pertemuankelima;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DataKomik1 extends JFrame {

    private JTextField tfId, tfJudul, tfPengarang, tfTahun, tfGenre;
    private JTable tabelKomik;
    private Connection conn;

    public DataKomik1() {
        setTitle("TOKO KOMIK JADOEL");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ======== Panel Atas (Logo + Judul) ========
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(45, 52, 54));

        // Logo (taruh file logo.png di folder project)
        JLabel logo = new JLabel(new ImageIcon("logo.png"));
        JLabel title = new JLabel("TOKO KOMIK JADOEL");
        title.setFont(new Font("Verdana", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        headerPanel.add(logo);
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        // ======== Panel Input Form ========
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 8, 8));
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Komik"));
        panelForm.setBackground(new Color(245, 245, 245));

        panelForm.add(new JLabel("ID:"));
        tfId = new JTextField();
        panelForm.add(tfId);

        panelForm.add(new JLabel("Judul:"));
        tfJudul = new JTextField();
        panelForm.add(tfJudul);

        panelForm.add(new JLabel("Pengarang:"));
        tfPengarang = new JTextField();
        panelForm.add(tfPengarang);

        panelForm.add(new JLabel("Tahun:"));
        tfTahun = new JTextField();
        panelForm.add(tfTahun);

        panelForm.add(new JLabel("Genre:"));
        tfGenre = new JTextField();
        panelForm.add(tfGenre);

        add(panelForm, BorderLayout.WEST);

        // ======== Panel Tombol ========
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButton.setBackground(new Color(220, 220, 220));

        JButton btnInsert = new JButton("Insert");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        // kasih warna tombol
        btnInsert.setBackground(new Color(46, 204, 113));
        btnInsert.setForeground(Color.WHITE);

        btnUpdate.setBackground(new Color(52, 152, 219));
        btnUpdate.setForeground(Color.WHITE);

        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);

        btnClear.setBackground(new Color(241, 196, 15));
        btnClear.setForeground(Color.BLACK);

        panelButton.add(btnInsert);
        panelButton.add(btnUpdate);
        panelButton.add(btnDelete);
        panelButton.add(btnClear);

        add(panelButton, BorderLayout.SOUTH);

        // ======== Tabel ========
        tabelKomik = new JTable();
        tabelKomik.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelKomik.setRowHeight(25);

        JTableHeader header = tabelKomik.getTableHeader();
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tabelKomik);
        add(scrollPane, BorderLayout.CENTER);

        // ======== Event Klik Tabel ========
        tabelKomik.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabelKomik.getSelectedRow();
                if (row != -1) {
                    tfId.setText(tabelKomik.getValueAt(row, 0).toString());
                    tfJudul.setText(tabelKomik.getValueAt(row, 1).toString());
                    tfPengarang.setText(tabelKomik.getValueAt(row, 2).toString());
                    tfTahun.setText(tabelKomik.getValueAt(row, 3).toString());
                    tfGenre.setText(tabelKomik.getValueAt(row, 4).toString());
                }
            }
        });

        // ======== Event Tombol ========
        btnInsert.addActionListener(e -> insertData());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());
        btnClear.addActionListener(e -> clearForm());

        // ======== Koneksi DB ========
        connectAndLoad();
    }

    private void connectAndLoad() {
        try {
            String url = "jdbc:postgresql://localhost:5432/KuliahPBO";
            String user = "postgres";
            String pass = "170206";
            conn = DriverManager.getConnection(url, user, pass);
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Koneksi gagal: " + e.getMessage());
        }
    }

    private void loadData() {
        String sql = "SELECT id, judul, pengarang, tahun, genre FROM toko_komik_jadoel";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            // Menampilkan data langsung ke tabel
            tabelKomik.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void insertData() {
        // Validasi input
        if (tfId.getText().isEmpty() || tfJudul.getText().isEmpty() || tfPengarang.getText().isEmpty() || tfTahun.getText().isEmpty() || tfGenre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi semua data!");
            return;
        }

        try {
            String sql = "INSERT INTO toko_komik_jadoel (id, judul, pengarang, tahun, genre) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(tfId.getText()));
                ps.setString(2, tfJudul.getText());
                ps.setString(3, tfPengarang.getText());
                ps.setInt(4, Integer.parseInt(tfTahun.getText()));
                ps.setString(5, tfGenre.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID dan Tahun harus berupa angka!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal insert: " + e.getMessage());
        }
        loadData();
        clearForm();
    }

    private void updateData() {
        // Validasi input
        if (tfId.getText().isEmpty() || tfJudul.getText().isEmpty() || tfPengarang.getText().isEmpty() || tfTahun.getText().isEmpty() || tfGenre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi semua data!");
            return;
        }

        try {
            String sql = "UPDATE toko_komik_jadoel SET judul=?, pengarang=?, tahun=?, genre=? WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, tfJudul.getText());
                ps.setString(2, tfPengarang.getText());
                ps.setInt(3, Integer.parseInt(tfTahun.getText()));
                ps.setString(4, tfGenre.getText());
                ps.setInt(5, Integer.parseInt(tfId.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID dan Tahun harus berupa angka!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
        }
        loadData();
        clearForm();
    }

    private void deleteData() {
        // Validasi input
        if (tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID harus diisi untuk menghapus data!");
            return;
        }

        try {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String sql = "DELETE FROM toko_komik_jadoel WHERE id=?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, Integer.parseInt(tfId.getText()));
                    int affectedRows = ps.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                    } else {
                        JOptionPane.showMessageDialog(this, "ID tidak ditemukan.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID harus berupa angka!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal delete: " + e.getMessage());
        }
        loadData();
        clearForm();
    }

    private void clearForm() {
        tfId.setText("");
        tfJudul.setText("");
        tfPengarang.setText("");
        tfTahun.setText("");
        tfGenre.setText("");
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new DataKomik1().setVisible(true));
//    }
//}import javax.swing.*;
//import javax.swing.table.JTableHeader;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//
//public class DataKomik1 extends JFrame {
//
//    private JTextField tfId, tfJudul, tfPengarang, tfTahun, tfGenre;
//    private JTable tabelKomik;
//    private Connection conn;
//
//    public DataKomik1() {
//        setTitle("TOKO KOMIK JADOEL");
//        setSize(950, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout(10, 10));
//
//        // ======== Panel Atas (Logo + Judul) ========
//        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        headerPanel.setBackground(new Color(45, 52, 54));
//
//        // Logo (taruh file logo.png di folder project)
//        JLabel logo = new JLabel(new ImageIcon("logo.png"));
//        JLabel title = new JLabel("TOKO KOMIK JADOEL");
//        title.setFont(new Font("Verdana", Font.BOLD, 26));
//        title.setForeground(Color.WHITE);
//
//        headerPanel.add(logo);
//        headerPanel.add(title);
//        add(headerPanel, BorderLayout.NORTH);
//
//        // ======== Panel Input Form ========
//        JPanel panelForm = new JPanel(new GridLayout(5, 2, 8, 8));
//        panelForm.setBorder(BorderFactory.createTitledBorder("Form Komik"));
//        panelForm.setBackground(new Color(245, 245, 245));
//
//        panelForm.add(new JLabel("ID:"));
//        tfId = new JTextField();
//        panelForm.add(tfId);
//
//        panelForm.add(new JLabel("Judul:"));
//        tfJudul = new JTextField();
//        panelForm.add(tfJudul);
//
//        panelForm.add(new JLabel("Pengarang:"));
//        tfPengarang = new JTextField();
//        panelForm.add(tfPengarang);
//
//        panelForm.add(new JLabel("Tahun:"));
//        tfTahun = new JTextField();
//        panelForm.add(tfTahun);
//
//        panelForm.add(new JLabel("Genre:"));
//        tfGenre = new JTextField();
//        panelForm.add(tfGenre);
//
//        add(panelForm, BorderLayout.WEST);
//
//        // ======== Panel Tombol ========
//        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//        panelButton.setBackground(new Color(220, 220, 220));
//
//        JButton btnInsert = new JButton("Insert");
//        JButton btnUpdate = new JButton("Update");
//        JButton btnDelete = new JButton("Delete");
//        JButton btnClear = new JButton("Clear");
//
//        // kasih warna tombol
//        btnInsert.setBackground(new Color(46, 204, 113));
//        btnInsert.setForeground(Color.WHITE);
//
//        btnUpdate.setBackground(new Color(52, 152, 219));
//        btnUpdate.setForeground(Color.WHITE);
//
//        btnDelete.setBackground(new Color(231, 76, 60));
//        btnDelete.setForeground(Color.WHITE);
//
//        btnClear.setBackground(new Color(241, 196, 15));
//        btnClear.setForeground(Color.BLACK);
//
//        panelButton.add(btnInsert);
//        panelButton.add(btnUpdate);
//        panelButton.add(btnDelete);
//        panelButton.add(btnClear);
//
//        add(panelButton, BorderLayout.SOUTH);
//
//        // ======== Tabel ========
//        tabelKomik = new JTable();
//        tabelKomik.setFont(new Font("Arial", Font.PLAIN, 14));
//        tabelKomik.setRowHeight(25);
//
//        JTableHeader header = tabelKomik.getTableHeader();
//        header.setBackground(new Color(52, 73, 94));
//        header.setForeground(Color.WHITE);
//        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
//
//        JScrollPane scrollPane = new JScrollPane(tabelKomik);
//        add(scrollPane, BorderLayout.CENTER);
//
//        // ======== Event Klik Tabel ========
//        tabelKomik.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                int row = tabelKomik.getSelectedRow();
//                if (row != -1) {
//                    tfId.setText(tabelKomik.getValueAt(row, 0).toString());
//                    tfJudul.setText(tabelKomik.getValueAt(row, 1).toString());
//                    tfPengarang.setText(tabelKomik.getValueAt(row, 2).toString());
//                    tfTahun.setText(tabelKomik.getValueAt(row, 3).toString());
//                    tfGenre.setText(tabelKomik.getValueAt(row, 4).toString());
//                }
//            }
//        });
//
//        // ======== Event Tombol ========
//        btnInsert.addActionListener(e -> insertData());
//        btnUpdate.addActionListener(e -> updateData());
//        btnDelete.addActionListener(e -> deleteData());
//        btnClear.addActionListener(e -> clearForm());
//
//        // ======== Koneksi DB ========
//        connectAndLoad();
//    }
//
//    private void connectAndLoad() {
//        try {
//            String url = "jdbc:postgresql://localhost:5432/KuliahPBO";
//            String user = "postgres";
//            String pass = "170206";
//            conn = DriverManager.getConnection(url, user, pass);
//            loadData();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Koneksi gagal: " + e.getMessage());
//        }
//    }
//
//    private void loadData() {
//        String sql = "SELECT id, judul, pengarang, tahun, genre FROM toko_komik_jadoel";
//        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//
//            // ambil data biasa dulu
//            DefaultTableModel model = (DefaultTableModel) DbUtils.resultSetToTableModel(rs);
//
//            // Tambahkan kolom untuk gambar sampul
//            model.addColumn("Sampul");
//
//            // Misal semua data ada di folder "img/" dengan nama file sesuai id
//            for (int i = 0; i < model.getRowCount(); i++) {
//                String id = model.getValueAt(i, 0).toString();
//                String path = "img/" + id + ".jpg"; // contoh: img/1.jpg
//
//                ImageIcon icon = new ImageIcon(new ImageIcon(path)
//                        .getImage()
//                        .getScaledInstance(60, 80, Image.SCALE_SMOOTH));
//
//                model.setValueAt(icon, i, 5);
//            }
//
//            tabelKomik.setModel(model);
//
//            // Renderer supaya gambar bisa tampil di tabel
//            tabelKomik.setRowHeight(80);
//            tabelKomik.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
//                @Override
//                public Component getTableCellRendererComponent(JTable table, Object value,
//                        boolean isSelected, boolean hasFocus,
//                        int row, int column) {
//                    if (value instanceof ImageIcon) {
//                        return new JLabel((ImageIcon) value);
//                    }
//                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                }
//            });
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
//        }
//    }
//
//    private void insertData() {
//        try {
//            String sql = "INSERT INTO toko_komik_jadoel (id, judul, pengarang, tahun, genre) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, Integer.parseInt(tfId.getText()));
//            ps.setString(2, tfJudul.getText());
//            ps.setString(3, tfPengarang.getText());
//            ps.setInt(4, Integer.parseInt(tfTahun.getText()));
//            ps.setString(5, tfGenre.getText());
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
//            loadData();
//            clearForm();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Gagal insert: " + e.getMessage());
//        }
//    }
//
//    private void updateData() {
//        try {
//            String sql = "UPDATE toko_komik_jadoel SET judul=?, pengarang=?, tahun=?, genre=? WHERE id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, tfJudul.getText());
//            ps.setString(2, tfPengarang.getText());
//            ps.setInt(3, Integer.parseInt(tfTahun.getText()));
//            ps.setString(4, tfGenre.getText());
//            ps.setInt(5, Integer.parseInt(tfId.getText()));
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
//            loadData();
//            clearForm();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Gagal update: " + e.getMessage());
//        }
//    }
//
//    private void deleteData() {
//        try {
//            String sql = "DELETE FROM toko_komik_jadoel WHERE id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, Integer.parseInt(tfId.getText()));
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
//            loadData();
//            clearForm();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Gagal delete: " + e.getMessage());
//        }
//    }
//
//    private void clearForm() {
//        tfId.setText("");
//        tfJudul.setText("");
//        tfPengarang.setText("");
//        tfTahun.setText("");
//        tfGenre.setText("");
//    }
//
//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(DataKomik1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataKomik1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataKomik1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataKomik1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        SwingUtilities.invokeLater(() -> new DataKomik1().setVisible(true));

    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

