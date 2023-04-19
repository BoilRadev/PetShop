package com.example.pet_shop.service;

import com.example.pet_shop.model.entities.Image;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.NotFoundException;
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
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String name = UUID.randomUUID() + "." + ext;
            File dir = new File("uploads");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(dir, name);
            Files.copy(file.getInputStream(), f.toPath());
            String url = dir.getName() + File.separator + f.getName();
            Product p = getProductByID(productId);
            Image image = new Image();
            image.setUrl(url);
            image.setProduct(p);

            p.getImages().add(image);
            //TODO Краси, трябва ли да сейфам снимката в листа и после продукта в репоситори ?
            mediaRepository.save(image);
            productRepository.save(p);

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
}
