package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.ProductAddDTO;
import com.example.pet_shop.model.DTOS.ProductInfoDTO;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.exceptions.NotFoundException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ProductRepository productRepository;


    public ProductInfoDTO viewProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return mapper.map(product.get(), ProductInfoDTO.class);
        }
        throw new NotFoundException("Product not found");
    }

    public List<ProductInfoDTO> viewAll() {
        return productRepository.findAll()
                .stream()
                .map( product -> mapper.map(product, ProductInfoDTO.class))
                .collect(Collectors.toList());    }

    public List<ProductInfoDTO> filter() {
        return null;
    }

    public List<ProductInfoDTO> search(ProductInfoDTO dto) {
        return null;
    }

    public List<ProductInfoDTO> sort() {
        return null;
    }

    public ProductInfoDTO addProduct(ProductAddDTO dto) {
        Product product = mapper.map(dto,Product.class);
        //admin verification??

        if(!user.isAdmin()){
            throw new UnauthorizedException("You are not admin");
        }
        else {

            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setQuantity(dto.getQuantity());
            product.setPrice(dto.getPrice());
            productRepository.save(product);
        }
        return mapper.map(product, ProductInfoDTO.class);
    }

    public ProductInfoDTO editProduct(int id) {

    }

    public void deleteProduct(int id) {
        Optional<Product> product = productRepository.deleteById(id);
        if(product.isPresent()){
            mapper.map(product.get(), ProductInfoDTO.class);
        }
        throw new NotFoundException("Product not found");
    }
}
