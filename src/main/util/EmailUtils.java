package main.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class EmailUtils {


//    Если с указанными портами и типом шифрования клиент не работает (из-за особенностей приложения, операционной системы или провайдера), попробуйте альтернативные порты и другой тип шапрования:
//    pop.rambler.ru - 995, 110 (шифрование: ssl/tls/startls/без шифрования)
//    imap.rambler.ru - 993, 143 (шифрование: ssl/tls/startls/без шифрования)
//    smtp.rambler.ru - 465, 587, 25, 2525 (шифрование: ssl/tls/startls/без шифрования)

    private final static String EMAIL_ACC = "j2022.java@rambler.ru";
    private final static String EMAIL_PWD = "2023Java";

    private final static String HOST_NAME = "smtp.rambler.ru";

    private final static String EMAIL_REGEX = "^(.+)@(\\\\S+)$";

    public static void sentEmail(String addressTo, String subtitle, String emailText) throws MessagingException {
//        if (!EMAIL_REGEX.equals(addressTo)) {
//            System.err.println("Incorrect email address!");
//
//        }

        Properties props = new Properties();
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.port", "587"); // 587

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_ACC, EMAIL_PWD);
                    }
                });

        //Compose the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_ACC));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
        message.setSubject(subtitle);
        message.setContent(emailText, "text/html");
        // message.setText(emailText);

        //send the message
        Transport.send(message);

        System.out.println("message sent successfully...");
    }

    public static void sentEmailWithAttachment(String addressTo, String subtitle, String emailText, String filePath) throws MessagingException {
//        if (!EMAIL_REGEX.equals(addressTo)) {
//            System.err.println("Incorrect email address!");
//
//        }

        Properties props = new Properties();
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.port", "587"); // 587

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_ACC, EMAIL_PWD);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_ACC));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(addressTo));
        message.setSubject(subtitle);
        message.setContent(emailText, "text/html");

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        Multipart multipart = new MimeMultipart();


        String fileName = "attachmentName";
        DataSource source = new FileDataSource(filePath);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        System.out.println("Sending");

        Transport.send(message);

        System.out.println("Done");
    }


    public static void main(String[] args) throws MessagingException {
        sentEmail("pro100mishqa@mail.ru", "Currencies: " + new Date(), "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Page Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Today's Currencies</h2>\n" +
                "<p>Table:</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
    }

}
