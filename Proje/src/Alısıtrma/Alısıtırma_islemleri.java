package Alısıtrma;

import javax.swing.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import dönem.HocaPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import VeriTabanı_Baglantısı.veribaglanti;

public class Alısıtırma_islemleri extends JFrame {
    private JPanel soruPaneli;
    private JTextField soruSayisiField;
    private JButton sorulariYayinlaButton, sinaviKaydetButton;
    private SoruYonetimi soruYonetimi;
    private JTextField dersAdiField; 
    private Connection conn;

    public Alısıtırma_islemleri() {
        soruYonetimi = new SoruYonetimi(); // Soru yönetim sistemi
        setTitle("Sınav Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setLayout(new BorderLayout());

        // Üst Panel
        JPanel ustPanel = new JPanel(new BorderLayout());
        JLabel soruSayisiLabel = new JLabel("Soru Sayısı: ");
        soruSayisiField = new JTextField(5);
        
        JLabel dersAdiLabel = new JLabel("Ders Adı: ");
        dersAdiField = new JTextField(20);
        
        sorulariYayinlaButton = new JButton("Soruları Yayınla");
        sorulariYayinlaButton.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Paomedia-Small-N-Flat-Post-it.24.png"));
        sinaviKaydetButton = new JButton("Sınavı Kaydet");
        sinaviKaydetButton.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Custom-Icon-Design-Pretty-Office-7-Save-as.24.png"));

        JPanel soruSayisiPanel = new JPanel();
        soruSayisiPanel.add(soruSayisiLabel);
        soruSayisiPanel.add(soruSayisiField);
        soruSayisiPanel.add(sorulariYayinlaButton);
        soruSayisiPanel.add(sinaviKaydetButton);
        soruSayisiPanel.add(dersAdiLabel); // Ders adı etiketi ekle
        soruSayisiPanel.add(dersAdiField);

        ustPanel.add(soruSayisiPanel, BorderLayout.CENTER);
        
        JButton geri_ntn_3 = new JButton("");
        geri_ntn_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		HocaPanel panelHoca = new HocaPanel();
        		panelHoca.setVisible(true);
        		setVisible(false);
        	}
         });
        
        geri_ntn_3.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Iconsmind-Outline-Back-2-2.24.png"));
        soruSayisiPanel.add(geri_ntn_3);
        getContentPane().add(ustPanel, BorderLayout.NORTH);

        // Soru Paneli
        soruPaneli = new JPanel();
        soruPaneli.setBackground(new Color(51, 204, 102));
        soruPaneli.setLayout(new BoxLayout(soruPaneli, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(soruPaneli);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Button actions
        sorulariYayinlaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorulariYayinla();
            }
        });

        sinaviKaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sinaviKaydet();
            }
        });
    }

    private void sorulariYayinla() {
        soruPaneli.removeAll(); // Önceki soruları temizle
        try {
            int soruSayisi = Integer.parseInt(soruSayisiField.getText());
            for (int i = 1; i <= soruSayisi; i++) {
                JPanel tekSoruPanel = new JPanel(new BorderLayout());
                tekSoruPanel.setBorder(BorderFactory.createTitledBorder("Soru " + i));

                // Soru içerik alanı
                JPanel ustPanel = new JPanel(new BorderLayout());
                JLabel soruLabel = new JLabel("Soru: ");
                JTextField soruMetniField = new JTextField("Soru " + i + " içeriğini buraya yazın.");
                ustPanel.add(soruLabel, BorderLayout.WEST);
                ustPanel.add(soruMetniField, BorderLayout.CENTER);
                tekSoruPanel.add(ustPanel, BorderLayout.NORTH);

                // Şıklar
                JPanel secenekPanel = new JPanel(new GridLayout(5, 1));
                JTextField[] secenekTextFields = new JTextField[5];

                for (int j = 0; j < 5; j++) {
                    JPanel secenekRow = new JPanel(new BorderLayout());
                    JTextField secenekTextField = new JTextField("Şık " + (char) ('A' + j));
                    secenekRow.add(new JLabel((char) ('A' + j) + ") "), BorderLayout.WEST);
                    secenekRow.add(secenekTextField, BorderLayout.CENTER);
                    secenekPanel.add(secenekRow);

                    secenekTextFields[j] = secenekTextField;
                }

                tekSoruPanel.add(secenekPanel, BorderLayout.CENTER);

                // Doğru şık seçimi
                JPanel dogruSikPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel dogruSikLabel = new JLabel("Doğru Şık: ");
                JComboBox<String> dogruSikComboBox = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
                dogruSikPanel.add(dogruSikLabel);
                dogruSikPanel.add(dogruSikComboBox);

                tekSoruPanel.add(dogruSikPanel, BorderLayout.SOUTH);

                // Sorunun verilerini saklamak için dizilere ekle
                tekSoruPanel.putClientProperty("soruMetniField", soruMetniField);
                tekSoruPanel.putClientProperty("secenekTextFields", secenekTextFields);
                tekSoruPanel.putClientProperty("dogruSikComboBox", dogruSikComboBox);

                soruPaneli.add(tekSoruPanel);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }

        soruPaneli.revalidate();
        soruPaneli.repaint();
    }


    private void sinaviKaydet() {
        try {
            // Kullanıcıdan dosya formatı seçmesini iste
            String[] options = {"Text Dosyası", "PDF Dosyası"};
            int choice = JOptionPane.showOptionDialog(this, "Soruları hangi türde kaydetmek istersiniz?",
                                                      "Dosya Türü Seçimi", JOptionPane.DEFAULT_OPTION,
                                                      JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            // Ders adını al
            String dersAdi = dersAdiField.getText().trim();
            if (dersAdi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen ders adı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Soruların verilerini al
            StringBuilder soruMetinleri = new StringBuilder();
            for (Component comp : soruPaneli.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel tekSoruPanel = (JPanel) comp;

                    // Sorunun verilerini oku
                    JTextField soruMetniField = (JTextField) tekSoruPanel.getClientProperty("soruMetniField");
                    JTextField[] secenekTextFields = (JTextField[]) tekSoruPanel.getClientProperty("secenekTextFields");
                    JComboBox<String> dogruSikComboBox = (JComboBox<String>) tekSoruPanel.getClientProperty("dogruSikComboBox");

                    String soruMetni = soruMetniField.getText();
                    String[] secenekler = new String[5];
                    for (int i = 0; i < secenekTextFields.length; i++) {
                        secenekler[i] = secenekTextFields[i].getText();
                    }
                    String dogruSik = (String) dogruSikComboBox.getSelectedItem();

                    // Soruları metin olarak hazırlıyoruz
                    soruMetinleri.append("Soru: ").append(soruMetni).append("\n");
                    for (int i = 0; i < secenekler.length; i++) {
                        soruMetinleri.append((char) ('A' + i)).append(") ").append(secenekler[i]).append("\n");
                    }

                    // Dosya yolu belirle
                    String filePath = "src/alıstırmalar_pdftext/" + dersAdi + (choice == 0 ? ".txt" : ".pdf");

                    // Veritabanına soru kaydetme işlemi
                    if (!veritabaninaSoruKaydet(dersAdi, soruMetni, secenekler, dogruSik, filePath)) {
                        return; // Ders bulunamazsa, işlemi durdur
                    }
                }
            }

            // Seçilen formata göre dosya kaydet
            String filePath = "src/alıstırmalar_pdftext/" + dersAdi + (choice == 0 ? ".txt" : ".pdf");
            if (choice == 0) {
                // Text dosyası olarak kaydet
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(soruMetinleri.toString());
                }
                JOptionPane.showMessageDialog(this, "Sorular text dosyasına kaydedildi: " + filePath, "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } else if (choice == 1) {
                // PDF dosyası olarak kaydet
                Document document = new Document();
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(filePath));
                    document.open();
                    document.add(new Paragraph(soruMetinleri.toString()));
                    document.close();
                } catch (DocumentException | FileNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "Dosya kaydedilirken bir hata oluştu: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(this, "Sorular PDF dosyasına kaydedildi: " + filePath, "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean veritabaninaSoruKaydet(String dersAdi, String soruMetni, String[] secenekler, String dogruSik, String dosyaYolu) {
        Connection conn = null;
        PreparedStatement dersSorguStmt = null;
        PreparedStatement soruKaydetStmt = null;

        try {
            // Veritabanı bağlantısı
            veribaglanti dbIslemleri = new veribaglanti();
            conn = dbIslemleri.getConnection();

            // Ders tablosunda dersi sorgulama
            String dersSorguSql = "SELECT ID FROM Dersler WHERE DersAdi = ?";
            dersSorguStmt = conn.prepareStatement(dersSorguSql);
            dersSorguStmt.setString(1, dersAdi);

            var dersSonuc = dersSorguStmt.executeQuery();

            if (!dersSonuc.next()) {
                // Ders bulunamadı
                JOptionPane.showMessageDialog(this, "Bu ders bulunamadı! Lütfen önce dersi ekleyin.", "Hata", JOptionPane.ERROR_MESSAGE);
                return false; // Ders bulunamazsa işlem yapılmaz
            }

            // Ders bulundu, ID'sini al
            int dersId = dersSonuc.getInt("ID");

            // Soruları kaydetme işlemi
            String soruKaydetSql = "INSERT INTO Sorular (ders_id, ders_adi, soru_metni, siklar, dogru_sik, dosya_yolu,ekleme_tarihi) VALUES (?, ?, ?, ?, ?, ?, ?)";
            soruKaydetStmt = conn.prepareStatement(soruKaydetSql);

            // Şıkları virgülle birleştir
            String siklar = String.join(",", secenekler);

            // Güncel tarihi ve saati al
            java.sql.Timestamp olusturulmaTarihi = new java.sql.Timestamp(System.currentTimeMillis());

            // Parametreleri ayarla
            soruKaydetStmt.setInt(1, dersId);
            soruKaydetStmt.setString(2, dersAdi);
            soruKaydetStmt.setString(3, soruMetni);
            soruKaydetStmt.setString(4, siklar);
            soruKaydetStmt.setString(5, dogruSik);
            soruKaydetStmt.setString(6, dosyaYolu);
            soruKaydetStmt.setTimestamp(7, olusturulmaTarihi); // Güncel tarihi ve saati ekle

            // Sorguyu çalıştır
            soruKaydetStmt.executeUpdate();
            return true; // Soru başarıyla kaydedildi
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanına kaydederken hata oluştu: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            return false; // Hata durumunda işlem yapılmaz
        } finally {
            try {
                if (dersSorguStmt != null) dersSorguStmt.close();
                if (soruKaydetStmt != null) soruKaydetStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Alısıtırma_islemleri().setVisible(true);
        });
    }
}
