package com.example.jibberjabber.repository;

import com.example.jibberjabber.entity.Post;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebClient
@DataJpaTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    Post post1= Post.builder()
            .message("testing")
            .user("user")
            .build();

    Post post2 = Post.builder()
            .message("testing2")
            .user("user2")
            .build();

    Post post3= Post.builder()
            .message("test")
            .user("user")
            .build();

    @Test
    void Test001_PostRepositoryShouldSavePosts() {

        assertTrue(postRepository.findAll().isEmpty());

        assertNull(post1.getId());

        postRepository.save(post1);

        assertFalse(postRepository.findAll().isEmpty());

        List<Post> posts = postRepository.findAll();

        assertEquals(1, posts.size());

        val savedProject = posts.get(0);

        assertNotNull(savedProject.getId());
        assertEquals(post1.getMessage(), savedProject.getMessage());
        assertEquals(post1.getUser(), savedProject.getUser());
    }

    @Test
    void Test002_PostRepositoryShouldDeletePosts() {

        postRepository.save(post1);

        assertFalse(postRepository.findAll().isEmpty());

        postRepository.delete(post1);

        assertTrue(postRepository.findAll().isEmpty());
    }


}
