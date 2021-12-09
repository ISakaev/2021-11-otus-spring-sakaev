package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {"location=ru","barrier=1"})
class TestServiceImplTest {

    @Value( "${location}" )
    private String location;

    @Value("${barrier}")
    private Integer passingBarrier;

    private Student student;

    private Set<Question> questionSet;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ReaderService readerService;

    @MockBean
    private MessageSource source;


    @BeforeEach
    void setUp() {
        student = new Student("Ilnur", "Sakaev", 3);
        questionSet = Set.of(
                new Question("One plus one",
                        new String[]{"one","two","three"}, "two"),
                new Question("One plus two",
                        new String[]{"one","two","three"}, "three")
        );
    }

    @Test
    void testStudent_void_success() throws NoSuchFieldException, IllegalAccessException {

        when(questionService.loadQuestions()).thenReturn(questionSet);

        when(studentService.getStudent()).thenReturn(student);

        String two = "two";
        String exit = "exit";
        when(readerService.readFromConsole()).thenReturn(two, two, exit);

        when(source.getMessage("text.attempt.exist", null, new Locale(location)))
                .thenReturn("The student %s %s your have %s attempts %s");
        when(source.getMessage("text.test.finish.pass", null, new Locale(location)))
                .thenReturn("Eee you pass test and have %s right answer! %s");
        when(source.getMessage("text.test.exit", null, new Locale(location)))
                .thenReturn("Enter \"EXIT\" if want to exit test or any line for test another student");
        when(source.getMessage("text.test.check", null, new Locale(location)))
                .thenReturn("EXIT");

        TestService test = new TestServiceImpl(source, studentService, questionService, readerService);

        //        location
        Field location = test.getClass().getDeclaredField("location");
        location.setAccessible(true);
        location.set(test, this.location);
        //        passingBarrier
        Field passingBarrier = test.getClass().getDeclaredField("passingBarrier");
        passingBarrier.setAccessible(true);
        passingBarrier.set(test, this.passingBarrier);

        test.testStudent();
        Assertions.assertThat(student.getIsTestComplete()).isEqualTo(true);
    }

    @Test
    void testStudent_void_success_without_availableAttempts() throws NoSuchFieldException, IllegalAccessException {
        student.setAvailableAttempts(0);
        when(studentService.getStudent()).thenReturn(student);

        when(source.getMessage("text.attempt.empty", null, new Locale(location)))
                .thenReturn("The Student %s %s has not available attempt %s");

        TestService test = new TestServiceImpl(source, studentService, questionService, readerService);
        Field location = test.getClass().getDeclaredField("location");
        location.setAccessible(true);
        location.set(test, this.location);

        test.testStudent();
    }

    @Test
    void testStudent_void_success_with_TestComplete() throws NoSuchFieldException, IllegalAccessException {
        student.setIsTestComplete(true);
        when(studentService.getStudent()).thenReturn(student);

        when(source.getMessage("text.test.pass", null, new Locale(location)))
                .thenReturn("Student %s %s successfully pass test%s");

        TestService test = new TestServiceImpl(source, studentService, questionService, readerService);
        Field location = test.getClass().getDeclaredField("location");
        location.setAccessible(true);
        location.set(test, this.location);

        test.testStudent();
    }

    @Test
    void testStudent_void_success_with_fail_test() throws NoSuchFieldException, IllegalAccessException {

        when(questionService.loadQuestions()).thenReturn(questionSet);

        when(studentService.getStudent()).thenReturn(student);

        String two = "one";
        String exit = "exit";
        when(readerService.readFromConsole()).thenReturn(two, two, "no", exit);

        when(source.getMessage("text.attempt.exist", null, new Locale(location)))
                .thenReturn("The student %s %s your have %s attempts %s");
        when(source.getMessage("text.test.finish.fail", null, new Locale(location)))
                .thenReturn("You fail test and have %s right answer, and have %s attempts %s");

        when(source.getMessage("text.test.finish.retry", null, new Locale(location)))
                .thenReturn("If you want try again enter RETRY");

        when(source.getMessage("text.test.finish.check", null, new Locale(location)))
                .thenReturn("RETRY");
        when(source.getMessage("text.test.exit", null, new Locale(location)))
                .thenReturn("Enter \"EXIT\" if want to exit test or any line for test another student");
        when(source.getMessage("text.test.check", null, new Locale(location)))
                .thenReturn("EXIT");

        TestService test = new TestServiceImpl(source, studentService, questionService, readerService);

        //        location
        Field location = test.getClass().getDeclaredField("location");
        location.setAccessible(true);
        location.set(test, this.location);
        //        passingBarrier
        Field passingBarrier = test.getClass().getDeclaredField("passingBarrier");
        passingBarrier.setAccessible(true);
        passingBarrier.set(test, this.passingBarrier);

        test.testStudent();
        Assertions.assertThat(student.getIsTestComplete()).isEqualTo(false);
    }
}