package ru.isakaev.service;

import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.util.Set;

public interface QuestionnaireStudent {

    Student getQuestionnaire(Student student, Set<Question> questionList);
}
