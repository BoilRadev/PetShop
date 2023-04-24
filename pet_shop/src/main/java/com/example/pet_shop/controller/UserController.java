package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.userDTOs.*;
import com.example.pet_shop.exceptions.BadRequestException;
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
    protected LoginManager loginManager;
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserWithoutPassDTO> register(@Valid @RequestBody RegisterDTO dto) {
        UserWithoutPassDTO user = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token){
        if(userService.confirmEmail(token)){
            return "Email confirmed";
        }else {
            return "Invalid confirmation";
        }
    }

    @PostMapping("/users/login")
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto){
        UserWithoutPassDTO respDto = userService.login(dto);
        loginManager.login(userService.getUserById(respDto.getId()));
        return respDto;
    }

    @PutMapping("/users")
    public UserWithoutPassDTO editUser(@Valid @RequestBody RegisterDTO userDto) {
        int loggedUserId = getLoggedUserId();
        return userService.edit(userDto, loggedUserId);
    }

    @PostMapping("/users/logout")
    public void logout() {
        loginManager.logout();
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
        int loggedUserId = getLoggedUserId();
        userService.deleteUser(loggedUserId);
    }

    @PutMapping("/users/subscribe")
    public ResponseEntity<?> subscribeUser() {
        int userId = getLoggedUserId();
        userService.subscribeUser(userId);
        return ResponseEntity.ok("User subscribed successfully");
    }

    @PutMapping("/users/unsubscribe")
    public ResponseEntity<?> unSubscribeUser() {
        int userId = getLoggedUserId();
        userService.unSubscribeUser(userId);
        return ResponseEntity.ok("User unsubscribed successfully");
    }

    private int getLoggedUserId() {
        if (!loginManager.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            return loginManager.id();
        }
    }
}