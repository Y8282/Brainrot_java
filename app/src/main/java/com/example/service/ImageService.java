package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.BrainrotImage;
import com.example.repository.BrainrotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final BrainrotRepository brainrotRepository;

    public String generateImage(String searchTerm) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return "https://example.com/images/" + searchTerm + ".jpg";
        }
        return null;
    }

    public BrainrotImage findById(Long id) {
        return brainrotRepository.getReferenceById(id);
    }
}