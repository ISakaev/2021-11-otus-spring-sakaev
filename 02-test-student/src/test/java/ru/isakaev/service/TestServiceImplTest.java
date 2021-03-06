package ru.isakaev.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;;
import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.Set;

import static org.mockito.Mockito.when;

class TestServiceImplTest {

    @Test
    void testStudent() throws IllegalAccessException, NoSuchFieldException {

        Student student = new Student("Ilnur", "Sakaev", 3);
        Set<Question> questionSet = Set.of(
                new Question("One plus one",
                        new String[]{"one","two","three"}, "two"),
                new Question("One plus two",
                        new String[]{"one","two","three"}, "three")
        );

        QuestionService questionService = Mockito.mock(QuestionService.class);

        when(questionService.loadQuestions()).thenReturn(questionSet);

        StudentService studentService = Mockito.mock(StudentService.class);
        when(studentService.getStudent()).thenReturn(student);


        TestServiceImpl test = new TestServiceImpl(studentService, questionService);

        InputStream stdin = System.in;

        Field passingBarrier = test.getClass().getDeclaredField("passingBarrier");
        passingBarrier.setAccessible(true);
        passingBarrier.set(test, 1);

        Field scanner = test.getClass().getDeclaredField("scanner");
        scanner.setAccessible(true);
        scanner.set(test, new Scanner(new ByteArrayInputStream("three\ntwo\nexit".getBytes())));

        try {
            test.testStudent();
        } finally {
            System.setIn(stdin);
        }
    }
}
