package com.example.pet_shop.service;

import com.example.pet_shop.model.entities.Discount;
import com.example.pet_shop.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationService extends AbstractService implements Runnable{

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;
    @Override
    @Scheduled(cron = "0 0 0 * * SUN") // Schedule to run every Sunday at midnight
    public void run() {
        // Retrieve all discounts that have been added or updated since the last run
        List<Discount> updatedDiscounts = discountService.getUpdatedDiscounts();

        for (Discount discount : updatedDiscounts) {
            // Check if any users have subscribed to notifications for this discount
            List<User> subscribedUsers = userService.getSubscribedUsers(discount);

            for (User user : subscribedUsers) {
                // Send a notification to the user via email or push notification
                // You can use an external library like JavaMail or FCM to send notifications
            }
        }
    }
}
