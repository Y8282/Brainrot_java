package com.example.image.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final WebClient webClient;

    public String generateImage(String head, String body, String arm, String legs,
            String tail) {
        Map<String, String> request = new HashMap<>();
        request.put("head", head);
        request.put("body", body);
        request.put("arm", arm);
        request.put("legs", legs);
        request.put("tail", tail);
        request.put("add", null);
        request.put("drs", null);

        Mono<FastApiResponse> apiResp = webClient.post()
                .uri("/save-image")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FastApiResponse.class);

        FastApiResponse responseBody = apiResp.block();

        return responseBody.getImageBase64();
    }

    @Data
    public static class FastApiResponse {
        private String responseCode;
        private Long id;
        private String imageBase64; // Base64 문자열
    }
}
