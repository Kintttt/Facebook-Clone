package com.facebook.minifacebook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor

public class PostModel {

    @Id
    @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    private Long post_id;

    @Column(nullable = false, columnDefinition = "text")
    private String postText;

    @Column(nullable = false)
    private int noOfLikes;

    @Column(nullable = false)
    private int noOfComments;

    @Column(nullable = false, updatable = false)
    private Long posterId;

    @Column(updatable = false)
    private String posterName;


    public PostModel(String postText, int noOfLikes, int noOfComments, Long posterId, String posterName) {
        this.postText = postText;
        this.noOfLikes = noOfLikes;
        this.noOfComments = noOfComments;
        this.posterId = posterId;
        this.posterName = posterName;
    }

}
