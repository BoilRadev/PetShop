package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.SupplierDTO;
import com.example.pet_shop.model.entities.Supplier;
import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.model.repositories.SupplierRepository;
import com.example.pet_shop.model.repositories.UserRepository;
import com.example.pet_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RestController
public class SupplierController extends AbstractController {
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierRepository supplierRepository;



    @PostMapping("/suppliers/add")
    public ResponseEntity<Void> addSupplier(@RequestBody SupplierDTO supplierDTO, HttpSession ses) {

        if (ses.getAttribute("LOGGED") == null || !((Boolean) ses.getAttribute("LOGGED"))) {
            throw new BadRequestException("You have to be logged in!");
        }

        int loggedUserId = (int) ses.getAttribute("LOGGED_ID");
        User u = userService.getUserById(loggedUserId);

        if (!u.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        //TODO да търси по ид понеже имена може да съвпадат или да са фирми ?
        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplierRepository.save(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
