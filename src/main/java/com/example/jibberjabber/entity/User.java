package com.example.jibberjabber.entity;

import com.example.jibberjabber.model.PostDTO;
import com.example.jibberjabber.model.UserDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String name;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .id(id)
                .username(username)
                .name(name)
                .build();
    }

}