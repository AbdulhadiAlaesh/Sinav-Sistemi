package Ögrenci_Paneli;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import VeriTabanı_Baglantısı.veribaglanti;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Font;

public class Sinavlarım extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel_1;
    private JButton btnOpenExam, btnNextPage, btnPrevPage, btnSave;
    private JLabel timerLabel;
    private JTextArea answerTextArea;

    private Timer timer;
    private int remainingTime = 40 * 60; // 40 dakika (saniye cinsinden)

    private PDDocument document;
    private PDFRenderer pdfRenderer;
    private int currentPageIndex = 0;
    private int totalPages = 0;
    private int selectedExamId;  // Seçilen sınavın ID'si***
    private JTextField ParolatextField;
    private JTextField KullaniciAditextField_1;


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Sinavlarım frame = new Sinavlarım();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    
 
    
    
    public Sinavlarım() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1458, 777);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Üst panel
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1455, 53);
        panel.setBackground(new Color(102, 255, 255));
        contentPane.add(panel);
        panel.setLayout(null);

        JButton btnNewButton = new JButton("Ana Sayfa");
        btnNewButton.setBounds(10, 11, 116, 30);
        panel.add(btnNewButton);

        btnNewButton.addActionListener(e -> {
            Ögr_Anasayfa anasayfa = new Ögr_Anasayfa();
            anasayfa.setVisible(true);
            setVisible(false);
        });

        panel_1 = new JPanel();
        panel_1.setBackground(new Color(204, 255, 255));
        panel_1.setBounds(0, 54, 1434, 600);
        contentPane.add(panel_1);
      /*  btnOpenExam = new JButton("Sınavı Aç"); //sınav ac butonun yeri
        btnOpenExam.setEnabled(false);
        btnOpenExam.setBounds(1000, 11, 120, 30);

        btnOpenExam.addActionListener(e -> {
            // Sınav verisini al ve sayfayı aç
        	fetchAndRenderFirstExam();
            startCountdownTimer(); // Sayaç başlatılır
        });
        panel.add(btnOpenExam);*/

        // Sayaç etiketi
        timerLabel = new JLabel("Kalan Süre: 40:00");
        timerLabel.setBounds(1140, 11, 150, 30);
        panel.add(timerLabel);

        // Cevap alanı
        answerTextArea = new JTextArea();
        answerTextArea.setBounds(10, 670, 580, 70);
        contentPane.add(answerTextArea);

        // Kaydet butonu
        btnSave = new JButton("Kaydet");
        btnSave.setBounds(600, 670, 120, 30);
        btnSave.addActionListener(e -> saveAnswersToDatabase());
        contentPane.add(btnSave);

        // Önceki sayfa butonu
        btnPrevPage = new JButton("Önceki Sayfa");
        btnPrevPage.setBounds(730, 670, 120, 30);
        btnPrevPage.setEnabled(false); // PDF yüklenene kadar devre dışı
        btnPrevPage.addActionListener(e -> goToPreviousPage());
        contentPane.add(btnPrevPage);

        // Sonraki sayfa butonu
        btnNextPage = new JButton("Sonraki Sayfa");
        btnNextPage.setBounds(860, 670, 120, 30);
        btnNextPage.setEnabled(false); // PDF yüklenene kadar devre dışı
        btnNextPage.addActionListener(e -> goToNextPage());
        contentPane.add(btnNextPage);

        checkExams();
        setDateFieldWithCurrentDate();
    }
    
    
    

    
    
    /**/
    

    private void checkExams() {
        try (Connection conn = veribaglanti.getConnection()) {
            String query = "SELECT COUNT(*) AS exam_count FROM Sinavlar";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int examCount = rs.getInt("exam_count");
                    if (examCount > 0) {
                        // Sınav sayısını kullanıcıya bildir
                        displayExamCount(examCount);
                    } else {
                        displayMessage("Sınav bulunamadı.");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Sınav sayısını kullanıcıya göster
    private void displayExamCount(int examCount) {
        String message = examCount + " tane sınav var.";
        JOptionPane.showMessageDialog(this, message, "Sınav Sayısı", JOptionPane.INFORMATION_MESSAGE);

        // Sınavları listele ve kullanıcıdan seçim yapmasını sağla
        List<String> examNames = new ArrayList<>();
        try (Connection conn = veribaglanti.getConnection()) {
            String query = "SELECT ders_adi FROM Sinavlar";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    examNames.add(rs.getString("ders_adi"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Sınavları seçmek için bir ComboBox oluştur
        String[] examArray = examNames.toArray(new String[0]);
        String selectedExam = (String) JOptionPane.showInputDialog(
                this,
                "Hangi sınavdan başlamak istersiniz?",
                "Sınav Seçimi",
                JOptionPane.QUESTION_MESSAGE,
                null,
                examArray,
                examArray[0]
        );

        // Seçilen sınavı aç
        if (selectedExam != null) {
            openSelectedExam(selectedExam);
        }
    }

    // Seçilen sınavı açan fonksiyon
 /*   private void openSelectedExam(String selectedExam) {
        String pdfQuery = "SELECT dosya_yolu_1, ders_adi FROM Sinavlar WHERE ders_adi = ?";
        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement pdfStmt = conn.prepareStatement(pdfQuery)) {
            pdfStmt.setString(1, selectedExam);
            try (ResultSet pdfRs = pdfStmt.executeQuery()) {
                if (pdfRs.next()) {
                    String pdfYolu = pdfRs.getString("dosya_yolu_1");
                    displayCourseAndRenderPDF(selectedExam, pdfYolu);
                    startCountdownTimer(); // Sayaç başlatılır
                } else {
                    displayMessage("Seçilen sınav dosyası bulunamadı.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }*/

    private void openSelectedExam(String selectedExam) {
        String pdfQuery = "SELECT sinav_id, dosya_yolu_1, ders_adi FROM Sinavlar WHERE ders_adi = ?";
        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement pdfStmt = conn.prepareStatement(pdfQuery)) {
            pdfStmt.setString(1, selectedExam);
            try (ResultSet pdfRs = pdfStmt.executeQuery()) {
                if (pdfRs.next()) {
                    selectedExamId = pdfRs.getInt("sinav_id");  // ID'yi al
                    String pdfYolu = pdfRs.getString("dosya_yolu_1");
                    String dersAdi = pdfRs.getString("ders_adi");
                    displayCourseAndRenderPDF(dersAdi, pdfYolu);
                    startCountdownTimer(); // Sayaç başlatılır
                } else {
                    displayMessage("Seçilen sınav dosyası bulunamadı.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    // İlk sınavı açan fonksiyon
    private void fetchAndRenderFirstExam() {
        String pdfQuery = "SELECT TOP 1 dosya_yolu_1, ders_adi FROM Sinavlar";
        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement pdfStmt = conn.prepareStatement(pdfQuery);
             ResultSet pdfRs = pdfStmt.executeQuery()) {
            if (pdfRs.next()) {
                String pdfYolu = pdfRs.getString("dosya_yolu_1");
                String dersAdi = pdfRs.getString("ders_adi");
                displayCourseAndRenderPDF(dersAdi, pdfYolu);
            } else {
                displayMessage("Sınav dosyası bulunamadı.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // PDF dosyasını yükleyen ve ekranda gösteren fonksiyon
    private void displayCourseAndRenderPDF(String dersAdi, String pdfYolu) {
        JLabel courseNameLabel = new JLabel("Ders: " + dersAdi);
        courseNameLabel.setBounds(10, 10, 300, 30);
        panel_1.add(courseNameLabel);
        renderPDF(pdfYolu);
    }

    // PDF render fonksiyonu
    private void renderPDF(String pdfYolu) {
        File pdfFile = new File(pdfYolu);
        if (pdfFile.exists() && pdfFile.canRead()) {
            try {
                if (document != null) {
                    document.close();
                }
                document = PDDocument.load(pdfFile);
                pdfRenderer = new PDFRenderer(document);
                totalPages = document.getNumberOfPages();
                currentPageIndex = 0;
                renderPage(currentPageIndex);

                // Sayfa butonlarını etkinleştir
                btnPrevPage.setEnabled(currentPageIndex > 0);
                btnNextPage.setEnabled(currentPageIndex < totalPages - 1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            displayMessage("PDF dosyası bulunamadı.");
        }
    }


    private void renderPage(int pageIndex) {
        try {
            BufferedImage pageImage = pdfRenderer.renderImageWithDPI(pageIndex, 150);
            int panelWidth = panel_1.getWidth();
            int panelHeight = panel_1.getHeight();
            double widthScale = (double) panelWidth / pageImage.getWidth();
            double heightScale = (double) panelHeight / pageImage.getHeight();
            double scale = Math.min(widthScale, heightScale);

            int newWidth = (int) (pageImage.getWidth() * scale);
            int newHeight = (int) (pageImage.getHeight() * scale);

            Image scaledImage = pageImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            JLabel pdfImageLabel = new JLabel(new ImageIcon(scaledImage));
            panel_1.removeAll();
            panel_1.add(pdfImageLabel);
            panel_1.revalidate();
            panel_1.repaint();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void goToNextPage() {
        if (currentPageIndex < totalPages - 1) {
            currentPageIndex++;
            renderPage(currentPageIndex);
            btnPrevPage.setEnabled(currentPageIndex > 0);
            btnNextPage.setEnabled(currentPageIndex < totalPages - 1);
        }
    }

    private void goToPreviousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            renderPage(currentPageIndex);
            btnPrevPage.setEnabled(currentPageIndex > 0);
            btnNextPage.setEnabled(currentPageIndex < totalPages - 1);
        }
    }

  /*  private void saveAnswersToDatabase() {
        String answers = answerTextArea.getText();
        if (answers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen cevaplarınızı yazınız!");
            return;
        }

        String saveQuery = "INSERT INTO Sinav_Cevaplari ( sinav_id, cevaplar) VALUES ( ?, ?)";
        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement stmt = conn.prepareStatement(saveQuery)) {

            // Örnek veriler, burada gerçek veriler kullanılmalı
           // int studentId = 0; // Öğrenci ID
            int examId = 1;    // Sınav ID

            //stmt.setInt(1, studentId);
            stmt.setInt(1, examId);
            stmt.setString(2, answers);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cevaplar başarıyla kaydedildi!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cevaplar kaydedilirken bir hata oluştu.");
        }
    }
    */
 /*   private void saveAnswersToDatabase() {
        String answers = answerTextArea.getText();
        if (answers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen cevaplarınızı yazınız!");
            return;
        }

        String saveQuery = "INSERT INTO Sinav_Cevaplari (sinav_id, cevaplar) VALUES (?, ?)";
        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement stmt = conn.prepareStatement(saveQuery)) {

            // Seçilen sınavın ID'si
            stmt.setInt(1, selectedExamId);
            stmt.setString(2, answers);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cevaplar başarıyla kaydedildi!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cevaplar kaydedilirken bir hata oluştu.");
        }
    }*/

    private void saveAnswersToDatabase() {
        String answers = answerTextArea.getText();
        String kullaniciAdi = KullaniciAditextField_1.getText();
        String parola = ParolatextField.getText();

        if (answers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen cevaplarınızı yazınız!");
            return;
        }

        if (kullaniciAdi.isEmpty() || parola.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kullanıcı adı ve parolayı giriniz!");
            return;
        }

        String studentQuery = "SELECT OgrenciID FROM Ogrenci WHERE KullaniciAdi = ? AND Parola = ?";
        String saveQuery = "INSERT INTO Sinav_Cevaplari (ogrenci_id, sinav_id, cevaplar) VALUES (?, ?, ?)";

        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
             PreparedStatement saveStmt = conn.prepareStatement(saveQuery)) {

            // Öğrenci bilgilerini kontrol et
            studentStmt.setString(1, kullaniciAdi);
            studentStmt.setString(2, parola);
            try (ResultSet rs = studentStmt.executeQuery()) {
                if (rs.next()) {
                    int ogrenciId = rs.getInt("OgrenciID");

                    // Cevapları kaydet
                    saveStmt.setInt(1, ogrenciId);
                    saveStmt.setInt(2, selectedExamId);
                    saveStmt.setString(3, answers);
                    saveStmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Cevaplar başarıyla kaydedildi!");
                } else {
                    JOptionPane.showMessageDialog(this, "Kullanıcı adı veya parola hatalı!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cevaplar kaydedilirken bir hata oluştu.");
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    private void startCountdownTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingTime <= 0) {
                    timer.cancel();
                    timerLabel.setText("Süre doldu!");
                    closeExam();
                } else {
                    remainingTime--;
                    int minutes = remainingTime / 60;
                    int seconds = remainingTime % 60;
                    timerLabel.setText(String.format("Kalan Süre: %02d:%02d", minutes, seconds));
                }
            }
        }, 0, 1000);
    }

    private void closeExam() {
        try {
            if (document != null) {
                document.close();
                document = null;
            }
            panel_1.removeAll();
            panel_1.revalidate();
            panel_1.repaint();
            JOptionPane.showMessageDialog(this, "Sınav süreniz sona erdi.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayMessage(String message) {
        JLabel messageLabel = new JLabel(message);
        panel_1.removeAll();
        panel_1.add(messageLabel);
        panel_1.revalidate();
        panel_1.repaint();
    }

    private void setDateFieldWithCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JLabel dateLabel = new JLabel(sdf.format(currentDate));
        dateLabel.setBounds(1200, 10, 200, 30);
        contentPane.add(dateLabel);
        
        ParolatextField = new JTextField();
        ParolatextField.setBounds(1258, 663, 176, 30);
        contentPane.add(ParolatextField);
        ParolatextField.setColumns(10);
        
        KullaniciAditextField_1 = new JTextField();
        KullaniciAditextField_1.setBounds(1063, 663, 167, 30);
        contentPane.add(KullaniciAditextField_1);
        KullaniciAditextField_1.setColumns(10);
        
        
        
        
        JLabel lblNewLabel = new JLabel("Kullanıcı Adi ve Ögr_No");
        lblNewLabel.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 17));
        lblNewLabel.setBounds(1063, 703, 167, 27);
        contentPane.add(lblNewLabel);
        
        
    }
    

    @Override
    public void dispose() {
        super.dispose();
        try {
            if (document != null) {
                document.close();
            }
            if (timer != null) {
                timer.cancel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}  //sinavlarım
