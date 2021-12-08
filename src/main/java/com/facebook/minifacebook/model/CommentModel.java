package com.facebook.minifacebook.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CommentModel {

    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    private Long commentId;

    @Column(nullable = false, updatable = false)
    private Long postId;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "text")
    private String commentText;

    @Column(updatable = false)
    private String userName;

    public CommentModel(Long postId, Long userId, String commentText, String userName) {
        this.postId = postId;
        this.userId = userId;
        this.commentText = commentText;
        this.userName = userName;
    }
}
