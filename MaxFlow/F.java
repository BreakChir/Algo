import java.io.*;
import java.util.*;

public class F {
    private void solve() throws IOException {
        int n = nextInt();
        Graph graph = new Graph(n, 2 * n + 2, 0, 2 * n + 1);

        double[] xStart = new double[n];
        double[] xEnd = new double[n];
        double[] yStart = new double[n];
        double[] yEnd = new double[n];
        double[] speed = new double[n];

        for (int i = 0; i < n; i++) {
            xStart[i] = nextDouble();
            yStart[i] = nextDouble();
            speed[i] = nextDouble();
        }
        for (int i = 0; i < n; i++) {
            xEnd[i] = nextDouble();
            yEnd[i] = nextDouble();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double time = Math.sqrt((xEnd[j] - xStart[i]) * (xEnd[j] - xStart[i])
                        + (yEnd[j] - yStart[i]) * (yEnd[j] - yStart[i])) / speed[i];
                graph.addEdge(1 + i, n + 1 + j, time);
            }
        }

        for (int i = 1; i <= n; i++) {
            graph.addEdge(0, i, 0);
            graph.addEdge(n + i, 2 * n + 1, 0);
        }

        out.write(graph.calc() + "");
    }

    class Graph {
        ArrayList<Edge>[] edges;
        int n;
        int sz;
        int s;
        int t;
        boolean[] used;

        Graph(int n, int sz, int s, int t) {
            edges = new ArrayList[sz];
            this.n = n;
            this.sz = sz;
            this.s = s;
            this.t = t;
            used = new boolean[sz];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, double cost) {
            Edge e1 = new Edge(from, to, 0, 1, cost);
            Edge e2 = new Edge(to, from, 0, 0, cost);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        int dfs(int v, int cMin, double cost) {
            if (v == t) {
                return cMin;
            }
            used[v] = true;
            for (Edge e : edges[v]) {
                if (!used[e.to] && e.flow < e.cap && e.cost <= cost) {
                    int delta = dfs(e.to, Math.min(cMin, e.cap - e.flow), cost);
                    if (delta > 0) {
                        e.flow += delta;
                        e.rev.flow -= delta;
                        return delta;
                    }
                }
            }
            return 0;
        }

        int FordFalkerson(double cost) {
            int maxFlow = 0;
            int flow;
            Arrays.fill(used, false);
            while ((flow = dfs(s, Integer.MAX_VALUE, cost)) > 0) {
                Arrays.fill(used, false);
                maxFlow += flow;
            }
            return maxFlow;
        }

        void clean() {
            for (int i = 0; i < sz; i++) {
                for (Edge e : edges[i]) {
                    e.flow = 0;
                }
            }
        }

        double calc() {
            double eps = 0.00001;
            double l = 0.0;
            double r = (double) Integer.MAX_VALUE;
            while ((r - l) >= eps) {
                double mid = (l + r) / 2;
                int time = FordFalkerson(mid);
                if (time == n) {
                    r = mid;
                } else {
                    l = mid;
                }
                clean();
            }
            return r;
        }
    }

    class Edge {
        int from;
        int to;
        int flow;
        int cap;
        double cost;
        Edge rev;

        Edge(int from, int to, int flow, int cap, double cost) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.cap = cap;
            this.cost = cost;
        }

    }

    // ------------------

//    private BufferedReader br;
    private StringTokenizer st;
//    private PrintWriter out;
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
        new F().run();
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