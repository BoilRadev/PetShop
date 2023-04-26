package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.discountDTO.DiscountAddDTO;
import com.example.pet_shop.model.DTOS.discountDTO.DiscountEditDTO;
import com.example.pet_shop.model.DTOS.discountDTO.DiscountInfoDTO;
import com.example.pet_shop.model.entities.Discount;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.model.entities.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class DiscountService extends AbstractService {


    public DiscountInfoDTO addDiscount(DiscountAddDTO dto, int productId) {
        Discount discount = mapper.convertValue(dto, Discount.class);
        discount.setPercent(BigDecimal.valueOf(dto.getPercent()));
        discount.setDescription(dto.getDescription());
        discount.setFromDate(LocalDate.parse(LocalDate.from(dto.getFromDate()).toString()));
        discount.setToDate(LocalDate.parse(LocalDate.from(dto.getToDate()).toString()));
        discount.setActive(true);

        Product p = getProductById(productId);
        p.setDiscount(discount);

        discountRepository.save(discount);
        discount.getProducts().add(p);
        productRepository.save(p);
        return mapper.convertValue(discount, DiscountInfoDTO.class);
    }
    public Product getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new BadRequestException("Product not found");
        }
    }
    public DiscountInfoDTO editDiscount(int id, DiscountEditDTO dto) {
        Optional<Discount> opt = discountRepository.findById(id);
        if(opt.isEmpty()){
            throw new BadRequestException("No discount found with id: " + id);
        }

        Discount d = opt.get();
        d.setDescription(dto.getDescription());
        d.setFromDate(dto.getFromDate());
        d.setToDate(dto.getToDate());
        d.setPercent(dto.getPercent());
        d.setActive(dto.isActive());
        discountRepository.save(d);

        return mapper.convertValue(d , DiscountInfoDTO.class);
    }

    public void delete(int id) {
        discountRepository.deleteById(id);
    }
}
