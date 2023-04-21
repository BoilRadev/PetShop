package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.userDTOs.RegisterDTO;
import com.example.pet_shop.model.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Transactional
    public void sendSubscribedEmails() {
        List<RegisterDTO> subscribedUsers = userRepository.findAll()
                .stream()
                .map(u -> mapper.convertValue(u, RegisterDTO.class))
                .filter(RegisterDTO::isSubscribed)
                .toList();

        ExecutorService executorService = Executors.newFixedThreadPool(subscribedUsers.size());

        for (RegisterDTO user : subscribedUsers) {
            executorService.submit(() -> {
                senderService.sendEmail(user.getEmail(), "New discount at our shop",
                        "Come and check the latest discount for the upcoming holidays");
            });
        }
        executorService.shutdown();
    }
}
