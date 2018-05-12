import java.io.*;
import java.util.*;

public class min_cost_A {
    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        Graph graph = new Graph(n, 0, n - 1);

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            int cap = nextInt();
            int cost = nextInt();
            graph.addEdge(from, to, cap, cost);
        }

        out.write(graph.minCostFlow() + "");
    }

    class Graph {
        ArrayList<Edge>[] edges;
        int sz;
        int s;
        int t;
        long[] dist;
        long cost;
        int[] par;
        Edge[] edgesOnWay;

        Graph(int sz, int s, int t) {
            edges = new ArrayList[sz];
            this.sz = sz;
            this.s = s;
            this.t = t;
            cost = 0;
            dist = new long[sz];
            par = new int[sz];
            edgesOnWay = new Edge[sz];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, int cap, int cost) {
            Edge e1 = new Edge(from, to, 0, cap, cost);
            Edge e2 = new Edge(to, from, 0, 0, -cost);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        boolean findMinCostWay() {
            ArrayDeque<Integer> queue = new ArrayDeque<>(sz);
            queue.addLast(s);
            Arrays.fill(dist, Long.MAX_VALUE);
            int[] mark = new int[sz];
            dist[s] = 0;
            while (!queue.isEmpty()) {
                int v = queue.poll();
                mark[v] = 2;
                for (int i = 0; i < edges[v].size(); i++) {
                    Edge e = edges[v].get(i);
                    if (dist[e.from] != Long.MAX_VALUE && e.flow < e.cap && dist[e.from] + e.cost < dist[e.to]) {
                        dist[e.to] = dist[e.from] + e.cost;
                        if (mark[e.to] == 0) {
                            queue.addLast(e.to);
                        } else if (mark[e.to] == 2) {
                            queue.addFirst(e.to);
                        }
                        mark[e.to] = 1;
                        par[e.to] = e.from;
                        edgesOnWay[e.to] = e;
                    }
                }
            }
            return dist[t] != Long.MAX_VALUE;
        }

        long minCostFlow() {
            while (findMinCostWay()) {
                long add = Long.MAX_VALUE;
                for (int v = t; v != s; v = par[v]) {
                    Edge e = edgesOnWay[v];
                    add = Math.min(add, e.cap - e.flow);
                }

                for (int v = t; v != s; v = par[v]) {
                    Edge e = edgesOnWay[v];
                    e.flow += add;
                    e.rev.flow -= add;
                    cost += add * e.cost;
                }
            }
            return cost;
        }
    }

    class Edge {
        int from;
        int to;
        int flow;
        int cap;
        int cost;
        Edge rev;

        Edge(int from, int to, int flow, int cap, int cost) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.cap = cap;
            this.cost = cost;
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

    private double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    public static void main(String[] args) throws IOException {
        new min_cost_A().run();
    }

    private void run() throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("mincost.in")));
            out = new PrintWriter(new File("mincost.out"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}