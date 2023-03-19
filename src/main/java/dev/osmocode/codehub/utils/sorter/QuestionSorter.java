package dev.osmocode.codehub.utils.sorter;

import dev.osmocode.codehub.entity.Question;
import dev.osmocode.codehub.entity.User;

import java.util.List;

public interface QuestionSorter {

    List<Question> sortQuestions(List<Question> toBeSorted, User visitor);
}
