package com.example.pet_shop.controller;

import com.example.pet_shop.service.NotificationService;
import com.example.pet_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController extends AbstractController {

    @Autowired
    private final UserService userService;
    @Autowired
    private NotificationService notificationService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/admin/users/{user-id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("user-id") int userId, HttpSession s) {
        isAdminLoggedIn(s);
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PostMapping("/admin/notify-subscribers")
    public ResponseEntity<Void> sendEmails() {
        notificationService.sendSubscribedEmails();
        return ResponseEntity.ok().build();
    }
}
