import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class D {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn, rootsSize;
    private int parent[], depth[], degreeTwo[], roots[];
    private DSU DSU_tree;
    private int dp[][];
    private ArrayList<Integer> edges[];

    private void solve() throws IOException {
        n = in.nextInt();
        inisialisation(n);

        for (int i = 0; i < n; i++) {
            int x = in.nextInt() - 1;
            if (x != -1) {
                parent[i] = x;
                edges[x].add(i);
            } else {
                parent[i] = i;
                roots[rootsSize++] = i;
            }
        }

        preProcess();

        int m = in.nextInt();
        long ans = 0;
        for (int i = 0; i < m; i++) {
            int k = in.nextInt();
            long x = in.nextLong();
            long y = in.nextLong();
            long u = (x - 1 + ans) % n;
            long v = (y - 1 + ans) % n;
            if (k == 0) {
                ans = isLCA((int) v, (int) u);
                out.write(ans + "" + '\n');
            } else {
                addEdge((int) v, (int) u);
            }
        }
    }

    class DSU {
        private int parent[], rang[];

        DSU(int size) {
            parent = new int[size];
            rang = new int[size];

            for (int i = 0; i < size; i++) {
                rang[i] = 0;
                parent[i] = i;
            }
        }

        int get(int x) {
            if (parent[x] != x)
                parent[x] = get(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            x = get(x);
            y = get(y);

            if (x == y) return;

            if (rang[x] == rang[y])
                rang[x]++;
            if (rang[x] < rang[y])
                parent[x] = y;
            else parent[y] = x;
        }
    }

    private int isLCA(int v, int u) {
        if (DSU_tree.get(v) != DSU_tree.get(u)) return 0;
        return LCA(v, u) + 1;
    }

    private void addEdge(int v, int u) {
        parent[u] = v;
        dp[u][0] = v;
        edges[v].add(u);
        DSU_tree.union(v, u);
        setDepth(u, depth[v] + 1);
    }

    private void setDepth(int v, int d) {
        depth[v] = d;

        for (int j = 1; j < logn; j++) {
            dp[v][j] = dp[dp[v][j - 1]][j - 1];
        }

        for (int u : edges[v]) {
            DSU_tree.union(v, u);
            setDepth(u, d + 1);
        }
    }

    private void inisialisation(int size) {
        DSU_tree = new DSU(size);
        parent = new int[size];
        roots = new int[size];
        edges = new ArrayList[size];
        depth = new int[size];
        logn = (int) (Math.log(size) / Math.log(2)) + 1;
        dp = new int[size][logn + 1];
        degreeTwo = new int[logn + 1];

        for (int i = 0; i < size; i++) {
            edges[i] = new ArrayList<>();
        }
    }

    private void preProcess() {
        degreeTwo[0] = 1;
        for (int i = 1; i < logn + 1; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }

        for (int i = 0; i < n; i++) {
            dp[i][0] = parent[i];
        }

        for (int i = 0; i < rootsSize; i++)
            setDepth(roots[i], 0);
    }

    private int LCA(int v, int u) {
        if (depth[v] > depth[u]) {
            int tmp = v;
            v = u;
            u = tmp;
        }
        for (int i = logn - 1; i >= 0; i--) {
            if (depth[u] - depth[v] >= degreeTwo[i]) {
                u = dp[u][i];
            }
        }
        if (v == u) return v;
        for (int i = logn - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return parent[v];
    }

    private void run() {
        try {
            in = new FastScanner(new File("lca3.in"));
            out = new PrintWriter(new File("lca3.out"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        char nextChar() {
            try {
                return (char) br.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        void close() {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void nextLine() {
            try {
                br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] arg) {
        new D().run();
    }
}