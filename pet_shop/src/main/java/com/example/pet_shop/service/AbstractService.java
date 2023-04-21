package com.example.pet_shop.service;


import com.example.pet_shop.model.entities.Discount;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    @Autowired
    protected SupplierRepository supplierRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected PaymentRepository paymentRepository;
    @Autowired
    protected OrderStatusRepository orderStatusRepository;
    @Autowired
    protected PaymentMethodRepository paymentMethodRepository;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected SubcategoryRepository subcategoryRepository;
    @Autowired
    protected DiscountRepository discountRepository;
    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
    public Product getProductByID(int id){
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
