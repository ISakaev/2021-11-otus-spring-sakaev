package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.lang.reflect.Field;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class TestServiceImplTest {

    @MockBean
    private QuestionnaireStudent questionnaireStudent;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ReaderService readerService;

    @MockBean
    private MessageSourceService source;

    private Student student;

    private Set<Question> questionSet;

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
    void testStudent_void_success(){

        when(questionService.loadQuestions()).thenReturn(questionSet);

        when(studentService.getStudent()).thenReturn(student);

        String two = "two";
        String exit = "exit";
        when(readerService.readFromConsole()).thenReturn(two, two, exit);

        when(source.getMessage("text.attempt.exist"))
                .thenReturn("The student %s %s your have %s attempts %s");
        when(source.getMessage("text.test.exit"))
                .thenReturn("Enter \"EXIT\" if want to exit test or any line for test another student");
        when(source.getMessage("text.test.check"))
                .thenReturn("EXIT");

        Student studentAfterTest = new Student("Ilnur", "Sakaev", 3);
        studentAfterTest.setIsTestComplete(true);

        when(questionnaireStudent.getQuestionnaire(student,questionSet)).thenReturn(studentAfterTest);

        TestService test = new TestServiceImpl(source, studentService, questionService, readerService, questionnaireStudent);

        test.testStudent();
        Assertions.assertThat(studentAfterTest.getIsTestComplete()).isEqualTo(true);
    }

    @Test
    void testStudent_void_success_without_availableAttempts() {
        student.setAvailableAttempts(0);
        when(studentService.getStudent()).thenReturn(student);

        when(source.getMessage("text.attempt.empty"))
                .thenReturn("The Student %s %s has not available attempt %s");

        TestService test = new TestServiceImpl(source, studentService, questionService, readerService, questionnaireStudent);

        test.testStudent();
    }

    @Test
    void testStudent_void_success_with_TestComplete() {
        student.setIsTestComplete(true);
        when(studentService.getStudent()).thenReturn(student);

        when(source.getMessage("text.test.pass"))
                .thenReturn("Student %s %s successfully pass test%s");

        TestService test = new TestServiceImpl(source, studentService, questionService, readerService, questionnaireStudent);

        test.testStudent();
    }
}