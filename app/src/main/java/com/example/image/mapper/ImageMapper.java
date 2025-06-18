package com.example.image.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.image.Entity.BrainrotImage;
import com.example.image.Entity.Comment;
import com.example.image.Entity.PostDto;

@Mapper
public interface ImageMapper {

    List<PostDto> selectAllPosts();

    BrainrotImage selectimage(Long id);

    List<Comment> selectAllComments(Integer postId);
}
