package graphs;

public class UnionFind {
    public int[] adjList;
    public int[] rank;
    /// [0,1,2,3,4,5]
    /// [0,1,0,3,3,5]

    public UnionFind(int totalElements) {
        this.adjList = new int[totalElements+1];
        this.rank = new int[totalElements+1];
        for (int x = 0;x<totalElements+1;x++){
            this.adjList[x] = x;
        }
    }

    public void union(int member1, int member2){
        int x = find(member1);
        int y = find(member2);
        if (x == y) return;
        this.adjList[y] = x; /// assigning parent
    }

    public int find(int a){
        if (this.adjList[a] == a) return a;
        else return find(adjList[a]);
    }

    public int findAndCompressPath(int a){
        if (this.adjList[a] != a) {
            this.adjList[a] = findAndCompressPath(this.adjList[a]);
        }
        return this.adjList[a];
    }

    public void unionByRank(int member1, int member2){
        int member1Root = find(member1); // 5
        int member2Root = find(member2); // 0
        if (this.rank[member1Root] == this.rank[member2Root]){
            this.adjList[member2] = member1; // parent of 2 is 0
            this.rank[member1] += 1; // increase rank of 0
        }
        else if (this.rank[member1Root] > this.rank[member2Root]){
            this.adjList[member2Root] = member1Root;
        }
        else if (this.rank[member1Root] < this.rank[member2Root]){
            this.adjList[member1Root] = member2Root;
        }
    }

    public void unionByRankAndCompressedPath(int member1, int member2){
        int member1Root = findAndCompressPath(member1); // 5
        int member2Root = findAndCompressPath(member2); // 0
        if (this.rank[member1Root] == this.rank[member2Root]){
            this.adjList[member2] = member1; // parent of 2 is 0
            this.rank[member1] += 1; // increase rank of 0
        }
        else if (this.rank[member1Root] > this.rank[member2Root]){
            this.adjList[member2Root] = member1Root;
        }
        else if (this.rank[member1Root] < this.rank[member2Root]){
            this.adjList[member1Root] = member2Root;
        }
    }

}
