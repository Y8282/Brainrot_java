package com.example.post.Entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import lombok.Data;

@Data
public class PostDto {
    private int id;
    private String title;
    private String content;
    private int imgId;
    private String author;
    private String image;
    private String username;
    private boolean liked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
