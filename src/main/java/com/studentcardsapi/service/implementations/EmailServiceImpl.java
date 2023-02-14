package com.studentcardsapi.service.implementations;

import com.studentcardsapi.exception.ApiRequestException;
import com.studentcardsapi.service.interfaces.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.studentcardsapi.utils.constants.EndpointConstants.DEFAULT_DOMAIN;
import static com.studentcardsapi.utils.constants.EndpointConstants.USERNAME_CONFIRMATION;
import static com.studentcardsapi.utils.messages.ErrorMessages.GENERIC_ERROR;
import static com.studentcardsapi.utils.messages.InformationMessages.ACTIVATION_EMAIL_SENT;

@Component
@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private void sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(System.getenv("SPRING_MAIL_USERNAME"));
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (Exception e) {
            throw new ApiRequestException(GENERIC_ERROR);
        }

    }

    @Override
    public String sendUsernameConfirmation(String username, String activationToken) {
        this.sendMail(
                username,
                "Link de ativação de conta",
                "Seu link para ativação de sua conta Student Cards é \n"
                        + DEFAULT_DOMAIN
                        + USERNAME_CONFIRMATION + "/"
                        + activationToken
        );
        return ACTIVATION_EMAIL_SENT;
    }
}
