package Ögrenci_Paneli;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import VeriTabanı_Baglantısı.veribaglanti;  
import Ögrenci_Paneli.*; 

public class Duyurular extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Duyurular frame = new Duyurular();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Duyurular() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1361, 658);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Table setup
        String[] columnNames = {"Başlık", "Tarih", "İçerik", "Okundu"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setBackground(new Color(102, 204, 255));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Veritabanından veri çekme ve tabloyu doldurma
        loadDataFromDatabase(model);

        // Butonları alt alta yerleştirmek için panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));  // Y-axis (dikey) düzenleme
        contentPane.add(buttonPanel, BorderLayout.EAST);  // Buton panelini sağa yerleştiriyoruz

        // İçeriği görüntüle butonu
        JButton btnGoruntule = new JButton("Görüntüle");
        btnGoruntule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String content = (String) table.getValueAt(selectedRow, 2);
                    openContentWindow(content);
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir duyuru seçin.");
                }
            }
        });
        buttonPanel.add(btnGoruntule);  // Butonu panelin içine ekliyoruz

        // Geri Butonu
        JButton btnGeri = new JButton("Geri");
        btnGeri.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        btnGeri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Burada Ögrenci_Paneli ekranını başlatıyoruz
                dispose(); // Şu anki pencereyi kapatır
                Ögr_Anasayfa panel = new Ögr_Anasayfa();  // Öğrenci Paneli'ni başlat
                panel.setVisible(true);  // Öğrenci Paneli ekranını göster
            }
        });
        buttonPanel.add(btnGeri);  // Geri butonunu panelin içine ekliyoruz

        // Checkbox to mark as read
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()));
    }

    // Veritabanından veri çekme metodu
    private void loadDataFromDatabase(DefaultTableModel model) {
        // MSSQL bağlantı parametrelerini ayarlayın
        String url = "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;encrypt=true;trustServerCertificate=true"; // Veritabanı URL
        String user = "sa"; // Veritabanı kullanıcı adı (genellikle 'sa' root kullanıcıdır)
        String password = "12345"; // Veritabanı şifresi

        // Veritabanı bağlantısı ve veri çekme
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT baslik, tarih, icerik FROM duyurular"; // Veri çekeceğiniz SQL sorgusu
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Verileri tabloya ekleme
            while (rs.next()) {
                String baslik = rs.getString("baslik");
                String tarih = rs.getString("tarih");
                String icerik = rs.getString("icerik");

                Object[] row = {baslik, tarih, icerik, false};  // Okundu sütununu false olarak ekliyoruz
                model.addRow(row); // Tabloya satır ekleme
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı kurulamadı: " + e.getMessage());
        }
    }

    // İçeriği gösterecek ikinci pencereyi açan metot
    private void openContentWindow(String content) {
        JFrame contentFrame = new JFrame("İçerik Görüntüle");
        contentFrame.setBounds(200, 200, 600, 400);
        contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setText(content);
        textArea.setEditable(false);  // İçeriğin düzenlenebilir olmamasını sağlar
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentFrame.getContentPane().add(scrollPane);

        contentFrame.setVisible(true);
    }
}
