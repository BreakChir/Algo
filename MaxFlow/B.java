import java.io.*;
import java.util.*;

public class B {
    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        Graph graph = new Graph(n, m, 0, n - 1);

        for (int i = 0; i < m; i++) {
            graph.addEdge(nextInt() - 1, nextInt() - 1, 0, nextInt(), i);
        }

        graph.output();
    }

    class Graph {
        ArrayList<Edge>[] edges;
        int sz;
        int szE;
        int s;
        int t;
        boolean[] used;
        int[] dist;
        int[] visit;

        Graph(int sz, int szE, int s, int t) {
            edges = new ArrayList[sz];
            this.sz = sz;
            this.szE = szE;
            this.s = s;
            this.t = t;
            used = new boolean[sz];
            dist = new int[sz];
            visit = new int[sz];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, int flow, int maxFlow, int num) {
            Edge e1 = new Edge(from, to, flow, maxFlow, num);
            Edge e2 = new Edge(to, from, -flow, maxFlow, num);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        long addOnWay(int v, long cMin) {
            if (v == t) {
                return cMin;
            }
            for (int i = visit[v]; i < edges[v].size(); i++) {
                Edge e = edges[v].get(i);
                if (e.flow < e.maxFlow && dist[e.to] == dist[v] + 1) {
                    long delta = addOnWay(e.to, Math.min(cMin, e.maxFlow - e.flow));
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
            Arrays.fill(used, false);
            used[s] = true;
            while (!queue.isEmpty()) {
                int v = queue.poll();
                for (Edge e : edges[v]) {
                    if (e.flow < e.maxFlow && !used[e.to]) {
                        used[e.to] = true;
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

        long dinica() {
            long maxFlow = 0;
            while (findShortestWay()) {
                Arrays.fill(visit, 0);
                long flow;
                while ((flow = addOnWay(s, Long.MAX_VALUE)) > 0) {
                    maxFlow += flow;
                }
            }
            return maxFlow;
        }

        ArrayList<Integer> minCut = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();

        void output() {
            long ans = dinica();
            Arrays.fill(used, false);
            dfs(0);
            for (int v : list) {
                for (Edge e : edges[v]) {
                    if (used[e.to]) continue;
                    minCut.add(e.num + 1);
                }
            }

            Collections.sort(minCut);
            out.write(minCut.size() + " " + ans + "\n");
            for (int num : minCut) {
                out.write(num + " ");
            }
        }

        void dfs(int v) {
            used[v] = true;
            list.add(v);
            for (Edge e : edges[v]) {
                if (!used[e.to] && e.flow < e.maxFlow && e.maxFlow != 0) {
                    dfs(e.to);
                }
            }
        }
    }

    class Edge {
        int from;
        int to;
        int flow;
        int maxFlow;
        int num;
        Edge rev;

        Edge(int from, int to, int flow, int maxFlow, int num) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.maxFlow = maxFlow;
            this.num = num;
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
        new B().run();
    }

    private void run() throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("cut.in")));
            out = new PrintWriter(new File("cut.out"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}