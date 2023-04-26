package com.example.pet_shop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
<<<<<<< HEAD
        mailSender.setUsername("pet.shop.ittalents@gmail.com");
        mailSender.setPassword("741852963b");
=======
        mailSender.setUsername("your-email@gmail.com");
        mailSender.setPassword("your-email-password");
>>>>>>> origin/master

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.debug", true);

        return mailSender;
    }
}
<<<<<<< HEAD
=======

>>>>>>> origin/master