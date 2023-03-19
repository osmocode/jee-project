package dev.osmocode.codehub.utils.algorithm;

import java.util.HashMap;
import java.util.Map;


/**
 * Class representing a user vertex in the graph
 */
public class UserVertex {
    private final String label; //username
    private final Map<UserVertex, Integer> neighbors = new HashMap<>();

    public UserVertex(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVertex that = (UserVertex) o;
        return label.equals(that.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    public String getLabel() {
        return label;
    }

    public Map<UserVertex, Integer> getNeighbors() {
        return neighbors;
    }

    public void updateScore(UserVertex vertex, int score) {
        neighbors.put(vertex, score);
    }

    public void removeNeighbor(UserVertex vertex) {
        if (!neighbors.containsKey(vertex)) {
            throw new IllegalStateException("Vertices are not connected");
        }
        neighbors.remove(vertex);
    }
}
