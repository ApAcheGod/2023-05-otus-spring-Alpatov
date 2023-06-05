package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/spring-test-context.xml")
class FileReaderCsvTest {

    private final FileReader fileReaderCsv;

    @Autowired
    public FileReaderCsvTest(FileReader fileReaderCsv) {
        this.fileReaderCsv = fileReaderCsv;
    }

    @Test
    @DisplayName("Проверка количества элементов")
    void readFile() {
        Assertions.assertEquals(5, fileReaderCsv.readFile().size());
    }
}