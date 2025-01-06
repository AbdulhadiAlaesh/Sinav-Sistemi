package dönem;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import MailGönderici.MailGönderici;

public class Hoca_destek_Masası extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextArea textArea;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Hoca_destek_Masası frame = new Hoca_destek_Masası();
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
    public Hoca_destek_Masası() {
        setTitle("Hoca Destek Masası");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));

        textField = new JTextField();
        panel.add(textField, BorderLayout.CENTER);
        textField.setColumns(10);

        JButton btnSend = new JButton("Gönder");
        panel.add(btnSend, BorderLayout.EAST);

        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userInput = textField.getText();
                if (!userInput.isEmpty()) {
                    textArea.append("Siz: " + userInput + "\n");
                    String response = cevapVer(userInput);
                    textArea.append("Hoca Destek Masası: " + response + "\n");
                    textField.setText("");
                    if (userInput.toLowerCase().contains("sorunum hala devam ediyor")) {
                        openMailWindow();
                    }
                }
            }
        });
    }

    /**
     * Basit bir cevap üreten metod.
     */
    private String cevapVer(String soru) {
        if (soru.toLowerCase().contains("merhaba")) {
            return "Merhaba! Size nasıl yardımcı olabilirim?";
        } else if (soru.toLowerCase().contains("nasılsınız")) {
            return "Ben bir yazılımım, ama iyi diyebilirim. Teşekkürler!";
        } else if (soru.toLowerCase().contains("giriş yapamıyorum")) {
            return "Lütfen kullanıcı adı ve şifrenizi kontrol edin. Sorun devam ederse, sistem yöneticisine başvurun.";
        } else if (soru.toLowerCase().contains("sınav sonuçları görünmüyor")) {
            return "Sınav sonuçları henüz yayınlanmamış olabilir. Daha fazla bilgi için öğretim görevlinize danışabilirsiniz.";
        } else if (soru.toLowerCase().contains("sayfa yüklenmiyor")) {
            return "Bağlantınızı kontrol edin ve tarayıcınızı yenileyin. Sorun devam ederse, teknik destek ile iletişime geçin.";
        } else if (soru.toLowerCase().contains("soru hatalı")) {
            return "Hatalı soruyu rapor edebilirsiniz. Detayları öğretim görevlisine iletin.";
        } else if (soru.toLowerCase().contains("pdf yükleyemiyorum")) {
            return "PDF dosyanızın boyutunu ve formatını kontrol edin. Sorun devam ederse, teknik destek ile iletişime geçin.";
        } else if (soru.toLowerCase().contains("alıştırma ekleyemiyorum")) {
            return "Alıştırma eklerken gerekli alanların doldurulduğundan emin olun. Sorun devam ederse, detayları sistem yöneticisine iletebilirsiniz.";
        } else if (soru.toLowerCase().contains("sınav ekleyemiyorum")) {
            return "Sınav ekleme sırasında gerekli bilgilerin eksiksiz olduğundan emin olun. Sorun devam ederse, sistem yöneticisinden yardım alın.";
        } else if (soru.toLowerCase().contains("bilgisayar donuyor")) {
            return "Bilgisayarınızı yeniden başlatmayı deneyin ve gereksiz programları kapatın. Sorun devam ederse, donanım veya yazılım sorunlarını kontrol edin.";
        } else if (soru.toLowerCase().contains("internet yok")) {
            return "Modeminizi yeniden başlatmayı deneyin. Eğer sorun devam ederse, internet servis sağlayıcınızla iletişime geçin.";
        } else if (soru.toLowerCase().contains("program açılmıyor")) {
            return "Programı kaldırıp yeniden yüklemeyi deneyin. Güncelleme veya uyumluluk sorunlarını kontrol edin.";
        } else if (soru.toLowerCase().contains("ekran kararıyor")) {
            return "Ekran bağlantılarını kontrol edin ve ekranın enerji aldığından emin olun. Sorun devam ederse, ekran veya grafik kartı ayarlarını kontrol edin.";
        } else {
            return "Bu konuda size nasıl yardımcı olabileceğimi bilmiyorum. Daha fazla bilgi verebilir misiniz?";
        }
    }
    
    private void openMailWindow() {
        JFrame mailFrame = new JFrame("Destek ile İletişime Geçin");
        mailFrame.setBounds(150, 150, 400, 300);
        JPanel mailPanel = new JPanel();
        mailPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mailPanel.setLayout(new BorderLayout(0, 0));
        mailFrame.setContentPane(mailPanel);

        JPanel inputPanel = new JPanel();
        mailPanel.add(inputPanel, BorderLayout.NORTH);
        inputPanel.setLayout(new BorderLayout(0, 0));

        JLabel lblEmail = new JLabel("E-posta Adresiniz:");
        inputPanel.add(lblEmail, BorderLayout.WEST);

        JTextField emailField = new JTextField();
        inputPanel.add(emailField, BorderLayout.CENTER);
        emailField.setColumns(20);

        JTextArea messageArea = new JTextArea("Sorununuzu buraya yazın...");
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        mailPanel.add(messageScrollPane, BorderLayout.CENTER);

        JButton sendMailButton = new JButton("Gönder");
        mailPanel.add(sendMailButton, BorderLayout.SOUTH);

        sendMailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String message = messageArea.getText();
                if (!email.isEmpty() && !message.isEmpty()) {
                    // Destek masasına e-posta gönderme işlemi
                    MailGönderici.sendEmail(email, "javamailislemi@gmail.com", message); // sadece destek talebi kısmı çalışacak
                    textArea.append("Destek Masası: E-postanız başarıyla gönderildi. Size en kısa sürede dönüş yapacağız.\n");
                    mailFrame.dispose();
                } else {
                    messageArea.setText("Lütfen tüm alanları doldurun.");
                }
            }
        });

        mailFrame.setVisible(true);
    }


    /**
     * Destek masasına e-posta gönderir (örnek).
     */
    private void sendMailToSupport(String email, String message) {
        // Burada e-posta gönderme işlemi gerçekleştirilebilir.
        textArea.append("Destek Masası: E-postanız alındı. Size en kısa sürede dönüş yapacağız.\n");
    }
}
