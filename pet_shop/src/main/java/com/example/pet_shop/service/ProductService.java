package com.example.pet_shop.service;

import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.model.DTOS.orderDTO.CartDTO;
import com.example.pet_shop.model.DTOS.productDTOs.*;
import com.example.pet_shop.model.entities.*;
import com.example.pet_shop.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService{

    public synchronized void addToCart(int productId, CartDTO cart) {

        Product product = productRepository.getProductsById(productId).orElseThrow(()
                -> new NotFoundException("Product not found"));

        if (product.getQuantity() > 0) {
            if (!cart.getCart().containsKey(product)) {
                cart.getCart().put(product, 1);
            }else {
                cart.getCart().put(product, cart.getCart().get(product) + 1);
            }
            if (cart.getCart().get(product) != null && product.getQuantity() < cart.getCart().get(product)){
                cart.getCart().put(product, cart.getCart().get(product) - 1);
                throw new  BadRequestException("Not enough products.");
            }
        } else {
            throw new NotFoundException("Not enough products.");
        }
    }

    public ProductInfoDTO viewProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return mapper.convertValue(product.get(), ProductInfoDTO.class);
        }
        throw new NotFoundException("Product not found");
    }

    public Page<ProductInfoDTO> viewAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductInfoDTO> productInfoDTOList = productPage.getContent()
                .stream()
                .map(product -> mapper.convertValue(product, ProductInfoDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(productInfoDTOList, pageable, productPage.getTotalElements());
    }

    public Page<ProductInfoDTO> filter(int subId, Pageable pageable, String order) {
        Page<Product> products;
        if (order.equalsIgnoreCase("asc")) {
            products = productRepository.findAllBySubcategoryIdOrderByPriceAsc(subId, pageable);
        } else if (order.equalsIgnoreCase("desc")) {
            products = productRepository.findAllBySubcategoryIdOrderByPriceDesc(subId, pageable);
        } else {
            throw new BadRequestException("Invalid sort order");
        }
        return products.map(product -> mapper.convertValue(product, ProductInfoDTO.class));
    }

    public Page<ProductInfoDTO> search(String name, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCase(name, pageable)
                .map(product -> mapper.convertValue(product, ProductInfoDTO.class));
    }

    public ProductInfoDTO addProduct(ProductAddDTO dto) {

        Product product = new Product();
        product.setSupplier(getSupplierById(dto.getSupplierId()));
        product.setSubcategory(getSubcategoryById(dto.getSubcategoryId()));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        ProductInfoDTO productInfoDTO = mapper.convertValue(product, ProductInfoDTO.class);
        productInfoDTO.setId(product.getId());
        getSubcategoryById(dto.getSubcategoryId()).getProducts().add(product);

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

    public void removeFromCart(int productId, CartDTO cart) {

        Product product = productRepository.getProductsById(productId).orElseThrow(()
                -> new NotFoundException("Product not found"));

        if (cart.getCart().containsKey(product)) {
            if (cart.getCart().get(product) > 1) {
                cart.getCart().put(product, cart.getCart().get(product) - 1);
            } else {
                cart.getCart().remove(product);
            }
        } else {
            throw new NotFoundException("Product not found in cart");
        }
    }

    public ProductInfoDTO editProduct(ProductAddDTO dto, int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException("Product not found");
        }
        Product product = optionalProduct.get();
        product.setSubcategory(getSubcategoryById(dto.getSubcategoryId()));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        product.setSupplier(getSupplierById(dto.getSupplierId()));
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
}
