package com.example.pet_shop.service;


import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.model.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.CategoryRepository;
import com.example.pet_shop.model.repositories.ProductRepository;
import com.example.pet_shop.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected CategoryRepository categoryRepository;

    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
    public Product getProductByID(int id){
        return getProductByID(id);
    }
}
