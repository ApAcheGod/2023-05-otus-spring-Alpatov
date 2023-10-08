package ru.otus.spring.hw16.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw16.entity.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
