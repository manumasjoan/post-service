package com.example.jibberjabber.service;

import com.example.jibberjabber.model.PostCreateDTO;
import com.example.jibberjabber.model.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostService {

    PostDTO createPost(PostCreateDTO postCreateDTO);

    Page<PostDTO> getAllPostsByUser(String user, Pageable pageable);

    void deletePost(UUID uuid);

    PostDTO getPostById(UUID uuid);

    PostDTO respond(UUID id, PostCreateDTO postCreateDTO);
}
