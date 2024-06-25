import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DepthFirstPaths {
    private int[] parent;
    private Set<Integer> visited;
    private Graph G;
    private int s;

    private void dfs(int v) {
        visited.add(v);

        for (int w: G.adj(v)) {
            if (!visited.contains(w)) {
                parent[w] = v;
                dfs(w);
            }
        }
    }

    public DepthFirstPaths(Graph G, int s) {
        this.G = G;
        this.s = s;

        parent = new int[G.V()];
        for (int i=0; i<G.V(); i++) {
            parent[i] = -1;
        }
        parent[s] = s;

        visited = new HashSet<>();
        dfs(s);
    }

    public boolean hasPathTo(int v) {
        return parent[v]!=-1;
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Deque<Integer> ret = new LinkedList<>();
        while (v!=s) {
            ret.push(v);
            v = parent[v];
        }
        ret.push(v);

        return ret;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Create a graph with data streamed from a text file
        File f = new File("src/test1.txt");
        Scanner sc = new Scanner(f);
        int V = sc.nextInt();
        int E = sc.nextInt();

        Graph g = new Graph(V);
        for (int i=0; i<E; i++) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(v, w);
        }

        DepthFirstPaths p = new DepthFirstPaths(g, 0);
        System.out.println(p.hasPathTo(6));
        Iterator itr = p.pathTo(6).iterator();
        StringBuilder sb = new StringBuilder();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append("->");
            }
        }
        System.out.println(sb);

        itr = p.pathTo(0).iterator();
        sb = new StringBuilder();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append("->");
            }
        }
        System.out.println(sb);
    }
}
