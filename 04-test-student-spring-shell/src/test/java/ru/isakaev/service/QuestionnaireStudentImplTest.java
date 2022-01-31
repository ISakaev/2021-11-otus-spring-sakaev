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

import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
@TestPropertySource(properties = {"barrier=1"})
class QuestionnaireStudentImplTest {

    @MockBean
    private ReaderService readerService;

    @MockBean
    private MessageSourceService source;

    @Value("${barrier}")
    private Integer passingBarrier;

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
    void getQuestionnaire_student_success() {

        String two = "two";
        String exit = "exit";
        when(readerService.readFromConsole()).thenReturn(two, two, exit);

        when(source.getMessage("text.test.finish.pass"))
                .thenReturn("Eee you pass test and have %s right answer! %s");

        QuestionnaireStudent questionnaireStudent = new QuestionnaireStudentImpl(passingBarrier, source, readerService);
        Student studentAfterTest = questionnaireStudent.getQuestionnaire(student, questionSet);

        Assertions.assertThat(studentAfterTest.getIsTestComplete()).isEqualTo(true);
    }

    @Test
    void getQuestionnaire_student_failTest() {

        String two = "one";
        String exit = "exit";
        when(readerService.readFromConsole()).thenReturn(two, two, "no", exit);

        when(source.getMessage("text.test.finish.fail"))
                .thenReturn("You fail test and have %s right answer, and have %s attempts %s");

        when(source.getMessage("text.test.finish.retry"))
                .thenReturn("If you want try again enter RETRY");

        QuestionnaireStudent questionnaireStudent = new QuestionnaireStudentImpl(passingBarrier, source, readerService);
        Student studentAfterTest = questionnaireStudent.getQuestionnaire(student, questionSet);

        Assertions.assertThat(studentAfterTest.getAvailableAttempts()).isEqualTo(2);
    }

    @Test
    void getQuestionnaire_student_failTest_withTwoAttempt() {

        String one = "one";
        String exit = "exit";
        when(readerService.readFromConsole()).thenReturn(one, one, "RETRY", one, one, "no", exit);

        when(source.getMessage("text.test.finish.fail"))
                .thenReturn("You fail test and have %s right answer, and have %s attempts %s");

        when(source.getMessage("text.test.finish.retry"))
                .thenReturn("If you want try again enter RETRY");

        when(source.getMessage("text.test.finish.check"))
                .thenReturn("RETRY");

        QuestionnaireStudent questionnaireStudent = new QuestionnaireStudentImpl(passingBarrier, source, readerService);
        Student studentAfterTest = questionnaireStudent.getQuestionnaire(student, questionSet);

        Assertions.assertThat(studentAfterTest.getAvailableAttempts()).isEqualTo(1);
    }

}