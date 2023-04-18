package com.example.pet_shop.controller;

import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.service.NotificationService;
import com.example.pet_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController extends AbstractController{

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;



    @DeleteMapping("/admin/users/{user-id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("user-id") int userId, HttpSession session) {
        boolean isAdmin = userService.getLoggedUser(session).is_admin();
        if (!isAdmin) {
            throw new UnauthorizedException("Only admins can delete users.");
        }

        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PostMapping("/admin/notify-subscribers")
    public void notifySubscribers(){
    }




}