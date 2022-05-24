package com.example.jibberjabber.repository;

import com.example.jibberjabber.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    Page<Post> findAllByUser(String user, Pageable pageable);


}
