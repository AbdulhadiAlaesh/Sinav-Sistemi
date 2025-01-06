package Ögrenci_Paneli;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import VeriTabanı_Baglantısı.veribaglanti;

public class Slaytlarım extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField searchField;
    private JTextField dateField;
    private JPanel panel_1;

    private PDDocument document; // PDF belgesini tutar
    private PDFRenderer pdfRenderer; // PDF Renderer
    private int currentPageIndex = 0; // Mevcut sayfa indeksi
    private int totalPages = 0; // Toplam sayfa sayısı

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Slaytlarım frame = new Slaytlarım();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Slaytlarım() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1458, 777);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1455, 53);
        panel.setBackground(new Color(102, 255, 255));
        contentPane.add(panel);
        panel.setLayout(null);

        // Arama çubuğu
        searchField = new JTextField();
        searchField.setBounds(10, 11, 300, 30);
        panel.add(searchField);
        searchField.setColumns(10);

        // Arama butonu
        JButton searchButton = new JButton("Ara");
        searchButton.setBounds(566, 11, 80, 30);
        panel.add(searchButton);

        // Geri butonu
        JButton previousButton = new JButton("Geri");
        previousButton.setBounds(821, 11, 80, 30);
        panel.add(previousButton);

        // İleri butonu
        JButton nextButton = new JButton("İleri");
        nextButton.setBounds(932, 11, 80, 30);
        panel.add(nextButton);

        // Tarih alanı
        dateField = new JTextField();
        dateField.setBounds(336, 11, 200, 30);
        panel.add(dateField);
        dateField.setEditable(false);
        
        JButton btnNewButton = new JButton("Ana Sayfa");
        btnNewButton.setBounds(667, 11, 116, 30);
        panel.add(btnNewButton);
        
        btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Ögr_Anasayfa anasayfa = new Ögr_Anasayfa();
				
				anasayfa.setVisible(true);
				setVisible(false);
				
			}
		});
        
        

        panel_1 = new JPanel();
        panel_1.setBackground(new Color(204, 255, 255));
        panel_1.setBounds(0, 54, 1455, 676);
        contentPane.add(panel_1);

        // Arama butonuna tıklama işlemi
        searchButton.addActionListener(e -> handleSearch());

        // Geri ve ileri butonlarına tıklama işlemleri
        previousButton.addActionListener(e -> goToPreviousPage());
        nextButton.addActionListener(e -> goToNextPage());

        // Güncel tarihi yazdırma
        setDateFieldWithCurrentDate();
    }

    /**
     * Handle the search button action
     */
    private void handleSearch() {
        String dersAdi = searchField.getText().trim();
        if (!dersAdi.isEmpty()) {
            try (Connection conn = veribaglanti.getConnection()) {
                String query = "SELECT ID FROM Dersler WHERE DersAdi LIKE ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, "%" + dersAdi + "%");
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            int dersId = rs.getInt("ID");
                            fetchAndRenderPDF(dersId);
                        } else {
                            displayMessage("Ders bulunamadı.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            displayMessage("Lütfen bir ders adı girin.");
        }
    }

    /**
     * Fetch and render the PDF
     */
    private void fetchAndRenderPDF(int dersId) {
        String pdfQuery = "SELECT dosya_yolu FROM pdf_dosyaları WHERE ders_id = ?";
        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement pdfStmt = conn.prepareStatement(pdfQuery)) {
            pdfStmt.setInt(1, dersId);
            try (ResultSet pdfRs = pdfStmt.executeQuery()) {
                if (pdfRs.next()) {
                    String pdfYolu = pdfRs.getString("dosya_yolu");
                    renderPDF(pdfYolu);
                } else {
                    displayMessage("PDF dosyası bulunamadı.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Render the PDF file
     */
    private void renderPDF(String pdfYolu) {
        File pdfFile = new File(pdfYolu);
        if (pdfFile.exists() && pdfFile.canRead()) {
            try {
                if (document != null) {
                    document.close(); // Daha önce açık olan belgeyi kapat
                }
                document = PDDocument.load(pdfFile);
                pdfRenderer = new PDFRenderer(document);
                totalPages = document.getNumberOfPages(); // Toplam sayfa sayısını al
                currentPageIndex = 0; // İlk sayfadan başla
                renderPage(currentPageIndex);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            displayMessage("PDF dosyası bulunamadı.");
        }
    }

    /**
     * Render a specific page of the PDF
     */
    private void renderPage(int pageIndex) {
        try {
            BufferedImage pageImage = pdfRenderer.renderImage(pageIndex); // Belirtilen sayfayı render et
            JLabel pdfImageLabel = new JLabel(new ImageIcon(pageImage));
            panel_1.removeAll();
            panel_1.add(pdfImageLabel);
            panel_1.revalidate();
            panel_1.repaint();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Navigate to the next page
     */
    private void goToNextPage() {
        if (document != null && currentPageIndex < totalPages - 1) {
            currentPageIndex++;
            renderPage(currentPageIndex);
        }
    }

    /**
     * Navigate to the previous page
     */
    private void goToPreviousPage() {
        if (document != null && currentPageIndex > 0) {
            currentPageIndex--;
            renderPage(currentPageIndex);
        }
    }

    /**
     * Display a message in the panel
     */
    private void displayMessage(String message) {
        JLabel messageLabel = new JLabel(message);
        panel_1.removeAll();
        panel_1.add(messageLabel);
        panel_1.revalidate();
        panel_1.repaint();
    }

    /**
     * Set the dateField with the current date formatted as "yyyy-MM-dd"
     */
    private void setDateFieldWithCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateField.setText(sdf.format(currentDate));
    }

    @Override
    public void dispose() {
        super.dispose();
        try {
            if (document != null) {
                document.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
