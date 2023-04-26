package com.example.pet_shop.service;

import com.example.pet_shop.model.entities.Image;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.MediaRepository;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService extends AbstractService {

    @Autowired
    private MediaRepository mediaRepository;
    private final String uploadDir = "uploads/";

    public void upload(MultipartFile file, int productId) {
        try {
            Product p = getProductByID(productId);
            if (p == null) {
                throw new NotFoundException("Product not found");
            }

            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String name = UUID.randomUUID() + "." + ext;
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(dir, name);
            Files.copy(file.getInputStream(), f.toPath());
            String url = uploadDir + f.getName();

            Image image = new Image();
            image.setUrl(url);
            image.setProduct(p);

            mediaRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<Image> getImagesByProductId(int productId) {
        Product product = getProductByID(productId);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        return product.getImages();
    }

    public File getFileByName(String fileName) {
        File dir = new File(uploadDir);
        File file = new File(dir, fileName);
        if (!file.exists()) {
            throw new NotFoundException("File not found");
        }
        return file;
    }
}
