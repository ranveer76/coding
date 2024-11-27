import java.util.Iterator;
import java.util.LinkedList;
@SuppressWarnings("unchecked")

class Graph{
    private final int V;
    private final LinkedList<Integer> adj[];

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }
    
    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    void printGraph() {
        for (int i = 0; i < V; i++) {
            System.out.print(i);
            for (Integer pCrawl : adj[i]) {
                System.out.print(" -> " + pCrawl);
            }
            System.out.println("");
        }
    }

    Boolean DFS(int v, Boolean visited[], int parent) {
        visited[v] = true;
        Integer i;
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i]) {
                if (DFS(i, visited, v)) {
                    return true;
                }
            } else if (i != parent) {
                return true;
            }
        }
        return false;
    }

    Boolean BFS(int s, Boolean visited[], int parent) {
        visited[s] = true;
        Integer i;
        LinkedList<Integer> queue = new LinkedList<>();
        LinkedList<Integer> parentQueue = new LinkedList<>();
        queue.add(s);
        parentQueue.add(parent);
        while (!queue.isEmpty()) {
            s = queue.poll();
            parent = parentQueue.poll();
            Iterator<Integer> it = adj[s].iterator();
            while (it.hasNext()) {
                i = it.next();
                if (!visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                    parentQueue.add(s);
                } else if (i != parent) {
                    return true;
                }
            }
        }
        return false;
    }

    Boolean isCyclic(String method) {
        Boolean visited[] = new Boolean[V];
        for (int i = 0; i < V; i++) {
            visited[i] = false;
        }
        for (int u = 0; u < V; u++) {
            if (!visited[u]) {
                if (method.toUpperCase().equals("DFS")? DFS(u, visited, -1):BFS(u, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        if (g1.isCyclic("DFS")) {
            System.out.println("Graph contains cycle");
        } else {
            System.out.println("Graph doesn't contains cycle");
        }

        g1.printGraph();
        System.out.println("");

        Graph g2 = new Graph(3);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        if (g2.isCyclic("BFS")) {
            System.out.println("Graph contains cycle");
        } else {
            System.out.println("Graph doesn't contains cycle");
        }

        g2.printGraph();
    }

}