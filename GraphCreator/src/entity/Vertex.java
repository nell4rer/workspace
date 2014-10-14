package entity;

import java.util.HashSet;
import java.util.Set;

public class Vertex implements Comparable<Vertex> {

    private Set<Vertex> incidentVertices;
    private Integer numVertex;

    public Vertex(Integer numVertex) {
        this.numVertex = numVertex;
        this.incidentVertices = new HashSet<Vertex>();
    }

    public int getOrder () {
        return incidentVertices.size();
    }

    public void connectVertex (Vertex vertex) {
        incidentVertices.add(vertex);
    }

    public boolean disconnectVertex (Vertex vertex) {
        return incidentVertices.remove(vertex);
    }

    public boolean isConnect (Vertex vertex) {
        return incidentVertices.contains(vertex);
    }

    public Set<Vertex> getIncidentVertices() {
        return incidentVertices;
    }

    public String toString(){
        return "V" + numVertex;
    }

    public Integer getNumVertex(){
        return numVertex;
    }

    @Override
    public int compareTo(Vertex vertex) {
        if(this.getNumVertex() > vertex.getNumVertex()){
            return 1;
        }else if(this.getNumVertex()<vertex.getNumVertex()){
            return -1;
        }else {
            return 0;
        }
    }
}
