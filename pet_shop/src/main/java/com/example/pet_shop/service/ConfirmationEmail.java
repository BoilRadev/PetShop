package com.example.pet_shop.service;

public class ConfirmationEmail {
    private String emailAddress;
    private String confirmationToken;

    public ConfirmationEmail(String emailAddress, String confirmationToken) {
        this.emailAddress = emailAddress;
        this.confirmationToken = confirmationToken;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }
}