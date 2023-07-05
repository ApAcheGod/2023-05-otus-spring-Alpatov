package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class QuizShellCommands {

    @ShellMethod(value = "Start quiz", key = {"s", "start"})
    public void requestUserNane() {
    }
}
