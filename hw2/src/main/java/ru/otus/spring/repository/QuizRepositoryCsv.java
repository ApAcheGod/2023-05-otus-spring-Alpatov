package ru.otus.spring.repository;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.domain.Quiz;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuizRepositoryCsv implements QuizRepository{

    private static final char SEMICOLON_SEPARATOR = ';';

    private final String path;

    public QuizRepositoryCsv(@Value("${questions.path}") String path) {
        this.path = path;
    }

    @Override
    public List<Quiz> getAll() {
        try (var inputStreamReader = new InputStreamReader(new ClassPathResource(path).getInputStream())) {
            CSVReader csvReader = getCsvReader(inputStreamReader);
            return getData(csvReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CSVReader getCsvReader(InputStreamReader inputStreamReader) {
        return new CSVReaderBuilder(inputStreamReader)
                .withSkipLines(1)
                .withCSVParser(getCsvParser())
                .build();
    }

    private CSVParser getCsvParser() {
        return new CSVParserBuilder()
                .withSeparator(SEMICOLON_SEPARATOR)
                .build();
    }

    private List<Quiz> getData(CSVReader csvReader) {
        List<Quiz> data = new ArrayList<>();
        try {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                data.add(Quiz.builder().question(line[0]).answer(line[1]).build());
            }
            return data;
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
