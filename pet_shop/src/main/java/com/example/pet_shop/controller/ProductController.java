package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.productDTOs.ProductAddDTO;
import com.example.pet_shop.model.DTOS.productDTOs.ProductEditRequestDTO;
import com.example.pet_shop.model.DTOS.productDTOs.ProductInfoDTO;
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
