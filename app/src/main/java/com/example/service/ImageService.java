package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Image;
import com.example.repository.ImageRepository;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // 이미지 저장
    public Image SaveImage(String fileName) {
        Image image = new Image();
        image.setFileName(fileName);
        image.setFilePath(fileName);
        return imageRepository.save(image);
    }

    // 이미지 가져오기
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no image"));
    }
}
