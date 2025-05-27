package com.example.controller;

import com.example.entity.Image;
import com.example.service.ImageService;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/save")
    public ResponseEntity<Image> saveImage(@RequestParam("fileName") String fileName) {
        Image image = imageService.SaveImage(fileName);

        return ResponseEntity.ok(image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        File file = new File("Uploads/" + image.getFileName());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}