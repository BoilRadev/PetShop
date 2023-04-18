package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.userDTOs.*;
import com.example.pet_shop.model.entities.User;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.NotFoundException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserWithoutPassDTO register(RegisterDTO dto) {

        if(!dto.getPassword().equals(dto.getConfirm_password())){
            throw new BadRequestException("Passwords mismatch!");
        }
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("Email already exists!");
        }
        User u = mapper.map(dto, User.class);
        u.setPassword(encoder.encode(u.getPassword()));
        u.setCreated_at(LocalDateTime.now());
        u.set_admin(true);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO login(LoginDTO dto) {

        Optional<User> u = userRepository.getByEmail(dto.getEmail());
        if(!u.isPresent()){
            throw new UnauthorizedException("Wrong credentials");
        }
        if(!encoder.matches(dto.getPassword(), u.get().getPassword())){
            throw new UnauthorizedException("Wrong credentials");
        }
        return mapper.map(u, UserWithoutPassDTO.class);
    }


    public UserEditResponseDTO edit(UserEditRequestDTO userDto, int id) {


        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

                u.setAddress(userDto.getAddress());


        userRepository.save(u);
        return mapper.map(u, UserEditResponseDTO.class);
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

    public User getLoggedUser(HttpSession session) {
        if (session.getAttribute("LOGGED") == null) {
            throw new BadRequestException("You have to log in!");

        } else {
            int userId = (int) session.getAttribute("LOGGED_ID");
            return userRepository.findById(userId).get();
        }
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public void subscribeUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.set_subscribed(true);
        userRepository.save(user);
    }


    public void unSubscribeUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.set_subscribed(false);
        userRepository.save(user);
    }


}
