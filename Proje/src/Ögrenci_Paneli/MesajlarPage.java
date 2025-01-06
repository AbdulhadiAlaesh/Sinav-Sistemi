package Ögrenci_Paneli;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MesajlarPage extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private static String ogrenciAdi;

    // Constructor: Öğrenci adını alarak başlatır
    public MesajlarPage(String ogrenciAdi) {
        this.ogrenciAdi = ogrenciAdi;

        setTitle("Mesajlar Sayfası - " + ogrenciAdi);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1226, 672);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(102, 255, 255));
        setContentPane(contentPane);

        // Üst panel: Geri git ve tablo boyutlarını değiştir düğmeleri
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(51, 255, 255));
        topPanel.setBounds(0, 0, 1196, 31);
        
        JButton btnBack = new JButton("Geri Git");
        btnBack.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Iconsmind-Outline-Back-2-2.24.png"));
        btnBack.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
        btnBack.setBounds(5, 5, 131, 26);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Mevcut pencereyi kapat
                // Geri gitmek için ana menü veya önceki pencereyi burada açabilirsiniz
               Ögr_Anasayfa öGAnasayfa = new Ögr_Anasayfa();
               öGAnasayfa.setVisible(true);
               setVisible(false);
            }
        });
        contentPane.setLayout(null);
        topPanel.setLayout(null);
        topPanel.add(btnBack);

        contentPane.add(topPanel);

        // Tabloyu oluştur
        String[] columnNames = {"Ders Adı", "Tarih", "Mesaj"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 41, 1187, 580);
        contentPane.add(scrollPane);

        // Veritabanından verileri çek
        fetchMessagesFromDatabase(model);
    }

    private void fetchMessagesFromDatabase(DefaultTableModel model) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "12345";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Öğrenci adı ile filtrelenmiş mesajları getir
            String query = "SELECT ders_adi, tarih, mesaj FROM ogrenci_mesaj WHERE ogrenci_adi = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ogrenciAdi); // Giriş yapan öğrencinin adını sorguya ekle
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String dersAdi = rs.getString("ders_adi");
                String tarih = rs.getString("tarih");
                String mesaj = rs.getString("mesaj");

                // Tablo modeline satır ekle
                model.addRow(new Object[]{dersAdi, tarih, mesaj});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı kurulamadı: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Örnek: Giriş yapan kullanıcının adı "AhmetYilmaz"
                MesajlarPage frame = new MesajlarPage(ogrenciAdi); // Kullanıcının adını buradan geçirin
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
