import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

public class Mail {
    public static void main (String[] args){
        processFile();
    }

    public static void processFile() {
        File file = new File("emails.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String email = sc.nextLine();
                send(email);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void send(String email){
        try{
            final String fromEmail = ""; //requires valid gmail id
            final String password = ""; // correct password for gmail id
            final String toEmail = email; // can be any email id
            final String emailMessage = "hi"; // our message

            //System.out.println("OPMail Begin");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            //System.out.println("Mail Check 2");

            message.setSubject("Hi from OP!");
            message.setText(emailMessage);

            //System.out.println("Mail Check 3");

            Transport.send(message);
            System.out.println("Mail Sent");
        }catch(Exception ex){
            System.out.println("Mail fail");
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
}