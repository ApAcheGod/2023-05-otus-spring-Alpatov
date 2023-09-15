package ru.otus.spring.hw12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw12.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
}
