package edu.khnu.rbecs.ads2026;

import java.util.*;

public class GraphAlgorithms {
    static void main() {
        Graph g = new UndirectedGraph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 1);

        int start = 0;
        var bfsTree = bfs(g, start, new SimpleVisitor()); // 0 1 2 4 3
        System.out.println(shortestPathTo(4, bfsTree)); // [0, 1, 4]
    }

    static Map<Integer, BacktrackInfo> bfs(Graph g, int source, Visitor visitor) {
        Map<Integer, BacktrackInfo> res = new HashMap<>();
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        res.put(source, new BacktrackInfo(0, -1)); // -1 as a sentinel
        while (!queue.isEmpty()) {
            var current = queue.poll();
            visitor.visit(current);
            for (var neighbor : g.neighbors(current)) {
                if (!res.containsKey(neighbor)) {
                    queue.add(neighbor);
                    res.put(neighbor, new BacktrackInfo(res.get(current).distance + 1, current));
                }
            }
        }
        return res;
    }

    static List<Integer> shortestPathTo(int target, Map<Integer, BacktrackInfo> bfsTree) {
        List<Integer> path = new ArrayList<>();
        int current = target;
        while (current != -1) { // the sentinel
            path.add(current);
            current = bfsTree.get(current).previous;
        }
        Collections.reverse(path);
        return path;
    }


    @FunctionalInterface
    interface Visitor {
        void visit(int node);
    }

    static class SimpleVisitor implements Visitor {
        @Override
        public void visit(int node) {
            System.out.println("Visiting node " + node);
        }
    }

    record BacktrackInfo(int distance, int previous) {}
}

abstract class Graph {
    private final int nNodes;
    protected List<Integer>[] adjacency;

    public Graph(int nNodes) {
        this.nNodes = nNodes;
        adjacency = new List[nNodes];
        for (int i = 0; i < nNodes; i++) {
            adjacency[i] = new ArrayList<>();
        }
    }
    public int nNodes() {
        return nNodes;
    }
    abstract void addEdge(int node1, int node2);

    Iterable<Integer> neighbors(int iNode) {
        return Collections.unmodifiableList(adjacency[iNode]);
    }
}

class DirectedGraph extends Graph {
    public DirectedGraph(int nNodes) {
        super(nNodes);
    }

    void addEdge(int node1, int node2) {
        adjacency[node1].add(node2);
    }
}

class UndirectedGraph extends Graph {
    public UndirectedGraph(int nNodes) {
        super(nNodes);
    }

    @Override
    void addEdge(int node1, int node2) {
        adjacency[node1].add(node2);
        adjacency[node2].add(node1);
    }
}