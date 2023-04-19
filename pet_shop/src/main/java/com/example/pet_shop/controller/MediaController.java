package com.example.pet_shop.controller;

import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.service.MediaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
public class MediaController extends AbstractController {

    @Autowired
    private MediaService mediaService;
    @Autowired
    protected Logger logger;

    @PostMapping("/products/{productId}/media")
    public ResponseEntity<?> upload(@PathVariable int productId, @RequestParam("file") MultipartFile file, HttpSession s) {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        mediaService.upload(file, productId);
        return ResponseEntity.ok("Image uploaded successfully.");
    }

    @SneakyThrows
    @GetMapping("/media/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse resp){
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        File f = mediaService.download(fileName);
        Files.copy(f.toPath(), resp.getOutputStream());
    }
}
