package entity;

//import java.awt.*;

import java.util.*;

public class Graph {

    private Set<Vertex> vertices;

    public Graph() {
        this.vertices = new HashSet<Vertex>();
    }

    public Vertex addVertex (int name) {
        Vertex vertex = new Vertex(name);
        vertices.add(vertex);
        return vertex;
    }

    public void deleteEdge (Vertex from, Vertex to) {
        from.disconnectVertex(to);
        to.disconnectVertex(from);
    }

    public void setEdge (Vertex from, Vertex to) {
        from.connectVertex(to);
        to.connectVertex(from);
    }

    public Set<Vertex> getVertexes(int num){
        Set<Vertex> res  = new TreeSet<Vertex>();

        for(Vertex vertex: getVertices()){
            if(vertex.getNumVertex() == num){
                res.addAll(vertex.getIncidentVertices());
            }
        }

        return res;
    }

    public void deleteVertex (Vertex vertex) {
        Set<Vertex> toDelete = new HashSet<Vertex>();
        for (Vertex incidentVertex : vertex.getIncidentVertices()) {
            toDelete.add(incidentVertex);
        }
        for (Vertex incidentVertex : toDelete) {
            deleteEdge(vertex, incidentVertex);
        }
        vertices.remove(vertex);
    }

    public int getOrder () {
        return vertices.size();
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }


}