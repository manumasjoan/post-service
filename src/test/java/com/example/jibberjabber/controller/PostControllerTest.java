package com.example.jibberjabber.controller;

import com.example.jibberjabber.model.PostCreateDTO;
import com.example.jibberjabber.model.PostDTO;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PostControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/post";

    PostCreateDTO post1= PostCreateDTO.builder()
            .text("testing")
            .user("user")
            .build();
    PostCreateDTO post2= PostCreateDTO.builder()
            .text("test")
            .user("user2")
            .build();
    PostCreateDTO post3= PostCreateDTO.builder()
            .text("message")
            .user("user")
            .build();


    @Test
    void Test001_PostControllerWhenReceivesValidCreatePostDTOShouldReturnStatusCreated() {

        HttpEntity<PostCreateDTO> request = new HttpEntity<>(post1);

        val getResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, request, PostDTO.class);
        assertEquals(HttpStatus.CREATED, getResponse.getStatusCode());
    }

    @Test
    void Test002_PostControllerWhenReceivesInvalidCreatePostDTOShouldReturnBadRequest() {
        val post= PostCreateDTO.builder()
                .text("")
                .user("user")
                .build();

        HttpEntity<PostCreateDTO> request = new HttpEntity<>(post);

        val getResponse = restTemplate.exchange(baseUrl, HttpMethod.POST, request, PostDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, getResponse.getStatusCode());
    }

    /**@Test
    void Test003_PostControllerWhenReceivesValidUserShouldAllUsersPosts() {

        HttpEntity<PostCreateDTO> request1 = new HttpEntity<>(post1);
        HttpEntity<PostCreateDTO> request2 = new HttpEntity<>(post2);
        HttpEntity<PostCreateDTO> request3 = new HttpEntity<>(post3);

        restTemplate.exchange(baseUrl, HttpMethod.POST, request1, PostDTO.class);
        restTemplate.exchange(baseUrl, HttpMethod.POST, request2, PostDTO.class);
        restTemplate.exchange(baseUrl, HttpMethod.POST, request3, PostDTO.class);


        val getResponse = restTemplate.exchange(baseUrl+"/all/user", HttpMethod.GET, null, PostDTO[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(2, getResponse.getBody().length);

    }

    @Test
    void Test004_PostControllerWhenReceivesValidPostIDShouldDeletePost() {

        HttpEntity<PostCreateDTO> request1 = new HttpEntity<>(post1);
        val id= restTemplate.exchange(baseUrl, HttpMethod.POST, request1, PostDTO.class).getBody().getId();

        val getResponse1 = restTemplate.exchange(baseUrl+"/all/user", HttpMethod.GET, null, PostDTO[].class);
        assertEquals(1, getResponse1.getBody().length);

        val getResponse2 = restTemplate.exchange(baseUrl+"/"+id, HttpMethod.DELETE, null, void.class);

        val getResponse3 = restTemplate.exchange(baseUrl+"/all/user", HttpMethod.GET, null, PostDTO[].class);
        assertEquals(0, getResponse3.getBody().length);

    }**/

    @Test
    void Test005_PostControllerWhenReceivesValidPostIDShouldReturnPost() {

        HttpEntity<PostCreateDTO> request1 = new HttpEntity<>(post1);
        val id= restTemplate.exchange(baseUrl, HttpMethod.POST, request1, PostDTO.class).getBody().getId();

        val getResponse = restTemplate.exchange(baseUrl+"/"+id, HttpMethod.GET, null, PostDTO.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(id, getResponse.getBody().getId());
    }


}


