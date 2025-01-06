package otoMail;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Flags;
import java.util.Properties;

public class otomail {

    public static void main(String[] args) {
        // Gmail ayarları
        String imapHost = "imap.gmail.com";
        String smtpHost = "smtp.gmail.com";
        String username = "javamailislemi@gmail.com";
        String password = "zyws wgnk nmak mbwz"; // Güvenli şekilde saklayın

        // IMAP ve SMTP ayarları
        Properties properties = new Properties();
        properties.put("mail.imap.host", imapHost);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", "587");

        try {
            // E-posta oturumu oluştur
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Gelen kutusunu bağla
            Store store = session.getStore("imap");
            store.connect(imapHost, username, password);

            // Gelen kutusunu aç
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            System.out.println("Gelen kutusu kontrol ediliyor...");

            // Gelen e-postaları al
            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                if (!message.isSet(Flags.Flag.SEEN)) { // Yeni mesajları kontrol et
                    System.out.println("Yeni e-posta bulundu: " + message.getSubject());

                    // Yanıt oluştur
                    Message replyMessage = new MimeMessage(session);
                    replyMessage.setFrom(new InternetAddress(username));
                    replyMessage.setRecipients(Message.RecipientType.TO, message.getFrom()); // Yanıtı gönderen kişiye gönder
                    replyMessage.setSubject("Re: " + message.getSubject());
                    replyMessage.setContent(
                        "<p>Merhaba,</p><p>Bu konu Hakkında Çalışıyoruz.</p>", 
                        "text/html"
                    );


                    // Yanıtı gönder
                    Transport.send(replyMessage);
                    System.out.println("Otomatik yanıt gönderildi.");

                    // Mesajı işaretle
                    message.setFlag(Flags.Flag.SEEN, true);
                }
            }

            // Gelen kutusunu kapat
            inbox.close(false);
            store.close();

            System.out.println("Gelen kutusu kontrolü tamamlandı.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
