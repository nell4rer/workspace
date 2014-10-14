package method;

import entity.Graph;
import entity.Vertex;

import java.util.*;

public class DFS {

    private Graph graph;
    // map посещаемых вершин
    static Map<Vertex, Mark> visitedMap = new LinkedHashMap<Vertex, Mark>();
    private int counter;

    public DFS() {
    }

    public void dfs(Vertex vertex) {
        if (visitedMap.containsKey(vertex)) return;

        // добовляем время входа в вершину - pre
        visitedMap.put(vertex, new Mark(counter++, -1));

        for (Vertex v : vertex.getIncidentVertices()) {
            if (visitedMap.containsKey(v)) continue;
            dfs(v);
        }

        // время выхода - post
        Mark m = visitedMap.get(vertex);
        m.post = counter++;
    }

    public void print(){
        visitedMap.clear();
        counter = 1;


        // сортируем вершины
        Set<Vertex> set = new TreeSet<Vertex>();
        for (Vertex vertex : graph.getVertices()) {
            set.add(vertex);
        }

        for (Vertex v : set) {
            dfs(v);
        }

        System.out.println("\nDFS");
        for (Map.Entry<Vertex, Mark> entry : visitedMap.entrySet()) {
            Mark m = entry.getValue();
            System.out.format("%1$s : (%2$d, %3$d)\n", entry.getKey(), m.pre, m.post);
        }
    }

    public void getGraph(Graph graph){
        this.graph = graph;
    }

    static class Mark {
        public int pre;
        public int post;

        public Mark(int pre, int post) {
            this.pre  = pre;
            this.post = post;
        }
    }
}
