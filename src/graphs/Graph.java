package graphs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    private int vertices;
    private LinkedList<Integer>[] adjList;

    public Graph(int vertices) {
        this.adjList = new LinkedList[vertices];
        this.vertices = vertices;
        for (int x = 0; x< vertices; x++){
            this.adjList[x] = new LinkedList<Integer>();
        }
    }

    protected void addEdgeUndirected(int start,int end){
       this.adjList[start].add(end);
       this.adjList[end].add(start);
    }

    protected void addEdgeDirected(int start,int end){
        this.adjList[start].add(end);
    }

    public void bfs() {
        boolean[] visited = new boolean[vertices];
        for (int vertex = 0;vertex<vertices;vertex++){
            if (!visited[vertex]){
                traverseBfs(vertex,visited);
            }
        }
    }

    /**
     * Use queue for the bfs implementation
     *
     *
     *
     */
    private void traverseBfs(int src, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        while (!queue.isEmpty()){
           int currElement = queue.poll();
           System.out.print(currElement + "->");
           visited[currElement] = true;
           for (int vertex : this.adjList[currElement]){
               if (!visited[vertex] && !queue.contains(vertex)){
                   queue.add(vertex);
               }
           }
        }
    }

    /**
     * Implementation of Dfs.
     *
     *
     *
     */
    public void dfs() {
        boolean[] visited = new boolean[vertices];
        for (int vertex = 0;vertex<vertices;vertex++){
            if (!visited[vertex]){
                traverseDfs(vertex,visited);
            }
        }
    }

    private void traverseDfs(int src, boolean[] visited) {
        visited[src] = true;
        System.out.print(src + "->");
        for (int vertex : this.adjList[src]){
            if (!visited[vertex]){
                traverseDfs(vertex,visited);
            }
        }
    }

    /**
     * Maintain Parent node for each of the nodes and then if parent != neighbor then cycle is present
     * Generally if the nieghbor is visited then parent == neighbor
     * Only in case of cycle getting detected parent != neighbor
     *
     */
    public boolean detectCycleInUndirectedGraph() {
        boolean[] visited = new boolean[vertices];
        for (int vertex = 0;vertex<vertices;vertex++){
            if (!visited[vertex]){
               if (traverseDfsWise(vertex,visited,-1)){
                   return true;
               }
            }
        }
        return false;
    }

    private boolean traverseDfsWise(int vertex, boolean[] visited, int parent) {
        visited[vertex] = true;
        for (int neighbor : this.adjList[vertex]){
            if (!visited[neighbor]){
                if (traverseDfsWise(neighbor,visited,vertex)) {
                    return true;
                }
            }
            else if (parent != neighbor){
                return true;
            }
        }
        return false;
    }

    /**
     * Maintain a recursion stack and make the recursion stack element false after the end of iteration
     * loop is found when the recStack[ele] == true
     *
     */
    public boolean detectCycleInDirectedGraph() {
        boolean[] visited = new boolean[vertices];
        boolean[] recVisited = new boolean[vertices];
        for (int vertex = 0;vertex<vertices;vertex++){
            if (!visited[vertex]){
                if (traverseDfsWiseDirected(vertex,visited,recVisited)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean traverseDfsWiseDirected(int vertex, boolean[] visited, boolean[] recVisited) {
        visited[vertex] = true;
        recVisited[vertex] = true;
        for (int neighbor : this.adjList[vertex]){
            if (!visited[neighbor]){
                if (traverseDfsWiseDirected(neighbor,visited,recVisited)) {
                    return true;
                }
            }else if (recVisited[neighbor]){
                return true;
            }
        }
        recVisited[vertex] = false;
        return false;
    }

    /**
     * Traverse to the end of the nodes using dfs and push it to the stack
     * So the bottom most element in stack has less dependency and as the stack size increases the dependency goes higher
     *
     */
    public void applyTopologicalSort() {
        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<Integer>();
        for (int vertex = 0;vertex<vertices;vertex++){
            if (!visited[vertex]){
                traverseDfsForTopoSort(vertex,visited,stack);
            }
        }
        while (!stack.isEmpty()){
            int ele = stack.pop();
            System.out.print(ele+"-->");
        }
    }

    private void traverseDfsForTopoSort(int vertex, boolean[] visited, Stack<Integer> stack) {
        visited[vertex] = true;
        for (int neighbor : this.adjList[vertex]){
            if (!visited[neighbor]){
                traverseDfsForTopoSort(neighbor,visited, stack);
            }
        }
        /// Push the element with lowest dependency to the bottom of the stack .....
        stack.push(vertex);
    }

    /**
     * Kahn's Algorithm
     *
     *
     * Intuition is to calculate the in-degree of each vertex for zero in degree nodes add nodes to the queue
     * For the zero in-degree elements poll from the queue and then decrement in-degree of neighbors
     * if all the elements are visited then stop the recursion
     */
    public void applyTopologicalSortUsingBfs() {
        int[] calculateInDegree = new int[vertices];
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        for (int x  = 0;x<adjList.length;x++){
            for (int ele : adjList[x]){
                calculateInDegree[ele]++;
            }
        }
        applyBfsForTopoSort(calculateInDegree,queue,visited);
    }

    private void applyBfsForTopoSort(int[] calculateInDegree, Queue<Integer> queue, boolean[] visited) {

        if (checkForElementVisited(visited)) {
            return;
        }

        for (int x = 0;x<calculateInDegree.length;x++){
            if (calculateInDegree[x] == 0 && !visited[x]){
                visited[x] = true;
                queue.add(x);
            }
        }

        while (!queue.isEmpty()){
            int ele = queue.poll();
            visited[ele] = true;
            System.out.print(ele+"--->");
            for (int x : adjList[ele]){
                calculateInDegree[x]--;
            }
            applyBfsForTopoSort(calculateInDegree,queue, visited);
        }
    }

    private boolean checkForElementVisited(boolean[] visited) {
        for (int x = 0;x<visited.length;x++){
            if (!visited[x]) return false;
        }
        return true;
    }
}
