package com.example.pet_shop.service;

import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.model.DTOS.userDTOs.RegisterDTO;
import com.example.pet_shop.model.DTOS.userDTOs.UserWithoutPassDTO;
import com.example.pet_shop.model.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class NotificationService extends AbstractService {

    @Autowired
    private EmailSenderService senderService;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ObjectMapper mapper;

    @Transactional
    public void sendSubscribedEmails() {
        List<UserWithoutPassDTO> subscribedUsers = userRepository.findAll()
                .stream()
                .map(u -> mapper.convertValue(u, UserWithoutPassDTO.class))
                .filter(UserWithoutPassDTO::isSubscribed)
                .toList();

        ExecutorService executorService = Executors.newFixedThreadPool(subscribedUsers.size());

        try{
            for (UserWithoutPassDTO u : subscribedUsers){

                MimeMessage message = mailSender.createMimeMessage();
                message.setFrom(new InternetAddress("pet.shop.ittalents@gmail.com"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
                message.setSubject("NEW DISCOUNT");
                message.setText("Come and check our latest discounts ");
                executorService.submit(() -> {
                    mailSender.send(message);
                });
            }
            executorService.shutdown();
        }
        catch (Exception e){
            throw new BadRequestException("bla bla");
        }
    }
}
