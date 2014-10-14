package method;

import entity.Graph;
import entity.Vertex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    private Graph graph;
    public BFS() {

    }

    public void bfs(int sourceVertex){
        ArrayList<Integer> result = new ArrayList<Integer>();
        System.out.println();
        System.out.println(graph.getOrder());
        boolean[] visitedVertexes = new boolean[graph.getOrder()];
        Queue<Integer> queueVertexes = new LinkedList<Integer>();
        visitedVertexes[sourceVertex] = true;
        queueVertexes.add(sourceVertex);

        while (!queueVertexes.isEmpty()) {
            int peekVertex = queueVertexes.poll();
            result.add(peekVertex);
            for (Vertex vertex: graph.getVertexes(peekVertex)) {
                if (!visitedVertexes[vertex.getNumVertex()]) {
                    visitedVertexes[vertex.getNumVertex()] = true;
                    queueVertexes.add(vertex.getNumVertex());
                }
            }
        }



        for(Integer a: result){
            System.out.print(a + " ");
        }
    }

    public void getGraph(Graph graph){
        this.graph = graph;
    }
}
