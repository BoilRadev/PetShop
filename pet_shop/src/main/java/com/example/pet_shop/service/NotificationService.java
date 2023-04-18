package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.userDTOs.RegisterDTO;

import com.example.pet_shop.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;



@Service
public class NotificationService extends AbstractService {

    @Autowired
    private EmailSenderService senderService;
    @Autowired
    private UserRepository userRepository;
/*
    @EventListener(ApplicationReadyEvent.class)
    public void sendAllSubscribed() {
        userRepository.findAll()
                    .stream()
                    .map( u -> mapper.map(u, RegisterDTO.class))
                    .map(u -> u.is_subscribed())
                    .forEach(u -> senderService.sendEmail("email@mail.com" ,"New discount at our shop",
                            "Come and check the latest discount for the upcoming holidays "));

        }

 */
}
