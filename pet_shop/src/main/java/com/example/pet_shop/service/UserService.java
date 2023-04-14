package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.LoginDTO;
import com.example.pet_shop.model.DTOS.RegisterDTO;
import com.example.pet_shop.model.DTOS.UserWithoutPassDTO;
import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.NotFoundException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    public UserWithoutPassDTO register(RegisterDTO dto) {

        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new BadRequestException("Passwords mismatch!");
        }
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("Email already exists!");
        }
        User u = mapper.map(dto, User.class);
        u.setPassword(encoder.encode(u.getPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO login(LoginDTO dto) {

        Optional<User> u = userRepository.getByEmail(dto.email());
        if(!u.isPresent()){
            throw new UnauthorizedException("Wrong credentials");
        }
        if(!encoder.matches(dto.password(), u.get().getPassword())){
            throw new UnauthorizedException("Wrong credentials");
        }
        return mapper.map(u, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO getById(int id) {
        Optional<User> u = userRepository.findById(id);
        if(u.isPresent()){
            return mapper.map(u.get(), UserWithoutPassDTO.class);
        }
        throw new NotFoundException("User not found");
    }

    public List<UserWithoutPassDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map( u -> mapper.map(u, UserWithoutPassDTO.class))
                .collect(Collectors.toList());
    }

}
