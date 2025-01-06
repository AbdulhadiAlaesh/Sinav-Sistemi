package dönem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import VeriTabanı_Baglantısı.veribaglanti;
import java.awt.Font;

public class Sonuc_Takibi extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model; // Burada model'ı sınıf genelinde tanımlıyoruz.

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Sonuc_Takibi frame = new Sonuc_Takibi();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Sonuc_Takibi() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400); // Yükseklik ve genişlik artırıldı
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Tabloyu oluşturuyoruz
        String[] columnNames = {"Öğrenci Adı", "Sınav Adı", "Cevaplar", "Not"}; // Kolon başlıkları
        model = new DefaultTableModel(); // model'ı burada bir kez tanımlıyoruz.
        model.setColumnIdentifiers(columnNames);

        // JTable nesnesini oluşturuyoruz
        table = new JTable(model);
        table.setFillsViewportHeight(true); // JTable'ın paneli doldurmasını sağlıyoruz

        // Scroll Paneli ekliyoruz
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 560, 300); // Scroll panelin boyutları
        contentPane.add(scrollPane);

        // Aç butonunu oluşturuyoruz
        JButton btnAç = new JButton("Aç");
        btnAç.setBackground(new Color(255, 255, 255));
        btnAç.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
        btnAç.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Umut-Pulat-Tulliana-2-Korganizer.48.png"));
        btnAç.setBounds(10, 320, 100, 30);
        contentPane.add(btnAç);

        btnAç.addActionListener(e -> {
            model.setRowCount(0); // Tablodaki önceki verileri temizliyoruz

            try {
                // Veritabanı bağlantısını alıyoruz
                Connection con = veribaglanti.getConnection();

                // Sınav, cevaplar ve öğrenci adı bilgilerini almak için sorguyu güncelliyoruz
                String query = "SELECT o.KullaniciAdi, sn.ders_adi, s.cevaplar " +
                               "FROM Sinavlar sn " +
                               "LEFT JOIN sinav_cevaplari s ON sn.sinav_id = s.sinav_id " +
                               "LEFT JOIN Ogrenci o ON s.ogrenci_id = o.OgrenciID";

                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    String ogrenciAdi = rs.getString("KullaniciAdi");  // Öğrenci adı alınıyor
                    String dersAdi = rs.getString("ders_adi");  // Ders adı alınıyor
                    String cevaplar = rs.getString("cevaplar");  // Cevaplar alınıyor

                    // Veri tablosuna ekleme yapıyoruz
                    model.addRow(new Object[]{ogrenciAdi, dersAdi, cevaplar, ""});
                }

                con.close(); // Bağlantıyı kapatıyoruz

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Veritabanı bağlantısı hatalı veya veri çekilemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // Kaydet butonunu oluşturuyoruz
        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setBackground(new Color(255, 255, 255));
        btnKaydet.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
        btnKaydet.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Custom-Icon-Design-Pretty-Office-9-Open-file.32 (1).png"));
        btnKaydet.setBounds(120, 320, 116, 30);
        contentPane.add(btnKaydet);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Iconsmind-Outline-Back-2-2.24.png"));
        btnNewButton.setBounds(246, 323, 94, 27);
        contentPane.add(btnNewButton);
        
        btnNewButton.addActionListener(new ActionListener() {
     		
     		@Override
     		public void actionPerformed(ActionEvent e) {
     			// TODO Auto-generated method stub
     			HocaPanel hocaPanel = new HocaPanel();
     			
     			hocaPanel.setVisible(true);
     			setVisible(false);
     		}
     	});
        
        btnKaydet.addActionListener(e -> {
            // Tablodaki her satır için işlem yapıyoruz
            for (int row = 0; row < table.getRowCount(); row++) {
                String ogrenciAdi = (String) table.getValueAt(row, 0); // Öğrenci adı
                String dersAdi = (String) table.getValueAt(row, 1);   // Ders adı (Sınav ismi)
                String cevaplar = (String) table.getValueAt(row, 2);  // Cevaplar
                String notStr = (String) table.getValueAt(row, 3);    // Not

                // Eğer not değeri girilmemişse uyarı ver
                if (notStr == null || notStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Tüm satırlar için not girilmelidir!", "Eksik Not", JOptionPane.ERROR_MESSAGE);
                    return; // İşlemi sonlandırıyoruz
                }

                try {
                    int notDegeri = Integer.parseInt(notStr); // Notu tam sayı olarak parse ediyoruz

                    // Veritabanında öğrenciyi ve sınavı bulmamız lazım
                    try (Connection con = veribaglanti.getConnection()) {
                        // Öğrenci ve sınav bilgilerini bulmak için sorgu
                    	String query = """
                    		    SELECT o.OgrenciID, s.sinav_id, sn.ders_adi
                    		    FROM Ogrenci o
                    		    JOIN sinav_cevaplari s ON o.OgrenciID = s.ogrenci_id
                    		    JOIN sinavlar sn ON s.sinav_id = sn.sinav_id
                    		    WHERE o.KullaniciAdi = ? AND sn.ders_adi = ?
                    		""";


                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1, ogrenciAdi); // Öğrenci adı
                        pst.setString(2, dersAdi);   // Ders adı (Sınav ismi)

                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
                            int ogrenciId = rs.getInt("OgrenciID");
                            int sinavId = rs.getInt("sinav_id");

                            // Sonuçları `SonucTakibi` tablosuna eklemek için sorgu
                            String insertQuery = """
                                INSERT INTO SonucTakibi (OgrenciID, SinavID, Cevaplar, NotDegeri, Ogrenci_Adi, Sinav_Adi) 
                                VALUES (?, ?, ?, ?, ?, ?)
                            """;

                            PreparedStatement insertPst = con.prepareStatement(insertQuery);
                            insertPst.setInt(1, ogrenciId);
                            insertPst.setInt(2, sinavId);
                            insertPst.setString(3, cevaplar);
                            insertPst.setInt(4, notDegeri);
                            insertPst.setString(5, ogrenciAdi);
                            insertPst.setString(6, dersAdi);

                            insertPst.executeUpdate();

                            JOptionPane.showMessageDialog(this, "Not başarıyla kaydedildi!", "Başarı", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Öğrenci veya sınav bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Veritabanı hatası!\n" + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Geçersiz not formatı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    
    
        
    }
}