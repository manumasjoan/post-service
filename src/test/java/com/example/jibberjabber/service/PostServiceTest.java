package com.example.jibberjabber.service;

import com.example.jibberjabber.exception.PostNotFoundException;
import com.example.jibberjabber.model.PostCreateDTO;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PostServiceTest {

    @Autowired
    private PostService postService;

    PostCreateDTO post1= PostCreateDTO.builder()
            .message("testing")
            .user("user")
            .build();
    PostCreateDTO post2= PostCreateDTO.builder()
            .message("test")
            .user("user2")
            .build();
    PostCreateDTO post3= PostCreateDTO.builder()
            .message("message")
            .user("user")
            .build();

    @Test
    void Test001_PostServiceWhenReceivesValidPostCreateDTOShouldCreatePost() {
        val createdPost = postService.createPost(post1);
        assertEquals(createdPost.getMessage(),post1.getMessage());
        assertEquals(createdPost.getUser(),post1.getUser());
        assertNotNull(createdPost.getId());
    }

    @Test
    void Test002_PostServiceWhenReceivesValidPostIdShouldReturnPost() {
        val postID = postService.createPost(post2).getId();
        val post = postService.getPostById(postID);
        assertEquals(post.getMessage(),post2.getMessage());
        assertEquals(post.getUser(),post2.getUser());
        assertEquals(post.getId(),postID);
    }

    @Test
    void Test003_PostServiceWhenReceivesValidPostIdShouldDeletePost() {
        val postID = postService.createPost(post3).getId();
        postService.deletePost(postID);
        assertThrows(
                PostNotFoundException.class,
                () -> postService.getPostById(postID));
    }

    @Test
    void Test004_PostServiceWhenReceivesValidUserShouldReturnAllUsersPosts() {
        val postID1 = postService.createPost(post1).getId();
        val postID2 = postService.createPost(post2).getId();
        val postID3 = postService.createPost(post3).getId();
        val posts = postService.getAllPostsByUser("user");
        assertEquals(posts.size(),2);
        assertEquals(posts.get(0).getId(),postID1);
        assertEquals(posts.get(1).getId(),postID3);
    }



}
