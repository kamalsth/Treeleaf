package com.treeleaf.restapi.services;

import com.treeleaf.restapi.dtos.Image;
import com.treeleaf.restapi.entities.BlogPost;
import com.treeleaf.restapi.repositories.BlogPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogPostService {
    private BlogPostRepository blogPostRepository;
    private UserService userService;

    public ResponseEntity<?> addPost(BlogPost blogPost) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        blogPost.setUser(userService.getUserByUsername(username));
        blogPostRepository.save(blogPost);
        return ResponseEntity.ok("Post added successfully");
    }

    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(blogPostRepository.findAll());
    }


    public ResponseEntity<?> getPostById(Long postId) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(postId);
        if (blogPost.isPresent()) {
            return ResponseEntity.ok(blogPost.get());
        }
        return ResponseEntity.badRequest().body("Post not found with id: " + postId);
    }


    public ResponseEntity<?> updatePostById(Long id, BlogPost blogPost) {
        Optional<BlogPost> existingPost = blogPostRepository.findById(id);
        if (existingPost.isPresent()) {
            BlogPost post = existingPost.get();
            post.setPostTitle(blogPost.getPostTitle());
            post.setPostDescription(blogPost.getPostDescription());
            blogPostRepository.save(post);
            return ResponseEntity.ok("Post updated successfully");
        }
        return ResponseEntity.badRequest().body("Post not found with id: " + id);

    }

    public ResponseEntity<?> deletePostById(Long postId) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(postId);
        if (blogPost.isPresent()) {
            blogPostRepository.deleteById(postId);
            return ResponseEntity.ok("Post deleted successfully");

        }
        return ResponseEntity.badRequest().body("Post not found with id: " + postId);
    }


    public ResponseEntity<?> addThumbnailToPost(Long postId, MultipartFile file) throws IOException {
        Optional<BlogPost> blogPost = blogPostRepository.findById(postId);
        if (blogPost.isPresent()) {
            BlogPost post = blogPost.get();
            Byte[] thumbnail = Image.uploadImage(file);
            post.setThumbnail(thumbnail);
            blogPostRepository.save(post);
            return ResponseEntity.ok("Thumbnail added successfully");
        }
        return ResponseEntity.badRequest().body("Post not found with id: " + postId);
    }

    public ResponseEntity<?> getThumbnailByPostId(Long postId) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(postId);
        if (blogPost.isPresent()) {
            BlogPost post = blogPost.get();
            byte[] thumbnailBytes = Image.getImage(post.getThumbnail());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(thumbnailBytes, headers, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("Post not found with id: " + postId);
    }
}
