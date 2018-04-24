import java.io.*;
import java.util.*;

public class D {
    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        Graph graph = new Graph(n, n + m + 2, 0, n + m + 1);

        for (int i = 1; i <= n; i++) {
            graph.addEdge(0, i, 0, 1);
            int v;
            while ((v = nextInt()) != 0)
                graph.addEdge(i, n + v, 0, 1);
        }

        for (int i = 1; i <= m; i++) {
            graph.addEdge(n + i, n + m + 1, 0, 1);
        }

        graph.output();
    }

    class Graph {
        ArrayList<Edge>[] edges;
        int sz;
        int szF;
        int s;
        int t;
        boolean[] used;
        int[] dist;
        int[] visit;

        Graph(int szF, int sz, int s, int t) {
            edges = new ArrayList[sz];
            this.sz = sz;
            this.szF = szF;
            this.s = s;
            this.t = t;
            used = new boolean[sz];
            dist = new int[sz];
            visit = new int[sz];
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

        int dfs(int v, int cMin) {
            if (v == t) {
                return cMin;
            }
            used[v] = true;
            for (Edge e : edges[v]) {
                if (!used[e.to] && e.flow < e.maxFlow) {
                    int delta = dfs(e.to, Math.min(cMin, e.maxFlow - e.flow));
                    if (delta > 0) {
                        e.flow += delta;
                        e.rev.flow -= delta;
                        return delta;
                    }
                }
            }
            return 0;
        }

        long FordFalkerson() {
            long maxFlow = 0;
            int flow;
            Arrays.fill(used, false);
            while ((flow = dfs(s, Integer.MAX_VALUE)) > 0) {
                Arrays.fill(used, false);
                maxFlow += flow;
            }
            return maxFlow;
        }

        void output() {
            long ans = FordFalkerson();
            System.out.println(ans);

            for (int i = 1; i <= szF; i++) {
                for (Edge e : edges[i]) {
                    if (e.flow == 1) {
                        System.out.println(e.from + " " + (e.to - szF));
                    }
                }
            }
        }
    }

    class Edge {
        int from;
        int to;
        int flow;
        int maxFlow;
        Edge rev;

        Edge(int from, int to, int flow, int maxFlow) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.maxFlow = maxFlow;
        }

    }

    // ------------------

    //    private BufferedReader br;
    //    private PrintWriter out;
    private StringTokenizer st;
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

    public static void main(String[] args) throws IOException {
        new D().run();
    }

    private void run() throws IOException {
        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
//            out = new PrintWriter(new File("output.txt"));

            solve();
//            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}