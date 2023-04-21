package com.example.pet_shop.service;

import com.example.pet_shop.model.entities.Image;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.MediaRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class MediaService extends AbstractService {

    @Autowired
    private MediaRepository mediaRepository;
    public void upload(MultipartFile file, int productId) {
        try {
            Product p = getProductByID(productId);
            if (p == null) {
                throw new NotFoundException("Product not found");
            }

            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String name = UUID.randomUUID() + "." + ext;
            String path = "uploads/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(dir, name);
            Files.copy(file.getInputStream(), f.toPath());
            String url = path + f.getName();

            Image image = new Image();
            image.setUrl(url);
            image.setProduct(p);

            mediaRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
    }
    public File download(String fileName) {
        File dir = new File("uploads");
        File f = new File(dir, fileName);
        if (f.exists()) {
            return f;
        }
        throw new NotFoundException("File not found");
    }

    public Image getMediaById(int mediaId) {
       return mediaRepository.getReferenceById(mediaId);
    }
}
