package com.facebook.minifacebook.services;

import com.facebook.minifacebook.model.PostModel;
import com.facebook.minifacebook.repository.PostRepository;
import com.facebook.minifacebook.serviceInterfaces.postInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class PostServices implements postInterface {

    private final PostRepository postRepo;


    @Autowired
    public PostServices(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public void createPost(PostModel postModel){

        postRepo.save(postModel);
    }

    public List<PostModel> getAllPosts(){
        List<PostModel> postList =  postRepo.findAll();
        Collections.reverse(postList);
        return postList;
    }

    public void deletePost(Long postId){

        PostModel model = postRepo.findById(postId).get();
        postRepo.delete(model);
    }

    public PostModel getPostById(Long postId){
        PostModel model = postRepo.findById(postId).get();
        return model;
    }

}
