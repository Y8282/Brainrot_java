package com.example.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.login.model.ApiResponse;
import com.example.service.ImageService;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse> generateImage(@RequestBody ImageRequest request) {
        String imageUrl = imageService.generateImage(request.getSearchTerm());
        if (imageUrl != null) {
            ApiResponse response = new ApiResponse("000", "Image generated");
            response.getResultData().put("requestId", request.getRequestId());
            response.getResultData().put("imageUrl", imageUrl);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new ApiResponse("500", "Failed to generate image"));
    }
}

@Data
class ImageRequest {
    private String searchTerm;
    private String requestId;
}