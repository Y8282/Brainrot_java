package com.example.login.model;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class ApiResponse {
    private String resultCode;
    private String resultMessage;
    private Map<String, Object> resultData = new HashMap<>();

    public ApiResponse(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}