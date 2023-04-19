package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.productDTOs.*;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public ProductInfoDTO viewProductById(@PathVariable int id){
        return productService.viewProductById(id);
    }

    @GetMapping("/products/all")
    public List<ProductInfoDTO> viewAllProducts(){
        return productService.viewAll();
    }

    @GetMapping("/product/filter")
    public List<ProductInfoDTO> filter(@RequestBody FilterDTO dto){
        return productService.filter(dto);
    }

    @GetMapping("/products/search")
    public List<ProductInfoDTO> search(@RequestBody SearchDTO dto){
        return productService.search(dto);
    }

    @GetMapping("/products/order")
    public List<ProductInfoDTO> sortProductsByOrder(@RequestParam(value = "order", defaultValue = "asc") String order) {
        if (order.equalsIgnoreCase("asc")) {
            return productService.sortAscending();
        } else if (order.equalsIgnoreCase("desc")) {
            return productService.sortDescending();
        } else {
            throw new BadRequestException("Invalid sort order");
        }
    }

    @PostMapping("/products")
    public ProductInfoDTO addProduct(@RequestBody ProductAddDTO dto, HttpSession ses){
        return productService.addProduct(dto,ses);
    }

    @PutMapping("/products/{id}")
    public void editProduct(@RequestBody ProductEditRequestDTO productDto, @PathVariable int id) {
        productService.editProduct(productDto, id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
    }

}
