package com.facebook.minifacebook.repository;

import com.facebook.minifacebook.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {


}
