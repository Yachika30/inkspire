package com.inkspire.controllers;

import com.inkspire.models.Post;
import com.inkspire.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // ✅ Create a new post
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);  // Saves post in the database
    }

    // ✅ Get all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();  // Fetches posts from the database
    }

    // ✅ Get a single post by ID
    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable Long id) {
        return postRepository.findById(id);
    }

    // ✅ Update a post
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setAuthor(updatedPost.getAuthor());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // ✅ Delete a post
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "Post deleted successfully";
    }
}
