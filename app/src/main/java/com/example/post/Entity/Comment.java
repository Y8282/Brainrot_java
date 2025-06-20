package com.example.post.Entity;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private int postId;
    private String userId;
    private String content;
    private Integer commentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
