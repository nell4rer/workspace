package method;

import entity.Graph;
import entity.Vertex;

import java.util.*;

public class DFS {

    private String methodName;
    private Graph graph;

    Set<Vertex> set = new TreeSet<Vertex>();
    static Map<Vertex, Mark> visitedMap;
    int counter = 0;

    public DFS() {
        methodName = "DFS";
        visitedMap = new LinkedHashMap<Vertex, Mark>();
    }

    public void dfs(Vertex vertex) {
        if (visitedMap.containsKey(vertex)) return;

        visitedMap.put(vertex, new Mark(counter, -1));
        counter++;

        Set<Vertex> adjacentVertices = vertex.getIncidentVertices();

        for (Vertex v : adjacentVertices) {
            if (visitedMap.containsKey(v)) continue;
            dfs(v);
        }

        Mark m = visitedMap.get(vertex);
        m.post = counter++;
    }

    public void print(){
        set.clear();
        visitedMap.clear();
        counter = 1;

        for (Vertex vertex : graph.getVertices()) {
            set.add(vertex);
        }

        for (Vertex v : set) {
            dfs(v);
        }

        System.out.println(methodName);
        for (Map.Entry<Vertex, Mark> entry : visitedMap.entrySet()) {
            Mark m = entry.getValue();
            System.out.format("%1$s : (%2$d, %3$d)\n", entry.getKey(), m.pre, m.post);
        }
    }

    public void getGraph(Graph graph){
        this.graph = graph;
    }

    static class Mark {
        public Mark(int pre, int post) {
            this.pre  = pre;
            this.post = post;
        }
        public int pre;
        public int post;
    };
}
