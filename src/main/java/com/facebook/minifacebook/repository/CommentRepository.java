package com.facebook.minifacebook.repository;

import com.facebook.minifacebook.POJO.Post;
import com.facebook.minifacebook.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {

    List<CommentModel> findAllByPostId(Long postId);
    List<CommentModel> findByPostId(Long postId);


}
