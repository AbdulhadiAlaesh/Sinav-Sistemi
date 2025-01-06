package dönem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import java.awt.Dialog.ModalExclusionType;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import VeriTabanı_Baglantısı.veribaglanti;
import Ögrenci_Paneli.*;

public class girisclass extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField girisİsimtextField_2;
	private JPasswordField GirispasswordField;
	
	public static int loggedInStudentId; //değiştir sonra

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					girisclass frame = new girisclass();
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
	public girisclass() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Student-3-icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 689, 616);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 204, 0));
		panel_1.setBounds(0, 0, 679, 579);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Office-Customer-Male-Light-icon.png"));
		lblNewLabel_3.setBounds(101, 184, 74, 81);
		panel_1.add(lblNewLabel_3);
		
		girisİsimtextField_2 = new JTextField();
		girisİsimtextField_2.setBounds(202, 233, 199, 27);
		panel_1.add(girisİsimtextField_2);
		girisİsimtextField_2.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("New label");
		lblNewLabel_3_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Security-Password-2-icon.png"));
		lblNewLabel_3_1.setBounds(101, 296, 74, 81);
		panel_1.add(lblNewLabel_3_1);
		
		JButton GirisKapatbtnNewButton_1 = new JButton("Cancel");
		GirisKapatbtnNewButton_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		GirisKapatbtnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Close-2-icon.png"));
		GirisKapatbtnNewButton_1.setBounds(133, 477, 133, 48);
		panel_1.add(GirisKapatbtnNewButton_1);
		
		JButton GirisbtnNewButton_1_1 = new JButton("Login");
		GirisbtnNewButton_1_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
		GirisbtnNewButton_1_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\App-login-manager-icon.png"));
		GirisbtnNewButton_1_1.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
	                String kullaniciAdi = girisİsimtextField_2.getText();
	                String sifre = new String(GirispasswordField.getPassword());

	                String kullaniciTipi = kullaniciKontrol(kullaniciAdi, sifre);

	                if (kullaniciTipi != null) {
	                    if (kullaniciTipi.equals("ogrenci")) {
	                        JOptionPane.showMessageDialog(null, "Giriş Başarılı! Öğrenci paneline yönlendiriliyorsunuz.");
	                        new Ögr_Anasayfa().setVisible(true);
	                        setVisible(false);
	                    } else if (kullaniciTipi.equals("ogretmen")) {
	                        JOptionPane.showMessageDialog(null, "Giriş Başarılı! Öğretmen paneline yönlendiriliyorsunuz.");
	                        new HocaPanel().setVisible(true);
	                        setVisible(false);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Giriş Başarısız! Kullanıcı adı veya şifre hatalı.");
	                }
	            }
	        });
	        GirisbtnNewButton_1_1.setBounds(320, 477, 133, 48);
	        panel_1.add(GirisbtnNewButton_1_1);
	    
    

		
		JLabel lblNewLabel = new JLabel("  Giriş Alanı");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Student-3-icon.png"));
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 53, 659, 72);
		panel_1.add(lblNewLabel);
		
		GirispasswordField = new JPasswordField();
		GirispasswordField.setBounds(202, 340, 199, 27);
		panel_1.add(GirispasswordField);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Teacher-icon.png"));
		lblNewLabel_1.setBounds(439, 57, 74, 72);
		panel_1.add(lblNewLabel_1);
	}
	
	 private String kullaniciKontrol(String kullaniciAdi, String sifre) {
	        String sqlOgrenci = "SELECT * FROM Ogrenci WHERE KullaniciAdi = ? AND Parola = ?";
	        String sqlOgretmen = "SELECT * FROM Ogretmen WHERE KullaniciAdi = ? AND Parola = ?";

	        try (Connection conn = veribaglanti.getConnection()) {
	            // Öğrenci tablosunu kontrol et
	            try (PreparedStatement pstmt = conn.prepareStatement(sqlOgrenci)) {
	                pstmt.setString(1, kullaniciAdi);
	                pstmt.setString(2, sifre);
	                ResultSet rs = pstmt.executeQuery();
	                if (rs.next()) {
	                	
	                    return "ogrenci";
	                }
	            }

	            // Öğretmen tablosunu kontrol et
	            try (PreparedStatement pstmt = conn.prepareStatement(sqlOgretmen)) {
	                pstmt.setString(1, kullaniciAdi);
	                pstmt.setString(2, sifre);
	                ResultSet rs = pstmt.executeQuery();
	                if (rs.next()) {
	                    return "ogretmen";
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return null; // Kullanıcı bulunamadı
	    }
	
	
	
	
	
	
	
	
	
	
}
