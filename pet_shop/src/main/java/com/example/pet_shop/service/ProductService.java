package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.CartDTO;
import com.example.pet_shop.model.DTOS.OrderStatusDTO;
import com.example.pet_shop.model.DTOS.ProductInfoDTO;
import com.example.pet_shop.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ProductRepository productRepository;


    public ProductInfoDTO viewProductById(int id) {
        return null;
    }

    public List<ProductInfoDTO> viewAll() {
        return null;
    }

    public List<ProductInfoDTO> filter() {
        return null;
    }

    public List<ProductInfoDTO> search(ProductInfoDTO dto) {
        return null;
    }

    public List<ProductInfoDTO> sort() {
        return null;
    }

    public ProductInfoDTO addProduct(ProductInfoDTO dto) {
        return null;
    }

    public ProductInfoDTO editProduct(int id) {
        return null;
    }

}
