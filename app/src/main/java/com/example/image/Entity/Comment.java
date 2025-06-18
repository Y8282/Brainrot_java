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
public class Comment {
    private Long id;
    private int postId;
    private String userId;
    private String content;
    private Integer commentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
