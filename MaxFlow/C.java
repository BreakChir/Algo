import java.io.*;
import java.util.*;

public class C {
    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        Graph graph = new Graph(n, m, nextInt() - 1, nextInt() - 1);

        for (int i = 0; i < m; i++) {
            graph.addEdge(nextInt() - 1, nextInt() - 1, 0, 1, i);
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
            Edge e2 = new Edge(to, from, -flow, 0, num);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        int addOnWay(int v, int cMin) {
            if (v == t) {
                return cMin;
            }
            for (int i = visit[v]; i < edges[v].size(); i++) {
                Edge e = edges[v].get(i);
                if (e.flow < e.maxFlow && dist[e.to] == dist[v] + 1) {
                    int delta = addOnWay(e.to, Math.min(cMin, e.maxFlow - e.flow));
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
                int flow;
                while ((flow = addOnWay(s, Integer.MAX_VALUE)) > 0) {
                    maxFlow += flow;
                }
            }
            return maxFlow;
        }

        ArrayList<Integer> list = new ArrayList<>();
        int size;
        boolean wasFirst = false;

        void output() {
            dinica();
            Arrays.fill(used, false);
            if (dfs(s) && dfs(s)) {
                out.write("YES\n");
                for (int i = 0; i < size; i++) {
                    out.write(list.get(i) + " ");
                }
                out.write("\n");
                for (int i = size; i < list.size(); i++) {
                    out.write(list.get(i) + " ");
                }
            } else out.write("NO");


        }

        boolean dfs(int v) {
            list.add(v + 1);
            if (v == t) {
                if (!wasFirst) size = list.size();
                wasFirst = true;
                return true;
            }

            for (Edge e : edges[v]) {
                if (e.uz) continue;
                if (e.flow == 1) {
                    e.uz = true;
                    return dfs(e.to);
                }
            }
            return false;
        }

    }

    class Edge {
        int from;
        int to;
        int flow;
        int maxFlow;
        int num;
        boolean uz;
        Edge rev;

        Edge(int from, int to, int flow, int maxFlow, int num) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.maxFlow = maxFlow;
            this.num = num;
            this.uz = false;
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
        new C().run();
    }

    private void run() throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("snails.in")));
            out = new PrintWriter(new File("snails.out"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}