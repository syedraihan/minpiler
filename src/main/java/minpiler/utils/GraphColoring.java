package minpiler.utils;

import java.util.*;
 
/**
 A Java program to implement greedy algorithm for graph coloring
 It doesn't guarantee to use minimum colors, but it guarantees an upper bound on the number of colors. 
 The basic algorithm never uses more than d+1 colors where d is the maximum degree of a vertex in the given graph.
 Adopted From: http://www.geeksforgeeks.org/graph-coloring-set-2-greedy-algorithm/
 */
public class GraphColoring
{
    public static int[] assignColors(int[][] graph)
    {
        Graph graph2 = new Graph(graph.length);

        for (int u=0; u<graph.length; u++)
        for (int w=0; w<graph[0].length; w++) {
            if (graph[u][w] == 1)
                graph2.addEdge(u, w);
        }

        return assignColors(graph2);            
    }

    // Assigns colors (starting from 0) to all vertices
    public static int[] assignColors(Graph graph)
    {
        int V = graph.getVertextCount();
        int[] result = new int[V];
 
        // Assign the first color to first vertex
        result[0]  = 0;
 
        // Initialize remaining V-1 vertices as unassigned
        for (int u = 1; u < V; u++)
            result[u] = -1;  // no color is assigned to u
 
        // A temporary array to store the available colors. True
        // value of available[cr] would mean that the color cr is
        // assigned to one of its adjacent vertices
        boolean available[] = new boolean[V];
        for (int cr = 0; cr < V; cr++)
            available[cr] = false;
 
        // Assign colors to remaining V-1 vertices
        for (int u = 1; u < V; u++)
        {
            // Process all adjacent vertices and flag their colors
            // as unavailable
            Iterator<Integer> it = graph.getAdjacencyList(u).iterator();
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = true;
            }
 
            // Find the first available color
            int cr;
            for (cr = 0; cr < V; cr++)
                if (available[cr] == false)
                    break;
 
            result[u] = cr; // Assign the found color
 
            // Reset the values back to false for the next iteration
            it = graph.getAdjacencyList(u).iterator();
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = false;
            }
        }

        return result;
    }
}