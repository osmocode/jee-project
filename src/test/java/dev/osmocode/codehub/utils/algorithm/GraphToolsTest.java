package dev.osmocode.codehub.utils.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class GraphToolsTest {

    @Test
    public void distanceByUser() {
        UserOrientedGraph graph = new UserOrientedGraph();
        UserVertex forax = new UserVertex("R.Forax");
        UserVertex carayol = new UserVertex("A.Carayol");
        UserVertex pivoteau = new UserVertex("C.Pivoteau");
        UserVertex curee = new UserVertex("O.Curee");
        var arcs = List.of(
                new Arc(carayol, forax, 5),
                new Arc(curee, forax, 5),
                new Arc(pivoteau, forax, 5),
                new Arc(forax, carayol, 5),
                new Arc(pivoteau, carayol, 5),
                new Arc(curee, pivoteau, 5),
                new Arc(carayol, pivoteau, 5)
        );
        graph.addArcs(arcs);
        Map<UserVertex, Integer> dijkstra = GraphTools.distanceByUser(graph, curee);
        String collect = dijkstra.entrySet()
                .stream()
                .map(entry -> entry.getKey().getLabel() + ": " + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
        System.out.println(collect);
    }

    @Test
    public void orientedPathsByUser() {
        UserOrientedGraph graph = new UserOrientedGraph();
        UserVertex forax = new UserVertex("R.Forax");
        UserVertex carayol = new UserVertex("A.Carayol");
        UserVertex pivoteau = new UserVertex("C.Pivoteau");
        UserVertex curee = new UserVertex("O.Curee");
        UserVertex u1 = new UserVertex("u1");
        UserVertex u2 = new UserVertex("u2");
        var arcs = List.of(
                new Arc(carayol, forax, 3),
                new Arc(curee, forax, 5),
                new Arc(pivoteau, forax, 5),
                new Arc(forax, carayol, 0),
                new Arc(pivoteau, carayol, 4),
                new Arc(curee, pivoteau, 2),
                new Arc(carayol, pivoteau, 1),
                new Arc(u1, u2, 1)
        );
        graph.addArcs(arcs);
        Map<UserVertex, List<OrientedPath>> orientedPaths = GraphTools.orientedPathsByUser(graph, curee);
        System.out.println(GraphTools.pathsToString(orientedPaths));
    }
}