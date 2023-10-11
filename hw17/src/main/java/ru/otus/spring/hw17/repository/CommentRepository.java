package ru.otus.spring.hw17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw17.entity.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
