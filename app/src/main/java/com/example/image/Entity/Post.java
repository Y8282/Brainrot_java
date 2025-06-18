package com.example.image.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
public class Post {
    private Long id;
    private String title;
    private String content;
    private int imgId;
    private String author;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
