package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.ProductInfoDTO;
import com.example.pet_shop.service.ProductService;
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
    public List<ProductInfoDTO> filter(){
        return productService.filter();
    }

    @GetMapping("/products/search")
    public List<ProductInfoDTO> search(@RequestBody ProductInfoDTO dto){
        return productService.search(dto);
    }

    @GetMapping("/products/sort")
    public List<ProductInfoDTO> sort(){
        return productService.sort();
    }

    @PostMapping("/products")
    public ProductInfoDTO addProduct(@RequestBody ProductInfoDTO dto){
        return productService.addProduct(dto);
    }

    @PutMapping("/products/{id}")
    public ProductInfoDTO editProduct(@PathVariable int id){
        return productService.editProduct(id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id){
    }

}
