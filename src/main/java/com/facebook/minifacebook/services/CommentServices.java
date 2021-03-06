package com.facebook.minifacebook.services;

import com.facebook.minifacebook.model.CommentModel;
import com.facebook.minifacebook.model.PostModel;
import com.facebook.minifacebook.repository.CommentRepository;
import com.facebook.minifacebook.serviceInterfaces.commentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class CommentServices implements commentInterface {

    private final CommentRepository commentRepo;

    @Autowired
    public CommentServices(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void createComment(CommentModel commentModel){
        commentRepo.save(commentModel);
    }

    public List<CommentModel> getAllComments(Long postId){
        List<CommentModel> allComments = commentRepo.findAllByPostId(postId);
        return allComments;
    }
}
