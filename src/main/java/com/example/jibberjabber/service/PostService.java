package com.example.jibberjabber.service;

import com.example.jibberjabber.model.PostCreateDTO;
import com.example.jibberjabber.model.PostDTO;

import java.util.List;
import java.util.UUID;

public interface PostService {

    PostDTO createPost(PostCreateDTO postCreateDTO);

    List<PostDTO> getAllPostsByUser(String user);

    void deletePost(UUID uuid);

    PostDTO getPostById(UUID uuid);

}
