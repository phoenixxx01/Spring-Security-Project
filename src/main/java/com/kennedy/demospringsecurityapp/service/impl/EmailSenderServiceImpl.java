package com.kennedy.demospringsecurityapp.service.impl;

import com.kennedy.demospringsecurityapp.dto.EmailDetails;
import com.kennedy.demospringsecurityapp.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {

        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMessageBody());
            simpleMailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(simpleMailMessage);

            System.out.println("mail sent successfully");

        }catch (MailException e){
            throw new RuntimeException("email sent failed");
        }

    }
}
