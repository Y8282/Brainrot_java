package com.example.post.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.post.Entity.BrainrotImage;
import com.example.post.Entity.Comment;
import com.example.post.Entity.Likecheck;
// import com.example.image.service.ImageService;
import com.example.login.model.ApiResponse;
import com.example.post.Entity.PostDto;
import com.example.post.mapper.PostMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Cors 추가하기 귀찮아서 일단 이렇게만 해 놓음.
public class ImageController {
    private final PostMapper postMapper;
    // private final ImageService imageService;

    // @PostMapping("/generate")
    // public ResponseEntity<ApiResponse> generateImage(@RequestBody ImageRequest
    // request) {
    // String imageUrl = imageService.generateImage(request.getSearchTerm());
    // if (imageUrl != null) {
    // ApiResponse response = new ApiResponse("000", "Image generated");
    // response.getResultData().put("requestId", request.getRequestId());
    // response.getResultData().put("imageUrl", imageUrl);
    // return ResponseEntity.ok(response);
    // }
    // return ResponseEntity.ok(new ApiResponse("500", "Failed to generate image"));
    // }

    // 이미지 가져오기 -> image.network
    @GetMapping(value = "/raw/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> fetchRaw(@PathVariable Long id) {
        BrainrotImage ent = postMapper.selectimage(id);
        if (ent == null || ent.getImage() == null) {
            return ResponseEntity.notFound().build();
        }

        MediaType contentType = detectImageType(ent.getImage());
        if (contentType == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .ok()
                .contentType(contentType)
                .header("Cache-Control", "public, max-age=31536000")
                .body(ent.getImage());
    }

    // 이미지 타입설정
    private MediaType detectImageType(byte[] imageData) {
        if (imageData == null || imageData.length < 4) {
            return null;
        }
        // JPEG
        if (imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8 && imageData[2] == (byte) 0xFF) {
            return MediaType.IMAGE_JPEG;
        }
        // PNG
        if (imageData[0] == (byte) 0x89 && imageData[1] == (byte) 0x50 && imageData[2] == (byte) 0x4E
                && imageData[3] == (byte) 0x47) {
            return MediaType.IMAGE_PNG;
        }
        return null;
    }

    // 글 전체 list
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> postList() {
        try {
            List<PostDto> posts = postMapper.selectAllPosts();

            // Base64 인코딩
            List<PostDto> encodedPosts = posts.stream().map(post -> {
                if (post.getImage() != null) {
                    String encoded = Base64.getEncoder().encodeToString(post.getImage().getBytes());
                    post.setImage(encoded);
                }
                return post;
            }).collect(Collectors.toList());
            encodedPosts.forEach(post -> System.out.println("Post : " + post));

            ApiResponse response = new ApiResponse("000", "글 불러오기");
            response.getResultData().put("posts", posts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse("500", "서버 오류 : " +
                    e.getStackTrace()));
        }
    }

    // 댓글 가져오기
    @PostMapping("/commentlist")
    public ResponseEntity<ApiResponse> commentList(@RequestBody Map<String, Integer> request) {
        try {

            List<Comment> comments = postMapper.selectAllComments(request.get("postId"));
            ApiResponse response = new ApiResponse("000", "댓글 불러오기");
            response.getResultData().put("comments", comments);

            comments.forEach(comment -> System.out.println("Comment : " + comment));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse("500", "서버 오류 : " +
                    e.getStackTrace()));
        }
    }

    // 댓글 쓰기
    @PostMapping("/commentsubmit")
    public ResponseEntity<ApiResponse> commentSubmit(@RequestBody Comment comment) {
        try {
            System.out.println("Received comment : " + comment);
            postMapper.insertComment(comment);
            ApiResponse response = new ApiResponse("000", "댓글 추가 성공");
            response.getResultData().put("postId", comment.getPostId());
            response.getResultData().put("userId", comment.getUserId());
            response.getResultData().put("commentId", comment.getCommentId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse("500", "서버 오류 : " + e.getStackTrace()));
        }
    }

    // 하트
    @PostMapping("/lovepost")
    public ResponseEntity<ApiResponse> toggleLovePost(@RequestBody Likecheck likecheck) {
        try {
            System.out.println("Received post : " + likecheck);

            boolean existsLove = postMapper.existsLove(likecheck);

            if (existsLove) {
                postMapper.deleteLove(likecheck);
            } else {
                postMapper.insertLove(likecheck);
            }

            ApiResponse response = new ApiResponse("000", "토글 하트 성공");
            response.getResultData().put("postId", likecheck.getPostId());
            response.getResultData().put("liked", !existsLove);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse("500", "+서버 오류 : " + e.getStackTrace()));
        }
    }

}

@Data
class CommentRequest {
    private int id;
    private int postId;
    private String userId;
    private String content;
    private int commentId;
}