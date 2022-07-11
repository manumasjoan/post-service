package com.example.jibberjabber.service.impl;

import com.example.jibberjabber.entity.Post;
import com.example.jibberjabber.exception.PostNotFoundException;
import com.example.jibberjabber.model.PostCreateDTO;
import com.example.jibberjabber.model.PostDTO;
import com.example.jibberjabber.repository.PostRepository;
import com.example.jibberjabber.service.PostService;
import com.example.jibberjabber.service.UserService;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = Logger.getLogger(PostServiceImpl.class.getName());

    private final PostRepository postRepository;
    private final UserService userService;

    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public PostDTO createPost(PostCreateDTO postCreateDTO) {
        logger.info("Creating post");
        Post post = Post.builder()
                .message(postCreateDTO.getText())
                .user(userService.getCurrentUser())
                .responses(List.of())
                .build();

        logger.info("cerated post: " + post);
        logger.info("Saving post");

        return postRepository.save(post).toDTO();
    }

    @Override
    public Page<PostDTO> getAllPostsByUser(UUID userId, Pageable pageable) {
        return postRepository.findAllByUser(userService.getUserById(userId), pageable).map(Post::toDTO);
    }

    @Override
    public void deletePost(UUID uuid) {
        if (postRepository.existsById(uuid)) {
            logger.info("Deleting post");
            val post = postRepository.findById(uuid).get();
            if(post.getUser().getId().equals(userService.getCurrentUser().getId())) {
                postRepository.deleteById(uuid);
            } else {
                throw new RuntimeException("You can't delete post from another user");
            }
            return;
        }
        throw new PostNotFoundException(String.format("No post found for id: %s", uuid));
    }

    @Override
    public PostDTO getPostById(UUID uuid) {
        logger.info("Getting post by id");
        if (postRepository.existsById(uuid)) {
            val post = postRepository.findById(uuid);
            logger.info("Found post: " + post);
            return post.get().toDTO();
        }
        throw new PostNotFoundException(String.format("No post found for id: %s", uuid));

    }

    @Override
    public PostDTO respond(UUID id, PostCreateDTO postCreateDTO) {
        logger.info("Responding to post");
        if (postRepository.existsById(id)) {
            val post = postRepository.findById(id).get();

            Post responsePost = Post.builder()
                    .message(postCreateDTO.getText())
                    .user(userService.getCurrentUser())
                    .responses(List.of())
                    .build();

            logger.info("Created response post: " + responsePost);

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
        logger.info("Getting all posts");
        return postRepository.findAll(pageable).map(Post::toDTO);
    }
}
