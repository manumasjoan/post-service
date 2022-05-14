package com.example.jibberjabber.model;

import com.example.jibberjabber.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private UUID id;

    private String message;

    private List<PostDTO> responses;

    private LocalDateTime date;

    private String user;
}
