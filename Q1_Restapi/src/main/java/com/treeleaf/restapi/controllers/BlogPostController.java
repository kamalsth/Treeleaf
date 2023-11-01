package com.treeleaf.restapi.controllers;

import com.treeleaf.restapi.dtos.BlogPostDto;
import com.treeleaf.restapi.services.BlogPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/blog")
public class BlogPostController {
    private BlogPostService blogPostService;

    @PostMapping("/addPost")
    public ResponseEntity<?> addBlogPost(@RequestBody @Validated BlogPostDto blogPostDto) {
        return blogPostService.addPost(blogPostDto.mapToBlogPost());
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<?> getAllBlogPosts() {
        return blogPostService.getAllPosts();
    }


    @GetMapping("/getPostById")
    public ResponseEntity<?> getBlogPostById(@RequestParam(name = "postId") Long postId) {
        return blogPostService.getPostById(postId);
    }


    @PatchMapping("/updatePostById")
    public ResponseEntity<?> updateBlogPostById(@RequestParam(name = "postId") Long postId, @RequestBody @Validated BlogPostDto blogPostDto) {
        return blogPostService.updatePostById(postId, blogPostDto.mapToBlogPost());
    }

    @DeleteMapping("/deletePostById")
    public ResponseEntity<?> deleteBlogPostById(@RequestParam(name = "postId") Long postId) {
        return blogPostService.deletePostById(postId);
    }

    @PostMapping("/uploadThumbnail")
    public ResponseEntity<?> addThumbnail(@RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam(name = "postId") Long postId) throws IOException {
        return blogPostService.addThumbnailToPost(postId, thumbnail);
    }


    @GetMapping("/getThumbnail")
    public ResponseEntity<?> getThumbnail(@RequestParam(name = "postId") Long postId) {
        return blogPostService.getThumbnailByPostId(postId);
    }
}
