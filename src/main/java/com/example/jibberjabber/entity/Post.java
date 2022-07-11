package com.example.jibberjabber.entity;

import com.example.jibberjabber.model.PostDTO;
import lombok.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotEmpty
    @Size(max = 60)
    private String message;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull
    private List<Post> responses;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

    public PostDTO toDTO() {
        return PostDTO.builder()
                .id(id)
                .message(message)
                .user(user.toDTO())
                .responses(responses.stream().map(Post::toDTO).collect(Collectors.toList()))
                .build();
    }
}
