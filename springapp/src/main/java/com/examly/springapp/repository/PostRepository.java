package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Custom query to filter posts by hashtags
    List<Post> findByHashtagsContaining(String tag);

    // Custom query to sort posts by time (latest first)
    List<Post> findAllByOrderByTimestampDesc();
}
