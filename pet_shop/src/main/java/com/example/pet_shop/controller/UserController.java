package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.userDTOs.*;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends AbstractController {
    @Autowired
    protected Logger logger;
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserWithoutPassDTO> registerUser(@Valid @RequestBody RegisterDTO dto) {
        UserWithoutPassDTO user = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto){
        UserWithoutPassDTO respDto = userService.login(dto);
        logger.login(userService.getUserById(respDto.getId()));
        return respDto;
    }


    @PutMapping("/users")
    public UserWithoutPassDTO editUser(@Valid @RequestBody RegisterDTO userDto) {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            int loggedUserId = logger.id();
            return userService.edit(userDto, loggedUserId);
        }
    }

    @PostMapping("/users/logout")
    public void logout() {
        logger.logout();
    }

    @GetMapping("/users/{id}")
    public UserWithoutPassDTO getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @GetMapping("/users")
    public List<UserWithoutPassDTO> getAll() {
        return userService.getAll();
    }


    @DeleteMapping("/users")
    public void deleteUser() {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            userService.deleteUser(logger.id());
        }
    }

    @PutMapping("/users/subscribe")
    public ResponseEntity<?> subscribeUser() {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            int userId = logger.id();
            userService.subscribeUser(userId);
            return ResponseEntity.ok("User subscribed successfully");
        }
    }

    @PutMapping("/users/unsubscribe")
    public ResponseEntity<?> unSubscribeUser() {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            int userId = logger.id();
            userService.unSubscribeUser(userId);
            return ResponseEntity.ok("User unsubscribed successfully");
        }
    }
}
