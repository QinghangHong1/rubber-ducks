package Forest;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private String userName;
    private String userEmail;
    private String fromMail;
    private String pass;
    private Session session;
    private Properties properties;
    private String[] toAddress;
    private String host;
    MimeMessage message;
    public EmailSender(String userName, String userEmail) {
        fromMail= "cs48RubberDucks@gmail.com";
        pass = "HelloWorldcs48!";
        this.userEmail = userEmail;
        toAddress = new String[]{userEmail};
        properties = System.getProperties();
        host = "smtp.gmail.com";
        this.userName = userName;
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", host);
        properties.put("mail.smtp.user", fromMail);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(properties);
        message = new MimeMessage(session);
    }
    public boolean sendMail() throws AddressException, MessagingException{
        String subject = "Forest";
        String body = "";
        String welcomeHeader = String.format("<h3>Welcome to the Forest, %s!</h3><br>", userName);
        String paragraph = "<p>In the Forest, you have a home, forest, store, battle field. At home, you have your own pets that you can play with. You can also have a conversation with a priest.<br>Enjoy the game!<br><br>Best,<br>Rubber Ducks Developer Team</p>";
        body = welcomeHeader + paragraph;
        message.setFrom(new InternetAddress(fromMail));
        InternetAddress[] internetAddress = new InternetAddress[toAddress.length];

        // iterate array of addresses
        for (int i = 0; i < toAddress.length; i++) {
            internetAddress[i] = new InternetAddress(toAddress[i]);
       }

        for (int i = 0; i < internetAddress.length; i++) {
            message.addRecipient(Message.RecipientType.TO, internetAddress[i]);
        }

        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");

        Transport transport = session.getTransport("smtp");

        transport.connect(host, fromMail, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        return true;    
    }

}