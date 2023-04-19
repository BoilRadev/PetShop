package com.example.pet_shop.controller;

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


    @PostMapping("/products/{productId}/media")
    public ResponseEntity<?> upload(@PathVariable int productId, @RequestParam("file") MultipartFile file, HttpSession s) {
        mediaService.upload(file, productId,s);
        return ResponseEntity.ok("Image uploaded successfully.");
    }

    @SneakyThrows
    @GetMapping("/media/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse resp){
        File f = mediaService.download(fileName);
        Files.copy(f.toPath(), resp.getOutputStream());
    }
}
