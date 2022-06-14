package com.example.jibberjabber.service.impl;

import com.example.jibberjabber.entity.Post;
import com.example.jibberjabber.exception.PostNotFoundException;
import com.example.jibberjabber.model.PostCreateDTO;
import com.example.jibberjabber.model.PostDTO;
import com.example.jibberjabber.repository.PostRepository;
import com.example.jibberjabber.service.PostService;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostCreateDTO postCreateDTO) {
        Post post = Post.builder()
                .message(postCreateDTO.getText())
                .user(postCreateDTO.getUser())
                .responses(List.of())
                .date(LocalDateTime.now())
                .build();

        return postRepository.save(post).toDTO();
    }

    @Override
    public Page<PostDTO> getAllPostsByUser(String user, Pageable pageable) {
        return postRepository.findAllByUser(user, pageable).map(Post::toDTO);
    }

    @Override
    public void deletePost(UUID uuid) {
        if (postRepository.existsById(uuid)) {
            postRepository.deleteById(uuid);
            return;
        }
        throw new PostNotFoundException(String.format("No post found for id: %s", uuid));
    }

    @Override
    public PostDTO getPostById(UUID uuid) {
        if (postRepository.existsById(uuid)) {
            val post = postRepository.findById(uuid);
            return post.get().toDTO();
        }
        throw new PostNotFoundException(String.format("No post found for id: %s", uuid));

    }

    @Override
    public PostDTO respond(UUID id, PostCreateDTO postCreateDTO) {
        if (postRepository.existsById(id)) {
            val post = postRepository.findById(id).get();

            Post responsePost = Post.builder()
                    .message(postCreateDTO.getText())
                    .user(postCreateDTO.getUser())
                    .responses(List.of())
                    .date(LocalDateTime.now())
                    .build();


            List<Post> responses= post.getResponses();
            responses.add(responsePost);
            post.setResponses(responses);

            val updatedPost= postRepository.save(post);
            return updatedPost.toDTO();

        }
        throw new PostNotFoundException(String.format("No post found for id: %s", id));

    }

    @Override
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::toDTO);
    }
}
