package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.discountDTO.DiscountAddDTO;
import com.example.pet_shop.model.DTOS.discountDTO.DiscountInfoDTO;
import com.example.pet_shop.model.entities.Discount;
import com.example.pet_shop.model.exceptions.BadRequestException;

import com.example.pet_shop.model.exceptions.NotFoundException;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class DiscountService extends AbstractService {



    public DiscountInfoDTO addDiscount(DiscountAddDTO dto) {

        Discount discount = mapper.convertValue(dto, Discount.class);
        discount.setPercent(dto.getPercent());
        discount.setDescription(dto.getDescription());
        discount.setFromDate(LocalDate.from(dto.getFromDate()));
        discount.setToDate(LocalDate.from(dto.getToDate()));
        discount.setActive(dto.isActive());
        discountRepository.save(discount);

        return mapper.convertValue(discount, DiscountInfoDTO.class);
    }

    public DiscountInfoDTO editDiscount(int id) {
        Optional<Discount> opt = discountRepository.findById(id);
        if(opt.isEmpty()){
            throw new BadRequestException("No discount found with id: " + id);
        }

        Discount d = opt.get();
        d.setDescription(d.getDescription());
        d.setFromDate(d.getFromDate());
        d.setToDate(d.getToDate());
        d.setPercent(d.getPercent());
        d.setActive(d.isActive());
        discountRepository.save(d);

        return mapper.convertValue(d , DiscountInfoDTO.class);
    }
    public DiscountInfoDTO getById(int id) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isPresent()) {
            return mapper.convertValue(discount.get(), DiscountInfoDTO.class);
        }
        throw new NotFoundException("User not found");
    }

    public List<Discount> getUpdatedDiscounts() {
       return discountRepository.findAll();
    }

    public void delete(int id) {
        discountRepository.deleteById(id);
    }
}
