package graphs;

import java.util.*;

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
    public static int networkDelayTime(int[][] times, int n, int k) {
        PriorityQueue<Pair> queue = new PriorityQueue<Pair>(new Comparator<Pair>() {
            @Override
            public int compare(Pair pair, Pair t1) {
                return pair.wt - t1.wt;
            }
        });
        queue.add(new Pair(k,0));
        int wt = 0;
        boolean[] visited = new boolean[n+1];
        while (!queue.isEmpty()){
            Pair currentPair = queue.poll();
            int vertex = currentPair.vertex;
            if (visited[vertex]) break;
            wt = currentPair.wt;
            visited[vertex] = true;
            for (int[] nodes : times){
                if (nodes[0] == vertex){
                    int dest = nodes[1];
                    int currWt = nodes[2];
                    if (!visited[dest]){
                        queue.add(new Pair(dest,wt+currWt));
                    }
                }
            }
        }
        for (int x = 0;x<visited.length;x++){
            if (visited[x]) return -1;
        }
        return wt;
    }

    /**
     *
     * Bellman Ford Algorithm
     * **/
    public static boolean isNegativeCycle(int[][] edges,int n){
        int dist[] = new int[n];
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[0] = 0;

        for (int count = 1;count < n;count++){
            for (int edge = 0;edge < edges.length;edge++){
                int src = edges[edge][0];
                int dest = edges[edge][1];
                int wt = edges[edge][2];
                if (dist[src] != Integer.MAX_VALUE && dist[src]+wt < dist[dest]){
                    dist[dest] = dist[src]+wt;
                }
            }
        }

        for (int edge = 0;edge < edges.length;edge++){
            int src = edges[edge][0];
            int dest = edges[edge][1];
            int wt = edges[edge][2];
            if (dist[src] != Integer.MAX_VALUE && dist[src]+wt < dist[dest]){
                return true;
            }
        }

        return false;
    }

    /**
     *
     *
     * Kruskal's Algorithm
     * **/
    public static int spanningTree(int n,int[][] graph){
        int minimumSpanningTree = 0;
        UnionFind unionFind = new UnionFind(n);
        PriorityQueue<Pair1> queue = new PriorityQueue<>(new Comparator<Pair1>() {
            @Override
            public int compare(Pair1 pair1, Pair1 t1) {
                return pair1.wt-t1.wt;
            }
        });

        for (int i = 0;i<n;i++){
           int[] gt = graph[i];
           int u = gt[0];
           int v = gt[1];
           int wt = gt[2];
           Pair1 pair1 = new Pair1(u,v,wt);
           queue.add(pair1);
        }

        while (!queue.isEmpty()){
            Pair1 pair1 = queue.poll();
            int u = pair1.u;
            int vertex = pair1.vertex;
            int x = unionFind.find(u);
            int y = unionFind.find(vertex);
            if (x != y){
                unionFind.adjList[y] = x;
                minimumSpanningTree += pair1.wt;
            }
        }

        return minimumSpanningTree;
    }

    public static class Pair {
        int vertex;
        int wt;
        public Pair(int vertex,int wt){
            this.vertex = vertex;
            this.wt = wt;
        }
    }

    public static class Pair1 {
        int u;
        int vertex;
        int wt;
        public Pair1(int u,int vertex,int wt){
            this.u = u;
            this.vertex = vertex;
            this.wt = wt;
        }
    }

    public static ArrayList<String> findPath(int[][] m, int n) {
        ArrayList<String> finalPath = new ArrayList<String>();
        String path = "";
        boolean[][] visited = new boolean[n][n];
        helper(m,n,finalPath,0,0,path,visited);
        return finalPath;
    }


    public static void helper(int[][] m,int n,
                              ArrayList<String> path,int row,int col,
                              String currPath,boolean[][] visited){


        if ((row == n-1 && col == n-1)){
            path.add(currPath);
            return;
        }else {
            visited[row][col] = true;
        }

        if (row+1 < n && m[row+1][col] == 1 && !visited[row+1][col]) {
            helper(m, n, path, row + 1, col, currPath + "D", visited); /// Down
        }

        if (row -1 >= 0 && m[row-1][col] == 1 && !visited[row-1][col]) {
            helper(m, n, path, row - 1, col, currPath + "U",visited);  ///Up
        }

        if (col+1 < n && m[row][col+1] == 1 && !visited[row][col+1]) {
            helper(m, n, path, row, col + 1, currPath + "R",visited); /// Right
        }

        if (col-1 >= 0 && m[row][col-1] == 1 && !visited[row][col-1]) {
            helper(m, n, path, row, col - 1, currPath + "L",visited); /// Left
        }

        visited[row][col] = false;

    }

    /***
     * 
     * 
     *  beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     * 
     */

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int length = 0;
        boolean isAtEnd = false;
        boolean[] visited = new boolean[wordList.size()];
        Queue<LevelAndWord> queue = new LinkedList<>();
        queue.add(new LevelAndWord(0,beginWord));
        int prevLevel = -1;
        while (!queue.isEmpty()){
            LevelAndWord currWord = queue.poll();
            if (currWord.level>prevLevel){
                length++;
                prevLevel = currWord.level;
            }
            if (currWord.word.equals(endWord)){
                isAtEnd = true;
                break;
            }
            for (int x = 0;x<wordList.size();x++){
                if (!visited[x] && isDifferentiatedByOneLetter(currWord.word,wordList.get(x))){
                    visited[x] = true;
                    queue.add(new LevelAndWord(currWord.level+1, wordList.get(x)));
                }
            }
        }

        if (isAtEnd) return length;
        else return 0;
    }

    public static boolean isDifferentiatedByOneLetter(String currentWord,String word){
       char[] currentWordChars = currentWord.toCharArray();
       char[] wordChars = word.toCharArray();
       int index = 0;
       int count = 0;
       while (index < currentWordChars.length){
           char c = currentWordChars[index];
           char w = wordChars[index];
           if (c == w){
               index++;
               continue;
           }else {
               index++;
               count++;
           }
       }

       if (count == 1){
           /// Difference of one letter;
           return true;
       }
       return false;
    }

    public class LevelAndWord {
        int level;
        String word;

        public LevelAndWord(int level, String word) {
            this.level = level;
            this.word = word;
        }
    }

    /***
     * 
     * Island Perimeter 
     * Time complexity : O(m*n)
     * Space Complexity : O(1)
     * 
     * If there are adjacent ones then reduce the current Output by one 
     *
     */

    public int islandPerimeter(int[][] grid) {
        int output = 0;
        for (int row = 0;row<grid.length;row++){
            for (int col = 0;col<grid[0].length;col++){
            if (grid[row][col] == 1){
            
            output += 4;

            if (col-1 >= 0  && grid[row][col-1] == 1){
                output -= 1;
            } 

            if (col+1 != grid[row].length && grid[row][col+1] == 1){
                output -=1;
            }  

            if (row-1 >= 0 && grid[row-1][col] == 1){
                output -= 1;
            }   
                    
            if (row+1 != grid.length && grid[row+1][col] == 1){
                output -=1;
            } 

            }
            }
        }

        return output;
    }

}