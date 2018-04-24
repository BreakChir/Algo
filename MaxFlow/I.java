import java.io.*;
import java.util.*;

public class I {
    private void solve() throws IOException {
        int n = nextInt();
        Graph graph = new Graph(n + 2, 0, 0, n + 1);
        int[] score = new int[n];
        String[] s = new String[n];

        for (int i = 0; i < n; i++) {
            s[i] = br.readLine();
            for (int j = i + 1; j < n; j++) {
                switch (s[i].charAt(j)) {
                    case 'W':
                        score[i] += 3;
                        break;
                    case 'w':
                        score[i] += 2;
                        score[j] += 1;
                        break;
                    case 'l':
                        score[i] += 1;
                        score[j] += 2;
                        break;
                    case 'L':
                        score[j] += 3;
                        break;
                    case '.':
                        graph.addEdge(i + 1, j + 1, 3, i);
                        break;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            score[i] = nextInt() - score[i];
        }
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (Edge e : graph.edges[i + 1]) {
                if (e.to < i + 1) count++;
            }
            count = 3 * (graph.edges[i + 1].size() - count);
            if (score[i] < count) {
                graph.addEdge(0, i + 1, count - score[i], i + 1);
            } else {
                graph.addEdge(i + 1, n + 1, score[i] - count, i + 1);
            }
        }

        graph.dinica();
        int[][] cap = graph.getCap();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (s[i].charAt(j) == '.') {
                    System.out.print(cap[i + 1][j + 1] == 3 ? "W" : cap[i + 1][j + 1] == 2 ? 'w'
                            : cap[i + 1][j + 1] == 1 ? 'l' : 'L');
                } else {
                    System.out.print(s[i].charAt(j));
                }
            }
            System.out.println();
        }
    }

    class Graph {
        ArrayList<Edge>[] edges;
        int sz;
        int s;
        int t;
        int[] dist;
        int[] visit;

        Graph(int sz, int szE, int s, int t) {
            edges = new ArrayList[sz];
            this.sz = sz;
            this.s = s;
            this.t = t;
            dist = new int[sz];
            visit = new int[sz];
            for (int i = 0; i < sz; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addEdge(int from, int to, int cap, int num) {
            Edge e1 = new Edge(from, to, cap, num);
            Edge e2 = new Edge(to, from, 0, num);
            e1.rev = e2;
            e2.rev = e1;
            edges[from].add(e1);
            edges[to].add(e2);
        }

        int addOnWay(int v, int flow) {
            if (v == t) {
                return flow;
            }
            int retFlow = 0;
            for (int i = visit[v]; i < edges[v].size(); i++) {
                Edge e = edges[v].get(i);
                if (e.cap > 0 && dist[e.to] == dist[v] + 1) {
                    int delta = addOnWay(e.to, Math.min(flow, e.cap));
                    if (delta > 0) {
                        retFlow += delta;
                        flow -= delta;
                        e.cap -= delta;
                        e.rev.cap += delta;
                        if (flow == 0) break;
                    }
                }
                visit[v] = i + 1;
            }
            return retFlow;
        }

        boolean findShortestWay() {
            Queue<Integer> queue = new ArrayDeque<>(sz);
            queue.offer(s);
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[s] = 0;
            while (!queue.isEmpty()) {
                int v = queue.poll();
                for (Edge e : edges[v]) {
                    if (e.cap > 0 && dist[e.to] == Integer.MAX_VALUE) {
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
                while ((flow = addOnWay(s, 3)) > 0) {
                    maxFlow += flow;
                }
            }
            return maxFlow;
        }

        int[][] getCap() {
            int[][] cap = new int[sz + 1][sz + 1];
            for (int v = 1; v < sz - 1; v++) {
                for (Edge e : edges[v]) {
                    cap[v][e.to] = e.cap;
                }
            }
            return cap;
        }
    }

    class Edge {
        int from;
        int to;
        int cap;
        int num;
        boolean uz;
        Edge rev;

        Edge(int from, int to, int cap, int num) {
            this.from = from;
            this.to = to;
            this.cap = cap;
            this.num = num;
            this.uz = false;
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
        new I().run();
    }

    private void run() throws IOException {
        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream("snails.in")));
//            out = new PrintWriter(new File("snails.out"));

            solve();
//            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}