import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeightedQuickUnionUF implements UF {
    private int[] parent, sz;

    public WeightedQuickUnionUF(int N) {
        parent = new int[N];
        sz = new int[N];

        for (int i=0; i<N; i++) {
            parent[i] = i;
        }

        Arrays.fill(sz, 1);
    }

    private int root(int p) {
        List<Integer> temp = new ArrayList<>();
        while (p!=parent[p]) {
            temp.add(p);
            p = parent[p];
        }

        // Path compression: making the parent node
        for (int i: temp) {
            parent[i] = p;
        }

        return p;
    }

    @Override
    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);

        if (pRoot!=qRoot) {
            if (sz[pRoot] > sz[qRoot]) {
                parent[qRoot] = pRoot;
                sz[pRoot] += sz[qRoot];
            } else {
                parent[pRoot] = qRoot;
                sz[qRoot] += sz[pRoot];
            }
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p)==root(q);
    }
}
