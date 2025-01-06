package MailGönderici;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailGönderici {

    public static void sendEmail(String recipientEmail, String subject, String messageText) {
        final String senderEmail = "tunak267@gmail.com";
        final String senderPassword = "rauu uhju jffg xjpr";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("E-posta başarıyla gönderildi!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("E-posta gönderiminde bir hata oluştu: " + e.getMessage());
        }   
    }
    
    
    
    // Destek masasına e-posta gönderme işlemi
    /*private*/ public static void sendDestekEmail(String senderEmail, String messageText) {
        final String senderPassword = "zyws wgnk nmak mbwz"; // Bu şifrenin daha güvenli bir şekilde saklanması gerektiğini unutmayın

        // SMTP ayarlarını yapılandırıyoruz
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Oturum oluşturuluyor
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Destek talebi mesajı
            Message message2 = new MimeMessage(session);
            message2.setFrom(new InternetAddress(senderEmail));
            //message2.setRecipients(Message.RecipientType.TO, InternetAddress.parse("javamailislemi@gmail.com"));
            message2.setRecipients(Message.RecipientType.TO, InternetAddress.parse(senderEmail));  // Burada gönderene yanıt gönderilecek
            message2.setSubject("Destek Talebi");
            message2.setText("Gönderen: " + senderEmail + "\n\nMesaj:\n" + messageText);

            Transport.send(message2);
            System.out.println("Destek talebiniz başarıyla gönderildi!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Destek talebi e-posta gönderiminde bir hata oluştu: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    // Test etmek için bir main metodu
   /* public static void main(String[] args) {
        String recipient = "nidimid212@myweblaw.com";  // Test için geçerli bir e-posta adresi yazın
        String subject = "Test E-postası";
        String message = "Bu bir test e-postasıdır. Lütfen yanıtlamayın.";

        sendEmail(recipient, subject, message);
    }*/
}
