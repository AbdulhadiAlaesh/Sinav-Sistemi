package Ögrenci_Paneli;

import java.awt.EventQueue;
import Ögrenci_Paneli.Ögr_Mesaj;
import Ögrenci_Paneli.Slaytlarım;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.xml.simpleparser.NewLineHandler;

import dönem.Hoca_destek_Masası;
import dönem.girisclass;

import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

public class Ögr_Anasayfa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ögr_Anasayfa frame = new Ögr_Anasayfa();
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
	public Ögr_Anasayfa() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Graphicloads-100-Flat-Student.48.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1220, 760);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setBounds(0, 0, 492, 83);
		contentPane.add(menuBar_1);
		
		JMenu mnNewMenu = new JMenu("Sınav Yönetimi");
		mnNewMenu.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Umut-Pulat-Tulliana-2-Korganizer.48.png"));
		mnNewMenu.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 18));
		menuBar_1.add(mnNewMenu);
		
		JMenuItem mnıtmSnavlarm = new JMenuItem("Sınavlarım");
		mnıtmSnavlarm.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Test-paper-icon.png"));
		mnıtmSnavlarm.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 15));
		mnNewMenu.add(mnıtmSnavlarm);
		
		mnıtmSnavlarm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Sinavlarım sinavlarım = new Sinavlarım();
				sinavlarım.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		
		
		JMenu mnNewMenu_2 = new JMenu("Materyeller");
		mnNewMenu_2.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Robinweatherall-Library-Books.48.png"));
		mnNewMenu_2.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		menuBar_1.add(mnNewMenu_2);
		
		JMenuItem PdfyüklemnıtmNewMenuItem_6 = new JMenuItem("Slaytlarım");
		PdfyüklemnıtmNewMenuItem_6.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Icons-Land-Vista-Multimedia-Slide-Show.48.png"));
		PdfyüklemnıtmNewMenuItem_6.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_2.add(PdfyüklemnıtmNewMenuItem_6);
		
		PdfyüklemnıtmNewMenuItem_6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Slaytlarım slaytlarım = new Slaytlarım();
				
				slaytlarım.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		JMenuItem AlıstırmamnıtmNewMenuItem_5 = new JMenuItem("Alıştırmalarım");
		AlıstırmamnıtmNewMenuItem_5.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Inipagi-Job-Seeker-Note.48.png"));
		AlıstırmamnıtmNewMenuItem_5.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_2.add(AlıstırmamnıtmNewMenuItem_5);
		
		AlıstırmamnıtmNewMenuItem_5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Ögr_Alıstırma alıstırma = new Ögr_Alıstırma();
				alıstırma.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JMenu mnNewMenu_3 = new JMenu("Duyurular");
		mnNewMenu_3.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Notification-icon.png"));
		mnNewMenu_3.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		menuBar_1.add(mnNewMenu_3);
		
		JMenuItem MesajGöndermnıtmNewMenuItem_8 = new JMenuItem("Mesajlarım");
		MesajGöndermnıtmNewMenuItem_8.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Send-message-2-icon.png"));
		MesajGöndermnıtmNewMenuItem_8.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_3.add(MesajGöndermnıtmNewMenuItem_8);
		
		MesajGöndermnıtmNewMenuItem_8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Ögr_Mesaj mesaj = new Ögr_Mesaj();
				mesaj.setVisible(true);
				setVisible(false);
				
			}
		});
		
		
		
		
		
		JMenuItem DuyurupaylaşmnıtmNewMenuItem_7 = new JMenuItem("Duyurular");
		DuyurupaylaşmnıtmNewMenuItem_7.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\Notification-icon.png"));
		DuyurupaylaşmnıtmNewMenuItem_7.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 16));
		mnNewMenu_3.add(DuyurupaylaşmnıtmNewMenuItem_7);
		
		DuyurupaylaşmnıtmNewMenuItem_7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Duyurular Ögrenciduyurular = new Duyurular();
				Ögrenciduyurular.setVisible(true);
				
				setVisible(false);
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JMenuItem mnıtmDestekMasasıNewMenuItem_3_1 = new JMenuItem("Destek Masası");
		mnıtmDestekMasasıNewMenuItem_3_1.setBackground(new Color(255, 255, 255));
		mnıtmDestekMasasıNewMenuItem_3_1.setIcon(new ImageIcon("C:\\Users\\Acer\\OneDrive - İstanbul University - Cerrahpasa\\Masaüstü\\dönem\\java\\support-icon.png"));
		mnıtmDestekMasasıNewMenuItem_3_1.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 20));
		mnıtmDestekMasasıNewMenuItem_3_1.setBounds(491, 0, 351, 81);
		contentPane.add(mnıtmDestekMasasıNewMenuItem_3_1);
		
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
