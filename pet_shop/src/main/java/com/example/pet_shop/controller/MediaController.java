package com.example.pet_shop.controller;

import com.example.pet_shop.model.entities.Image;
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
import java.util.List;

@RestController
public class MediaController extends AbstractController {

    @Autowired
<<<<<<< Updated upstream
    protected LoginManager loginManager;
=======
    private MediaService mediaService;
>>>>>>> Stashed changes

    @Autowired
    private MediaService mediaService;

    @PostMapping("/products/{productId}/media")
<<<<<<< Updated upstream
    public ResponseEntity<?> upload(@PathVariable int productId, @RequestParam("file") MultipartFile file, HttpSession ses) {
        checkAuthorization(loginManager);
=======
    public ResponseEntity<?> upload(@PathVariable int productId, @RequestParam("file") MultipartFile file, HttpSession s) {

        isAdminLoggedIn(s);
>>>>>>> Stashed changes
        mediaService.upload(file, productId);
        return ResponseEntity.ok("Image uploaded successfully.");
    }

    @SneakyThrows
    @GetMapping("/media/{fileName}")
<<<<<<< Updated upstream
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response){
        checkLogin(loginManager);
        File file = mediaService.getFileByName(fileName);
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }

    @GetMapping("/products/{productId}/media")
    public ResponseEntity<List<Image>> getImagesByProductId(@PathVariable int productId) {
        List<Image> images = mediaService.getImagesByProductId(productId);
        return ResponseEntity.ok(images);
=======
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse resp, HttpSession s){

        getLoggedId(s);
        File f = mediaService.download(fileName);
        Files.copy(f.toPath(), resp.getOutputStream());
>>>>>>> Stashed changes
    }
}
