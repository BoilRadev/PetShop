package com.example.pet_shop.model.DTOS;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayDTO {

        @DecimalMin(value = "1", message = "Payment method ID should be 1 for Cash.")
        @DecimalMax(value = "2", message = "Payment method ID should be 2 for MasterCard.")
        private int paymentMethodId;

        public String getPaymentMethodName() {
            if (paymentMethodId == 1) {
                return "Cash";
            } else if (paymentMethodId == 2) {
                return "MasterCard";
            } else {
                return "Unknown";
            }
        }
}
