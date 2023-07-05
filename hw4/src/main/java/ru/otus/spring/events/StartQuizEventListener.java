package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.QuizService;

@Component
@RequiredArgsConstructor
public class StartQuizEventListener implements ApplicationListener<StartQuizEvent> {

    private final QuizService quizService;

    @Override
    public void onApplicationEvent(StartQuizEvent event) {
        quizService.startQuiz();
    }
}
