package dev.osmocode.codehub.utils.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserOrientedGraphTest {

    @Test
    public void addArc() {
        UserOrientedGraph g = new UserOrientedGraph();
        UserVertex v0 = new UserVertex("0");
        UserVertex v1 = new UserVertex("1");
        UserVertex v2 = new UserVertex("2");
        UserVertex v3 = new UserVertex("3");

        g.addArc(v2, v3);
        assertTrue(g.getVertices().get(v2.getLabel()).getNeighbors().containsKey(v3));

        g.addArc(v1, v1);
        assertTrue(g.getVertices().get(v1.getLabel()).getNeighbors().containsKey(v1));
        assertTrue(g.getVertices().get(v2.getLabel()).getNeighbors().containsKey(v3));

        g.addArc(v0, v3);
        assertTrue(g.getVertices().get(v0.getLabel()).getNeighbors().containsKey(v3));
        assertTrue(g.getVertices().get(v2.getLabel()).getNeighbors().containsKey(v3));
        assertTrue(g.getVertices().get(v1.getLabel()).getNeighbors().containsKey(v1));

        g.addArc(v0, v3);
        assertTrue(g.getVertices().get(v0.getLabel()).getNeighbors().containsKey(v3));
        assertTrue(g.getVertices().get(v2.getLabel()).getNeighbors().containsKey(v3));
        assertTrue(g.getVertices().get(v1.getLabel()).getNeighbors().containsKey(v1));
    }

    @Test
    public void addArcs() {
        UserOrientedGraph g = new UserOrientedGraph();
        UserVertex v0 = new UserVertex("0");
        UserVertex v1 = new UserVertex("1");
        UserVertex v2 = new UserVertex("2");
        UserVertex v3 = new UserVertex("3");

        List<Arc> arcs = List.of(new Arc(v2, v3, 0), new Arc(v1, v1, 0), new Arc(v0, v3, 0));
        g.addArcs(arcs);

        assertTrue(g.getVertices().get(v0.getLabel()).getNeighbors().containsKey(v3));
        assertTrue(g.getVertices().get(v2.getLabel()).getNeighbors().containsKey(v3));
        assertTrue(g.getVertices().get(v1.getLabel()).getNeighbors().containsKey(v1));
    }

    @Test
    public void updateScoreArc() {
        UserOrientedGraph g = new UserOrientedGraph();
        UserVertex v0 = new UserVertex("0");
        UserVertex v1 = new UserVertex("1");
        UserVertex v2 = new UserVertex("2");
        UserVertex v3 = new UserVertex("3");

        List<Arc> arcs = List.of(new Arc(v2, v3, 0), new Arc(v1, v1, 0), new Arc(v0, v3, 0));
        g.addArcs(arcs);

        g.updateScoreArc(v2, v3, 4);
        assertEquals(4, g.getVertices().get(v2.getLabel()).getNeighbors().get(v3));

        g.updateScoreArc(v1, v1, 2);
        assertEquals(2, g.getVertices().get(v1.getLabel()).getNeighbors().get(v1));

        // update a non-existing arcs create it
        g.updateScoreArc(v3, v0, 3);
        assertEquals(3, g.getVertices().get(v3.getLabel()).getNeighbors().get(v0));

        //update a non-existing vertex create it
        UserVertex notInGraph = new UserVertex("notInGraph");
        g.updateScoreArc(notInGraph, v2, 3);
        assertEquals(3, g.getVertices().get(notInGraph.getLabel()).getNeighbors().get(v2));

    }

    @Test
    public void updateScoreArcs() {
        UserOrientedGraph g = new UserOrientedGraph();
        UserVertex v0 = new UserVertex("0");
        UserVertex v1 = new UserVertex("1");
        UserVertex v2 = new UserVertex("2");
        UserVertex v3 = new UserVertex("3");
        UserVertex notInGraph = new UserVertex("notInGraph");

        List<Arc> arcs = List.of(new Arc(v2, v3, 0), new Arc(v1, v1, 0), new Arc(v0, v3, 0));
        List<Arc> modifs = List.of(new Arc(v2, v3, 4), new Arc(v1, v1, 2), new Arc(v3, v0, 3), new Arc(notInGraph, v2, 3));

        g.addArcs(arcs);
        g.updateScoreArcs(modifs);

        assertEquals(4, g.getVertices().get(v2.getLabel()).getNeighbors().get(v3));
        assertEquals(2, g.getVertices().get(v1.getLabel()).getNeighbors().get(v1));
        assertEquals(3, g.getVertices().get(v3.getLabel()).getNeighbors().get(v0));
        assertEquals(0, g.getVertices().get(v0.getLabel()).getNeighbors().get(v3));
        assertEquals(3, g.getVertices().get(notInGraph.getLabel()).getNeighbors().get(v2));
    }

    @Test
    public void scoreArc() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        UserVertex v3 = new UserVertex("v3");
        UserVertex v4 = new UserVertex("v4");
        UserVertex v5 = new UserVertex("v5");
        UserVertex v6 = new UserVertex("v6");

        List<Arc> arcs = List.of(
                new Arc(v0, v2, 8),
                new Arc(v1, v4, 5),
                new Arc(v5, v4, 7),
                new Arc(v1, v2, 3),
                new Arc(v1, v1, 3),
                new Arc(v2, v3, 4),
                new Arc(v2, v6, 6)
        );
        g.addArcs(arcs);

        assertEquals(3, g.scoreArc(v1, v1));
        assertEquals(8, g.scoreArc(v0, v2));
        assertThrows(IllegalArgumentException.class, () -> g.scoreArc(v4, v4));
    }

    @Test
    public void addVertexWithLabel() {
        UserOrientedGraph g = new UserOrientedGraph();

        g.addVertex("0");
        assertTrue(g.getVertices().containsKey("0"));
        assertEquals("0", g.getVertices().get("0").getLabel());
        assertEquals(0, g.getVertices().get("0").getNeighbors().size());

        g.addVertex("1");
        assertTrue(g.getVertices().containsKey("1"));
        assertEquals("1", g.getVertices().get("1").getLabel());
        assertEquals(0, g.getVertices().get("1").getNeighbors().size());

        assertEquals(2, g.getVertices().size());
    }

    @Test
    public void addVertexWithVertexObject() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("0");
        UserVertex v1 = new UserVertex("1");

        g.addVertex(v0);
        assertTrue(g.getVertices().containsKey("0"));
        assertEquals("0", g.getVertices().get(v0.getLabel()).getLabel());
        assertEquals(0, g.getVertices().get(v0.getLabel()).getNeighbors().size());

        g.addVertex(v1);
        assertTrue(g.getVertices().containsKey("1"));
        assertEquals("1", g.getVertices().get(v1.getLabel()).getLabel());
        assertEquals(0, g.getVertices().get(v1.getLabel()).getNeighbors().size());

        assertEquals(2, g.getVertices().size());

    }

    @Test
    public void arcs() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0)
        );
        g.addArcs(arcs);
        assertTrue(arcs.containsAll(g.arcs()));
        g.addArc(v0, v1);
        assertTrue(g.arcs().containsAll(arcs));
        assertTrue(g.arcs().contains(new Arc(v0, v1, 0)));
    }

    @Test
    public void boucles() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0),
                new Arc(v0, v0, 0)
        );
        g.addArcs(arcs);
        assertTrue(g.arcs().containsAll(arcs));
        assertTrue(g.boucles().contains(new Arc(v1, v1, 0)));
        assertTrue(g.boucles().contains(new Arc(v0, v0, 0)));
    }

    @Test
    public void noBouclesByDefault() {
        UserOrientedGraph g = new UserOrientedGraph();
        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);

        assertEquals(0, g.boucles().size());
    }

    @Test
    public void containsArcs() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        UserVertex notInGraph = new UserVertex("notInGraph");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0)
        );

        g.addArcs(arcs);
        assertTrue(g.containsArc(v1, v1));
        assertTrue(g.containsArc(v0, v2));
        assertTrue(g.containsArc(v2, v1));
        assertFalse(g.containsVertex(notInGraph));

        UserOrientedGraph g2 = new UserOrientedGraph();
        assertFalse(g2.containsVertex(notInGraph));
        assertFalse(g2.containsVertex(v0));
        assertFalse(g2.containsVertex(v1));
    }


    @Test
    public void containsVertex() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        UserVertex notInGraph = new UserVertex("notInGraph");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0)
        );

        g.addArcs(arcs);
        assertTrue(g.containsVertex(v0));
        assertTrue(g.containsVertex("v0"));
        assertTrue(g.containsVertex(v1));
        assertTrue(g.containsVertex("v1"));
        assertTrue(g.containsVertex(v2));
        assertTrue(g.containsVertex("v2"));
        assertFalse(g.containsVertex(notInGraph));
        assertFalse(g.containsVertex("notInGraph"));
    }

    @Test
    public void degree() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        UserVertex notInGraph = new UserVertex("notInGraph");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0),
                new Arc(v2, v0, 0)
        );

        g.addArcs(arcs);
        assertEquals(1, g.degree(v1));
        assertEquals(1, g.degree(v0));
        assertEquals(2, g.degree(v2));
        assertThrows(NullPointerException.class, () -> g.degree(notInGraph));
    }


    @Test
    public void arcsNumber() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0)
        );

        g.addArcs(arcs);
        assertEquals(3, g.arcsNumber());

        UserOrientedGraph g2 = new UserOrientedGraph();
        assertEquals(0, g2.arcsNumber());
    }


    @Test
    public void bouclesNumber() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0),
                new Arc(v0, v0, 0)
        );

        g.addArcs(arcs);
        assertEquals(2, g.bouclesNumber());

        UserOrientedGraph g2 = new UserOrientedGraph();
        assertEquals(0, g2.arcsNumber());
    }


    @Test
    public void verticesNumber() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0),
                new Arc(v0, v0, 0)
        );

        g.addArcs(arcs);
        assertEquals(3, g.verticesNumber());

        UserOrientedGraph g2 = new UserOrientedGraph();
        assertEquals(0, g2.verticesNumber());
    }

    @Test
    public void removeArc() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0)
        );

        g.addArcs(arcs);
        assertTrue(arcs.containsAll(g.arcs()));

        g.removeArc(v0, v2);
        assertFalse(g.arcs().containsAll(arcs));
        g.removeArc(v1, v1);
        g.removeArc(v2, v1);
        assertEquals(0, g.arcsNumber());
        assertEquals(0, g.bouclesNumber());
        assertEquals(3, g.verticesNumber());

    }

    @Test
    public void removeArcs() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0)
        );

        g.addArcs(arcs);
        assertTrue(arcs.containsAll(g.arcs()));

        g.removeArcs(arcs);
        assertEquals(0, g.arcsNumber());
        assertEquals(0, g.bouclesNumber());
        assertEquals(3, g.verticesNumber());
    }

    @Test
    public void removeVertex() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0),
                new Arc(v2, v0, 0)

        );

        g.addArcs(arcs);
        assertTrue(arcs.containsAll(g.arcs()));

        g.removeVertex(v0);
        assertFalse(g.vertices().contains(v0));
        assertFalse(g.getVertices().get(v2.getLabel()).getNeighbors().containsKey(v0));
        assertEquals(2, g.arcsNumber());
        assertEquals(1, g.bouclesNumber());
    }

    @Test
    public void removeVertices() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        List<Arc> arcs = List.of(
                new Arc(v1, v1, 0),
                new Arc(v0, v2, 0),
                new Arc(v2, v1, 0),
                new Arc(v2, v0, 0)

        );

        g.addArcs(arcs);
        assertTrue(arcs.containsAll(g.arcs()));

        g.removeVertices(List.of(v0, v1, v2));
        assertFalse(g.vertices().contains(v0));
        assertEquals(0, g.verticesNumber());
    }

    @Test
    public void vertices() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        UserVertex v3 = new UserVertex("v3");
        UserVertex v4 = new UserVertex("v4");
        UserVertex v5 = new UserVertex("v5");
        UserVertex v6 = new UserVertex("v6");

        List<Arc> arcs = List.of(
                new Arc(v0, v2, 8),
                new Arc(v1, v4, 5),
                new Arc(v5, v4, 7),
                new Arc(v1, v2, 3),
                new Arc(v1, v1, 3),
                new Arc(v2, v3, 4),
                new Arc(v2, v6, 6)
        );

        g.addArcs(arcs);
        assertTrue(g.arcs().containsAll(arcs));
        assertTrue(arcs.containsAll(g.arcs()));
        assertEquals(7, g.vertices().size());

        var vertices = List.of(v0, v1, v2, v3, v4, v5, v6);
        assertTrue(vertices.containsAll(g.vertices()));
    }

    @Test
    public void inducedSubgraph() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        UserVertex v3 = new UserVertex("v3");
        UserVertex v4 = new UserVertex("v4");
        UserVertex v5 = new UserVertex("v5");
        UserVertex v6 = new UserVertex("v6");

        List<Arc> arcs = List.of(
                new Arc(v0, v2, 8),
                new Arc(v1, v4, 5),
                new Arc(v5, v4, 7),
                new Arc(v1, v2, 3),
                new Arc(v1, v1, 3),
                new Arc(v2, v3, 4),
                new Arc(v2, v6, 6)
        );
        g.addArcs(arcs);
        assertTrue(g.arcs().containsAll(arcs));
        assertTrue(arcs.containsAll(g.arcs()));

        UserOrientedGraph h = g.inducedSubgraph(List.of(v0, v1, v2, v3));

        assertEquals(4, h.verticesNumber());
        assertEquals(4, h.arcsNumber());
        assertEquals(1, h.bouclesNumber());

        assertEquals(1, h.neighbor(v0).size());
        assertTrue(h.neighbor(v0).contains(v2));

        assertEquals(2, h.neighbor(v1).size());
        assertTrue(h.neighbor(v1).contains(v1));
        assertTrue(h.neighbor(v1).contains(v2));

        assertEquals(1, h.neighbor(v2).size());
        assertTrue(h.neighbor(v2).contains(v3));

        assertEquals(0, h.neighbor(v3).size());
    }

    @Test
    public void neighbor() {
        UserOrientedGraph g = new UserOrientedGraph();

        UserVertex v0 = new UserVertex("v0");
        UserVertex v1 = new UserVertex("v1");
        UserVertex v2 = new UserVertex("v2");
        UserVertex v3 = new UserVertex("v3");
        UserVertex v4 = new UserVertex("v4");
        UserVertex v5 = new UserVertex("v5");
        UserVertex v6 = new UserVertex("v6");

        List<Arc> arcs = List.of(
                new Arc(v0, v2, 8),
                new Arc(v1, v4, 5),
                new Arc(v5, v4, 7),
                new Arc(v1, v2, 3),
                new Arc(v1, v1, 3),
                new Arc(v2, v3, 4),
                new Arc(v2, v6, 6)
        );

        g.addArcs(arcs);
        assertTrue(g.arcs().containsAll(arcs));
        assertTrue(arcs.containsAll(g.arcs()));
        assertEquals(7, g.vertices().size());

        var neighborsV1 = List.of(v1, v2, v4);

        assertTrue(neighborsV1.containsAll(g.neighbor(v1)));
        assertTrue(g.neighbor(v1).containsAll(neighborsV1));

        var neighborsV2 = List.of(v3, v6);
        assertTrue(neighborsV2.containsAll(g.neighbor(v2)));
        assertTrue(g.neighbor(v2).containsAll(neighborsV2));

        assertTrue(g.neighbor(v4).isEmpty());

    }

}