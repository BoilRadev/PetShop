package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.productDTOs.ProductAddDTO;
import com.example.pet_shop.model.DTOS.productDTOs.ProductEditRequestDTO;
import com.example.pet_shop.model.DTOS.productDTOs.ProductInfoDTO;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.NotFoundException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.model.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService{



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

    public ProductInfoDTO addProduct(ProductAddDTO dto, HttpSession ses) {
        Product product = mapper.map(dto, Product.class);
        //admin verification??

        if (ses.getAttribute("LOGGED") == null || !((Boolean) ses.getAttribute("LOGGED"))) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            int loggedUserId = (int) ses.getAttribute("LOGGED_ID");
            User u = getUserById(loggedUserId);

            if (!u.is_admin()) {
                throw new UnauthorizedException("You are not admin");
            } else {

                product.setName(dto.getName());
                product.setDescription(dto.getDescription());
                product.setQuantity(dto.getQuantity());
                product.setPrice(dto.getPrice());
                productRepository.save(product);
            }
            return mapper.map(product, ProductInfoDTO.class);
        }
    }

    public void deleteProduct(int id) {
        Optional<Product> product = productRepository.getProductsById(id);
        if(product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new NotFoundException("Product not found");
        }
    }



        public void editProduct(ProductEditRequestDTO productDto, int id) {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isEmpty()) {
                throw new NotFoundException("Product not found!");
            }
            Product product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            // set other fields
            productRepository.save(product);
        }

    }
