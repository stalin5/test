package com.examly.springapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Post;
import com.examly.springapp.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Add Post
    @PostMapping("/addPost")
    public Post addPost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    // Get All Posts
    @GetMapping("/allPosts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // Get Posts by Hashtag
    @GetMapping("/byHashtag")
    public List<Post> getPostsByHashtag(@RequestParam String tag) {
        return postService.getPostsByHashtag(tag);
    }

    // Get Posts Sorted by Time
    @GetMapping("/sortedByTime")
    public List<Post> getPostsSortedByTime() {
        return postService.getPostsSortedByTime();
    }

    // Delete Post
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "Post deleted with ID: " + id;
    }
}
