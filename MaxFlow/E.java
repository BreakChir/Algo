import java.io.*;
import java.util.*;

public class E {
    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        Graph graph1 = new Graph(2 * (n + m), 0, 2 * (n + m) - 1);
        Graph graph2 = new Graph(2 * (n + m), 0, 2 * (n + m) - 1);

        int[][] color = new int[n][m];
        for (int i = 0; i < n; i++) {
            String s = next();
            for (int j = 0; j < m; j++) {
                color[i][j] = (i + j) % 2;
                color[i][j] -= s.charAt(j) == 'B' ? 1 : 0;
                color[i][j] = Math.abs(color[i][j]);
            }
        }
        graph1.fillGraph(color, n, m);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                color[i][j] = 1 - color[i][j];
            }
        }
        graph2.fillGraph(color, n, m);

        int w = 0;
        if (graph1.FordFalkerson() > graph2.FordFalkerson()) {
            graph1 = graph2;
            w = 1;
        }
        graph1.getAns(n, m, w);

    }

    class Graph {
        ArrayList<Edge>[] edges;
        int sz;
        int s;
        int t;
        int maxFlow;
        boolean[] used;

        Graph(int sz, int s, int t) {
            edges = new ArrayList[sz];
            this.sz = sz;
            this.s = s;
            this.t = t;
            used = new boolean[sz];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, int flow, int maxFlow, int x, int y) {
            Edge e1 = new Edge(from, to, flow, maxFlow, x, y);
            Edge e2 = new Edge(to, from, -flow, 0, x, y);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        void fillGraph(int[][] color, int n, int m) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (color[i][j] == 1) {
                        int from = i - j;
                        if (from <= 0) from = n - from;
                        int to = n + m + i + j;
                        addEdge(from, to, 0, 1, i, j);
                    }
                }
            }
            for (int i = 1; i < n + m; i++) {
                addEdge(s, i, 0, 1, i, i);
            }
            for (int i = n + m; i < 2 * (n + m); i++) {
                addEdge(i, t, 0, 1, i, i);
            }
        }

        void getAns(int n, int m, int w) {
            System.out.println(maxFlow);
            for (int i = 0; i < n + m; i++) {
                if (used[i]) continue;
                for (Edge e : edges[i]) {
                    if (e.to == s) continue;
                    System.out.print("2 " + (e.x + 1) + " " + (e.y + 1) + " ");
                    if ((e.x + e.y) % 2 == w) {
                        System.out.println("W");
                    } else {
                        System.out.println("B");
                    }
                    break;
                }
            }
            for (int i = n + m; i < 2 * (n + m); i++) {
                if (!used[i]) continue;
                for (Edge e : edges[i]) {
                    if (e.to == t) continue;
                    System.out.print("1 " + (e.x + 1) + " " + (e.y + 1) + " ");
                    if ((e.x + e.y) % 2 == w) {
                        System.out.println("W");
                    } else {
                        System.out.println("B");
                    }
                    break;
                }
            }
        }

        int dfs(int v, int cMin) {
            if (v == t) {
                return cMin;
            }
            used[v] = true;
            for (Edge e : edges[v]) {
                if (!used[e.to] && e.flow < e.cap) {
                    int delta = dfs(e.to, Math.min(cMin, e.cap - e.flow));
                    if (delta > 0) {
                        e.flow += delta;
                        e.rev.flow -= delta;
                        return delta;
                    }
                }
            }
            return 0;
        }

        int FordFalkerson() {
            int maxFlow = 0;
            int flow;
            Arrays.fill(used, false);
            while ((flow = dfs(s, Integer.MAX_VALUE)) > 0) {
                Arrays.fill(used, false);
                maxFlow += flow;
            }
            this.maxFlow = maxFlow;
            return maxFlow;
        }
    }

    class Edge {
        int from;
        int to;
        int flow;
        int cap;
        int x;
        int y;
        Edge rev;

        Edge(int from, int to, int flow, int cap, int x, int y) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.cap = cap;
            this.x = x;
            this.y = y;
        }
    }

    // ------------------

//    private BufferedReader br;
    private StringTokenizer st;
//    private PrintWriter out;
//    private PrintWriter out = new PrintWriter(System.out);
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
        new E().run();
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