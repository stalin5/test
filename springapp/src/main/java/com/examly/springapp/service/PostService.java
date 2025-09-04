package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.PostNotFoundException;
import com.examly.springapp.model.Post;
import com.examly.springapp.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // Save a new post
    public Post savePost(Post post) {
        if (post.getTimestamp() == null) {
            post.setTimestamp(LocalDateTime.now());
        }
        return postRepository.save(post);
    }

    // Get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get posts by hashtag
    public List<Post> getPostsByHashtag(String tag) {
        return postRepository.findByHashtagsContaining(tag);
    }

    // Get posts sorted by time
    public List<Post> getPostsSortedByTime() {
        return postRepository.findAllByOrderByTimestampDesc();
    }

    // Delete post by ID
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException("Post with ID " + id + " not found.");
        }
        postRepository.deleteById(id);
    }
}
