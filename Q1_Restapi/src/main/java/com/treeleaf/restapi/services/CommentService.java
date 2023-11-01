package com.treeleaf.restapi.services;

import com.treeleaf.restapi.dtos.CommentDto;
import com.treeleaf.restapi.entities.BlogPost;
import com.treeleaf.restapi.entities.Comment;
import com.treeleaf.restapi.repositories.BlogPostRepository;
import com.treeleaf.restapi.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private BlogPostRepository blogPostRepository;
    private UserService userService;

    public ResponseEntity<?> addComment(CommentDto commentDto) {
        Comment comment = commentDto.mapToComment();
        Optional<BlogPost> blogPost = blogPostRepository.findById(commentDto.getPostId());
        if (blogPost.isEmpty()) {
            return ResponseEntity.badRequest().body("Post not found with id: " + commentDto.getPostId());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        comment.setUser(userService.getUserByUsername(authentication.getName()));
        comment.setBlogPost(blogPost.get());
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment added successfully");
    }

    public ResponseEntity<?> getAllComments() {
        return ResponseEntity.ok(commentRepository.findAll());
    }

    public ResponseEntity<?> getCommentById(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return ResponseEntity.ok(comment.get());
        }
        return ResponseEntity.badRequest().body("Comment not found with id: " + commentId);
    }

    public ResponseEntity<?> updateCommentById(Long commentId, CommentDto commentDto) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (comment.getUser() != userService.getUserByUsername(authentication.getName())) {
                return ResponseEntity.badRequest().body("You are not authorized to update this comment");
            }
            comment.setComment(commentDto.getComment());
            commentRepository.save(comment);
            return ResponseEntity.ok("Comment updated successfully");
        }
        return ResponseEntity.badRequest().body("Comment not found with id: " + commentId);
    }

    public ResponseEntity<?> deleteCommentById(Long commentId) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (comment.getUser() != userService.getUserByUsername(authentication.getName())) {
                return ResponseEntity.badRequest().body("You are not authorized to update this comment");
            }
            commentRepository.deleteById(commentId);
            return ResponseEntity.ok("Comment deleted successfully");
        }
        return ResponseEntity.badRequest().body("Comment not found with id: " + commentId);
    }
}
