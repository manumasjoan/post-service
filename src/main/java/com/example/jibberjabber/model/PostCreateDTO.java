package com.example.jibberjabber.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {

    private String text;

    private String user;
}
