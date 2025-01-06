package dönem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import VeriTabanı_Baglantısı.veribaglanti;

public class Ögrenci_Sınav_Atma extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldAd;
    private JTextField textFieldDersAd;
    private JTable table;
    private String selectedFilePath = "";
    private boolean isFileSelected = false;
   

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC sürücüsü bulunamadı: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Bağlantı hatası:");
            yazdirSQLException(e);
        }
        return connection;
    }

    // SQLException detaylarını yazdıran metot
    public static void yazdirSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                System.err.println("SQL Hatası Mesajı: " + e.getMessage());
                System.err.println("SQL Durum Kodu: " + ((SQLException) e).getSQLState());
                System.err.println("Hata Kodu: " + ((SQLException) e).getErrorCode());
                Throwable sebep = ex.getCause();
                if (sebep != null) {
                    System.err.println("Neden: " + sebep);
                }
            }
        }
    }

    // Öğrenci var mı kontrol et
    private static boolean ogrenciVarMi(String ogrenciAdi) {
        String query = "SELECT * FROM Ogrenci WHERE KullaniciAdi = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ogrenciAdi);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ders var mı kontrol et
    private static boolean dersVarMi(String dersAdi) {
        String query = "SELECT * FROM Dersler WHERE DersAdi = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dersAdi);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Öğrenci ve ders kaydını veritabanına ekle
    private static void kaydetOgrenciDers(String ogrenciAdi, String dersAdi, String dosyaAdi, String dosyaYolu) {
        String query = "INSERT INTO ogrenci_sinav_atma (ogrenci_adi, ders_adi, dosya_adi, dosya_yolu) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ogrenciAdi);
            stmt.setString(2, dersAdi);
            stmt.setString(3, dosyaAdi);
            stmt.setString(4, dosyaYolu);  // Add the file path here
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Öğrenci ve ders kaydını veritabanından sil
    private static void silOgrenciDers(String ogrenciAdi, String dersAdi, String dosyaAdi, String dosyaYolu) {
        String query = "DELETE FROM ogrenci_sinav_atma WHERE ogrenci_adi = ? AND ders_adi = ? AND dosya_adi = ? AND dosya_yolu = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ogrenciAdi);
            stmt.setString(2, dersAdi);
            stmt.setString(3, dosyaAdi);
            stmt.setString(4, dosyaYolu); // Dosya yolu parametresi eklendi
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Veritabanından silindi: " + ogrenciAdi + ", " + dersAdi + ", " + dosyaAdi + ", " + dosyaYolu);
            } else {
                System.out.println("Silme işlemi başarısız! Kayıt bulunamadı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // GUI class initialization
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Ögrenci_Sınav_Atma frame = new Ögrenci_Sınav_Atma();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Ögrenci_Sınav_Atma() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1177, 537);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 255, 255));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblAd = new JLabel("AD:");
        lblAd.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 20));
        lblAd.setBounds(52, 86, 112, 26);
        contentPane.add(lblAd);

        textFieldAd = new JTextField();
        textFieldAd.setBounds(138, 90, 163, 26);
        contentPane.add(textFieldAd);

        JLabel lblDersAd = new JLabel("Ders Adı:");
        lblDersAd.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 20));
        lblDersAd.setBounds(21, 150, 107, 26);
        contentPane.add(lblDersAd);

        textFieldDersAd = new JTextField();
        textFieldDersAd.setBounds(138, 154, 163, 26);
        contentPane.add(textFieldDersAd);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Ad", "Ders Adı", "Dosya Adı"}
        ));
        table.setBounds(404, 48, 702, 308);
        contentPane.add(table);

        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.setBounds(425, 400, 112, 31);
        btnKaydet.addActionListener(e -> kaydet());
        contentPane.add(btnKaydet);

        JButton btnDosyaSec = new JButton("Dosya Seç");
        btnDosyaSec.setBounds(722, 400, 112, 31);
        btnDosyaSec.addActionListener(e -> dosyaSec());
        contentPane.add(btnDosyaSec);

        JButton btnSil = new JButton("Sil");
        btnSil.setBounds(993, 400, 112, 31);
        btnSil.addActionListener(e -> sil());
        contentPane.add(btnSil);
        
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
    }

    private void kaydet() {
        String ad = textFieldAd.getText().trim();
        String dersAdi = textFieldDersAd.getText().trim();

        if (ad.isEmpty() || dersAdi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Öğrenci ve ders veritabanında var mı kontrol et
        if (!ogrenciVarMi(ad)) {
            JOptionPane.showMessageDialog(this, "Öğrenci bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!dersVarMi(dersAdi)) {
            JOptionPane.showMessageDialog(this, "Ders bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isFileSelected) {
            JOptionPane.showMessageDialog(this, "Lütfen dosya seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedFileName = new File(selectedFilePath).getName();

        // Find the project’s root directory and create a folder for uploaded files
        String projectPath = System.getProperty("user.dir"); // Get the project's root directory
        String folderPath = projectPath + "/src/Ogr_Sınav_Pdf"; // Create the folder path

        // Ensure the folder exists
        File destinationFolder = new File(folderPath);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs(); // Create the folder if it doesn't exist
        }

        String destinationPath = folderPath + "/" + selectedFileName; // Full path to save the file

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{ad, dersAdi, selectedFileName});

        try {
            // Copy the selected file to the project folder
            Files.copy(Paths.get(selectedFilePath), Paths.get(destinationPath));
            JOptionPane.showMessageDialog(this, "Dosya başarıyla yüklendi: " + selectedFilePath, "Başarılı", JOptionPane.INFORMATION_MESSAGE);

            // Veritabanına kaydet
            kaydetOgrenciDers(ad, dersAdi, selectedFileName, destinationPath);

        } catch (IOException e) {
            e.printStackTrace();  // Hata ayrıntılarını konsola yazdır
            JOptionPane.showMessageDialog(this, "Dosya yüklenirken bir hata oluştu: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }




        JOptionPane.showMessageDialog(this, "Bilgiler başarıyla kaydedildi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        textFieldAd.setText("");
        textFieldDersAd.setText("");
        isFileSelected = false;
    }


    private void dosyaSec() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Dosyaları", "pdf"));
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedFilePath = selectedFile.getAbsolutePath();
            isFileSelected = true;
            JOptionPane.showMessageDialog(this, "Dosya başarıyla seçildi: " + selectedFile.getName(), "Dosya Seçildi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void sil() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Silmek için bir satır seçiniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ogrenciAdi = (String) table.getValueAt(selectedRow, 0);
        String dersAdi = (String) table.getValueAt(selectedRow, 1);
        String dosyaAdi = (String) table.getValueAt(selectedRow, 2);

        // Dosya yolunu tabloya eklemediyseniz manuel olarak sağlayın
        String projectPath = System.getProperty("user.dir"); // Proje dizinini al
        String folderPath = projectPath + "/src/Ogr_Sınav_Pdf"; // Dosyanın kaydedildiği klasör
        String dosyaYolu = folderPath + "/" + dosyaAdi; // Tam dosya yolu

        int confirm = JOptionPane.showConfirmDialog(this, "Seçili kayıt ve dosya silinecek. Emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(selectedRow);

            // Dosyayı sil
            File file = new File(dosyaYolu);
            if (file.exists() && file.delete()) {
                JOptionPane.showMessageDialog(this, "Dosya başarıyla silindi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Dosya silinemedi veya bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }

            // Veritabanından sil
            silOgrenciDers(ogrenciAdi, dersAdi, dosyaAdi, dosyaYolu);
        }
    }
    public static void SQLException(SQLException ex) {
        while (ex != null) {
            System.err.println("SQL Hatası Mesajı: " + ex.getMessage());
            System.err.println("SQL Durum Kodu: " + ex.getSQLState());
            System.err.println("Hata Kodu: " + ex.getErrorCode());
            Throwable cause = ex.getCause();
            if (cause != null) {
                System.err.println("Neden: " + cause);
            }
            ex = ex.getNextException(); // Eğer başka bir SQLException varsa, onu da yazdır
        }
    }


}
