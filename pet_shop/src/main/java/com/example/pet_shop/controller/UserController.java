package com.example.pet_shop.controller;

import com.example.pet_shop.exceptions.UnauthorizedException;
import com.example.pet_shop.model.DTOS.userDTOs.*;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
<<<<<<< Updated upstream
    
=======
>>>>>>> Stashed changes

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token) {
        if (userService.confirmEmail(token)) {
            return "Email confirmed";
        } else {
            return "Invalid confirmation";
        }
    }

<<<<<<< Updated upstream
    @PostMapping("/login")
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto) {
        UserWithoutPassDTO respDto = userService.login(dto);
        loginManager.login(userService.getUserById(respDto.getId()));
        return respDto;
    }

    @PutMapping
    public UserWithoutPassDTO editUser(@Valid @RequestBody RegisterDTO userDto) {
        return userService.edit(userDto, getLoggedUserId(loginManager));
    }

    @PostMapping("/logout")
    public void logout() {
        loginManager.logout();
    }

    @GetMapping("/{id}")
=======
    @PostMapping("/users/login")
    public UserWithoutPassDTO login(@Valid @RequestBody LoginDTO loginData, HttpSession s) {
        UserWithoutPassDTO u = userService.login(loginData);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", u.getId());
        return u;
    }

    @PutMapping("/users")
    public UserWithoutPassDTO editUser(@Valid @RequestBody RegisterDTO userDto, HttpSession s) {
        int loggedUserId = getLoggedId(s);
        return userService.edit(userDto, loggedUserId);
    }
    @PostMapping("/users/logout")
    public void logout(HttpSession s) {
        if (s.isNew()) {
            throw new UnauthorizedException("You have to login");
        }
        s.invalidate();

    }
    @GetMapping("/users/{id}")
>>>>>>> Stashed changes
    public UserWithoutPassDTO getById(@PathVariable int id) {
        checkLogin(loginManager);
        return userService.getById(id);
    }

    @GetMapping("/all")
    public Page<UserWithoutPassDTO> getAll(Pageable pageable) {
        checkLogin(loginManager);
        return userService.getAllUsers(pageable);
    }


<<<<<<< Updated upstream
    @DeleteMapping
    public void deleteUser() {
        userService.deleteUser(getLoggedUserId(loginManager));
    }

    @PutMapping("/subscribe")
    public ResponseEntity<?> subscribeUser() {
        userService.subscribeUser(getLoggedUserId(loginManager));
        return ResponseEntity.ok("User subscribed successfully");
    }

    @PutMapping("/unsubscribe")
    public ResponseEntity<?> unSubscribeUser() {
        userService.unSubscribeUser(getLoggedUserId(loginManager));
        return ResponseEntity.ok("User unsubscribed successfully");
    }


=======
    @DeleteMapping("/users")
    public void deleteUser(HttpSession s) {
        int loggedUserId = getLoggedId(s);
        userService.deleteUser(loggedUserId);
    }

    @PutMapping("/users/subscribe")
    public ResponseEntity<?> subscribeUser(HttpSession s) {
        int userId = getLoggedId(s);
        userService.subscribeUser(userId);
        return ResponseEntity.ok("User subscribed successfully");
    }

    @PutMapping("/users/unsubscribe")
    public ResponseEntity<?> unSubscribeUser(HttpSession s) {
        int userId = getLoggedId(s);
        userService.unSubscribeUser(userId);
        return ResponseEntity.ok("User unsubscribed successfully");
    }
>>>>>>> Stashed changes
}