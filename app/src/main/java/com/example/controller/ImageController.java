package com.example.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.BrainrotImage;
import com.example.login.model.ApiResponse;
import com.example.service.ImageService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Cors 추가하기 귀찮아서 일단 이렇게만 해 놓음.
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

    @GetMapping(value = "/raw/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> fetchRaw(@PathVariable Long id) {
        BrainrotImage ent = imageService.findById(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(ent.getImage());
    }

}

@Data
class ImageRequest {
    private String searchTerm;
    private String requestId;
}