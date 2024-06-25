import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BreadthFirstPaths {
    private Graph g;
    private int s;
    public int[] parent;
    private boolean[] visited;

    private void bfs() {
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        while (q.size()>0) {
            int w = q.poll();
            visited[w] = true;

            for (int v: g.adj(w)) {
                if (!visited[v]) {
                    q.add(v);
                    parent[v] = w;
                }
            }
        }
    }

    public BreadthFirstPaths(Graph g, int s) {
        this.g = g;
        this.s = s;

        parent = new int[g.V()];
        visited = new boolean[g.V()];
        for (int i=0; i<g.V(); i++) {
            parent[i] = -1;
        }

        bfs();
    }

    public boolean hasPathTo(int v) {
        return parent[v]!=-1;
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;

        Deque<Integer> p = new LinkedList<>();
        while (parent[v]!=s) {
            p.push(v);
            v = parent[v];
        }
        p.push(s);

        System.out.println(p);

        return p;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Create a graph with data streamed from a text file
        File f = new File("src/test2.txt");
        Scanner sc = new Scanner(f);
        int V = sc.nextInt();
        int E = sc.nextInt();

        Graph g = new Graph(V);
        for (int i=0; i<E; i++) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(v, w);
        }
        System.out.println(g);

        BreadthFirstPaths p = new BreadthFirstPaths(g, 0);
        System.out.println(p.hasPathTo(4));
        Iterator itr = p.pathTo(4).iterator();
        StringBuilder sb = new StringBuilder();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append("->");
            }
        }
        System.out.println(sb);

        for (int i: p.parent) {
            System.out.printf("%s ", i);
        }
        System.out.println();

//        itr = p.pathTo(0).iterator();
//        sb = new StringBuilder();
//        while (itr.hasNext()) {
//            sb.append(itr.next());
//            if (itr.hasNext()) {
//                sb.append("->");
//            }
//        }
//        System.out.println(sb);
    }
}
