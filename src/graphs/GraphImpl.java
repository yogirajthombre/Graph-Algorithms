package graphs;

public class GraphImpl {
    public static void main(String[] args) {

        Graph graph = new Graph(5);
        graph.addEdgeUndirected(0,1);
        graph.addEdgeUndirected(0,2);
        graph.addEdgeUndirected(1,3);
        graph.addEdgeUndirected(2,3);
        graph.addEdgeUndirected(3,4);
        graph.bfs();
        System.out.println("");
        graph.dfs();

        Graph graph1 = new Graph(4);
        graph1.addEdgeUndirected(0,1);
        graph1.addEdgeUndirected(1,2);
        graph1.addEdgeUndirected(2,3);

        boolean op = graph1.detectCycleInUndirectedGraph();
        System.out.println(op);

        Graph graph2 = new Graph(6);
        graph2.addEdgeDirected(0,1);
        graph2.addEdgeDirected(1,2);
        graph2.addEdgeDirected(3,4);
        graph2.addEdgeDirected(5,4);
        graph2.addEdgeDirected(5,3);

        boolean op1 = graph2.detectCycleInDirectedGraph();
        System.out.println(op1);

        Graph graph3 = new Graph(6);
        graph3.addEdgeDirected(0,3);
        graph3.addEdgeDirected(0,2);
        graph3.addEdgeDirected(2,3);
        graph3.addEdgeDirected(3,1);
        graph3.addEdgeDirected(2,1);
        graph3.addEdgeDirected(1,4);
        graph3.addEdgeDirected(5,4);
        graph3.addEdgeDirected(5,1);

        System.out.println("Apply topological sort using dfs ---->");

        graph3.applyTopologicalSort();

        System.out.println("");

        System.out.println("Apply topological sort using bfs (kahn's Algorithm)---->");

        System.out.println("");

        graph3.applyTopologicalSortUsingBfs();

    }
}