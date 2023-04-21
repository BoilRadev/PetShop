package com.example.pet_shop.controller;

import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.model.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class Logger extends AbstractController {

    private final HttpSession session;

    @Autowired
    private UserRepository repository;

    public Logger(HttpSession session) {
        this.session = session;
    }

    public boolean isLogged() {
        return session.getAttribute("LOGGED_ID") != null;
    }

    public int id() {
        return (int) session.getAttribute("LOGGED_ID");
    }

    public boolean isAdmin() {
        if (!isLogged()) {
            return false;
        }
        User user = repository.findById(id()).orElseThrow(() -> new BadRequestException("You have to login first."));
        return user.isAdmin();
    }

    public void login(User user) {
        session.setAttribute("LOGGED_ID", user.getId());
    }

    public void logout(){
        session.invalidate();
    }
}