package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.FileReaderCsv;

public class StudentTestingApplication {

    public static void main(String[] args) {
        var ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        FileReaderCsv fileReader = ctx.getBean("fileReader", FileReaderCsv.class);
        System.out.println(fileReader.readFile());
    }
}
