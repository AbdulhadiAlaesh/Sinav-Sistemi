package Ögrenci_Paneli;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Ögr_Mesaj extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfOgrenciAdi, tfParola;
    private JButton btnGirisYap;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Ögr_Mesaj frame = new Ögr_Mesaj();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Ögr_Mesaj() {
        setTitle("Öğrenci Mesaj Giriş");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 644, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(102, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblOgrenciAdi = new JLabel("Ögrenci Adı:");
        lblOgrenciAdi.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
        lblOgrenciAdi.setBounds(166, 13, 82, 63);
        contentPane.add(lblOgrenciAdi);

        tfOgrenciAdi = new JTextField();
        tfOgrenciAdi.setBounds(258, 17, 198, 39);
        contentPane.add(tfOgrenciAdi);
        tfOgrenciAdi.setColumns(10);

        JLabel lblParola = new JLabel("Parola:");
        lblParola.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
        lblParola.setBounds(166, 86, 54, 63);
        contentPane.add(lblParola);

        tfParola = new JPasswordField();
        tfParola.setBounds(258, 99, 198, 39);
        contentPane.add(tfParola);
        tfParola.setColumns(10);

        btnGirisYap = new JButton("Giriş Yap");
        btnGirisYap.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
        btnGirisYap.setBounds(241, 162, 213, 63);
        contentPane.add(btnGirisYap);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Student-3-icon.png"));
        lblNewLabel.setBounds(80, 13, 76, 72);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Icons8-Windows-8-Security-Password-2.32.png"));
        lblNewLabel_1.setBounds(102, 99, 54, 50);
        contentPane.add(lblNewLabel_1);

        btnGirisYap.addActionListener(e -> {
            String ogrenciAdi = tfOgrenciAdi.getText().trim();
            String parola = tfParola.getText().trim();

            if (ogrenciAdi.isEmpty() || parola.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lütfen Öğrenci Adı ve Parola girin.");
                return;
            }
            if (checkPasswordInOgrenci(parola)) {
                if (checkStudentNameInMessages(ogrenciAdi)) {
                    JOptionPane.showMessageDialog(null, "Giriş başarılı, Mesajlar sayfasına yönlendiriliyorsunuz.");
                    dispose();
                    // Öğrenci adını MesajlarPage sınıfına ilet
                    MesajlarPage mesajlarPage = new MesajlarPage(ogrenciAdi);
                    mesajlarPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Bu öğrencinin mesajı bulunmamaktadır.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Öğrenci Adı veya parola hatalı!");
            }
        });
    }

    private boolean checkPasswordInOgrenci(String parola) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "12345";
        boolean passwordFound = false;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Parola sadece Ogrenci tablosunda kontrol ediliyor
            String query = "SELECT * FROM Ogrenci WHERE parola = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, parola);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                passwordFound = true;  // Parola bulundu
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı kurulamadı: " + e.getMessage());
        }

        return passwordFound;
    }

    private boolean checkStudentNameInMessages(String ogrenciAdi) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "12345";
        boolean studentNameExists = false;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Öğrenci adı sadece Ogrenci_Mesaj tablosunda kontrol ediliyor
            String query = "SELECT * FROM Ogrenci_Mesaj WHERE ogrenci_adi = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ogrenciAdi);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                studentNameExists = true;  // Öğrenci adı bulundu
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı kurulamadı: " + e.getMessage());
        }

        return studentNameExists;
    }
}
