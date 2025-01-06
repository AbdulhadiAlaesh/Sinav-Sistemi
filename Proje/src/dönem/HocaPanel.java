 package dönem;

import java.awt.EventQueue;
import SınavEKLE.Sınav_Ekle;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.contentstream.operator.markedcontent.BeginMarkedContentSequenceWithProperties;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;
import Ögrenci_Listeleme.Ögernci_Listeleme_Alanı;


import javax.swing.JProgressBar;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import VeriTabanı_Baglantısı.veribaglanti;
import Alısıtrma.*;
import Eski_Sınavlarım.ESKİ_Sinav;


public class HocaPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_gösterme = new JPanel();
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HocaPanel frame = new HocaPanel();
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
	public HocaPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Teacher-icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1418, 770);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setBounds(0, 0, 1404, 83);
		contentPane.add(menuBar_1);
		
		JMenu mnNewMenu = new JMenu("Sınav Yönetimi");
		mnNewMenu.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 18));
		mnNewMenu.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Test-paper-icon (1).png"));
		menuBar_1.add(mnNewMenu);
		
		JMenuItem mnıtmNewMenuItem = new JMenuItem("Sınav Ekle");
		mnıtmNewMenuItem.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Test-paper-icon.png"));
		mnıtmNewMenuItem.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
		mnNewMenu.add(mnıtmNewMenuItem);
		
		
		mnıtmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Sınav_Ekle sınav_Ekle = new Sınav_Ekle();
				
				sınav_Ekle.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		
		
		
		JMenuItem mnıtmNewMenuItem_1 = new JMenuItem("Eski Sınavlar");
		mnıtmNewMenuItem_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
		mnıtmNewMenuItem_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\tests-icon.png"));
		mnNewMenu.add(mnıtmNewMenuItem_1);
		
		mnıtmNewMenuItem_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ESKİ_Sinav sinav = new ESKİ_Sinav();
				sinav.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				sinav.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		JMenu mnNewMenu_1 = new JMenu("Ögrenci Yönetimi");
		mnNewMenu_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Student-3-icon.png"));
		mnNewMenu_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		menuBar_1.add(mnNewMenu_1);
		
		
		
		
		
		
		
		
		JMenuItem mnıtmNewMenuItem_3 = new JMenuItem("Ögrenci Listeleme");
		mnıtmNewMenuItem_3.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Student-3d-Default-icon.png"));
		mnıtmNewMenuItem_3.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_1.add(mnıtmNewMenuItem_3);
		
		mnıtmNewMenuItem_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Ögernci_Listeleme_Alanı ögr_listeleme = new Ögernci_Listeleme_Alanı();
				ögr_listeleme.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JMenuItem mnıtmNewMenuItem_2 = new JMenuItem("Ögrenciye Sınav Atma");
		mnıtmNewMenuItem_2.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\submit-document-icon.png"));
		mnıtmNewMenuItem_2.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_1.add(mnıtmNewMenuItem_2);
		
		mnıtmNewMenuItem_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
					Ögrenci_Sınav_Atma ögrenci_Sınav_Atma = new Ögrenci_Sınav_Atma();
					
					ögrenci_Sınav_Atma.setVisible(true);
					setVisible(false);
				
				
			}
		});
		
		JMenuItem mnıtmNewMenuItem_4 = new JMenuItem("Sonuc Takip");
		mnıtmNewMenuItem_4.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Election-Result-icon.png"));
		mnıtmNewMenuItem_4.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 17));
		mnNewMenu_1.add(mnıtmNewMenuItem_4);
		
		mnıtmNewMenuItem_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Sonuc_Takibi takibi = new Sonuc_Takibi();
				 takibi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Pencereyi gizle, kapatma değil
				takibi.setVisible(true);
				
			}
		});
		
		
		
		
		JMenu mnNewMenu_2 = new JMenu("Materyeller");
		mnNewMenu_2.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Science-Courses-icon.png"));
		mnNewMenu_2.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		menuBar_1.add(mnNewMenu_2);
		
		
		
		
		
		
		
		
		
		
		//pdf yükleme Butonu
		JMenuItem PdfyüklemnıtmNewMenuItem_6 = new JMenuItem("PDF Yükle");
		PdfyüklemnıtmNewMenuItem_6.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Adobe-PDF-Document-icon.png"));
		PdfyüklemnıtmNewMenuItem_6.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_2.add(PdfyüklemnıtmNewMenuItem_6);
		
		PdfyüklemnıtmNewMenuItem_6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Pdf_Alıstıma_Gönderme pdfgönderme = new Pdf_Alıstıma_Gönderme();
				
				pdfgönderme.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		
		
		
		//Alıştırma butınuna tıklanma olayı
		JMenuItem AlıstırmamnıtmNewMenuItem_5 = new JMenuItem("Alıştırma At");
		AlıstırmamnıtmNewMenuItem_5.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\tests-icon.png"));
		AlıstırmamnıtmNewMenuItem_5.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_2.add(AlıstırmamnıtmNewMenuItem_5);
		
		
		
		AlıstırmamnıtmNewMenuItem_5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
		   Alısıtırma_islemleri alıstırmaAc = new Alısıtırma_islemleri();
		   
		   alıstırmaAc.setVisible(true);
		   
		   setVisible(false);

				
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JMenu mnNewMenu_3 = new JMenu("Duyurular");
		mnNewMenu_3.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Notification-icon.png"));
		mnNewMenu_3.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		menuBar_1.add(mnNewMenu_3);
		
	
		
		
			JPanel panel_gösterme = new JPanel();
		panel_gösterme.setBackground(new Color(102, 204, 102));
		panel_gösterme.setBounds(10, 140, 1394, 583);
		contentPane.add(panel_gösterme);
		
		
		
		
		
		
		
			JMenuItem MesajGöndermnıtmNewMenuItem_8 = new JMenuItem("Mesaj Gönder");
		MesajGöndermnıtmNewMenuItem_8.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Send-message-2-icon.png"));
		MesajGöndermnıtmNewMenuItem_8.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_3.add(MesajGöndermnıtmNewMenuItem_8);
		
		//PANELDE KİME MESAJ GÖNDERİLECEĞİNİ GÖSTERİLECEK
		MesajGöndermnıtmNewMenuItem_8.addActionListener(e -> {
		    panel_gösterme.removeAll();
		    panel_gösterme.setLayout(null);

		    // Öğrenci Adı
		    JLabel lblAd = new JLabel("Ögrenci Adı:");
		    lblAd.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 14));
		    lblAd.setBounds(50, 20, 100, 25);
		    panel_gösterme.add(lblAd);

		    JTextField txtAd = new JTextField();
		    txtAd.setBounds(160, 20, 200, 25);
		    panel_gösterme.add(txtAd);

		    // Öğrenci Bölümü
		    JLabel lblSınıf = new JLabel("Bölüm:");
		    lblSınıf.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
		    lblSınıf.setBounds(50, 60, 100, 25);
		    panel_gösterme.add(lblSınıf);

		    JTextField txtSınıf = new JTextField();
		    txtSınıf.setBounds(160, 60, 200, 25);
		    panel_gösterme.add(txtSınıf);

		    // Mesaj
		    JLabel lblMesaj = new JLabel("Mesaj:");
		    lblMesaj.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
		    lblMesaj.setBounds(50, 100, 100, 25);
		    panel_gösterme.add(lblMesaj);

		    JTextArea txtMesaj = new JTextArea();
		    txtMesaj.setLineWrap(true);
		    txtMesaj.setWrapStyleWord(true);
		    txtMesaj.setBounds(160, 100, 300, 100);
		    panel_gösterme.add(txtMesaj);

		    // Ders Seçimi
		    JLabel lblDers = new JLabel("Ders Seçimi:");
		    lblDers.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
		    lblDers.setBounds(50, 220, 100, 25);
		    panel_gösterme.add(lblDers);

		    // Veritabanından ders bilgilerini çek
		    DefaultListModel<String> dersModel = new DefaultListModel<>();
		    try (Connection connection = DriverManager.getConnection(
		            "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;user=sa;password=12345;encrypt=true;trustServerCertificate=true")) {

		        String dersSQL = "SELECT DersAdi FROM dersler";
		        try (PreparedStatement dersStmt = connection.prepareStatement(dersSQL);
		             ResultSet rs = dersStmt.executeQuery()) {

		            while (rs.next()) {
		                dersModel.addElement(rs.getString("DersAdi"));
		            }
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Ders bilgileri alınırken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
		    }

		    JList<String> DersBilgileri = new JList<>(dersModel);
		    DersBilgileri.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		    JScrollPane scrollPane = new JScrollPane(DersBilgileri);
		    scrollPane.setBounds(160, 220, 200, 100);
		    panel_gösterme.add(scrollPane);

		    // Sonuç Mesajı
		    JLabel lblSonuç = new JLabel("");
		    lblSonuç.setForeground(Color.RED);
		    lblSonuç.setBounds(50, 380, 400, 25);
		    panel_gösterme.add(lblSonuç);

		    // Gönder Butonu
		    JButton btnGönder = new JButton("Gönder");
		    btnGönder.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Custom-Icon-Design-Flatastic-10-Email-send.24.png"));
		    btnGönder.setBounds(172, 345, 129, 25);

		    btnGönder.addActionListener(actionEvent -> {
		        String ad = txtAd.getText().trim();
		        String mesaj = txtMesaj.getText().trim();
		        String secilenDers = DersBilgileri.getSelectedValue();

		        lblSonuç.setText("");

		        try (Connection connection = DriverManager.getConnection(
		                "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;user=sa;password=12345;encrypt=true;trustServerCertificate=true")) {

		            String kontrolSQL = "SELECT OgrenciID FROM ogrenci WHERE kullaniciAdi = ?";
		            try (PreparedStatement kontrolStmt = connection.prepareStatement(kontrolSQL)) {
		                kontrolStmt.setString(1, ad);
		                ResultSet rs = kontrolStmt.executeQuery();

		                if (!rs.next()) {
		                    lblSonuç.setText("Bu öğrenci sistemde yok!");
		                    lblSonuç.setForeground(Color.RED);
		                } else if (mesaj.isEmpty() || secilenDers == null) {
		                    lblSonuç.setText("Lütfen tüm alanları doldurun ve bir ders seçin!");
		                    lblSonuç.setForeground(Color.RED);
		                } else {
		                    int ogrenciId = rs.getInt("OgrenciID");

		                    String ekleSQL = "INSERT INTO ogrenci_mesaj (ogrenci_adi, mesaj, ders_adi, tarih) VALUES (?, ?, ?, GETDATE())";
		                    try (PreparedStatement ekleStmt = connection.prepareStatement(ekleSQL)) {
		                        ekleStmt.setString(1, ad);
		                        ekleStmt.setString(2, mesaj);
		                        ekleStmt.setString(3, secilenDers);
		                        ekleStmt.executeUpdate();

		                        lblSonuç.setText("Mesaj başarıyla gönderildi!");
		                        lblSonuç.setForeground(new Color(0, 128, 0));

		                        JOptionPane.showMessageDialog(null,
		                                "Ad: " + ad +
		                                        "\nMesaj: " + mesaj +
		                                        "\nSeçilen Ders: " + secilenDers,
		                                "Gönderilen Mesaj",
		                                JOptionPane.INFORMATION_MESSAGE);
		                    }
		                }
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            lblSonuç.setText("Bir hata oluştu. Lütfen tekrar deneyin.");
		            lblSonuç.setForeground(Color.RED);
		        }
		    });

		    panel_gösterme.add(btnGönder);

		    panel_gösterme.revalidate();
		    panel_gösterme.repaint();
		});

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	
		
		
		
		
		JMenuItem DuyurupaylaşmnıtmNewMenuItem_7 = new JMenuItem("Duyuru Paylaş");
		DuyurupaylaşmnıtmNewMenuItem_7.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Notification-icon.png"));
		DuyurupaylaşmnıtmNewMenuItem_7.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_3.add(DuyurupaylaşmnıtmNewMenuItem_7);
		

	

		
		// Duyuru Paylaş menü öğesine tıklama olayı
		DuyurupaylaşmnıtmNewMenuItem_7.addActionListener(e -> {
			 System.out.println("Duyuru Paylaş tıklandı!");
		    try {
		        // Kodunuzu buraya koyun
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
	
		    // Panel içeriğini temizleme
		    panel_gösterme.removeAll();
		    panel_gösterme.setLayout(null);

		    // Duyuru Başlığı etiketi
		    JLabel lblBaşlık = new JLabel("Duyuru Başlığı:");
		    lblBaşlık.setFont(new Font("Arial", Font.BOLD, 16));
		    lblBaşlık.setBounds(569, 16, 116, 19);
		    panel_gösterme.add(lblBaşlık);

		    // Duyuru Başlığı için metin alanı
		    JTextField txtBaşlık = new JTextField();
		    txtBaşlık.setFont(new Font("Arial", Font.PLAIN, 14));
		    txtBaşlık.setBounds(569, 45, 247, 27);
		    panel_gösterme.add(txtBaşlık);

		    // Duyuru İçeriği etiketi
		    JLabel lblİçerik = new JLabel("Duyuru İçeriği:");
		    lblİçerik.setFont(new Font("Arial", Font.BOLD, 16));
		    lblİçerik.setBounds(569, 96, 111, 19);
		    panel_gösterme.add(lblİçerik);
		    
		    
		    // Duyuru İçeriği için metin alanı (ScrollPane olmadan)
		    JTextArea txtİçerik = new JTextArea();
		    txtİçerik.setFont(new Font("Arial", Font.PLAIN, 14));
		    txtİçerik.setLineWrap(true);
		    txtİçerik.setWrapStyleWord(true);
		    txtİçerik.setBounds(579, 124, 438, 281);
		    panel_gösterme.add(txtİçerik);


		  

		    // Sonuç mesajı etiketi
		    JLabel lblSonuç = new JLabel("");
		    lblSonuç.setBackground(SystemColor.textHighlight);
		    lblSonuç.setFont(new Font("Arial", Font.BOLD, 16));
		    lblSonuç.setForeground(new Color(0, 51, 0));
		    lblSonuç.setBounds(762, 96, 214, 19);
		    panel_gösterme.add(lblSonuç);
		    
		    
		    
		    
		    
		      // Gönder butonu
		    JButton btnGönder = new JButton("Gönder");
		    btnGönder.setFont(new Font("Arial", Font.BOLD, 16));
		    btnGönder.setBounds(875, 427, 116, 27);
		    panel_gösterme.add(btnGönder);
		    
		    
		    
		    btnGönder.addActionListener(actionEvent -> {
		        String baslik = txtBaşlık.getText().trim();
		        String icerik = txtİçerik.getText().trim();

		        if (baslik.isEmpty() || icerik.isEmpty()) {
		            lblSonuç.setForeground(Color.RED);
		            lblSonuç.setText("Lütfen tüm alanları doldurun!");
		        } else {
		            try (Connection connection = DriverManager.getConnection(
		                    "jdbc:sqlserver://localhost:1433;databaseName=UzaktanSinavSistemi;user=sa;password=12345;encrypt=true;trustServerCertificate=true")) {
		                
		                // Duyuruyu veritabanına ekle
		                String ekleSQL = "INSERT INTO duyurular (baslik, icerik) VALUES (?, ?)";
		                try (PreparedStatement stmt = connection.prepareStatement(ekleSQL)) {
		                    stmt.setString(1, baslik);
		                    stmt.setString(2, icerik);
		                    stmt.executeUpdate();
		                    
		                    lblSonuç.setForeground(new Color(0, 128, 0));
		                    lblSonuç.setText("Duyuru başarıyla gönderildi!");

		                    JOptionPane.showMessageDialog(null,
		                            "Duyuru Başlığı: " + baslik +
		                            "\nDuyuru İçeriği: " + icerik,
		                            "Gönderilen Duyuru",
		                            JOptionPane.INFORMATION_MESSAGE);
		                }
		            } catch (Exception ex) {
		                lblSonuç.setForeground(Color.RED);
		                lblSonuç.setText("Duyuru gönderilirken bir hata oluştu!");
		                ex.printStackTrace();
		            }
		        }
		    });


		    // Paneli yeniden çiz
		    panel_gösterme.revalidate();
		    panel_gösterme.repaint();
		});
		


		
		
		
		
		
		
		
		
		
		JMenuItem mnıtmDestekMasasıNewMenuItem_3_1 = new JMenuItem("Destek Masası");
		mnıtmDestekMasasıNewMenuItem_3_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\support-icon.png"));
		mnıtmDestekMasasıNewMenuItem_3_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 20));
		menuBar_1.add(mnıtmDestekMasasıNewMenuItem_3_1);
		
		mnıtmDestekMasasıNewMenuItem_3_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Hoca_destek_Masası destek_Masası = new Hoca_destek_Masası();
				 destek_Masası.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Pencereyi gizle, kapatma değil
				destek_Masası.setVisible(true);
				
			}
		});
		
		JMenuItem mnıtmÇıkısNewMenuItem_4_1 = new JMenuItem("Çıkış");
		mnıtmÇıkısNewMenuItem_4_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Button-Close-icon.png"));
		mnıtmÇıkısNewMenuItem_4_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 20));
		mnıtmÇıkısNewMenuItem_4_1.setBounds(839, 0, 367, 81);
		contentPane.add(mnıtmÇıkısNewMenuItem_4_1);

		// Çıkış ikonuna basılınca
		mnıtmÇıkısNewMenuItem_4_1.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Sistemi kapatmadan önce 3 saniye beklemek için yeni bir iş parçacığı (thread) oluşturuyoruz
		        new Thread(new Runnable() {
		            @Override
		            public void run() {
		                try {
		                    // 3 saniye bekleme (3000 ms)
		                    Thread.sleep(500);
		                    
		                    // Giriş ekranını gösteriyoruz
		                    girisclass giris = new girisclass();
		                    giris.setVisible(true);  // Giriş ekranını açıyoruz
		                    
		                    // Ana pencereyi gizliyoruz
		                    setVisible(false);
		                    
		                    // Giriş ekranı açıldığında, ana pencereyi tamamen kapatıyoruz
		                    dispose(); 
		                    
		                } catch (InterruptedException ex) {
		                    ex.printStackTrace();
		                }
		            }
		        }).start();

		        // Sistemi kapatmak
		        //System.exit(0); // Bu komut ile uygulama sonlanır ancak önce giriş ekranı açılır
		    }
		});


		
		
		
	}
}
