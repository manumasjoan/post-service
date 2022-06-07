package com.example.jibberjabber.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {

    private String message;

    private String user;
}
