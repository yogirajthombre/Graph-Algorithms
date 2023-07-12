package graphs;

import java.util.Arrays;

public class UnionFindImpl {
    public static void main(String[] args) {
        UnionFind unionFind = new UnionFind(7);
        unionFind.union(0,2);
        unionFind.union(3,4);
        unionFind.union(2,4);
        unionFind.union(5,4);
        unionFind.unionByRank(0,2);
        unionFind.unionByRank(3,4);
        unionFind.unionByRank(2,4);
        unionFind.unionByRank(5,4);

        unionFind.unionByRankAndCompressedPath(0,2);
        unionFind.unionByRankAndCompressedPath(3,4);
        unionFind.unionByRankAndCompressedPath(2,4);
        unionFind.unionByRankAndCompressedPath(5,4);

        System.out.println(Arrays.toString(unionFind.adjList));
    }
}
