import java.io.*;
import java.util.*;

public class H {
    private static int MAXDAY = 10000;

    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        int k = nextInt();
        int s = nextInt() - 1;
        int t = nextInt() - 1;
        int day = 1;
        int count = 0;

        int[] from = new int[m];
        int[] to = new int[m];
        int[] size = new int[n];

        for (int i = 0; i < m; i++) {
            from[i] = nextInt() - 1;
            to[i] = nextInt() - 1;
            size[from[i]] += 2;
            size[to[i]] += 2;
        }

        Graph graph = new Graph(s, t, size, n);

        while (count != k) {
            for (int i = 0; i < n; i++) {
                graph.addEdge((day - 1) * n + i, day * n + i, Integer.MAX_VALUE);
            }
            for (int i = 0; i < m; i++) {
                graph.addEdge((day - 1) * n + from[i], day * n + to[i], 1);
                graph.addEdge((day - 1) * n + to[i], day * n + from[i], 1);
            }
            count += graph.dinica(day * n + t, k - count, n, day);
            day++;
        }
        out.write(--day + "\n");

        int[] place = new int[k];
        Arrays.fill(place, s + 1);
        for (int d = 0; d < day; d++) {
            ArrayList<Integer> ansFrom = new ArrayList<>();
            ArrayList<Integer> ansTo = new ArrayList<>();
            boolean[] isMove = new boolean[k];
            for (int i = 0; i < n; i++) {
                for (Edge e : graph.edges[d * n + i]) {
                    if (e.cap != Integer.MAX_VALUE && e.flow == 1) {
                        ansFrom.add(e.from - d * n + 1);
                        ansTo.add(e.to - (d + 1) * n + 1);
                    }
                }
            }
            out.write(ansFrom.size() + " ");
            for (int i = 0; i < ansFrom.size(); i++) {
                for (int j = 0; j < k; j++) {
                    if (!isMove[j] && place[j] == ansFrom.get(i)) {
                        isMove[j] = true;
                        out.write(j + 1 + " " + ansTo.get(i) + " ");
                        place[j] = ansTo.get(i);
                        break;
                    }
                }
            }
            out.write("\n");
        }
    }

    class Graph {
        ArrayList<Edge>[] edges;
        int sz;
        int s;
        int t;
        int[] dist;
        int[] visit;

        Graph(int s, int t, int[] size, int n) {
            this.sz = MAXDAY * n;
            edges = new ArrayList[sz];
            this.s = s;
            this.t = t;
            dist = new int[sz];
            visit = new int[sz];
            for (int j = 0; j < MAXDAY; j++) {
                for (int i = 0; i < n; i++) {
                    edges[n * j + i] = new ArrayList<>(size[i]);
                }
            }

        }

        void addEdge(int from, int to, int cap) {
            Edge e1 = new Edge(from, to, 0, cap);
            Edge e2 = new Edge(to, from, 0, 0);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        long addOnWay(int v, long cMin) {
            if (v == t || cMin == 0) {
                return cMin;
            }
            long flow = 0;
            for (int i = visit[v]; i < edges[v].size(); i++) {
                Edge e = edges[v].get(i);
                if (e.flow < e.cap && dist[e.to] == dist[v] + 1) {
                    long delta = addOnWay(e.to, Math.min(cMin, e.cap - e.flow));
                    if (delta > 0) {
                        cMin -= delta;
                        flow += delta;
                        e.flow += delta;
                        e.rev.flow -= delta;
                        if (cMin == 0) break;
                    }
                }
                visit[v] = i + 1;
            }
            return flow;
        }

        boolean findShortestWay(int max) {
            Queue<Integer> queue = new ArrayDeque<>(sz);
            queue.offer(s);
            Arrays.fill(dist, 0, max, Integer.MAX_VALUE);
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

        long dinica(int t, int flows, int n, int day) {
            long maxFlow = 0;
            this.t = t;
            while (findShortestWay((day + 1) * n)) {
                Arrays.fill(visit, 0, (day + 1) * n, 0);
                maxFlow += addOnWay(s, flows - maxFlow);
                if (maxFlow == flows) break;
            }
            return maxFlow;
        }
    }

    class Edge {
        int from;
        int to;
        int flow;
        int cap;
        Edge rev;

        Edge(int from, int to, int flow, int cap) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.cap = cap;
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
        new H().run();
    }

    private void run() throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
            out = new PrintWriter(new File("output.txt"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}