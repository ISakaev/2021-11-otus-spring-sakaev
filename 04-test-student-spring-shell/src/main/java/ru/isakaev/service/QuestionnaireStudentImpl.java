package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.util.Set;

@Service
@PropertySource("classpath:application.yml")
public class QuestionnaireStudentImpl implements QuestionnaireStudent {

    private final Integer passingBarrier;

    private final MessageSourceService source;

    private final ReaderService readerService;


    public QuestionnaireStudentImpl(@Value("${passing.barrier}") Integer passingBarrier, MessageSourceService source, ReaderService readerService) {
        this.passingBarrier = passingBarrier;
        this.source = source;
        this.readerService = readerService;
    }


    @Override
    public Student getQuestionnaire(Student student, Set<Question> questions) {

            int attCount = student.getAvailableAttempts();

            boolean oneMoreAttempt;
            do {
                oneMoreAttempt = false;
                int countOfRightAnswer = 0;
                for (Question q : questions) {
                    System.out.println(customizeQuestionForPrint(q));
                    if (readerService.readFromConsole().equalsIgnoreCase(q.getRightAnswer())) {
                        countOfRightAnswer++;
                    }
                }
                if (countOfRightAnswer >= passingBarrier) {
                    System.out.printf(source.getMessage("text.test.finish.pass"), countOfRightAnswer, System.lineSeparator());
                    student.setIsTestComplete(true);
                } else {
                    System.out.printf(source.getMessage("text.test.finish.fail"), countOfRightAnswer, (--attCount),  System.lineSeparator());
                    if (attCount > 0) {
                        System.out.println(source.getMessage("text.test.finish.retry"));
                        String checkYes = readerService.readFromConsole();
                        String yesWord = source.getMessage("text.test.finish.check");
                        if (checkYes.equalsIgnoreCase(yesWord)) {
                            oneMoreAttempt = true;
                        }
                    }
                }
                student.setAvailableAttempts(attCount);
            }while (oneMoreAttempt);

            return student;
    }

    private String customizeQuestionForPrint(Question question){

        StringBuilder builder = new StringBuilder();
        builder.append(source.getMessage("text.question")).append(" : ").append(question.getTextQuestion()).append('?');
        String[] answers = question.getAnswers();
        for (int i=0; i<answers.length; i++) {
            builder.append('\n');
            builder.append(answers[i].trim());
        }
        builder.append('\n');
        return builder.toString();
    }
}
