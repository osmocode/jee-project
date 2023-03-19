package dev.osmocode.codehub.utils.sorter;

import dev.osmocode.codehub.entity.Question;
import dev.osmocode.codehub.entity.QuestionAnswer;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.utils.algorithm.GraphTools;
import dev.osmocode.codehub.utils.algorithm.OrientedPath;
import dev.osmocode.codehub.utils.algorithm.UserOrientedGraph;
import dev.osmocode.codehub.utils.algorithm.UserVertex;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Component
public class UgeAnswerSorter implements AnswerSorter {

    private final UserOrientedGraph graph;

    public UgeAnswerSorter(UserOrientedGraph graph) {
        this.graph = graph;
    }

    private int compute(QuestionAnswer questionAnswer, Map<UserVertex, List<OrientedPath>> paths) {
        //TODO
        return 0;
    }

    @Override
    public List<QuestionAnswer> sortAnswers(Question question, User visitor) {
        Map<UserVertex, List<OrientedPath>> orientedPathsByUser =
                GraphTools.orientedPathsByUser(
                        graph,
                        new UserVertex(visitor.getUsername())
                );
        ToIntFunction<QuestionAnswer> computeScore = (answer) -> compute(answer, orientedPathsByUser);
        return question.getAnswers().stream().sorted(
                Comparator.comparingInt(computeScore)
        ).collect(Collectors.toList());
    }
}
