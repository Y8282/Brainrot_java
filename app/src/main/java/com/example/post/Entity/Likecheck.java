package com.example.post.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Likecheck {

    private String userId;
    private int postId;
}
