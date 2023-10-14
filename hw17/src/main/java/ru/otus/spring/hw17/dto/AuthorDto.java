package ru.otus.spring.hw17.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private UUID id;

    private String name;

    private String lastName;
}
