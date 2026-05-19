package edu.khnu.rbecs.ads2026;

import java.util.*;

public class GraphAlgorithms {
    static void main() {
        Graph g = new UndirectedGraph(5);
    }


}



abstract class Graph {
    private int nNodes;
    protected Map<Integer, List<Integer>> outAdjacency = new HashMap<>();

    public Graph(int nNodes) {
        this.nNodes = nNodes;
        for (int i = 0; i < nNodes; i++) {
            outAdjacency.put(i, new ArrayList<>());
        }
    }
    public int nNodes() {
        return nNodes;
    }
    abstract void addEdge(int node1, int node2);

    Iterator<Integer> neighbors(int iNode) {
        return outAdjacency.get(iNode).iterator();
    }
}

class DirectedGraph extends Graph {


    public DirectedGraph(int nNodes) {
        super(nNodes);
    }

    void addEdge(int node1, int node2) {
        outAdjacency.get(node1).add(node2);
    }
}

class UndirectedGraph extends Graph {
    public UndirectedGraph(int nNodes) {
        super(nNodes);
    }

    @Override
    void addEdge(int node1, int node2) {
        outAdjacency.get(node1).add(node2);
        outAdjacency.get(node2).add(node1);
    }
}