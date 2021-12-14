package ru.isakaev.service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.util.HashSet;
import java.util.Set;

@Service
@PropertySource("classpath:application.yml")
public class TestServiceImpl implements TestService {

    private final MessageSourceService source;

    private final StudentService studentService;

    private final QuestionService questionService;

    private final ReaderService readerService;

    private final QuestionnaireStudent questionnaireStudent;

    private Set<Question> questions = new HashSet<>();

    private Student student;

    public TestServiceImpl(MessageSourceService source, StudentService studentService, QuestionService questionService,
                           ReaderService readerService, QuestionnaireStudent questionnaireStudent) {
        this.source = source;
        this.studentService = studentService;
        this.questionService = questionService;
        this.readerService = readerService;
        this.questionnaireStudent = questionnaireStudent;
    }

    @Override
    public void testStudent() {
        boolean testing = true;
        while(testing){

            student = studentService.getStudent();

            if (student.getIsTestComplete()){
                System.out.printf(source.getMessage("text.test.pass"), student.getFirstName(), student.getLastName(), System.lineSeparator());
                testing = false;
                continue;
            }

            if (!isStudentHasAttempts(student)){
                testing = false;
                continue;
            }
            checkQuestions();

            student = questionnaireStudent.getQuestionnaire(student, questions);

            System.out.println(source.getMessage("text.test.exit"));
            String checkExit = readerService.readFromConsole();
            if (checkExit.equalsIgnoreCase(source.getMessage("text.test.check"))){
                testing = false;
            }

            studentService.addStudent(student);
        }
    }

    private boolean isStudentHasAttempts(Student student) {
        int attCount = student.getAvailableAttempts();
        if(attCount < 1){
            System.out.printf(source.getMessage("text.attempt.empty"), student.getFirstName(), student.getLastName(), System.lineSeparator());
            return false;
        }
        System.out.printf(source.getMessage("text.attempt.exist"), student.getFirstName(), student.getLastName(), attCount, System.lineSeparator());
        return true;
    }

    private void checkQuestions() {
        if (questions.isEmpty()){
            questions = questionService.loadQuestions();
        }
    }

}
