package com.example.post.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Image {
    private Long id;
    private String fileName;
    private String filePath;
}