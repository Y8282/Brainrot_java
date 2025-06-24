package com.example.post.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.post.Entity.BrainrotImage;
import com.example.post.Entity.Comment;
import com.example.post.Entity.Likecheck;
import com.example.post.Entity.Post;
import com.example.post.Entity.PostDto;

@Mapper
public interface PostMapper {

    List<PostDto> selectAllPosts();
    
    List<Post> selectMyPosts(String email);

    BrainrotImage selectimage(Long id);

    List<Comment> selectAllComments(int postId);

    void insertComment(Comment comment);

    void commentDelete(Comment comment);

    Comment findParentCommentByUserId(String userId , int postId);

    void insertLove(Likecheck Likecheck);
    
    boolean existsLove(Likecheck Likecheck);

    void deleteLove(Likecheck Likecheck);

}