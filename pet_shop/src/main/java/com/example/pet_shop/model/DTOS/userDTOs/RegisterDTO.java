package com.example.pet_shop.model.DTOS.userDTOs;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDTO {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_!])(?=\\S+$).{8,20}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character (@#$%^&+=_!) and no whitespace")
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_!])(?=\\S+$).{8,20}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character (@#$%^&+=_!) and no whitespace")
    private String confirmPassword;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Please provide a valid phone number")
    private String phoneNumber;

    @NotBlank(message = "Town cannot be blank")
    private String town;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    private LocalDateTime createdAt;

    @NotNull
    private boolean isSubscribed;

    @NotNull
    private  boolean isAdmin;

}



