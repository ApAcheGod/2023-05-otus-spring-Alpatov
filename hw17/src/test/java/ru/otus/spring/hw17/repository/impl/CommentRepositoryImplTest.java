package ru.otus.spring.hw17.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.hw17.repository.CommentRepository;
import ru.otus.spring.hw17.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
//@Import(CommentRepositoryImpl.class)
@DisplayName("Тест CommentRepository")
class CommentRepositoryImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Поиск по ID")
    public void findById() {
        UUID uuid = UUID.fromString("c094c253-1a81-4848-9ef5-3c41667514fb");
        String text = "Комментарий1";
        Optional<Comment> optionalComment = commentRepository.findById(uuid);
        Assertions.assertTrue(optionalComment.isPresent());
        Assertions.assertEquals(text, optionalComment.get().getComment());
    }

    @Test
    @DisplayName("Поиск всех")
    public void findAll() {
        List<Comment> comments = commentRepository.findAll();
        Assertions.assertEquals(21, comments.size());
    }

    @Test
    @DisplayName("Обновление записи")
    public void update() {
        UUID uuid = UUID.fromString("c094c253-1a81-4848-9ef5-3c41667514fb");
        String text = "Обновленный комментарий";
        Optional<Comment> optionalComment = commentRepository.findById(uuid);
        Assertions.assertTrue(optionalComment.isPresent());
        optionalComment.get().setComment("Обновленный комментарий");
        commentRepository.save(optionalComment.get());

        Optional<Comment> optionalComment1 = commentRepository.findById(uuid);

        Assertions.assertEquals(text, optionalComment1.get().getComment());
    }

    @Test
    @DisplayName("Удаление записи")
    public void delete() {
        UUID uuid = UUID.fromString("a6800f4c-a2c1-49fd-b2ff-66bfbe10f620");
        commentRepository.deleteById(uuid);

        Assertions.assertEquals(Optional.empty(), commentRepository.findById(uuid));
    }
}