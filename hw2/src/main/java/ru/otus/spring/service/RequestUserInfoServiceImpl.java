package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.io.OutputService;

@Service
@RequiredArgsConstructor
public class RequestUserInfoServiceImpl implements RequestUserInfoService{

    private static final String REQUEST_USER_DATA_MESSAGE = "Please, Enter your name and surname";

    private final OutputService iOServiceStreams;

    @Override
    public void requestUserInfo() {
        iOServiceStreams.outputString(REQUEST_USER_DATA_MESSAGE);
    }
}
