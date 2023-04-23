package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.productDTOs.*;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.UnauthorizedException;
import com.example.pet_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class ProductController extends AbstractController {
    @Autowired
    protected Logger logger;
    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public ProductInfoDTO viewProductById(@PathVariable int id){
        return productService.viewProductById(id);
    }

    @GetMapping("/products/all")
    public ResponseEntity<Page<ProductInfoDTO>> viewAllProducts(Pageable pageable){
        Page<ProductInfoDTO> productPage = productService.viewAll(pageable);
        return ResponseEntity.ok(productPage);
    }

    @GetMapping("/products/filter/{subcategory-id}")
    public List<ProductInfoDTO> filter(@PathVariable("subcategory-id") int subId, @RequestParam(value = "order", defaultValue = "asc") String order) {
        List<ProductInfoDTO> filteredProducts = productService.filter(subId);

        if (order.equalsIgnoreCase("asc")) {
            filteredProducts.sort(Comparator.comparing(ProductInfoDTO::getPrice));
        } else if (order.equalsIgnoreCase("desc")) {
            filteredProducts.sort(Comparator.comparing(ProductInfoDTO::getPrice).reversed());
        } else {
            throw new BadRequestException("Invalid sort order");
        }

        return filteredProducts;
    }

    @GetMapping("/products/search")
    public List<ProductInfoDTO> search(@RequestParam(value = "name") String name){
        return productService.search(name);
    }

    @PostMapping("/products")
    public ProductInfoDTO addProduct(@RequestBody ProductAddDTO dto){
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        return productService.addProduct(dto);
    }

    @PutMapping("/products/{id}")
    public void editProduct(@RequestBody ProductAddDTO productDto, @PathVariable int id) {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        productService.editProduct(productDto, id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id){
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        productService.deleteProduct(id);
    }
}
