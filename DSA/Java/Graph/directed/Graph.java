import java.util.Iterator;
import java.util.LinkedList;
@SuppressWarnings("unchecked")

class Graph {
    private final int V;
    private final LinkedList<Integer>[] adj;

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    void addEdge(int v, int v2) {
        adj[v].add(v2);
    }

    void printGraph() {
        for (int i = 0; i < V; i++) {
            System.out.print(i + " -> ");
            for (Integer j : adj[i]) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    public boolean DFS(int u, boolean visited[], int dest) {
        if (u == dest) {
            return true;
        }
        visited[u] = true;
        Integer i;
        Iterator<Integer> it = adj[u].iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i]) {
                if (DFS(i, visited, dest)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean BFS(int src, boolean visited[], int dest) {
        LinkedList<Integer> queue = new LinkedList<>();
        visited[src] = true;
        queue.add(src);
        while (!queue.isEmpty()) {
            src = queue.poll();
            if (src == dest) {
                return true;
            }
            Iterator<Integer> it = adj[src].listIterator();
            while (it.hasNext()) {
                int n = it.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return false;
    }

    public boolean findpath(int src, int dest, String method) {
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; i++) {
            visited[i] = false;
        }
        if ((method.toUpperCase()).equals("DFS")) {
            return DFS(src, visited, dest);
        } else if ((method.toUpperCase()).equals("BFS")) {
            return BFS(src, visited, dest);
        }
        return false;
    }

    public static void main(String[] args) {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 0);

        if (g.findpath(1, 3, "bfs")) {
            System.out.println("Path exists");
        } else {
            System.out.println("Path does not exist");
        }

        g.printGraph();
    }
}