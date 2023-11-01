package com.treeleaf.restapi.controllers;

import com.treeleaf.restapi.dtos.CommentDto;
import com.treeleaf.restapi.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private CommentService commentService;

    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody @Validated CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }


    @GetMapping("/getAllComments")
    public ResponseEntity<?> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/getCommentById")
    public ResponseEntity<?> getCommentById(@RequestParam(name = "commentId") Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @PatchMapping("/updateCommentById")
    public ResponseEntity<?> updateCommentById(@RequestParam(name = "commentId") Long commentId, @RequestBody @Validated CommentDto commentDto) {
        return commentService.updateCommentById(commentId, commentDto);
    }

    @DeleteMapping("/deleteCommentById")
    public ResponseEntity<?> deleteCommentById(@RequestParam(name = "commentId") Long commentId) {
        return commentService.deleteCommentById(commentId);
    }
}
