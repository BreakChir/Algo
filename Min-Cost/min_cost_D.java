import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class min_cost_D {
    private void solve() throws IOException {
        int r1 = nextInt();
        int s1 = nextInt();
        int p1 = nextInt();
        int r2 = nextInt();
        int s2 = nextInt();
        int p2 = nextInt();

        Graph graph = new Graph(8, 0, 7);

        graph.addEdge(0, 1, r1, 0);
        graph.addEdge(0, 2, s1, 0);
        graph.addEdge(0, 3, p1, 0);

        graph.addEdge(4, 7, r2, 0);
        graph.addEdge(5, 7, s2, 0);
        graph.addEdge(6, 7, p2, 0);

        graph.addEdge(1, 4, Integer.MAX_VALUE, 0);
        graph.addEdge(1, 5, Integer.MAX_VALUE, 1);
        graph.addEdge(1, 6, Integer.MAX_VALUE, 0);

        graph.addEdge(2, 4, Integer.MAX_VALUE, 0);
        graph.addEdge(2, 5, Integer.MAX_VALUE, 0);
        graph.addEdge(2, 6, Integer.MAX_VALUE, 1);

        graph.addEdge(3, 4, Integer.MAX_VALUE, 1);
        graph.addEdge(3, 5, Integer.MAX_VALUE, 0);
        graph.addEdge(3, 6, Integer.MAX_VALUE, 0);

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
        new min_cost_D().run();
    }

    private void run() throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("rps2.in")));
            out = new PrintWriter(new File("rps2.out"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}