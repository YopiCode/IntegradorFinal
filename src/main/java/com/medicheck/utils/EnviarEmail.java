package com.medicheck.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarEmail {
    final String userName = "nerbat643@gmail.com";
    final String password = "nxjyrgajdhqqyium";

    private Session properties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
    }

    public void enviarMensaje(String correo) {
        try {
            MimeMessage message = new MimeMessage(properties());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo, true));
            message.setSubject("Medicheck-Cita Medica");
            message.setText("Se registro Su cita");
            Transport.send(message);

        } catch (MessagingException me) {
            System.out.println("Exception: " + me);

        }
    }

}

