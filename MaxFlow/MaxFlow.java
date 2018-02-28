import java.io.*;
import java.util.*;

public class MaxFlow {
    private void solve() throws IOException {

    }

    class Graph {
        ArrayList<Edge>[] edges;
        int sz;
        int s;
        int t;
        int scale;
        int[] dist;
        int[] visited;
        int[] deg;

        Graph(int sz, int s, int t) {
            edges = new ArrayList[sz];
            this.sz = sz;
            this.s = s;
            this.t = t;
            dist = new int[sz];
            visited = new int[sz];
            deg = new int[31];
            deg[0] = 1;
            for (int i = 1; i < 31; i++) {
                deg[i] = deg[i - 1] << 1;
            }
            scale = deg[30];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, int flow, int maxFlow) {
            Edge e1 = new Edge(from, to, flow, maxFlow);
            Edge e2 = new Edge(to, from, -flow, 0);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        boolean addOnWay(int v, int flow) {
            if (v == t) {
                return true;
            }
            for (int i = visited[v]; i < edges[v].size(); visited[v] = ++i) {
                Edge e = edges[v].get(i);
                if ((e.cap - e.flow) >= flow && dist[e.to] == dist[v] + 1) {
                    if (addOnWay(e.to, flow)) {
                        e.flow += flow;
                        e.rev.flow -= flow;
                        return true;
                    }
                }
            }
            return false;
        }

        boolean findShortestWay() {
            Queue<Integer> queue = new ArrayDeque<>(sz);
            queue.offer(s);
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[s] = 0;
            while (!queue.isEmpty() && dist[t] == Integer.MAX_VALUE) {
                int v = queue.poll();
                for (Edge e : edges[v]) {
                    if ((e.cap - e.flow) >= scale && dist[e.to] == Integer.MAX_VALUE) {
                        dist[e.to] = dist[v] + 1;
                        queue.offer(e.to);
                        if (e.to == t) {
                            return true;
                        }
                    }
                }
            }
            return dist[t] != Integer.MAX_VALUE;
        }

        long dinic() {
            long maxFlow = 0;
            for (scale = deg[30]; scale >= 1; scale >>= 1) {
                while (findShortestWay()) {
                    Arrays.fill(visited, 0);
                    while (addOnWay(s, scale)) {
                        maxFlow += scale;
                    }
                }
            }
            return maxFlow;
        }
    }

    class Edge {
        int from;
        int to;
        int flow;
        int cap;
        Edge rev;

        Edge(int from, int to, int flow, int cap) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.cap = cap;
        }
    }

    // ------------------

    private BufferedReader br;
    private StringTokenizer st;
    private PrintWriter out;
//    private PrintWriter out = new PrintWriter(System.out);
//    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public static void main(String[] args) throws IOException {
        new MaxFlow().run();
    }

    private void run() throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
            out = new PrintWriter(new File("output.txt"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}