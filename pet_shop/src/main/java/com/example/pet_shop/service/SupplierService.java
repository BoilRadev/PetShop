package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.SupplierDTO;
import com.example.pet_shop.model.entities.Supplier;
import org.springframework.stereotype.Service;

@Service
public class SupplierService extends AbstractService {

    public Supplier addSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierDTO.getSupplierName());
        return supplierRepository.save(supplier);
    }

    public void deleteById(int id) {
        supplierRepository.deleteById(id);
    }
}