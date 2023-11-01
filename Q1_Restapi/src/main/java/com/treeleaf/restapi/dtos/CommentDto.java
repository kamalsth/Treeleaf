package com.treeleaf.restapi.dtos;

import com.treeleaf.restapi.entities.Comment;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotNull(message = "comment cannot be null")
    @NotEmpty(message = "comment  cannot be empty")
    private String comment;

    private Long postId;

    public Comment mapToComment() {
        Comment comment1 = new Comment();
        comment1.setComment(comment);
        return comment1;
    }

}
