package dönem;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import VeriTabanı_Baglantısı.veribaglanti;
import dönem.DogrulamaPenceresi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import VeriTabanı_Baglantısı.veribaglanti;
import MailGönderici.MailGönderici;


public class KayıtClass extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField KullanıcıtextField;
    private JPasswordField passwordField;
    private JTextField branşTextField;
    private JComboBox<String> comboKullaniciTuru;
    private DogrulamaPenceresi dogrulamaPenceresi;
    
    
    private int dogrulamaKodu;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(() -> {
            try {
                KayıtClass frame = new KayıtClass();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public KayıtClass() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png")); // İkon yolunu değiştirin
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 676, 622);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(102, 204, 51));
        panel.setBounds(0, 0, 662, 585);
        contentPane.add(panel);

        JLabel lblNewLabel = new JLabel("Kayıt Alanı");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 31));
        lblNewLabel.setBounds(0, 40, 662, 81);
        panel.add(lblNewLabel);

        KullanıcıtextField = new JTextField();
        KullanıcıtextField.setBounds(215, 150, 183, 25);
        panel.add(KullanıcıtextField);

        passwordField = new JPasswordField();
        passwordField.setBounds(215, 200, 183, 25);
        panel.add(passwordField);

        branşTextField = new JTextField();
        branşTextField.setBounds(215, 250, 183, 25);
        branşTextField.setEnabled(false); // Varsayılan olarak devre dışı
        panel.add(branşTextField);

        JLabel lblBrans = new JLabel("Branş:");
        lblBrans.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
        lblBrans.setBounds(133, 250, 72, 25);
        panel.add(lblBrans);

        comboKullaniciTuru = new JComboBox<>(new String[]{"Öğrenci", "Öğretmen"});
        comboKullaniciTuru.setBounds(215, 300, 183, 25);
        panel.add(comboKullaniciTuru);

        comboKullaniciTuru.addActionListener(e -> {
            String secim = (String) comboKullaniciTuru.getSelectedItem();
            if ("Öğrenci".equals(secim)) {
                branşTextField.setEnabled(false); // Öğrenci için devre dışı
                branşTextField.setText(""); // Temizle
            } else {
                branşTextField.setEnabled(true); // Öğretmen için aktif
            }
        });

        JButton SavebtnSave = new JButton("Save");
        SavebtnSave.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\App-login-manager-icon.png"));
        SavebtnSave.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 18));
        SavebtnSave.setBounds(215, 350, 133, 48);
        panel.add(SavebtnSave);

        SavebtnSave.addActionListener(e -> {
            String kullaniciAdi = KullanıcıtextField.getText();
            String sifre = new String(passwordField.getPassword());
            String kullaniciTuru = (String) comboKullaniciTuru.getSelectedItem();
            String brans = branşTextField.getText();

            if (kullaniciAdi.isEmpty() || sifre.isEmpty() || (kullaniciTuru.equals("Öğretmen") && brans.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun!");
                return;
            }

            try {
            	 veritabaniKaydet(kullaniciAdi, sifre, kullaniciTuru, brans);
                 JOptionPane.showMessageDialog(null, "Kayıt Başarılı!");
                 //gecikmeliPencereAc(new DogrulamaPenceresi());
                 
                 
                 DogrulamaPenceresi dogrulamaPenceresi = new DogrulamaPenceresi(dogrulamaKodu);
                 dogrulamaPenceresi.setVisible(true);
                 setVisible(false);
                 
                 
                 try {
                	    // 4 basamaklı rastgele doğrulama kodu üret
                	    //Random random = new Random();
                	   

                	    String subject = "Sisteme Hoş Geldiniz";
                	    String messageText = "Merhaba,\n\n" +
                	                         "Sistemimize başarılı bir şekilde kayıt oldunuz. " +
                	                         "Bilgilerinizi dikkatlice saklayınız.\n\n" +
                	                         "Doğrulama Kodunuz: " + dogrulamaKodu + "\n\n" +
                	                         "İyi günler dileriz!";
                	    
                	    // E-posta gönderimi
                	    MailGönderici.sendEmail(kullaniciAdi, subject, messageText);

                	    JOptionPane.showMessageDialog(null, 
                	        "Kayıt bilgilendirme e-postası başarıyla gönderildi! : ");
                	} catch (Exception emailException) {
                	    JOptionPane.showMessageDialog(null, "E-posta gönderimi sırasında bir hata oluştu: " + emailException.getMessage());
                	    emailException.printStackTrace();
                	}
                 
                 
               

                 
                 
                 
                 
                 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Kayıt Başarısız! Hata: " + ex.getMessage());
                ex.printStackTrace();
            }
        });




        JButton İptalbtnNewButton = new JButton("Cancel");
        İptalbtnNewButton.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Close-2-icon.png"));
        İptalbtnNewButton.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 18));
        İptalbtnNewButton.setBounds(360, 350, 149, 48);
        panel.add(İptalbtnNewButton);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Office-Customer-Male-Light-icon.png"));
        lblNewLabel_1.setBounds(141, 109, 64, 72);
        panel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("");
        lblNewLabel_1_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Icons8-Windows-8-Security-Password-2.32.png"));
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1.setBounds(133, 187, 72, 53);
        panel.add(lblNewLabel_1_1);

        İptalbtnNewButton.addActionListener(e -> dispose());
    }

    private void veritabaniKaydet(String kullaniciAdi, String sifre, String kullaniciTuru, String brans) throws SQLException {
        String sql;
        if ("Öğrenci".equals(kullaniciTuru)) {
            sql = "INSERT INTO Ogrenci (kullaniciadi, parola, dogrulamaKodu) VALUES (?, ?, ?)";
        } else {
            sql = "INSERT INTO Ogretmen (kullaniciadi, parola, brans, dogrulamaKodu) VALUES (?, ?, ?, ?)";
        }

        // 4 basamaklı rastgele doğrulama kodu oluştur
         dogrulamaKodu = 1000 + new Random().nextInt(9000);

        try (Connection conn = veribaglanti.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, kullaniciAdi);
            ps.setString(2, sifre);

            if ("Öğrenci".equals(kullaniciTuru)) {
                ps.setInt(3, dogrulamaKodu); // Doğrulama kodu üçüncü parametre
            } else {
                ps.setString(3, brans);      // Branş üçüncü parametre
                ps.setInt(4, dogrulamaKodu); // Doğrulama kodu dördüncü parametre
            }

            ps.executeUpdate();
        }
    }

    
   /* private void gecikmeliPencereAc(JFrame yeniPencere) {
        // Şu anki pencereyi kapat
        this.setVisible(false);

        // 2 saniye bekleyip yeni pencereyi aç
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yeniPencere.setVisible(true);
            }
        });
        timer.setRepeats(false); // Sadece bir kez çalışsın
        timer.start();
    }*/
    
    
    
   
    

}//bura bizim bitiş parantezi    
