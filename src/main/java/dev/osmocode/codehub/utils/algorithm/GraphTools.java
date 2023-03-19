package dev.osmocode.codehub.utils.algorithm;

import java.util.*;
import java.util.stream.Collectors;

//https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
public class GraphTools {

    public static Map<UserVertex, Integer> distanceByUser(UserOrientedGraph graph, UserVertex source) {
        Map<UserVertex, Integer> dist = new HashMap<>();
        Set<UserVertex> q = new HashSet<>();
        graph.vertices().forEach(vertex -> {
            dist.put(vertex, Integer.MAX_VALUE);
            q.add(vertex);
        });
        dist.put(source, 0);
        while (!q.isEmpty()) {
            UserVertex u = q.stream()
                    .min(Comparator.comparingInt(dist::get))
                    .orElseThrow(() -> new IllegalStateException(""));
            q.remove(u);
            Set<UserVertex> neighbors = graph.neighbor(u).stream().filter(q::contains).collect(Collectors.toSet());
            for (var v : neighbors) {
                int alt = dist.get(u) + 1;
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                }
            }
        }
        return dist;
    }

    public static Map<UserVertex, List<OrientedPath>> orientedPathsByUser(UserOrientedGraph graph, UserVertex source) {
        Map<UserVertex, Integer> dist = new HashMap<>();
        Map<UserVertex, UserVertex> prev = new HashMap<>();
        Set<UserVertex> q = new HashSet<>();
        graph.vertices().forEach(vertex -> {
            dist.put(vertex, Integer.MAX_VALUE);
            prev.put(vertex, null);
            q.add(vertex);
        });
        dist.put(source, 0);
        while (!q.isEmpty()) {
            UserVertex u = q.stream()
                    .min(Comparator.comparingInt(dist::get))
                    .orElseThrow(() -> new IllegalStateException(""));
            q.remove(u);
            Set<UserVertex> neighbors = graph.neighbor(u).stream().filter(q::contains).collect(Collectors.toSet());
            for (var v : neighbors) {
                int alt = dist.get(u) + 1;
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                }
            }
        }
        return retrievePaths(graph, source, dist, prev);
    }

    public static List<OrientedPath> retrievePath(
            UserOrientedGraph graph,
            UserVertex source,
            UserVertex destination,
            Map<UserVertex, Integer> dist,
            Map<UserVertex, UserVertex> prev
    ) {
        List<OrientedPath> path = new ArrayList<>();
        UserVertex currentVertex = destination;
        while (!currentVertex.equals(source)) {
            OrientedPath orientedPath = new OrientedPath(
                    currentVertex,
                    dist.get(currentVertex),
                    graph.scoreArc(prev.get(currentVertex), currentVertex)
            );
            path.add(orientedPath);
            currentVertex = prev.get(currentVertex);
        }
        path.add(new OrientedPath(source, 0, 5));
        Collections.reverse(path);
        if (!path.get(0).vertex().equals(source)) {
            return null;
        }
        return path;
    }

    public static Map<UserVertex, List<OrientedPath>> retrievePaths(
            UserOrientedGraph graph,
            UserVertex source,
            Map<UserVertex, Integer> dist,
            Map<UserVertex, UserVertex> prev
    ) {
        Map<UserVertex, List<OrientedPath>> paths = new HashMap<>();
        dist
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != null)
                .filter(entry -> (entry.getValue() != Integer.MAX_VALUE && entry.getValue() >= 0))
                .forEach(entry -> {
                    var path = retrievePath(graph, source, entry.getKey(), dist, prev);
                    paths.put(entry.getKey(), path);
                });
        paths.put(source, List.of(new OrientedPath(source, 0, 5)));
        return paths;
    }

    public static String pathsToString(Map<UserVertex, List<OrientedPath>> orientedPaths) {
        return orientedPaths
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().getLabel() + ": " + entry.getValue().stream()
                        .map(orientedPath -> "(" + orientedPath.vertex().getLabel() + ", " + orientedPath.depth() + ", " + orientedPath.note() + ")")
                        .collect(Collectors.joining(", ", "{", "}"))
                ).collect(Collectors.joining(",\n", "[", "]"));
    }
}
