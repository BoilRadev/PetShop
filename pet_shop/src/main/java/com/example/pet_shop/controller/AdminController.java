package com.example.pet_shop.controller;

import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController extends AbstractController{

    @Autowired
    private UserService userService;


    @DeleteMapping("/admin/users/{user-id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("user-id") int userId) {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PostMapping("/admin/notify-subscribers")
    public void notifySubscribers(){
    }




}
