package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.userDTOs.*;
import com.example.pet_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserWithoutPassDTO> register(@Valid @RequestBody RegisterDTO dto) {
        UserWithoutPassDTO user = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token) {
        if (userService.confirmEmail(token)) {
            return "Email confirmed";
        } else {
            return "Invalid confirmation";
        }
    }

    @PostMapping("/users/login")
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto, HttpSession s) {
        UserWithoutPassDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;
    }

    @PutMapping
    public UserWithoutPassDTO editUser(@Valid @RequestBody RegisterDTO userDto, HttpSession s) {
        getLoggedId(s);
        return userService.edit(userDto, getLoggedId(s));
    }

    @PostMapping("/logout")
    public void logout(HttpSession s) {
        s.invalidate();
    }

    @GetMapping("/{id}")
    public UserWithoutPassDTO getById(@PathVariable int id, HttpSession s) {
        getLoggedId(s);
        return userService.getById(id);
    }

    @GetMapping("/all")
    public Page<UserWithoutPassDTO> getAll(Pageable pageable, HttpSession s) {
        getLoggedId(s);
        return userService.getAllUsers(pageable);
    }

    @DeleteMapping
    public void deleteUser(HttpSession s) {
        userService.deleteUser(getLoggedId(s));
    }

    @PutMapping("/subscribe")
    public ResponseEntity<?> subscribeUser( HttpSession s) {
        userService.subscribeUser(getLoggedId(s));
        return ResponseEntity.ok("User subscribed successfully");
    }

    @PutMapping("/unsubscribe")
    public ResponseEntity<?> unSubscribeUser( HttpSession s) {
        userService.unSubscribeUser(getLoggedId(s));
        return ResponseEntity.ok("User unsubscribed successfully");
    }
}