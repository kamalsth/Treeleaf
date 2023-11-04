package com.treeleaf.restapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blogs")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postTitle;
    private String postDescription;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Lob
    @Column(name = "thumbnail")
    private Byte[] thumbnail;

}
