package com.example.image.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.image.Service.ImageService;
import com.example.login.model.ApiResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse> generateImage(@RequestBody ImageRequest request) {
        String imageBase64 = imageService.generateImage(request.getHead(), request.getBody(),
                request.getArm(), request.getLegs(), request.getTail());

        if (imageBase64 != null) {
            ApiResponse response = new ApiResponse("000", "Image generated");
            response.getResultData().put("requestId", "generateImage");
            response.getResultData().put("imageBase64", imageBase64);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new ApiResponse("500", "Failed to generate image"));
    }

    @Data
    static class ImageRequest {
        private String head;
        private String body;
        private String arm;
        private String legs;
        private String tail;
    }

    @Data
    public class FastApiResponse {
        private String responseCode;
        private Long id;
        private String imageBase64; // Base64 문자열
    }

}
