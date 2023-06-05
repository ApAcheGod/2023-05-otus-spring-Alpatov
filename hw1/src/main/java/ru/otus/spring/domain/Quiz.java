package ru.otus.spring.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Quiz {

    private String question;

    private String answer;
}
