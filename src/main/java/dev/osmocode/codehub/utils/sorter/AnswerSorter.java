package dev.osmocode.codehub.utils.sorter;

import dev.osmocode.codehub.entity.Question;
import dev.osmocode.codehub.entity.QuestionAnswer;
import dev.osmocode.codehub.entity.User;

import java.util.List;

public interface AnswerSorter {
    
    List<QuestionAnswer> sortAnswers(Question question, User visitor);

}
