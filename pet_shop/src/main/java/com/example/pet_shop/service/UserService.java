package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.userDTOs.*;
import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserWithoutPassDTO register(RegisterDTO dto) {
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new BadRequestException("Passwords mismatch!");
        }
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("Email already exists!");
        }
        User u = mapper.convertValue(dto, User.class);
        u.setPassword(encoder.encode(u.getPassword()));
        u.setCreatedAt(LocalDateTime.now());
        u.setAdmin(dto.isAdmin());
        u.setSubscribed(dto.isSubscribed());
        u.setPersonalDiscount(BigDecimal.valueOf(dto.isSubscribed() ? 5 : 0));
        userRepository.save(u);
        logger.info("User with email : "+ u.getEmail() + "have registered");
        return mapper.convertValue(u, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO login(LoginDTO dto) {
        Optional<User> u = userRepository.getByEmail(dto.getEmail());
        if (u.isEmpty()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        if (!encoder.matches(dto.getPassword(), u.get().getPassword())) {
            throw new UnauthorizedException("Wrong credentials");
        }
        logger.info(u.get().getEmail() + "have logged in");
        return mapper.convertValue(u.get(), UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO edit(RegisterDTO dto, int userId) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new NotFoundException("Email already exists!");
        }
        User u = getUserById(userId);
        u.setEmail(dto.getEmail());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setPhoneNumber(dto.getPhoneNumber());
        u.setTown(dto.getTown());
        u.setAddress(dto.getAddress());
        u.setSubscribed(dto.isSubscribed());

        userRepository.save(u);
        return mapper.convertValue(u, UserWithoutPassDTO.class);
    }
    public UserWithoutPassDTO getById(int id) {
        Optional<User> u = userRepository.findById(id);
        if(u.isPresent()){
            return mapper.convertValue(u.get(), UserWithoutPassDTO.class);
        }
        throw new NotFoundException("User not found");
    }

    public List<UserWithoutPassDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map( u -> mapper.convertValue(u, UserWithoutPassDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public void subscribeUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setSubscribed(true);
        user.setPersonalDiscount(BigDecimal.valueOf(5));
        userRepository.save(user);
    }

    public void unSubscribeUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setSubscribed(false);
        user.setPersonalDiscount(BigDecimal.valueOf(5));
        userRepository.save(user);
    }
}
