package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.DiscountAddDTO;
import com.example.pet_shop.model.DTOS.DiscountInfoDTO;
import com.example.pet_shop.model.entities.Discount;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;

import com.example.pet_shop.model.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DiscountService extends AbstractService {

    @Autowired
    private DiscountRepository discountRepository;

    public DiscountInfoDTO addDiscount(DiscountAddDTO dto) {

        Discount discount = mapper.map(dto,Discount.class);
            discount.setPercent(dto.getPercent());
            discount.setFromDate(dto.getFromDate().toLocalDate());
            discount.setToDate(dto.getToDate().toLocalDate());
            discount.setActive(dto.isActive());
            discountRepository.save(discount);

        return mapper.map(discount, DiscountInfoDTO.class);
    }

    public DiscountInfoDTO editDiscount(int id) {
        Optional<Discount> opt = discountRepository.findById(id);
        if(!opt.isPresent()){
            throw new BadRequestException("No discount found with id: " + id);
        }
        //scanner?
//        Discount d = opt.get();
//        d.setDescription();
//        d.setFromDate();
//        d.setToDate();
//        d.setPercent();
//        d.setActive();
//        discountRepository.save(д);
//
//        return mapper.map(d , DiscountInfoDTO.class);
        return null;
    }

    public List<Discount> getUpdatedDiscounts() {
       return discountRepository.findAll();
    }

}
