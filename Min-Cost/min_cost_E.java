import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class min_cost_E {
    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();

        Graph graph = new Graph(2 * n + 2, 0, 2 * n + 1);

        for (int i = 1; i <= n; i++) {
            graph.addEdge(n + i, i, Integer.MAX_VALUE, nextInt());

            graph.addEdge(0, n + i, 1, 0);
            graph.addEdge(i, 2 * n + 1, 1, 0);
            graph.addEdge(i, n + i, Integer.MAX_VALUE, 0);
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt();
            int to = nextInt();
            int cost = nextInt();
            graph.addEdge(n + from, to, Integer.MAX_VALUE, cost);
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
        long[] minim;
        int[] edgesOnWay;

        Graph(int sz, int s, int t) {
            edges = new ArrayList[sz];
            this.sz = sz;
            this.s = s;
            this.t = t;
            cost = 0;
            minim = new long[sz];
            dist = new long[sz];
            par = new int[sz];
            edgesOnWay = new int[sz];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, int cap, int cost) {
            edges[from].add(new Edge(from, to, 0, cap, cost, edges[to].size()));
            edges[to].add(new Edge(to, from, 0, 0, -cost, edges[from].size() - 1));
        }

        boolean findMinCostWay() {
            ArrayDeque<Integer> queue = new ArrayDeque<>(sz);
            queue.addLast(s);
            Arrays.fill(dist, Long.MAX_VALUE);
            int[] mark = new int[sz];
            dist[s] = 0;
            minim[s] = Long.MAX_VALUE;
            while (!queue.isEmpty()) {
                int v = queue.poll();
                mark[v] = 2;
                for (int i = 0; i < edges[v].size(); i++) {
                    Edge e = edges[v].get(i);
                    if (e.flow < e.cap && dist[e.from] + e.cost < dist[e.to]) {
                        dist[e.to] = dist[e.from] + e.cost;
                        minim[e.to] = Math.min(minim[e.from], e.cap - e.flow);
                        if (mark[e.to] == 0) {
                            queue.addLast(e.to);
                        } else if (mark[e.to] == 2) {
                            queue.addFirst(e.to);
                        }
                        mark[e.to] = 1;
                        par[e.to] = e.from;
                        edgesOnWay[e.to] = i;
                    }
                }
            }
            return dist[t] != Long.MAX_VALUE;
        }

        long minCostFlow() {
            while (findMinCostWay()) {
                long add = minim[t];

                for (int v = t; v != s; v = par[v]) {
                    Edge e = edges[par[v]].get(edgesOnWay[v]);
                    edges[par[v]].get(edgesOnWay[v]).flow += add;
                    edges[e.to].get(e.rev).flow -= add;
                }
            }

            for (int v = 0; v < sz; ++v) {
                for (Edge e : edges[v]) {
                    if (e.flow > 0) {
                        cost += e.flow * e.cost;
                    }
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
        int rev;

        Edge(int from, int to, int flow, int cap, int cost, int rev) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.cap = cap;
            this.cost = cost;
            this.rev = rev;
        }
    }

    // ------------------

//    private BufferedReader br;
//    private PrintWriter out;
    private StringTokenizer st;
    private PrintWriter out = new PrintWriter(System.out);
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
        new min_cost_E().run();
    }

    private void run() throws IOException {
        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
//            out = new PrintWriter(new File("output.txt"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}