package dönem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import VeriTabanı_Baglantısı.veribaglanti;

public class Pdf_Alıstıma_Gönderme extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField DersAdıtextField;
    private JTextField KonutextField;
    private JTable pdfTable;
    private JTree tree;
    private String selectedFileName;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Pdf_Alıstıma_Gönderme frame = new Pdf_Alıstıma_Gönderme();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Pdf_Alıstıma_Gönderme() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\path\\to\\icon.png"));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1326, 570);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(51, 204, 51));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("PDF Yükle");
        lblNewLabel.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 24));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(103, 44, 271, 68);
        contentPane.add(lblNewLabel);

        JLabel Ders_Adı = new JLabel("Ders Adı");
        Ders_Adı.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 20));
        Ders_Adı.setHorizontalAlignment(SwingConstants.CENTER);
        Ders_Adı.setBounds(53, 171, 76, 25);
        contentPane.add(Ders_Adı);

        DersAdıtextField = new JTextField();
        DersAdıtextField.setBounds(151, 171, 178, 25);
        contentPane.add(DersAdıtextField);
        DersAdıtextField.setColumns(10);

        JLabel Konu_1 = new JLabel("Konu:");
        Konu_1.setHorizontalAlignment(SwingConstants.CENTER);
        Konu_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 20));
        Konu_1.setBounds(53, 255, 76, 25);
        contentPane.add(Konu_1);

        KonutextField = new JTextField();
        KonutextField.setColumns(10);
        KonutextField.setBounds(151, 256, 178, 25);
        contentPane.add(KonutextField);

        JButton dosyasecbtnNewButton = new JButton("Dosya seç");
        dosyasecbtnNewButton.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
        dosyasecbtnNewButton.setBounds(151, 316, 171, 34);
        contentPane.add(dosyasecbtnNewButton);

        JButton pdfyüklebtnNewButton_1 = new JButton("PDF Yükle");
        pdfyüklebtnNewButton_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 20));
        pdfyüklebtnNewButton_1.setBounds(151, 412, 171, 45);
        contentPane.add(pdfyüklebtnNewButton_1);

        // Create table and scroll pane for displaying PDFs
        String[] columnNames = {"Ders Adı", "Konu", "PDF"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        pdfTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pdfTable);
        scrollPane.setBounds(339, 132, 626, 344);
        contentPane.add(scrollPane);

        // Create tree structure
        tree = new JTree();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("PDF Dosyaları");
        tree.setModel(new DefaultTreeModel(rootNode));
        tree.setBounds(1034, 59, 185, 417);
        contentPane.add(tree);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		HocaPanel hocaPanel = new HocaPanel();
        		hocaPanel.setVisible(true);
        		setVisible(false);
        	}
        });
        btnNewButton.setBackground(new Color(153, 0, 0));
        btnNewButton.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Iconsmind-Outline-Back-2-2.24.png"));
        btnNewButton.setBounds(0, 0, 84, 25);
        contentPane.add(btnNewButton);

        dosyasecbtnNewButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Dosyaları", "pdf"));
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFileName = selectedFile.getName(); // Dosya adı
                String sourcePath = selectedFile.getAbsolutePath();
                String destinationPath = "src/pdf_ds/" + selectedFileName;

                try {
                    // Klasör oluştur
                    File pdfFolder = new File("src/pdf_ds");
                    if (!pdfFolder.exists()) {
                        pdfFolder.mkdirs();
                    }

                    // Dosyayı kopyala
                    Files.copy(Paths.get(sourcePath), Paths.get(destinationPath));
                    JOptionPane.showMessageDialog(null, "Dosya Seçildi ve Yüklendi: " + selectedFileName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Dosya yüklenirken bir hata oluştu.");
                    ex.printStackTrace();
                }
            }
        });

        pdfyüklebtnNewButton_1.addActionListener(e -> {
            String dersAdi = DersAdıtextField.getText().trim();
            String konu = KonutextField.getText().trim();

            if (dersAdi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ders adı boş olamaz!");
                return;
            }

            int dersId = getDersIdByAdi(dersAdi); // Ders ID'sini almak için
            if (dersId == -1) {
                JOptionPane.showMessageDialog(null, "Ders bulunamadı! Geçerli bir ders adı girin.");
                return;
            }

            if (selectedFileName == null || selectedFileName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lütfen bir PDF dosyası seçin.");
                return;
            }

            String pdfPath = "src/pdf_ds/" + selectedFileName; // PDF dosyasının kaydedileceği yol

            // Veritabanına kaydetme işlemi
            savePdfData(dersId, konu, selectedFileName, pdfPath);

            // Tabloyu güncelleme
            DefaultTableModel model = (DefaultTableModel) pdfTable.getModel();
            model.addRow(new Object[]{dersAdi, konu, selectedFileName});

            JOptionPane.showMessageDialog(null, "PDF başarıyla yüklendi ve veritabanına kaydedildi!");
        });
    }

 // Ders Adı ile Ders ID'sini almak için metod
    private int getDersIdByAdi(String dersAdi) {
        String query = "SELECT ID FROM Dersler WHERE DersAdi = ?";
        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, dersAdi);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Ders bulunamazsa -1 dönecek
    }

    // PDF dosyasını veritabanına kaydetmek için metod
    private void savePdfData(int dersId, String konu, String dosyaAdi, String dosyaYolu) {
        String query = "INSERT INTO pdf_dosyaları (ders_id, konu, dosya_adi, dosya_yolu) VALUES (?, ?, ?, ?)";
        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, dersId); // Ders ID'sini ekliyoruz
            ps.setString(2, konu); // Konuyu ekliyoruz
            ps.setString(3, dosyaAdi); // Dosya adını ekliyoruz
            ps.setString(4, dosyaYolu); // Dosya yolunu ekliyoruz
            ps.executeUpdate(); // Veritabanına ekleme işlemini gerçekleştiriyoruz
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
}
