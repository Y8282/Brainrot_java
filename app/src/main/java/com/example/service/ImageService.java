package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
    public String generateImage(String searchTerm) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return "https://example.com/images/" + searchTerm + ".jpg";
        }
        return null;
    }
}