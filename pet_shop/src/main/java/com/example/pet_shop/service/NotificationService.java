package com.example.pet_shop.service;

import com.example.pet_shop.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                    .map( u -> mapper.convertValue(u, RegisterDTO.class))
                    .filter(u -> u.isSubscribed())
                    .forEach(u -> senderService.sendEmail(u.getEmail() ,"New discount at our shop",
                            "Come and check the latest discount for the upcoming holidays "));

        }

 */

}
