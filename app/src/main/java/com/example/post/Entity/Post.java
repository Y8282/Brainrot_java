package com.example.post.Entity;

import java.time.LocalDateTime;


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
