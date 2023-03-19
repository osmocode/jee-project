package dev.osmocode.codehub.utils.algorithm;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to manage the graph of social connections between users
 */
@Component
public class UserOrientedGraph {
    
    private final Object lock = new Object();

    private final Map<String, UserVertex> vertices = new HashMap<>();

    public Map<String, UserVertex> getVertices() {
        synchronized (lock) {
            return vertices;
        }
    }

    public void addArc(UserVertex source, UserVertex destination) {
        addArc(source, destination, 0);
    }

    public void addArc(UserVertex source, UserVertex destination, int score) {
        synchronized (lock) {
            if (containsArc(source, destination)) {
                return;
            }
            if (!containsVertex(source)) {
                addVertex(source);
            }
            if (!containsVertex(destination)) {
                addVertex(destination);
            }
            vertices.get(source.getLabel()).updateScore(destination, score);
        }
    }

    public void addArcs(Collection<Arc> arcs) {
        arcs.forEach(a -> addArc(a.source(), a.destination(), a.score()));
    }

    public void updateScoreArc(UserVertex source, UserVertex destination, int score) {
        synchronized (lock) {
            if (!containsVertex(source.getLabel())) {
                addVertex(source);
            }
            if (!containsVertex(destination.getLabel())) {
                addVertex(destination);
            }
            vertices.get(source.getLabel()).updateScore(destination, score);
        }
    }

    public void updateScoreArcs(Collection<Arc> arcs) {
        arcs.forEach(a -> updateScoreArc(a.source(), a.destination(), a.score()));
    }

    public int scoreArc(UserVertex source, UserVertex destination) {
        synchronized (lock) {
            if (!containsArc(source, destination)) {
                throw new IllegalArgumentException("Arc doesn't exist");
            }
            return vertices.get(source.getLabel()).getNeighbors().get(destination);
        }
    }

    public void addVertex(String label) {
        addVertex(new UserVertex(label));
    }

    public void addVertex(UserVertex vertex) {
        synchronized (lock) {
            if (containsVertex(vertex.getLabel())) {
                return;
            }
            vertices.put(vertex.getLabel(), vertex);
        }
    }

    public Collection<Arc> arcs() {
        synchronized (lock) {
            return vertices
                    .entrySet()
                    .stream()
                    .flatMap(entry -> entry.getValue()
                            .getNeighbors()
                            .entrySet()
                            .stream()
                            .map(neighbor -> new Arc(entry.getValue(), neighbor.getKey(), neighbor.getValue()))
                    ).collect(Collectors.toSet());
        }
    }

    public Collection<Arc> boucles() {
        return arcs()
                .stream()
                .filter(vertex -> vertex.source().equals(vertex.destination()))
                .collect(Collectors.toSet());
    }

    public boolean containsArc(UserVertex source, UserVertex destination) {
        synchronized (lock) {
            if (!containsVertex(source) || !containsVertex(destination)) {
                return false;
            }
            return vertices.get(source.getLabel()).getNeighbors().containsKey(destination);
        }
    }

    public boolean containsVertex(UserVertex vertex) {
        synchronized (lock) {
            return vertices.containsKey(vertex.getLabel());
        }
    }

    public boolean containsVertex(String label) {
        synchronized (lock) {
            return vertices.containsKey(label);
        }
    }

    public int degree(UserVertex vertex) {
        synchronized (lock) {
            return vertices.get(vertex.getLabel()).getNeighbors().size();
        }
    }

    public int arcsNumber() {
        return this.arcs().size();
    }

    public int bouclesNumber() {
        return boucles().size();
    }

    public int verticesNumber() {
        synchronized (lock) {
            return vertices.size();
        }
    }

    public void removeArc(UserVertex source, UserVertex destination) {
        synchronized (lock) {
            vertices.get(source.getLabel()).getNeighbors().remove(destination);
        }
    }

    public void removeArcs(Collection<Arc> arcs) {
        arcs.forEach(arc -> removeArc(arc.source(), arc.destination()));
    }

    public void removeVertex(UserVertex toRemove) {
        synchronized (lock) {
            vertices.values().forEach(v -> v.getNeighbors().remove(toRemove));
            vertices.remove(toRemove.getLabel());
        }
    }

    public void removeVertices(Collection<UserVertex> vertices) {
        vertices.forEach(this::removeVertex);
    }

    public Collection<UserVertex> vertices() {
        synchronized (lock) {
            return vertices.values();
        }
    }

    public UserOrientedGraph inducedSubgraph(Collection<UserVertex> inducers) {
        UserOrientedGraph g = new UserOrientedGraph();
        inducers.forEach(i -> g.addVertex(i.getLabel()));
        arcs().forEach(arc -> {
            if (g.containsVertex(arc.source()) && g.containsVertex(arc.destination())) {
                g.addArc(arc.source(), arc.destination(), arc.score());
            }
        });
        return g;
    }

    public Collection<UserVertex> neighbor(UserVertex vertex) {
        synchronized (lock) {
            return vertices.get(vertex.getLabel()).getNeighbors().keySet();
        }
    }
}


