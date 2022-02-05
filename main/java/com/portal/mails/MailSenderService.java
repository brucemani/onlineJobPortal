package com.portal.mails;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("prismentsoljobs@gmail.com");
        message.setText(body);
        message.setSubject("Electric Mail Generator");
        message.setTo(toEmail);
        javaMailSender.send(message);
        //prismentsol
//        prismentsoljobs@gmail.com
//        jaywpuopdlyauhgf
    }
}
