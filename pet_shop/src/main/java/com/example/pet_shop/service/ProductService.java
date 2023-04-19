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
                .filter(p -> p.getCategory().getName().equals(dto.getCategory()))
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

        product.setSupplier(getSupplierById(dto.getSupplierId()));
        product.setSubcategory(getSubcategoryById(dto.getSubcategoryId()));
        product.setCategory(getCategoryById(dto.getCategoryId()));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());

        ProductInfoDTO productInfoDTO = mapper.convertValue(product, ProductInfoDTO.class);
        productRepository.save(product);
        productInfoDTO.setId(product.getId());
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


    public ProductInfoDTO editProduct(ProductAddDTO dto, int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException("Product not found");
        }
        Product product = optionalProduct.get();

        product.setSupplier(getSupplierById(dto.getSupplierId()));
        product.setSubcategory(getSubcategoryById(dto.getSubcategoryId()));
        product.setCategory(getCategoryById(dto.getCategoryId()));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());

        productRepository.save(product);
        ProductInfoDTO infoDto  = new ProductInfoDTO();
        infoDto.setId(id);
        return mapper.convertValue(product, ProductInfoDTO.class);
    }


    private Supplier getSupplierById(int supplierId) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if (optionalSupplier.isEmpty()) {
            throw new NotFoundException("Supplier not found. Please add a new supplier here: /suppliers");
        }
        return optionalSupplier.get();
    }

    private Subcategory getSubcategoryById(int subcategoryId) {
        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findById(subcategoryId);
        if (optionalSubcategory.isEmpty()) {
            throw new NotFoundException("Subcategory not found. Please add a new subcategory here: /subcategories");
        }
        return optionalSubcategory.get();
    }

    private Category getCategoryById(int categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found. Please add a new category here: /categories");
        }
        return optionalCategory.get();
    }
    }
