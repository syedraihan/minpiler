package minpiler.utils;

import java.util.LinkedList;
 
// This class represents an undirected graph using adjacency list
public class Graph
{
    private int _vertexCount;                       
    private LinkedList<Integer>[] _adjacencyList;   
 
    public Graph(int vertexCount)
    {
        _vertexCount = vertexCount;
        _adjacencyList = new LinkedList[vertexCount];

        for (int i=0; i < vertexCount; ++i)
            _adjacencyList[i] = new LinkedList();
    }
 
    public void addEdge(int v, int w)
    {
        _adjacencyList[v].add(w);
        _adjacencyList[w].add(v); //Graph is undirected
    }

    public int getVertextCount() {
        return _vertexCount;
    }
    
    public LinkedList<Integer> getAdjacencyList(int vertextNo) {
        return _adjacencyList[vertextNo];
    }
}