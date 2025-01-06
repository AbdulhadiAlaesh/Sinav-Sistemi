package SınavEKLE;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdatepicker.impl.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import VeriTabanı_Baglantısı.veribaglanti;
import dönem.HocaPanel;

public class Sınav_Ekle extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldDersAdi;
    private JTextField textFieldOgretimGorevlisi;
    private JLabel lblSelectedFile;
    private String selectedFilePath = "";
    private UtilDateModel dateModel;
    private JDatePickerImpl datePicker;
    private JSpinner timeSpinner;

    // Launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Sınav_Ekle frame = new Sınav_Ekle();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Create the frame
    public Sınav_Ekle() {
    	setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 683, 659);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(51, 204, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 669, 60);
        contentPane.add(panel);
        panel.setLayout(null);

        // Ders Adı Alanı
        JLabel lblDersAdi = new JLabel("Ders Adı:");
        lblDersAdi.setBounds(41, 118, 100, 30);
        lblDersAdi.setVisible(false);
        contentPane.add(lblDersAdi);

        textFieldDersAdi = new JTextField();
        textFieldDersAdi.setBounds(151, 119, 200, 30);
        textFieldDersAdi.setVisible(false); // İlk başta görünmesin
        contentPane.add(textFieldDersAdi);

        // Öğretim Görevlisi Alanı
        JLabel lblOgretimGorevlisi = new JLabel("\u00d6\u011fretim G\u00f6revlisi:");
        lblOgretimGorevlisi.setBounds(41, 199, 120, 30);
        lblOgretimGorevlisi.setVisible(false);
        contentPane.add(lblOgretimGorevlisi);

        textFieldOgretimGorevlisi = new JTextField();
        textFieldOgretimGorevlisi.setBounds(150, 200, 200, 30);
        textFieldOgretimGorevlisi.setVisible(false);
        contentPane.add(textFieldOgretimGorevlisi);

        // Tarih ve Saat Seçme
        JLabel lblTarihSaat = new JLabel("Tarih ve Saat:");
        lblTarihSaat.setBounds(41, 271, 120, 30);
        lblTarihSaat.setVisible(false);
        contentPane.add(lblTarihSaat);

        dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Bugün");
        properties.put("text.month", "Ay");
        properties.put("text.year", "Yıl");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datePicker.setBounds(151, 271, 200, 30);
        datePicker.setVisible(false);
        contentPane.add(datePicker);

        timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setBounds(151, 335, 200, 30);
        timeSpinner.setVisible(false);
        contentPane.add(timeSpinner);
        
        JLabel DosyaseçlblNewLabel = new JLabel("Dosya seç");
        DosyaseçlblNewLabel.setBounds(50, 406, 91, 22);
        DosyaseçlblNewLabel.setVisible(false);
        contentPane.add(DosyaseçlblNewLabel);
        
     // Dosya Seç Butonu
        JButton DosyabtnNewButton = new JButton("Dosya Seç");
        DosyabtnNewButton.setVisible(false);
        DosyabtnNewButton.setBounds(176, 406, 120, 30);
        contentPane.add(DosyabtnNewButton);

        // Dosya seçildi mi? Kontrol etmek için bir JCheckBox
        JCheckBox chkDosyaSecildi = new JCheckBox("Dosya Seçildi");
        chkDosyaSecildi.setBounds(320, 406, 150, 30);
        chkDosyaSecildi.setEnabled(false); // Kullanıcı tarafından işaretlenemez, sadece sistem tarafından
        chkDosyaSecildi.setVisible(false);
        contentPane.add(chkDosyaSecildi);

        // Dosya Seç butonuna tıklandığında dosya seçme işlemini gerçekleştiren olay dinleyicisi
        DosyabtnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Dosyaları", "pdf")); // Sadece PDF dosyaları için filtre
                int result = fileChooser.showOpenDialog(Sınav_Ekle.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    chkDosyaSecildi.setSelected(true); // Dosya seçildiğinde checkbox işaretlenir
                } else {
                    chkDosyaSecildi.setSelected(false); // Dosya seçimi iptal edilirse checkbox temizlenir
                }
            }
        });



        // PDF Yükle Butonu
        JButton PdfbtnNewButton = new JButton("PDF Yükle");
        PdfbtnNewButton.setBounds(54, 10, 113, 40);
        panel.add(PdfbtnNewButton);

        PdfbtnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblDersAdi.setVisible(true);
                textFieldDersAdi.setVisible(true);
                lblOgretimGorevlisi.setVisible(true);
                textFieldOgretimGorevlisi.setVisible(true);
                lblTarihSaat.setVisible(true);
                datePicker.setVisible(true);
                timeSpinner.setVisible(true);
                DosyaseçlblNewLabel.setVisible(true);
                DosyabtnNewButton.setVisible(true);
                chkDosyaSecildi.setVisible(true);
                
                contentPane.revalidate();
                contentPane.repaint();
            }
        });
        
        
        
        
        
        
        
        
        
        // Kaydet Butonu
        
        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setBounds(213, 10, 113, 40);
        panel.add(btnKaydet);
        
        JButton GeribtnNewButton = new JButton("");
        GeribtnNewButton.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Iconsmind-Outline-Back-2-2.24.png"));
        GeribtnNewButton.setBounds(368, 10, 113, 40);
        panel.add(GeribtnNewButton);
        
        GeribtnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				HocaPanel hocaPanel = new HocaPanel();
				hocaPanel.setVisible(true);
				setVisible(false);
				
			}
		});
        
      
        
     
        
        
     // Kaydet Butonu
        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dersAdi = textFieldDersAdi.getText().trim();
                String ogretimGorevlisi = textFieldOgretimGorevlisi.getText().trim();
                Date selectedDate = (Date) datePicker.getModel().getValue();
                Date selectedTime = (Date) timeSpinner.getValue();

                if (dersAdi.isEmpty() || ogretimGorevlisi.isEmpty() || !chkDosyaSecildi.isSelected() || selectedDate == null || selectedTime == null) {
                    JOptionPane.showMessageDialog(Sınav_Ekle.this, "Lütfen tüm alanları doldurun ve dosya seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Dosyanın adını alıyoruz
                    String fileName = new java.io.File(selectedFilePath).getName();

                    // SRC/_sınavlarım klasörüne kaydetmek
                    String examDirectory = "src/Sinavlarım/";

                    // Dosyanın tam yolu
                    String examFilePath = examDirectory + fileName;

                    // Dosyayı _sınavlarım klasörüne kopyala
                    java.nio.file.Files.copy(
                        java.nio.file.Paths.get(selectedFilePath),
                        java.nio.file.Paths.get(examFilePath),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );

                    // Tarih ve saat formatlama
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    String formattedDate = dateFormat.format(selectedDate);
                    String formattedTime = timeFormat.format(selectedTime);

                 // Veritabanı bağlantısı ve kaydetme işlemi
                    try (Connection connection = veribaglanti.getConnection()) {
                        // Ders adı veritabanında var mı kontrol et
                        String query = "SELECT ID FROM Dersler WHERE DersAdi = ?";
                        PreparedStatement pst = connection.prepareStatement(query);
                        pst.setString(1, dersAdi);
                        ResultSet rs = pst.executeQuery();

                        if (rs.next()) {
                            // Ders bulundu, ders_id'yi al
                            int ders_id = rs.getInt("ID");

                            // Sinavlar tablosunda bu ders için daha önce sınav kaydı var mı kontrol et
                            String checkQuery = "SELECT * FROM Sinavlar WHERE ders_id = ?";
                            PreparedStatement checkPst = connection.prepareStatement(checkQuery);
                            checkPst.setInt(1, ders_id);
                            ResultSet checkRs = checkPst.executeQuery();

                            if (checkRs.next()) {
                                // Ders için zaten bir sınav kaydı varsa
                                JOptionPane.showMessageDialog(Sınav_Ekle.this, "Bu ders için zaten bir sınav kaydı bulunmaktadır!", "Hata", JOptionPane.ERROR_MESSAGE);
                                return;  // Eğer sınav zaten varsa, işlemi sonlandır
                            }

                            // Ders için sınav kaydını ekle
                            String insertQuery = "INSERT INTO Sinavlar (ders_id, ders_adi, ogretim_gorevlisi, tarih, saat, dosya_tipi, dosya_yolu_1) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement insertPst = connection.prepareStatement(insertQuery);
                            insertPst.setInt(1, ders_id); // Ders id'sini ekle
                            insertPst.setString(2, dersAdi); // Ders adını ekle
                            insertPst.setString(3, ogretimGorevlisi);
                            insertPst.setString(4, formattedDate);
                            insertPst.setString(5, formattedTime);
                            insertPst.setString(6, "PDF");
                            insertPst.setString(7, examFilePath);
                            insertPst.executeUpdate();

                            JOptionPane.showMessageDialog(Sınav_Ekle.this, "Bilgiler başarıyla kaydedildi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(Sınav_Ekle.this, "Ders adı bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    // Sadece kullanıcıya hata mesajı gösterilir, hata mesajı konsola yazdırılmaz
                    JOptionPane.showMessageDialog(Sınav_Ekle.this, "Bir hata oluştu. Lütfen tekrar deneyin.", "Hata", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

    }
}
