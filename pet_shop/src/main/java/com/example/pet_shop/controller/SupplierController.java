package com.example.pet_shop.controller;
import com.example.pet_shop.model.DTOS.SupplierDTO;
import com.example.pet_shop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
public class SupplierController extends AbstractController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    protected LoginManager loginManager;

    @PostMapping
    public ResponseEntity<Void> addSupplier(@RequestBody SupplierDTO supplierDTO) {
        checkAuthorization(loginManager);
        supplierService.addSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{supplier-id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable("supplier-id") int supplierId) {
        checkAuthorization(loginManager);
        supplierService.deleteById(supplierId);
        return ResponseEntity.ok("Successfully deleted");
    }
}
