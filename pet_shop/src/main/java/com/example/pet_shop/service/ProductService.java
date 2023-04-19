package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.productDTOs.*;
import com.example.pet_shop.model.entities.*;
import com.example.pet_shop.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService{



    public ProductInfoDTO viewProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return mapper.convertValue(product.get(), ProductInfoDTO.class);
        }
        throw new NotFoundException("Product not found");
    }

    public List<ProductInfoDTO> viewAll() {
        return productRepository.findAll()
                .stream()
                .map( product -> mapper.convertValue(product, ProductInfoDTO.class))
                .collect(Collectors.toList());    }

    public List<ProductInfoDTO> filter(FilterDTO dto){
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getSubcategory().getName().equals(dto.getCategory()))
                .filter(product -> product.getSubcategory().getName().equals(dto.getSubcategory()))
                .map( product -> mapper.convertValue( product ,ProductInfoDTO.class))
                .collect(Collectors.toList());
    }



    public List<ProductInfoDTO> search(SearchDTO dto) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getName().toLowerCase(Locale.ROOT).contains(dto.getName().toLowerCase(Locale.ROOT)))
                .map( product -> mapper.convertValue( product ,ProductInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductInfoDTO> sortAscending() {
        return productRepository.findAll()
                .stream()
                .map( product -> mapper.convertValue( product ,ProductInfoDTO.class))
                .sorted()
                .collect(Collectors.toList());
    }
    public List<ProductInfoDTO> sortDescending() {
        return productRepository.findAll()
                .stream()
                .map( product -> mapper.convertValue( product ,ProductInfoDTO.class))
                .sorted()
                .collect(Collectors.toList());
    }

    public ProductInfoDTO addProduct(ProductAddDTO dto) {
        Product product = mapper.convertValue(dto, Product.class);


        Optional<Supplier> optionalSupplier = supplierRepository.findById(dto.getSupplierId());
        if (optionalSupplier.isEmpty()) {
            throw new NotFoundException("Supplier not found. Please add a new supplier here: /suppliers");
        }

        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findById(dto.getSubcategoryId());
        if (optionalSubcategory.isEmpty()) {
            throw new NotFoundException("Subcategory not found. Please add a new subcategory here: /subcategories");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found. Please add a new category here: /categories");
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setSupplier(optionalSupplier.get());
        product.setSubcategory(optionalSubcategory.get());
        product.setCategory(optionalCategory.get());

        ProductInfoDTO productInfoDTO = mapper.convertValue(product, ProductInfoDTO.class);
        productRepository.save(product);
        return productInfoDTO;
    }

    public void deleteProduct(int id) {
        Optional<Product> product = productRepository.getProductsById(id);
        if(product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new NotFoundException("Product not found");
        }
    }


        public void editProduct(ProductAddDTO productDto, int id) {
        addProduct(productDto);
        }

    }
