package com.studentcardsapi.service.implementations;

import com.studentcardsapi.service.interfaces.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.studentcardsapi.utils.ConstantEndpoints.DEFAULT_DOMAIN;
import static com.studentcardsapi.utils.ConstantEndpoints.USERNAME_CONFIRMATION_ENDPOINT;
import static com.studentcardsapi.utils.ErrorMessages.GENERIC_ERROR;
import static com.studentcardsapi.utils.InformationMessages.ACTIVATION_EMAIL_SENT;

@Component
@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(System.getenv("SPRING_MAIL_USER"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public String sendUsernameConfirmation(String username, String activationToken) {
        try {
            this.sendMail(
                    username,
                    "Link de ativação de conta",
                    "Seu link para ativação de sua conta Student Cards é \n"
                            + DEFAULT_DOMAIN
                            + USERNAME_CONFIRMATION_ENDPOINT
                            + activationToken
            );
            return ACTIVATION_EMAIL_SENT;
        } catch (Exception e) {
            return GENERIC_ERROR;
        }
    }
}
