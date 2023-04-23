package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.SupplierDTO;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.UnauthorizedException;
import com.example.pet_shop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SupplierController extends AbstractController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    protected LoginManager loginManager;

    @PostMapping("/suppliers")
    public ResponseEntity<Void> addSupplier(@RequestBody SupplierDTO supplierDTO) {
        checkLoggedInUser();
        supplierService.addSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/suppliers/{supplier-id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable("supplier-id") int supplierId) {
        checkLoggedInUser();
        supplierService.deleteById(supplierId);
        return ResponseEntity.ok("successfully deleted");
    }

    private void checkLoggedInUser() {
        if (!loginManager.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!loginManager.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
    }
}
