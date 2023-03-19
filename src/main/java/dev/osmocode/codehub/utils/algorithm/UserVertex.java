package dev.osmocode.codehub.utils.algorithm;

import java.util.HashMap;
import java.util.Map;


/**
 * Class representing a user vertex in the graph
 */
public class UserVertex {
    private final String label; //username
    private final Map<UserVertex, Integer> neighbors = new HashMap<>();
    
    private final Object lock = new Object();

    public UserVertex(String label) {
        synchronized (lock) {
            this.label = label;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVertex that = (UserVertex) o;
        synchronized (lock) {
            return label.equals(that.label);
        }
    }

    @Override
    public int hashCode() {
        synchronized (lock) {
            return label.hashCode();
        }
    }

    public String getLabel() {
        synchronized (lock) {
            return label;
        }
    }

    public Map<UserVertex, Integer> getNeighbors() {
        synchronized (lock) {
            return neighbors;
        }
    }

    public void updateScore(UserVertex vertex, int score) {
        synchronized (lock) {
            neighbors.put(vertex, score);
        }
    }

    public void removeNeighbor(UserVertex vertex) {
        synchronized (lock) {
            if (!neighbors.containsKey(vertex)) {
                throw new IllegalStateException("Vertices are not connected");
            }
            neighbors.remove(vertex);
        }
    }
}
