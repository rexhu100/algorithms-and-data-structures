import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Graph {
    /*
    The graph uses a fairly generic representation, where a graph with V vertices are represented with labels from 0 to
    V-1, and the adjacency list is represented by an array of iterables. There is no additional class to represent the
    graph nodes.
     */
    private int V, E;
    private Set<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = new Set[V];
        for (int i=0; i<V; i++) {
            adj[i] = new HashSet<>();
        }
    }

    public void addEdge(int v, int w) {
        int oldLen = adj[v].size();
        // In an undirected graph, the adjacency lists of both nodes need to be updated.
        adj[v].add(w);
        adj[w].add(v);

        int newLen = adj[v].size();

        // This check is to prevent duplicates in the edge list.
        if (newLen>oldLen) {
            E++;
        }
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(V).append("\n").append(E).append("\n");
        for (int v=0; v<V; v++) {
            for (int w: adj[v]) {
                ret.append(v).append("-").append(w).append("\n");
            }
        }

        return ret.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Create a graph
        Graph g = new Graph(10);
        System.out.println("Empty graph created...");
        System.out.println(g);
        g.addEdge(0, 1);
        g.addEdge(0, 5);
        g.addEdge(2, 9);
        System.out.println("After adding some edges...");
        System.out.println(g);

        // Create a graph with data streamed from a text file
        File f = new File("src/test1.txt");
        Scanner sc = new Scanner(f);
        int V = sc.nextInt();
        int E = sc.nextInt();

        g = new Graph(V);
        for (int i=0; i<E; i++) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(v, w);
        }
        System.out.println("Graph created from data streamed from a file...");
        System.out.println(g);
    }
}
