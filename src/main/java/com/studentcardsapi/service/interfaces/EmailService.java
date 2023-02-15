package com.studentcardsapi.service.interfaces;

public interface EmailService {

    public String sendUsernameConfirmation(String username, String activationToken);

}
