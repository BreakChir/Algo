import java.io.*;
import java.util.*;

public class A {
    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        Graph graph = new Graph(n, m, 0, n - 1);

        for (int i = 0; i < m; i++) {
            graph.addEdge(nextInt() - 1, nextInt() - 1, 0, nextDouble(), i);
        }
        graph.out();
    }

    class Graph {
        ArrayList<Edge>[] edges;
        int sz;
        int szE;
        int s;
        int t;
        int[] dist;
        int[] visit;

        Graph(int sz, int szE, int s, int t) {
            edges = new ArrayList[sz];
            this.sz = sz;
            this.szE = szE;
            this.s = s;
            this.t = t;
            dist = new int[sz];
            visit = new int[sz];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, double flow, double maxFlow, int num) {
            Edge e1 = new Edge(from, to, flow, maxFlow, num, true);
            Edge e2 = new Edge(to, from, -flow, maxFlow, num, false);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        double addOnWay(int v, double cMin) {
            if (v == t) {
                return cMin;
            }
            for (int i = visit[v]; i < edges[v].size(); i++) {
                Edge e = edges[v].get(i);
                if (e.flow < e.cap && dist[e.to] == dist[v] + 1) {
                    double delta = addOnWay(e.to, Math.min(cMin, e.cap - e.flow));
                    if (delta > 0) {
                        e.flow += delta;
                        e.rev.flow -= delta;
                        return delta;
                    }
                }
                visit[v] = i + 1;
            }
            return 0;
        }

        boolean findShortestWay() {
            Queue<Integer> queue = new ArrayDeque<>(sz);
            queue.offer(s);
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[s] = 0;
            while (!queue.isEmpty()) {
                int v = queue.poll();
                for (Edge e : edges[v]) {
                    if (e.flow < e.cap && dist[e.to] == Integer.MAX_VALUE) {
                        dist[e.to] = dist[v] + 1;
                        queue.offer(e.to);
                        if (e.to == t) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        double dinica() {
            double maxFlow = 0;
            while (findShortestWay()) {
                Arrays.fill(visit, 0);
                double flow;
                while ((flow = addOnWay(s, Integer.MAX_VALUE)) > 0) {
                    maxFlow += flow;
                }
            }
            return maxFlow;
        }

        void out() {
            out.write(dinica() + "\n");
            double[] flows = new double[szE];
            for (int i = 0; i < sz; i++) {
                for (Edge v : edges[i]) {
                    if (!v.isMain) continue;
                    flows[v.num] = v.flow;
                }
            }
            for (int i = 0; i < szE; i++) {
                out.write(flows[i] + "\n");
            }
        }
    }

    class Edge {
        int from;
        int to;
        double flow;
        double cap;
        int num;
        boolean isMain;
        Edge rev;

        Edge(int from, int to, double flow, double cap, int num, boolean isMain) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.cap = cap;
            this.num = num;
            this.isMain = isMain;
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
        new A().run();
    }

    private void run() throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("flow.in")));
            out = new PrintWriter(new File("flow.out"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}