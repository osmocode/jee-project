package dev.osmocode.codehub.utils.sorter;

import dev.osmocode.codehub.entity.Question;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.utils.algorithm.GraphTools;
import dev.osmocode.codehub.utils.algorithm.UserOrientedGraph;
import dev.osmocode.codehub.utils.algorithm.UserVertex;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UgeQuestionSorter implements QuestionSorter {

    private final UserOrientedGraph graph;

    public UgeQuestionSorter(UserOrientedGraph graph) {
        this.graph = graph;
    }

    @Override
    public List<Question> sortQuestions(List<Question> toBeSorted, User visitor) {
        Map<UserVertex, Integer> distanceByUser = GraphTools.distanceByUser(graph, new UserVertex(visitor.getUsername()));
        return toBeSorted.stream().sorted(
                Comparator.comparingInt(question ->
                        distanceByUser.getOrDefault(new UserVertex(question.getInterviewer().getUsername()), Integer.MAX_VALUE))
        ).collect(Collectors.toList());
    }
}
