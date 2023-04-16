package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.userDTOs.*;
import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserWithoutPassDTO register(@Valid @RequestBody RegisterDTO dto){
        return userService.register(dto);
    }

    @PostMapping("/users/login")
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto, HttpSession s){
        UserWithoutPassDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;
    }


    @PutMapping("/users")
    public UserEditResponseDTO editUser(@RequestBody UserEditRequestDTO userDto, HttpSession ses) {
        if (ses.getAttribute("LOGGED") == null || !((Boolean) ses.getAttribute("LOGGED"))) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            int loggedUserId = getLoggedId(ses);

            return userService.edit(userDto,loggedUserId);

        }
    }

    @PostMapping("/users/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/users/{id}")
    public UserWithoutPassDTO getById(@PathVariable int id){
        return userService.getById(id);
    }

    @GetMapping("/users")
    public List<UserWithoutPassDTO> getAll(){
        return userService.getAll();
    }


    @DeleteMapping("/users")
    public void deleteUser( HttpSession ses) {
        if (userService.getLoggedUser(ses) == null) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            userService.deleteUser(getLoggedId(ses));
        }
    }
}
