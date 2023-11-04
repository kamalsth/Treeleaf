package com.treeleaf.restapi.dtos;

import com.treeleaf.restapi.entities.BlogPost;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class BlogPostDto {

    @NotNull(message = "postTitle is required")
    @NotEmpty(message = "postTitle  is required")
    @Size(min = 2, max = 20, message = "postTitle must be between 2 and 20 characters")
    private String postTitle;

    @NotNull(message = "postDescription is required")
    @NotEmpty(message = "postDescription  is required")
    @Size(min = 2, max = 2000, message = "postDescription must be between 2 and 2000 characters")
    private String postDescription;


    public BlogPost mapToBlogPost() {
        BlogPost blogPost = new BlogPost();
        blogPost.setPostTitle(getPostTitle());
        blogPost.setPostDescription(getPostDescription());
        return blogPost;
    }
}
