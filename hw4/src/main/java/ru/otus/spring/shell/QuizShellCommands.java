package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.QuizService;

@ShellComponent
@RequiredArgsConstructor
public class QuizShellCommands {

    private final QuizService quizService;

    @ShellMethod(value = "Start quiz", key = {"s", "start"})
    public void startQuiz() {
        quizService.startQuiz();
    }
}
