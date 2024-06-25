public class QuickUnionUF implements UF {
    private int[] parent;

    public QuickUnionUF(int N) {
        parent = new int[N];
        for (int i=0; i<N; i++) {
            parent[i] = i;
        }
    }

    private int root(int p) {
        while (p!=parent[p]) p = parent[p];

        return p;
    }

    @Override
    public void union(int p, int q) {
        parent[p] = q;
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p)==root(q);
    }
}
