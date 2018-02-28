import java.io.*;
import java.util.*;

public class A {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn;
    private int parent[], depth[], degreeTwo[];
    private int dp[][];
    private ArrayList<Integer> edges[];

    private void solve() throws IOException {
        n = in.nextInt();
        parent = new int[n];
        edges = new ArrayList[n];
        depth = new int[n];
        logn = (int) (Math.log(n) / Math.log(2)) + 1;
        dp = new int[n][logn + 1];
        degreeTwo = new int[logn + 1];

        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            int x = in.nextInt() - 1;
            parent[i] = x;
            edges[x].add(i);
        }

        preProcess();

        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            out.write(LCA(in.nextInt() - 1, in.nextInt() - 1) + 1 + "" + '\n');
        }
    }

    private void setDepth(int v, int d) {
        depth[v] = d;
        for (int u : edges[v]) {
            setDepth(u, d + 1);
        }
    }

    private void preProcess() {
        setDepth(0, 0);

        degreeTwo[0] = 1;
        for (int i = 1; i < logn + 1; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }

        for (int i = 0; i < n; i++) {
            dp[i][0] = parent[i];
        }
        for (int j = 1; j < logn; j++) {
            for (int i = 1; i < n; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
            }
        }

    }

    private int LCA(int v, int u) {
        if (depth[v] >= depth[u]) {
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
            in = new FastScanner(new File("lca.in"));
            out = new PrintWriter(new File("lca.out"));

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
        new A().run();
    }
}