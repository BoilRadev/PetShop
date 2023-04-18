package com.example.pet_shop;

import com.example.pet_shop.service.EmailSenderService;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.NamingConvention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.InputStream;

@SpringBootApplication
public class PetShopApplication {

    @Autowired
    private EmailSenderService senderService;

    public static void main(String[] args) {
        SpringApplication.run(PetShopApplication.class, args);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
//
//    @EventListener(ApplicationReadyEvent.class)
//        public void sendMail(){
//            senderService.sendEmail("b.p.radev@gmail.com",
//                    "Test", "This is a test");
//        }
    @Bean
    public JavaMailSenderImpl mailSender(){
        return new JavaMailSenderImpl();

    }



}
