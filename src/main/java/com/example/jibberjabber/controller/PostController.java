package com.example.jibberjabber.controller;

import com.example.jibberjabber.model.PostCreateDTO;
import com.example.jibberjabber.service.PostService;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody PostCreateDTO postCreateDTO) {
        val createdPost = postService.createPost(postCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("/all/{user}")
    public ResponseEntity<?> getAllPostsByUser (@PathVariable String user) {
        val posts = postService.getAllPostsByUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable UUID id) {
        val post = postService.getPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PatchMapping("/{id}/respond")
    public ResponseEntity<?> respond(@PathVariable UUID id,@Valid @RequestBody PostCreateDTO postCreateDTO) {
        val post = postService.respond(id,postCreateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
}
