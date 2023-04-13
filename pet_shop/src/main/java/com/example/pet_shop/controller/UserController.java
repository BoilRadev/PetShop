package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.LoginDTO;
import com.example.pet_shop.model.DTOS.RegisterDTO;
import com.example.pet_shop.model.DTOS.UserWithoutPassDTO;
import com.example.pet_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserWithoutPassDTO register(@RequestBody RegisterDTO dto){
        return userService.register(dto);
    }

    @PostMapping("/users/login")
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto, HttpSession s){
        UserWithoutPassDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;
    }

    @GetMapping("/users/{id}")
    public UserWithoutPassDTO getById(@PathVariable int id){
        return userService.getById(id);
    }

    @GetMapping("/users")
    public List<UserWithoutPassDTO> getAll(){
        return userService.getAll();
    }


}
