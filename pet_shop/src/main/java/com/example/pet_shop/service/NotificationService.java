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
    List<RegisterDTO> subscribedUsers = userRepository.findAll()
            .stream()
            .map(u -> mapper.convertValue(u, RegisterDTO.class))
            .filter(RegisterDTO::isSubscribed)
            .collect(Collectors.toList());

    ExecutorService executorService = Executors.newFixedThreadPool(subscribedUsers.size());

    for (RegisterDTO user : subscribedUsers) {
        executorService.submit(() -> {
            senderService.sendEmail(user.getEmail(), "New discount at our shop",
                    "Come and check the latest discount for the upcoming holidays");
        });
    }

    executorService.shutdown();
}

 */

}
