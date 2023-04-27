package com.example.pet_shop.controller;
import com.example.pet_shop.model.DTOS.SupplierDTO;
import com.example.pet_shop.service.SupplierService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
public class SupplierController extends AbstractController {
    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<Void> addSupplier(@RequestBody SupplierDTO supplierDTO, HttpSession s) {
        isAdminLoggedIn(s);
        supplierService.addSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{supplier-id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable("supplier-id") int supplierId, HttpSession s) {
        isAdminLoggedIn(s);
        supplierService.deleteById(supplierId);
        return ResponseEntity.ok("Successfully deleted");
    }
}
