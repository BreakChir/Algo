import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class E {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn;
    private int parent[], depth[], degreeTwo[];
    private int dp[][];
    private ArrayList<Integer> edges[];

    private void solve() throws IOException {
        n = in.nextInt();
        while (n != 0) {

            inisialisation(n);

            for (int i = 0; i < n - 1; i++) {
                int x = in.nextInt() - 1;
                int y = in.nextInt() - 1;
                edges[x].add(y);
                edges[y].add(x);
            }

            preProcess();

            int m = in.nextInt();
            int root = 0;
            for (int i = 0; i < m; i++) {
                String str = in.next();
                if (str.equals("?")) {
                    int x = in.nextInt() - 1;
                    int y = in.nextInt() - 1;
                    int z = LCA(x, y);

                    int rootAndX_LCA = LCA(root, x);
                    int rootAndY_LCA = LCA(root, y);

                    if (depth[rootAndX_LCA] > depth[z]) {
                        z = rootAndX_LCA;
                    }
                    if (depth[rootAndY_LCA] > depth[z]) {
                        z = rootAndY_LCA;
                    }

                    out.write(z + 1 + "" + '\n');
                } else {
                    root = in.nextInt() - 1;
                }
            }

            n = in.nextInt();
        }
    }

    private void setDepth(int v, int d) {
        depth[v] = d;

        for (int u : edges[v]) {
            if (u == parent[v]) continue;
            parent[u] = v;
            setDepth(u, d + 1);
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

    private void inisialisation(int size) {
        parent = new int[size];
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
        parent[0] = 0;
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

    private void run() {
        try {
            in = new FastScanner(new File("dynamic.in"));
            out = new PrintWriter(new File("dynamic.out"));

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
        new E().run();
    }
}