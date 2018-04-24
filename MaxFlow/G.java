import java.io.*;
import java.util.*;

public class G {
    private void solve() throws IOException {
        int m = nextInt();
        int n = nextInt();
        Graph graph = new Graph(n * m * 2, m, n);

        for (int i = 0; i < m; i++) {
            graph.table[i] = br.readLine();
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                switch (graph.table[i].charAt(j)) {
                    case '-':
                        graph.addOrientEdge(i * n + j, i * n + j + n * m, Integer.MAX_VALUE, i, j);
                        break;
                    case '.':
                        graph.addOrientEdge(i * n + j, i * n + j + n * m, 1, i, j);
                        break;
                    case '#':
                        graph.addOrientEdge(i * n + j, i * n + j + n * m, 0, i, j);
                        break;
                    case 'A':
                        graph.setS_T(i * n + j + n * m, true);
                        //graph.addOrientEdge(i * n + j, i * n + j + n * m, 0, i, j);
                        break;
                    default:
                        graph.setS_T(i * n + j, false);
                        //graph.addOrientEdge(i * n + j, i * n + j + n * m, 0, i, j);
                        break;
                }
            }
        }
        for (int i = 0; i < m - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                graph.addEdge(i * n + j + n * m, (i + 1) * n + j, Integer.MAX_VALUE, -i, j);
                graph.addEdge((i + 1) * n + j + n * m, i * n + j, Integer.MAX_VALUE, -i, j);

                graph.addEdge(i * n + j + n * m, i * n + (j + 1), Integer.MAX_VALUE, -i, j);
                graph.addEdge(i * n + (j + 1) + n * m, i * n + j, Integer.MAX_VALUE, -i, j);
            }
        }
        for (int j = 0; j < n - 1; j++) {
            graph.addEdge((m - 1) * n + j + n * m, (m - 1) * n + (j + 1), Integer.MAX_VALUE, -(m - 1), n);
            graph.addEdge((m - 1) * n + (j + 1) + n * m, (m - 1) * n + j, Integer.MAX_VALUE, -(m - 1), n);
        }
        for (int i = 0; i < m - 1; i++) {
            graph.addEdge(i * n + (n - 1) + n * m, (i + 1) * n + (n - 1), Integer.MAX_VALUE, -(n - 1), m);
            graph.addEdge((i + 1) * n + (n - 1) + n * m, i * n + (n - 1), Integer.MAX_VALUE, -(n - 1), m);
        }

        graph.calc();
    }

    class Graph {
        ArrayList<Edge>[] edges;
        String table[];
        int m;
        int n;
        int sz;
        int s;
        int t;
        boolean[] used;
        int[] dist;
        int[] visit;

        Graph(int sz, int m, int n) {
            edges = new ArrayList[sz];
            table = new String[m];
            this.m = m;
            this.n = n;
            this.sz = sz;
            this.s = 0;
            this.t = 0;
            used = new boolean[sz];
            dist = new int[sz];
            visit = new int[sz];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, int cap, int x, int y) {
            Edge e1 = new Edge(from, to, 0, cap, x, y, false);
            Edge e2 = new Edge(to, from, 0, 0, x, y, false);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        void addOrientEdge(int from, int to, int maxFlow, int x, int y) {
            edges[from].add(new Edge(from, to, 0, maxFlow, x, y, true));
        }

        void setS_T(int v, boolean isS) {
            if (isS) {
                this.s = v;
            } else {
                this.t = v;
            }
        }

        int addOnWay(int v, int cMin) {
            if (v == t) {
                return cMin;
            }
            for (int i = visit[v]; i < edges[v].size(); i++) {
                Edge e = edges[v].get(i);
                if (e.flow < e.cap && dist[e.to] == dist[v] + 1) {
                    int delta = addOnWay(e.to, Math.min(cMin, e.cap - e.flow));
                    if (delta > 0) {
                        e.flow += delta;
                        if (!e.isTerm)
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
            return dist[t] != Integer.MAX_VALUE;
        }

        long dinica() {
            long maxFlow = 0;
            while (findShortestWay()) {
                Arrays.fill(visit, 0);
                long flow;
                while ((flow = addOnWay(s, Integer.MAX_VALUE)) > 0) {
                    maxFlow += flow;
                }
            }
            return maxFlow;
        }

        ArrayList<Integer> list = new ArrayList<>();

        void calc() {
            long ans = dinica();
            if (ans >= Integer.MAX_VALUE) {
                System.out.print(-1);
                return;
            }
            System.out.println(ans);
            Arrays.fill(used, false);
            dfs(s);

            char[][] ansT = new char[m][n];
            for (int i = 0; i < m; i++) {
                ansT[i] = table[i].toCharArray();
            }

            for (int v : list) {
                for (Edge e : edges[v]) {
                    if (used[e.to]) continue;
                    if (e.flow == 1) ansT[e.x][e.y] = '+';
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(ansT[i][j]);
                }
                System.out.println();
            }

        }

        void dfs(int v) {
            used[v] = true;
            list.add(v);
            for (Edge e : edges[v]) {
                if (!used[e.to] && e.flow < e.cap) {
                    dfs(e.to);
                }
            }
        }
    }

    class Edge {
        int from;
        int to;
        int flow;
        int cap;
        int x;
        int y;
        boolean isTerm;
        Edge rev;

        Edge(int from, int to, int flow, int cap, int x, int y, boolean isTerm) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.cap = cap;
            this.x = x;
            this.y = y;
            this.isTerm = isTerm;
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

    public static void main(String[] args) throws IOException {
        new G().run();
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