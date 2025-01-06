package Ögrenci_Listeleme;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import VeriTabanı_Baglantısı.veribaglanti;
import dönem.HocaPanel;

import javax.swing.ImageIcon;

public class Ögernci_Listeleme_Alanı extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JTextField ARAtextField;
    private boolean isEditable = false; // Düzenlenebilirlik durumu

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ögernci_Listeleme_Alanı frame = new Ögernci_Listeleme_Alanı();
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
    public Ögernci_Listeleme_Alanı() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1422, 653);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(51, 204, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 170, 1388, 436);
        contentPane.add(scrollPane);

        table = new JTable(new CustomTableModel()); // Özel model kullanılıyor
        scrollPane.setViewportView(table);

        ARAtextField = new JTextField();
        ARAtextField.setBounds(66, 48, 225, 27);
        contentPane.add(ARAtextField);
        ARAtextField.setColumns(10);

        JButton ARAbtnNewButton = new JButton("Ara");
        ARAbtnNewButton.setBounds(367, 51, 85, 21);
        contentPane.add(ARAbtnNewButton);
        
        
        
        
        
        
        
        
        
        
        

        JButton DüzenlebtnNewButton_2 = new JButton("Düzenle");
        DüzenlebtnNewButton_2.setBounds(669, 51, 85, 21);
        contentPane.add(DüzenlebtnNewButton_2);

  
        
        // Düzenle butonu işlevi
        DüzenlebtnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditable = !isEditable; // Düzenlenebilirlik durumunu tersine çevir
                ((CustomTableModel) table.getModel()).setEditable(isEditable);

                if (isEditable) {
                    DüzenlebtnNewButton_2.setText("Kilitle");
                } else {
                    DüzenlebtnNewButton_2.setText("Düzenle");
                }
            }
        });
        
        
        
        
        
        
        
        

        JButton KaydetbtnNewButton_1 = new JButton("Kaydet");
        KaydetbtnNewButton_1.setBounds(514, 51, 85, 21);
        contentPane.add(KaydetbtnNewButton_1);

        // Kaydet butonu işlevi
        KaydetbtnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // JTable'den seçili satırı alıyoruz
                int selectedRow = table.getSelectedRow();

                if (selectedRow == -1) {
                    System.out.println("Lütfen bir satır seçin.");
                    return;  // Satır seçilmemişse işlem yapma
                }

                // Seçilen satırdaki verileri alıyoruz
                String parola = (String) model.getValueAt(selectedRow, 0);
                String kullaniciAdi = (String) model.getValueAt(selectedRow, 1);
                String dersAdi = (String) model.getValueAt(selectedRow, 2);
                String tarih = (String) model.getValueAt(selectedRow, 3);

                // Devamsızlık durumunu alıyoruz (Integer türünde olabilir)
                Object devamsizlikObj = model.getValueAt(selectedRow, 4);
                int devamsizlikDurumu = 0; // Varsayılan değer sıfır

                if (devamsizlikObj != null) {
                    try {
                        // Eğer devamsızlık objesi Integer türündeyse, doğrudan alıyoruz
                        if (devamsizlikObj instanceof Integer) {
                            devamsizlikDurumu = (Integer) devamsizlikObj;
                        } else {
                            // Devamsızlık bilgisi String olarak ise, String'den Integer'a dönüşüm
                            String devamsizlikStr = devamsizlikObj.toString();
                            if (!devamsizlikStr.trim().isEmpty()) {
                                devamsizlikDurumu = Integer.parseInt(devamsizlikStr);
                            }
                        }
                    } catch (NumberFormatException ex) {
                        // Eğer geçerli bir sayı değilse, hata mesajı veriyoruz
                        System.out.println("Devamsızlık durumu geçerli bir sayı değil. Varsayılan değer 0 kullanılacak.");
                        devamsizlikDurumu = 0;
                    }
                } else {
                    System.out.println("Devamsızlık durumu boş. Varsayılan değer 0 kullanılacak.");
                }

                // Devamsızlık durumu 0'dan büyük ve 25'ten küçük olmalı
                if (devamsizlikDurumu < 0 || devamsizlikDurumu >= 25) {
                    System.out.println("Devamsızlık durumu geçerli değil. Lütfen 0'dan büyük ve 25'ten küçük bir sayı girin.");
                    return;
                }

                // Veritabanı işlemi: Kayıt var mı diye kontrol et
                try (Connection conn = veribaglanti.getConnection()) {
                    // Öncelikle bu öğrenciye ait bir kayıt var mı kontrol edelim
                    String checkSql = "SELECT COUNT(*) FROM Ogrenci_Listeleme WHERE KullaniciAdi = ? AND DersAdi = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                    checkStmt.setString(1, kullaniciAdi);
                    checkStmt.setString(2, dersAdi);
                    ResultSet rs = checkStmt.executeQuery();

                    rs.next();
                    int count = rs.getInt(1); // Kayıt sayısını al

                    if (count > 0) {
                        // Eğer kayıt varsa, devamsızlık durumunu güncelle
                        String updateSql = "UPDATE Ogrenci_Listeleme SET DevamsizlikDurumu = ? WHERE KullaniciAdi = ? AND DersAdi = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                        updateStmt.setInt(1, devamsizlikDurumu);
                        updateStmt.setString(2, kullaniciAdi);
                        updateStmt.setString(3, dersAdi);

                        int rowsAffected = updateStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Devamsızlık durumu başarıyla güncellendi: " + kullaniciAdi + " - " + dersAdi);
                        } else {
                            System.out.println("Devamsızlık durumu güncellenirken bir hata oluştu.");
                        }
                    } else {
                        // Eğer kayıt yoksa, yeni bir kayıt ekle
                        String insertSql = "INSERT INTO Ogrenci_Listeleme (KullaniciAdi, Parola, DersAdi, Tarih, DevamsizlikDurumu) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insertSql);

                        insertStmt.setString(1, kullaniciAdi);
                        insertStmt.setString(2, parola);
                        insertStmt.setString(3, dersAdi);
                        insertStmt.setString(4, tarih);
                        insertStmt.setInt(5, devamsizlikDurumu);

                        int rowsAffected = insertStmt.executeUpdate();

                        // Etkilenen satır sayısını kontrol et
                        if (rowsAffected > 0) {
                            System.out.println("Yeni kayıt başarıyla eklendi: " + kullaniciAdi + " - " + dersAdi);
                        } else {
                            System.out.println("Kayıt eklenemedi.");
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("Veritabanına eklenirken hata oluştu: " + ex.getMessage());
                    ex.printStackTrace(); // Hata hakkında daha fazla bilgi yazdırın
                }
            
      

                
                

                // Düzenle butonunu tekrar "Düzenle" yapıyoruz
                DüzenlebtnNewButton_2.setText("Düzenle");
                isEditable = false;
                ((CustomTableModel) table.getModel()).setEditable(isEditable);  // Düzenleme modunu kapat
            }
        });

       
        

        
        
        
        
        
        
              JButton SilbtnNewButton_2_1 = new JButton("Sil");
        SilbtnNewButton_2_1.setBounds(823, 51, 85, 21);
        contentPane.add(SilbtnNewButton_2_1);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setBackground(new Color(204, 0, 0));
        btnNewButton.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Iconsmind-Outline-Back-2-2.24.png"));
        btnNewButton.setBounds(0, 0, 94, 27);
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
        
        
        
        
        
        
        SilbtnNewButton_2_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // JTable'den seçili satırı alıyoruz
                int selectedRow = table.getSelectedRow();
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(contentPane, "Lütfen bir satır seçin.");
                    return;  // Satır seçilmemişse işlem yapılmaz
                }

                // Seçilen satırdaki verileri alıyoruz
                String parola = (String) model.getValueAt(selectedRow, 0);  // Parola
                String kullaniciAdi = (String) model.getValueAt(selectedRow, 1);  // Kullanıcı adı
                String dersAdi = (String) model.getValueAt(selectedRow, 2);  // Ders adı
                String tarih = (String) model.getValueAt(selectedRow, 3);  // Tarih

                // Veritabanından silme işlemi
                deleteFromDatabase(kullaniciAdi, dersAdi);

                // Tabloyu güncelleme işlemi
                model.removeRow(selectedRow);  // Tablodan seçili satırı sil
            }
        });

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

        // Ara butonu işlevi
        ARAbtnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aramaMetni = ARAtextField.getText();
                if (!aramaMetni.isEmpty()) {
                    searchAndLoadData(aramaMetni);
                } else {
                    loadData(); // Eğer alan boşsa tüm verileri getir
                }
            }
        });

 
        // Başlangıçta tüm verileri yükle
        loadData();
    }

    /**
     * Veritabanından verileri çeker ve JTable'e yazar.
     */
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Önceki verileri temizle

        try (Connection conn = veribaglanti.getConnection()) {
            // SQL sorgusundaki kolon adlarını kontrol edin
            String sql = "SELECT Ogrenci.Parola, Ogrenci.KullaniciAdi, Dersler.DersAdi, ogrenci_mesaj.tarih " +
                         "FROM Ogrenci " +
                         "LEFT JOIN ogrenci_mesaj ON Ogrenci.KullaniciAdi = ogrenci_mesaj.ogrenci_adi " +
                         "LEFT JOIN Dersler ON ogrenci_mesaj.ders_adi = Dersler.DersAdi"; // 'ders_adi' kolon adı burada kontrol edilmeli

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String parola = rs.getString("Parola");
                String kullaniciAdi = rs.getString("KullaniciAdi");
                String dersAdi = rs.getString("DersAdi"); // 'ders_adi' yerine 'DersAdi' alınması gerekebilir
                String tarih = rs.getString("tarih");

                // Devamsızlık durumu varsayılan olarak 0
                int devamsizlikDurumu = 0; 

                // Tabloya yeni satır ekle
                model.addRow(new Object[]{parola, kullaniciAdi, dersAdi, tarih, devamsizlikDurumu});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Veritabanında arama yapar ve JTable'e arama sonuçlarını yazar.
     */
    private void searchAndLoadData(String aramaMetni) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Önceki verileri temizle

        try (Connection conn = veribaglanti.getConnection()) {
            String sql = "SELECT Ogrenci.Parola, Ogrenci.KullaniciAdi, ogrenci_mesaj.ders_adi, ogrenci_mesaj.tarih, " +
                         "'Devamsızlık Bilgisi Yok' AS devamsızlık_durumu " +
                         "FROM Ogrenci " +
                         "LEFT JOIN ogrenci_mesaj ON Ogrenci.KullaniciAdi = ogrenci_mesaj.ogrenci_adi " +
                         "WHERE Ogrenci.KullaniciAdi LIKE ? OR ogrenci_mesaj.ders_adi LIKE ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + aramaMetni + "%");
            stmt.setString(2, "%" + aramaMetni + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String parola = rs.getString("Parola");
                String kullaniciAdi = rs.getString("KullaniciAdi");
                String dersAdi = rs.getString("ders_adi");
                String tarih = rs.getString("tarih");
                String devamsizlik = rs.getString("devamsızlık_durumu");

                model.addRow(new Object[]{parola, kullaniciAdi, dersAdi, tarih, devamsizlik});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Özelleştirilmiş tablo modeli
    // Özelleştirilmiş tablo modeli
    class CustomTableModel extends DefaultTableModel {
        private boolean isEditable;

        public CustomTableModel() {
            super(new Object[][] {}, new String[] {
                "Parola", "KullaniciAdi", "Ders Adi", "Tarih", "Devamsızlık"
            });
        }

        public void setEditable(boolean isEditable) {
            this.isEditable = isEditable;
            fireTableStructureChanged();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // Sadece devamsızlık kolonunu düzenlenebilir yap
            return isEditable && column == 4; // 4. kolon devamsızlık
        }
    }
    
    private void deleteFromDatabase(String kullaniciAdi, String dersAdi) {
        try (Connection conn = veribaglanti.getConnection()) {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Veritabanı bağlantısı sağlanamadı.");
            }

            // Silme sorgusu
            String sql = "DELETE FROM Ogrenci_Listeleme WHERE KullaniciAdi = ? AND DersAdi = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, kullaniciAdi);  // Kullanıcı adı
                stmt.setString(2, dersAdi);       // Ders adı

                int rowsDeleted = stmt.executeUpdate(); // Sorguyu çalıştırma
                System.out.println("Silinen Satır Sayısı: " + rowsDeleted);

                if (rowsDeleted > 0) {
                    System.out.println("Veritabanı başarıyla güncellendi!");
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Kayıt bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

